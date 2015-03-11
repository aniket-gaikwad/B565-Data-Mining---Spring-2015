package HW_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

	public class CSVquoteRemoval {
		
		public static void main(String args[]){
			String fileName="E:\\Spring 2015\\B565-Data Mining\\HW_1\\movielens.csv";
			String newFile1="E:\\Spring 2015\\B565-Data Mining\\HW_1\\movielens2.csv";
			
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
					System.out.println("\n"+line);
					String[] result=line.split(",");                     // Split file into token separated by ::
					
					for(int i=0;i<result.length;i++){
						buf1.write(result[i].replace("\"", ""));
						
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
