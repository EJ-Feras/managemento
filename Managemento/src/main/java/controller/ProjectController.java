package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import facades.ServiceFacade;
import javafx.application.HostServices;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Callback;
import main.MainApp;
import model.Project;
import model.Sprint;
import model.Student;

import utils.WindowManager;

/**
 * Hier wird der Inhalt eines Projekts gesteuert.
 * 
 * @author Feras
 *
 */
public class ProjectController implements Initializable {

	@FXML
	private GridPane gridPane;
	@FXML
	private StackPane alertStackPane;
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private JFXButton exitBtn;
	@FXML
	private ImageView closeImageView;
	@FXML
	private JFXButton maxBtn;
	@FXML
	private ImageView maxImageView;
	@FXML
	private JFXButton minBtn;
	@FXML
	private ImageView minImageView;
	@FXML
	private Label projectNameLabel;
	@FXML
	private Label projectTopicLabel;
	@FXML
	private Label projectYear;
	@FXML
	private Hyperlink projectLink;
	@FXML
	private StackPane gradeBtnsStackPane;
	@FXML
	private AnchorPane giveGradeBtnPane;
	@FXML
	private JFXButton giveGradeBtn;
	@FXML
	private AnchorPane saveGradeBtnPane;
	@FXML
	private JFXButton saveGradeBtn;
	@FXML
	private StackPane gradeStakePane;
	@FXML
	private AnchorPane gradePane;
	@FXML
	private Label gradeLabel;
	@FXML
	private AnchorPane gradeTextFieldPane;
	@FXML
	private JFXTextField gradeTextField;
	@FXML
	private Text sprintTableText;
	@FXML
	private StackPane sprintsStackPane;
	@FXML
	private VBox SprintsTableVbox;
	@FXML
	private TableView<Sprint> sprintsTable;
	@FXML
	private TableColumn<Sprint, Integer> sprintNumberCol;
	@FXML
	private TableColumn<Sprint, String> sprintDescriptionColumn;
	@FXML
	private TableColumn<Sprint, String> sprintNotesCol;
	@FXML
	private TableColumn<Sprint, LocalDate> sprintStartCol;
	@FXML
	private TableColumn<Sprint, LocalDate> sprintEndCol;
	@FXML
	private StackPane sprintsBtnsStackPane;
	@FXML
	private GridPane sprintsTableBtns;
	@FXML
	private JFXButton addSprintoTableBtn;
	@FXML
	private JFXButton editSprintInTableBtn;
	@FXML
	private JFXButton deleteSprintFromTable;
	@FXML
	private AnchorPane mainPane;
	@FXML
	private Text studentsText;
	@FXML
	private Text urlsText;
	@FXML
	private Text notesText;
	@FXML
	private StackPane studentsLinksNotesStackPane;
	@FXML
	private VBox studentsVbox;
	@FXML
	private TableView<Student> studentsTable;
	@FXML
	private TableColumn<Student, Integer> matriculationCol;
	@FXML
	private TableColumn<Student, String> firstNameCol;
	@FXML
	private TableColumn<Student, String> lastNameCol;
	@FXML
	private TableColumn<Student, Double> gradeCol;
	@FXML
	private VBox urlsVbox;
	@FXML
	private TableView<model.URL> urlsTable;
	@FXML
	private TableColumn<model.URL, String> urlsNameCol;
	@FXML
	private TableColumn<model.URL, String> urlCol;
	@FXML
	private VBox notesVbox;
	@FXML
	private JFXTextArea projectNoteTextArea;
	@FXML
	private GridPane studentsBtns;
	@FXML
	private JFXButton addStudentBtn;
	@FXML
	private JFXButton deleteStudentBtn;
	@FXML
	private JFXButton editStudentBtn;
	@FXML
	private GridPane urlsBtns;
	@FXML
	private JFXButton addUrlBtn;
	@FXML
	private JFXButton deleteUrlBtn;
	@FXML
	private GridPane notesBtns;
	@FXML
	private JFXButton saveNotesBtn;
	@FXML
	private JFXButton editNotesBtn;
	@FXML
	private JFXButton clearNotesFieldBtn;
	@FXML
	private StackPane linkBtnsStackPane;
	@FXML
	private AnchorPane addLinkBtnPane;
	@FXML
	private JFXButton addLinkBtn;
	@FXML
	private AnchorPane saveLinkBtnPane;
	@FXML
	private JFXButton saveLinkBtn;
	@FXML
	private StackPane linkStakePane;
	@FXML
	private AnchorPane projectLinkPane;
	@FXML
	private AnchorPane linkTextFieldPane;
	@FXML
	private JFXTextField linkTextField;

