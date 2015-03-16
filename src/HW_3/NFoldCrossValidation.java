package HW_3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.bind.attachment.AttachmentMarshaller;

public class NFoldCrossValidation {
	MySqlConnection mySqlConn;
	Statement st;
	public String databaseName="decisiontree";
	public String server="localhost";
	public String userName="root";
	public String passWord="";
	public void makeConnection(){
		/*
		 * makeConnection() : This function will form a SQL query based on the ATTRIBUTE and CLASSLABEL.
		 *                    and execute query on the MySql database by making ODBC connection.
		 *                    The CLASSLABEL will be always the last part of returned tuple.
		 *                    The each tuple returned is stored in ArrayList named TrainData.
		 * */
		
		try{
			mySqlConn = new MySqlConnection(server,databaseName,userName,passWord);
			st = mySqlConn.conn.createStatement();
		 }
		catch(SQLException s){
			System.out.println("SQL not able to execute");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void insertDataToMySql(String inputFilePath,String tableName,boolean flag){
		//System.out.println("--------------------------------------------------------");
		//System.out.println(" **** Insertion of input data to MySql table start **** ");
		String line=null;
		String query;
		try{
			int rowCount=0;
 	/***********************************************************************************************/
			BufferedReader inputBuffer = new BufferedReader(new FileReader(new File(inputFilePath)));
			line = inputBuffer.readLine();
			String[] header = line.split(",");                // This is data header
	/***********************************************************************************************/
//			System.out.println("Dropping existing table");
//	
//			String query=("drop table if exists "+tableName+"\n");
//			System.out.println(" Query : "+query);
//			try{
//				makeConnection();
//				st.executeUpdate(query);
//	            
//			}catch(SQLException e){
//				System.out.println("SQL Error :(");
//	            e.printStackTrace();
//			}
//			System.out.println("Table Dropped successfully");
			
/******************************************************************************************************/			
			if(flag==true){
				//System.out.println("Truncating existing table");
				
				query=("truncate table "+tableName+"\n");
				//System.out.println(" Query : "+query);
				try{
					makeConnection();
					st.executeUpdate(query);
		            
				}catch(SQLException e){
					System.out.println("SQL Error :(");
		            e.printStackTrace();
				}
				//System.out.println("Table Truncated successfully");
			}

	/*************************************************************************************************/
			int numberOfColumns=header.length;
//			query="";
//			query="Create table "+tableName+" ( ";
//			System.out.println("Creating a new table");
//			for(int columnIndex=0;columnIndex<numberOfColumns;columnIndex++){
//				if(columnIndex==0){
//					query=query+header[columnIndex]+" varchar(10) ";
//				}
//				else{
//					query=query+", \n"+header[columnIndex]+" varchar(10) ";
//				}
//				
//	        }
//			query=query+")";
//			//System.out.println("Query : "+query);
//			try{
//				makeConnection();
//				st.executeUpdate(query);
//	            
//			}catch(SQLException e){
//				System.out.println("SQL Error :(");
//	            e.printStackTrace();
//			}
//			System.out.println("New Table created successfully ... !!!!");
//			
	
	/*************************************************************************************************/
			//System.out.println("Inserting records in table");
			while((line = inputBuffer.readLine())!=null){
				String[] tuple = line.split(",");
				query="";
				query="insert into "+tableName+" values(";
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
			//System.out.println("Records inserted successfully into table ");
		//System.out.println(" **** Insertion of input data to MySql table ends **** ");

			
	/************************************************************************************************/		
		}catch (FileNotFoundException e) {
            System.out.println("File Not Found !!!");
        } catch (IOException e) {
            System.out.println("Error in reading file : ");
            e.printStackTrace();
        }

	}
	
/*******************************************************************************************************/
	public void generateTestFile(String testAttribute,String testFilePath){
		//ArrayList<ArrayList<String>> newTrainData=new ArrayList<ArrayList<String>>();
		String statement;
		statement="select "+testAttribute+" from "+databaseName+"."+"testdata";
		FileWriter writer=null;
		BufferedWriter buf1 = null;			

		try {
			makeConnection();
			ResultSet res=st.executeQuery(statement);
			String file=testFilePath;
			
			writer=new FileWriter(file);
			
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
			 
			while(res.next()){
					
					//buf1=new BufferedWriter(writer);
					String newTuple=null;
					int temp=0;
					if(testAttribute!=null){
						while(temp<testAttribute.length()){
							String data=testAttribute.charAt(temp)+"";
							if(data.equals(",")){
								temp++;
								continue;
							}
							else{
								if(newTuple==null){
									newTuple=res.getString(data);
								}
								else{
									newTuple=newTuple+","+res.getString(data);
								}
							temp++;
							}
						}
						newTuple=newTuple;
						out.println(newTuple);
					}
					
				    
					//buf1.write(newTuple);
					//buf1.write("\n");
				}
			out.close();
			//buf1.close();
		    } 
		catch(SQLException s){
		    System.out.println("SQL not able to execute");
		   }
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try {
				mySqlConn.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			}
	}
/*******************************************************************************************************/	
//	public void generateTestFile(String fileName,String testAttribute){
//		char[] trainFileCharArray=fileName.toCharArray();
//		int i=trainFileCharArray.length-1;
//		int count=0;
//		String temp = "";
//		
//		while(i>=0){
//			if(trainFileCharArray[i]=='\\'){
//				break;
//			}
//			temp=trainFileCharArray[i]+temp;
//			i--;
//			count++;
//		}
//		System.out.println(" temp : "+temp);
//		
//		int j=0;
//		String rest="";
//		while(j<trainFileCharArray.length-count){
//			rest=rest+trainFileCharArray[j];
//			j++;
//		}
//		System.out.println(" Rest of the String : "+rest);
//		
//		String outFileName=rest+"test"+".txt";
//		
//		// Header of data file - test file
//		String header = null;
//		BufferedReader buf = null;
//		try {
//			FileReader reader = new FileReader(fileName);
//			buf=new BufferedReader(reader);
//			header = buf.readLine();
//		} catch (IOException e1) {
//			System.out.println("IO Exception in opening Input file for output files.");
//			e1.printStackTrace();
//		}
//		
//		String[] headerResult=header.split(",");
//		String[] attributeResult=testAttribute.split(",");
//		ArrayList<Integer> attributeIndexList=new ArrayList<Integer>();
//		
//		for(int index=0;i<attributeResult.length;index++){
//			String nextAttribute=attributeResult[index];
//			for(int index1=0;index1<headerResult.length;index1++){
//				if(nextAttribute.equals(headerResult[index1])){
//					attributeIndexList.add(index1);
//					break;
//				}
//				System.out.println(" WRONG ATTRIBUTES IN INPUT FILES : ");
//			}
//		}
//		
//		String line=null;
//		
//		try{
//			while((line=buf.readLine())!=null){
//				String result[]=line.split(",");
//				
//			}
//		}catch (IOException e) {
//			e.printStackTrace();
//		}
//		int flag=1;
//		String line=null;
//		
//		try{
//			for(String file :outputFileList){
//				/*
//				 * Writting Header to all output files
//				 * */
//				FileWriter writer=null;
//				BufferedWriter buf1 = null;			
//				writer = new FileWriter(file.toString());
//				buf1=new BufferedWriter(writer);
//				System.out.println(" File : "+file);
//				System.out.println(" Header : "+header);
//				buf1.write(header);
//				buf1.close();
//			}
//			
//		}
//		catch (IOException e) {
//				e.printStackTrace();
//		}
//		
//	}

}
