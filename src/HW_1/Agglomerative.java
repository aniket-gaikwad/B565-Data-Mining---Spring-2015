package HW_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Agglomerative {
				
		 int[][] inputMatrix=new int[86816][22];
				  
		 public int[][] getInputMatrix(){
			 return inputMatrix;
		 }
		
		 static List<Tree> TreeList=new ArrayList<Tree>(); 
		 static int cnt=0;
		 

         static public void loadData(String fileName){
				int row=0,col=0;
				//String fileName=fileName;
				
				System.out.println(fileName);
				String line=null;
				StringTokenizer token;
				int count=0;
				
				
				
				try{
					FileReader reader=new FileReader(fileName);                // read file
					BufferedReader buf=new BufferedReader(reader);            // Write to new file
					 
					while((line=buf.readLine())!=null){	
						List<Integer> l1=new ArrayList<Integer>();
						String[] result=line.split(",");                     // Split file into token separated by ::
						for(int i=0;i<result.length;i++){
							l1.add(Integer.parseInt(result[i]));
						}
						
						Tree t1=new Tree();
						t1.addNode(l1);
						TreeList.add(t1); 
						cnt++;
					}
					buf.close();
					 
				}
				catch(FileNotFoundException e){
					System.out.println("File Not Found : "+fileName);
				}
				catch(IOException e){
					System.out.println("Error in reading file : ");
					e.printStackTrace();
				}
}

         static public List<Tree> build(){
        	 CosineSimilarity c=new CosineSimilarity();
        	 Tree temp1=null,temp2=null;
        	 List<Tree> mergeTreeList=new ArrayList<Tree>();
        	 HashMap<String, Double> hash=new HashMap<String, Double>();
        	 double cosineSim;
        	 double max=0.0;
        	 int i=0;
        	 
        	 for(Tree t1:TreeList){
     			int[] x={t1.getRoot().getTuple().get(0),t1.getRoot().getTuple().get(1)};
     			for(Tree t2:TreeList.subList(i+1,TreeList.size())){
     				i++;
     				int[] y={t2.getRoot().getTuple().get(0),t2.getRoot().getTuple().get(1)};
     				
     			//	System.out.print("x : ");
     			//	print(x);
     			//	System.out.print("y : ");
     			//	print(y);
     				
     				String hashStr=""+x[0]+x[1]+y[0]+y[1];
     				String hashStr1=""+y[0]+y[1]+x[0]+x[1];
     				if(hash.containsKey(hashStr)){
     					cosineSim=hash.get(hashStr);
     				}
     				else if(hash.containsKey(hashStr1)){
     						cosineSim=hash.get(hashStr1);
     				}
     				else{
     				cosineSim=c.cosineSimilarity(x,y);
     				hash.put(hashStr, cosineSim);
     				hash.put(hashStr1, cosineSim);
     				}
     				if(max<cosineSim){
     					max=cosineSim;
     					temp1=null;
     					temp2=null;
     					temp1=t1;
     					temp2=t2;
     				}
     			//	System.out.print("\t Max :"+max);
     			//	System.out.print("\t "+cosineSim);
     			//	System.out.println();

     			}
        	 }
     		
        	 mergeTreeList.add(temp1);
        	 mergeTreeList.add(temp2);
        	 return mergeTreeList;
     	}

         
         static public List<Tree> build2(){
        	 CosineSimilarity c=new CosineSimilarity();
        	 Tree temp1=null,temp2=null;
        	 List<Tree> mergeTreeList=new ArrayList<Tree>();
        	 HashMap<String, Double> hash=new HashMap<String, Double>();
        	 double cosineSim;
        	 double max=0.0;
        	 int i=0;
        	 
        	 for(Tree t1:TreeList){
     			int[] x={t1.getRoot().getTuple().get(2),t1.getRoot().getTuple().get(3),
     					t1.getRoot().getTuple().get(4),t1.getRoot().getTuple().get(5),
     					t1.getRoot().getTuple().get(6),t1.getRoot().getTuple().get(7),
     					t1.getRoot().getTuple().get(8),t1.getRoot().getTuple().get(9),
     					t1.getRoot().getTuple().get(10),t1.getRoot().getTuple().get(11),
     					t1.getRoot().getTuple().get(12),t1.getRoot().getTuple().get(13),
     					t1.getRoot().getTuple().get(14),t1.getRoot().getTuple().get(15),
     					t1.getRoot().getTuple().get(16),t1.getRoot().getTuple().get(17),
     					t1.getRoot().getTuple().get(17),t1.getRoot().getTuple().get(19),
     					t1.getRoot().getTuple().get(20)};
     			for(Tree t2:TreeList.subList(i+1,TreeList.size())){
     				i++;
     				int[] y={t2.getRoot().getTuple().get(2),t2.getRoot().getTuple().get(3),
         					t2.getRoot().getTuple().get(4),t2.getRoot().getTuple().get(5),
         					t2.getRoot().getTuple().get(6),t2.getRoot().getTuple().get(7),
         					t2.getRoot().getTuple().get(8),t2.getRoot().getTuple().get(9),
         					t2.getRoot().getTuple().get(10),t2.getRoot().getTuple().get(11),
         					t2.getRoot().getTuple().get(12),t2.getRoot().getTuple().get(13),
         					t2.getRoot().getTuple().get(14),t2.getRoot().getTuple().get(15),
         					t2.getRoot().getTuple().get(16),t2.getRoot().getTuple().get(17),
         					t2.getRoot().getTuple().get(17),t2.getRoot().getTuple().get(19),
         					t2.getRoot().getTuple().get(20)};
     				
     				//System.out.print("x : ");
     				//print(x);
     				//System.out.print("y : ");
     				//print(y);
     				
     				String hashStr=""+x[0]+x[1]+x[2]+x[3]+x[4]+x[5]+x[6]+x[7]+x[8]+x[9]+x[10]+x[11]+x[12]+x[13]+x[14]+x[15]+x[16]+x[17]+x[18]+y[0]+y[1]+y[3]+y[4]+y[5]+y[6]+y[7]+y[8]+y[9]+y[10]+y[11]+y[12]+y[13]+y[14]+y[15]+y[16]+y[17]+y[18];
     				String hashStr1=""+y[0]+y[1]+y[3]+y[4]+y[5]+y[6]+y[7]+y[8]+y[9]+y[10]+y[11]+y[12]+y[13]+y[14]+y[15]+y[16]+y[17]+y[18]+x[0]+x[1]+x[2]+x[3]+x[4]+x[5]+x[6]+x[7]+x[8]+x[9]+x[10]+x[11]+x[12]+x[13]+x[14]+x[15]+x[16]+x[17]+x[18];
     				if(hash.containsKey(hashStr)){
     					cosineSim=hash.get(hashStr);
     				}
     				else if(hash.containsKey(hashStr1)){
     						cosineSim=hash.get(hashStr1);
     				}
     				else{
     				cosineSim=c.cosineSimilarity(x,y);
     				hash.put(hashStr, cosineSim);
     				hash.put(hashStr1, cosineSim);
     				}
     				if(max<cosineSim){
     					max=cosineSim;
     					temp1=null;
     					temp2=null;
     					temp1=t1;
     					temp2=t2;
     				}
     			//	System.out.print("\t Max :"+max);
     			//	System.out.print("\t "+cosineSim);
     			//	System.out.println();

     			}
        	 }
     		
        	 mergeTreeList.add(temp1);
        	 mergeTreeList.add(temp2);
        	 return mergeTreeList;
     	}

         
         public void printMatrix(){
    	   for(int i=0;i<inputMatrix.length;i++){
    		   System.out.println("");
    		   for(int j=0;j<inputMatrix[0].length;j++){
    			   System.out.print("\t"+inputMatrix[i][j]);
    		   }
    	   }
       }
         
         
         
         
         
         static void removeTree(List<Tree> removeList){
        	 for(Tree t1:removeList){
        		   TreeList.remove(t1);
        	   } 
         }
         
         static Node mergeTree(Tree t1,Tree t2){
     		int x1=t1.getRoot().getTuple().get(0);         // Age of Data point1
     		int y1=t1.getRoot().getTuple().get(1);		   // Profession of Data point1
     		
     		int x2=t2.getRoot().getTuple().get(0);        // Age of Data point2
     		int y2=t2.getRoot().getTuple().get(1);		  // Profession of Data point2
     		
     		int x3,y3;
     		
     		x3=(x1+x2)/2;
     		y3=(y1+y2)/2;
     		
     		List<Integer> newList=new ArrayList<Integer>();
     		
     		newList.add(x3);
     		newList.add(y3);
     		newList.add(0);
     		newList.add(0);
     		newList.add(0);
     		newList.add(0);
     		newList.add(0);
     		newList.add(0);
     		newList.add(0);
     		newList.add(0);
     		newList.add(0);
     		newList.add(0);
     		newList.add(0);
     		newList.add(0);
     		newList.add(0);
     		newList.add(0);
     		newList.add(0);
     		newList.add(0);
     		newList.add(0);
     		newList.add(0);
     		newList.add(0);
     		     		
     		Node newNode=new Node(newList);
     		newNode.setLeft(t1.getRoot());
     		newNode.setRight(t2.getRoot());
     		
     		return newNode;
     	}
         
         static Node mergeTree2(Tree t1,Tree t2){
        	 
        	 int[] tempAvg=new int[19];
        	 //int[] tempSumY=new int[19];
        	 for(int i=2;i<21;i++){
        		 int x1=t1.getRoot().getTuple().get(i);
        		 int x2=t2.getRoot().getTuple().get(i);
        		 tempAvg[i-2]=(x1+x2)/2;
//        		 int y1=t1.getRoot().getTuple().get(1);		    
//           		 int y2=t2.getRoot().getTuple().get(1);
//           		 tempSumY[i-2]=(y1+y2)/2;
        	 }
        	
      		List<Integer> newList=new ArrayList<Integer>();
      		
      		newList.add(0);
      		newList.add(0);
      		newList.add(tempAvg[0]);
      		newList.add(tempAvg[1]);
      		newList.add(tempAvg[2]);
      		newList.add(tempAvg[3]);
      		newList.add(tempAvg[4]);
      		newList.add(tempAvg[5]);
      		newList.add(tempAvg[6]);
      		newList.add(tempAvg[7]);
      		newList.add(tempAvg[8]);
      		newList.add(tempAvg[9]);
      		newList.add(tempAvg[10]);
      		newList.add(tempAvg[11]);
      		newList.add(tempAvg[12]);
      		newList.add(tempAvg[13]);
      		newList.add(tempAvg[14]);
      		newList.add(tempAvg[15]);
      		newList.add(tempAvg[16]);
      		newList.add(tempAvg[17]);
      		newList.add(tempAvg[18]);
      		
      		     		
      		Node newNode=new Node(newList);
      		newNode.setLeft(t1.getRoot());
      		newNode.setRight(t2.getRoot());
      		
      		return newNode;
      	}
       public static void addNode(Node l1){
    	   Tree t1=new Tree();
			t1.addNode(l1.getTuple());
			t1.setRoot(l1);
			TreeList.add(t1);
			//System.out.println("Added Tree's Child: \n");
			//t1.printTree(t1.getRoot());
       }
         
       static void print(int[] x){
    		
    		for(int i=0;i<x.length;i++){
    			System.out.print("\t"+x[i]);
    		}
    		System.out.print("\t");
    	}
       
       static void print(List<Tree> T){
      	 for(Tree t1:T){
    		   System.out.println(t1.getRoot().getTuple());
    	   }
       }
       
       public static void makeClustures(){
    	  
    	   List<Tree> mergeTreeList=build();
    	   //System.out.println("Tree to merge are : \n ");
    	  // print(mergeTreeList);
    	   
    	   
    	   Node newNode=mergeTree(mergeTreeList.get(0),mergeTreeList.get(1));
    	   
    	   removeTree(mergeTreeList); 
    	   addNode(newNode);
    	   
    	   //System.out.println("After Next of level clusture : \n ");
    	   //print(TreeList);

       }
       
       public static void makeClustures2(){
     	  
    	   List<Tree> mergeTreeList=build2();
    	   //System.out.println("Tree to merge are : \n ");
    	  // print(mergeTreeList);
    	   
    	   
    	   Node newNode=mergeTree2(mergeTreeList.get(0),mergeTreeList.get(1));
    	   
    	   removeTree(mergeTreeList); 
    	   addNode(newNode);
    	   
    	   //System.out.println("After Next of level clusture : \n ");
    	   //print(TreeList);

       }
       
       public static void printChildsOfCluster(){
    	   int i=0;
    	   for(Tree t1:TreeList){
    		   System.out.printf("\n Cluster : %d : ",i);
    		   i++;
    		   System.out.print(t1.getRoot().getTuple());
    		   System.out.println();
    		   t1.printTree(t1.getRoot());
    	   }
    	   
    	   System.out.println("*********************** Number of Clusters ***************"+i);
       }
       
       public static void main(String args[]){
    	   
    	   String fileName=args[0];
    	   System.out.println("File Path : "+fileName);
    	   loadData(fileName);
    	   int i=0;
    	   //print(TreeList);
    	   //System.out.println(args[1]);
    	   if(Integer.parseInt(args[1])==0){
    		   System.out.println("Clustering based on Age and Profession : ");
    		   while(i<86500){
	    		   makeClustures();
	    		   i++;
	    	   }
    	   }
    	   else{
    		   System.out.println("Clustering based on Genre and Ratings: ");
	    	   while(i<86500){
	    		   makeClustures2();
	    		   i++;
	    	   }
    	   }     	   
    	   System.out.println("********** Printing Childs as of clusture *********************\n");
    	   printChildsOfCluster();
       }
}
