package service;

import java.util.List;

import dao.UrlDao;
import model.Project;
import model.URL;

public class UrlService {

	/**
	 * einen neune URL hinzufuegen
	 * 
	 * @param url
	 * @author Feras
	 */

	public static void addURL(URL url) {
		UrlDao.addURL(url);
	}

	/**
	 * Gibt eine Liste mit allen URL, die zu dem uebergegebenen Project gehoeren.
	 * 
	 * @param project
	 * @return List<URL>
	 * @author Feras
	 */
	public static List<URL> findURLs(Project project) {
		return UrlDao.findURLs(project);
	}

	/**
	 * 
	 * einen URL aktualisieren.
	 * 
	 * @param url
	 * @author Feras
	 */

	public static void updateURL(URL url) {
		UrlDao.updateURL(url);
	}

	/**
	 * einen URL loeschen
	 * 
	 * @param url
	 * @author Feras
	 */
	public static void deleteURL(URL url) {
		UrlDao.deleteURL(url);
	}
}
