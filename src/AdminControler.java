

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AdminControler {
	String mName, mEmail;
	public static ArrayList<Manager> managers = Controler1.managers;
	ArrayList<String> mNames = new ArrayList<String>();
	String mAge;
	int mFn;
	Manager m;
	@FXML
	TextField managerName, managerEmail, managerFn;
	@FXML
	public ListView<String> managerList,managerAvailable;
	@FXML
	DatePicker managerAge;
	
	public void removeManager() throws SQLException {
		int i;
		String s =managerAvailable.getSelectionModel().getSelectedItem();
		for( i=  0;i<managers.size();i++) {
			if((managers.get(i).getName() + " - " + managers.get(i).getFileNb()).equals(s)) {
			break;
			}
		}
		Statement stmt = dbConnector.getConnection().createStatement();
		String sql="DELETE From managers where name ='"+managers.get(i).getName()+"'";
		stmt.execute(sql);
		managers.remove(i);
		updateLists();
	}
	public void addManager(ActionEvent event) {
		mName = managerName.getText();
		mEmail = managerEmail.getText();
		mFn = Integer.parseInt(managerFn.getText());

		m = new Manager(mName, mEmail, mFn);

		managers.add(m);
		try {
			//Statement stmt = dbConnector.getConnection().createStatement();
			String sql = "INSERT INTO managers VALUES (?,?,?,?,?)";
			PreparedStatement prprdstmnt = null;
			Connection con = dbConnector.getConnection();
			prprdstmnt = con.prepareStatement(sql);
			prprdstmnt.setString(1, m.getName());
			prprdstmnt.setInt(2, m.getFileNb());
			prprdstmnt.setString(3, m.getUsername());
			prprdstmnt.setString(4, m.getPassword());
			prprdstmnt.setString(5, m.getEmail());
			// "insert into user values ('',7)
			// "update

			prprdstmnt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateLists();
	}

	public void logout(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene scene = new Scene(root, 400, 400);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();

	}

	public void conn() {

		try {
			Statement stmt = dbConnector.getConnection().createStatement();
			String sql = "SELECT * From users";
			// "insert into user values ('',7)
			// "update
			// stmt.execute(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int a = rs.getInt(1);
				System.out.println("" + a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	public void updateLists() {
		
		managerAvailable.getItems().clear();
		for(int k = 0 ; k<managers.size();k++) {
			managerAvailable.getItems().add(managers.get(k).getName() + " - " + managers.get(k).getFileNb());
		}
	}
	
	
}
