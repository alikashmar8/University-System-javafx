
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class ManagerMenuControler {
	Manager manager;
	Doctor d,doctorSelected;
	ArrayList<Doctor> doctors = Controler1.doctors;
	ArrayList<Instructor>instructors= Controler1.instructors;
	ObservableList<Doctor> drs =FXCollections.observableArrayList();
	ObservableList<Course> courses = FXCollections.observableArrayList();
	String dName,dEmail,iName,iEmail;
	String dAge,iAge;
	int dFn,iFn;
	@FXML Label managerWelcome;
	@FXML TextField IName,IFileNb,IEmail,doctorName,doctorFileNumber,doctorEmail,courseName,courseCode,courseCredit,detailsName,detailsCode,detailsCredits,detailsDr,detailsNbStudents,NameEdit,FileNbEdit,EmailEdit,UsernameEdit,PasswordEdit;
	@FXML DatePicker doctorAge,IAge,AgeEdit;
	@FXML ListView<String> availableDoctors,selectedDoctorCourses,availableCourses,availableInstructors;
	@FXML TableView<Doctor> drTable;
	@FXML TableView<Course> coursesTable;
	@FXML TableColumn<Course,String> cName,cCode;
	@FXML TableColumn <Course,int[]> cCredits;
	@FXML TableColumn<Doctor,String> name;
	@FXML TableColumn<Doctor,int[]> fileNb;
	
	public void settingManager(Manager m) {
		manager = m ;
		managerWelcome.setText(managerWelcome.getText() + " " + manager.getName());
		updateLists();
		courses.addAll(Controler1.courses);
		name.setCellValueFactory(new PropertyValueFactory<Doctor,String>("name"));
		fileNb.setCellValueFactory(new PropertyValueFactory<Doctor,int[]>("fileNb"));
		initDrs();
		drTable.setItems(drs);
		
		cName.setCellValueFactory(new PropertyValueFactory<Course,String>("name"));
		cCode.setCellValueFactory(new PropertyValueFactory<Course,String>("code"));
		cCredits.setCellValueFactory(new PropertyValueFactory<Course,int[]>("credits"));
		coursesTable.setItems(courses);
		
		coursesTable.setEditable(true);
		cName.setCellFactory(TextFieldTableCell.forTableColumn());
		cCode.setCellFactory(TextFieldTableCell.forTableColumn());
		
		drTable.setOnMouseClicked(e -> {
			showSelected();
		});
		NameEdit.setText(m.getName());
		FileNbEdit.setText(m.getFileNb()+"");
		EmailEdit.setText(m.getEmail());
	//	//DateTimeFormatter formatter =DateTimeFormatter.ofPattern("dd/MM/yyyy"	);
		//LocalDate localDate = LocalDate.parse(m.getAge(),formatter);
		//AgeEdit.setValue(localDate);
		UsernameEdit.setText(manager.getUsername());
		PasswordEdit.setText(manager.getPassword());
	}
	
	public void addDoctor(ActionEvent event) {
		dName = doctorName.getText();
	//	//dAge = doctorAge.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		//dAge = (String) doctorAge.getEditor().getText();
		
		dEmail = doctorEmail.getText();
		dFn = Integer.parseInt(doctorFileNumber.getText());		
		d = new Doctor(dName,dEmail,dFn);	
		doctors.add(d);
		drs.add(d);
		
		try {
			//Statement stmt = dbConnector.getConnection().createStatement();
			String sql = "INSERT INTO doctors VALUES (?,?,?,?,?)";
			PreparedStatement prprdstmnt = null;
			Connection con = dbConnector.getConnection();
			prprdstmnt = con.prepareStatement(sql);
			prprdstmnt.setString(1, d.getName());
			prprdstmnt.setInt(2, d.getFileNb());
			prprdstmnt.setString(3, d.getUsername());
			prprdstmnt.setString(4, d.getPassword());
			prprdstmnt.setString(5, d.getEmail());
			// "insert into user values ('',7)
			// "update
			prprdstmnt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		updateLists();
	}
	
	public void updateLists() {
		availableDoctors.getItems().clear();
		for(int x=0;x<doctors.size();x++) {
			availableDoctors.getItems().add(doctors.get(x).getName()+" - "+doctors.get(x).getFileNb());
		}
		availableInstructors.getItems().clear();
		for(int x=0;x<instructors.size();x++) {
			availableInstructors.getItems().add(instructors.get(x).getName()+" - "+instructors.get(x).getFileNb());
		}
		
	}
	

	public void showSelected() {
		availableCourses.getItems().clear();
		selectedDoctorCourses.getItems().clear();
		doctorSelected = drTable.getSelectionModel().getSelectedItem();
		for(int x = 0; x<doctorSelected.getCourses().size();x++) {
			selectedDoctorCourses.getItems().add(doctorSelected.getCourses().get(x).getName()+" - "+doctorSelected.getCourses().get(x).getCode());
		}
		
		ArrayList<Course> drCourses = doctorSelected.getCourses();
		ArrayList<Course> availableCourse = new ArrayList<Course>();
		for(int k = 0 ; k<courses.size();k++) {
			availableCourse.add(courses.get(k));
		}
		for(int k = 0 ;k<drCourses.size();k++) {
			for(int j = 0;j<availableCourse.size();j++) {
				if(drCourses.get(k).equals(availableCourse.get(j))) {
					availableCourse.remove(j);
				}
			}
		}
		availableCourses.getItems().clear();
		for(int ind = 0;ind<availableCourse.size();ind++) {
			availableCourses.getItems().add(availableCourse.get(ind).getName() + " - "+availableCourse.get(ind).getCode());
	
		}
	}
	public void addCourse() throws SQLException {
		Course cr = new Course(courseName.getText(),courseCode.getText(),Integer.parseInt(courseCredit.getText()));
		courses.add(cr);
	
		String sql = "INSERT INTO courses VALUES (?,?,?)";
		PreparedStatement prprdstmnt = null;
		Connection con = dbConnector.getConnection();
		prprdstmnt = con.prepareStatement(sql);
		prprdstmnt.setString(1, cr.getName());
		prprdstmnt.setString(2, cr.getCode());
		prprdstmnt.setInt(3, cr.getCredits());
		prprdstmnt.executeUpdate();
	}
	public void removeCr() throws SQLException {
		ObservableList<Course> selectedCr,allDrs;
		allDrs = coursesTable.getItems();
		selectedCr = coursesTable.getSelectionModel().getSelectedItems();
		String nb = selectedCr.get(0).getCode();
		selectedCr.forEach(allDrs :: remove);
		Statement stmt = dbConnector.getConnection().createStatement();
		String sql="DELETE From courses where code ='"+nb+"'";
		stmt.execute(sql);
		
	}
	public void initDrs() {
		for(int k =0;k<doctors.size();k++) {
			drs.add(doctors.get(k));
		}
	}
	public void showCrDetails() {
		Course cr = coursesTable.getSelectionModel().getSelectedItem();
		detailsName.setText(cr.getName());
		detailsCode.setText(cr.getCode());
		detailsCredits.setText(cr.getCredits()+"");
		if(cr.getTutor()==null) {
			detailsDr.setText("NULL");
		}
		else
			detailsDr.setText(cr.getTutor().getName());
		if(cr.getStudents().isEmpty()) {
			detailsNbStudents.setText("0");
		}
		else
			detailsNbStudents.setText(cr.getStudents().size()+"");
	}
	public void addSCourse(){
		int k;
		
		for(k=0;k<courses.size();k++) {
			if((courses.get(k).getName()+" - "+courses.get(k).getCode()).equals(availableCourses.getSelectionModel().getSelectedItem())) {
				manager.assignCourse(doctorSelected, courses.get(k));
				break;
			}
		}
		availableCourses.getSelectionModel().clearSelection();
		showSelected();
	}
	public void removeSCourse() {
		for(int k=0;k<doctorSelected.getCourses().size();k++) {
			if((doctorSelected.getCourses().get(k).getName()+" - "+doctorSelected.getCourses().get(k).getCode()).equals(selectedDoctorCourses.getSelectionModel().getSelectedItem())){
				selectedDoctorCourses.getSelectionModel().getSelectedItem();
				doctorSelected.getCourses().remove(k);
				break;
			}
		}
	}
	public void removeSDr() throws SQLException {
		int i = drTable.getSelectionModel().getSelectedIndex();
		Statement stmt = dbConnector.getConnection().createStatement();
		String sql="DELETE From doctors where fileNb ='"+drTable.getItems().get(i).getFileNb()+"'";
		stmt.execute(sql);
		drTable.getItems().remove(i);
		Controler1.doctors.clear();
		Controler1.doctors.addAll(drTable.getItems());
		updateLists();
	}
	public void logout(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();

	}
	public void addInstructor(ActionEvent event) {
		iName = IName.getText();
		
		
		iEmail = IEmail.getText();
		iFn = Integer.parseInt(IFileNb.getText());		
		Instructor i = new Instructor(iName,iEmail,iFn);	
		instructors.add(i);
		try {
			//Statement stmt = dbConnector.getConnection().createStatement();
			String sql = "INSERT INTO instructors VALUES (?,?,?,?,?)";
			PreparedStatement prprdstmnt = null;
			Connection con = dbConnector.getConnection();
			prprdstmnt = con.prepareStatement(sql);
			prprdstmnt.setString(1, i.getName());
			prprdstmnt.setInt(2, i.getFileNb());
			prprdstmnt.setString(3, i.getUsername());
			prprdstmnt.setString(4, i.getPassword());
			prprdstmnt.setString(5, i.getEmail());
			// "insert into user values ('',7)
			// "update
			prprdstmnt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		updateLists();
	}
	public void saveEdit() throws SQLException {
		String n = NameEdit.getText();
		String e = EmailEdit.getText();
		//String a = AgeEdit.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String u = UsernameEdit.getText();
		String p = PasswordEdit.getText();
		PreparedStatement prprdstmnt = null;
		Connection con = dbConnector.getConnection();
		String sql ="UPDATE managers SET name = ?, fileNb = ?, username = ?, password = ?,email = ? WHERE fileNb="+manager.getFileNb()+"" ;
		prprdstmnt = con.prepareStatement(sql);
		prprdstmnt.setString(1, n);
		prprdstmnt.setInt(2, manager.getFileNb());
		prprdstmnt.setString(3, u);
		if(PasswordEdit.getText().length() >3) {
			prprdstmnt.setString(4, p);
		}else {
			prprdstmnt.setString(4, manager.getFileNb()+"");	
		}
		prprdstmnt.setString(5, e);
		prprdstmnt.executeUpdate();
		managerWelcome.setText("Welcome Manager "+ n );
	}
}
