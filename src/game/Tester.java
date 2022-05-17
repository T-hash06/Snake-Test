package game;

import logic.NeuralLayer;

import java.util.Arrays;

public final class Tester {

    public static void main(String[] args) {
//        int[] topology = {2, 4, 4, 2};
//
        NeuralLayer layer = new NeuralLayer(4, 2);
        NeuralLayer layer1 = new NeuralLayer(4, 2);
        NeuralLayer[] child = NeuralLayer.createTwoByWeightsArray(4, 2, layer.getWeightsAsArray(), layer1.getWeightsAsArray(), NeuralLayer.ReproductionMethods.ALTERNATE_DIVISION);
        double[] result = layer.updateValues(new double[]{1, 0.5});
        double[] result2 = layer1.updateValues(new double[]{1, 0.5});
        double[] childResult = child[0].updateValues(new double[]{1, 0.5});
        double[] childResult2 = child[1].updateValues(new double[]{1, 0.5});
        System.out.println(Arrays.deepToString(layer.getWeights()));
        System.out.println(Arrays.deepToString(layer1.getWeights()));
        System.out.println(Arrays.deepToString(child[0].getWeights()));
        System.out.println(Arrays.deepToString(child[1].getWeights()));
//
//        System.out.println("=================== RESULTS ========================");
//        System.out.println(Arrays.toString(result));
//        System.out.println(Arrays.toString(result2));
//        System.out.println(Arrays.toString(childResult));

//        NeuralNetwork neuralNetwork = new NeuralNetwork(new int[]{2, 2, 4});
//        NeuralNetwork neuralNetwork1 = new NeuralNetwork(new int[]{2, 2, 4});
//        NeuralNetwork child = NeuralNetwork.createByAverageLayers(neuralNetwork, neuralNetwork1);
//        System.out.println(Arrays.toString(neuralNetwork.calculate(new double[]{1, 1})));
//        System.out.println(Arrays.toString(neuralNetwork1.calculate(new double[]{1, 1})));
//        System.out.println(Arrays.toString(child.calculate(new double[]{1, 1})));
        //        neuralNetwork.resetLayers();
//        System.out.println("========================");
//        System.out.println(Arrays.toString(neuralNetwork.calculate(new double[]{1, 1})));
//        System.out.println(Arrays.toString(neuralNetwork1.calculate(new double[]{1, 1})));
    }
}
