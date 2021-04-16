package controller.remindercontroller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.*;

import facades.ServiceFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Meeting;

/**
 * Steuert den NewReminderDialog.
 * 
 * @author Feras
 *
 */
public class NewReminderController implements Initializable {

	@FXML
	private DialogPane addDialogPane;
	@FXML
	private JFXTextField title;
	@FXML
	private JFXTextArea comment;
	@FXML
	private JFXDatePicker date;
	@FXML
	private JFXTimePicker time;
	@FXML
	private JFXButton saveBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initImagesAndCss();

		addDialogPane.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ENTER) {
				save();
				closeStage(event);
				event.consume();
			}
		});
	}

	@FXML
	public void saveNewMemento(ActionEvent event) {

		save();

		closeStage(event);

	}

	public String getSaveBtn() {

		return saveBtn.getId();
	}

	/**
	 * 
	 */
	private void save() {

		Meeting newReminder = new Meeting();

		newReminder.setTitle(title.getText().trim());
		newReminder.setComment(comment.getText().trim());
		newReminder.setDate(String.valueOf(date.getValue()));
		newReminder.setTime(time.getValue());
		newReminder.setFinished(false);

		ServiceFacade.addReminder(newReminder);
	}

	/**
	 * Close stage if the X button was clicked.
	 * 
	 * @param event
	 */
	private void closeStage(ActionEvent event) {
		final Node source = (Node) event.getSource();
		final Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}

	/**
	 * 
	 * 
	 * @param event
	 */
	private void closeStage(KeyEvent event) {
		final Node source = (Node) event.getSource();
		final Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}

	private void initImagesAndCss() {
//		Image close = new Image(getClass().getResource("/img/close.png").toExternalForm());
//		Image max = new Image(getClass().getResource("/img/max.png").toExternalForm());
//		Image min = new Image(getClass().getResource("/img/minimize.png").toExternalForm());
//		closeImageView.setImage(close);
//		maxImageView.setImage(max);
//		minImageView.setImage(min);

		addDialogPane.getStylesheets().add(getClass().getResource("/css/reminder/DialogsStyle.css").toExternalForm());
	}

}
