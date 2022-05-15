package logic;

import java.util.Arrays;
import java.util.function.Function;

public final class NeuralLayer {

    public static final double DEFAULT_BIAS = 0;

    private final int neuronCount;
    private final int previousNeuronCount;

    private final double[][] weights;
    private final double[] bias;

    //TODO: Implement more functions in another class
    private final Function<Double, Double> activationFunction = x -> ((1 - Math.exp(-2 * x)) / (1 + Math.exp(-2 * x)));

    public static NeuralLayer createByAverageWeightsArray(int neuronCount, int previousNeuronCount, double[] firstWeights, double[] secondWeights) {

        if (firstWeights.length != secondWeights.length) {
            System.out.println("Inconsistent weights lenghts");
        }

        double[] result = new double[firstWeights.length];
        for (int index = 0; index < firstWeights.length; index++) {
            result[index] = (firstWeights[index] + secondWeights[index]) / 2;
        }

        return new NeuralLayer(neuronCount, previousNeuronCount, result);
    }

    public NeuralLayer(int neuronCount, int previousNeuronCount, double[][] weights) {

        this.previousNeuronCount = previousNeuronCount;
        this.neuronCount = neuronCount;

        this.weights = weights.clone();

        this.bias = new double[neuronCount];
        Arrays.fill(this.bias, DEFAULT_BIAS);
    }

    public NeuralLayer(int neuronCount, int previousNeuronCount, double[] weightsAsArray) {
        this.previousNeuronCount = previousNeuronCount;
        this.neuronCount = neuronCount;

        this.weights = new double[previousNeuronCount][neuronCount];

        for (int current = 0; current < neuronCount; current++) {
            for (int previous = 0; previous < previousNeuronCount; previous++) {
                this.weights[previous][current] = weightsAsArray[current + (previous * neuronCount)];
            }
        }

        this.bias = new double[neuronCount];
        Arrays.fill(this.bias, DEFAULT_BIAS);
    }

    public NeuralLayer(int neuronCount, int previousNeuronCount) {
        this.neuronCount = neuronCount;
        this.previousNeuronCount = previousNeuronCount;

        this.weights = new double[previousNeuronCount][neuronCount];

        for (int current = 0; current < this.neuronCount; current++) {
            for (int previous = 0; previous < this.weights.length; previous++) {
                this.weights[previous][current] = (Math.random() * 2) - 1;
            }
        }

        this.bias = new double[neuronCount];
        Arrays.fill(this.bias, DEFAULT_BIAS);
    }

    public NeuralLayer(int neuronCount) {
        this.neuronCount = neuronCount;
        this.previousNeuronCount = 0;

        this.weights = null;

        this.bias = new double[neuronCount];
        Arrays.fill(this.bias, DEFAULT_BIAS);
    }

    public double[] updateValues(double[] previousNeuronValues) {
        double[] values = new double[this.neuronCount];

        if (this.weights == null) {
            System.out.println("Updating heights in input layer!");
            return null;
        }

        if (previousNeuronValues.length != this.weights.length) {
            System.out.println("Inconsistent weights and previous neuron count!");
        }

        double helper = 0;
        for (int current = 0; current < this.neuronCount; current++) {
            for (int previous = 0; previous < this.weights.length; previous++) {
                helper += (this.weights[previous][current] * previousNeuronValues[previous]) - this.bias[current];
            }

            values[current] = this.activationFunction.apply(helper);
            helper = 0;
        }

        return values;
    }

    public double[][] getWeights() {
        return weights;
    }

    public double[] getWeightsAsArray() {
        double[] result = new double[this.previousNeuronCount * this.neuronCount];

        int index = 0;
        for (double[] row : this.weights) {
            for (double weight : row) {
                result[index] = weight;
                index++;
            }
        }

        return result;
    }

    public int getNeuronCount() {
        return neuronCount;
    }
}
