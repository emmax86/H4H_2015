package com.john;

/**
 * Genetic Algorithm Class to act as a bridge between the Neural Network algorithm and the Gene Data Structure
 */
public class GeneticAlgorithm {

    float dError, tolerance;
    NeuralNetwork NN;
    Gene[] genes1;
    Gene[] genes2;

    GeneticAlgorithm(int numData, float[][] labeledData){
        //Initialization of Neural Network
        NN = new NeuralNetwork(4,5,1,numData,labeledData);
        //Initialization Genetic Population
        initializePopulation(15);
        //Start Loop
        while (dError < tolerance) {
            //Forward Propagate Neural Network
            NN.forwardPropgate();
            //If error < tolerance or doesn't change
            //Exit
            //Get Error and Retrieve top 5; clone all and mutate
            mutateGenes();
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
        genes1 = new Gene[popSize];
        genes2 = new Gene[popSize];
        for (int i = 0; i < popSize; i++){
            genes1[i] = new Gene(w1);
            genes2[i] = new Gene(w2);
        }
    }

    //Retrieve Top 5
    void retrieve(){
        sort(genes1);
        sort(genes2);
    }

    //Breed
    void breed(){
        retrieve();
    }

    void mutateGenes(){
        for (int i = 0; i < genes1.length; i++){
            genes1[i].mutateGene();
        }
        for (int i = 0; i < genes2.length; i++){
            genes2[i].mutateGene();
        }
    }
    //update Neural Network
    void updateNeuralNetwork(){

    }

    /*
    Sort Method (in terms of Fitness)
    NOTE-TO-SELF: IMPLEMENT O(nlogn) LATER
     */
    void sort(Gene[] g){
        for (int i = 1; i < g.length; i++){
            Gene g1 = g[i];
            int j = i - 1;
            while (i > 0 && g[i].getFitness() > g1.getFitness()){
                g[i + 1] = g[i];
                i = i - 1;
            }
            g[i + 1] = g1;
        }
    }
}
