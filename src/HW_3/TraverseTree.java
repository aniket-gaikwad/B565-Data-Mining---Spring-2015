package HW_3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class TraverseTree {

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
		//System.out.println("---------------------------------------------");
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
	
	public void getProbabilityOftupleInTestFile(String testFile,TreeNode root){
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
				String[] result=line.split("\\t");
				node=root;
				while(i<result.length){
					if(result[i].equals("*")){
						break;
					}
					parentNode=node;
					node=getNodeBasedOnTuple(result[i],node);
					if(node==null){
						// The new tuple introduced in test data. Get probability of parent node.
						System.out.println(" New Tuple encountered ");
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
		System.out.println(" **** Testing on test data starts **** ");
	}
	
	public TreeNode getNodeBasedOnTuple(String result,TreeNode root){
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
}
