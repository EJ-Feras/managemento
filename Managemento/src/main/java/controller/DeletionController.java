package controller;


import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import facades.ServiceFacade;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Meeting;
import model.Project;
import model.Sprint;
import model.Student;
import model.URL;


/**
 * Steuert den Löschbestätigungsdialog.
 * 
 * @author Feras
 *
 */
public class DeletionController implements Initializable {

	@FXML
	DialogPane dialogPane;
	@FXML
	Label msgText;
	@FXML
	JFXButton yes;
	@FXML
	JFXButton no;

	private ObservableList<Project> projects;
	private ObservableList<Sprint> sprints;
	private ObservableList<Student> students;
	private ObservableList<URL> urls;
	private ObservableList<Meeting> reminders;

	private boolean deleteProject;
	private boolean deleteSprint;
	private boolean deleteStudent;
	private boolean deleteUrl;
	private boolean deleteReminder;

	public void setProjectsList(ObservableList<Project> projects) {

		this.projects = projects;

		deleteProject = true;
		deleteSprint = false;
		deleteStudent = false;
		deleteUrl = false;
		deleteReminder = false;

		if (projects.size() == 1)
			msgText.setText("  Do you want to delete this Project ?");
		else
			msgText.setText("  Do you want to delete these Projects ?");
	}

	public void setSprintsList(ObservableList<Sprint> sprints) {

		this.sprints = sprints;

		deleteSprint = true;
		deleteProject = false;
		deleteStudent = false;
		deleteUrl = false;
		deleteReminder = false;

		if (sprints.size() == 1)
			msgText.setText("  Do you want to delete this Sprint ?");
		else
			msgText.setText("  Do you want to delete these Sprints ?");
	}

	public void setStudentList(ObservableList<Student> students) {

		this.students = students;

		deleteStudent = true;
		deleteSprint = false;
		deleteProject = false;
		deleteUrl = false;
		deleteReminder = false;

		if (students.size() == 1)
			msgText.setText("  Do you want to delete this Student ?");
		else
			msgText.setText("  Do you want to delete these Students ?");
	}

	public void setURLsList(ObservableList<URL> urls) {
		this.urls = urls;

		deleteUrl = true;
		deleteStudent = false;
		deleteSprint = false;
		deleteProject = false;
		deleteReminder = false;

		if (urls.size() == 1)
			msgText.setText("	Do you want to delete this url ?");
		else
			msgText.setText("   Do you want to delete these urls ?");

	}

	public void setRemindersList(ObservableList<Meeting> reminders) {
		this.reminders = reminders;

		deleteReminder = true;
		deleteUrl = false;
		deleteStudent = false;
		deleteSprint = false;
		deleteProject = false;

		if (reminders.size() == 1)
			msgText.setText("   Do you want to delete this reminder ?");
		else
			msgText.setText("   Do you want to delete these reminders ?");

	}

	@FXML
	void delete(ActionEvent event) {

		delete();

		final Node source = (Node) event.getSource();
		final Stage stage = (Stage) source.getScene().getWindow();
		stage.close();

	}

	@FXML
	void cancel(ActionEvent event) {
		final Node source = (Node) event.getSource();
		final Stage stage = (Stage) source.getScene().getWindow();
		stage.close();

	}

	private void delete() {

		if (deleteProject)
			projects.stream().forEach(p -> ServiceFacade.deleteProject(p));
		else if (deleteSprint)
			sprints.stream().forEach(s -> ServiceFacade.deleteSprint(s));
		else if (deleteStudent)
			students.stream().forEach(student -> ServiceFacade.deleteStudent(student));
		else if (deleteUrl)
			urls.stream().forEach(url -> ServiceFacade.deleteURL(url));
		else if (deleteReminder)
			reminders.stream().forEach(reminder -> ServiceFacade.deleteReminder(reminder));

	}

	@Override
	public void initialize(java.net.URL location, ResourceBundle resources) {
		initCss();
	}

	private void initCss() {

		dialogPane.getStylesheets().add(getClass().getResource("/css/ConfirmDeletionStyle.css").toExternalForm());
	}

}
