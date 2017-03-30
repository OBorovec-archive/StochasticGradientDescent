package com.ondrej.sgd;

import com.sun.javafx.geom.Vec2f;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Ondrej on 23.6.2016.
 *
 * Class for parsing of input file with points and their classifications
 */
public class Dataset extends LinkedList<Dataset.SGDPoint>{

    /*
    * Loads input files from resources folder and store them
    *
    * @throws DatasetException - input files were not able to be opened or readable 
    */
    public Dataset() throws DatasetException{
        BufferedReader brData = null;
        BufferedReader brClass = null;
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            String dataLine = null;
            String classLine = null;
            brData = new BufferedReader(new FileReader(new File(classLoader.getResource("linsep-traindata.csv").getFile())));
            brClass = new BufferedReader(new FileReader(new File(classLoader.getResource("linsep-trainclass.csv").getFile())));

            while ((dataLine = brData.readLine()) != null && (classLine = brClass.readLine()) != null) {
                String[] parsedDataLine = dataLine.split(",");
                Float x = null;
                Float y = null;
                Integer value = null;
                try{
                    x = Float.parseFloat(parsedDataLine[0]);
                    y = Float.parseFloat(parsedDataLine[1]);
                    value = Integer.parseInt(classLine);
                    this.add(new SGDPoint(x, y, value));
                } catch (Exception e){
                    //TODO use apache logger
                    System.out.println("Unparseble data: " + dataLine + " or class: " + classLine);
                }
            }

        } catch (IOException e) {
            throw new DatasetException("File opening problem.");
        } finally {
            try {
                if (brData != null)brData.close();
                if (brClass != null)brClass.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /*
     * Static class for storing of point information as position a classification
     */
    public static class SGDPoint{
        public Vec2f pos;
        public int classVal;

        public SGDPoint(float x, float y, int classVal) {
            this.pos = new Vec2f(x, y);
            this.classVal = classVal;
        }

        @Override public String toString() {
            StringBuilder result = new StringBuilder();
            result.append("SGDPoint - x: " + pos.x + ", y: " + pos.y + ", class: " + classVal);
            return result.toString();
        }
    }

    public static class DatasetException extends Exception{
        public  DatasetException(String message){
            super(message);
        }
    }
}
