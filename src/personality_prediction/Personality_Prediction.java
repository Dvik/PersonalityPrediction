/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package personality_prediction;

import java.util.Scanner;

/**
 *
 * @author somya
 */
public class Personality_Prediction {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Liwcdict dict=new Liwcdict();
        Classifier classifier=new Classifier();
        Evaluation_Result eval=new Evaluation_Result();
        System.out.println("Enter your choice");
        Scanner sc=new Scanner(System.in);
        System.out.println("1..for extracting features");
        System.out.println("2..Classify using J48");
        System.out.println("3..Evaluating results");
        int choice=sc.nextInt();
        if(choice==1){
            dict.extract_features();
        }
        else if(choice==2){
            classifier.run_classifier();
        }
        else if( choice==3){
            eval.eval_result();
        }
    }
    
}
