package comp3111.coursescraper;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@SuppressWarnings("unused")
public class Section{
	private static final int MAX_NO_OF_SLOTS = 4;
	private int noOfSlots;
	private String instructor;
	private Slot [] slots;
	private String sID;
	private String type;
	private boolean enrollment;
	
	public Section()	{
		slots = new Slot[MAX_NO_OF_SLOTS];
		noOfSlots = 0;
		for (int i = 0; i < MAX_NO_OF_SLOTS; i++) slots[i] = null;
		enrollment = false;
		sID = null;
		type = null;
		instructor = null;
	}
	
	
	@Override
	public Section clone() {
		Section s = new Section();
		s.noOfSlots = this.noOfSlots;
		for (int i = 0; i<noOfSlots; i++)
			this.slots[i] = slots[i].clone();	
		s.sID = this.sID;
		s.type = this.type;
		s.enrollment = this.enrollment;
		s.instructor = this.slots[0].getInstructor();
		return s;
	}
	
	public void setType(String s)	{
		this.type = s;
	}
	
	public String getType()	{
		return type;
	}
	
	public void addSlot(Slot s) {
		if (noOfSlots >= MAX_NO_OF_SLOTS)
			return;
		slots[noOfSlots++] = s.clone();
	}
	
	public Slot getSlot(int i)	{
		if (i>=0 && i<noOfSlots)
			return slots[i];
		return null;
	}

	public void setEnrollment(boolean enroll)	{
		this.enrollment = enroll;
	}

	public boolean getEnrollment()	{
		return enrollment;
	}
	
	public int getNoOfSlots()	{
		return noOfSlots;
	}
	
	public void setNoOfSlots(int slots)	{
		this.noOfSlots = slots;
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
		this.noOfSlots++;
	}
}
