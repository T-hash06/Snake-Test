package objects;

import logic.NeuralNetwork;

import java.awt.*;

public final class Snake extends GameObject {

    public enum Direction {
        RIGHT, LEFT, UP, DOWN
    }

    private final boolean isHead;
    private Direction direction;
    private Snake tail;
    private Point foodPosition;

    private int directionChanges;
    private int positionLimit;
    private boolean isAlive;
    private double score;
    private int tailSize;

    private final double[] senses = new double[4];
    private final int MAX_MOVEMENTS = 60;
    private NeuralNetwork brain = new NeuralNetwork(new int[]{senses.length, 10, 16, 8, 4, 4});

    public Snake(int x, int y, Point foodPosition, Color color, int cellsCount) {
        super(x * STANDARD_SIZE, y * STANDARD_SIZE, color);

        this.direction = Direction.RIGHT;
        this.isHead = true;
        this.isAlive = true;
        this.positionLimit = cellsCount - 1;

        this.foodPosition = foodPosition;
        this.directionChanges = 0;
        this.score = 0;
        this.tailSize = 0;
    }

    public Snake(int x, int y, Point foodPosition, NeuralNetwork brain, Color color, int cellsCount) {
        super(x * STANDARD_SIZE, y * STANDARD_SIZE, color);

        this.direction = Direction.RIGHT;
        this.isHead = true;
        this.isAlive = true;
        this.positionLimit = cellsCount - 1;

        this.foodPosition = foodPosition;
        this.directionChanges = 0;
        this.score = 0;
        this.tailSize = 0;
        this.brain = brain;
    }

    public Snake(boolean isHead, int x, int y, Color color) {
        super(x * STANDARD_SIZE, y * STANDARD_SIZE, color);

        this.direction = Direction.RIGHT;
        this.isHead = isHead;
    }

    @Override
    public void tick() {
        int lastX = this.position.x;
        int lastY = this.position.y;

        if (this.isHead) {
            double floatX = (float) this.position.x / (positionLimit * STANDARD_SIZE);
            double floatY = (float) this.position.y / (positionLimit * STANDARD_SIZE);

            double floatFoodX = (float) this.foodPosition.x / (positionLimit * STANDARD_SIZE);
            double floatFoodY = (float) this.foodPosition.y / (positionLimit * STANDARD_SIZE);

            double floatDirectionChanges = (float) this.directionChanges / this.MAX_MOVEMENTS;

            double[] choose = this.brain.calculate(new double[]{floatX, floatY, floatFoodX, floatFoodY});
            setDirectionWithBrain(choose);

            if (this.direction == Direction.RIGHT) {
                this.position.x += this.width;
            }
            if (this.direction == Direction.LEFT) {
                this.position.x -= this.width;
            }
            if (this.direction == Direction.DOWN) {
                this.position.y += this.height;
            }
            if (this.direction == Direction.UP) {
                this.position.y -= this.height;
            }

            if (this.directionChanges > MAX_MOVEMENTS) {
                kill(1);
                return;
            }
        }

        if (this.tail != null) {
            this.tail.tick(lastX, lastY);
        }

        if (this.inCollisionWithTail(this) || this.outOfLimits()) kill();
    }

    private void tick(int x, int y) {
        int lastX = this.position.x;
        int lastY = this.position.y;

        this.position.x = x;
        this.position.y = y;

        if (this.tail != null) {
            this.tail.tick(lastX, lastY);
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);

        g.fillRect(this.position.x, this.position.y, this.width, this.height);

        if (this.tail != null)
            tail.draw(g);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void growTail() {
        if (this.tail == null) {
            this.tail = new Snake(false, this.position.x, this.position.y, this.color);
            this.tailSize++;
            this.directionChanges = 0;
        } else {
            this.tail.growTail();
        }
    }

    private boolean outOfLimits() {
        return (this.position.x < 0 || this.position.x > positionLimit * STANDARD_SIZE) || (this.position.y < 0 || this.position.y > positionLimit * STANDARD_SIZE);
    }

    public boolean inCollisionWithTail(Snake head) {
        if (this.tail != null) {
            if (this.inCollision(head, this.tail)) return true;
            else return this.tail.inCollisionWithTail(head);
        }
        return false;
    }

    private void setDirectionWithBrain(double[] decisions) {
        if (decisions.length != 4) {
            System.out.println("Decisions are corrupted!");
            return;
        }

        int bestDecision = getBestDecisionIndex(decisions);

        if (bestDecision == 0 && this.direction != Direction.UP) {
            this.directionChanges++;
            this.direction = Direction.UP;
        }
        if (bestDecision == 1 && this.direction != Direction.DOWN) {
            this.directionChanges++;
            this.direction = Direction.DOWN;
        }
        if (bestDecision == 2 && this.direction != Direction.LEFT) {
            this.directionChanges++;
            this.direction = Direction.LEFT;
        }
        if (bestDecision == 3 && this.direction != Direction.RIGHT) {
            this.directionChanges++;
            this.direction = Direction.RIGHT;
        }
    }

    public int getBestDecisionIndex(double[] decisions) {
        int index = 0;
        for (int i = 1; i < decisions.length; i++) {
            if (decisions[i] > decisions[index]) {
                index = i;
            }
        }

        return index;
    }

    public boolean isAlive() {
        return isAlive;
    }

    private void kill() {
        this.isAlive = false;
        this.score = (0.5 * this.directionChanges * this.tailSize) + 0.25 * this.directionChanges;
    }

    private void kill(int forcedScore) {
        this.isAlive = false;
        this.score = forcedScore;
    }

    public double getScore() {
        return score;
    }

    public NeuralNetwork getBrain() {
        return brain;
    }
}
