package game;

import logic.NeuralNetwork;

public final class Tester {

    public static void main(String[] args) {
        int[] topology = {2, 4, 8, 2};

        NeuralNetwork neuralNetwork = new NeuralNetwork(topology);

    }
}
