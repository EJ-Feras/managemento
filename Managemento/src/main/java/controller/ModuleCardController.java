package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;

import facades.ServiceFacade;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.Module;

/**
 * Modulkarte streuern.
 * 
 * @author Feras
 *
 */
public class ModuleCardController implements Initializable {

	@FXML
	private StackPane stackPane;
	@FXML
	private AnchorPane createdCard;
	@FXML
	private VBox vbox;
	@FXML
	private JFXTextField name;
	@FXML
	private Label numberOfProjectsLabel;
	@FXML
	private Label numberOfStudentsLabel;
	@FXML
	private ImageView showImageView;
	@FXML
	private ImageView settingImageView;
	@FXML
	private AnchorPane deletePane;
	@FXML
	private Label semesterLabel;
	@FXML
	private AnchorPane editCard;
	@FXML
	private VBox vbox1;
	@FXML
	private JFXTextField newNameText;
	@FXML
	private JFXButton backBtn;
	@FXML
	private ImageView backImageView;
	@FXML
	private JFXButton saveBtn;

	private JFXTabPane tabPane;
	private Module module;
	private HomeController homeController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initImages();

	}

	/**
	 * Ersetzt die Tabs des Home-Fensters durch die der Modulkarte.
	 *  (modules,statistics, calendar) -> (Projects, notes ,topics)
	 * 
	 * @param event
	 * @throws IOException
	 * @author Feras
	 */
	@FXML
	void openModule(ActionEvent event) throws IOException {

		ModuleController.setModule(module);

		FXMLLoader fxmlLoader = new FXMLLoader();
		JFXTabPane moduleTabpane = fxmlLoader.load(getClass().getResource("/fxmls/Module.fxml").openStream());
		ModuleController moduleController = (ModuleController) fxmlLoader.getController();

		moduleController.setPane(tabPane);
		
		// Loesche die Tabs ( Module,Statistiken,Kalender)
		tabPane.getTabs().clear();
		// Setze die Tabs von Projekten,Notizen und Themen
		tabPane.getTabs().addAll(moduleTabpane.getTabs());

	}

	/**
	 * Setzt die Sichtbarkeit des Bearbeitungskartenfensters mit einer Transition auf true.
	 * 
	 * @param event
	 * @throws IOException
	 * @author Feras
	 */
	@FXML
	void editModuleCard(ActionEvent event) throws IOException {

		editCard.setVisible(true);
		TranslateTransition tt1 = new TranslateTransition(Duration.millis(800));
		tt1.setNode(createdCard);
		tt1.setFromX(0.0);
		tt1.setToX(-516.0);
		tt1.setAutoReverse(false);
		tt1.play();

		tt1.setOnFinished(e -> {
			createdCard.setVisible(false);
		});

	}

	/**
	 * Setzt die Sichtbarkeit der Modulkarte auf true und dann aktualisiert die
	 * Modulkarten, falls die Karteninformationen vor der Rückgabe geändert wurden.
	 * 
	 * @param event
	 * @author Feras
	 */
	@FXML
	void backToCard(ActionEvent event) {

		createdCard.setVisible(true);
		TranslateTransition tt1 = new TranslateTransition(Duration.millis(800));
		tt1.setNode(createdCard);

		tt1.setFromX(-516.0);
		tt1.setToX(0.0);
		tt1.setAutoReverse(false);
		tt1.play();

		tt1.setOnFinished(e -> {
			editCard.setVisible(false);
			try {
				homeController.initModuleCards();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		});
	}

	@FXML
	void save(ActionEvent event) throws IOException {
		String newName = newNameText.getText().trim();
		ServiceFacade.updateModule(module, newName);
	}

	@FXML
	void deleteModule(MouseEvent event) throws IOException {

		ServiceFacade.deleteModule(module);
		homeController.initModuleCards();

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
	 * Der Löschbereich wird ausgeblendet, wenn der Mauszeiger von der
	 * Modulkarte losgelassen wird.
	 * 
	 * @param event
	 * @author Feras
	 */
	@FXML
	void hideDeletePane(MouseEvent event) {
		deletePane.setVisible(false);

	}

	public void setHomeController(HomeController homeController) {

		this.homeController = homeController;
	}

	public void setPane(JFXTabPane tabPane) {
		this.tabPane = tabPane;
	}

	public void setName(String moduleName) {

		name.setText(moduleName);

	}

	public void setSemester(String semester) {

		semesterLabel.setText(semester);

	}

	public void setModule(Module module) {

		this.module = module;
	}

	private void initImages() {

		Image show = new Image(getClass().getResource("/img/show.png").toExternalForm());
		Image setting = new Image(getClass().getResource("/img/setting.png").toExternalForm());
		Image back = new Image(getClass().getResource("/img/back.png").toExternalForm());
		showImageView.setImage(show);
		settingImageView.setImage(setting);
		backImageView.setImage(back);
		
		stackPane.getStylesheets().add(getClass().getResource("/css/ModuleCardDialogStyle.css").toExternalForm());
	}

}
