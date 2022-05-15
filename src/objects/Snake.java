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
    private final NeuralNetwork brain = new NeuralNetwork(new int[]{senses.length, 8, 4});

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

    public Snake(boolean isHead, int x, int y, Color color) {
        super(x * STANDARD_SIZE, y * STANDARD_SIZE, color);

        this.direction = Direction.RIGHT;
        this.isHead = isHead;
    }

    @Override
    public void tick() {
        int lastX = this.x;
        int lastY = this.y;


        if (this.isHead) {
            double floatX = (float) x / (positionLimit * STANDARD_SIZE);
            double floatY = (float) y / (positionLimit * STANDARD_SIZE);

            double floatFoodX = (float) this.foodPosition.x / (positionLimit * STANDARD_SIZE);
            double floatFoodY = (float) this.foodPosition.y / (positionLimit * STANDARD_SIZE);

            double[] choose = this.brain.calculate(new double[]{floatX, floatY, floatFoodX, floatFoodY});
            setDirectionWithBrain(choose);

            if (this.direction == Direction.RIGHT) {
                this.x += this.width;
            }
            if (this.direction == Direction.LEFT) {
                this.x -= this.width;
            }
            if (this.direction == Direction.DOWN) {
                this.y += this.height;
            }
            if (this.direction == Direction.UP) {
                this.y -= this.height;
            }

            if (this.directionChanges > 20) {
                kill(0);
                return;
            }
        }

        if (this.tail != null) {
            this.tail.tick(lastX, lastY);
        }

        if (this.inCollisionWithTail(this) || this.outOfLimits()) kill();
    }

    private void tick(int x, int y) {
        int lastX = this.x;
        int lastY = this.y;

        this.x = x;
        this.y = y;

        if (this.tail != null) {
            this.tail.tick(lastX, lastY);
        }
    }


    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);

        g.fillRect(this.x, this.y, this.width, this.height);

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
            this.tail = new Snake(false, this.x, this.y, this.color);
            this.tailSize++;
        } else {
            this.tail.growTail();
        }
    }

    private boolean outOfLimits() {
        return (this.x < 0 || this.x > positionLimit * STANDARD_SIZE) || (this.y < 0 || this.y > positionLimit * STANDARD_SIZE);
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
            System.out.println("Desicions are corrupted!");
            return;
        }

        int bestDesicion = getBestDesicionIndex(decisions);

        if (bestDesicion == 0 && this.direction != Direction.UP) {
            this.directionChanges++;
            this.direction = Direction.UP;
        }
        if (bestDesicion == 1 && this.direction != Direction.DOWN) {
            this.directionChanges++;
            this.direction = Direction.DOWN;
        }
        if (bestDesicion == 2 && this.direction != Direction.LEFT) {
            this.directionChanges++;
            this.direction = Direction.LEFT;
        }
        if (bestDesicion == 3 && this.direction != Direction.RIGHT) {
            this.directionChanges++;
            this.direction = Direction.RIGHT;
        }
    }

    public int getBestDesicionIndex(double[] desicions) {
        int index = 0;
        for (int i = 1; i < desicions.length; i++) {
            if (desicions[i] > desicions[index]) {
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
