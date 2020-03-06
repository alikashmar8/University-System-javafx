

public class Petition {

	private Course c;
	private Student s;
	private String note;
	private String code;
	private int fileNb;
	private String name;
	private boolean answer ;
	
	public Petition(Course c, String note) {
		this.c = c;
		this.note = note;
		setCode(c.getCode());
		setName(c.getName());
	}
	public Petition(Course c) {
		this.c = c;
	}
	public boolean getAnswer() {
		return answer;
	}
	public void setAnswer(boolean answer) {
		this.answer = answer;
	}
	public Course getC() {
		return c;
	}
	public String getNote() {
		return note;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Student getS() {
		return s;
	}
	public void setS(Student s) {
		this.s = s;
		setFileNb(s.getFileNb());
	}
	public int getFileNb() {
		return fileNb;
	}
	public void setFileNb(int i) {
		this.fileNb = i;
	}
	
	
	
	
	
}
