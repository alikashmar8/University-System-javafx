

public class Mark {
	private Course c;
	private String name;
	private float partialMark=0,finalMark=0;
	public Mark(Course cr) {
		c = cr;
		setName(cr.getName());
	}
	public Course getC() {
		return c;
	}
	public void setC(Course c) {
		this.c = c;
	}
	public float getPartialMark() {
		return partialMark;
	}
	public void setPartialMark(float partialMark) {
		this.partialMark = partialMark;
	}
	public float getFinalMark() {
		return finalMark;
	}
	public void setFinalMarks(float finalMark) {
		this.finalMark = finalMark;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
