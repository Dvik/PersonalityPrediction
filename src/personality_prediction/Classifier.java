/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package personality_prediction;
 import weka.core.Instances;
 import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
 import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import weka.classifiers.trees.J48;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
/**
 *
 * @author somya
 */
public class Classifier{

    /**
     * @param args the command line arguments
     */
     void run_classifier() {
        // TODO code application logic here
        try{
            //csv2arff();
            System.out.println("Enter the class for which you want to classify");
            System.out.println("1..Openness");
            System.out.println("2..Neuroticism");
            System.out.println("3..Agreeableness");
            System.out.println("4..Conscientiousness");
            System.out.println("5..Extraversion");
            System.out.println();
            Scanner sc=new Scanner(System.in);
            int choice=sc.nextInt();
            String filename="";
           // BufferedReader reader=new BufferedReader(new FileReader(""));
            if(choice==1){
                filename="C:\\Users\\divya\\Desktop\\Personality Mining\\WEKA_DataSet\\Training dataset\\Training_data_open.arff";
                //reader = new BufferedReader(new FileReader("C:\\Users\\somya\\Desktop\\Personality Mining\\WEKA_DataSet\\Training dataset\\Training_data_open.arff"));
            }
            else if(choice==2){
                filename="C:\\Users\\divya\\Desktop\\Personality Mining\\WEKA_DataSet\\Training dataset\\Training_data_neur.arff";
                // reader = new BufferedReader(new FileReader("C:\\Users\\somya\\Desktop\\Personality Mining\\WEKA_DataSet\\Training dataset\\Training_data_neur.arff"));
            }
            else if(choice==3){
                filename="C:\\Users\\divya\\Desktop\\Personality Mining\\WEKA_DataSet\\Training dataset\\Training_data_agr.arff";
                // reader = new BufferedReader(new FileReader("C:\\Users\\somya\\Desktop\\Personality Mining\\WEKA_DataSet\\Training dataset\\Training_data_agr.arff"));
            }
            else if(choice==4){
                filename="C:\\Users\\divya\\Desktop\\Personality Mining\\WEKA_DataSet\\Training dataset\\Training_data_con.arff";
                // reader = new BufferedReader(new FileReader("C:\\Users\\somya\\Desktop\\Personality Mining\\WEKA_DataSet\\Training dataset\\Training_data_con.arff"));
            }
            else if(choice==5){
                filename="C:\\Users\\divya\\Desktop\\Personality Mining\\WEKA_DataSet\\Training dataset\\Training_data_extr.arff";
              // reader = new BufferedReader(new FileReader("C:\\Users\\somya\\Desktop\\Personality Mining\\WEKA_DataSet\\Training dataset\\Training_data_extr.arff"));  
            }
           //BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\somya\\Desktop\\Personality Mining\\WEKA_DataSet\\Training dataset\\"));
            // DataSource source = new DataSource("C:\\Users\\somya\\Desktop\\Personality Mining\\WEKA_Dataset\\Features_value.arff");
            //Instances data = source.getDataSet();
            BufferedReader reader=new BufferedReader(new FileReader(filename));
            Instances data = new Instances(reader);
            reader.close();
            //******88setting class attribute************
            data.setClassIndex(data.numAttributes() - 1);
       
      //  OptionsToCode option=new OptionsToCode();
       // String options[]={"java","ExperimentDemo","-classifier weka.classifiers.trees.M5P","-exptype regression","-splittype randomsplit","-runs 10",
   //"-percentage 66","-result /some/where/results.arff","-t bolts.arff","-t bodyfat.arff"};
       // String[] options={"weka.classifiers.functions.SMO"};
        //String[] options={"weka.classifiers.trees.M5P"};
        //option.convert(options);
        
        //*******************building a classifier*********************
        String[] options = new String[1];
        options[0] = "-U";            // unpruned tree
        J48 tree = new J48();         // new instance of tree
        tree.setOptions(options);     // set the options
        tree.buildClassifier(data);   // build classifier
         
        if(choice==1){   
            filename="C:\\Users\\divya\\Desktop\\Personality Mining\\WEKA_DataSet\\Labelling\\Testing_data_open.arff";
            //fr=new FileReader("C:\\Users\\somya\\Desktop\\Personality Mining\\WEKA_DataSet\\Labelling\\Testing_data_open.arff");
         }
        else if(choice==2){
            filename="C:\\Users\\divya\\Desktop\\Personality Mining\\WEKA_DataSet\\Labelling\\Testing_data_neur.arff";
            //fr=new FileReader("C:\\Users\\somya\\Desktop\\Personality Mining\\WEKA_DataSet\\Labelling\\Testing_data_neur.arff");       
        }
        else if(choice==3){
            filename="C:\\Users\\divya\\Desktop\\Personality Mining\\WEKA_DataSet\\Labelling\\Testing_data_agr.arff";
           // fr=new FileReader("C:\\Users\\somya\\Desktop\\Personality Mining\\WEKA_DataSet\\Labelling\\Testing_data_agr.arff");              
            }
        else if(choice==4){
            filename="C:\\Users\\divya\\Desktop\\Personality Mining\\WEKA_DataSet\\Labelling\\Testing_data_con.arff";
            //fr=new FileReader("C:\\Users\\somya\\Desktop\\Personality Mining\\WEKA_DataSet\\Labelling\\Testing_data_con.arff");                
            }
        else if(choice==5){
            filename="C:\\Users\\divya\\Desktop\\Personality Mining\\WEKA_DataSet\\Labelling\\Testing_data_extr.arff";
            //fr=new FileReader("C:\\Users\\somya\\Desktop\\Personality Mining\\WEKA_DataSet\\Labelling\\Testing_data_extr.arff");              
            }
       FileReader fr=new FileReader(filename);
        BufferedReader br=new BufferedReader(fr);
        Instances unlabeled = new Instances(br);
       /// Instances unlabeled = new Instances(
                       //  new BufferedReader(
                       //  new FileReader("C:\\Users\\somya\\Desktop\\Personality Mining\\WEKA_Dataset\\experiment\\test_data_unlabelled.arff")));
        // set class attribute
        unlabeled.setClassIndex(unlabeled.numAttributes() - 1);
        // create copy
        Instances labeled = new Instances(unlabeled);
        // label instances
        for (int i = 0; i < unlabeled.numInstances(); i++) {
            double clsLabel = tree.classifyInstance(unlabeled.instance(i));
            labeled.instance(i).setClassValue(clsLabel);
        }
        // save labeled data
        
        if(choice==1){   
            filename="C:\\Users\\divya\\Desktop\\Personality Mining\\WEKA_DataSet\\Labelling\\Testing_data_open_labelled.arff";
           // fr1=new FileWriter("C:\\Users\\somya\\Desktop\\Personality Mining\\WEKA_DataSet\\Labelling\\Testing_data_open123.arff");
         }
        else if(choice==2){
           // fr1=new FileWriter("C:\\Users\\somya\\Desktop\\Personality Mining\\WEKA_DataSet\\Labelling\\Testing_data_neur_labelled.arff");       
        }
        else if(choice==3){
           // fr1=new FileWriter("C:\\Users\\somya\\Desktop\\Personality Mining\\WEKA_DataSet\\Labelling\\Testing_data_agr_labelled.arff");              
            }
        else if(choice==4){
            //fr1=new FileWriter("C:\\Users\\somya\\Desktop\\Personality Mining\\WEKA_DataSet\\Labelling\\Testing_data_con_labelled.arff");                
            }
        else if(choice==5){
           // fr1=new FileWriter("C:\\Users\\somya\\Desktop\\Personality Mining\\WEKA_DataSet\\Labelling\\Testing_data_extr_labelled.arff");              
            }
        FileWriter fr1=new FileWriter(filename);
        BufferedWriter writer = new BufferedWriter(fr1);
        writer.write(labeled.toString());
        writer.newLine();
        writer.flush();
        writer.close();      
        }
        catch(Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }
     void csv2arff(){
         try{
            CSVLoader loader = new CSVLoader();
            loader.setSource(new File("C:\\Users\\divya\\Desktop\\Personality Mining\\WEKA_Dataset\\Features_value.csv"));
            Instances data = loader.getDataSet();
 
            // save ARFF
            File f= new File("C:\\Users\\divya\\Desktop\\Personality Mining\\WEKA_Dataset\\Features_value.arff");
            ArffSaver saver = new ArffSaver();
            saver.setInstances(data);
            saver.setFile(f);
            //saver.setDestination(f);
            saver.writeBatch();
         }
        catch(Exception e){
            System.out.println(e.getLocalizedMessage());
         }
     }
}//end of class
 
