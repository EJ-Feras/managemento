package service;

import java.util.List;

import dao.ProjectDao;
import model.Module;
import model.Project;

public class ProjectService {

	/**
	 * ein projekt hinzufuegen oder aktualisieren.
	 * 
	 * @param project
	 * @author Feras
	 */
	public static void saveORUpdate(Project project) {
		ProjectDao.saveORUpdate(project);
	}

	/**
	 * ein projekt loeschen.
	 * 
	 * @param project
	 * @author Feras
	 */
	public static void deleteProject(Project project) {
		ProjectDao.deleteProject(project);
	}

	/**
	 * Gibt ein bestimmtes Project zuruck
	 * 
	 * @param project
	 * @return Project
	 * @author Feras
	 */
	public static Project findProject(Project project) {
		return ProjectDao.findProject(project);
	}

	/**
	 * 
	 * Gibt alle Projekte zuruck,die zu dem uebergegebenen Module gehoeren.
	 * 
	 * @param module
	 * @return List<Project>
	 * @author Feras
	 */
	public static List<Project> findAllProjects(Module module) {
		List<Project> modules = ProjectDao.findAllProjects(module);
		return modules;
	}

}
