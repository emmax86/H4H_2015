package com.john;

/**
 * Neural Network (Multilayer Perceptron) as the Client AI
 */
public class NeuralNetwork {

    float [] [] data;
    float [] [] weight1;

    /*
    Initialization of the NeuralNetwork
     */
    NeuralNetwork(int inputLayerDimensions, int hiddenLayerDimensions, int outputLayerDimensions, int numData){
        data = new float[inputLayerDimensions][numData];
        weight1 = new float[1][numData];
    }

    void forwardPropgate(){
        float[][] data = new float[10][10];
        float[][] something2 = new float[10][10];
        //float z2 = matrixMultiply(data, );
    }

    /*
    Matrix Multiplication for Forward Propagation
     */
    float[][] matrixMultiply(float[][] M1,int m1Row, int m1Col, float[][] M2, int m2Col) {
        float[][] M3 = new float[2][2];
        for (int i = 0; i < m1Row; i++) { //M1 Rows
            for (int j = 0; j < m2Col; j++) { // M2 Columns
                for(int k = 0; k < m1Col; k++) { // M3 Columns
                    M3[i][j] += M1[i][k] * M2[k][j];
                }
            }
        }
        return M3;
    }

    /*
    Apply activation function to each elements of a matrix
     */
    float[][] applyActivationFuction(float[][] M, int x, int y){
        for(int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++){
                M[i][j] = activationFunction(M[i][j]);
            }
        }
        return M;
    }

    /*
    Given a float value applied with the logistic function, return that new value
     */
    float activationFunction(float value){
        return (float)(1/(1 + Math.exp(-value)));
    }

    /*
    Print Matrix
     */
    void printMatrix(float [] [] M, int x, int y){
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                System.out.print(M[i][j]);
            }
            System.out.println();
        }
    }
}
