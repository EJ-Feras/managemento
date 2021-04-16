package dao;

import java.util.List;
import java.util.Set;

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

public class TopicDao {
	private static SessionFactory sessionFactory = new Configuration().configure().addAnnotatedClass(URL.class)
			.addAnnotatedClass(Student.class).addAnnotatedClass(Module.class).addAnnotatedClass(Topic.class)
			.addAnnotatedClass(Project.class).addAnnotatedClass(Meeting.class).buildSessionFactory();

	/***********************************************************
	 * 
	 * Topic
	 * 
	 * @author Feras
	 ***********************************************************/

	public static void updateTopic(Topic thema) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(thema);
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

	public static void saveTopic(Topic thema) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(thema);
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

	/**
	 * Gibt ein bestimmtes Topic zurueck.
	 * 
	 * @param module Das Module, zu dem das Topic gehört.
	 * @param tpoic
	 * @return Topic
	 * @author Feras
	 */
	public static Topic findTopic(Module module, Topic tpoic) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Topic topic = null;
		try {
			tx = session.beginTransaction();

			Query<Topic> query = session.createQuery(
					// Select t -> es gibt ein Objekt von Topic zurueck.
					"SELECT t " + "FROM Module m JOIN m.themen t " + "WHERE m.id = :MODULE_ID AND t.id = :TOPIC_ID",
					Topic.class);

			query.setParameter("MODULE_ID", module.getId());
			query.setParameter("TOPIC_ID", tpoic.getId());

			topic = query.getSingleResult();

			tx.commit();
			return topic;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return topic;

	}

	/**
	 * Gibt alle Themen zurueck , die in dem Module sind.
	 * 
	 * @param module
	 * @return List<Topic>
	 * @author Feras
	 */
	public static List<Topic> findAllTopics(Module module) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Topic> topics = null;
		try {
			tx = session.beginTransaction();

			Query<Topic> query = session.createQuery(
					// Select t -> it returns an object of Topic.
					"SELECT t " + "FROM Module m JOIN m.themen t " + "where m.id = :Value ", Topic.class);

			query.setParameter("Value", module.getId());
			topics = query.list();

			tx.commit();
			return topics;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return topics;

	}

	/**
	 * das Topic loeschen, ohne eine andere Entitaet zu beeinflussen.
	 * 
	 * @param topic
	 * @author Feras
	 */
	public static void deleteTopic(Topic topic) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			Topic toBeDeleted = session.load(Topic.class, topic.getId());

			// das Topic von allen Moduleen und Projecten, zu denen es gehört,Trennen .
			Set<Module> modules = toBeDeleted.getModules();
//			Set<Project> projects = toBeDeleted.getProjectsList();

			for (Module modul : modules) {
				List<Topic> topics = modul.getTopics();
				// toBeDeleted.removeModulee(modul);
				topics.remove(toBeDeleted);
			}

//			for (Project project : projects) {
//				project.removeTopic(toBeDeleted);
//			}

			session.flush();

			// loesche es aus der DB
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