	private static Project project;
	private TableView<Project> projectTable;
	private ModuleController moduleController;
	private InformationAlertController informationAlertController;
	private VBox informationAlert;
	public static JFXDialog informationAlertDialog;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initImagesAndCss();
		initInformationAlert();

	}

	/***********************************************************
	 * 
	 * Sprints
	 * 
	 * @author Feras
	 ***********************************************************/

	@FXML
	void showSprintsTable(MouseEvent event) {

		SprintsTableVbox.setVisible(true);
		sprintsTableBtns.setVisible(true);
		// Underline the currently selected text.
		sprintTableText.setUnderline(true);

	}

	@FXML
	void addSprintIntoTable(ActionEvent event) throws IOException {

		NewSprintController.setProject(project);

		FXMLLoader dialogLoader = new FXMLLoader();
		DialogPane newSprintDialog = dialogLoader
				.load(ModuleController.class.getResource("/fxmls/NewSprintDialog.fxml").openStream());
		NewSprintController newSprintController = (NewSprintController) dialogLoader.getController();
		// Wir übergeben die Sprints-Tabelle an NewProjectController, damit wir sie
		// nach
		// jedem Hinzufügen eines neuen Sprints gleichzeitig aktualisieren können.
		newSprintController.setSprintsTable(sprintsTable);

		Dialog<Object> addDialog = createDialog(newSprintDialog);
		abilityToCloseFromXButton(addDialog);
		addDialog.showAndWait();

		moduleController.initProjectsTable();

	}

	@FXML
	void deleteSprintfromTable(ActionEvent event) throws IOException {

		// ueberpruefe , ob mindestens eine Zeile der sprintsTablle ausgewaehlt ist.
		if (sprintsTable.getItems().isEmpty() || sprintsTable.getSelectionModel().isEmpty())
			return;
		// alle ausgewaehlten Zeilen.
		ObservableList<Sprint> sprints = sprintsTable.getSelectionModel().getSelectedItems();

		// Bestaetigungsdialog vor dem Loeschen anzeigen.
		FXMLLoader dialogLoader = new FXMLLoader();
		DialogPane confirmDeletion = dialogLoader
				.load(ModuleController.class.getResource("/fxmls/ConfirmDeletion.fxml").openStream());
		DeletionController deletionController = (DeletionController) dialogLoader.getController();

		deletionController.setSprintsList(sprints);

		Dialog<Object> removeDialog = createDialog(confirmDeletion);
		abilityToCloseFromXButton(removeDialog);
		removeDialog.showAndWait();
		// Sprinttabelle aktualisieren

		List<Sprint> sprintsAfterDeletion = ServiceFacade.findSortedSprints(project);
		initSprintsTable(sprintsAfterDeletion);

	}

	@FXML
	void editSprintInTable(ActionEvent event) throws IOException {

		editSprint();
	}

	/***********************************************************
	 * 
	 * URL
	 * 
	 * @author Feras
	 * @throws IOException
	 ***********************************************************/

	@FXML
	void openProjectLink(ActionEvent event) {

		projectLink.setTooltip(new Tooltip(projectLink.getText()));

		Hyperlink h = (Hyperlink) event.getTarget();
		String s = h.getTooltip().getText();
		HostServices services = MainApp.getInstance().getHostServices();
		services.showDocument(s);
		event.consume();

	}

	@FXML
	void makeLinkFieldEditable(ActionEvent event) {

		linkTextField.clear();
		projectLinkPane.setVisible(false);
		linkTextFieldPane.setVisible(true);
		addLinkBtnPane.setVisible(false);
		saveLinkBtnPane.setVisible(true);

	}

	@FXML
	void saveProjectLink(ActionEvent event) {

		if (checkUrl(linkTextField.getText())) {
			projectLink.setText(linkTextField.getText().trim());
			project.setMainUrl(linkTextField.getText().trim());
			ServiceFacade.saveORUpdate(project);
		}

		projectLinkPane.setVisible(true);
		linkTextFieldPane.setVisible(false);
		addLinkBtnPane.setVisible(true);
		saveLinkBtnPane.setVisible(false);

	}

	private boolean checkUrl(String url) {

		return !url.trim().isEmpty() && url != null && url.length() >= 15;
	}

	@FXML
	void showUrlsTable(MouseEvent event) {

		urlsVbox.setVisible(true);
		studentsVbox.setVisible(false);
		notesVbox.setVisible(false);
		urlsBtns.setVisible(true);
		studentsBtns.setVisible(false);
		notesBtns.setVisible(false);

		urlsText.setUnderline(true);
		studentsText.setUnderline(false);
		notesText.setUnderline(false);

	}

	@FXML
	void addNewUrl(ActionEvent event) throws IOException {

		FXMLLoader dialogLoader = new FXMLLoader();
		DialogPane newSprintDialog = dialogLoader
				.load(NewStudentController.class.getResource("/fxmls/NewURLDialog.fxml").openStream());
		NewURLController newURLController = (NewURLController) dialogLoader.getController();
		// Wir übergeben die Sprints-Tabelle an NewStudentController, damit wir sie
		// nach
		// jedem Hinzufügen eines neuen Studentes gleichzeitig aktualisieren können.
		newURLController.setURLSTable(urlsTable);
		newURLController.setProject(project);

		Dialog<Object> addDialog = createDialog(newSprintDialog);
		abilityToCloseFromXButton(addDialog);
		addDialog.showAndWait();

	}

	@FXML
	void deleteUrl(ActionEvent event) throws IOException {

		// ueberpruefe , ob mindestens eine Zeile der urlsTabelle ausgewaehlt ist.
		if (urlsTable.getItems().isEmpty() || urlsTable.getSelectionModel().isEmpty())
			return;
		// alle ausgewaehlten Zeilen.
		ObservableList<model.URL> urls = urlsTable.getSelectionModel().getSelectedItems();

		// Bestaetigungsdialog vor dem Loeschen anzeigen.
		FXMLLoader dialogLoader = new FXMLLoader();
		DialogPane confirmDeletion = dialogLoader
				.load(ModuleController.class.getResource("/fxmls/ConfirmDeletion.fxml").openStream());
		DeletionController deletionController = (DeletionController) dialogLoader.getController();

		deletionController.setURLsList(urls);

		Dialog<Object> removeDialog = createDialog(confirmDeletion);
		abilityToCloseFromXButton(removeDialog);
		removeDialog.showAndWait();
		// Sprinttabelle aktualisieren

		List<model.URL> urlsAfterDeletion = ServiceFacade.findURLs(project);
		initURLSTable(urlsAfterDeletion);
	}

	@FXML
	void editUrl(ActionEvent event) throws IOException {

		editURL();
	}

	/***********************************************************
	 * 
	 * Students
	 * 
	 * @author Feras
	 ***********************************************************/

	@FXML
	void showStudentsTable(MouseEvent event) {

		studentsVbox.setVisible(true);
		urlsVbox.setVisible(false);
		notesVbox.setVisible(false);
		studentsBtns.setVisible(true);
		urlsBtns.setVisible(false);
		notesBtns.setVisible(false);

		studentsText.setUnderline(true);
		urlsText.setUnderline(false);
		notesText.setUnderline(false);

	}

	@FXML
	void addStudent(ActionEvent event) throws IOException {

		FXMLLoader dialogLoader = new FXMLLoader();
		DialogPane newSprintDialog = dialogLoader
				.load(NewStudentController.class.getResource("/fxmls/NewStudentDialog.fxml").openStream());
		NewStudentController newStudentController = (NewStudentController) dialogLoader.getController();
		// Wir übergeben die Sprints-Tabelle an NewStudentController, damit wir sie
		// nach
		// jedem Hinzufügen eines neuen Studentes gleichzeitig aktualisieren können.
		newStudentController.setStudentsTable(studentsTable);
		newStudentController.setProject(project);

		Dialog<Object> addDialog = createDialog(newSprintDialog);
		abilityToCloseFromXButton(addDialog);
		addDialog.showAndWait();

	}

	@FXML
	void deleteStudent(ActionEvent event) throws IOException {

		// ueberpruefe , ob mindestens eine Zeile der studentenTablle ausgewaehlt ist.
		if (studentsTable.getItems().isEmpty() || studentsTable.getSelectionModel().isEmpty())
			return;
		// alle ausgewaehlten Zeilen.
		ObservableList<Student> students = studentsTable.getSelectionModel().getSelectedItems();

		// Bestaetigungsdialog vor dem Loeschen anzeigen.
		FXMLLoader dialogLoader = new FXMLLoader();
		DialogPane confirmDeletion = dialogLoader
				.load(ModuleController.class.getResource("/fxmls/ConfirmDeletion.fxml").openStream());
		DeletionController deletionController = (DeletionController) dialogLoader.getController();

		deletionController.setStudentList(students);

		Dialog<Object> removeDialog = createDialog(confirmDeletion);
		abilityToCloseFromXButton(removeDialog);
		removeDialog.showAndWait();

		// Studententabelle aktualisieren
		List<Student> studentsAfterDeletion = ServiceFacade.findStudents(project);
		initStudentsTable(studentsAfterDeletion);

	}

	@FXML
	void editStudent(ActionEvent event) throws IOException {

		edit();

	}

	/***********************************************************
	 * 
	 * Notes
	 * 
	 * @author Feras
	 ***********************************************************/

	@FXML
	void showNotesField(MouseEvent event) {

		notesVbox.setVisible(true);
		studentsVbox.setVisible(false);
		urlsVbox.setVisible(false);
		notesBtns.setVisible(true);
		studentsBtns.setVisible(false);
		urlsBtns.setVisible(false);

		notesText.setUnderline(true);
		studentsText.setUnderline(false);
		urlsText.setUnderline(false);
	}

	@FXML
	void clearNotesField(ActionEvent event) {

		projectNoteTextArea.clear();
		project.setNote("");
		ServiceFacade.saveORUpdate(project);
	}

	@FXML
	void editNotes(ActionEvent event) {

	}

	@FXML
	void saveNotes(ActionEvent event) {

		String notes = projectNoteTextArea.getText();

		project.setNote(notes);

		ServiceFacade.saveORUpdate(project);
	}

	@FXML
	void makeGradeFieldEditable(ActionEvent event) {

		if (project.getNote() != null)
			gradeTextField.setText(String.valueOf(project.getNote()));
		else
			gradeTextField.setText("0.0");
		gradePane.setVisible(false);
		gradeTextFieldPane.setVisible(true);
		giveGradeBtnPane.setVisible(false);
		saveGradeBtnPane.setVisible(true);
	}

	@FXML
	void saveGrade(ActionEvent event) {

		gradeLabel.setText(gradeTextField.getText().trim());
		try {
			project.setMark(Double.valueOf(gradeTextField.getText().trim()));
			ServiceFacade.saveORUpdate(project);
		} catch (IllegalArgumentException e) {

			showMessage(e.getMessage());
			return;

		}

		gradePane.setVisible(true);
		gradeTextFieldPane.setVisible(false);
		giveGradeBtnPane.setVisible(true);
		saveGradeBtnPane.setVisible(false);

		moduleController.initProjectsTable();
	}

	public static void setProject(Project project_) {
		project = project_;
	}

	public void setProjectName(String name) {
		projectNameLabel.setText(name);

	}

	public void setProjectTopic(String topicName) {
		projectTopicLabel.setText(topicName);

	}

	public void setProjectYear(int semester) {
		projectYear.setText(String.valueOf(semester));

	}

	public void setModuleController(ModuleController moduleController) {
		this.moduleController = moduleController;

	}

	public void setProjectLink(String link) {
		projectLink.setText(link);
	}

	public void setProjectGrade(double grade) {
		gradeLabel.setText(String.valueOf(grade));

	}

	public void initStudentsTable(List<Student> studentsList) {
		studentsTable.setItems(FXCollections.observableList(studentsList));
		// Moeglichkeit, mehrere Zeilen in einer Tabelle auszuwaehlen
		studentsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		firstNameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("vorname"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
		matriculationCol.setCellValueFactory(new PropertyValueFactory<Student, Integer>("matrikelnummer"));
		gradeCol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Student, Double>, ObservableValue<Double>>() {
					@Override
					public ObservableValue<Double> call(CellDataFeatures<Student, Double> param) {

						Student student = param.getValue();

						if (student != null) {
							Double studentGrade = ServiceFacade.findStudent(student.getMatriculationNumber()).getGrade();
							if (studentGrade != null)
								return new SimpleDoubleProperty(studentGrade).asObject();
						}
						return null;
					}
				});

		// Student bearbeiten, wenn die ausgewaehlte Zeile zweimal mit der linken
		// Maustaste gedrueckt wurde.
		studentsTable.setOnMousePressed(mouseEvent -> {
			if ((mouseEvent.isPrimaryButtonDown()) && (mouseEvent.getClickCount() == 2)) {
				try {
					edit();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void initURLSTable(List<model.URL> urlsList) {
		urlsTable.setItems(FXCollections.observableList(urlsList));
		// Moeglichkeit, mehrere Zeilen in einer Tabelle auszuwaehlen
		urlsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		urlsNameCol.setCellValueFactory(new PropertyValueFactory<model.URL, String>("name"));
		urlCol.setCellValueFactory(new PropertyValueFactory<model.URL, String>("url"));
		urlCol.setCellFactory(new HyperlinkCell());

	}

	public void initSprintsTable(List<Sprint> sprintsList) {

		sprintsTable.setItems(FXCollections.observableList(sprintsList));
		// Moeglichkeit, mehrere Zeilen in einer Tabelle auszuwaehlen
		sprintsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		// Die cellValueFactory teilt der Spalte mit, welcher Wert in ihren Zellen
		// angezeigt werden soll.
		sprintNumberCol.setCellValueFactory(new PropertyValueFactory<Sprint, Integer>("sprintNumber"));
		sprintDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Sprint, String>("sprintBeschreibung"));
		sprintNotesCol.setCellValueFactory(new PropertyValueFactory<Sprint, String>("notizen"));
		sprintStartCol.setCellValueFactory(new PropertyValueFactory<Sprint, LocalDate>("sprintAnfang"));
		sprintEndCol.setCellValueFactory(new PropertyValueFactory<Sprint, LocalDate>("sprintEnde"));

//
//		// offne das Project, wenn es zweimal mit der linken Maustaste gedrueckt wurde.
//		sp.setOnMousePressed(mouseEvent -> {
//			if ((mouseEvent.isPrimaryButtonDown()) && (mouseEvent.getClickCount() == 2)) {
//				try {
//					openProject();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		});

	}

	public void initNotes(String notes) {
		projectNoteTextArea.setText(notes);

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

	/***********************************************************
	 * 
	 * Hilfemethoden
	 * 
	 * @author Feras
	 ***********************************************************/

	private void initImagesAndCss() {

		Image close = new Image(getClass().getResource("/img/close.png").toExternalForm());
		Image max = new Image(getClass().getResource("/img/max.png").toExternalForm());
		Image min = new Image(getClass().getResource("/img/minimize.png").toExternalForm());
		closeImageView.setImage(close);
		maxImageView.setImage(max);
		minImageView.setImage(min);

		gridPane.getStylesheets().add(getClass().getResource("/css/project.css").toExternalForm());
	}

	private Dialog<Object> createDialog(DialogPane dialogPane) {

		Dialog<Object> dialog = new Dialog<Object>();

		dialog.initStyle(StageStyle.TRANSPARENT);
		dialog.getDialogPane().getScene().setFill(Color.TRANSPARENT);

		dialog.setDialogPane(dialogPane);

		return dialog;

	}

	/**
	 * Schließt die Bühne über den "X"-Button .
	 * 
	 * @param dialog
	 * @author Feras
	 */
	private void abilityToCloseFromXButton(final Dialog<Object> dialog) {

		Window window = dialog.getDialogPane().getScene().getWindow();
		window.setOnCloseRequest(e -> dialog.hide());
	}

	/*
	 * Die Zeichenfolgen in der URL-Spalte, die sich in der URL-Tabelle befinden,
	 * werden als Hyperlinks angezeigt.
	 */
	private class HyperlinkCell implements Callback<TableColumn<model.URL, String>, TableCell<model.URL, String>> {

		@Override
		public TableCell<model.URL, String> call(TableColumn<model.URL, String> arg) {
			TableCell<model.URL, String> cell = new TableCell<model.URL, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {

					Hyperlink link = new Hyperlink(item);
					link.setTooltip(new Tooltip(item));
					link.setOnAction((ActionEvent event) -> {
						Hyperlink h = (Hyperlink) event.getTarget();
						/*
						 * OLBERTZ Erstes Semester: Nennen Sie eine Variable niemals s oder so. Was
						 * steht da drin? Wenn mich das interessiert, weil ich mit dem Wert arbeiten
						 * muss, dann muss ich erst mal herauskriegen, wo der Wert herkommt. Ein Name
						 * waere z.B. toolTipText oder so.
						 */
						String s = h.getTooltip().getText();
						HostServices services = MainApp.getInstance().getHostServices();
						services.showDocument(s);
						event.consume();
					});
					setGraphic(link);
				}
			};
			return cell;
		}
	}

	private void edit() throws IOException {
		// OLBERTZ Thema Einrueckung: Gilt immer noch.
		if (studentsTable.getItems().isEmpty() || studentsTable.getSelectionModel().isEmpty()) {
			return;
		}

		Student toBeUpdateStudent = studentsTable.getSelectionModel().getSelectedItem();

		EditStudentController.setToBeUpdated(toBeUpdateStudent);

		FXMLLoader dialogLoader = new FXMLLoader();
		DialogPane editStudentDialog = dialogLoader
				.load(NewStudentController.class.getResource("/fxmls/EditStudentDialog.fxml").openStream());
		EditStudentController editStudentController = (EditStudentController) dialogLoader.getController();

		editStudentController.setProject(project);

		Dialog<Object> editDialog = createDialog(editStudentDialog);
		editDialog.showAndWait();

		studentsTable.refresh();
		studentsTable.getSelectionModel().select(toBeUpdateStudent);
	}

	private void editSprint() throws IOException {
		if (sprintsTable.getItems().isEmpty() || sprintsTable.getSelectionModel().isEmpty()) {
			return;
		}

		Sprint toBeUpdatedSprint = sprintsTable.getSelectionModel().getSelectedItem();

		EditSprintController.setToBeUpdated(toBeUpdatedSprint);

		FXMLLoader dialogLoader = new FXMLLoader();
		DialogPane editStudentDialog = dialogLoader
				.load(EditSprintController.class.getResource("/fxmls/EditSprintDialog.fxml").openStream());
		EditSprintController editSprintController = (EditSprintController) dialogLoader.getController();

		editSprintController.setProject(project);

		Dialog<Object> editDialog = createDialog(editStudentDialog);
		editDialog.showAndWait();

		sprintsTable.refresh();
		sprintsTable.getSelectionModel().select(toBeUpdatedSprint);
	}

	private void editURL() throws IOException {
		if (urlsTable.getItems().isEmpty() || urlsTable.getSelectionModel().isEmpty()) {
			return;
		}

		model.URL toBeUpdatedURL = urlsTable.getSelectionModel().getSelectedItem();

		EditURLController.setToBeUpdated(toBeUpdatedURL);

		FXMLLoader dialogLoader = new FXMLLoader();
		DialogPane editURLDialog = dialogLoader
				.load(EditSprintController.class.getResource("/fxmls/EditURLDialog.fxml").openStream());
		EditURLController editURLController = (EditURLController) dialogLoader.getController();

		editURLController.setProject(project);

		Dialog<Object> editDialog = createDialog(editURLDialog);
		editDialog.showAndWait();

		urlsTable.refresh();
		urlsTable.getSelectionModel().select(toBeUpdatedURL);
	}

	private void showMessage(String msg) {

		informationAlertController.setErrorFromProjectController(true);
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

		informationAlertDialog = new JFXDialog(alertStackPane, informationAlert, JFXDialog.DialogTransition.CENTER);
	}

}
