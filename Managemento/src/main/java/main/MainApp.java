package main;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import controller.HomeController;
import controller.remindercontroller.ReminderController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.ResizeHelper;

public class MainApp extends Application {

	public static Stage stage;
	private static MainApp mInstance;

	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		mInstance = new MainApp();
		
		stage = primaryStage;
		//um Warnungen zu unterdrucken
		Logger.getRootLogger().setLevel(Level.OFF);
	
		FXMLLoader loader = new FXMLLoader(HomeController.class.getResource("/fxmls/Home.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root, 980, 620);
		scene.getStylesheets().add(getClass().getResource("/css/HomeStyle.css").toExternalForm());
		
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.getIcons().add(new Image("/img/logo.png"));
		primaryStage.setMinWidth(980);
		primaryStage.setMinHeight(625);
		primaryStage.setScene(scene);
		primaryStage.setResizable(true);
		primaryStage.setOnCloseRequest(event -> {
			Platform.exit();
		});
		primaryStage.setAlwaysOnTop(true);
		primaryStage.setAlwaysOnTop(false);
		primaryStage.show();
		ResizeHelper.addResizeListener(primaryStage);
       
	}

	
	@Override
	public void stop() throws Exception {
		super.stop();
		if(ReminderController.getTimer() != null)
			ReminderController.cancelTimer();
		
		System.exit(0);
	}
	
	 public static MainApp getInstance() {
	        return mInstance;
	   }
}
