package persistencia.conexion;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.ibatis.jdbc.ScriptRunner;

public class RunningScripts {
	public void inicializarScript() throws Exception {
		try (InputStream inputStream = getClass().getResourceAsStream("scriptAgenda.sql");
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			// Registering the Driver
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			// Getting the connection
			String mysqlUrl = "jdbc:mysql://localhost:3306";
			Connection con = DriverManager.getConnection(mysqlUrl, "root", "root");
			System.out.println("Connection established......");
			// Initialize the script runner
			ScriptRunner sr = new ScriptRunner(con);
			// Running the script
			sr.runScript(reader);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}
}