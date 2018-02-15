import java.lang.Object.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.io.*;
import java.util.zip.*;
import java.util.Date;
import java.util.Calendar;
import java.time.format.DateTimeFormatter;
import java. text.*;
import javax.print.*;
import javax.swing.*;

/**
*@author Alex Kramer
*@version 1.0
*/
//ISTE-121
//HW8 

//Test Dups class is main class where files are added to an array list if they are duplicates
public class TestDups{

   /**
   *attributes
   */
   private ArrayList<Compare> masterList = new ArrayList<Compare>();
  // private ArrayList<HashMap<Long,File>> sortedValues = new ArrayList<HashMap<Long,File>>();
   private ArrayList<Compare> duplicates = new ArrayList<Compare>();
   private final int BF_SIZE = 8192;
   private File [] listOfFiles;
   private String path;
   
   public static void main(String [] args){
      new TestDups();
     
   
   } //end of main
   
   
   public TestDups(){
     
      /**
      *Get an array of list of files from a given directory.
      *Loops through the array of files and if it is a file and not a directory it is
      *passed in as a parameter to the Compare object which gets added to an arraylist
      *
      */
       JOptionPane jp = new JOptionPane();
       path =jp.showInputDialog(this, "Please enter relative path");
      File folder = new File(path);
      listOfFiles = folder.listFiles();
        
      for(int i=0; i <listOfFiles.length;i++){
         if(listOfFiles[i].isFile()){
            Compare co = new Compare(listOfFiles[i]); 
            masterList.add(co);   
           
            
         }
         else{
            continue;
         }
      }
      
      /**
      *Once the Compare Objects have been added to the arrarylist the array list is sorted
      */
      Collections.sort(masterList);
      
      /**
      *Loop through the arraylist and pass each item in the arraylist to the comparTo method
      If they are duplicates they are added to the duplicates arraylist.
      */
         
      for(int i =0; i <masterList.size() -1; i++) {
         if(masterList.get(i).compareTo(masterList.get(i+1)) == 0){
           //  if(masterList.get(i).compareTo(duplicates.get(duplicates.size() -1)) != 0){
         //                duplicates.add(masterList.get(i));
                      
            duplicates.add(masterList.get(i));
            duplicates.add(masterList.get(i+1));
         
         }
         else {
            continue;
         }
      
      }
      
      /**
      *Print out the duplicate files information
      */
      for(int i =0; i< duplicates.size()-1; i++){
         if(duplicates.get(i).getCrc() == duplicates.get(i +1).getCrc() && duplicates.get(i).getLength() == duplicates.get(i +1).getLength()){
            System.out.println("crc: " + duplicates.get(i).getCrc() + " length: " + duplicates.get(i).getLength() + " Last Date Modified: " + duplicates.get(i).getDate() + " File: " + duplicates.get(i).getName());
         
            System.out.println("crc: " + duplicates.get(i+1).getCrc() + " length: " + duplicates.get(i+1).getLength() + " Last Date Modified: " + duplicates.get(i+1).getDate() + " File: " + duplicates.get(i+1).getName());
            System.out.println();
         
         
         }
      
      }
   }
       
         
 /**
 *@author Alex Kramer
 *@version 1.0
 */
 //class compares the crc and length of two files
   class Compare implements Comparable<Compare>{
      File homeFile;
      
      /**
      *@author Alex Kramer
      *@version 1.0
      *@param File to be compared 
      **/
      public Compare(File aFile){
         homeFile = aFile;
      
      
      }
      
       /**
      *@author Alex Kramer
      *@version 1.0
      *@return long file length
      */
      public long getLength(){
         return homeFile.length();
        
      }
      
         /**
      *@author Alex Kramer
      *@version 1.0
      *@return String date last modified
      */
      public String getDate(){
      
         long yourmilliseconds = homeFile.lastModified();
      
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS",Locale.US);
      
         GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("US/Central"));
         calendar.setTimeInMillis(yourmilliseconds);
         return sdf.format(calendar.getTime());
      }
      
         /**
      *@author Alex Kramer
      *@version 1.0
      *@return String file name
      */
      public String getName(){
         return homeFile.getAbsolutePath();
      }
      
         /**
      *@author Alex Kramer
      *@version 1.0
      *@return long file crc
      */
      public long getCrc()
      {
         byte [] buffer = new byte[BF_SIZE];
         CRC32 crc = new CRC32();
         int len = 0;
         long crcValue = -1;
      
         crc.reset();
         try
         {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(homeFile));
         
            while ((len = bis.read(buffer)) > -1) 
            {
               crc.update(buffer,0,len);
            }
         
            crcValue = crc.getValue();
            bis.close();
         }
         catch ( Exception e )
         {
            e.printStackTrace();
         }
      
         return crcValue;
      
      } 
      
         /**
      *@author Alex Kramer
      *@version 1.0
      *@return int determines duplicate
      */
      public int compareTo(Compare other){
         if(this.getLength() > other.getLength()){
            return 1;
         
         }
         else if(this.getLength() == other.getLength()){
            if(this.getCrc() > other.getCrc()){
               return 1;
            }
               
            else if(this.getCrc() == other.getCrc()){
               return 0;
            }
            else {
               return -1;
            }
              
         }
         else{
            return -1;
         }
         
               
      
      }
      
         /**
      *@author Alex Kramer
      *@version 1.0
      */
      public void Check() { 
         for(int i =0; i <masterList.size(); i++) {
            if(masterList.get(i).compareTo(masterList.get(i+1)) == 0){
               if(masterList.get(i).compareTo(duplicates.get(duplicates.size() -1)) != 0){
                  duplicates.add(masterList.get(i));
               }
               
            
            
            }
         
         
         }
      
      }
         
      
   }
}
    
   
   

 
     
   
   

 // end of main class