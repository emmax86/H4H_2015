package com.john;

/**
 * Gene Class for the Genetic Algorithm used for Learning
 */
public class Gene {

    float [][] gene;

    Gene(int rows, int cols){
        gene = new float[rows][cols];
        for( int i = 0; i < rows; i++){
            for ( int j = 0; j < cols; j++) {
                gene[i][j] = 0.0f;
            }
        }
    }

    /*
    Return Length of Gene
     */
    int getSize(){
        return gene.length;
    }

    /*
    Copy Weight Matrix to Gene Matrix
     */
    void copyWeightMatrix(float [][] weight){
        gene = weight;
    }
}
