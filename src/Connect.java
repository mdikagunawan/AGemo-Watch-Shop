import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class Connect {

     Statement stmt;
     ResultSet rs;
     int result;
     ResultSetMetaData rsm;

    public Connect() {
        try{  
            Class.forName("com.mysql.jdbc.Driver");

            // Nanti prk nya diubah sesuai dengan nama db yang mau di connect
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/agemowatch","root","");  
            stmt = con.createStatement();  
            
            System.out.println("Connected to the database..");
        }catch(Exception e){ 
        	e.printStackTrace();
        }  
    }

    public ResultSet executeQuery(String query){
        try{
            rs = stmt.executeQuery(query);
            rsm = rs.getMetaData();
        }
        catch(Exception e){
            System.out.println(e);
        }

        return rs;
    }

    public int executeUpdate(String query){
        try{
            result = stmt.executeUpdate(query);
        }
        catch(Exception e){
            System.out.println(e);
        }
        return result;
    }

}