package HW_3;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import HW_3.ID3tree;
import HW_3.NFoldCrossValidation;
import HW_3.SplitFileIntoThreeParts;
import HW_3.TraverseTree;

public class CrossValidation {

	public static void main(String args[]){
		SplitFileIntoThreeParts sp1=new SplitFileIntoThreeParts();
		NFoldCrossValidation nFold=new NFoldCrossValidation();
		ID3tree newID3=new ID3tree();
		
		String databaseName=args[2];
		String server=args[3];
		String passWord=args[4];
		String userName=args[5];
		
		// Getting run time arguments
		String treeFile=args[0];
		int noOfCrossValidationFold=Integer.parseInt(args[1]);
		
		// Generate the TestFile Path
				char[] trainFileCharArray=treeFile.toCharArray();
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
				
				int j=0;
				String rest="";
				while(j<trainFileCharArray.length-count){
					rest=rest+trainFileCharArray[j];
					j++;
				}
				
				String testFilePath=rest+"\\CrossValtest.txt";
		
		// Generate Input Data File Path		
		FileReader reader;
		BufferedReader buf;
		String file = null;
		try {
			reader = new FileReader(treeFile);
			buf=new BufferedReader(reader);
			file=rest+buf.readLine();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		System.out.println("FILE : "+file);
		
		// Splitting file in 3 parts
		sp1.splitFiles(file, noOfCrossValidationFold);
		
		// Starting Execution of n-Cross fold validation
		int testFileNumber=0;
		while(testFileNumber<sp1.outputFileList.size()){
			System.out.println("\n ------------ CROSS FOLD RUNNING -------------- : "+testFileNumber);
			String testFile=sp1.outputFileList.get(testFileNumber);
			boolean flag=true;
			System.out.println();
			for(String inputFilePath:sp1.outputFileList){
				// For one combination of cross validation populate data in MySql
				if(inputFilePath.equals(testFile)){
					nFold.insertDataToMySql(inputFilePath,"TestData",true);
				}
				else{
					nFold.insertDataToMySql(inputFilePath,"TrainData",flag);
					flag=false;
				}
			}
			System.out.println("\n **** Execute ID3 Starts *****");
			newID3.ExecuteID3(treeFile,databaseName,server,passWord,userName);
			//System.out.println("\n **** nFold.generateTestFile Starts *****");
			nFold.generateTestFile(newID3.d1.attributes, testFilePath);
			//System.out.println("\n **** traversal.getProbabilityOftupleInTestFile Starts *****");
			//System.out.println("\n Step 4 : ");
			
			// Generating the Train Attribute order
			int temp1=0;
			int index=0;
			ArrayList<String> inputTrainAttribute=new ArrayList<String>();
			while(temp1<newID3.d1.attributes.length()){
				String data=newID3.d1.attributes.charAt(temp1)+"";
				if(data.equals(",")){
					temp1++;
					continue;
				}
				else{
					inputTrainAttribute.add(data);
					temp1++;
				}
			}
			
			// Tree traversal and probability generation
			System.out.println("\n Step 5 : ");
			TraverseTree traversal=new TraverseTree();
			traversal.getProbabilityOftupleInTestFile(testFilePath,newID3.root,inputTrainAttribute,true);

			testFileNumber++;
		}
		
	}
}
