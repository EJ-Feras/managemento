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

public class UrlDao {
	private static SessionFactory sessionFactory = new Configuration().configure().addAnnotatedClass(URL.class)
			.addAnnotatedClass(Student.class).addAnnotatedClass(Module.class).addAnnotatedClass(Topic.class)
			.addAnnotatedClass(Project.class).addAnnotatedClass(Meeting.class).buildSessionFactory();


	/***********************************************************
	 * 
	 * URL
	 * 
	 * @author Feras
	 ***********************************************************/
	public static void addURL(URL url) {

		Session session = sessionFactory.openSession();

		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(url);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {

			session.close();
		}

	}

	public static void updateURL(URL url) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.update(url);
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

	public static List<URL> findURLs(Project project) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<URL> urls = null;
		try {
			tx = session.beginTransaction();

			Query<URL> query = session.createQuery(
					// Select u -> Es gibt ein Objekt des Linkes zurueck
					"Select u " + "From Project p JOIN p.urls u " + "where p.id = :Value ", URL.class);

			query.setParameter("Value", project.getId());
			urls = query.list();

			tx.commit();

			return urls;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return urls;
	}

	public static void deleteURL(URL url) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			URL toBeDeleted = session.load(URL.class, url.getId());
			Project project = toBeDeleted.getProject();
			// einen Link vom Project , zu dem er gehört trennen
			project.removeUrl(toBeDeleted);

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
