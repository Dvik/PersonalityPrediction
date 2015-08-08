/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package personality_prediction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
        

/**
 *
 * @author somya
 */
public class Liwcdict {
    void extract_features(){
        File f=new File("C:\\Users\\divya\\Desktop\\Personality Mining\\LIWC2007.cat");
        LIWCDictionary dict=new LIWCDictionary(f);
        System.out.println("1.. for viewing dictionary");
        System.out.println("2..generating excel file");
        Scanner sc= new Scanner (System.in);
        int choice=sc.nextInt();
        if(choice==1){
            dict.Display_Dictionary_Map();
        }
        else 
        {
        Liwcdict obj=new Liwcdict();      
        Map<String, Double> counts;
       // String filename="C:\\Users\\somya\\Desktop\\Personality Mining\\WEKA_Dataset\\feature_values.xls" ;
        HSSFWorkbook hwb=new HSSFWorkbook();
        HSSFSheet sheet =  hwb.createSheet("new sheet");
        try
        {
            FileInputStream file = new FileInputStream(new File("C:\\Users\\divya\\Desktop\\Personality Mining\\mypersonality_final\\mypersonality_final.xls"));
            //Create Workbook instance holding reference to .xlsx file
            HSSFWorkbook workbook = new HSSFWorkbook(file);
 
            //Get first/desired sheet from the workbook
            HSSFSheet sheet_1 = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet_1.iterator();
            int row_count=0;
            while (rowIterator.hasNext()){
               //for(int i=0;i<10;i++){
                    Row row = rowIterator.next();
                    //For each row, iterate through all the columns
                    Iterator<Cell> cellIterator = row.cellIterator();
                    Cell cell = cellIterator.next();
                    cell=cellIterator.next();
                    String tweet=cell.getStringCellValue();
                    
                    counts=dict.getJustCounts(tweet, true);
                    //counts=dict.getCounts(tweet, true);
                    obj.export2excel(counts, row_count, tweet, sheet,hwb);
                    System.out.println(tweet);
                    //Check the cell type and format accordingly
                    //System.out.print(cell.getStringCellValue() + "t");
                    row_count++;
                }
            file.close();
        }
        
        catch (Exception e)
        {
            e.printStackTrace();
        }

       // LIWCDictionary.tokenize("g hjgjh hgjh");
        }
    }
    //void ee(){
        
    //}
    void fetch_tweet(){
     try
        {
            FileInputStream file = new FileInputStream(new File("C:\\Users\\somya\\Desktop\\Personality Mining\\mypersonality_final\\mypersonality_final.xls"));
            //Create Workbook instance holding reference to .xlsx file
            HSSFWorkbook workbook = new HSSFWorkbook(file);
 
            //Get first/desired sheet from the workbook
            HSSFSheet sheet = workbook.getSheetAt(0);Iterator<Row> rowIterator = sheet.iterator();
            int row_count=0;
            //while (rowIterator.hasNext())
            for(int i=0;i<10;i++)
            {
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                 
                    Cell cell = cellIterator.next();
                    cell=cellIterator.next();
                    String tweet=cell.getStringCellValue();
                    System.out.println(tweet);
                    //Check the cell type and format accordingly
                            //System.out.print(cell.getStringCellValue() + "t");
                    
                    row_count++;
                            
            file.close();
        }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    Map call_liwc( String tweet){
       Map m;
        File f=new File("C:\\Users\\somya\\Desktop\\Personality Mining\\LIWC2007.cat");
      LIWCDictionary  o =new LIWCDictionary(f);
       m=o.getJustCounts(tweet, true);
       return m;
                    
    }
    void export2excel(Map<String, Double> counts, int row_count,String tweet,HSSFSheet sheet,HSSFWorkbook hwb){
        String filename="C:\\Users\\somya\\Desktop\\Personality Mining\\Features_mypersonality_final.xls" ;
        //HSSFWorkbook hwb=new HSSFWorkbook();
        //HSSFSheet sheet =  hwb.createSheet("new sheet");
        Iterator<Map.Entry<String, Double>> entries = counts.entrySet().iterator();
        int column_count=0,top=0;
        HSSFRow row=   sheet.createRow((short)top);
        //row.createCell((short) column_count).setCellValue("TWEET");
        //column_count++;
        while (entries.hasNext()) {
            Map.Entry<String, Double> entry = entries.next();
            //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());        
            row.createCell((short) column_count).setCellValue(entry.getKey());
            column_count++;
        }
        //row_count++;
        Iterator<Map.Entry<String, Double>> entries2 = counts.entrySet().iterator();
        //int row_count=0,
        column_count=0;
        HSSFRow row2=   sheet.createRow((short)row_count);
       // row2.createCell((short) column_count).setCellValue(tweet);
        //column_count++;
        while (entries2.hasNext()) {
            Map.Entry<String, Double> entry = entries2.next();
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());    
            row2.createCell((short) column_count).setCellValue(entry.getValue());
            column_count++;
        }
        try{
            FileOutputStream fileOut =  new FileOutputStream(filename);
            hwb.write(fileOut);
            fileOut.close();
            System.out.println("Your excel file has been generated!");
        }
        catch ( Exception ex ) {
            System.out.println(ex);

        }
    }
}//end of class