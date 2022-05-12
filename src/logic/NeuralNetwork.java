package logic;

import java.util.Arrays;

public final class NeuralNetwork {

    private final int[] topology;
    private double[][] weights;

    public NeuralNetwork(int[] topology) {

        this.topology = topology;
        this.weights = new double[topology.length][];

        for (int index = 0; index < topology.length; index++) {
            this.weights[index] = new double[topology[index]];

            for (int neuron = 0; neuron < topology[index]; neuron++) {
                this.weights[index][neuron] = 1;
            }
        }

        for (double[] layer : weights) {
            System.out.println(Arrays.toString(layer));
        }
    }
}
