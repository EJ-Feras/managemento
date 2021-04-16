package controller;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;

import facades.ServiceFacade;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.Module;
import model.Project;
import model.Student;
import model.Topic;
import utils.WindowManager;

/**
 * Steuert den Dialog zum Erstellen eines neuen Projekts.
 * 
 * @author Feras
 *
 */
public class NewProjectController implements Initializable {

	@FXML
	private DialogPane dialogPane;
	@FXML
	private StackPane stackPane;
	@FXML
	private JFXButton addProjectBtn;
	@FXML
	private JFXButton cancelBtn;
	@FXML
	private JFXButton minBtn;
	@FXML
	private ImageView minImageView;
	@FXML
	private JFXButton exitBtn;
	@FXML
	private ImageView closeImageView;
	@FXML
	private JFXTextField groupNameField;
	@FXML
	private JFXComboBox<String> comboBoxTopic;
	@FXML
	private TableView<Student> studentsTable;
	@FXML
	private JFXTextField firstNameField;
	@FXML
	private JFXTextField lastNameField;
	@FXML
	private JFXTextField matriculationField;
	@FXML
	private JFXButton addStudentBtn;
	@FXML
	private JFXButton clearBtn;
	@FXML
	private TableColumn<Student, String> firstNameCol;
	@FXML
	private TableColumn<Student, String> lastNameCol;
	@FXML
	private TableColumn<Student, Integer> matriculationCol;
	@FXML
	private TableColumn<Student, JFXButton> deleteStudent;

