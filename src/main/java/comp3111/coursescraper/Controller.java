package comp3111.coursescraper;

import java.util.*;
import java.util.function.Supplier;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.util.Random;
import java.util.List;

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

	@FXML
	private CheckBox AM;

	@FXML
	private CheckBox PM;

	@FXML
	private CheckBox Monday;

	@FXML
	private CheckBox Tuesday;

	@FXML
	private CheckBox Wednesday;

	@FXML
	private CheckBox Thursday;

	@FXML
	private CheckBox Friday;

	@FXML
	private CheckBox Saturday;

	@FXML
	private Button SelectAll;

	@FXML
	private CheckBox CC;

	@FXML
	private CheckBox NoEx;

	@FXML
	private CheckBox LabTut;

	private Scraper scraper = new Scraper();

	@FXML
	void allSubjectSearch() {

	}

	@FXML
	void findInstructorSfq() {
		buttonInstructorSfq.setDisable(true);
		textAreaConsole.setText(textAreaConsole.getText() + "\n" + textfieldSfqUrl.getText());

	}

	@FXML
	void findSfqEnrollCourse() {
	}

	@FXML
	void selectAll() {
		if (SelectAll.getText().equals("Select All")) {
			SelectAll.setText("De-select All");
			Monday.setSelected(true);
			Tuesday.setSelected(true);
			Wednesday.setSelected(true);
			Thursday.setSelected(true);
			Friday.setSelected(true);
			Saturday.setSelected(true);
			CC.setSelected(true);
			AM.setSelected(true);
			PM.setSelected(true);
			NoEx.setSelected(true);
			LabTut.setSelected(true);
			monday();
			tuesday();
			wednesday();
			thursday();
			friday();
			saturday();
			AMT();
			PMT();

		}

		else {
			SelectAll.setText("Select All");
			Monday.setSelected(false);
			Tuesday.setSelected(false);
			Wednesday.setSelected(false);
			Thursday.setSelected(false);
			Friday.setSelected(false);
			Saturday.setSelected(false);
			CC.setSelected(false);
			AM.setSelected(false);
			PM.setSelected(false);
			NoEx.setSelected(false);
			LabTut.setSelected(false);
			textAreaConsole.setText("");
		}

	}

	@FXML
	void AMT() {
		int mflag = -1;
		int tflag = -1;
		int wflag = -1;
		int thflag = -1;
		int fflag = -1;
		int sflag = -1;
		int endtime = 0;

		if (Monday.isSelected())
			tflag = 0;
		if (Tuesday.isSelected())
			tflag = 1;
		if (Wednesday.isSelected())
			wflag = 2;
		if (Thursday.isSelected())
			thflag = 3;
		if (Friday.isSelected())
			fflag = 4;
		if (Saturday.isSelected())
			sflag = 5;
		if (AM.isSelected()) {

			List<Course> nvv = new ArrayList<Course>();
			List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
					textfieldSubject.getText());

			if (!(PM.isSelected())) {

				for (Course c : v) {
					int timeflag = 0;
					int dayflag = 0;
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getStartHour() < 12 ) {
//							if (!nvv.contains(c)) {
//								nvv.add(c);
							timeflag = 1;
							}
						if (t.getDay() == tflag || t.getDay() == wflag || t.getDay() == thflag
								|| t.getDay() == fflag || t.getDay() == sflag || t.getDay() == mflag) {
							dayflag= 1;
						}

						}//scans all slots one by one
					if (dayflag==1 && timeflag ==1) { //course needs to have slots both am and monday
						if (!nvv.contains(c)) {
							nvv.add(c);
					}
					}
				}
			}
			if (PM.isSelected()) {
				for (Course c : v) {
					int timeflag = 0;
					int dayflag = 0;

						for (int i = 0; i < c.getNumSlots(); i++) {
							Slot t = c.getSlot(i);
							if (t.getStartHour() < 12 && t.getEndHour()>=12) {
//								if (!nvv.contains(c)) {
//									nvv.add(c);
								timeflag = 1;
								}
							if (t.getDay() == tflag || t.getDay() == wflag || t.getDay() == thflag
									|| t.getDay() == fflag || t.getDay() == sflag || t.getDay() == mflag) {
								dayflag= 1;
							}

							}//scans all slots one by one
						if (dayflag==1 && timeflag ==1) { //course needs to have slots both am and monday
							if (!nvv.contains(c)) {
								nvv.add(c);
						}
						
					}
				}
			}
			textAreaConsole.setText("");
			for (Course c : nvv) {
				String newline = c.getTitle() + "\n";
				for (int i = 0; i < c.getNumSlots(); i++) {
					Slot t = c.getSlot(i);
					newline += "Slot " + i + ":" + t + "\n";
				}
				textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
			}

		}
		if (!AM.isSelected()) {
			List<Course> nvv = new ArrayList<Course>();
			List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
					textfieldSubject.getText());

			if (!(PM.isSelected())) {
				for (Course c : v) {
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getDay() == tflag || t.getDay() == wflag || t.getDay() == thflag || t.getDay() == fflag
								|| t.getDay() == sflag || t.getDay() == mflag) {
							if (!nvv.contains(c)) {
								nvv.add(c);
							}
						}
					}
				}

			}
			if (PM.isSelected()) {
				for (Course c : v) {
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if ((t.getEndHour() >= 12) || t.getDay() == tflag || t.getDay() == wflag || t.getDay() == thflag
								|| t.getDay() == fflag || t.getDay() == sflag || t.getDay() == mflag) {
							if (!nvv.contains(c)) {
								nvv.add(c);
							}
						}
					}
				}

			}
			textAreaConsole.setText("");
			for (Course c : nvv) {
				String newline = c.getTitle() + "\n";
				for (int i = 0; i < c.getNumSlots(); i++) {
					Slot t = c.getSlot(i);
					newline += "Slot " + i + ":" + t + "\n";
				}
				textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
			}
		}

	}

	@FXML
	void PMT() {
		int mflag = -1;
		int tflag = -1;
		int wflag = -1;
		int thflag = -1;
		int fflag = -1;
		int sflag = -1;
		int endtime = 0;
		if (Monday.isSelected())
			tflag = 0;
		if (Tuesday.isSelected())
			tflag = 1;
		if (Wednesday.isSelected())
			wflag = 2;
		if (Thursday.isSelected())
			thflag = 3;
		if (Friday.isSelected())
			fflag = 4;
		if (Saturday.isSelected())
			sflag = 5;
		if (PM.isSelected()) {

			List<Course> nvv = new ArrayList<Course>();
			List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
					textfieldSubject.getText());

			if (!(AM.isSelected())) {

				for (Course c : v) {
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getEndHour() >= 12 && t.getDay() == tflag && t.getDay() == wflag && t.getDay() == thflag
								&& t.getDay() == fflag && t.getDay() == sflag && t.getDay() == mflag) {
							if (!nvv.contains(c)) {
								nvv.add(c);
							}
						}
					}
				}

			}
			if (AM.isSelected()) {
				for (Course c : v) {
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if ((t.getStartHour() < 12 && t.getEndHour() >= 12) || t.getDay() == tflag
								|| t.getDay() == wflag || t.getDay() == thflag || t.getDay() == fflag
								|| t.getDay() == sflag || t.getDay() == mflag) {
							if (!nvv.contains(c)) {
								nvv.add(c);
							}
						}
					}
				}
			}
			textAreaConsole.setText("");
			for (Course c : nvv) {
				String newline = c.getTitle() + "\n";
				for (int i = 0; i < c.getNumSlots(); i++) {
					Slot t = c.getSlot(i);
					newline += "Slot " + i + ":" + t + "\n";
				}
				textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
			}

		}
		if (!PM.isSelected()) {
			List<Course> nvv = new ArrayList<Course>();
			List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
					textfieldSubject.getText());

			if (!(AM.isSelected())) {
				for (Course c : v) {
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getDay() == tflag || t.getDay() == wflag || t.getDay() == thflag || t.getDay() == fflag
								|| t.getDay() == sflag || t.getDay() == mflag) {
							if (!nvv.contains(c)) {
								nvv.add(c);
							}
						}
					}
				}

			}
			if (AM.isSelected()) {
				for (Course c : v) {
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if ((t.getStartHour() < 12) || t.getDay() == tflag || t.getDay() == wflag
								|| t.getDay() == thflag || t.getDay() == fflag || t.getDay() == sflag
								|| t.getDay() == mflag) {
							if (!nvv.contains(c)) {
								nvv.add(c);
							}
						}
					}
				}

			}
			textAreaConsole.setText("");
			for (Course c : nvv) {
				String newline = c.getTitle() + "\n";
				for (int i = 0; i < c.getNumSlots(); i++) {
					Slot t = c.getSlot(i);
					newline += "Slot " + i + ":" + t + "\n";
				}
				textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
			}
		}
	}

	@FXML
	void monday() {

		
		if (Monday.isSelected()) {
			int tflag = 0;
			int wflag = 0;
			int thflag = 0;
			int fflag = 0;
			int sflag = 0;
			int stime= 100;
			int etime= 0;
			if (Tuesday.isSelected())
				tflag = 1;
			if (Wednesday.isSelected())
				wflag = 2;
			if (Thursday.isSelected())
				thflag = 3;
			if (Friday.isSelected())
				fflag = 4;
			if (Saturday.isSelected())
				sflag = 5;
			if(AM.isSelected())
				stime= 12;
			if(PM.isSelected())
				etime= 12;

			List<Course> nvv = new ArrayList<Course>();
			List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
					textfieldSubject.getText());

			for (Course c : v) {
				for (int i = 0; i < c.getNumSlots(); i++) {
					Slot t = c.getSlot(i);
					if (t.getDay() == 0 || t.getDay() == tflag || t.getDay() == wflag || t.getDay() == thflag
							|| t.getDay() == fflag || t.getDay() == sflag || (t.getStartHour()< stime && t.getEndHour()>=etime)) {

						if (!nvv.contains(c)) {
							nvv.add(c);

						}
					}
				}
			}
			textAreaConsole.setText("");
			for (Course c : nvv) {
				String newline = c.getTitle() + "\n";
				for (int i = 0; i < c.getNumSlots(); i++) {
					Slot t = c.getSlot(i);
					newline += "Slot " + i + ":" + t + "\n";
				}
				textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
			}
		} else if (!(Monday.isSelected())) {
			int tflag = -1;
			int wflag = -1;
			int thflag = -1;
			int fflag = -1;
			int sflag = -1;
			int stime= 100;
			int etime= 0;
			if (Tuesday.isSelected())
				tflag = 1;
			if (Wednesday.isSelected())
				wflag = 2;
			if (Thursday.isSelected())
				thflag = 3;
			if (Friday.isSelected())
				fflag = 4;
			if (Saturday.isSelected())
				sflag = 5;
			if(AM.isSelected())
				stime= 12;
			if(PM.isSelected())
				etime= 12;
			List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
					textfieldSubject.getText());
			List<Course> nv = new ArrayList<Course>();
	
			for (Course c : v) {
				String newline = c.getTitle() + "\n";
				for (int i = 0; i < c.getNumSlots(); i++) {
					Slot t = c.getSlot(i);
					if ( t.getDay() == tflag || t.getDay() == wflag || t.getDay() == thflag
							|| t.getDay() == fflag || t.getDay() == sflag ||((t.getStartHour()< stime && t.getEndHour()>=etime))) {
						if (!nv.contains(c)) {
							nv.add(c);
							
						}
					}
				}
			}
			
			textAreaConsole.setText("");
			for (Course c : nv) {
				String newline = c.getTitle() + "\n";
				for (int i = 0; i < c.getNumSlots(); i++) {
					Slot t = c.getSlot(i);
					newline += "Slot " + i + ":" + t + "\n";
				}
				textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
			}
		}
	}

	@FXML
	void tuesday() {
		int mflag = 1;
		int wflag = 1;
		int thflag = 1;
		int fflag = 1;
		int sflag = 1;
		if (Monday.isSelected())
			mflag = 0;
		if (Wednesday.isSelected())
			wflag = 2;
		if (Thursday.isSelected())
			thflag = 3;
		if (Friday.isSelected())
			fflag = 4;
		if (Saturday.isSelected())
			sflag = 5;
		if (Tuesday.isSelected()) {
			List<Course> nvv = new ArrayList<Course>();
			List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
					textfieldSubject.getText());

			for (Course c : v) {
				for (int i = 0; i < c.getNumSlots(); i++) {
					Slot t = c.getSlot(i);
					if (t.getDay() == 1 || t.getDay() == mflag || t.getDay() == wflag || t.getDay() == thflag
							|| t.getDay() == fflag || t.getDay() == sflag) {

						if (!nvv.contains(c)) {
							nvv.add(c);
						}
					}
				}
			}

			textAreaConsole.setText("");
			for (Course c : nvv) {
				String newline = c.getTitle() + "\n";
				for (int i = 0; i < c.getNumSlots(); i++) {
					Slot t = c.getSlot(i);
					newline += "Slot " + i + ":" + t + "\n";
				}
				textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
			}

		}

		else if (!(Tuesday.isSelected())) {
			List<Course> nv = new ArrayList<Course>();
			int count = 0;
			textAreaConsole.setText("");
			if (Monday.isSelected()) {
				List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
						textfieldSubject.getText());
				for (Course c : v) {
					String newline = c.getTitle() + "\n";
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getDay() == 0) {
							if (!nv.contains(c)) {
								nv.add(c);
								count++;
							}
						}
					}
				}
			}
			if (Wednesday.isSelected()) {
				List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
						textfieldSubject.getText());
				for (Course c : v) {
					String newline = c.getTitle() + "\n";
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getDay() == 2) {
							if (!nv.contains(c)) {
								nv.add(c);
								count++;
							}
						}
					}
				}
			}
			if (Thursday.isSelected()) {
				List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
						textfieldSubject.getText());
				for (Course c : v) {
					String newline = c.getTitle() + "\n";
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getDay() == 3) {
							if (!nv.contains(c)) {
								nv.add(c);
								count++;
							}
						}
					}
				}
			}
			if (Friday.isSelected()) {
				List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
						textfieldSubject.getText());
				for (Course c : v) {
					String newline = c.getTitle() + "\n";
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getDay() == 4) {
							if (!nv.contains(c)) {
								nv.add(c);
								count++;
							}
						}
					}
				}
			}
			if (Saturday.isSelected()) {
				List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
						textfieldSubject.getText());
				for (Course c : v) {
					String newline = c.getTitle() + "\n";
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getDay() == 5) {
							if (!nv.contains(c)) {
								nv.add(c);
								count++;
							}
						}
					}
				}
			}
			textAreaConsole.setText("");
			for (Course c : nv) {

				String newline = c.getTitle() + "\n";
				for (int i = 0; i < c.getNumSlots(); i++) {
					Slot t = c.getSlot(i);
					newline += "Slot " + i + ":" + t + "\n";
				}
				textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
			}
		}
	}

	@FXML
	void wednesday() {
		int mflag = 2;
		int tflag = 2;
		int thflag = 2;
		int fflag = 2;
		int sflag = 2;
		if (Monday.isSelected())
			mflag = 0;
		if (Tuesday.isSelected())
			tflag = 1;
		if (Thursday.isSelected())
			thflag = 3;
		if (Friday.isSelected())
			fflag = 4;
		if (Saturday.isSelected())
			sflag = 5;
		if (Wednesday.isSelected()) {
			List<Course> nvv = new ArrayList<Course>();
			List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
					textfieldSubject.getText());

			for (Course c : v) {
				for (int i = 0; i < c.getNumSlots(); i++) {
					Slot t = c.getSlot(i);
					if (t.getDay() == 2 || t.getDay() == mflag || t.getDay() == tflag || t.getDay() == thflag
							|| t.getDay() == fflag || t.getDay() == sflag) {
						if (!nvv.contains(c)) {
							nvv.add(c);
						}
					}
				}
			}

			textAreaConsole.setText("");
			for (Course c : nvv) {
				String newline = c.getTitle() + "\n";
				for (int i = 0; i < c.getNumSlots(); i++) {
					Slot t = c.getSlot(i);
					newline += "Slot " + i + ":" + t + "\n";
				}
				textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
			}
		} else if (!(Wednesday.isSelected())) {
			List<Course> nv = new ArrayList<Course>();
			textAreaConsole.setText("");

			if (Tuesday.isSelected()) {
				List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
						textfieldSubject.getText());
				for (Course c : v) {
					String newline = c.getTitle() + "\n";
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getDay() == 1) {
							if (!nv.contains(c)) {
								nv.add(c);

							}
						}
					}
				}
			}
			if (Monday.isSelected()) {
				List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
						textfieldSubject.getText());
				for (Course c : v) {
					String newline = c.getTitle() + "\n";
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getDay() == 0) {
							if (!nv.contains(c)) {
								nv.add(c);

							}

						}
					}
				}
			}
			if (Thursday.isSelected()) {
				List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
						textfieldSubject.getText());
				for (Course c : v) {
					String newline = c.getTitle() + "\n";
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getDay() == 5) {
							if (t.getDay() == 3) {
								if (!nv.contains(c)) {
									nv.add(c);

								}
							}
						}
					}
				}
			}
			if (Friday.isSelected()) {
				List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
						textfieldSubject.getText());
				for (Course c : v) {
					String newline = c.getTitle() + "\n";
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getDay() == 4) {
							if (!nv.contains(c)) {
								nv.add(c);

							}
						}
					}
				}
			}
			if (Saturday.isSelected()) {
				List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
						textfieldSubject.getText());
				for (Course c : v) {
					String newline = c.getTitle() + "\n";
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getDay() == 5) {
							if (!nv.contains(c)) {
								nv.add(c);

							}
						}
					}
				}
			}
			textAreaConsole.setText("");
			for (Course c : nv) {

				String newline = c.getTitle() + "\n";
				for (int i = 0; i < c.getNumSlots(); i++) {
					Slot t = c.getSlot(i);
					newline += "Slot " + i + ":" + t + "\n";
				}
				textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
			}

		}
	}

	@FXML
	void thursday() {
		int mflag = 3;
		int tflag = 3;
		int wflag = 3;
		int fflag = 3;
		int sflag = 3;
		if (Monday.isSelected())
			mflag = 0;
		if (Tuesday.isSelected())
			tflag = 1;
		if (Wednesday.isSelected())
			wflag = 2;
		if (Friday.isSelected())
			fflag = 4;
		if (Saturday.isSelected())
			sflag = 5;
		if (Thursday.isSelected()) {
			List<Course> nvv = new ArrayList<Course>();
			List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
					textfieldSubject.getText());

			for (Course c : v) {
				for (int i = 0; i < c.getNumSlots(); i++) {
					Slot t = c.getSlot(i);
					if (t.getDay() == 3 || t.getDay() == mflag || t.getDay() == tflag || t.getDay() == wflag
							|| t.getDay() == fflag || t.getDay() == sflag) {
						if (!nvv.contains(c)) {
							nvv.add(c);
						}
					}
				}
			}

			textAreaConsole.setText("");
			for (Course c : nvv) {
				String newline = c.getTitle() + "\n";
				for (int i = 0; i < c.getNumSlots(); i++) {
					Slot t = c.getSlot(i);
					newline += "Slot " + i + ":" + t + "\n";
				}
				textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
			}
		} else if (!(Thursday.isSelected())) {
			List<Course> nv = new ArrayList<Course>();
			textAreaConsole.setText("");

			if (Tuesday.isSelected()) {
				List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
						textfieldSubject.getText());
				for (Course c : v) {
					String newline = c.getTitle() + "\n";
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getDay() == 1) {
							if (!nv.contains(c)) {
								nv.add(c);

							}
						}
					}
				}
			}
			if (Monday.isSelected()) {
				List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
						textfieldSubject.getText());
				for (Course c : v) {
					String newline = c.getTitle() + "\n";
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getDay() == 0) {
							if (!nv.contains(c)) {
								nv.add(c);

							}

						}
					}
				}
			}
			if (Wednesday.isSelected()) {
				List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
						textfieldSubject.getText());
				for (Course c : v) {
					String newline = c.getTitle() + "\n";
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getDay() == 5) {
							if (t.getDay() == 2) {
								if (!nv.contains(c)) {
									nv.add(c);

								}
							}
						}
					}
				}
			}
			if (Friday.isSelected()) {
				List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
						textfieldSubject.getText());
				for (Course c : v) {
					String newline = c.getTitle() + "\n";
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getDay() == 4) {
							if (!nv.contains(c)) {
								nv.add(c);

							}
						}
					}
				}
			}
			if (Saturday.isSelected()) {
				List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
						textfieldSubject.getText());
				for (Course c : v) {
					String newline = c.getTitle() + "\n";
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getDay() == 5) {
							if (!nv.contains(c)) {
								nv.add(c);

							}
						}
					}
				}
			}
			textAreaConsole.setText("");
			for (Course c : nv) {

				String newline = c.getTitle() + "\n";
				for (int i = 0; i < c.getNumSlots(); i++) {
					Slot t = c.getSlot(i);
					newline += "Slot " + i + ":" + t + "\n";
				}
				textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
			}

		}
	}

	@FXML
	void friday() {
		int mflag = 4;
		int tflag = 4;
		int wflag = 4;
		int thflag = 4;
		int sflag = 4;
		if (Monday.isSelected())
			mflag = 0;
		if (Tuesday.isSelected())
			tflag = 1;
		if (Wednesday.isSelected())
			wflag = 2;
		if (Thursday.isSelected())
			thflag = 3;
		if (Saturday.isSelected())
			sflag = 5;
		if (Friday.isSelected()) {
			List<Course> nvv = new ArrayList<Course>();
			List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
					textfieldSubject.getText());

			for (Course c : v) {
				for (int i = 0; i < c.getNumSlots(); i++) {
					Slot t = c.getSlot(i);
					if (t.getDay() == 4 || t.getDay() == mflag || t.getDay() == tflag || t.getDay() == wflag
							|| t.getDay() == thflag || t.getDay() == sflag) {
						if (!nvv.contains(c)) {
							nvv.add(c);
						}
					}
				}
			}

			textAreaConsole.setText("");
			for (Course c : nvv) {
				String newline = c.getTitle() + "\n";
				for (int i = 0; i < c.getNumSlots(); i++) {
					Slot t = c.getSlot(i);
					newline += "Slot " + i + ":" + t + "\n";
				}
				textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
			}
		} else if (!(Friday.isSelected())) {
			List<Course> nv = new ArrayList<Course>();
			textAreaConsole.setText("");

			if (Tuesday.isSelected()) {
				List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
						textfieldSubject.getText());
				for (Course c : v) {
					String newline = c.getTitle() + "\n";
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getDay() == 1) {
							if (!nv.contains(c)) {
								nv.add(c);

							}
						}
					}
				}
			}
			if (Monday.isSelected()) {
				List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
						textfieldSubject.getText());
				for (Course c : v) {
					String newline = c.getTitle() + "\n";
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getDay() == 0) {
							if (!nv.contains(c)) {
								nv.add(c);

							}

						}
					}
				}
			}
			if (Wednesday.isSelected()) {
				List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
						textfieldSubject.getText());
				for (Course c : v) {
					String newline = c.getTitle() + "\n";
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getDay() == 5) {
							if (t.getDay() == 2) {
								if (!nv.contains(c)) {
									nv.add(c);

								}
							}
						}
					}
				}
			}
			if (Thursday.isSelected()) {
				List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
						textfieldSubject.getText());
				for (Course c : v) {
					String newline = c.getTitle() + "\n";
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getDay() == 3) {
							if (!nv.contains(c)) {
								nv.add(c);

							}
						}
					}
				}
			}
			if (Saturday.isSelected()) {
				List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
						textfieldSubject.getText());
				for (Course c : v) {
					String newline = c.getTitle() + "\n";
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getDay() == 5) {
							if (!nv.contains(c)) {
								nv.add(c);

							}
						}
					}
				}
			}
			textAreaConsole.setText("");
			for (Course c : nv) {

				String newline = c.getTitle() + "\n";
				for (int i = 0; i < c.getNumSlots(); i++) {
					Slot t = c.getSlot(i);
					newline += "Slot " + i + ":" + t + "\n";
				}
				textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
			}
		}
	}

	@FXML
	void saturday() {
		int mflag = 5;
		int tflag = 5;
		int wflag = 5;
		int thflag = 5;
		int fflag = 5;
		if (Monday.isSelected())
			mflag = 0;
		if (Tuesday.isSelected())
			tflag = 1;
		if (Wednesday.isSelected())
			wflag = 2;
		if (Thursday.isSelected())
			thflag = 3;
		if (Friday.isSelected())
			fflag = 4;
		if (Saturday.isSelected()) {
			List<Course> nvv = new ArrayList<Course>();
			List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
					textfieldSubject.getText());

			for (Course c : v) {
				for (int i = 0; i < c.getNumSlots(); i++) {
					Slot t = c.getSlot(i);
					if (t.getDay() == 5 || t.getDay() == mflag || t.getDay() == tflag || t.getDay() == wflag
							|| t.getDay() == thflag || t.getDay() == fflag) {
						if (!nvv.contains(c)) {
							nvv.add(c);
						}
					}
				}
			}

			textAreaConsole.setText("");
			for (Course c : nvv) {
				String newline = c.getTitle() + "\n";
				for (int i = 0; i < c.getNumSlots(); i++) {
					Slot t = c.getSlot(i);
					newline += "Slot " + i + ":" + t + "\n";
				}
				textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
			}
		} else if (!(Saturday.isSelected())) {
			List<Course> nv = new ArrayList<Course>();
			textAreaConsole.setText("");

			if (Tuesday.isSelected()) {
				List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
						textfieldSubject.getText());
				for (Course c : v) {
					String newline = c.getTitle() + "\n";
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getDay() == 1) {
							if (!nv.contains(c)) {
								nv.add(c);

							}
						}
					}
				}
			}
			if (Monday.isSelected()) {
				List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
						textfieldSubject.getText());
				for (Course c : v) {
					String newline = c.getTitle() + "\n";
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getDay() == 0) {
							if (!nv.contains(c)) {
								nv.add(c);

							}

						}
					}
				}
			}
			if (Wednesday.isSelected()) {
				List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
						textfieldSubject.getText());
				for (Course c : v) {
					String newline = c.getTitle() + "\n";
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getDay() == 5) {
							if (t.getDay() == 2) {
								if (!nv.contains(c)) {
									nv.add(c);

								}
							}
						}
					}
				}
			}
			if (Thursday.isSelected()) {
				List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
						textfieldSubject.getText());
				for (Course c : v) {
					String newline = c.getTitle() + "\n";
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getDay() == 3) {
							if (!nv.contains(c)) {
								nv.add(c);

							}
						}
					}
				}
			}
			if (Friday.isSelected()) {
				List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),
						textfieldSubject.getText());
				for (Course c : v) {
					String newline = c.getTitle() + "\n";
					for (int i = 0; i < c.getNumSlots(); i++) {
						Slot t = c.getSlot(i);
						if (t.getDay() == 4) {
							if (!nv.contains(c)) {
								nv.add(c);

							}
						}
					}
				}
			}
			textAreaConsole.setText("");
			for (Course c : nv) {

				String newline = c.getTitle() + "\n";
				for (int i = 0; i < c.getNumSlots(); i++) {
					Slot t = c.getSlot(i);
					newline += "Slot " + i + ":" + t + "\n";
				}
				textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
			}

		}
	}

	@FXML
	void search() {
		List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(), textfieldSubject.getText());
		for (Course c : v) {
			String newline = c.getTitle() + "\n";
			for (int i = 0; i < c.getNumSections(); i++) {
				Section t = c.getSection(i);
				newline += "Slot " + i + ":" + t + "\n";
			}
			textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
		}

		// Add a random block on Saturday
		AnchorPane ap = (AnchorPane) tabTimetable.getContent();
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

	}
	
	

}
// practice committing
