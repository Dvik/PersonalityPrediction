/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package personality_prediction;
import weka.core.Instances;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.converters.ConverterUtils.DataSource;
/**
 *
 * @author somya
 */
public class Evaluation_Result {
    void eval_result() {
       try{
            DataSource source_train = new DataSource("C:\\Users\\divya\\Desktop\\Personality Mining\\WEKA_DataSet\\Training dataset\\training_data_neur.csv");
            Instances train = source_train.getDataSet();
            DataSource source_test = new DataSource("C:\\Users\\divya\\Desktop\\Personality Mining\\WEKA_DataSet\\Testing dataset\\Testing_data_neur.csv");
            Instances test = source_test.getDataSet();
            train.setClassIndex(train.numAttributes() - 1);
            test.setClassIndex(train.numAttributes() - 1);
            // train classifier
            Classifier cls = new J48();
            cls.buildClassifier(train);
            Evaluation eval = new Evaluation(train);
            eval.evaluateModel(cls, test);
            System.out.println(eval.toSummaryString("\nResults\n======\n", false));
            
           
            
       }
       catch(Exception e){
           System.out.println(e.getLocalizedMessage());
       }
   }
           
}
