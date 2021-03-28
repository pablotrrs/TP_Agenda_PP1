package persistencia.conexion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import org.apache.ibatis.jdbc.ScriptRunner;

public class RunningScripts {
   public void inicializarScript() throws Exception {
      //Registering the Driver
      DriverManager.registerDriver(new com.mysql.jdbc.Driver());
      //Getting the connection
      String mysqlUrl = "jdbc:mysql://localhost:3306";
      Connection con = DriverManager.getConnection(mysqlUrl, "root", "root");
      System.out.println("Connection established......");
      //Initialize the script runner
      ScriptRunner sr = new ScriptRunner(con);
      //Creating a reader object
      Reader reader = new BufferedReader(new FileReader("scriptAgenda.sql"));
      //Running the script
      sr.runScript(reader);
      
      
   }
}