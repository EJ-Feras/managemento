package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;


import facades.ServiceFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Module;
import model.Topic;
import utils.WindowManager;

/**
 * Steuert den Dialog zum Erstellen eines neuen Themas.
 * 
 * @author Feras
 *
 */
public class NewTopicController implements Initializable {

    @FXML
    private DialogPane dialogPane;
	@FXML
	private JFXButton saveBtn;
	@FXML
	private JFXButton cancelBtn;
	@FXML
	private JFXButton minBtn;
	@FXML
	private JFXButton exitBtn;
	@FXML
	private ImageView minImageView;
	@FXML
	private ImageView closeImageView;
	@FXML
	private JFXTextField topicNameTextField;
	@FXML
	private JFXTextArea descriptionTextArea;

	private Module module;
	private ModuleController moduleController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initImagesAndCss();

	}

	@FXML
	void save(ActionEvent event) throws IOException {

		String name = topicNameTextField.getText();
		String description = descriptionTextArea.getText();

		Topic thema = new Topic();
		thema.setName(name);
		thema.setDescription(description);
//		module.addThema(thema);


		ServiceFacade.updateModule(module, thema);

		// ScrollPane von Themenkarten aktualisieren
		moduleController.initTopicCards();

	}

	public void setModule(Module module) {
		this.module = module;
	}

	/**
	 * Wir benoetigen den Module-Controller, um die Themenkarten im Module nach jedem
	 * Hinzufuegen zu aktualisieren.
	 * 
	 * @param moduleController
	 * @author Feras
	 */
	public void setModuleController(ModuleController moduleController) {
		this.moduleController = moduleController;
	}

	@FXML
	void cancel(ActionEvent event) {

		WindowManager.exit(exitBtn);
	}

	@FXML
	void minimize(ActionEvent event) {

		WindowManager.minimize(minBtn);
	}

	@FXML
	void exit(ActionEvent event) {
		WindowManager.exit(exitBtn);

	}

	private void initImagesAndCss() {
		Image close = new Image(getClass().getResource("/img/whiteClose.png").toExternalForm());
		Image min = new Image(getClass().getResource("/img/whiteMinimize.png").toExternalForm());
		closeImageView.setImage(close);
		minImageView.setImage(min);
		
		
		dialogPane.getStylesheets().add(getClass().getResource("/css/ModuleCardDialogStyle.css").toExternalForm());
	}

}
