package com.ondrej.sgd;

import com.sun.javafx.geom.Line2D;
import com.sun.javafx.geom.Vec2f;
import com.sun.javafx.geom.Vec3f;

/**
 * Created by Ondrej on 23.6.2016.
 *
 * Class for basic perceptron binary classifier for 2D points.
 * see: https://en.wikipedia.org/wiki/Perceptron
 */
public class SGDAlg {

    private Dataset dataset;
    private float rateOfLearning = 1f;

    /*
     * Return a init weight vector for perceptron algorithm.
     * !At this moment only simple without a deeper logic.
     *
     * @return init weight vector for 2D points
     */
    private Vec3f initVec(){
        return new Vec3f(1f, -1f, 0f);
    }

    /*
     * Calculates to which class of 1/-1 classifier of weights
      * in vector vec places input point
     *
     * @param vec vector of perceptron weights of length 3
     * @param point 2D point for classification
     * @return class 1 or class -1 to which is placed input point
     */
    private int countClass(Vec3f vec, Vec2f point){
        float result = vec.x * point.x + vec.y * point.y;
        return (result > 0 ? 1 : -1);
    }

    /*
     * Perceptron algorithm (https://en.wikipedia.org/wiki/Perceptron)
      * run on set data in private variable dataset
       * - before starting used procedure setDataset to fill this variable
       *
      *@return weight vector for final state of perceptron alg.
     */
    private Vec3f countVec(){
        Vec3f vec = initVec();
        boolean finished = false;
        while (!finished) {
            finished = true;
            for(Dataset.SGDPoint point : dataset){
                int assignedClass = countClass(vec, point.pos);
                if(point.classVal != assignedClass){
                    vec.x += point.classVal * rateOfLearning * point.pos.x;
                    vec.y += point.classVal * rateOfLearning * point.pos.y;
                    vec.z += point.classVal * rateOfLearning;
                    finished = false;
                    break;
                }
            }
            System.out.println(vec);
        }
        return vec;
    }



    public Vec3f getLineSep(){
        return countVec();
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public void setRateOfLearning(float rateOfLearning) {
        this.rateOfLearning = rateOfLearning;
    }
}
