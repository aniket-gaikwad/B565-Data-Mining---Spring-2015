package HW_3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class TraverseTree {
	MySqlConnection mySqlConn = null;
	Statement st;

	public void traverseTree(TreeNode root){
		if(root==null){
			return;
		}
		printNodeForTree(root);
		if(root.children==null){
			return;
		}
		Iterator it = root.children.entrySet().iterator();
		while (it.hasNext()) {
	        HashMap.Entry pair = (HashMap.Entry)it.next();
	        traverseTree((TreeNode) pair.getValue());
	    }
		
	}
	
	public void printNode(TreeNode root){
		int i=0;
		System.out.println("---------------------------------------------");
		//System.out.println("attributeName :"+root.attributeName);
		//System.out.println("prevAttributeValue : "+root.prevAttributeValue);
		//System.out.println("Probability :");
		System.out.print("Class :");
		while(i<root.classProbability.size()){
			//System.out.println("Value : "+root.classProbability.get(i).name+" Prob : "+root.classProbability.get(i).prob);
			System.out.print("\t"+root.classProbability.get(i).name+" = "+root.classProbability.get(i).prob+",");
			i++;
		}
		System.out.println("\n ---------------------------------------------");
	}
	
	public void printNodeForTree(TreeNode root){
		int i=0;
		System.out.println("---------------------------------------------");
		System.out.println("attributeName :"+root.attributeName);
		System.out.println("prevAttributeValue : "+root.prevAttributeValue);
		System.out.println("Probability :");
		System.out.print("Class : ");
		while(i<root.classProbability.size()){
			System.out.println("Value : "+root.classProbability.get(i).name+" Prob : "+root.classProbability.get(i).prob);
			//System.out.print("\t"+root.classProbability.get(i).name+" "+root.classProbability.get(i).prob+",");
			i++;
		}
		System.out.println("\n ---------------------------------------------");
	}
	
	public void getProbabilityOftupleInTestFile(String testFile,TreeNode root,ArrayList<String> inputTrainAttribute,boolean isCrossValidation){
		System.out.println("------------------------------------------------");
		System.out.println(" **** Testing on test data starts **** ");
		System.out.println("Test File : "+testFile);
		System.out.println(" \n--- Results ----\n");
		try {
			FileReader reader=new FileReader(testFile);
			BufferedReader buf=new BufferedReader(reader);
			String line;
			TreeNode node;
			TreeNode parentNode;
			
			while((line=buf.readLine())!=null){
				int i=0;
				String[] result;
				//System.out.println("isCrossValidation : "+isCrossValidation);
				if(isCrossValidation){
					result=line.split(",");
				}
				else{
					result=line.split("\\t");
				}
				
				node=root;
				while(i<result.length){
					//System.out.println("node.attributeName : "+node.attributeName);
					String nextNode=node.attributeName;
					//System.out.println("nextNode : "+nextNode);
					if(nextNode.equals("Child Node")){
						break;
					}
					int index=inputTrainAttribute.indexOf(nextNode);
					//System.out.println("index : "+index);
					String testTupleValue=result[index];
					//System.out.println("testTupleValue : "+testTupleValue);
					if(testTupleValue.equals("*")){
						break;
					}
					parentNode=node;
					node=getNodeBasedOnTuple(testTupleValue,node);
					if(node==null){
						// The new tuple introduced in test data. Get probability of parent node.
						//System.out.println(" New Tuple encountered ");
						node=parentNode;
						break;
					}
					i++;
				}
				
				if(node==null){
					System.out.println(" Either wrong attribute order or wrong number of attributes ");
					return;
				}
				System.out.println(" tuple :     "+line);
				printNode(node);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println(" File Not Found :");
			e.printStackTrace();
		} catch(IOException e){
			System.out.println(" IOExceptio :");
			e.printStackTrace();
		}
		System.out.println(" **** Testing on test data Ends **** ");
	}
	
	 
	
	public TreeNode getNodeBasedOnTuple(String result,TreeNode root){
		//System.out.println("result : "+result);
		if(result.equals("*")){
			return root;
		}
		else{
			Iterator it = root.children.entrySet().iterator();
			while (it.hasNext()) {
		        HashMap.Entry pair = (HashMap.Entry)it.next();
		        TreeNode child=(TreeNode) pair.getValue();
		        if(child.prevAttributeValue.equals(result)){
		        	return child;
		        }
		    }
			return null;
		}
	}
	
	public void makeConnection(String server,String databaseName,String userName,String passWord){
		/*
		 * makeConnection() : This function will form a SQL query based on the ATTRIBUTE and CLASSLABEL.
		 *                    and execute query on the MySql database by making ODBC connection.
		 *                    The CLASSLABEL will be always the last part of returned tuple.
		 *                    The each tuple returned is stored in ArrayList named TrainData.
		 * */
		
		
		
		try{
			mySqlConn=new MySqlConnection(server,databaseName,userName,passWord);
			st=mySqlConn.conn.createStatement();
		 }
		catch(SQLException s){
			System.out.println("SQL not able to execute");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
