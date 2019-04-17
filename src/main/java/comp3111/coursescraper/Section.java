package comp3111.coursescraper;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Section{
	private static final int MAX_NO_OF_SLOTS = 4;
	private int noOfSlots;
	private Slot slots[];
	private int sID;
	
	public Section()	{
		slots = new Slot[MAX_NO_OF_SLOTS];
		noOfSlots = 0;
		for (int i = 0; i < MAX_NO_OF_SLOTS; i++) slots[i] = null;
	}
	
	
	@Override
	public Section clone() {
		Section s = new Section();
		s.noOfSlots = this.noOfSlots;
		for (int i = 0; i<noOfSlots; i++)
			this.slots[i] = slots[i].clone();
		s.sID = this.sID;
		return s;
	}
	
	
	public int getNoOfSlots()	{
		return noOfSlots;
	}
	
	public void setNoOfSlots(int slots)	{
		this.noOfSlots = slots;
	}
		
	public int getsID()	{
		return sID;
	}
	
	public void setsID(int ID)	{
		this.sID = ID;
	}
	
//	private Slot[] getslots()	{
//		return slots;
//	}
//	
//	private void setSlots(Slot slot[]) {
//		this.slots[] = slots[];
//	}
	
	
}
