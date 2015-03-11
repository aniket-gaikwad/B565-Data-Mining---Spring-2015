package HW_3;

import java.io.BufferedReader;
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

import HW_3.TreeNode;

public class DecisionTree {
	public String inputFile;
	public String attributes;
	public String classLabel;
	public String databaseName="decisiontree";
	public String server="localhost";
	public String userName="root";
	public String passWord="";
	public String tableName="table1";
	public int numberOfArgumentInSql=0;
	public int numberOfDistinctClassValues;
	
	ArrayList<String[]> trainData=new ArrayList<String[]>();
	ArrayList<Probability> ClassLabelProbability=new ArrayList<Probability>();
	HashMap<String,Integer> classLabelCount=new HashMap<String,Integer>();
	
	public void getInputData(String trainFile){
		/*
		 * FileReading : Assumption - As explained in Assignment, I am assuming that each train file
		 *               will contain first name as 'FILENAME', second line 'CLASS_LABEL',third line
		 *               'ATTRIBUTE'.           
		 * */
		
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
					classLabel=result[0];
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
		}catch (FileNotFoundException e) {
            System.out.println("File Not Found !!!");
        } catch (IOException e) {
            System.out.println("Error in reading file : ");
            e.printStackTrace();
        }
		
	}
	
/******************************************************************************************************/	
	void insertDataToMySql(){
		
	}
/******************************************************************************************************/	
	
	public void makeConnection() throws SQLException{
		/*
		 * makeConnection() : This function will form a SQL query based on the ATTRIBUTE and CLASSLABEL.
		 *                    and execute query on the MySql database by making ODBC connection.
		 *                    The CLASSLABEL will be always the last part of returned tuple.
		 *                    The each tuple returned is stored in ArrayList named TrainData.
		 * */
		
		String statement="select "+attributes+","+classLabel+" from "+databaseName+"."+tableName;
		System.out.println("SQL Query : "+statement);
		MySqlConnection mySqlConn = null;
		
		
		try{
			mySqlConn=new MySqlConnection(server,databaseName,userName,passWord);
			Statement st=mySqlConn.conn.createStatement();
			ResultSet res=st.executeQuery(statement);
			
			numberOfArgumentInSql=attributes.length()+classLabel.length()-2;
			
			
			while(res.next()){
				String rowArray[]=new String[numberOfArgumentInSql];
				int temp=0;
				int index=0;
				
				System.out.println("");
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
				while(temp<classLabel.length()){
					String data=classLabel.charAt(temp)+"";
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
		}
		catch(SQLException s){
			System.out.println("SQL not able to execute");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		finally{
		mySqlConn.conn.close();
		}
	}
	
/******************************************************************************************************/
	public void printTrainData(){
		System.out.println("\n :: Train Data :: \n");
		for(String[] x:trainData){
			int i=0;
			while(i<numberOfArgumentInSql){
				System.out.print("\t"+x[i++]);
			}
			System.out.println("");
		}
	}

/*******************************************************************************************************/	
	public TreeNode ID3(){
		
		getProbability(trainData);
		printClassLabelProbability();
		return null;
	}

/******************************************************************************************************/
	public ArrayList<Probability> getProbability(ArrayList<String[]> data){
		
		
		/*** Calculate the number of times value in class label appear in data ***/
		int i=0;
		int numberOfTuple;
		for(String[] x:data){
			int lengthOftuple=x.length;
			String key=x[lengthOftuple-1];
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
		
		System.out.println(" numberOfDistinctClassValues : "+numberOfDistinctClassValues);
		System.out.println(" numberOfTuple : "+numberOfTuple);
		
		/***  Calculation of actual probability  ***/
		
		Iterator it = classLabelCount.entrySet().iterator();
		float prob;
	    while (it.hasNext()) {
	        HashMap.Entry pair = (HashMap.Entry)it.next();
	        System.out.println(pair.getKey() + " = " + pair.getValue());
	        prob=Float.parseFloat(pair.getValue().toString())/numberOfTuple;
	        Probability newProbObj=new Probability(pair.getKey().toString(),prob);
	        ClassLabelProbability.add(newProbObj);
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		
		return ClassLabelProbability;
	}

/**************************************************************************************************/
	public void printClassLabelProbability(){
		for(Probability x:ClassLabelProbability){
			System.out.println("Class Label : "+x.name+"\t"+" Probability : "+x.prob);
		}
	}
}
