

public class Person {
	private String name,email,username,password;
	private int fileNb;
	public Person(String name,String email,int fn) {
		this.name = name;
		this.email = email;
		fileNb = fn;
		username = fileNb + "";
		password = fileNb + "";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getFileNb() {
		return fileNb;
	}
	public void setFileNb(int fileNb) {
		this.fileNb = fileNb;
	}
	
	
}
