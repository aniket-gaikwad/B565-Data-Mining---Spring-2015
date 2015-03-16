package Testing;

import HW_3.ID3tree;
import HW_3.NFoldCrossValidation;
import HW_3.SplitFileIntoThreeParts;
import HW_3.TraverseTree;

public class testFiles {

	public static void main(String args[]){
		SplitFileIntoThreeParts sp1=new SplitFileIntoThreeParts();
		NFoldCrossValidation nFold=new NFoldCrossValidation();
		ID3tree newID3=new ID3tree();
		
		String file="E:\\Spring 2015\\B565-Data Mining\\DataMining\\DataMining\\src\\HW_3\\SourceFiles\\table.txt";
		String treeFile="E:\\Spring 2015\\B565-Data Mining\\DataMining\\DataMining\\src\\HW_3\\SourceFiles\\tree.txt";
		String testFilePath="E:\\Spring 2015\\B565-Data Mining\\DataMining\\DataMining\\src\\HW_3\\SourceFiles\\test.txt";
		
		sp1.splitFiles(file, 3);
		
		int testFileNumber=0;
		while(testFileNumber<sp1.outputFileList.size()){
			System.out.println("\n ------------ FOLD RUNNING --------------"+testFileNumber);
			String testFile=sp1.outputFileList.get(testFileNumber);
		//String testFile=sp1.outputFileList.get(0);
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
			//newID3.ExecuteID3(treeFile);
			System.out.println("\n **** nFold.generateTestFile Starts *****");
			nFold.generateTestFile(newID3.d1.attributes, testFilePath);
			System.out.println("\n **** traversal.getProbabilityOftupleInTestFile Starts *****");
			//System.out.println("\n Step 4 : ");
			TraverseTree traversal=new TraverseTree();
			//traversal.getProbabilityOftupleInTestFile(testFilePath,newID3.root);
			testFileNumber++;
		}
		
	}
}
