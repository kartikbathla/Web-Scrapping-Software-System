package comp3111.coursescraper;


import java.awt.event.ActionEvent;
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
import java.util.List;
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
    	
    }

    @FXML
    void findInstructorSfq() {
    	List<SFQ> v = scraper.scrapeinstructorSFQ(textfieldSfqUrl.getText());
    	String disp = "X";
    	for (SFQ c : v)
    	{
    		disp = c.getname() + " " + c.getsfq() + "\n";
    	}
    	textAreaConsole.setText(disp);
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
    void search() {
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
