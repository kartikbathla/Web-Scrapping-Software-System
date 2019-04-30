package comp3111.coursescraper;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@SuppressWarnings("unused")
public class Section{
	private static final int MAX_NO_OF_SLOTS = 4;
	private int numSlots;
	private String instructor;
	private Slot [] slots;
	private String sID;
	private String type;
	private boolean enrollment;
	
	public Section()	{
		slots = new Slot[MAX_NO_OF_SLOTS];
		numSlots = 0;
		for (int i = 0; i < MAX_NO_OF_SLOTS; i++) slots[i] = null;
		enrollment = false;
		sID = null;
		type = null;
		instructor = null; //add
	}
	
	
	@Override
	public Section clone() {
		Section s = new Section();
		s.numSlots = this.numSlots;
		for (int i = 0; i<numSlots; i++)
			{ slots[i] = this.slots[i].clone();	}
		s.sID = this.sID;
		s.type = this.type;
		s.enrollment = this.enrollment;
		s.instructor = this.slots[0].getInstructor();
		return s;
	}
	
	/**
	 * @param s ; sets the type of section(L1; T1; LA2)
	 */
	public void setType(String s)	{
		this.type = s;
	}
	
	public String getType()	{
		return type;
	}
	
	public void addSlot(Slot s) {
		if (numSlots >= MAX_NO_OF_SLOTS)
			return;
		slots[numSlots++] = s;
	}
	
	public Slot getSlot(int i)	{
		if (i>=0 && i<numSlots)
			return slots[i];
		return null;
	}

	public void setEnrollment(boolean enroll)	{
		this.enrollment = enroll;
	}

	public boolean getEnrollment()	{
		return enrollment;
	}
	
	public int getnumSlots()	{
		return numSlots;
	}
	
	public void setnumSlots(int slots)	{
		this.numSlots = slots;
	}
		
	public String getsID()	{
		return sID;
	}
	
	public void setsID(String ID)	{
		this.sID = ID;
	}
	
	public String getInstructor()	{
		return instructor;
	}
	
	public void throughslot(Slot s) {
		this.instructor = s.getInstructor();
	}
	
	public void setinstructor(String s)	{
		this.instructor = s;
	}
	
	public void increaseslotby1()	{
		this.numSlots++;
	}
}
