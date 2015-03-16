package HW_3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SplitFileIntoThreeParts {
	
	public ArrayList<String> outputFileList=new ArrayList<String>();
	public void splitFiles(String fileName,int numberOfFoldsValidation){
		char[] trainFileCharArray=fileName.toCharArray();
		//System.out.println(" trainFileCharArray.length "+trainFileCharArray.length);
		int i=trainFileCharArray.length-1;
		int count=0;
		String temp = "";
		
		
		while(i>=0){
			if(trainFileCharArray[i]=='\\'){
				break;
			}
			temp=trainFileCharArray[i]+temp;
			i--;
			count++;
		}
		
		//System.out.println(" temp : "+temp);
		
		int j=0;
		String rest="";
		while(j<trainFileCharArray.length-count){
			rest=rest+trainFileCharArray[j];
			j++;
		}
		//System.out.println(" Rest of the String : "+rest);
		
		/*
		 * Generating n files names for output files.
		 * */
		int outFileNumber=1;
		while(outFileNumber<=numberOfFoldsValidation){
			String outFileName=rest+"table"+outFileNumber+".txt";
			//System.out.println("New File : "+outFileName);
			outputFileList.add(outFileName);
			outFileNumber++;
		}
		
		String header = null;
		BufferedReader buf = null;
		try {
			FileReader reader = new FileReader(fileName);
			buf=new BufferedReader(reader);
			header = buf.readLine();
		} catch (IOException e1) {
			System.out.println("IO Exception in opening Input file for output files.");
			e1.printStackTrace();
		}
		
		int flag=1;
		String line=null;
		
		
		 
		try{
			for(String file :outputFileList){
				/*
				 * Writting Header to all output files
				 * */
				FileWriter writer=null;
				BufferedWriter buf1 = null;			
				writer = new FileWriter(file.toString());
				buf1=new BufferedWriter(writer);
				//System.out.println(" File : "+file);
				//System.out.println(" Header : "+header);
				buf1.write(header);
				buf1.close();
			}
			
		}
		catch (IOException e) {
				e.printStackTrace();
		}
		
		
		int firstOccurance=1;
		try {
			while((line=buf.readLine())!=null){
				/*
				 * Reading rest of the input file and splitting data into n files.
				 * */
				String file=outputFileList.get(flag-1);
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
			    //line=line+"\n";
				if(firstOccurance<=numberOfFoldsValidation){
					out.println();
					firstOccurance++;
				}
				out.println(line);
			    out.close();
				flag++;
				if(flag>numberOfFoldsValidation){
					flag=1;
				}
			}
		} catch (IOException e1) {
			System.out.println("IO Exception in opening Input file for output files.");
			e1.printStackTrace();
		}
			
		
		
	}
}
