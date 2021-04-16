package controller.remindercontroller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.jfoenix.controls.*;

import animatefx.animation.AnimationFX;
import animatefx.animation.Hinge;
import animatefx.animation.Swing;
import controller.DeletionController;
import controller.ModuleController;
import facades.ServiceFacade;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import model.ReminderType;
import service.TasksScheduler;
import utils.DateTime;
import model.Meeting;


/**
 * Steuert den Meetingorganisator, der durch eine Tabellenansicht dargestellt
 * wird.
 * 
 * @author Feras
 *
 */
public class ReminderController implements Initializable {

	// ---------------------------------Static
	// Variables-----------------------------
	private final static String ADD_FXML = "/fxmls/reminderView/NewReminderDialog.fxml";
	private static final String UPDATE_FXML = "/fxmls/reminderView/UpdateReminderDialog.fxml";
	private static final String TITLE = "title";
	private static final String COMMENT = "comment";
	private static final String DATE = "date";
	private static final String TIME = "time";
	private static final String UPDATE_DIALOG = "Update Memento";
	private static final String UPDATE_STAGE_ICON = "/img/update.png";
	private static final String ADD_STAGE_ICON = "/img/add.png";
	private static final String ADD_TITLE = "New Memento";

	@FXML
	private BorderPane reminderBorderPane;
	@FXML
	public TableView<Meeting> remindersTable;
	@FXML
	private TableColumn<Meeting, String> title;
	@FXML
	private TableColumn<Meeting, String> comment;
	@FXML
	private TableColumn<Meeting, LocalDate> date;
	@FXML
	private TableColumn<Meeting, LocalTime> time;
	@FXML
	private TableColumn<Meeting, Boolean> finished;
	@FXML
	private JFXButton labelButton;
	@FXML
	private Label text;
	@FXML
	private JFXButton addBtn;
	@FXML
	private JFXButton updateBtn;
	@FXML
	private JFXButton removeBtn;
	@FXML
	private ImageView addImageView;
	@FXML
	private ImageView updateImageView;
	@FXML
	private ImageView removeImageView;
	@FXML
	private JFXListView<String> listView;

	private List<Meeting> reminders;
	private TasksScheduler tasksScheduler;
	private static Timer timer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initImagesAndCss();
		initRemindersTable();
		initListView();

