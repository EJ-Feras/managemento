package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;


import facades.ServiceFacade;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import model.Module;
import utils.WindowManager;

/**
 * Es steuert den Datenfluss in die Modellobjekte Module und Termin und
 * aktualisiert die Ansicht, wenn sich Daten ändern.
 * 
 * @author Feras
 *
 */
public class HomeController implements Initializable {

	@FXML
	private BorderPane mainBorderPane;
	@FXML
	private AnchorPane barAnchorPane;
	@FXML
	private JFXButton exitBtn;
	@FXML
	private JFXButton maxBtn;
	@FXML
	private JFXButton minBtn;
	@FXML
	private BorderPane borderPane;
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private VBox vboxScrollPane;
	@FXML
	private StackPane stackPane;
	@FXML
	private AnchorPane anchorPaneTabPane;
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private JFXTabPane tabPane;
    @FXML
    private Tab statisticsTab;
    @FXML
    private AnchorPane reminderAnchorPane;
    
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		try {
			initModuleCards();
			initReminder();
			initStatistics();
			
			mainBorderPane.getStylesheets().add(getClass().getResource("/css/HomeStyle.css").toExternalForm());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	
	
	/**
	 * Erstellt ein neues Module und aktualisiert den Startansicht nach dem
	 * Erstellen.
	 * 
	 * @param event
	 * @throws IOException
	 * @author Feras
	 */
	@FXML
	void createModule(ActionEvent event) throws IOException {

		FXMLLoader dialogLoader = new FXMLLoader(ModuleController.class.getResource("/fxmls/NewModuleCardDialog.fxml"));
		Dialog<Object> addDialog = createDialog(dialogLoader);
		abilityToCloseFromXButton(addDialog);
		addDialog.showAndWait();

		initModuleCards();

	}

	/**
	 * 
	 * @throws IOException
	 * @author Feras
	 */

	public void initModuleCards() throws IOException {
		// Delete all cards currently shown.
		vboxScrollPane.getChildren().clear();

		List<Module> modules = ServiceFacade.findAllModules();

		for (int i = 0; i < modules.size(); i++) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			Node newModuleCard = fxmlLoader.load(getClass().getResource("/fxmls/ModuleCard.fxml").openStream());
			ModuleCardController moduleCardController = (ModuleCardController) fxmlLoader.getController();

			moduleCardController.setPane(tabPane);
			moduleCardController.setHomeController(this);
			moduleCardController.setModule(modules.get(i));

			// Set module card information

			Module module = (Module) modules.get(i);
			String moduleName = module.getName();
			String semester = module.getSemester();
			if (semester != null && !semester.trim().isEmpty()) {
				moduleCardController.setName(moduleName);
				moduleCardController.setSemester(semester);
			} else
				moduleCardController.setName(moduleName);

			// Show the module card
			vboxScrollPane.getChildren().add(newModuleCard);

		}

	}

	
	@FXML
	void exit(ActionEvent event) {
		WindowManager.exit(exitBtn);
		Platform.exit();

	}

	@FXML
	void maximize(ActionEvent event) {

		WindowManager.maximize(maxBtn);
	}

	@FXML
	void minimize(ActionEvent event) {

		WindowManager.minimize(minBtn);
	}

	private void initReminder() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader();

		Node reminder = fxmlLoader.load(getClass().getResource("/fxmls/reminderView/Reminder.fxml").openStream());
		reminderAnchorPane.getChildren().add(reminder);
		AnchorPane.setBottomAnchor(reminder, 10.0);
		AnchorPane.setTopAnchor(reminder, 5.0);
		AnchorPane.setLeftAnchor(reminder, 10.0);
		AnchorPane.setRightAnchor(reminder, 10.0);

	}

	private void initStatistics() throws IOException {

		FXMLLoader fxmlLoader = new FXMLLoader();
		Node statisticsView = fxmlLoader.load(getClass().getResource("/fxmls/Statistics.fxml").openStream());
		statisticsTab.setContent(statisticsView);

	}

	/*
	 * Erstellt einen Dialogfenstercontainer.
	 */

	private Dialog<Object> createDialog(FXMLLoader dialogLoader) {

		Dialog<Object> dialog = new Dialog<Object>();

		dialog.initStyle(StageStyle.TRANSPARENT);
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("/img/logo.png"));
		dialog.getDialogPane().getScene().setFill(Color.TRANSPARENT);

		try {
			dialog.setDialogPane(dialogLoader.load());

		} catch (IOException e) {

			e.printStackTrace();
		}

		return dialog;

	}

	/**
	 * Schließt die Buehne ueber den "X"-Button .
	 * 
	 * @param dialog
	 * @author Feras
	 */
	private void abilityToCloseFromXButton(final Dialog<Object> dialog) {

		Window window = dialog.getDialogPane().getScene().getWindow();
		window.setOnCloseRequest(e -> {
			dialog.hide();
			Platform.exit();
			});
		
	}



}
