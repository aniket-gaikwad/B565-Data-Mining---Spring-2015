package HW_3;
import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnection {

    public Connection conn ;
    public MySqlConnection(String server,String databaseName,String userName,String passWord){
        try {
            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName);
            String serverName = server;     //"localhost";
            String mydatabase = databaseName;
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
            String username = userName;     //"root"
            String password = passWord;    //"";
            conn = DriverManager.getConnection(url, username, password);
            //System.out.println("Connection Success");
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }


}