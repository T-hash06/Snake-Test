package logic;

public final class NeuralNetwork {

    private final int[] topology;
    private NeuralLayer[] layers;

    public NeuralNetwork(int[] topology) {

        this.topology = topology;
        this.layers = new NeuralLayer[topology.length];
        for (int index = 0; index < topology.length; index++) {
            if (index == 0) {
                this.layers[index] = new NeuralLayer(topology[index]);
                continue;
            }

            this.layers[index] = new NeuralLayer(topology[index], topology[index - 1]);
        }
    }

    public double[] calculate(double[] inputValues) {

        double helper = 0;
        double[] previousValues = null;

        for (int index = 1; index < topology.length; index++) {
            if (index == 1) {
                previousValues = this.layers[index].updateValues(inputValues);
                continue;
            }

            previousValues = this.layers[index].updateValues(previousValues);
        }

        return previousValues;
    }
}
