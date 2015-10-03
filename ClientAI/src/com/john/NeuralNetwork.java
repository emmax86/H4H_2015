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

    void forwardPropgate(){

    }

    /*
    Matrix Multiplication for Forward Propagation
     */
    float[][] matrixMultiply(float[][] M1,int x1, int x2, float[][] M2, int y1, int y2){
        float[][] M3 = new float[x1][y2];
        for(int i = 0; i < x1; i++){
            for(int j = 0; j < y2; j++){
                M3[i][j] += M1[i][j]*M2[j][i];
            }
        }
    }
}
