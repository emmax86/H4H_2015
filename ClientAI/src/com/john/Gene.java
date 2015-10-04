package com.john;

/**
 * Gene Class for the Genetic Algorithm used for Learning
 */
public class Gene {

    float [] gene;

    Gene(int size){
        gene = new float[size];
        for( int i = 0; i < size; i++){
            gene[i] = 0.0f;
        }
    }
    
    /*
    Return Length of Gene
     */
    int getSize(){
        return gene.length;
    }

    /*
    Copy Weight Matrix (2-Dimensional) to
    Gene Data Structure (1-Dimensional Array)
     */
    void copyWeightMatrix(float [][] weight, int rows, int cols){
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                gene[(i*cols)+j] = weight[i][j];
            }
        }
    }
}
