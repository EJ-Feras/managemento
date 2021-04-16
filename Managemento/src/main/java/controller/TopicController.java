package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import facades.ServiceFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Topic;
import utils.WindowManager;
/**
 * 
 * Hier wird der Inhalt eines Themas gesteuert.
 * 
 * @author Feras
 *
 */
public class TopicController implements Initializable {

	@FXML
	private GridPane gridPane;
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private JFXButton exitBtn;
	@FXML
	private JFXButton maxBtn;
	@FXML
	private JFXButton minBtn;
	@FXML
	private JFXTextField topicNameTextfield;
	@FXML
	private Text descriptionText;
	@FXML
	private VBox notesVbox1;
	@FXML
	private JFXTextArea descriptionTextArea;
	@FXML
	private JFXButton saveDescriptionBtn;
	@FXML
	private JFXButton clearDescriptionBtn;
	@FXML
	private AnchorPane mainPane;
	@FXML
	private Text notesText;
	@FXML
	private JFXTextArea notesTextArea;
	@FXML
	private JFXButton saveNotesBtn;
	@FXML
	private JFXButton clearNotesFieldBtn;
	@FXML
	private ImageView closeImageView;
	@FXML
	private ImageView maxImageView;
	@FXML
	private ImageView minImageView;

	private Topic topic;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initImagesAndCss();

	}
	

	@FXML
	void saveNotes(ActionEvent event) {
		String notes = notesTextArea.getText();
		topic.setNote(notes);
		ServiceFacade.saveTopic(topic);

	}

	@FXML
	void saveDescription(ActionEvent event) {
		String description = descriptionTextArea.getText();
		topic.setDescription(description);
		ServiceFacade.saveTopic(topic);
	}

	@FXML
	void clearNotesField(ActionEvent event) {
		notesTextArea.clear();
		topic.setNote(null);
		ServiceFacade.saveTopic(topic);

	}

	@FXML
	void clearDescriptionField(ActionEvent event) {
		descriptionTextArea.clear();
		topic.setDescription("");
		ServiceFacade.saveTopic(topic);
	}

	@FXML
	void exit(ActionEvent event) {
		WindowManager.exit(exitBtn);
	}

	@FXML
	void maximize(ActionEvent event) {
		WindowManager.maximize(maxBtn);
	}

	@FXML
	void minimize(ActionEvent event) {
		WindowManager.minimize(minBtn);
	}


	public void setTopicName(String name) {
		topicNameTextfield.setText(name);
		
	}

	public void setNotes(String notes) {
		notesTextArea.setText(notes);
		
	}

	public void setDescription(String description) {
		descriptionTextArea.setText(description);
	}


	public void setTopic(Topic topic) {
		this.topic = topic;

	}
	
	
	private void initImagesAndCss() {
		Image close = new Image(getClass().getResource("/img/close.png").toExternalForm());
		Image max = new Image(getClass().getResource("/img/max.png").toExternalForm());
		Image min = new Image(getClass().getResource("/img/minimize.png").toExternalForm());
		closeImageView.setImage(close);
		maxImageView.setImage(max);
		minImageView.setImage(min);
		
		gridPane.getStylesheets().add(getClass().getResource("/css/project.css").toExternalForm());
	}


}
