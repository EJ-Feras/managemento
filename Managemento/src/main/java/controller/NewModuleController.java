package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;

import facades.ServiceFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Module;
import model.Topic;
import utils.WindowManager;

/**
 * Steuert den Dialog zum Erstellen eines neuen Moduls.
 * 
 * @author Feras
 *
 */
public class NewModuleController implements Initializable {

	@FXML
	private StackPane stackPane;
	@FXML
	private JFXButton saveBtn;
	@FXML
	private JFXButton cancelBtn;
	@FXML
	private JFXButton minBtn;
	@FXML
	private JFXButton exitBtn;
	@FXML
	private JFXTextField moduleNameTextField;
	@FXML
	private JFXTextField semesterTextField;
	@FXML
	private ImageView minImageView;
	@FXML
	private ImageView closeImageView;
	@FXML
	private ImageView avatarImageView;

	private InformationAlertController informationAlertController;
	private VBox informationAlert;
	public static JFXDialog informationAlertDialog;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initInformationAlert();
		initImagesAndCss();
	}

	@FXML
	void save(ActionEvent event) {

		String moduleName = moduleNameTextField.getText();
		String semester = semesterTextField.getText();

		Module module = new Module();
		module.setName(moduleName);

		if (checkSemester(semester))
			module.setSemester(semester);
		else
			showMessage("Semester should be in this form:\n" + "      20??/??  or   20??");

		// Themen des Moduls mit dem selben Namen dem neu erstellten Module hinzufuegen.
		if (getTopicsFrom(moduleName) != null && getTopicsFrom(moduleName).size() > 0) {

			List<Topic> topicsFromModule = getTopicsFrom(moduleName);

			for (Topic topic : topicsFromModule) {
				Topic newTopic = new Topic();
				newTopic.setName(topic.getName());
				newTopic.setNote(topic.getNote());
				module.addTopic(newTopic);
			}

		}

		ServiceFacade.addModule(module);

		// den Dialogbereich nach dem Erstellen des Moduls schließen.
		WindowManager.exit(cancelBtn);
	}

	private boolean checkSemester(String semester) {
		semester = semesterTextField.getText();
		return semester.matches("20[2-9][0-9]/[2-9][1-9]") || semester.matches("20[2-9][0-9]");
	}

	private List<Topic> getTopicsFrom(String moudleName) {

		List<Topic> topicFromModule = null;
		Module moduleFromDB = ServiceFacade.findModuleByName(moudleName);
		if (moduleFromDB != null)
			topicFromModule = moduleFromDB.getTopics();

		return topicFromModule;

	}

	@FXML
	void cancel(ActionEvent event) {
		WindowManager.exit(cancelBtn);
	}

	@FXML
	void exit(ActionEvent event) {
		WindowManager.exit(exitBtn);

	}

	@FXML
	void minimize(ActionEvent event) {
		WindowManager.minimize(minBtn);
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

		informationAlertDialog = new JFXDialog(stackPane, informationAlert, JFXDialog.DialogTransition.CENTER);
	}

	private void showMessage(String msg) {
		informationAlertController.setErrorFromNewModuleController(true);
		informationAlertController.setMsgLabel(msg);
		informationAlertDialog.show();
	}

	private void initImagesAndCss() {
		Image close = new Image(getClass().getResource("/img/whiteClose.png").toExternalForm());
		Image min = new Image(getClass().getResource("/img/whiteMinimize.png").toExternalForm());
		closeImageView.setImage(close);
		minImageView.setImage(min);

		stackPane.getStylesheets().add(getClass().getResource("/css/ModuleCardDialogStyle.css").toExternalForm());

	}

}
