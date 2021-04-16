package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;

import facades.ServiceFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
 * Steuert den Dialog zum Bearbeiten eines Studenten.
 * 
 * @author Feras
 *
 */
public class EditStudentController implements Initializable {

	@FXML
	private DialogPane dialogPane;
	@FXML
	private StackPane stackPane;
	@FXML
	private JFXTextField maticulationTextField;
	@FXML
	private JFXTextField firstNameTextField;
	@FXML
	private JFXTextField lastNameTextField;
	@FXML
	private JFXTextField gradeTextField;
	@FXML
	private JFXButton saveBtn;
	@FXML
	private JFXButton exitBtn;
	@FXML
	private ImageView closeImageView;

	private static Student toBeUpdatedStudent;

	private InformationAlertController informationAlertController;
	private VBox informationAlert;
	public static JFXDialog informationAlertDialog;
	private TableView<Student> studentsTable;
	private Project project;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initImages();
		initInformationAlert();
		initEditStudentDialog();

	}

	@FXML
	void editStudent(ActionEvent event) {
		edit();

	}

	@FXML
	void exit(ActionEvent event) {
		WindowManager.exit(exitBtn);

	}

	public void setStudentsTable(TableView<Student> studentsTable) {
		this.studentsTable = studentsTable;
	}

	public void setProject(Project project) {
		this.project = project;

	}

	public static void setToBeUpdated(Student toBeUpdatedStudent_) {
		toBeUpdatedStudent = toBeUpdatedStudent_;
	}

	private void edit() {

		try {
			int matricNr = Integer.valueOf(maticulationTextField.getText().trim());
			toBeUpdatedStudent.setMatriculationNumber(matricNr);

		} catch (IllegalArgumentException e) {
			// OLBERTZ Bitte niemals Strings in den Code. Benutzen Sie dafuer
			// properties-Datei (vgl. Kapitel Internationalisierung).
			showMessage("Enter a valid matriculation number!");
			return;
		}

		try {
			Double grade = Double.valueOf(gradeTextField.getText().trim());
			toBeUpdatedStudent.setGrade(grade);

		} catch (IllegalArgumentException e) {
			showMessage(e.getMessage());
			return;
		}

		toBeUpdatedStudent.setFirstName(firstNameTextField.getText());
		toBeUpdatedStudent.setLastName(lastNameTextField.getText());

		toBeUpdatedStudent.setProject(project);
		ServiceFacade.updateStudent(toBeUpdatedStudent);

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

	private void showMessage(String msg) {

		informationAlertController.setMsgLabel(msg);
		informationAlertController.setErrorFromEditStudentController(true);
		informationAlertDialog.show();
	}

	private void initEditStudentDialog() {
		if (toBeUpdatedStudent != null) {
			maticulationTextField.setText(String.valueOf(toBeUpdatedStudent.getMatriculationNumber()));
			if (toBeUpdatedStudent.getLastName() != null)
				lastNameTextField.setText(toBeUpdatedStudent.getLastName());
			if (toBeUpdatedStudent.getFirstName() != null)
				firstNameTextField.setText(toBeUpdatedStudent.getFirstName());
			if (toBeUpdatedStudent.getGrade() != 0.0)
				gradeTextField.setText(String.valueOf(toBeUpdatedStudent.getGrade()));
			else
				gradeTextField.setText("");

		}
	}

	private void initImages() {
		Image close = new Image(getClass().getResource("/img/whiteClose.png").toExternalForm());
		closeImageView.setImage(close);
	}

}
