package comp3111.coursescraper;



public class Course {
	private static final int DEFAULT_MAX_SECTIONS= 100;
	
	private String title ; 
	private String description ;
	private String exclusion;
	private Section [] sections;
	private int numSections;
	private boolean commoncore;
	
	public Course() {
		sections = new Section[DEFAULT_MAX_SECTIONS];
		for (int i = 0; i < DEFAULT_MAX_SECTIONS; i++) sections[i] = null;
		numSections = 0;
	}
	
	public void addSection(Section s) {
		if (numSections >= DEFAULT_MAX_SECTIONS)
			return;
		sections[numSections++] = s;
	}
	public Section getSection(int i) {
		if (i >= 0 && i < numSections)
			return sections[i];
		return null;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the exclusion
	 */
	public String getExclusion() {
		return exclusion;
	}

	/**
	 * @param exclusion the exclusion to set
	 */
	public void setExclusion(String exclusion) {
		this.exclusion = exclusion;
	}

	/**
	 * @return the numSlots
	 */
	public int getNumSections() {
		return numSections;
	}

	/**
	 * @param numSlots the numSlots to set
	 */
	public void setNumSlots(int numSections) {
		this.numSections = numSections;
	}
	
	public void setCommonCore(boolean c)	{
		this.commoncore = c;
	}
	
	public boolean getCommonCore()	{
		return commoncore;
	}

}
