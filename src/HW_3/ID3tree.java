package HW_3;

import java.util.ArrayList;

public class ID3tree {
	
	public DecisionTree d1;
	public TreeNode root;
	
	public static void main(String args[]){
		System.out.println(" *********** PROCESS STARTS ************");
		DecisionTree d1=new DecisionTree();
		String trainFile=args[0];
		String testFile=args[1];
		System.out.println("\n Step 1 :");
		if(!d1.getInputData(trainFile)){
			return;
		}
		
		System.out.println("\n Step 2 : ");
		d1.insertDataToMySql(trainFile);
		
		System.out.println("\n Step 3 : ");
		try{
		d1.populateTrainData();
		}
		catch(Exception e){
			System.out.println("Exception in makeConection () : " );
			e.printStackTrace();
		}
		
		System.out.println("\n Step 4 : ");
		String condition="";
		System.out.println("-------------------------------------------------------");
		System.out.println(" **** ID3 Starts **** ");
		TreeNode root=d1.ID3(d1.trainData,d1.classLabelAttribute,d1.attributes,condition,"star");
		System.out.println(" **** ID3 Ends **** ");
		
		int temp=0;
		int index=0;
		ArrayList<String> inputTrainAttribute=new ArrayList<String>();
		while(temp<d1.attributes.length()){
			String data=d1.attributes.charAt(temp)+"";
			if(data.equals(",")){
				temp++;
				continue;
			}
			else{
				//System.out.println("data : "+data);
				inputTrainAttribute.add(data);
				temp++;
			}
		}

		System.out.println("\n Step 5 : ");
		TraverseTree traversal=new TraverseTree();
		//traversal.traverseTree(root);
		traversal.getProbabilityOftupleInTestFile(testFile,root,inputTrainAttribute,false);
	
		System.out.println("\n\n *********** PROCESS ENDS ************");
	}
	
	public void ExecuteID3(String treeFile){
		System.out.println(" *********** PROCESS STARTS ************");
		d1=new DecisionTree();
		System.out.println("\n Step 1 :");
		if(!d1.getInputData(treeFile)){
			return;
		}
		
		System.out.println("\n Step 2 : ");
		try{
		d1.populateTrainData();
		}
		catch(Exception e){
			System.out.println("Exception in makeConection () : " );
			e.printStackTrace();
		}
		
		System.out.println("\n Step 3 : ");
		String condition="";
		System.out.println("-------------------------------------------------------");
		System.out.println(" **** ID3 Starts **** ");
		d1.isThisFirstCall=true;
		root=d1.ID3(d1.trainData,d1.classLabelAttribute,d1.attributes,condition,"star");
		System.out.println(" **** ID3 Ends **** ");
		
//		System.out.println("\n Step 4 : ");
//		TraverseTree traversal=new TraverseTree();
//		traversal.getProbabilityOftupleInTestFile(testFile,root);
	
		System.out.println("\n\n *********** PROCESS ENDS ************");

	}
}
