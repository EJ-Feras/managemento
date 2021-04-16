package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;

import facades.ServiceFacade;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Project;
import model.Sprint;
import utils.WindowManager;

public class NewSprintController implements Initializable {

	@FXML
	private DialogPane addDialogPane;
	@FXML
	private JFXTextArea descriptionTextArea;
	@FXML
	private JFXTextArea notesTextArea;
	@FXML
	private JFXDatePicker startDatepicker;
	@FXML
	private JFXDatePicker endDatePicker;
	@FXML
	private JFXButton saveBtn;
	@FXML
	private JFXButton exitBtn;
	@FXML
	private ImageView closeImageView;

	private TableView<Sprint> sprintsTable;
	private List<Sprint> sprintsList;
	private static Project project;

	public static void setProject(Project project_) {
		project = project_;
	}

	@FXML
	void saveNewSprint(ActionEvent event) {

		Sprint sprint = new Sprint();
		sprint.setDescription(descriptionTextArea.getText());
		sprint.setNote(notesTextArea.getText());
		sprint.setStart(startDatepicker.getValue());
		sprint.setEnd(endDatePicker.getValue());
		project.addSprint(sprint);

		ServiceFacade.saveSprint(sprint);

		sprintsList = ServiceFacade.findSortedSprints(project);

		sprintsTable.setItems(FXCollections.observableList(sprintsList));

		exit(event);

	}

	public void setSprintsTable(TableView<Sprint> sprintsTable) {
		this.sprintsTable = sprintsTable;
	}

	public void initImages() {

		Image close = new Image(getClass().getResource("/img/whiteClose.png").toExternalForm());
		closeImageView.setImage(close);

	}

	@FXML
	void exit(ActionEvent event) {
		WindowManager.exit(exitBtn);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initImages();
	}
}
