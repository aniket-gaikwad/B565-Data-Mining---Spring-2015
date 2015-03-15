package HW_3;

public class D3tree {

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
		
		System.out.println("\n Step 5 : ");
		TraverseTree traversal=new TraverseTree();
		traversal.getProbabilityOftupleInTestFile(testFile,root);
	
		System.out.println("\n\n *********** PROCESS ENDS ************");
	}
}
