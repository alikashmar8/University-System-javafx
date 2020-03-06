
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class InstructorMenuControler {
	public Instructor instructor;
	public Student s;
	public ArrayList<Student>students=Controler1.students;
	public ArrayList<Course> courses = Controler1.courses;
	public ObservableList<Student> studentss =FXCollections.observableArrayList();
	public ObservableList<Course> sCourses = FXCollections.observableArrayList();
	public ObservableList<Course> coursesOb = FXCollections.observableArrayList();
	@FXML Label instructorWelcome;
	@FXML TextField sName,sFileNb,sEmail,newSName,newSFileNb,newSEmail;
	@FXML DatePicker sAge,newSAge;
	@FXML TableView<Student> studentsTable,studentsTable2;
	@FXML TableView<Course> studentCoursesTable,coursesTable;
	@FXML TableColumn<Course,String> cNameColumn,cCodeColumn,cNameColumn2,cCodeColumn2;
	@FXML TableColumn<Student, String> name,name2;
	@FXML TableColumn<Student,int[]>fileNb,fileNb2;
	public void settingInstructor(Instructor ins) {
		coursesOb.clear();
		sCourses.clear();
		instructor = ins ;
		instructorWelcome.setText(instructorWelcome.getText() + " " + instructor.getName());
		studentss.addAll(students);
		name.setCellValueFactory(new PropertyValueFactory<Student,String>("name"));
		fileNb.setCellValueFactory(new PropertyValueFactory<Student,int[]>("fileNb"));
		studentsTable.setItems(studentss);
		name2.setCellValueFactory(new PropertyValueFactory<Student,String>("name"));
		fileNb2.setCellValueFactory(new PropertyValueFactory<Student,int[]>("fileNb"));
		studentsTable2.setItems(studentss);
		cNameColumn.setCellValueFactory(new PropertyValueFactory<Course,String>("name"));
		cNameColumn2.setCellValueFactory(new PropertyValueFactory<Course,String>("name"));
		cCodeColumn.setCellValueFactory(new PropertyValueFactory<Course,String>("code"));
		cCodeColumn2.setCellValueFactory(new PropertyValueFactory<Course,String>("code"));
		System.out.println(courses.size());
		coursesOb.addAll(courses);
		coursesTable.setItems(coursesOb);
		coursesTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}
	public void addStudent() {
		String sN = sName.getText();
		int sF = Integer.parseInt(sFileNb.getText());
		String sE = sEmail.getText();
		//String sA = (String) sAge.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		Student s = new Student(sN,sE,sF);
		students.add(s);
		studentss.add(s);
		try {
			//Statement stmt = dbConnector.getConnection().createStatement();
			String sql = "INSERT INTO students VALUES (?,?,?,?,?)";
			PreparedStatement prprdstmnt = null;
			Connection con = dbConnector.getConnection();
			prprdstmnt = con.prepareStatement(sql);
			prprdstmnt.setString(1, s.getName());
			prprdstmnt.setInt(2, s.getFileNb());
			prprdstmnt.setString(3, s.getUsername());
			prprdstmnt.setString(4, s.getPassword());
			prprdstmnt.setString(5, s.getEmail());
			//prprdstmnt.setString(6, s.getAge());
			prprdstmnt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void showStudent() {
		s= studentsTable.getSelectionModel().getSelectedItem();
		newSName.setText(s.getName());
		newSFileNb.setText(s.getFileNb()+"");
		newSEmail.setText(s.getEmail());
		//DateTimeFormatter formatter =DateTimeFormatter.ofPattern("dd/MM/yyyy");
		//LocalDate localDate = LocalDate.parse(s.getAge(),formatter);
		//newSAge.setValue(localDate);
	}
	public void save() throws SQLException {
		String n = newSName.getText();
		int f = Integer.parseInt(newSFileNb.getText());
		String e = newSEmail.getText();
		//String a = newSAge.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		PreparedStatement prprdstmnt = null;
		Connection con = dbConnector.getConnection();
		String sql ="UPDATE students SET name = ?, fileNb = ?, username = ?, password = ?, email = ? WHERE fileNb="+s.getFileNb()+"" ;
		prprdstmnt = con.prepareStatement(sql);
		prprdstmnt.setString(1, n);
		prprdstmnt.setInt(2, f);
		prprdstmnt.setString(3, f+"");
		prprdstmnt.setString(4, f+"");
		prprdstmnt.setString(5, e);
		//prprdstmnt.setString(6, a);
		prprdstmnt.executeUpdate();
		studentsTable.getItems().clear();
		Controler1.loadStudents();
		students=Controler1.students;
		studentss.clear();
		studentss.setAll(students);
		studentsTable.setItems(studentss);

	}
	public void showSelectedStudentCourse() {
		s = studentsTable2.getSelectionModel().getSelectedItem();
		System.out.println("in show: "+ s.getName());
		sCourses.clear();
		System.out.println(s.getCourses().size()+" size");
		System.out.println(s.getCourses().get(0).getCode());
		sCourses.addAll(s.getCourses());
		//System.out.println(s.getCourses().get(0).getCode());
//		if(s.getCourses().size() >0) {
//			for(int i = 0;i<s.getCourses().size();i++) {
//				sCourses.add(s.getCourses().get(i));
//			}
//		}
		studentCoursesTable.setItems(sCourses);
	}
	public void assignSelectedCourses(ActionEvent event) {
		ObservableList<Course> crs = coursesTable.getSelectionModel().getSelectedItems();
		s = studentsTable2.getSelectionModel().getSelectedItem();
		System.out.println(s.getEmail());
		System.out.println(crs.get(0).getName());
		for(int i = 0 ; i<crs.size();i++) {
			//crs.get(i).getCode();
			crs.get(i).getStudents().add(s);
			s.getCourses().add(crs.get(i));
		}

		showSelectedStudentCourse();
	}
	public void logout(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();

	}
}
