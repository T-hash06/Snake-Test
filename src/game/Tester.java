package game;

import logic.NeuralLayer;

import java.util.Arrays;

public final class Tester {

    public static void main(String[] args) {
        int[] topology = {2, 4, 4, 2};

        NeuralLayer layer = new NeuralLayer(4, 2);
        NeuralLayer layer1 = new NeuralLayer(4, 2);
        NeuralLayer child = NeuralLayer.createByAverageWeightsArray(4, 2, layer.getWeightsAsArray(), layer1.getWeightsAsArray());
        double[] result = layer.updateValues(new double[]{1, 0.5});
        double[] result2 = layer1.updateValues(new double[]{1, 0.5});
        double[] childResult = child.updateValues(new double[]{1, 0.5});
        System.out.println(Arrays.deepToString(layer.getWeights()));
        System.out.println(Arrays.deepToString(layer1.getWeights()));
        System.out.println(Arrays.deepToString(child.getWeights()));

        System.out.println("=================== RESULTS ========================");
        System.out.println(Arrays.toString(result));
        System.out.println(Arrays.toString(result2));
        System.out.println(Arrays.toString(childResult));
    }
}
