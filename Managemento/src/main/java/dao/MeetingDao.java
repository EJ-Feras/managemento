package dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import model.Meeting;
import model.Module;
import model.Project;
import model.Student;
import model.Topic;
import model.URL;

public class MeetingDao {

	private static SessionFactory sessionFactory = new Configuration().configure().addAnnotatedClass(URL.class)
			.addAnnotatedClass(Student.class).addAnnotatedClass(Module.class).addAnnotatedClass(Topic.class)
			.addAnnotatedClass(Project.class).addAnnotatedClass(Meeting.class).buildSessionFactory();

	/***********************************************************
	 * 
	 * Meeting
	 * 
	 * @author Feras
	 ***********************************************************/

	public static List<Meeting> findAllReminders() {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Meeting> reminders = null;
		try {
			tx = session.beginTransaction();
			// Wir verwenden den Namen der Entitaetsklasse und nicht den Namen der Tabelle
			// in
			// der Datenbank, indem wir Daten abrufen.
			reminders = session.createQuery("FROM Meeting", Meeting.class).list();

			tx.commit();

			return reminders;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return reminders;

	}

	public static Meeting findReminder(Meeting rem) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Meeting reminder = null;
		try {
			tx = session.beginTransaction();
			reminder = session.load(Meeting.class, rem.getId());
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();

		}

		return reminder;

	}

	public static void addReminder(Meeting reminder) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.save(reminder);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	public static void updateReminder(Meeting oldRem) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Meeting reminderFromDb = session.load(Meeting.class, oldRem.getId());
			reminderFromDb.setTitle(oldRem.getTitle());
			reminderFromDb.setComment(oldRem.getComment());
			reminderFromDb.setDate(String.valueOf(oldRem.getDate()));
			reminderFromDb.setTime(oldRem.getTime());
			reminderFromDb.setFinished(oldRem.isFinished());

			session.update(reminderFromDb);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public static void updateReminderStatus(Meeting oldRem) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Meeting reminderFromDB = session.load(Meeting.class, oldRem.getId());

			reminderFromDB.setFinished(oldRem.isFinished());

			session.update(reminderFromDB);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public static void deleteReminder(Meeting reminder) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Meeting toBeDeleted = session.load(Meeting.class, reminder.getId());
			session.delete(toBeDeleted);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}
