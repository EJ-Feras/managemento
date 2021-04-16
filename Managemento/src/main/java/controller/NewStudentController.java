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
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Project;
import model.Student;
import utils.WindowManager;

/**
 * Steuert den Dialog zum Erstellen eines neuen Studneten.
 * 
 * @author Feras
 *
 */
public class NewStudentController implements Initializable {

	@FXML
	private StackPane stackPane;
	@FXML
	private DialogPane addDialogPane;
	@FXML
	private JFXTextField maticulationTextField;
	@FXML
	private JFXTextField firstNameTextField;
	@FXML
	private JFXTextField lastNameTextField;
	@FXML
	private JFXButton saveBtn;
	@FXML
	private JFXButton exitBtn;
	@FXML
	private ImageView closeImageView;

	private InformationAlertController informationAlertController;
	private VBox informationAlert;
	public static JFXDialog informationAlertDialog;
	private TableView<Student> studentsTable;
	private Project project;

	public void setStudentsTable(TableView<Student> studentsTable) {
		this.studentsTable = studentsTable;
	}

	@FXML
	void saveNewStudent(ActionEvent event) {

		addStudent(event);

	}

	private void initImages() {

		Image close = new Image(getClass().getResource("/img/whiteClose.png").toExternalForm());
		closeImageView.setImage(close);

	}

	void addStudent(ActionEvent event) {

		String firstName = firstNameTextField.getText();
		String lastName = lastNameTextField.getText();
		int matricNr = -1;
		try {
			matricNr = Integer.valueOf(maticulationTextField.getText().trim());
			Student student = new Student(matricNr, lastName, firstName);
			project.addStudent(student);
			student.setProject(project);
			List<Student> students = studentsTable.getItems();

			if (!studentExists(student, students)) {// Studierenden einfuegen,nur wenn sie nicht in der Datenbank und
													// der Studentenliste sind.
				students.add(student);
				ServiceFacade.saveORUpdate(project);
			} else
				showMessage("This Student exists already !");

		} catch (IllegalArgumentException e) {
			showMessage("Enter a valid matriculation number!");
			return;
		}

	}

	private void showMessage(String msg) {

		informationAlertController.setMsgLabel(msg);
		informationAlertController.setErrorFromNewStudentController(true);
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
	void exit(ActionEvent event) {
		WindowManager.exit(exitBtn);

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initImages();
		initInformationAlert();
	}

	public void setProject(Project project) {
		this.project = project;
	}
}
