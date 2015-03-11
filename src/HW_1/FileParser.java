package HW_1;
//********************************************************************************************
//Name   : Aniket S Gaikwad
//Date   : 30-Jan-2014
//Details: Conversion of .DAT file to .CSV 
//********************************************************************************************

import java.util.*;
import java.io.*;

public class FileParser {

	public static void main(String args[]){
		String fileName="E:\\Spring 2015\\B565-Data Mining\\HW_1\\MovieLens\\ml-1m\\ml-1m\\test.dat";
		String newFile1="E:\\Spring 2015\\B565-Data Mining\\HW_1\\MovieLens\\ml-1m\\ml-1m\\Database\\test.csv";
		String newFile2="E:\\Spring 2015\\B565-Data Mining\\HW_1\\MovieLens\\ml-1m\\ml-1m\\Database\\test2.csv";
		System.out.println(fileName);
		String line=null;
		StringTokenizer token;
		int count=0;
		
		try{
			FileReader reader=new FileReader(fileName);                // read file
			BufferedReader buf=new BufferedReader(reader);            // Write to new file
			FileWriter writer=new FileWriter(newFile1);
			BufferedWriter buf1=new BufferedWriter(writer);
			while((line=buf.readLine())!=null){
				count++;
				if(count>600000)
				{
					buf1.close();
					writer=new FileWriter(newFile2);
					buf1=new BufferedWriter(writer);
					count=0;
					
				}
				System.out.println("\n"+line);
				String[] result=line.split("::");                     // Split file into token separated by ::
				
				for(int i=0;i<result.length;i++){
					
					token=new StringTokenizer(result[i],",");        // For each separated word further tokenize
					String newToken="";                              // on ',' and remove ',' as we want to make
					while(token.hasMoreTokens()){                    // CSV file and having comma in data will                    
						String nextToken=token.nextToken();          // be a problem.
						newToken=newToken.concat(nextToken);
					}
					
					System.out.println("\n New Token : "+newToken);
					System.out.print("\""+newToken+"\"");
					buf1.write("\""+newToken+"\"");
					
					if(i<result.length-1){                         // Add comma to each token exept last token
						System.out.print(",");
						buf1.write(",");
					}
				}
				buf1.newLine();                                     // Add new line to new file
			}
			buf.close();
			buf1.close();
		}
		catch(FileNotFoundException e){
			System.out.println("File Not Found : "+fileName);
		}
		catch(IOException e){
			System.out.println("Error in reading file : ");
			e.printStackTrace();
		}
	}
	
}
