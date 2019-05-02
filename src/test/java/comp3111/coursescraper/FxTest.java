/**
*
* You might want to uncomment the following code to learn testFX. Sorry, no tutorial session on this.
*
*/

package comp3111.coursescraper;

import static org.junit.Assert.*;

import java.awt.Checkbox;

import org.junit.Test;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;


public class FxTest extends ApplicationTest {

	private Scene s;

	@Override
	public void start(Stage stage) throws Exception {
   	FXMLLoader loader = new FXMLLoader();
   	loader.setLocation(getClass().getResource("/ui.fxml"));
  		VBox root = (VBox) loader.load();
  		Scene scene =  new Scene(root);
  		stage.setScene(scene);
  		stage.setTitle("Course Scraper");
  		stage.show();
  		s = scene;
	}



  @Test
	public void testSearch() {
		clickOn("#tabMain");
		clickOn("#buttonSearch");
		sleep(1000);
		// assertTrue(b.isDisabled());
	}

  @Test
  public void testSelectAll(){
    clickOn("#tabFilter");
    clickOn("#SelectAll");
  //  clickOn("#SelectAll");
    clickOn("#Monday");
    clickOn("#Tuesday");
    clickOn("#Wednesday");
    clickOn("#Thursday");
    clickOn("#Friday");
    clickOn("#Saturday");
    clickOn("#AM");
    clickOn("#PM");
    clickOn("#LabTut");
    clickOn("#NoEx");
    clickOn("#CC");
  }
  @Test
  public void testSelecAll(){
    clickOn("#tabFilter");
    clickOn("#SelectAll");
    clickOn("#SelectAll");
    
  }
  @Test
  public void testSelcAll(){
    clickOn("#tabFilter");
    clickOn("#AM");
    clickOn("#Monday");
    clickOn("#Tuesday");
    clickOn("#Wednesday");
    clickOn("#Thursday");
    clickOn("#Friday");
    clickOn("#Saturday");
    clickOn("#AM");
    clickOn("#PM");
    clickOn("#LabTut");
    clickOn("#NoEx");
    clickOn("#CC");
    
  }
  @Test
  public void tesSelcAll(){
    clickOn("#tabFilter");
    clickOn("#PM");
    clickOn("#Monday");
    clickOn("#Tuesday");
    clickOn("#Wednesday");
    clickOn("#Thursday");
    clickOn("#Friday");
    clickOn("#Saturday");
    clickOn("#AM");
    clickOn("#PM");
    clickOn("#LabTut");
    clickOn("#NoEx");
    clickOn("#CC");
    
  }

}
