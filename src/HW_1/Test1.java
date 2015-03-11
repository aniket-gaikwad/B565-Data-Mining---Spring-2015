package HW_1;
import java.util.*;

public class Test1 {

	public static void main(String args[]){
		List<Tree> clusterList=new ArrayList<Tree>();
		Tree t1=new Tree();
		Tree t2=new Tree();
		Tree t3=new Tree();
		
		//List<List<Integer>> newTupleList=new ArrayList<List<Integer>>();
		
		List<Integer> l1=new ArrayList<Integer>();
		l1.add(1);l1.add(2);l1.add(3);l1.add(4);
		
		List<Integer> l2=new ArrayList<Integer>();
		l2.add(5);l2.add(6);l2.add(7);l2.add(8);
		
		//newTupleList.add(l1);newTupleList.add(l1);
		
		t1.addNode(l1);
		t2.addNode(l2);
		
		Node newNode=mergeTree(t1,t2);
		
		t3.setRoot(newNode);
		
		System.out.println("\n Printing final tree :");
		t3.printTree(t3.getRoot());
	}
	
	static Node mergeTree(Tree t1,Tree t2){
		int x1=t1.getRoot().getTuple().get(0);
		int y1=t1.getRoot().getTuple().get(1);
		
		int x2=t2.getRoot().getTuple().get(0);
		int y2=t2.getRoot().getTuple().get(1);
		
		int x3,y3;
		
		x3=(x1+x2)/2;
		y3=(x1+x2)/2;
		
		List<Integer> newList=new ArrayList<Integer>();
		
		newList.add(x3);newList.add(y3);newList.add(0);newList.add(0);
		
		Node newNode=new Node(newList);
		newNode.setLeft(t1.getRoot());
		newNode.setRight(t2.getRoot());
		
		return newNode;
	}
	
}
