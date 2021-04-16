package service;

import java.util.List;

import dao.MeetingDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Meeting;

public class ReminderService {

	/***********************************************************
	 * 
	 * Meeting
	 * 
	 * @author Feras
	 ***********************************************************/

	public static List<Meeting> findAllReminders() {
		ObservableList<Meeting> allReminders = FXCollections.observableList(MeetingDao.findAllReminders());
		return allReminders;
	}

	public static Meeting findReminder(Meeting rem) {

		return MeetingDao.findReminder(rem);
	}

	public static void addReminder(Meeting reminder) {
		MeetingDao.addReminder(reminder);
	}

	public static void updateReminder(Meeting oldRem) {
		MeetingDao.updateReminder(oldRem);
	}

	public static void updateReminderStatus(Meeting oldRem) {
		MeetingDao.updateReminderStatus(oldRem);
	}

	public static void deleteReminder(Meeting reminder) {
		MeetingDao.deleteReminder(reminder);
	}

}
