
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DoctorMenuControler {
	Doctor doctor;
	ObservableList<Course> courses = FXCollections.observableArrayList();
	ObservableList<Student> students = FXCollections.observableArrayList();
	ObservableList<Petition> petitions = FXCollections.observableArrayList();

	Course selectedCourse;
	Petition p;
	@FXML Label welcomeDoctor;
	@FXML ChoiceBox <String>courseChoose,courseChoose2;
	@FXML TableView<Student> studentsTable;
	@FXML TableView <Petition> petitionsTable;
	@FXML TableColumn<Petition,String> pCode;
	@FXML TableColumn <Petition,int[]> pFileNb;
	@FXML TableColumn<Student, String> name;
	@FXML TableColumn<Student,int[]>fileNb;
	@FXML ToggleGroup radio,grp;
	@FXML RadioButton partialSet,finalSet,partialMark,finalMark,fullMark,accepted,rejected;
	@FXML TextField newMark,studentMark,editUsername,editName,editPassword,editRPassword,editEmail,pcode,pfileNb;
	@FXML TextArea pNote;
	@FXML DatePicker editAge;
	@FXML PieChart pieChart;
	public void settingDoctor(Doctor d) {
		doctor = d;
		welcomeDoctor.setText(welcomeDoctor.getText() + " " + doctor.getName());
		courses.clear();
		courses.addAll(doctor.getCourses());
		//System.out.println(doctor.getCourses().get(0).getCode() +" " + doctor.getCourses().get(1).getCode());
		System.out.println(doctor.getCourses().size());
		name.setCellValueFactory(new PropertyValueFactory<Student,String>("name"));
		fileNb.setCellValueFactory(new PropertyValueFactory<Student,int[]>("fileNb"));
		
		petitions.clear();
		petitions.addAll(d.getPetitions());
		pCode.setCellValueFactory(new PropertyValueFactory<Petition,String>("code"));
		pFileNb.setCellValueFactory(new PropertyValueFactory<Petition,int[]>("fileNb"));
		petitionsTable.setItems(petitions);
		for(int i = 0;i<courses.size();i++) {
			System.out.println(courses.get(i).getCode());
			courseChoose.getItems().add(courses.get(i).getCode());
		}
		for(int i = 0;i<courses.size();i++) {
			courseChoose2.getItems().add(courses.get(i).getCode());
		}
		editUsername.setText(d.getUsername());
		editName.setText(d.getName());
		//DateTimeFormatter formatter =DateTimeFormatter.ofPattern("dd/MM/yyyy");
		//LocalDate localDate = LocalDate.parse(d.getAge(),formatter);
		//editAge.setValue(localDate);
		editEmail.setText(d.getEmail());
	}
	public void showStudents() {
		
		String code = courseChoose.getValue();
		for(int i = 0;i<courses.size();i++) {
			if(code.equals(courses.get(i).getCode())) {
				selectedCourse = courses.get(i);
				break;
			}
		}
		students.clear();
		students.addAll(selectedCourse.getStudents());
		studentsTable.setItems(students);
	}
	public void submitMark() {
		Student s = studentsTable.getSelectionModel().getSelectedItem();
		int i;
		Mark m = null;
		for(i=0;i<s.getMarks().size();i++) {
			if(s.getMarks().get(i).getC().getCode().equals(selectedCourse.getCode())) {
				m=s.getMarks().get(i);
				System.out.println("no new");
				break;
			}
		}
		if(i==s.getMarks().size()) {
			System.out.println("new cr");
			m= new Mark(selectedCourse);
			s.getMarks().add(m);
		}
		if(partialSet.isSelected()) {
			m.setPartialMark(Float.parseFloat(newMark.getText()));
		}
		if(finalSet.isSelected()) {
			m.setFinalMarks(Float.parseFloat(newMark.getText()));
		}
	}
	public void showMark() {
		int i;
		Student s = studentsTable.getSelectionModel().getSelectedItem();
		for(i=0;i<s.getMarks().size();i++) {
			if(s.getMarks().get(i).getC().getCode().equals(selectedCourse.getCode())) {
				break;
			}
		}
		if(partialMark.isSelected()) {
			studentMark.setText(s.getMarks().get(i).getPartialMark()+"");
		}
		else {
			studentMark.setText(s.getMarks().get(i).getFinalMark()+"");
		}
		
	}
	public void selectCourse() {
		String code = courseChoose2.getValue();
		int pass=0,fail=0;
		for(int i = 0;i<courses.size();i++) {
			if(code.equals(courses.get(i).getCode())) {
				selectedCourse = courses.get(i);
				break;
			}
		}
		ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
		if(partialMark.isSelected()) {
			for(int i = 0 ; i<selectedCourse.getStudents().size();i++) {
				for(int j = 0 ; j<			selectedCourse.getStudents().get(i).getMarks().size();j++) {
					if(selectedCourse.getStudents().get(i).getMarks().get(j).getC().equals(selectedCourse)) {
						if(selectedCourse.getStudents().get(i).getMarks().get(j).getPartialMark()>14) {
							pass++;
						} else fail++;
					}
				}
			}
		}
		if(finalMark.isSelected()) {
			for(int i = 0 ; i<selectedCourse.getStudents().size();i++) {
				for(int j = 0 ; j<selectedCourse.getStudents().get(i).getMarks().size();j++) {
					if(selectedCourse.getStudents().get(i).getMarks().get(j).getC().equals(selectedCourse)) {
						if(selectedCourse.getStudents().get(i).getMarks().get(j).getFinalMark()>34) {
							pass++;
						} else fail++;
					}
				}
			}
		}
		if(fullMark.isSelected()) {
			for(int i = 0 ; i<selectedCourse.getStudents().size();i++) {
				for(int j = 0 ; j<selectedCourse.getStudents().get(i).getMarks().size();j++) {
					if(selectedCourse.getStudents().get(i).getMarks().get(j).getC().equals(selectedCourse)) {
						if(selectedCourse.getStudents().get(i).getMarks().get(j).getPartialMark()+selectedCourse.getStudents().get(i).getMarks().get(j).getFinalMark()>49) {
							pass++;
						} else fail++;
					}
				}
			}
		}
		data.add(new PieChart.Data("Pass", pass));
		data.add(new PieChart.Data("Fail", fail));
		pieChart.setData(data);
		System.out.println(pass +" "+ fail);
	}
	public void saveEdit() throws SQLException {
		String n = editName.getText();
		String e = editEmail.getText();
		String a = editAge.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String u = editUsername.getText();
		String p = editPassword.getText();
		PreparedStatement prprdstmnt = null;
		Connection con = dbConnector.getConnection();
		String sql ="UPDATE doctors SET name = ?, fileNumber = ?, username = ?, password = ?, email = ?, age = ? WHERE fileNumber="+doctor.getFileNb()+"" ;
		prprdstmnt = con.prepareStatement(sql);
		prprdstmnt.setString(1, n);
		prprdstmnt.setInt(2, doctor.getFileNb());
		prprdstmnt.setString(3, u);
		if(editPassword.getText().equals(editRPassword.getText()) && editPassword.getText().length() >3) {
			prprdstmnt.setString(4, p);
		}else {
			prprdstmnt.setString(4, doctor.getFileNb()+"");	
		}
		prprdstmnt.setString(5, e);
		prprdstmnt.setString(6, a);
		prprdstmnt.executeUpdate();
		welcomeDoctor.setText("Welcome Doctor "+ n );
		
	}
	public void showPDetails() {
		p = petitionsTable.getSelectionModel().getSelectedItem();
		pcode.setText(p.getCode());
		pfileNb.setText(p.getFileNb()+"");
		if(p.getNote()!=null) {
			pNote.setText(p.getNote());
		}
	}
	public void submitAnswer() {
		if(accepted.isSelected()) {
			p.setAnswer(true);
		}
		else {
			p.setAnswer(false);
		}
		petitions.remove(p);
	}
	public void logout(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();

	}
}
