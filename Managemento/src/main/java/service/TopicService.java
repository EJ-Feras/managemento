package service;

import java.util.List;

import dao.TopicDao;
import model.Module;
import model.Topic;

public class TopicService {

	/**
	 * ein neues Topic hinzufuegen.
	 * 
	 * @param thema
	 * @author Feras
	 */
	public static void saveTopic(Topic thema) {
		TopicDao.saveTopic(thema);

	}

	/**
	 * ein Topic aktualisieren.
	 * 
	 * @param thema
	 * @author Feras
	 */
	public static void updateTopic(Topic thema) {
		TopicDao.updateTopic(thema);
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
		return TopicDao.findTopic(module, tpoic);
	}

	/**
	 * Gibt alle Themen zurueck , die in dem Module uebergebenen sind.
	 * 
	 * @param module
	 * @return List<Topic>
	 * @author Feras
	 */
	public static List<Topic> findAllTopics(Module module) {
		return TopicDao.findAllTopics(module);
	}

	/**
	 * das Topic loeschen, ohne eine andere Entitaet zu beeinflussen.
	 * 
	 * @param topic
	 * @author Feras
	 */
	public static void deleteTopic(Topic topic) {
		TopicDao.deleteTopic(topic);
	}

}
