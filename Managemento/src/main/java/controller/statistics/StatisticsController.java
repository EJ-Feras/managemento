package controller.statistics;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import facades.ServiceFacade;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.scene.chart.NumberAxis;

/**
 * 
 * Statistiken ueber die Haeufigkeit der Auswahl eines Themas oder einer Note
 * 
 * @author Feras
 *
 */

public class StatisticsController implements Initializable {
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private PieChart pieChart;
	@FXML
	private BarChart<String, Number> barChart;
	@FXML
	private CategoryAxis categoryAxis;
	@FXML
	private NumberAxis numberAxis;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initTpoicsFrequencyBarChart();
		initGradesFrequencyPieChart();

		anchorPane.getStylesheets().add(getClass().getResource("/css/ChartStyle.css").toExternalForm());
	}

	/**
	 * wie oft eine projektnote vergeben wurde.
	 * 
	 * @author Feras
	 * 
	 */
	private void initGradesFrequencyPieChart() {

		Map<Double, Long> freq = ServiceFacade.findGradesFrequency();

		freq.forEach((note, frequency) -> {

			if (note != 0.0)
				pieChart.getData().add(new Data(String.valueOf(note), frequency));
		});

		pieChart.setStartAngle(90.0);
		pieChart.setTitle("Grades frequency");

		pieChart.getData().stream().forEach(data -> {
			Tooltip tooltip = new Tooltip();
			hackTooltipStartTiming(tooltip);
			tooltip.setText("(" + data.getName() + "): " + (int) data.getPieValue() + " times");
			Tooltip.install(data.getNode(), tooltip);
			data.pieValueProperty().addListener((observable, oldValue, newValue) -> tooltip.setText(newValue + ""));
		});
	}

	/**
	 * wie oft ein Thema ausgewaehlt wurde.
	 * 
	 * @author Feras
	 */
	private void initTpoicsFrequencyBarChart() {
		// alle Themen mit ihren Haeufigkeit aus der DB
		List<String> topicsList = ServiceFacade.findDistinctTopics();
		Map<String, Long> freq = ServiceFacade.findTopicsFrequency();
		for (String topic : topicsList) {
			if (!freq.containsKey(topic))
				freq.put(topic, 0L);
		}

		barChart.setTitle("Topics frequency");
		// auf der NumberAxis ganzzahlige Werte anstelle von double anzeigen
		numberAxis.setTickLabelFormatter(new StringConverter<Number>() {

			@Override
			public String toString(Number object) {
				if (object.intValue() != object.doubleValue())
					return "";
				return "" + (object.intValue());
			}

			@Override
			public Number fromString(String string) {
				Number val = Double.parseDouble(string);
				return val.intValue();
			}
		});
		numberAxis.setMinorTickVisible(false);

		// dem Diagramm Daten hinzufuegen.
		freq.forEach((name, frequency) -> {
			Series<String, Number> series = new XYChart.Series<>();

			series.getData().add(new XYChart.Data<String, Number>(name, frequency));
			barChart.getData().add(series);
		});

	}

	@FXML
	void changeSlicesPosition(MouseEvent event) {
		PieChart.Data removed = pieChart.getData().remove(pieChart.getData().size() - 1);
		pieChart.getData().add(0, new PieChart.Data(removed.getName(), removed.getPieValue()));
	}

	/*
	 * https://stackoverflow.com/questions/26854301/how-to-control-the-javafx-
	 * tooltips-delay
	 */
	private static void hackTooltipStartTiming(Tooltip tooltip) {
		try {
			java.lang.reflect.Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
			fieldBehavior.setAccessible(true);
			Object objBehavior = fieldBehavior.get(tooltip);

			java.lang.reflect.Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
			fieldTimer.setAccessible(true);
			Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

			objTimer.getKeyFrames().clear();
			objTimer.getKeyFrames().add(new KeyFrame(new Duration(150)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
