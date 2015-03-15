package HW_3;

import java.sql.SQLException;
import java.sql.Statement;

public class PopulateMySqlTable {
	MySqlConnection mySqlConn = null;
	Statement st;
	public String databaseName="decisiontree";
	public String server="localhost";
	public String userName="root";
	public String passWord="";
	
	public void insertToMySqlTable(){
		
	}
	
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
}
