package Testing;

import java.sql.SQLException;
import java.util.ArrayList;

import HW_3.DecisionTree;
import HW_3.TraverseTree;
import HW_3.TreeNode;

public class TestDecisionTree {
	
	public static void main(String args[]){
		DecisionTree d1=new DecisionTree();
		String trainFile="E:\\Spring 2015\\B565-Data Mining\\DataMining\\DataMining\\src\\HW_3\\SourceFiles\\tree.txt";
		
		if(!d1.getInputData(trainFile)){
			return;
		}
		System.out.println("File Name : "+d1.inputFile);
		System.out.println("Class Label : "+d1.classLabelAttribute);
		System.out.println("Attributes : "+d1.attributes);
		d1.insertDataToMySql(trainFile);
		try{
		d1.populateTrainData();
		}
		catch(Exception e){
			System.out.println("Exception in makeConection () : " );
			e.printStackTrace();
		}
//		d1.printData(d1.trainData);
//		String s="A like 'bad'";
//		try {
//			ArrayList<String[]> data=d1.ExecuteSQLwithCondition(s);
//			d1.printData(data);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		String condition="";
		TreeNode root=d1.ID3(d1.trainData,d1.classLabelAttribute,d1.attributes,condition,"star");
		//d1.InfoGain(d1.trainData,0);
		
		TraverseTree traversal=new TraverseTree();
		System.out.println("--------------------------------------");
		System.out.println("*** Printing a Tree ***");
		traversal.traverseTree(root);
		System.out.println("---------------------------------------");
		System.out.println("*** Testing on test data ***");
		String fileName="E:\\Spring 2015\\B565-Data Mining\\DataMining\\DataMining\\src\\HW_3\\SourceFiles\\test.txt";
		//traversal.getProbabilityOftupleInTestFile(fileName,root);
	}
	
	
}
