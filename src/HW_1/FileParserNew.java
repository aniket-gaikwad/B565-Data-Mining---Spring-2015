package HW_1;
//********************************************************************************************
//Name   : Aniket S Gaikwad
//Date   : 30-Jan-2014
//Details: Conversion of .DAT file to .CSV 
//********************************************************************************************

import java.util.*;
import java.io.*;

public class FileParserNew {

	public static void main(String args[]){
		String fileName="E:\\Spring 2015\\B565-Data Mining\\HW_1\\MovieLens\\ml-1m\\ml-1m\\movies.csv";
		String newFile1="E:\\Spring 2015\\B565-Data Mining\\HW_1\\MovieLens\\ml-1m\\ml-1m\\Database\\movies_1.csv";
		//String newFile2="E:\\Spring 2015\\B565-Data Mining\\HW_2\\MovieLens\\ml-1m\\ml-1m\\Database\\ratings2.csv";
		System.out.println(fileName);
		String line=null;
		StringTokenizer token;
		int count=0,cnt=0;
		
		try{
			FileReader reader=new FileReader(fileName);                // read file
			BufferedReader buf=new BufferedReader(reader);            // Write to new file
			FileWriter writer=new FileWriter(newFile1);
			BufferedWriter buf1=new BufferedWriter(writer);
			while((line=buf.readLine())!=null){
				 
				System.out.println("\n"+line);
				String[] result=line.split(",");                     // Split file into token separated by ,
				
				for(int i=0;i<result.length;i++){
					token=new StringTokenizer(result[i],"|");        // For each separated word further tokenize
					String newToken="";                              // on ',' and remove ',' as we want to make
					while(token.hasMoreTokens()){                    							// CSV file and having comma in data will                    
						if(cnt>0){
							System.out.print(",");
							buf1.write(",");
						}		
						String nextToken=token.nextToken();
						System.out.println("\n Next Token : "+nextToken);
						buf1.write("\""+nextToken+"\"");
						cnt++;
						}
						//newToken=newToken.concat(nextToken);
					cnt=0;
					if(i+1<result.length){
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
