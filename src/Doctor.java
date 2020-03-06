
import java.util.ArrayList;


public class Doctor extends Person {
	private ArrayList<Course> courses;
	private ArrayList <Petition> petitions;
		public Doctor (String name,String email,int fn) {
			super(name,email,fn);
		courses = new ArrayList<Course>();
		petitions = new ArrayList<Petition>();
	}
	
	
	
	public ArrayList<Course> getCourses() {
		return courses;
	}
	public ArrayList<Petition> getPetitions() {
		return petitions;
	}
	
	
	
	
	
}
