package HW_1;
import java.util.*;

public class Node {

	List<Integer> tuple;
	Node left;
	Node right;
	
	Node(){
		left=null;
		right=null;
	}
	
	Node(List<Integer> tuple){
		this.tuple=tuple;
		left=null;
		right=null;
	}
	
	public void setTuple(List<Integer> tuple){
		this.tuple=tuple;
	}
	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public List<Integer> getTuple() {
		return tuple;
	}
		
}
