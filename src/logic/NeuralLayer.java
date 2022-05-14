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

    public NeuralLayer(int neuronCount, int previousNeuronCount, double[][] weights) {

        this.previousNeuronCount = previousNeuronCount;
        this.neuronCount = neuronCount;

        this.weights = weights;

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
}
