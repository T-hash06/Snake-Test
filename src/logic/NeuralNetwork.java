package logic;

import java.util.Arrays;

public final class NeuralNetwork {

    private final int[] topology;
    private final NeuralLayer[] layers;

    public static NeuralNetwork[] createFromTwoNetworks(NeuralNetwork firstNetwork, NeuralNetwork secondNetwork, NeuralLayer.ReproductionMethods reproductionMethod) {

        if (firstNetwork.layers.length != secondNetwork.layers.length) {
            System.out.println("Neural Networks with different layers count!");
        }

        if (!Arrays.equals(firstNetwork.topology, secondNetwork.topology)) {
            System.out.println("Neural Networks with different topology!");
        }

        NeuralLayer[] firstLayers = new NeuralLayer[firstNetwork.layers.length];
        NeuralLayer[] secondLayers = new NeuralLayer[firstNetwork.layers.length];

        for (int layer = 0; layer < firstNetwork.layers.length; layer++) {
            if (layer == 0) {
                firstLayers[layer] = new NeuralLayer(firstNetwork.topology[layer]);
                secondLayers[layer] = new NeuralLayer(firstNetwork.topology[layer]);
                continue;
            }

            NeuralLayer[] helper = NeuralLayer.createTwoByWeightsArray(
                    firstNetwork.topology[layer],
                    secondNetwork.topology[layer - 1],
                    firstNetwork.layers[layer].getWeightsAsArray(),
                    secondNetwork.layers[layer].getWeightsAsArray(),
                    reproductionMethod);
            firstLayers[layer] = helper[0];
            secondLayers[layer] = helper[1];
        }

        return new NeuralNetwork[]{new NeuralNetwork(firstLayers), new NeuralNetwork(secondLayers)};
    }

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

    public NeuralNetwork(NeuralLayer[] layers) {
        this.topology = new int[layers.length];
        this.layers = layers.clone();

        for (int index = 0; index < this.topology.length; index++) {
            this.topology[index] = layers[index].getNeuronCount();
        }
    }

    public double[] calculate(double[] inputValues) {

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

    public int[] getTopology() {
        return topology;
    }

    public NeuralLayer[] getLayers() {
        return layers;
    }

    public void resetLayers() {
        for (int index = 0; index < topology.length; index++) {
            if (index == 0) {
                this.layers[index] = new NeuralLayer(topology[index]);
                continue;
            }

            this.layers[index] = new NeuralLayer(topology[index], topology[index - 1]);
        }
    }

    public void mutateLayers(double mutationRate) {
        for (int index = 0; index < this.topology.length; index++) {
            this.layers[index].mutateWeights(mutationRate);
        }
    }
}
