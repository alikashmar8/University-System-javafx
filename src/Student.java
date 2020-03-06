
import java.util.ArrayList;

public class Student extends Person {
	private ArrayList<Course>courses;
	private ArrayList <Mark> marks;
	private ArrayList <Petition> petitions;
	public Student (String name,String email ,int fn) {
		super (name,email,fn);
		marks = new ArrayList<Mark>();
		petitions = new ArrayList<Petition>();
		courses = new ArrayList<Course>();
	}
	
	public ArrayList<Mark> getMarks() {
		return marks;
	}	
	public ArrayList<Petition> getPetitions() {
		return petitions;
	}
	
	public void sendPetition(Petition p , Doctor d) {
		d.getPetitions().add(p);
	}
	
	public ArrayList<Course> getCourses() {
		return courses;
	}
	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}



}
