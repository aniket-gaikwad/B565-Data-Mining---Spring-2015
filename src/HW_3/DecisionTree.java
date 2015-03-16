package HW_3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import HW_3.TreeNode;

public class DecisionTree {
	public String inputFile;
	public String attributes;
	public String classLabelAttribute;
	public String databaseName;//="decisiontree";
	public String server;//="localhost";
	public String userName;//="root";
	public String passWord;//="";
	public String tableName="traindata";
	public int numberOfArgumentInSql=0;
	public int numberOfDistinctClassValues;
	public int lengthOftuple;
	public static boolean isThisFirstCall=true;
	
	MySqlConnection mySqlConn = null;
	Statement st;
	
	public ArrayList<String> classLabel=new ArrayList<String>();           // This will hold actual distinct class label
	public ArrayList<String[]> trainData=new ArrayList<String[]>();
	HashMap<String,ArrayList<String>> attributeDictionary=new HashMap<String,ArrayList<String>>();

	
	public boolean getInputData(String trainFile){
		/*
		 * FileReading : Assumption - As explained in Assignment, I am assuming that each train file
		 *               will contain first name as 'FILENAME', second line 'CLASS_LABEL',third line
		 *               'ATTRIBUTE'.           
		 * */
		System.out.println("--------------------------------------------------------");
		System.out.println(" **** Reading of tree.txt file starts **** ");
		
		String line=null;
		try{
			int rowCount=0;
			FileReader reader=new FileReader(trainFile);
			BufferedReader buf=new BufferedReader(reader);
			while ((line = buf.readLine()) != null) {
				String[] result = line.split("\\t");        //As file is Tab-separated
				if(rowCount==0){
					inputFile=result[0];
					rowCount++;
				}
				else if(rowCount==1){
					classLabelAttribute=result[0];
					rowCount++;
				}
				else{
					int attributeNumber=0;
					while(attributeNumber!=result.length){
						if(attributeNumber==0){
							attributes=result[attributeNumber++]+",";
						}
						else if(attributeNumber<result.length-1){
							attributes=attributes+result[attributeNumber++]+",";
						}
						else{
							attributes=attributes+result[attributeNumber++];
						}
						
					}
				}
				
			}
			System.out.println(" **** Reading of tree.txt file ends **** ");
			return true;
		}catch (FileNotFoundException e) {
            System.out.println("File Not Found !!!");
            return false;
        } catch (IOException e) {
            System.out.println("Error in reading file : ");
            e.printStackTrace();
            return false;
        }
		
	}
	
/******************************************************************************************************/	
	public void insertDataToMySql(String trainFile){
		System.out.println("--------------------------------------------------------");
		System.out.println(" **** Insertion of input data to MySql table start **** ");
		String line=null;
		try{
			int rowCount=0;
			FileReader reader=new FileReader(trainFile);
			BufferedReader buf=new BufferedReader(reader);
			line = buf.readLine();
			inputFile=line;
//				System.out.println(" TrainFile : "+trainFile);
//				System.out.println(" InputFile : "+inputFile);
			
			char[] trainFileCharArray=trainFile.toCharArray();
			//System.out.println(" trainFileCharArray.length "+trainFileCharArray.length);
			int i=trainFileCharArray.length-1;
			int count=0;
			String temp = "";
			while(i>=0){
				if(trainFileCharArray[i]=='\\'){
					break;
				}
				temp=trainFileCharArray[i]+temp;
				i--;
				count++;
			}
			
			//System.out.println(" temp : "+temp);
			
			int j=0;
			String rest="";
			while(j<trainFileCharArray.length-count){
				rest=rest+trainFileCharArray[j];
				j++;
			}
			//System.out.println(" Rest of the String : "+rest);
			
			temp=inputFile;
			String inputFilePath=rest+temp;
			//System.out.println("New path : "+inputFilePath);
			
			// Reading actual Input Data File
	/***********************************************************************************************/
			BufferedReader inputBuffer = new BufferedReader(new FileReader(new File(inputFilePath)));
			line = inputBuffer.readLine();
			//System.out.println(" line : "+line);
			String[] header = line.split(",");   // This is data header
	/***********************************************************************************************/
			System.out.println("Dropping existing table");
			String query=("drop table if exists traindata\n"); 
			try{
				makeConnection();
				st.executeUpdate(query);
	            
			}catch(SQLException e){
				System.out.println("SQL Error :(");
	            e.printStackTrace();
			}
			System.out.println("Table Dropped successfully");
	/*************************************************************************************************/
			int numberOfColumns=header.length;
			query="";
			query="Create table traindata ( ";
			System.out.println("Creating a new table");
			for(int columnIndex=0;columnIndex<numberOfColumns;columnIndex++){
				if(columnIndex==0){
					query=query+header[columnIndex]+" varchar(10) ";
				}
				else{
					query=query+", \n"+header[columnIndex]+" varchar(10) ";
				}
				
	        }
			query=query+")";
			//System.out.println("Query : "+query);
			try{
				makeConnection();
				st.executeUpdate(query);
	            
			}catch(SQLException e){
				System.out.println("SQL Error :(");
	            e.printStackTrace();
			}
			System.out.println("New Table created successfully ... !!!!");
			
	
	/*************************************************************************************************/
			System.out.println("Inserting records in table");
			while((line = inputBuffer.readLine())!=null){
				String[] tuple = line.split(",");
				query="";
				query="insert into traindata values(";
				for(int tupleIndex=0 ; tupleIndex<numberOfColumns ; tupleIndex++){
					//System.out.println(" tuple[tupleIndex] : "+tuple[tupleIndex]);
					if(tupleIndex==numberOfColumns-1){
						query=query+"\'"+tuple[tupleIndex]+"\'";
					}
					else{
						query=query+"\'"+tuple[tupleIndex]+"\',";
					}
				}
				query=query+")";
				//System.out.println(" query : "+query);
				
				try{
					makeConnection();
					int rowsReturned = st.executeUpdate(query);
				}catch(SQLException e){
					System.out.println("SQL Error :(");
		            e.printStackTrace();
				}
				
			}
			System.out.println("Records inserted successfully into table ");
		System.out.println(" **** Insertion of input data to MySql table ends **** ");

			
	/************************************************************************************************/		
		}catch (FileNotFoundException e) {
            System.out.println("File Not Found !!!");
        } catch (IOException e) {
            System.out.println("Error in reading file : ");
            e.printStackTrace();
        }

	}
/******************************************************************************************************/
	public void populateTrainData() throws SQLException{
		System.out.println("----------------------------------------------------------");
		System.out.println(" **** Population of Data from MySql starts **** ");
		lengthOftuple=(attributes.length()/2)+1+classLabelAttribute.length();
		//System.out.println("Length Of Tuple : "+lengthOftuple);
		String statement="select "+attributes+","+classLabelAttribute+" from "+databaseName+"."+tableName;
		//System.out.println("SQL Query : "+statement);
		try {
			makeConnection();
			ResultSet res=st.executeQuery(statement);
			if(attributes!=null){
				numberOfArgumentInSql=(attributes.length()/2)+1+classLabelAttribute.length();
			}
			
			
			while(res.next()){
				String rowArray[]=new String[numberOfArgumentInSql];
				int temp=0;
				int index=0;
				
				//System.out.println("");
				while(temp<attributes.length()){
					String data=attributes.charAt(temp)+"";
					if(data.equals(",")){
						temp++;
						continue;
					}
					else{
						//System.out.println(data);
						rowArray[index++]=res.getString(data);
						//System.out.print("\t"+res.getString(data));
						temp++;
					}
				}
				
				temp=0;
				while(temp<classLabelAttribute.length()){
					String data=classLabelAttribute.charAt(temp)+"";
					if(data.equals(",")){
						temp++;
						continue;
					}
					else{
						//System.out.println(data);
						rowArray[index++]=res.getString(data);
						//System.out.print("\t"+res.getString(data));
						temp++;
					}
				}
			trainData.add(rowArray);
			}
		}catch(SQLException s){
			System.out.println("SQL not able to execute");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		finally{
			mySqlConn.conn.close();
			}
		// Populating a class Label values
		getDistinctClasslabel();
		System.out.println(" **** Population of Data from MySql starts **** ");
	}
/*****************************************************************************************************/	
	public void makeConnection(){
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
	
/*******************************************************************************************************/	
	public ArrayList<ArrayList<String>> ExecuteSQLwithCondition (String classAttr,String trainAttr,String condition) throws SQLException{
		ArrayList<ArrayList<String>> newTrainData=new ArrayList<ArrayList<String>>();
		String statement;
		if(trainAttr==null){
			statement="select "+classAttr+" from "+databaseName+"."+tableName
			          +" Where "+condition;
		}
		else{
			statement="select "+trainAttr+","+classAttr+" from "+databaseName+"."+tableName
			          +" Where "+condition;
		}
		
		//System.out.println("SQL Query : "+statement);
		
		try {
			makeConnection();
			ResultSet res=st.executeQuery(statement);
			//System.out.println(" Here 1");
			while(res.next()){
				ArrayList<String> rowArray=new ArrayList<String>();
				//String rowArray[]=new String[numberOfArgumentInSql];
				int temp=0;
				int index=0;
				//System.out.println(" Here 2");
				//System.out.println("");
				if(trainAttr!=null){
					while(temp<trainAttr.length()){
						String data=trainAttr.charAt(temp)+"";
						if(data.equals(",")){
							temp++;
							continue;
						}
						else{
							rowArray.add(res.getString(data));
							temp++;
						}
					}
				}
				
				//System.out.println(" Here 3");
				temp=0;
				while(temp<classAttr.length()){
					String data=classAttr.charAt(temp)+"";
					if(data.equals(",")){
						temp++;
						continue;
					}
					else{
						rowArray.add(res.getString(data));
						temp++;
					}
				}
				newTrainData.add(rowArray);
			}
		}catch(SQLException s){
			System.out.println("SQL not able to execute");
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			mySqlConn.conn.close();
			}
		return newTrainData;
	}
/******************************************************************************************************/
	void printDistinctAttrValues(HashMap<String,ArrayList<String>> attributeDictionary){
		System.out.println("*** Distinct values of Attribute ***\n");
		Iterator it = attributeDictionary.entrySet().iterator();
		float prob;
	    while (it.hasNext()) {
	        HashMap.Entry pair = (HashMap.Entry)it.next();
	        System.out.println("Attribute Name :"+pair.getKey()+" : "+pair.getValue());
	        //it.remove(); // avoids a ConcurrentModificationException
	    }
	}	
/*******************************************************************************************************/	
	public TreeNode ID3(ArrayList<String[]> examples,String targetAttribute,String trainAttribute,String condition,String prevAttributeValue){
		
		int noOfAttrArg = 0;
		//System.out.println(" STATUS OF : "+isThisFirstCall);
		//System.out.println("---------------------------------------------------------------");
		//System.out.println(" ****** ID3 Begin ****** ");
		//System.out.println("\n targetAttribute : "+targetAttribute);
		//System.out.println("\n trainAttribute : "+trainAttribute);
		// Populate the distinct class labels
		
		if(trainAttribute!=null){
			//System.out.println(" Train Attribute length: "+trainAttribute.length());
		}
		
		//System.out.println(" Target Attribute length: "+targetAttribute.length());
		// Train attribute will be like : a,b,c so length will be 5 as comma is there
		// So actual length will be length of Train attribute divide by 2 plus 1
		if(trainAttribute!=null){
			noOfAttrArg=(trainAttribute.length()/2)+1+targetAttribute.length();
		}
		
		//System.out.println("No of Arguments :"+noOfAttrArg);
		//System.out.println("\n ****** Examples *******: \n");
		//printData(examples,noOfAttrArg);
		
		
		ArrayList<String> distictValuesOfAttribute;
		if(isThisFirstCall){
			// This will hold name of attribute and all distinct values in an attribute
			String attributeList[]=trainAttribute.split(",");
			String attributeName = null;
			for(int x=0;x<attributeList.length;x++){
				attributeName=getAttributeName(trainAttribute,x).trim();
				//System.out.println(" attributeName : "+attributeName);
				distictValuesOfAttribute=getDistinctValues(examples,x);
				//System.out.println("distictValuesOfAttribute.size() : "+distictValuesOfAttribute.size());
				attributeDictionary.put(attributeName, distictValuesOfAttribute);
			}
			//printDistinctAttrValues(attributeDictionary);
//				/*******/
//				distictValuesOfAttribute=attributeDictionary.get(attributeName);
//				System.out.println("distictValuesOfAttribute.size() : "+distictValuesOfAttribute.size());
//				int x=0;
//				while(x<distictValuesOfAttribute.size()){
//					System.out.println(distictValuesOfAttribute.get(x++)+"\t");
//				}
//			
//			   /***/
		 isThisFirstCall=false;	
		}
		
		TreeNode newNode=new TreeNode();
		newNode.classProbability=getProbability(examples); 
		float maxGain=0;
	
		if(trainAttribute==null){
			//System.out.println(" ************ Train Attribute is Null **************");
			newNode.attributeName="Child Node";
			newNode.children=null;
			newNode.prevAttributeValue=prevAttributeValue;
	        return newNode;
		}
		else{
			String result[]=trainAttribute.split(",");
			String maxGainAttribute=null;
			
			int attributeIndex = 0;
			for(int i=0;i<result.length;i++){
				// Calculate the maximum info gain attribute
				//System.out.println(" \n For attribute : "+getAttributeName(trainAttribute, i));
				float infoGain=InfoGain(targetAttribute,trainAttribute,examples,i,condition);
				if(i==0){
					maxGain=infoGain;
					maxGainAttribute=result[i];
					attributeIndex=i;
				}
				else{
					if(infoGain > maxGain){
						maxGain=infoGain;
						maxGainAttribute=result[i];
						attributeIndex=i;
					}
				}
			}
			
			// Assign the Class probability to Node
			//System.out.println(" *** MAXIMUM INFO-GAIN ATTRIBUTE *** "+maxGainAttribute);
			//System.out.println("-----------------------------------------------------------");
			newNode.attributeName=maxGainAttribute;
			newNode.classProbability=getProbability(examples );
			newNode.prevAttributeValue=prevAttributeValue;
			//System.out.println("-----------------------------------------------------------");
			// Get all distinct values of attribute selected having Maximum Info gain
			//System.out.println(" Attribute MAX GAIN IS : "+getAttributeName(trainAttribute,attributeIndex));
			distictValuesOfAttribute=attributeDictionary.get(getAttributeName(trainAttribute,attributeIndex));
			// 
			/*****/
			int x=0;
			//System.out.println(" For attribute which has maximum Info gain values are : ");
			
			//System.out.println("-----------------------------------------------------------");
		   /***/
			
			// Remove the attribute selected now for branching from TrainAttribute set
			String newTrainAttribute=null;
			String result1[]=trainAttribute.split(",");
			for(int j=0;j<result1.length;j++){
				if(result1[j].equals(maxGainAttribute)){
					continue;
				}
				else{
					if(newTrainAttribute==null){
						newTrainAttribute=result1[j];
					}
					else{
					newTrainAttribute=newTrainAttribute+","+result1[j];
					}
				}
			}
			
			//System.out.println(" Next Train Attribute : "+newTrainAttribute);
			//System.out.println(" Next Target Attribute : "+targetAttribute);
			
			
			for(String val:distictValuesOfAttribute){
				ArrayList<String[]> newExample=new ArrayList<String[]>();
				ArrayList<ArrayList<String>> tempExample=new ArrayList<ArrayList<String>>();
				//Object val;
				String valueInAttribute=val.toString();
				String newCondition;
				if(condition==""){
					newCondition=maxGainAttribute+"="+"'"+val+"'";
				}
				else{
					newCondition=condition+" and "+maxGainAttribute+"="+"'"+val+"'";
				}
				
					try {
						tempExample=ExecuteSQLwithCondition(targetAttribute,newTrainAttribute,newCondition);
						newExample=processExample(tempExample);
//						if(newTrainAttribute!=null){
//							noOfAttrArg=(newTrainAttribute.length()/2)+1+targetAttribute.length();
//						}
						//System.out.println("No of Arguments :"+noOfAttrArg);
						//System.out.println("\n ****** New Examples *******: \n");
						//printData(newExample,noOfAttrArg);
						if(newExample.isEmpty()){
							TreeNode childNode=new TreeNode();
							childNode.attributeName="Child Node";
							childNode.classProbability=newNode.classProbability; // Assigning parent prob. to child
							childNode.children=null;
							childNode.prevAttributeValue=val;
					        newNode.children.put(valueInAttribute,childNode);
						}
						else{
							newNode.children.put(valueInAttribute,ID3(newExample,targetAttribute,newTrainAttribute,newCondition,val));
						}
					} catch (SQLException e) {
						System.out.println("Exception in ExecuteSQLwithCondition()");
						e.printStackTrace();
					}
			}
		return(newNode);	
		}
//		getDistinctClasslabel();
//		printClassLabel();
//		getProbability(trainData);
//		Entropy(trainData);
//		return null;
	}

/******************************************************************************************************/
	ArrayList<String[]> processExample(ArrayList<ArrayList<String>> input){
		ArrayList<String[]> output=new ArrayList<String[]>();
		int i=0;
		while(i<input.size()){
			int tupleSize=input.get(i).size();
			String[] temp=new String[tupleSize];
			temp=(String[]) input.get(i).toArray(temp);
			//System.out.println(" Temp :"+temp);
			output.add(temp);
			i++;
		}
		return output;
	}
/*****************************************************************************************************/	
	public void getDistinctClasslabel(){
		classLabel=getDistinctValues(trainData,lengthOftuple-1);
	}
/******************************************************************************************************/	
	public float Entropy(ArrayList<String[]> data){
		float entropy=0;
		ArrayList<Probability> ClassLabelProbability=new ArrayList<Probability>();
		ClassLabelProbability=getProbability(data);
		
		for(Probability c:ClassLabelProbability){
			entropy-=(c.prob*Log(c.prob));
		}
		//System.out.println(" Entropy : "+entropy);
		return entropy;
	}
/******************************************************************************************************/
	public float InfoGain(String classAttr,String trainAttr,ArrayList<String[]> data,int attributeNumber,String condition){
			//System.out.println("\n ********** Information Gain Begin ********** ");
		ArrayList<String> listOfValues=new ArrayList<String>();
		int numberOfRecords=data.size();
		//System.out.println(" |S| = "+numberOfRecords);
		float trainDataEntropy=Entropy(data);
			//System.out.println("entropyOfData : "+trainDataEntropy);
		
		listOfValues=getDistinctValues(data,attributeNumber);
		
		/****************************************************************************/
			HashMap<String,Integer> ValueCountInAttribute=new HashMap<String,Integer>();
			
			int numberOfTuple;
			for(String[] x:data){
				int lengthOftuple=x.length;
				String key=x[attributeNumber];
				int value;
				if(ValueCountInAttribute.containsKey(key)){
					value=ValueCountInAttribute.get(key)+1;
					ValueCountInAttribute.put(key,value);
				}
				else{
					value=1;
					ValueCountInAttribute.put(key,value);
				}
			}
		
		/*****************************************************************************/
		float sumForAllValues=0;                     
		for(String val:listOfValues){
			// This will calculate             |S_v|*Entropy(S_v)/|S|
			String newCondition;
			if(condition==""){
				newCondition=getAttributeName(trainAttr,attributeNumber)+"="+"'"+val+"'";
			}
			else{
				newCondition=condition+" and "+getAttributeName(trainAttr,attributeNumber)+"="+"'"+val+"'";
			}
			
			try {
				ArrayList<ArrayList<String>> tempExample=ExecuteSQLwithCondition(classAttr,trainAttr,newCondition);
				ArrayList<String[]> newData=processExample(tempExample);
				float entropyOfsubsetData=Entropy(newData);
				sumForAllValues+=(ValueCountInAttribute.get(val)*entropyOfsubsetData/numberOfRecords);
				
			} catch (SQLException e) {
				System.out.println("Exception in ExecuteSQLwithCondition()");
				e.printStackTrace();
			}
		}
		//System.out.println("*** Attribute : ***"+getAttributeName(trainAttr,attributeNumber));
		//System.out.println("*** Info-Gain for Attribute is ***  : "+(trainDataEntropy-sumForAllValues));
		//System.out.println("-------------------------------------------------------------------------------");
		return(trainDataEntropy-sumForAllValues);
	}
	
/*****************************************************************************************************/
	public String getAttributeName(String trainAttr,int attributeNumber){
		String result[]=trainAttr.split(",");
		return(result[attributeNumber]);
	}
/*****************************************************************************************************/	
	public float Log(float val){
		return (float) (Math.log10(val)/Math.log10(2));
	}
	
/******************************************************************************************************/	
	public ArrayList<String> getDistinctValues(ArrayList<String[]> data,int attributeNumber){
		//System.out.println(" *** Get Distinct Values ");
		//System.out.println("Attribute number : "+attributeNumber);
		ArrayList<String> listOfValues=new ArrayList<String>();
		for(String[] x:data){
			String label=x[attributeNumber];            // Attribute first will be 0
			if(listOfValues.contains(label)){
				continue;
			}
			else{
				listOfValues.add(label);
			}
		}
		return listOfValues;
	}
/*****************************************************************************************************/	
	public ArrayList<Probability> getProbability(ArrayList<String[]> data){
		
		//System.out.println(" *** getProbability ***");
		/*** Calculate the number of times value in class label appear in data ***/
		ArrayList<Probability> ClassLabelProbability=new ArrayList<Probability>();
		HashMap<String,Integer> classLabelCount=new HashMap<String,Integer>();
		
		int i=0;
		int numberOfTuple;
		for(String[] x:data){
			int lengthOftuple=x.length;
			//System.out.println(" Length of tuple :"+(lengthOftuple-1));
			String key=x[lengthOftuple-1];
			//System.out.println(" Key : "+key);
			int value;
			if(classLabelCount.containsKey(key)){
				value=classLabelCount.get(key)+1;
				classLabelCount.put(key,value);
			}
			else{
				value=1;
				classLabelCount.put(key,value);
				i++;
				
			}
		}
		
		numberOfDistinctClassValues=i;                  // this is Gloabal variable
		numberOfTuple=data.size();
		
		//System.out.println(" numberOfDistinctClassValues : "+numberOfDistinctClassValues);
		//System.out.println(" numberOfTuple : "+numberOfTuple);
		
		/***  Calculation of actual probability  ***/
		
		int j=0;
		//System.out.println(" classLabel.size() : "+classLabel.size());
		while(j<classLabel.size()){
			//System.out.println(" classLabel.get(j) "+classLabel.get(j));
			if(!classLabelCount.containsKey(classLabel.get(j))){
				//System.out.println(" Dude...This class label isn't there in list....");
				Probability newProbObj=new Probability(classLabel.get(j),0);
				ClassLabelProbability.add(newProbObj);
			}
			j++;
		}
		
		Iterator it = classLabelCount.entrySet().iterator();
		float prob;
	    while (it.hasNext()) {
	        HashMap.Entry pair = (HashMap.Entry)it.next();
	        //System.out.println(pair.getKey() + " = " + pair.getValue());
	        prob=Float.parseFloat(pair.getValue().toString())/numberOfTuple;
	        Probability newProbObj=new Probability(pair.getKey().toString(),prob);
	        ClassLabelProbability.add(newProbObj);
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    //printClassLabelProbability(ClassLabelProbability);
		return ClassLabelProbability;
	}

/**************************************************************************************************/
	public void printClassLabelProbability(ArrayList<Probability> ClassLabelProbability){
		for(Probability x:ClassLabelProbability){
			System.out.println("Class Label : "+x.name+"\t"+" Probability : "+x.prob);
		}
	}


/******************************************************************************************************/
	public void printData(ArrayList<String[]> data,int noOfAttrArg){
		//System.out.println("\n :: Data :: \n");
		for(String[] x:data){
			int i=0;
			//System.out.println(" Length of X : "+x.length);
			int len=x.length;
			while(i<len){
				System.out.print("\t"+x[i++]);
			}
			System.out.println("");
		}
	}

/*****************************************************************************************************/
	public void printClassLabel(){
		//System.out.println("\n :: Distinct Class Label :: \n");
		for(String x:classLabel){
			//System.out.print("\t"+x);
		}
		//System.out.println();
	}
}
