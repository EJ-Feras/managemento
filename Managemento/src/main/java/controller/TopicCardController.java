package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;

import facades.ServiceFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Module;
import model.Topic;
import utils.ResizeHelper;

/**
 * die Themenkarte steuern.
 * 
 * @author Feras
 *
 */
public class TopicCardController implements Initializable {

	@FXML
	private AnchorPane createdCard;
	@FXML
	private JFXTextField name;
	@FXML
	private AnchorPane deletePane;

	private Module module;
	private Topic topic;
	private static ModuleController moduleController;
	private InformationAlertController informationAlertController;
	private VBox informationAlert;
	public static JFXDialog informationAlertDialog;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initInformationAlert();

	}

	private void showMessage(String msg) {

		informationAlertController.setErrorFromTopicCardController(true);
		informationAlertController.setMsgLabel(msg);
		informationAlertDialog.show();
	}

	private void initInformationAlert() {
		try {
			FXMLLoader dialogLoader = new FXMLLoader();
			informationAlert = dialogLoader
					.load(ModuleController.class.getResource("/fxmls/InformationAlert.fxml").openStream());
			informationAlertController = (InformationAlertController) dialogLoader.getController();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		informationAlertDialog = new JFXDialog(moduleController.getTopicsStackPane(), informationAlert,
				JFXDialog.DialogTransition.CENTER);
	}

	@FXML
	void openTopic(ActionEvent event) throws IOException {

		FXMLLoader fxmlLoader = new FXMLLoader();
		Parent root = fxmlLoader.load(getClass().getResource("/fxmls/Topic.fxml").openStream());
		TopicController topicController = (TopicController) fxmlLoader.getController();

		Topic topicFromDB = ServiceFacade.findTopic(module, topic);

		String name = topicFromDB.getName();
		String description = topicFromDB.getDescription();
		String notes = topicFromDB.getNote();

		// Themendaten setzen
		topicController.setTopicName(name);
		topicController.setDescription(description);
		topicController.setNotes(notes);
		topicController.setTopic(topic);

		showInNewStage(root);

	}

	@FXML
	void deleteTopic(MouseEvent event) throws IOException {

		if (topic.getProjectsList().size() == 0) {
			ServiceFacade.deleteTopic(topic);
			moduleController.initTopicCards();
		} else {

			showMessage("This topic is assotiated with a project !");

		}
	}

	/**
	 * Zeigt den Löschbereich an.
	 * 
	 * @param event
	 * @author Feras
	 */
	@FXML
	void showDeletePane(MouseEvent event) {

		deletePane.setVisible(true);
	}

	/**
	 * Der Löschbereich wird ausgeblendet, wenn der Mauszeiger von der Themenkarte
	 * losgelassen wird.
	 * 
	 * @param event
	 * @throws IOException
	 * @author Feras
	 */
	@FXML
	void hideDeletePane(MouseEvent event) {
		deletePane.setVisible(false);

	}

	public static void setModuleController(ModuleController moduleController_) {
		moduleController = moduleController_;
	}

	public void setName(String topicName) {
		name.setText(topicName);
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	private void showInNewStage(Parent root) {
		Stage stage = new Stage();
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.getIcons().add(new Image("/img/logo.png"));
		stage.setTitle("Topic");
		/*
		 * OLBERTZ Lagern Sie auch solche Groessenangaben ruhig immer in Konstanten aus. Das verbessert die
		 * Code-Qualitaet ungemein.
		 */
		stage.setScene(new Scene(root, 1100, 650));
		stage.show();
		ResizeHelper.addResizeListener(stage);
	}

}
