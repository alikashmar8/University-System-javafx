
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controler1 {
	public static Boolean flag = false;
	public int i = 1;
	public static ArrayList<Manager> managers = new ArrayList<Manager>();
	public static ArrayList<Doctor> doctors = new ArrayList<Doctor>();
	public static ArrayList<Course> courses = new ArrayList<Course>();
	public static ArrayList<Student> students = new ArrayList<Student>();
	public static ArrayList<Instructor>instructors=new ArrayList<Instructor>();
	Manager ma;
	Student s;
	Doctor d;
	Instructor ins;
	@FXML TextField username,password;
	@FXML Label error;
	public void adminLogin(ActionEvent event) throws IOException, SQLException {
		if(flag==false) {
		Statement stmt = dbConnector.getConnection().createStatement();
		String sql="SELECT * From managers";
		//stmt.execute(sql);
		ResultSet rs= stmt.executeQuery(sql);
		while(rs.next()) {
			String name = rs.getString(1);
			int fileNb = rs.getInt(2);
			String username = rs.getString(3);
			String password = rs.getString(4);
			String email = rs.getString(5);
			Manager m = new Manager(name,email,fileNb);
			m.setUsername(username);
			m.setPassword(password);
			managers.add(m);
		}
		
		Statement stmnt = dbConnector.getConnection().createStatement();
		String sqll="SELECT * From doctors";
		//stmt.execute(sql);
		ResultSet rrs= stmnt.executeQuery(sqll);
		while(rrs.next()) {
			String name = rrs.getString(1);
			int fileNb = rrs.getInt(2);
			String username = rrs.getString(3);
			String password = rrs.getString(4);
			String email = rrs.getString(5);
			Doctor d = new Doctor(name,email,fileNb);
			d.setUsername(username);
			d.setPassword(password);
			doctors.add(d);
		}
		loadCourses();
		loadInstructors();
		loadStudents();
		flag = true;
		}
		String user = username.getText();
		String pass = password.getText();
		if(isAvailable(user,pass)==1 ) {
		Parent root = FXMLLoader.load(getClass().getResource("AdminMenu.fxml"));	
		Scene scene = new Scene(root);
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
		}
		if(isAvailable(user,pass)==2) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("ManagerMenu.fxml"));
			Parent root = loader.load(); 
			ManagerMenuControler mmc = loader.getController();
			mmc.settingManager(ma);
			Scene scene = new Scene(root);
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		}
		if(isAvailable(user,pass)==3) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("DoctorMenu.fxml"));
			Parent root = loader.load(); 
			DoctorMenuControler dmc = loader.getController();
			dmc.settingDoctor(d);
			Scene scene = new Scene(root);
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		}
		if(isAvailable(user,pass)==4) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("InstructorMenu.fxml"));
			Parent root = loader.load(); 
			InstructorMenuControler imc = loader.getController();
			imc.settingInstructor(ins);
			Scene scene = new Scene(root);
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		}
		if(isAvailable(user,pass)==5) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("StudentMenu.fxml"));
			Parent root = loader.load(); 
			StudentMenuControler smc = loader.getController();
			smc.settingStudent(s);
			Scene scene = new Scene(root);
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		}
		if(isAvailable(user,pass)==0) {
			error.setVisible(true);			
		}
	}
	
	public int isAvailable(String user,String pass) {
		dbConnector.getConnection();
		if(user.equals("admin") && pass.equals("admin")) return 1;
		
		for(i = 0; i<managers.size();i++) {
			if(user.equals(managers.get(i).getUsername()) && (pass.equals(managers.get(i).getPassword()))) {
				ma = managers.get(i);
				return 2;
			}
		}
		
		for(i=0;i<doctors.size();i++) {
			if(user.equals(doctors.get(i).getUsername()) &&(pass.equals(doctors.get(i).getPassword()))){
			d = doctors.get(i);
			return 3;
			}
		}
		for(i=0;i<instructors.size();i++) {
			if(user.equals(instructors.get(i).getUsername()) &&(pass.equals(instructors.get(i).getPassword()))){
			ins = instructors.get(i);
			return 4;
			}
		}
		for(i=0;i<students.size();i++) {
			if(user.equals(students.get(i).getUsername()) &&(pass.equals(students.get(i).getPassword()))){
			s = students.get(i);
			return 5;
			}
		}
		return 0;
	}
	public void loadCourses() throws SQLException {
		Statement stmt = dbConnector.getConnection().createStatement();
		String sql="SELECT * From courses";
		//stmt.execute(sql);
		ResultSet rs= stmt.executeQuery(sql);
		while(rs.next()) {
			String name = rs.getString(1);
			String code = rs.getString(2);
			int credits = rs.getInt(3);
			Course c = new Course(name,code,credits);
			courses.add(c);
		}
	}
	public void loadInstructors() throws SQLException {
		Statement stmt = dbConnector.getConnection().createStatement();
		String sql="SELECT * From instructors";
		//stmt.execute(sql);
		ResultSet rs= stmt.executeQuery(sql);
		while(rs.next()) {
			String name = rs.getString(1);
			int fileNb = rs.getInt(2);
			String username = rs.getString(3);
			String password = rs.getString(4);
			String email = rs.getString(5);
//			String date = rs.getString(6);
			Instructor i = new Instructor(name,email,fileNb);
			i.setUsername(username);
			i.setPassword(password);
			instructors.add(i);
		}
	}
	public static void loadStudents() throws SQLException {
		students.clear();
		Statement stmt = dbConnector.getConnection().createStatement();
		String sql="SELECT * From students";
		//stmt.execute(sql);
		ResultSet rs= stmt.executeQuery(sql);
		while(rs.next()) {
			String name = rs.getString(1);
			int fileNb = rs.getInt(2);
			String username = rs.getString(3);
			String password = rs.getString(4);
			String email = rs.getString(5);
			//String date = rs.getString(6);
			Student s = new Student(name,email,fileNb);
			s.setUsername(username);
			s.setPassword(password);
			students.add(s);
		}
	}
	
	
	
	
	
	
	
	

}
