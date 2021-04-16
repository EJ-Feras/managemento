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

public class ModuleDao {
	private static SessionFactory sessionFactory = new Configuration().configure().addAnnotatedClass(URL.class)
			.addAnnotatedClass(Student.class).addAnnotatedClass(Module.class).addAnnotatedClass(Topic.class)
			.addAnnotatedClass(Project.class).addAnnotatedClass(Meeting.class).buildSessionFactory();

	
	/***********************************************************
	 * 
	 * Module
	 * 
	 * @author Feras
	 ***********************************************************/

	/**
	 * ein Module speichern oder aktualisieren .
	 * 
	 * @param modul
	 * @author Feras
	 */
	public static void addModule(Module modu) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(modu);
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

	public static void updateModule(Module module) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.update(module);
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
	 * Gibt alle Modulee zurueck.
	 * 
	 * @return List<Module>
	 * @author Feras
	 */
	public static List<Module> findAllModules() {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Module> modules = null;
		try {
			tx = session.beginTransaction();
			modules = session.createQuery("FROM Module", Module.class).list();
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

	/**
	 * Gibt den Module mit dem uebergegebenen Namen zurueck.
	 * 
	 * @return List<Module>
	 * @author Feras
	 */
	public static Module findModuleByName(String name) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Module> modules = null;
		Module lastModuleeWithSameName = null;
		try {
			tx = session.beginTransaction();
			Query<Module> query = session.createQuery("FROM Module m " + "WHERE m.name = :VALUE ", Module.class);

			query.setParameter("VALUE", name);
			modules = query.list();
			tx.commit();

			if (modules.size() > 0)
				lastModuleeWithSameName = modules.get(modules.size() - 1);

			return lastModuleeWithSameName;

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return lastModuleeWithSameName;

	}

	/**
	 * ein bestimmtes Module loeschen.
	 * 
	 * @param module
	 * @author Feras
	 */
	public static void deleteModule(Module module) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Module toBeDeleted = session.load(Module.class, module.getId());
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

	/**
	 * 
	 * @param toBeUpdated
	 * @param name
	 * @author Feras
	 */
	public static void updateModule(Module toBeUpdated, String name) { // TODO
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Module module = (Module) session.get(Module.class, toBeUpdated.getId());
			module.setName(name);
			session.update(module);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}


	public static void updateModule(Module toBeUpdated, Topic topic) { 
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Module module = (Module) session.get(Module.class, toBeUpdated.getId());

			module.addTopic(topic);

			session.update(module);
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
