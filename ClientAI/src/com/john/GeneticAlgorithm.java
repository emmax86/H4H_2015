package com.john;

/**
 * Genetic Algorithm Class to act as a bridge between the Neural Network algorithm and the Gene Data Structure
 */
public class GeneticAlgorithm {

    float dError, tolerance;
    NeuralNetwork NN;

    GeneticAlgorithm(int numData, float[][] labeledData){
        //Initialization of Neural Network
        NN = new NeuralNetwork(4,5,1,numData,labeledData);
        //Start Loop
        while (dError < tolerance) {
            //Forward Propagate Neural Network
            NN.forwardPropgate();
            //If error < tolerance or doesn't change
            //Exit
            //Get Error and Retrieve top 5; clone all
        }
    }

    /*
    Initialize Starting Population for Genetic Algorithm to commence Artificial Evolution
     */
    void initializePopulation(int popSize){
        float[][][] weights = NN.retrieveWeights();
        float[][] w1, w2;
        w1 = weights[1].clone();
        w2 = weights[2].clone();
        Gene[] genes1 = new Gene[popSize];
        Gene[] genes2 = new Gene[popSize];
        for (int i = 0; i < popSize; i++){
            genes1[i] = new Gene(w1);
            genes2[i] = new Gene(w2);
        }
    }

    //Retrieve Top 5
    void retrieve(){

    }

    //Breed
    void breed(){

    }

    //Test
    void testNeuralNetwork(){

    }

    //End
}
