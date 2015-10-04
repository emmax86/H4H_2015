package com.john;

import java.util.Random;
/**
 * Gene Class for the Genetic Algorithm used for Learning
 */
public class Gene {

    float [][] gene;
    float fitness;
    Random r = new Random();

    Gene(float[][] copy){
        copyWeightMatrix(copy);
    }

    /*
    Copy Weight Matrix to Gene Matrix
     */
    void copyWeightMatrix(float [][] weight){
        gene = weight;
    }

    /*
    Return Gene
     */
    float[][] returnGene(){
        return gene;
    }

    /*
    Mutate the Genes (Randomly Change the values inside the Gene)
     */
    void mutateGene(){
        int numberOfMutates = r.nextInt();
        for (int i = 0; i < numberOfMutates; i++){
            int x = r.nextInt(gene.length);
            int y = r.nextInt(gene[0].length);
            float value = r.nextFloat();
            gene[x][y] = value;
        }
    }

    /*
    Set Fitness Level
    IN THIS CASE, LOWER ERROR IS HIGHER FITNESS; THUS FOR EASE LOWER FITNESS IS OF HIGHER PRECEDENCE IN ARTIFICIAL SELECTION
     */
    void setFitnessLevel(float fitness){
        this.fitness = fitness;
    }

    /*
    Retrieve Fitness Level
     */

    float getFitness(){
        return fitness;
    }


}
