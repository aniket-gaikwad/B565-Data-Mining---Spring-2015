package HW_1;
import java.util.*;

public class Tree {
	Node root,temp;

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}
	
	public void printTree(Node temp){
		if(temp!=null){
			printTree(temp.getLeft());
			if(temp.getLeft()==null&&temp.getRight()==null){
				System.out.println("\t"+temp.getTuple());
			}
			printTree(temp.getRight());
		}
	}
	
	public void addNode(List<Integer> tuple){
		if(root==null){
			this.temp=new Node(tuple);
			this.root=this.temp;
			return;
		}
//		else{
//			
//			temp=root;
//			while(temp!=null){
//			if(data<=this.temp.getData()){
//				if(this.temp.getLeftSubTree()==null){
//					TreeNode temp1=new TreeNode(data);
//					this.temp.setLeftSubTree(temp1);
//					return;
//				}
//				else{
//					this.temp=this.temp.getLeftSubTree();
//				}
//			}
//			else{
//				if(this.temp.getRightSubTree()==null){
//					TreeNode temp1=new TreeNode(data);
//					this.temp.setRightSubTree(temp1);
//					return;
//				}
//				else{
//					this.temp=this.temp.getRightSubTree();
//				}
//			}
//		}
//	}
}
	 
}
