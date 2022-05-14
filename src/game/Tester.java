package game;

import logic.NeuralNetwork;

import java.util.Arrays;

public final class Tester {

    public static void main(String[] args) {
        int[] topology = {2, 4, 4, 2};

        NeuralNetwork neuralNetwork = new NeuralNetwork(topology);
        var result = neuralNetwork.calculate(new double[]{0.1, 1});
        var result2 = neuralNetwork.calculate(new double[]{0.6, -0.6});

        System.out.println(Arrays.toString(result));
        System.out.println(Arrays.toString(result2));
    }
}
