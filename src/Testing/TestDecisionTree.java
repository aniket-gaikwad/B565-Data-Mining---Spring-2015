package Testing;

import HW_3.DecisionTree;

public class TestDecisionTree {
	
	public static void main(String args[]){
		DecisionTree d1=new DecisionTree();
		d1.getInputData("E:\\Spring 2015\\B565-Data Mining\\DataMining\\src\\HW_3\\SourceFiles\\tree.txt");
		System.out.println("File Name : "+d1.inputFile);
		System.out.println("Class Label : "+d1.classLabel);
		System.out.println("Attributes : "+d1.attributes);
		try{
		d1.makeConnection();
		}
		catch(Exception e){
			System.out.println("Exception in makeConection () : " );
			e.printStackTrace();
		}
		d1.printTrainData();
		d1.ID3();
	}
	
	
}
