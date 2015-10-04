package com.john;

/**
 * Neural Network (Multilayer Perceptron) as the Client AI
 */
public class NeuralNetwork {

    float [] [] data;
    float [] [] weight1;
    float [] [] z2;
    float [] [] weight2;
    float [] [] yHat;
    int inputLayerDimensions;
    int hiddenLayerDimensions;
    int outputLayerDimensions;
    int numdata;

    /*
    Initialization of the NeuralNetwork
     */
    NeuralNetwork(int inputLayerDimensions, int hiddenLayerDimensions, int outputLayerDimensions, int numData){
        data = new float[numData][inputLayerDimensions];
        weight1 = new float[inputLayerDimensions][hiddenLayerDimensions];
        weight2 = new float[hiddenLayerDimensions][outputLayerDimensions];
        this.inputLayerDimensions = inputLayerDimensions;
        this.hiddenLayerDimensions = hiddenLayerDimensions;
        this.outputLayerDimensions = outputLayerDimensions;
        this.numdata = numData;
    }

    /*
    Applied forward propagation for the input to get output
     */
    void forwardPropgate(){
        float[][] z2 = applyActivationFuction(matrixMultiply(data, this.numdata, this.inputLayerDimensions, weight1, this.hiddenLayerDimensions), this.numdata, this.hiddenLayerDimensions);
        yHat = applyActivationFuction(matrixMultiply(z2,this.inputLayerDimensions,this.hiddenLayerDimensions,weight1,this.outputLayerDimensions ),this.inputLayerDimensions,this.outputLayerDimensions );

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
    Print Error (y - yHate) a.k.a (True Value - Predicted Value)
     */
    float[][] retrieveError(){
        for(int i = 0; i < numdata; i++){
            for(int j = 0; j < outputLayerDimensions; j++){
                System.out.println(yHat[i][j]);
            }
        }
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
