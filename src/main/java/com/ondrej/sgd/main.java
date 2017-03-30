package com.ondrej.sgd;

/**
 * Created by Ondrej on 23.6.2016.
 */
public class main {

    public static void main(String[] args){
        try {
            Dataset dataset = new Dataset();
            SGDAlg sgdAlg = new SGDAlg();
            sgdAlg.setDataset(dataset);
            Visualize2D visualize2D = new Visualize2D();
            visualize2D.view(dataset, sgdAlg.getLineSep());

        } catch (Dataset.DatasetException e) {
            e.printStackTrace();
        }
    }
}
