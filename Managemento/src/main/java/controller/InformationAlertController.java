package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Steuert den Inforamtionsalert,den angezeigt wird ,wenn falsche Daten
 * angegeben sind oder die  fehlen.
 * 
 * Hier wird  eine Nachricht in der Bezeichnung (Label)  des Informationsalerts festgelegt.
 * 
 * @author Feras
 *
 */
public class InformationAlertController {

	@FXML
	private Label msgLabel;

	private boolean errorFromNewProjectController;
	private boolean errorFromNewStudentController;
	private boolean errorFromTopicCardController;
	private boolean errorFromNewModuleController;
	private boolean errorFromProjectController;

	private boolean errorFromEditStudentController;

	public void setErrorFromTopicCardController(boolean errorFromTopicCardController) {
		this.errorFromTopicCardController = errorFromTopicCardController;
	}

	/**
	 * Nachricht in Label setzen.
	 * 
	 * @param msg eine Meldung, dass etwas nicht stimmt oder fehlt.
	 * @author Feras
	 */
	public void setMsgLabel(String msg) {
		msgLabel.setText(msg);
	}

	@FXML
	void goBack(ActionEvent event) {
		if (errorFromNewProjectController)
			NewProjectController.informationAlertDialog.close();
		else if (errorFromNewStudentController)
			NewStudentController.informationAlertDialog.close();
		else if (errorFromEditStudentController)
			EditStudentController.informationAlertDialog.close();
		else if (errorFromTopicCardController)
			TopicCardController.informationAlertDialog.close();
		else if (errorFromNewModuleController)
			NewModuleController.informationAlertDialog.close();
		else if(errorFromProjectController)
			ProjectController.informationAlertDialog.close();
	}

	public void setErrorFromNewProjectController(boolean errorFromNewProjectController) {
		this.errorFromNewProjectController = errorFromNewProjectController;
	}

	public void setErrorFromNewStudentController(boolean errorFromNewStudentController) {
		this.errorFromNewStudentController = errorFromNewStudentController;
	}

	public void setErrorFromNewModuleController(boolean errorFromNewModuleController) {
		this.errorFromNewModuleController = errorFromNewModuleController;
		
	}

	public void setErrorFromProjectController(boolean errorFromProjectController) {
		this.errorFromProjectController = errorFromProjectController;
		
	}

	public void setErrorFromEditStudentController(boolean errorFromEditStudentController) {
		this.errorFromEditStudentController = errorFromEditStudentController;
		
	}

}
