package service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimerTask;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.github.plushaze.traynotification.animations.Animations;
import com.github.plushaze.traynotification.notification.Notification;
import com.github.plushaze.traynotification.notification.Notifications;
import com.github.plushaze.traynotification.notification.TrayNotification;

import facades.ServiceFacade;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import model.Meeting;

/**
 * Dies kann verwendet werden, um eine Erinnerung zu planen, die zu einem
 * bestimmten Zeitpunkt in der Zukunft angezeigt werden soll.
 * 
 * @author Feras
 *
 */
public class TasksScheduler extends TimerTask {

	// Praedikate, die angeben, wann die Erinnerung angezeigt werden soll.
	private final static Predicate<Meeting> TODAY = m -> m.getDate().equals(LocalDate.now().toString());
	private final static Predicate<Meeting> TIME_NOW = m -> m.getTime().toString().equals(formatTimeInHoursAndMinutes(LocalTime.now()));
	private final static Predicate<Meeting> TIME_BEFORE_ONE_HOUR = m -> m.getTime().toString()
			.equals(formatTimeInHoursAndMinutes(LocalTime.now().minusHours(1)));
	private final static Predicate<Meeting> TIME_BEFORE_THREE_HOURS = m -> m.getTime().toString()
			.equals(formatTimeInHoursAndMinutes(LocalTime.now().minusHours(3)));

	public TasksScheduler() {
	}

	@Override
	public void run() {

		List<Meeting> toDo = getTasks();

		showNotifications(toDo);

	}

	private List<Meeting> getTasks() {
		return ServiceFacade.findAllReminders().stream()
				.filter(TODAY.and(TIME_NOW).or(TODAY.and(TIME_BEFORE_ONE_HOUR)).or(TODAY.and(TIME_BEFORE_THREE_HOURS)))
				.collect(Collectors.toList());
	}

	private void showNotifications(List<Meeting> toDo) {
		for (Meeting r : toDo) 
			Platform.runLater(() -> createNotitfication(r));

	}

	private void createNotitfication(Meeting reminder) {
		Image img = new Image("/img/bell.png");

		Notification notification = Notifications.INFORMATION;
		TrayNotification tray = new TrayNotification();

		tray.setNotification(notification);
		tray.setTitle(formatTitle(reminder));
		tray.setMessage(formatMessage(reminder));
		tray.setRectangleFill(Paint.valueOf("#BCB0A7"));
		tray.setAnimation(Animations.SLIDE);
		tray.setImage(img);
		tray.showAndWait();

		new Sound().playSound("/sounds/ping.wav");

	}

	private String formatTitle(Meeting reminder) {
		String result = "";
		if (reminder.getTitle().length() > 25)
			result = "<" + reminder.getTitle().substring(0, 20) + "...>" + "at: " + reminder.getTime();
		else
			result = "<" + reminder.getTitle() + ">" + "at: " + reminder.getTime();
		return result;
	}

	private String formatMessage(Meeting reminder) {
		String result = "";
		if (reminder.getComment().length() > 50)
			result = reminder.getComment().substring(0, 50) + "...";
		else
			result = reminder.getComment();

		return result;

	}

	private static String formatTimeInHoursAndMinutes(LocalTime time) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		return time.format(dtf);
	}

}
