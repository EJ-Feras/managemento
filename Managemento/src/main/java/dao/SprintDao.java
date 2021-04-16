package dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import model.Meeting;
import model.Module;
import model.Project;
import model.Sprint;
import model.Student;
import model.Topic;
import model.URL;

public class SprintDao {

	private static SessionFactory sessionFactory = new Configuration().configure().addAnnotatedClass(URL.class)
			.addAnnotatedClass(Student.class).addAnnotatedClass(Module.class).addAnnotatedClass(Topic.class)
			.addAnnotatedClass(Project.class).addAnnotatedClass(Meeting.class).buildSessionFactory();

	/***********************************************************
	 * 
	 * Sprint
	 * 
	 * @author Feras
	 ***********************************************************/

	public static void saveSprint(Sprint sprint) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.save(sprint);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.clear();
			session.close();
		}

	}

	public static void updateSprint(Sprint sprint) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.update(sprint);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.clear();
			session.close();
		}

	}

	public static List<Sprint> findAllSprints(Project project) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Sprint> sprints = null;
		try {
			tx = session.beginTransaction();

			Query<Sprint> query = session.createQuery(
					// Select t -> it returns an object of Sprint.
					"SELECT s " + "FROM Project p JOIN p.sprints s " + "WHERE p.id = :Value ", Sprint.class);

			query.setParameter("Value", project.getId());
			sprints = query.list();

			tx.commit();
			return sprints;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return sprints;

	}

	public static Integer findSprintsNumber(Project project) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Integer numberOfSprints = null;
		try {
			tx = session.beginTransaction();

			Query<Sprint> query = session.createQuery(
					// Select t -> it returns an object of Sprint.
					"SELECT s " + "FROM Project p JOIN p.sprints s " + "WHERE p.id = :Value ", Sprint.class);

			query.setParameter("Value", project.getId());
			numberOfSprints = query.list().size();

			tx.commit();
			return numberOfSprints;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return numberOfSprints;

	}

	public static void deleteSprint(Sprint sprint) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Sprint toBeDeleted = session.load(Sprint.class, sprint.getId());
			Project project = toBeDeleted.getProject();
			// dieses Sprint vom Project , zu dem es gehört trennen.
			project.removeSprint(toBeDeleted);

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
