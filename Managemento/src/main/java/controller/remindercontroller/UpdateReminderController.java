package controller.remindercontroller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import facades.ServiceFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DialogPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Meeting;

/**
 * Steuert den UpdateReminderDialog.
 * 
 * @author Feras
 *
 */

public class UpdateReminderController implements Initializable {

	@FXML
	private DialogPane dialogPane;
	@FXML
	private JFXTextField title;
	@FXML
	private JFXTextArea comment;
	@FXML
	private JFXDatePicker date;
	@FXML
	private JFXTimePicker time;
	@FXML
	private JFXButton updateBtn;
	@FXML
	private JFXCheckBox finishedCheckBox;

	private static Meeting toBeUpdatedReminder;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initImagesAndCss();
		initUpdateReminderDialog();

	}

	@FXML
	void updateSelectedMemento(ActionEvent event) {
		update();
		closeStage(event);

	}

	public static void setToBeUpdated(Meeting toBeUpdatedReminder_) {
		toBeUpdatedReminder = toBeUpdatedReminder_;
	}

	private void update() {
		toBeUpdatedReminder.setTitle(title.getText().trim());
		toBeUpdatedReminder.setComment(comment.getText().trim());
		toBeUpdatedReminder.setDate(String.valueOf(date.getValue()));
		toBeUpdatedReminder.setTime(time.getValue());
		toBeUpdatedReminder.setFinished(finishedCheckBox.isSelected());

		ServiceFacade.updateReminder(toBeUpdatedReminder);

	}

	private void closeStage(ActionEvent event) {
		final Node source = (Node) event.getSource();
		final Stage stage = (Stage) source.getScene().getWindow();
		stage.close();

	}

	private void closeStage(KeyEvent event) {
		final Node source = (Node) event.getSource();
		final Stage stage = (Stage) source.getScene().getWindow();
		stage.close();

	}

	/**
	 * Fuellt die Felder des Dialogs mit dem entspreschenden Daten aus der Datenbank
	 * 
	 * @author Feras
	 */
	private void initUpdateReminderDialog() {
		if (toBeUpdatedReminder != null) {

			if (toBeUpdatedReminder.getTitle() != null)
				title.setText(toBeUpdatedReminder.getTitle());
			if (toBeUpdatedReminder.getComment() != null)
				comment.setText(toBeUpdatedReminder.getComment());
			if (toBeUpdatedReminder.getDate() != null)
				date.setValue(LocalDate.parse(toBeUpdatedReminder.getDate()));
			if (toBeUpdatedReminder.getTime() != null)
				time.setValue(toBeUpdatedReminder.getTime());
			if (toBeUpdatedReminder.isFinished())
				finishedCheckBox.setSelected(true);
			else
				finishedCheckBox.setSelected(false);

			dialogPane.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
				if (event.getCode() == KeyCode.ENTER) {
					update();
					closeStage(event);
					event.consume();
				}
			});
		}
	}

	private void initImagesAndCss() {
		dialogPane.getStylesheets().add(getClass().getResource("/css/reminder/DialogsStyle.css").toExternalForm());
	}

}
