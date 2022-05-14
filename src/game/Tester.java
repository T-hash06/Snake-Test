package game;

import logic.NeuralNetwork;

public final class Tester {

    public static void main(String[] args) {
        int[] topology = {2, 4, 4, 2};

        NeuralNetwork neuralNetwork = new NeuralNetwork(topology);
        var result2 = neuralNetwork.calculate(new double[]{0.6, -0.6});
    }
}
