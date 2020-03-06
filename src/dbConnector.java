
import java.sql.Connection;
import java.sql.DriverManager;

public class dbConnector {
	
	private final static String CLAZZ = "com.mysql.cj.jdbc.Driver";
	private final static String URL = "jdbc:mysql://localhost:3306/univ";
	private final static String USER = "root";
	private final static String PASSWORD = "";
	private static Connection con;
	private static Connection Connect() {
		try {
			
			Class.forName(CLAZZ);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("Connected\n");
			return con;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	return null;
	}


public static Connection getConnection() {
	if(con==null)
		return Connect();
	return con;
}
}