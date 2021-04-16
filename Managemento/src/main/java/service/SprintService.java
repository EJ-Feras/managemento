package service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dao.SprintDao;
import model.Project;
import model.Sprint;

public class SprintService {

	/**
	 * Gibt eine Liste mit allen Sprints eines bestimmten Projektes.
	 * 
	 * @param project
	 * @return Liste
	 * @author Feras
	 */
	public static List<Sprint> findAllSprints(Project project) {
		return SprintDao.findAllSprints(project);
	}

	/**
	 * 
	 * Gibt die aktuelle Anzahl der Sprints in einem Project zuruck
	 * 
	 * @param project
	 * @return Anzahl der Sprints in dem Project
	 * @author Feras
	 *
	 */
	public static Integer findSprintsNumber(Project project) {
		return SprintDao.findSprintsNumber(project);
	}

	/**
	 * 
	 * ein Sprint abspeichern.
	 * 
	 * @param project
	 * @return Anzahl der Sprints in dem Project
	 * @author Feras
	 *
	 */
	public static void saveSprint(Sprint sprint) {

		SprintDao.saveSprint(sprint);

	}

	/**
	 * ein Spirnt aktualisieren.
	 * 
	 * @param sprint
	 * @author Feras
	 */
	public static void updateSprint(Sprint sprint) {

		SprintDao.updateSprint(sprint);

	}

	/**
	 * ein Sprint loeschen
	 * 
	 * @param sprint
	 * @author Feras
	 */
	public static void deleteSprint(Sprint sprint) {

		SprintDao.deleteSprint(sprint);
	}

	/**
	 * Sprints nach dem Anfangsdatum sortieren.
	 * 
	 * @param project
	 * @return List<Sprint>
	 * @author Feras
	 */
	public static List<Sprint> findSortedSprints(Project project) {

		List<Sprint> allSprints = findAllSprints(project);

		Collections.sort(allSprints, new Comparator<Sprint>() {
			public int compare(Sprint s1, Sprint s2) {
				if (s1.getStart() == null || s2.getStart() == null)
					return 0;
				return s1.getStart().compareTo(s2.getStart());
			}
		});

		int i = 1;
		for (Sprint sprint : allSprints) {
			sprint.setNumber(i++);
			updateSprint(sprint);
		}

		return allSprints;
	}

}
