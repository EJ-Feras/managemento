package dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

public class StatisticsDao {

	private static SessionFactory sessionFactory = new Configuration().configure().addAnnotatedClass(URL.class)
			.addAnnotatedClass(Student.class).addAnnotatedClass(Module.class).addAnnotatedClass(Topic.class)
			.addAnnotatedClass(Project.class).addAnnotatedClass(Meeting.class).buildSessionFactory();

	/***********************************************************
	 * 
	 * Statistiken
	 * 
	 * @author Feras
	 ***********************************************************/

	/**
	 * Gibt eine Liste mit den eindeutigen Namen aller Themen zurueck.
	 * 
	 * @return List<Topic>
	 * @author Feras
	 */

	public static List<String> findDistinctTopics() {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<String> distinctTopics = null;
		try {
			tx = session.beginTransaction();

			Query<String> query = session.createQuery("SELECT DISTINCT name FROM Topic", String.class);

			distinctTopics = query.list();

			tx.commit();
			return distinctTopics;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return distinctTopics;

	}

	/**
	 * Gibt eine <String, Long> HashMap zuruckk, mit String fur den Themennamen und
	 * Long fuer die Haeufigkeit
	 * 
	 * @return HashMap <String,Long>
	 * @author Feras
	 */
	public static Map<String, Long> findTopicsFrequency() {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Map<String, Long> topicsFrequency = new LinkedHashMap<>();
		try {
			tx = session.beginTransaction();

			String HQL = "SELECT t.name, count(*) " + "FROM Topic t  join t.projectsList pl " + "group by t.name "
					+ "order by count(*) DESC";

			Query<Object[]> query = session.createQuery(HQL, Object[].class);
			List<Object[]> list = query.list();
			for (Object[] objects : list) {
				String topicName = (String) objects[0];
				Long frequency = (Long) objects[1];
				topicsFrequency.put(topicName, frequency);
			}

			tx.commit();
			return topicsFrequency;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return topicsFrequency;
	}

	/**
	 * Gibt eine <Double, Long> HashMap zuruckk, mit Double fur den Noten und Long
	 * fuer die Haeufigkeit der Note.
	 * 
	 * @return
	 * @author Feras
	 */
	public static Map<Double, Long> findGradesFrequency() {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Map<Double, Long> gradesFrequency = new LinkedHashMap<>();
		try {
			tx = session.beginTransaction();

			String HQL = "SELECT  note, count(*) " + "FROM Project " + "where note is not null " + "group by note "
					+ "order by count(*) DESC";

			Query<Object[]> query = session.createQuery(HQL, Object[].class);
			List<Object[]> list = query.list();
			for (Object[] objects : list) {
				Double grade = (Double) objects[0];
				Long frequency = (Long) objects[1];
				gradesFrequency.put(grade, frequency);
			}

			tx.commit();
			return gradesFrequency;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return gradesFrequency;
	}
}
