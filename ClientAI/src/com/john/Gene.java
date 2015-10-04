package com.john;

import java.util.Random;
/**
 * Gene Class for the Genetic Algorithm used for Learning
 */
public class Gene {

    float [][] gene;
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
}