	private List<Student> studentsList = new LinkedList<>();
	private static Module module;
	private TableView<Project> projectsTable;
	private List<Project> projectsList;
	private List<Topic> topics;
	private InformationAlertController informationAlertController;
	private VBox informationAlert;
	public static JFXDialog informationAlertDialog;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initImagesAndCss();
		initTopicsComboBox();
		initInformationAlert();
		initStudentsTableView();

	}

	@FXML
	void addProject(ActionEvent event) {

		addNewProject(event);

	}

	private void addNewProject(ActionEvent event) {

		String groupName = groupNameField.getText();
		String topicNameFromComboBox = comboBoxTopic.getValue();
		Topic topic = getTopicByName(topicNameFromComboBox);

		Project project = new Project();

		if (!checkGroupName(groupName))
			return;
		project.setName(groupName);
		if (!checkTopic(topic))
			return;
		// das Project mit dem Topic und dem Module verknüpfen
		addStudentsTo(project);
		
		project.setTopic(topic);
		topic.addProject(project);
		project.setModule(module);

	

		ServiceFacade.saveORUpdate(project);
		// Inhalt aller Felder loeschen.
		refreshNewProjectView(event);

	}

	private void refreshNewProjectView(ActionEvent event) {

		refreshTopicsComboBox();
		refreshProjectsTable();
		refreshStudentsTableView();
		clearAllFields(event);
		clearTableFields(event);
	}

	private void refreshProjectsTable() {
		projectsList = ServiceFacade.findAllProjects(module);
		projectsTable.setItems(FXCollections.observableList(projectsList));
	}

	/*
	 * alle Studenten ,die in der Studenten-Tabelle vorhanden sind,zum Project
	 * hinzufuegen
	 * 
	 */
	private void addStudentsTo(Project project) {
		for (Student student : studentsList) {
			project.addStudent(student);
			student.setProject(project);

		}
	}

	private boolean checkGroupName(String groupName) {
		if (groupName == null || groupName.trim().isEmpty()) {
			showMessage("You have to give a name for the project! ");
			return false;
		}

		return true;
	}

	private boolean checkTopic(Topic topic) {

		if (topic == null) {
			
			showMessage("First create or choose a topic before creating a project.");

			return false;
		}
		return true;
	}

	/**
	 * Takes the name of the Topic and returns the associated Topic.
	 * 
	 * @param topicName
	 * @return
	 */
	private Topic getTopicByName(String topicName) {

		Topic topic = null;
		for (Topic topicFromList : topics) {

			if (topicFromList.getName().equals(topicName)) {
				topic = topicFromList;
				return topic;
			}
		}
		return topic;
	}

	@FXML
	void addStudent(ActionEvent event) {

		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();
		int matricNr = -1;
		try {
			matricNr = Integer.valueOf(matriculationField.getText().trim());
			Student student = new Student(matricNr, lastName, firstName);
			List<Student> students = studentsTable.getItems();

			if (!studentExists(student, students)) { // Studierenden einfuegen,nur wenn sie nicht in der Datenbank und
													// der Studentenliste sind.
				students.add(student);
				clearTableFields(event);
			}else {
				showMessage("This Student exists already !");
				clearTableFields(event);
			}

		} catch (IllegalArgumentException e) {
			showMessage("Enter a valid matriculation number!");
			return;
		}

	}

	private void showMessage(String msg) {

		informationAlertController.setErrorFromNewProjectController(true);
		informationAlertController.setMsgLabel(msg);
		informationAlertDialog.show();
	}

	private boolean studentExists(Student student, List<Student> list) {

		boolean studentExists = ServiceFacade.exists(student);

		if (studentExists || indexOf(student, list) > -1) {
			return true;
		}
		return false;

	}

	private int indexOf(Student student, List<Student> list) {

		int index = -1;
		for (int i = 0; i < list.size(); i++) {
			if (student.equals(list.get(i))) {
				index = i;
				return index;
			}
		}

		return index;
	}

	@FXML
	void cancel(ActionEvent event) {

		WindowManager.exit(exitBtn);
	}

	/**
	 * alle Feldinhalte im Fenster loeschen.
	 * 
	 * @param event
	 * @author Feras
	 */
	@FXML
	void clearAllFields(ActionEvent event) {

		groupNameField.clear();
		firstNameField.clear();
		lastNameField.clear();
		matriculationField.clear();

	}

	/**
	 *  alle Feldinhalte  der Studenten-Tabelle loeschen.
	 * 
	 * @param event
	 * @author Feras
	 */
	@FXML
	void clearTableFields(ActionEvent event) {
		firstNameField.clear();
		lastNameField.clear();
		matriculationField.clear();
	}

	@FXML
	void exit(ActionEvent event) {
		WindowManager.exit(exitBtn);

	}

	@FXML
	void minimize(ActionEvent event) {

		WindowManager.minimize(minBtn);
	}

	public void setProjectsTable(TableView<Project> projectsTable) {
		this.projectsTable = projectsTable;
	}

	public static void setModule(Module curModule) {
		module = curModule;
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

	private void initImagesAndCss() {

		Image close = new Image(getClass().getResource("/img/whiteClose.png").toExternalForm());
		Image min = new Image(getClass().getResource("/img/whiteMinimize.png").toExternalForm());
		closeImageView.setImage(close);
		minImageView.setImage(min);
		
		
		dialogPane.getStylesheets().add(getClass().getResource("/css/NewProjectDialogStyle.css").toExternalForm());
	}

	private void refreshTopicsComboBox() {

		comboBoxTopic.getItems().clear();
		initTopicsComboBox();
	}

	private void initTopicsComboBox() {

		topics = ServiceFacade.findAllTopics(module);
		for (Topic t : topics) {
			if (t.getProjectsList().isEmpty()) { // den Combobox nur verfuegbare Themen hinzufuegen
				comboBoxTopic.getItems().add(t.getName());
			}
		}
	}

	private void refreshStudentsTableView() {

		studentsTable.getItems().clear();
		initStudentsTableView();
	}

	private void initStudentsTableView() {

		studentsTable.setItems(FXCollections.observableList(studentsList));

		firstNameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("vorname"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
		matriculationCol.setCellValueFactory(new PropertyValueFactory<Student, Integer>("matrikelnummer"));
		deleteStudent.setCellFactory(column -> new implCallBack().call(column));
	}

	/*
	 * 
	 * teilt der Spalte mit, wie der Delete-Button angezeigt werden soll.
	 *
	 */
	private class implCallBack implements Callback<TableColumn<Student, JFXButton>, TableCell<Student, JFXButton>> {

		public TableCell<Student, JFXButton> call(TableColumn<Student, JFXButton> column) {

			return new TableCell<Student, JFXButton>() {
				// Der Delete-Button in der Studenten-Tabelle.
				private JFXButton button = new JFXButton();

				@Override
				protected void updateItem(JFXButton item, boolean empty) {
					super.updateItem(item, empty);
					TableRow<Student> tableRow = getTableRow();

					if (tableRow != null) {
						final Student tmp = tableRow.getItem();
						if (tmp != null) {

							ImageView imgView = new ImageView(
									new Image(getClass().getResource("/img/delete.png").toExternalForm()));
							imgView.prefHeight(30);
							imgView.prefWidth(30);
							button.setAlignment(Pos.CENTER);
							button.setGraphic(imgView);
							addAction(tmp);
							setGraphic(button);
							initStudentsTableView();

						}

					}

				}

				/*
				 * Loeschen eines Student aus Studenten Tabelle.
				 */
				private void addAction(Student tmp) {

					button.setOnAction(event -> {
						studentsTable.getSelectionModel().select(tmp);
						int index = indexOf(tmp, studentsList);
						studentsList.remove(index);
						initStudentsTableView();

					});

				}

			};
		}

	}

}
