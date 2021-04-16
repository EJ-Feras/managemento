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

public class StudentDao {
	private static SessionFactory sessionFactory = new Configuration().configure().addAnnotatedClass(URL.class)
			.addAnnotatedClass(Student.class).addAnnotatedClass(Module.class).addAnnotatedClass(Topic.class)
			.addAnnotatedClass(Project.class).addAnnotatedClass(Meeting.class).buildSessionFactory();

	/***********************************************************
	 * 
	 * Student
	 * 
	 * 
	 ***********************************************************/

	public static void addStudent(Student student) {

		Session session = sessionFactory.openSession();

		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(student);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {

			session.close();
		}

	}

	public static void updateStudent(Student toUpdate) {
		Session session = sessionFactory.openSession();

		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Student studentFromDb = session.load(Student.class, toUpdate.getMatriculationNumber());
			studentFromDb.setMatriculationNumber(toUpdate.getMatriculationNumber());
			studentFromDb.setFirstName(toUpdate.getFirstName());
			studentFromDb.setLastName(toUpdate.getLastName());
			studentFromDb.setGrade(toUpdate.getGrade());
			studentFromDb.setProject(toUpdate.getProject());

			session.saveOrUpdate(studentFromDb);
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
	 * Gibt den Student mit der gegebenen MatrikelNr zurueck.
	 * 
	 * @param matriculationNumber
	 * @return Student
	 */
	public static Student findStudent(int matriculationNumber) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Student student = null;
		try {
			tx = session.beginTransaction();
			student = session.load(Student.class, matriculationNumber);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();

		}

		return student;

	}

	/**
	 * Gibt List mit allen Studenten zurueck.
	 * 
	 * @return liste mit allen Studenten
	 */
	public static List<Student> findStudents() {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Student> students = null;
		try {
			tx = session.beginTransaction();
			students = session.createQuery("FROM Student", Student.class).list();
			tx.commit();

			return students;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return students;
	}

	/**
	 * Gibt eine Liste mit allen Studenten eines bestimmten Projects zuruck.
	 * 
	 * @return liste mit allen Studenten eines bestimmten Projects
	 * @author Feras
	 */
	public static List<Student> findStudents(Project project) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Student> students = null;
		try {
			tx = session.beginTransaction();

			Query<Student> query = session.createQuery(
					// Select s -> Es gibt ein Objekt des Studenten zurueck..
					"SELECT s " + "FROM Project p JOIN p.students s " + "WHERE p.id = :Value ", Student.class);

			query.setParameter("Value", project.getId());
			students = query.list();

			tx.commit();

			return students;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return students;
	}

	/**
	 * Student von einem zugehörigen Project trennen und dann aus der DB loeschen.
	 * 
	 * @param student
	 * @author Feras
	 */
	public static void deleteStudent(Student student) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Student toBeDeleted = session.load(Student.class, student.getMatriculationNumber());
			Project project = toBeDeleted.getProject();
			// den Student vom Project , zu dem er gehört trennen
			project.removeStudent(toBeDeleted);

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
	 * @param matriculationNumber
	 * @return True,ob die Matrikelnummer in der Datenbank gefunden wurde
	 */
	public static Boolean exists(int matriculationNumber) {
		Session session = sessionFactory.openSession();
		try {

			Query<Integer> query = session.createQuery(
					"SELECT s.matriculationNumber " + "FROM Student s " + "WHERE s.matriculationNumber = :VALUE", Integer.class);
			query.setParameter("VALUE", matriculationNumber);

			return (query.uniqueResult() != null);
		} finally {
			session.close();
		}

	}

}
