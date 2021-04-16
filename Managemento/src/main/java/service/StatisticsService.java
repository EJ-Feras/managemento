package service;


import java.util.List;
import java.util.Map;


import dao.StatisticsDao;

public class StatisticsService {

	/**
	 * Gibt eine Liste mit den eindeutigen Namen aller Themen zurueck.
	 * 
	 * @return List<Thema>
	 * @author Feras
	 */
	public static List<String> findDistinctTopics() {
		return StatisticsDao.findDistinctTopics();
	}
	
	
	/**
	 * Gibt eine <String, Long> HashMap zuruckk, mit String fur den Themennamen und
	 * Long fuer die Haeufigkeit
	 * 
	 * @return HashMap <String,Long>
	 * @author Feras
	 */
	public static Map<String, Long> findTopicsFrequency() {
		return StatisticsDao.findTopicsFrequency();
	}

	/**
	 * Gibt eine <Double, Long> HashMap zuruckk, mit Double fur den Noten und Long
	 * fuer die Haeufigkeit der Note.
	 * 
	 * @return
	 * @author Feras
	 */
	public static Map<Double,Long> findGradesFrequency(){
		return StatisticsDao.findGradesFrequency();
	}
	
	
}
