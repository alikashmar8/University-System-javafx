
import java.io.IOException;
import java.time.format.DateTimeFormatter;

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

public class StudentMenuControler {
	Student student;
	Course selectedCourse;
	ObservableList<Course> courses = FXCollections.observableArrayList();
	ObservableList<Mark> marks = FXCollections.observableArrayList();
	ObservableList<Petition> petitions = FXCollections.observableArrayList();
	DateTimeFormatter formatter =DateTimeFormatter.ofPattern("dd/MM/yyyy");
	@FXML Label studentWelcome,sumOfCredits;
	@FXML TableView<Petition> sentPetitions;
	@FXML TableView<Course> myCourses;
	@FXML TableView <Mark> pMarks,fMarks;
	@FXML TableColumn<Petition,String> pCode;
	@FXML TableColumn<Petition,Boolean> pAnswer;
	@FXML TableColumn<Mark,String> cName2,cName1;
	@FXML TableColumn<Mark,float[]> cMark2,cMark1;
	@FXML TableColumn<Course,String> cName,cCode;
	@FXML TableColumn<Course,int[]> cCredits;
	@FXML ChoiceBox<String> selectCourse;
	@FXML TextArea note;
	public void settingStudent(Student s) {
		student = s;
		studentWelcome.setText(studentWelcome.getText() + " " + student.getName());
		courses.clear();
		marks.clear();
		courses.addAll(s.getCourses());
		marks.addAll(s.getMarks());
		
		cName.setCellValueFactory(new PropertyValueFactory<Course,String>("name"));
		cCode.setCellValueFactory(new PropertyValueFactory<Course,String>("code"));
		cCredits.setCellValueFactory(new PropertyValueFactory<Course,int[]>("credits"));
		myCourses.setItems(courses);
		
		
		
		System.out.println(s.getMarks().size() + " marks");
		cName1.setCellValueFactory(new PropertyValueFactory<Mark,String>("name"));
		cMark1.setCellValueFactory(new PropertyValueFactory<Mark,float[]>("partialMark"));
		pMarks.setItems(marks);

		cName2.setCellValueFactory(new PropertyValueFactory<Mark,String>("name"));
		cMark2.setCellValueFactory(new PropertyValueFactory<Mark,float[]>("finalMark"));
		fMarks.setItems(marks);
		
		petitions.clear();
		petitions.addAll(s.getPetitions());
		pCode.setCellValueFactory(new PropertyValueFactory<Petition,String>("code"));
		pAnswer.setCellValueFactory(new PropertyValueFactory<Petition,Boolean>("answer"));
		sentPetitions.setItems(petitions);
		
		for(int i = 0;i<s.getCourses().size();i++) {
			selectCourse.getItems().add(s.getCourses().get(i).getCode());
		}
		
		int sum = 0;
		for(int i = 0 ; i< courses.size();i++) {
			sum = sum + courses.get(i).getCredits();
		}
		sumOfCredits.setText(sumOfCredits.getText()+" "+sum);
		
	}
	
	public void sendPetition() {
		String code = selectCourse.getValue();
		Petition p;
		for(int i = 0;i<student.getCourses().size();i++) {
			if(code.equals(student.getCourses().get(i).getCode())) {
				selectedCourse = courses.get(i);
				break;
			}
		}
		try {
		if(note.getText().trim().isEmpty()) {
			p = new Petition(selectedCourse);
			p.setS(student);
			selectedCourse.getTutor().getPetitions().add(p);
			student.getPetitions().add(p);
		}
		else {
			p = new Petition(selectedCourse,note.getText());
			p.setS(student);
			selectedCourse.getTutor().getPetitions().add(p);
			student.getPetitions().add(p);
			petitions.add(p);
		}
		note.clear();
		
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
	}
	public void logout(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();

	}
	
	
}