		timer = new Timer();
		tasksScheduler = new TasksScheduler();
		// OLBERTZ Warum ist das ein Kommentar? Warum nicht eine Konstante, deren Name
		// genau das aussagt?
		// 60000 steht fuer eine Minute
		// eine Minute zwischen aufeinanderfolgenden Aufgabenausfuehrungen.
		timer.schedule(tasksScheduler, 0, 60000);

	}

	@FXML
	void addNewReminder(ActionEvent event) {

		addAnimation(addBtn, "swing").play();

		FXMLLoader dialogLoader = new FXMLLoader(NewReminderController.class.getResource(ADD_FXML));
		Dialog<Meeting> addDialog = createDialog(dialogLoader, ADD_TITLE, ADD_STAGE_ICON);

		addDialog.showAndWait();

		initRemindersTable();
	}

	@FXML
	void updateReminder(ActionEvent event) {

		addAnimation(updateBtn, "swing").play();
		updateSelectedReminder();

	}

	@FXML
	void removeReminder(ActionEvent event) throws IOException {

		addAnimation(removeBtn, "swing").play();

		// ueberpruefe , ob mindestens eine Zeile der Tabelle ausgewaehlt ist.
		if (remindersTable.getItems().isEmpty() || remindersTable.getSelectionModel().isEmpty())
			return;
		// alle ausgewaehlten Zeilen.
		ObservableList<Meeting> reminders = remindersTable.getSelectionModel().getSelectedItems();

		// Bestaetigungsdialog vor dem Loeschen anzeigen.
		FXMLLoader dialogLoader = new FXMLLoader();
		DialogPane confirmDeletion = dialogLoader
				.load(ModuleController.class.getResource("/fxmls/ConfirmDeletion.fxml").openStream());
		DeletionController deletionController = (DeletionController) dialogLoader.getController();

		deletionController.setRemindersList(reminders);

		Dialog<Meeting> removeDialog = createDialog(confirmDeletion);
		abilityToCloseFromXButton(removeDialog);
		removeDialog.showAndWait();
		// Tabelle aktualisieren
		initRemindersTable();
	}

	/**
	 * Add Animation to the Label "Reminder"
	 * 
	 * @param event
	 */
	@FXML
	public void animate(ActionEvent event) {
		addAnimation(text, "hinge").play();

	}

	// ------------------Help Methods-------------------------------------

	/**
	 * einer Komponente eine Animation hinzufuegen.
	 * 
	 * @param Knoten der betroffenen Komponente
	 * @param type   (hinge,swing)
	 * @return
	 */
	/*
	 * OLBERTZ So was ist eine wirklich gute private Methode. Ich koennte mir das
	 * aber auch als statische Methode in einer Werkzeugklasse vorstellen mit
	 * Konstanten fuer diese Strings.
	 */
	private AnimationFX addAnimation(Node node, String typ) {

		AnimationFX animationFX = null;

		switch (typ) {
		case "hinge":
			animationFX = new Hinge(node).setResetOnFinished(true);
			break;
		case "swing":
			animationFX = new Swing(node);
			break;
		}

		return animationFX;
	}

	/**
	 *
	 * 
	 * @param removeDialog
	 */
	private void abilityToCloseFromXButton(final Dialog<Meeting> removeDialog) {

		Window window = removeDialog.getDialogPane().getScene().getWindow();
		window.setOnCloseRequest(e -> removeDialog.hide());
	}

	/**
	 * 
	 * @param dialog
	 * @param titel
	 * @param stageIcon
	 */
	private void addSytletoStage(Dialog<Meeting> dialog, String titel, String stageIcon) {
		dialog.setTitle(titel);
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(stageIcon));
	}

	private void initImagesAndCss() {
		/*
		 * OLBERTZ Diesen ganzen Schwanz an Methodenaufrufen wuerde ich auch auslagern,
		 * um die Sache zu vereinfachen. Sie finden ein Beispiel in olbertz.ImageUtils.
		 */
		Image add = new Image(getClass().getResource("/img/add.png").toExternalForm());
		Image update = new Image(getClass().getResource("/img/update.png").toExternalForm());
		Image remove = new Image(getClass().getResource("/img/remove.png").toExternalForm());
		addImageView.setImage(add);
		updateImageView.setImage(update);
		removeImageView.setImage(remove);

		reminderBorderPane.getStylesheets()
				.add(getClass().getResource("/css/reminder/ReminderStyle.css").toExternalForm());
	}

	/**
	 * Initialisiert die Erinnerungstabellenansicht mit den in der Datenbank
	 * gespeicherten Erinnerungen.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initRemindersTable() {

		reminders = ServiceFacade.findAllReminders();

		remindersTable.setItems(FXCollections.observableList(reminders));

		remindersTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		title.setCellValueFactory(new PropertyValueFactory<>(TITLE));
		comment.setCellValueFactory(new PropertyValueFactory<>(COMMENT));
		date.setCellValueFactory(new PropertyValueFactory<>(DATE));
		time.setCellValueFactory(new PropertyValueFactory<>(TIME));
		// finished.setCellValueFactory(new PropertyValueFactory<>(FINISHED));
		finished.setCellValueFactory(c -> {
			Meeting reminder = c.getValue();
			CheckBox checkBox = new CheckBox();

			checkBox.selectedProperty().setValue(reminder.isFinished());
			checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
				reminder.setFinished(new_val);
				remindersTable.getSelectionModel().select(reminder);
				reminder.setFinished(checkBox.isSelected());
				ServiceFacade.updateReminderStatus(reminder);
				remindersTable.getSelectionModel().select(reminder);
				initRemindersTable();
			});

			return new SimpleObjectProperty(checkBox);
		});

		// offne die Erinnerung, wenn sie zweimal mit der linken Maustaste gedrueckt
		// wurde.
		remindersTable.setOnMousePressed(mouseEvent -> {
			if ((mouseEvent.isPrimaryButtonDown()) && (mouseEvent.getClickCount() == 2)) {
				updateSelectedReminder();
			}
		});

		// setToolTips();

		remindersTable.requestFocus();

	}

	@SuppressWarnings("unused")
	private void setToolTips() {
		titleToolTip();
		commentToolTip();
		timeToolTip();
		dateToolTip();
	}

	private void dateToolTip() {
		date.setCellFactory(column -> {
			return new TableCell<Meeting, LocalDate>() {

				@SuppressWarnings("unchecked")
				@Override
				protected void updateItem(LocalDate item, boolean empty) {
					super.updateItem(item, empty);
					TableRow<Meeting> tableRow = getTableRow();

					if (tableRow != null) {
						final Meeting tmp = tableRow.getItem();

						if (item != null && tmp != null) {
							setText(item.toString());
							String remainingTime = DateTime.diffrenceBetweenTodayAnd(LocalDate.parse(tmp.getDate()),
									tmp.getTime());
							Tooltip tooltip = new Tooltip(remainingTime);
//							tooltip.setShowDelay(Duration.seconds(0.5));
//							tooltip.setShowDuration(Duration.seconds(10));
							setTooltip(tooltip);
						}
					}

				}
			};
		});
	}

	private void timeToolTip() {
		time.setCellFactory(column -> {
			return new TableCell<Meeting, LocalTime>() {

				@Override
				protected void updateItem(LocalTime item, boolean empty) {
					super.updateItem(item, empty);
					@SuppressWarnings("unchecked")
					TableRow<Meeting> tableRow = getTableRow();

					if (tableRow != null) {
						final Meeting tmp = tableRow.getItem();

						if (item != null && tmp != null) {
							setText(item.toString());
							String remainingTime = DateTime.diffrenceBetweenTodayAnd(LocalDate.parse(tmp.getDate()),
									tmp.getTime());
							Tooltip tooltip = new Tooltip(remainingTime);
//							tooltip.setShowDelay(Duration.seconds(0.5));
//							tooltip.setShowDuration(Duration.seconds(10));
							setTooltip(tooltip);
						}
					}

				}
			};
		});
	}

	private void commentToolTip() {
		comment.setCellFactory(column -> {
			return new TableCell<Meeting, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (item != null) {
						setText(item);
						Tooltip tooltip = new Tooltip(item);
//						tooltip.setShowDelay(Duration.seconds(0.5));
//						tooltip.setShowDuration(Duration.seconds(10));
						setTooltip(tooltip);

						setOnMouseClicked(mouseEvent -> {
							if ((mouseEvent.isPrimaryButtonDown()) && (mouseEvent.getClickCount() == 2)) {
								updateSelectedReminder();
							}
						});
					}
				}
			};
		});
	}

	private void titleToolTip() {
		title.setCellFactory(column -> {
			return new TableCell<Meeting, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (item != null) {
						setText(item);
						Tooltip tooltip = new Tooltip(item);
//	From Java9 onwards	tooltip.setShowDelay(new Duration(2000));
//						tooltip.setShowDuration(Duration.seconds(10));
						setTooltip(tooltip);
					}
				}
			};
		});
	}

	/**
	 * initialisiert Das ListView mit den Erinnerungstypen.
	 */
	private void initListView() {

		listView.setItems(ReminderType.getReminderTypes());
		// Fit the size of the List view with the number of the cells -size of cell is
		// almost 24px
		listView.setPrefHeight(ReminderType.getReminderTypes().size() * 24 + 15);

		listView.getSelectionModel().selectFirst();

		listView.getSelectionModel().selectedIndexProperty().addListener(new ListSelectChangeListener());

		initRemindersTable();

	}

	private class ListSelectChangeListener implements ChangeListener<Number> {

		@Override
		public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {

			int ix = newVal.intValue();

			if (ix >= 0) {

				String type = listView.getItems().get(ix);
				reminders = getReminderType(type);
				remindersTable.setItems(FXCollections.observableList(reminders));
				remindersTable.requestFocus();
				listView.getSelectionModel().select(ix);

			}
		}
	}

	/*
	 * Ein Erinnerungstyp kann beispielsweise die Zeile "Tomorrow" in der
	 * Listenansicht sein. Wenn man darauf klickt, sollten alle Meetinge von morgen
	 * in der Erinnerungstabelle angezeigt werden.
	 */
	private ObservableList<Meeting> getReminderType(String type) {

		Predicate<Meeting> remPredicate = null;
		List<Meeting> res = ServiceFacade.findAllReminders();

		switch (type) {

		case "ALL Reminders":
			remPredicate = reminder -> true;
			break;

		case "Today":
			remPredicate = reminder -> LocalDate.parse(reminder.getDate()).isEqual(LocalDate.now());
			break;

		case "Tomorrow":
			remPredicate = reminder -> LocalDate.now().isEqual(LocalDate.parse(reminder.getDate()).minusDays(1));
			break;

		case "Missed":
			remPredicate = reminder -> (!reminder.isFinished()
					&& LocalDate.parse(reminder.getDate()).isEqual(LocalDate.now())
					&& LocalTime.now().isAfter(reminder.getTime()))
					|| (!reminder.isFinished() && LocalDate.now().isAfter(LocalDate.parse(reminder.getDate())));
			break;
		case "Finished":
			remPredicate = reminder -> reminder.isFinished();
			break;
		default:
			remPredicate = reminder -> true;
		}

		List<Meeting> result = res.stream().filter(remPredicate).collect(Collectors.toList());

		return FXCollections.observableList(result);
	}

	public static Timer getTimer() {

		return timer;
	}

	public static void cancelTimer() {

		timer.cancel();
	}

	private void updateSelectedReminder() {

		if (remindersTable.getItems().isEmpty() || remindersTable.getSelectionModel().isEmpty()) {
			return;
		}

		Meeting toBeUpdateReminder = remindersTable.getSelectionModel().getSelectedItem();

		UpdateReminderController.setToBeUpdated(toBeUpdateReminder);

		FXMLLoader dialogLoader = new FXMLLoader(UpdateReminderController.class.getResource(UPDATE_FXML));
		Dialog<Meeting> updateDialog = createDialog(dialogLoader, UPDATE_DIALOG, UPDATE_STAGE_ICON);

		updateDialog.showAndWait();

		remindersTable.refresh();
		remindersTable.getSelectionModel().select(toBeUpdateReminder);

	}

	private Dialog<Meeting> createDialog(FXMLLoader dialogLoader, String title, String url) {

		Dialog<Meeting> dialog = new Dialog<Meeting>();

		abilityToCloseFromXButton(dialog);
		addSytletoStage(dialog, title, url);

		try {
			dialog.setDialogPane(dialogLoader.load());

		} catch (IOException e) {

			e.printStackTrace();
		}

		return dialog;

	}

	private Dialog<Meeting> createDialog(DialogPane dialogPane) {

		Dialog<Meeting> dialog = new Dialog<Meeting>();

		dialog.initStyle(StageStyle.TRANSPARENT);
		dialog.getDialogPane().getScene().setFill(Color.TRANSPARENT);

		dialog.setDialogPane(dialogPane);

		return dialog;

	}

	@SuppressWarnings("unused")
	private static <T> void preventColumnReordering(TableView<T> tableView) {
		Platform.runLater(() -> {
			for (Node header : tableView.lookupAll(".column-header")) {
				header.addEventFilter(MouseEvent.MOUSE_DRAGGED, Event::consume);
			}
		});
	}

}
