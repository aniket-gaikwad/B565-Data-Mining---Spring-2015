package HW_1;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class DataInsertionMoviesSplit {
	static private Statement statement=null;
	static private PreparedStatement preparedStatement = null;
	static private ResultSet resultSet=null;
	
	public static void main(String args[]) throws Exception{
		MySqlConnection connection=new MySqlConnection();
		String fileName="E:\\Spring 2015\\B565-Data Mining\\HW_1\\MovieLens\\ml-1m\\ml-1m\\Database\\movies.csv";
		String line=null;
		StringTokenizer token;
		try{ 
			
			FileReader reader=new FileReader(fileName);
			BufferedReader buf=new BufferedReader(reader);
			while((line=buf.readLine())!=null){
				int Action=0,Adventure=0,Animation=0,Children=0,Comedy=0,Crime=0,Documentary=0,Drama=0,
					    Fantasy=0,FilmNoir=0,Horror=0,Musical=0,Mystery=0,Romance=0,SciFi=0,Thriller=0,
					    War=0,Western=0;
				System.out.println("\n"+line);
				String[] result=line.split(",");
				
				int movieid=Integer.parseInt(result[0]);
				String Title=result[1];
				String Genres=result[2];
				
				token=new StringTokenizer(result[2],"|");
				while(token.hasMoreTokens()){
					
					String newToken=token.nextToken();
					System.out.println(newToken);
					if(newToken.equals("Action")){
						Action=1;
					}
					else if(newToken.equals("Adventure")){
						Adventure=1;
					}
					else if(newToken.equals("Animation")){
						Animation=1;
					}
					else if(newToken.equals("Children's")){
						Children=1;
					}
					else if(newToken.equals("Comedy")){
						Comedy=1;
					}
					else if(newToken.equals("Crime")){
						Crime=1;
					}
					else if(newToken.equals("Documentary")){
						Documentary=1;
					}
					else if(newToken.equals("Drama")){
						Drama=1;
					}
					else if(newToken.equals("Fantasy")){
						Fantasy=1;
					}
					else if(newToken.equals("Film-Noir")){
						FilmNoir=1;
					}
					else if(newToken.equals("Horror")){
						Horror=1;
					}
					else if(newToken.equals("Musical")){
						Musical=1;
					}
					else if(newToken.equals("Mystery")){
						Mystery=1;
					}
					else if(newToken.equals("Romance")){
						Romance=1;
					}
					else if(newToken.equals("Sci-Fi")){
						SciFi=1;
					}
					else if(newToken.equals("Thriller")){
						Thriller=1;
					}
					else if(newToken.equals("War")){
						War=1;
					}
					else if(newToken.equals("Western")){
						Western=1;
					}
					
				}
				 
				String newToken="";                     
				preparedStatement = connection.conn
				          .prepareStatement("insert into  movielens.movies values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				
				preparedStatement.setInt(1,movieid);
				preparedStatement.setString(2,Title);
				preparedStatement.setString(3,Genres);
				preparedStatement.setInt(4,Action);
				preparedStatement.setInt(5,Adventure);
				preparedStatement.setInt(6,Animation);
				preparedStatement.setInt(7,Children);
				preparedStatement.setInt(8,Comedy);
				preparedStatement.setInt(9,Crime);
				preparedStatement.setInt(10,Documentary);
				preparedStatement.setInt(11,Drama);
				preparedStatement.setInt(12,Fantasy);
				preparedStatement.setInt(13,FilmNoir);
				preparedStatement.setInt(14,Horror);
				preparedStatement.setInt(15,Musical);
				preparedStatement.setInt(16,Mystery);
				preparedStatement.setInt(17,Romance);
				preparedStatement.setInt(18,SciFi);
				preparedStatement.setInt(19,Thriller);
				preparedStatement.setInt(20,War);
				preparedStatement.setInt(21,Western);


				preparedStatement.executeUpdate();

			 }
		}
		catch(Exception e){
			throw e;
		}
		
	}

	static private void writeResultSet(ResultSet resultSet) throws SQLException {
	     
	    int cnt=0;
		while (resultSet.next()) {
	       
	      cnt++;
	      String movieID = resultSet.getString("MovieID");
	      String title = resultSet.getString("Title");
	      String generes = resultSet.getString("Genres");
	       
	      System.out.println("MovieID: " +movieID);
	      System.out.println("Title: " + title);
	      System.out.println("Generes: " + generes);
	      
	    }
		System.out.println("Count : "+cnt);
	  }
	
}
