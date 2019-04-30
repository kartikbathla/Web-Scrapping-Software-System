package comp3111.coursescraper;

public class SFQ {
	private double sfq;
	private String name;
	
	public SFQ()	{
		sfq = 0; //  add
		name = null;
	}
	
	public SFQ(SFQ s) {
		this.sfq = s.sfq;
		this.name = s.name;
	}
	
	public void setsfq(double i)	{
		this.sfq = i;
	}
	
	public double getsfq()	{
		return sfq;
	}
	
	public void setname(String s) {
		this.name = s;
	}
	
	public String getname()	{
		return name;
	}
}
