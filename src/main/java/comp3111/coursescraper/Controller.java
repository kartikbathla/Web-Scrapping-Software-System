package comp3111.coursescraper;


import java.awt.event.ActionEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;

import java.util.Random;
import java.util.Set;
import java.util.Vector;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
/**
 * @author Administrator
 *
 */
public class Controller {
	
	

    @FXML
    private Tab tabMain;

    @FXML
    private TextField textfieldTerm;

    @FXML
    private TextField textfieldSubject;

    @FXML
    private Button buttonSearch;

    @FXML
    private TextField textfieldURL;

    @FXML
    private Tab tabStatistic;

    @FXML
    private ComboBox<?> comboboxTimeSlot;

    @FXML
    private Tab tabFilter;

    @FXML
    private Tab tabList;

    @FXML
    private Tab tabTimetable;

    @FXML
    private Tab tabAllSubject;

    @FXML
    private ProgressBar progressbar;

    @FXML
    private TextField textfieldSfqUrl;

    @FXML
    private Button buttonSfqEnrollCourse;

    @FXML
    private Button buttonInstructorSfq;
  
    @FXML
    private TextArea textAreaConsole;
    
    private Scraper scraper = new Scraper();
   
    
    @FXML
    void allSubjectSearch() {
    	buttonSfqEnrollCourse.setDisable(false);

    	
    }

    

    @FXML
    void findSfqEnrollCourse() {
    	   	
    }

    @FXML
    void selectAll()  {}
    @FXML
    void AMT()  {}
    @FXML
    void PMT()  {}
    @FXML
    void monday()  {}
    @FXML
    void tuesday()  {}
    @FXML
    void wednesday()  {}
    @FXML
    void thursday()  {}
    @FXML
    void friday()  {}
    @FXML
    void saturday()  {}
    
    
    
    @FXML
    void findInstructorSfq() {
    	Vector<SFQ> v = scraper.scrapeinstructorSFQ(textfieldSfqUrl.getText());
    	String disp = "";
//    	for (int c = 0; c<v.size(); c++)
//    	{
//    		SFQ basis = v.get(c); String name = basis.getname();
//    		for (int d = c+1; d<(v.size()-c); d++)
//    		{
//    			SFQ compare = v.get(d); String cname = compare.getname();
//    			if (name == cname)
//    			{
//    				 
//    				
//    			}
//    		}
//    	}
    	for (int i = 0; i < v.size(); i++)
    	{
    		SFQ c = v.get(i);
    		disp += c.getname() + " " + c.getsfq() + "\n";
    	}
    	textAreaConsole.setText(disp);
    }
    
    
    public void initialize()
    {
    	buttonSfqEnrollCourse.setDisable(true);
    }
    
    @FXML
    void search() {
    	buttonSfqEnrollCourse.setDisable(false);
    	try {
    	textAreaConsole.setText(" ");
    	List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),textfieldSubject.getText());
    	for (Course c : v) {
    		String newline = c.getTitle() + "\n";
    		for (int i = 0; i < c.getNumSections(); i++)
    		{
    			Section t = c.getSection(i);
    			for (int j = 0; j < t.getnumSlots(); j++)
    			{
    				Slot x = t.getSlot(j);
    				newline += t.getType() + " " + t.getsID() + " : " + x + "\n";
    			}
    		}
    		textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
    	}
    	
    	int totalsections = scraper.sections(textfieldURL.getText(), textfieldTerm.getText(),textfieldSubject.getText());
//    	for (Course c : v)
//    	{
//    		int x = c.getNumSections();
//    		totalsections += x;
//    	}
    	textAreaConsole.setText(textAreaConsole.getText() + "\n" + "TOTAL NUMBER OF DIFFERENT SECTIONS: " + totalsections );
    	
    	int totalvalidcourses = scraper.course(textfieldURL.getText(), textfieldTerm.getText(),textfieldSubject.getText());;
//    	for (Course c : v)	{
//    		if (c.getNumSections()!=0)
//    			totalvalidcourses++;
//    	}
    	textAreaConsole.setText(textAreaConsole.getText() + "\n" + "TOTAL NUMBER OF COURSES: " + totalvalidcourses);
    	
    	Vector<String> instructors = new Vector<String>(); Vector<String> reject = new Vector<String>();
    	String timeb = "03:10PM";
		LocalTime boogey = LocalTime.parse(timeb, DateTimeFormatter.ofPattern("hh:mma", Locale.US));
    	for (Course c : v)
    	{
    		for (int i = 0; i<c.getNumSections(); i++)
    		{
    			Section x = c.getSection(i);
    			for (int j = 0; j<x.getnumSlots(); j++)
    			{
    				Slot y = x.getSlot(j);
    				if (y.getStart().isBefore(boogey) && y.getEnd().isAfter(boogey) && y.getDay() == 1)
    				{
    					String boo = y.getInstructor();
    					reject.add(boo);
    				}
    			}
    			for (int j = 0; j<x.getnumSlots(); j++)
    			{	
    				Slot y = x.getSlot(j);
    				String name = y.getInstructor(); 
    				if (!instructors.contains(name) && !name.equals("TBA"))
    				{
    					instructors.add(name);
    				}	
    			}	
    		}
    	}
    	Vector<String> finallist = new Vector<String>();
    	for (int i = 0; i<instructors.size(); i++)
    	{
    		String temp = instructors.get(i);
    		if (reject.contains(temp))
    			continue;
    		finallist.add(temp);
    	}
    	Collections.sort(finallist); 
    	String line = "";
    	for (int i = 0; i<finallist.size(); i++)
    	{
    		line += finallist.get(i) + " "+ i + " " + "\n";
    	}
    	textAreaConsole.setText(textAreaConsole.getText() + "\n" + "INSTRUCTORS WHO DO NOT HAVE A TEACHING ASSIGNMENT AT 3:10PM ON TUESDAY " + "\n" + line);

    	
    	
    	//Add a random block on Saturday
    	AnchorPane ap = (AnchorPane)tabTimetable.getContent();
    	Label randomLabel = new Label("COMP1022\nL1");
    	Random r = new Random();
    	double start = (r.nextInt(10) + 1) * 20 + 40;


    	randomLabel.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
    	randomLabel.setLayoutX(600.0);
    	randomLabel.setLayoutY(start);
    	randomLabel.setMinWidth(100.0);
    	randomLabel.setMaxWidth(100.0);
    	randomLabel.setMinHeight(60);
    	randomLabel.setMaxHeight(60);
    
    	ap.getChildren().addAll(randomLabel);
    	} catch(Exception e)	{
    		String error = "Error 404: Page not Found. The Requested URL was not found.";
    		textAreaConsole.setText(error);
    	}
    	
    	
    }
}
