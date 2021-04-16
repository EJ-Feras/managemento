package service;

import java.util.List;


import dao.ModuleDao;
import model.Module;
import model.Topic;


public class ModuleService {



	/**
	 * ein Module speichern oder aktualisieren .
	 * 
	 * @param modul
	 * @author Feras
	 */
	public static void addModule(Module modul) {
		ModuleDao.addModule(modul);
	}

	/**
	 * Gibt alle Module zurueck.
	 * 
	 * @return List<Module>
	 * @author Feras
	 */
	public static List<Module> findAllModules() {
		return ModuleDao.findAllModules();
	}
	
	public static Module findModuleByName(String name) {
		return ModuleDao.findModuleByName(name);
	}

	/**
	 * ein bestimmtes Module loeschen.
	 * 
	 * @param module
	 * @author Feras
	 */
	public static void deleteModule(Module module) {
		ModuleDao.deleteModule(module);
	}

	public static void updateModule(Module modul) {
		ModuleDao.updateModule(modul);
	}

	/**
	 * 
	 * @param toBeUpdated
	 * @param name
	 * @author Feras
	 */
	public static void updateModule(Module toBeUpdated, String name) { 
		ModuleDao.updateModule(toBeUpdated, name);
	}

	public static void updateModule(Module toBeUpdated, Topic topic) { 
		ModuleDao.updateModule(toBeUpdated, topic);
	}

}
