package Testing;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mysql.jdbc.Connection;
import HW_3.MySqlConnection;

public class testConnection {
	public static void main(String args[]) throws SQLException{
		String databaseName="decisiontree";
		String server="localhost";
		String userName="root";
		String passWord="";
		String tableName="table1";
		String statement="select * from "+databaseName+"."+tableName;
		PreparedStatement preparedStatement = null;
		MySqlConnection mySqlConn = null;
		
		try{
			mySqlConn=new MySqlConnection(server,databaseName,userName,passWord);
			Statement st=mySqlConn.conn.createStatement();
			ResultSet res=st.executeQuery(statement);
			while(res.next()){
				String a=res.getString("A").trim();
				int b=res.getInt("B");
				String c=res.getString("C").trim();
				String d=res.getString("D").trim();
				System.out.println("\t A :"+a+"\t B :"+b+"\t C :"+c+"\t D : "+d);
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
}
