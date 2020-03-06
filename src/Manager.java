public class Manager extends Doctor {
	
	public Manager(String name,String email , String mAge,int fn) {
		super(name,email,fn);
	}
	public Manager(String name,String email ,int fn) {
		super(name,email,fn);
	}
	
	public void assignCourse(Doctor d,Course c ) {
		d.getCourses().add(c);
		c.setTutor(d);
	}
	public void removeCourse(Doctor d,String name) {
		int i;
		for(i=1;i<=d.getCourses().size();i++) {
			if(d.getCourses().get(i).getName().equals(name)) {
				break;
			}
		}
		if(i<=d.getCourses().size()) {
			d.getCourses().remove(i);
	
		}
	}
}
