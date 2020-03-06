
import java.util.ArrayList;

public class Course {
	private String name,code;
	private int credits;
	private ArrayList <Student> Students;
	private Doctor tutor;
	public Course (String name,String code,int credits) {
		this.name = name;
		this.setCode(code);
		this.setCredits(credits);
		Students = new ArrayList<Student>();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Student> getStudents() {
		return Students;
	}
	public Doctor getTutor() {
		return tutor;
	}
	public void setTutor(Doctor tutor) {
		this.tutor = tutor;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getCredits() {
		return credits;
	}
	public void setCredits(int credits) {
		this.credits = credits;
	}
	
	
	
	
}
