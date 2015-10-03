package com.john;

/**
 * Neural Network (Multilayer Perceptron) as the Client AI
 */
public class NeuralNetwork {

    float [] [] weight1;
    float [] [] weight2;

    /*
    Initialization of the NeuralNetwork
     */
    NeuralNetwork(int inputLayerDimensions, int hiddenLayerDimensions, int outputLayerDimensions, int numData){
        weight1 = new float[inputLayerDimensions][numData];
        weight2 = new float[1][numData];
    }
}
