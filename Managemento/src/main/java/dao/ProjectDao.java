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
import model.Student;
import model.Topic;
import model.URL;

public class ProjectDao {
	private static SessionFactory sessionFactory = new Configuration().configure().addAnnotatedClass(URL.class)
			.addAnnotatedClass(Student.class).addAnnotatedClass(Module.class).addAnnotatedClass(Topic.class)
			.addAnnotatedClass(Project.class).addAnnotatedClass(Meeting.class).buildSessionFactory();

	
	/***********************************************************
	 * 
	 * Project
	 * 
	 * @author Feras
	 ***********************************************************/
	/**
	 * Das uebergebene Project Speichern oder aktualisieren .
	 * 
	 * @param project
	 * @author Feras
	 */

	public static void saveORUpdate(Project project) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(project);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {

			session.close();
		}

	}

	public static void updateProject(Project project) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.update(project);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {

			session.close();
		}

	}

	/**
	 * Project loeschen.
	 * 
	 * @param project
	 * @author Feras
	 */
	public static void deleteProject(Project project) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Project toBeDeleted = session.load(Project.class, project.getId());
			Topic thema = toBeDeleted.getTopic();
			Module module = toBeDeleted.getModule();
			// dieses Project vom Module und dem Topic, zu dem es gehört trennen.
			module.removeProject(toBeDeleted);
			if (thema != null)
				thema.removeProject(toBeDeleted);

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

	public static Project findProject(Project project) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Project projectFromDB = null;
		try {
			tx = session.beginTransaction();
			projectFromDB = session.load(Project.class, project.getId());
			tx.commit();

			return projectFromDB;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return projectFromDB;

	}

	/**
	 * Gibt alle Projecte von einem bestimmten Module zurueck.
	 * 
	 * @param module
	 * @return List<Project>
	 * @author Feras
	 */
	public static List<Project> findAllProjects(Module module) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Project> modules = null;
		try {
			tx = session.beginTransaction();

			Query<Project> query = session.createQuery("FROM Project p " + "WHERE p.modul.id = :Value ", Project.class);
			query.setParameter("Value", module.getId());
			modules = query.list();

			tx.commit();
			return modules;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return modules;

	}

}
