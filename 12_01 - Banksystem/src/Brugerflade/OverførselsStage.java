package Brugerflade;

import java.sql.SQLException;

import DB.DB;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.NumberStringConverter;

// PENIS!

public class OverførselsStage extends Application {

	public void start(Stage stage) {
		DB db = new DB();

		stage.setTitle("Overførsel - Lortebank A/S");
		stage.getIcons().add(new Image("Brugerflade/ico.png"));

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text navn = new Text("Overførsel");
		HBox hbNavn = new HBox(10);
		grid.add(hbNavn, 0, 0, 1, 1);
		hbNavn.getChildren().add(navn);
		navn.setId("logo");

		Button close = new Button("X");
		HBox hbClose = new HBox(10);
		close.setId("close");
		hbClose.getChildren().add(close);
		hbClose.setAlignment(Pos.TOP_RIGHT);
		grid.add(hbClose, 1, 0);

		close.setOnAction(e -> {
			stage.close();
		});

		Label beløb = new Label("Beløb: ");
		beløb.setId("labelting");
		grid.add(beløb, 0, 1);

		TextField beløbfelt = new TextField();
		beløbfelt.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
		grid.add(beløbfelt, 1, 1);

		Label frakonto = new Label("Hæves på: ");
		frakonto.setId("labelting");
		grid.add(frakonto, 0, 2);

		ObservableList<String> Kundekontoer = FXCollections.observableArrayList("Konto1", "Konto2", "Konto3");
		final ComboBox senderfelt = new ComboBox(Kundekontoer);
		HBox hbSendefelt = new HBox();
		hbSendefelt.getChildren().add(senderfelt);
		senderfelt.setPrefWidth(175);
		grid.add(hbSendefelt, 1, 2);

//		TextField senderfelt = new TextField();
//		grid.add(senderfelt, 1, 2);

		Label tilKonto = new Label("Modtager: ");
		tilKonto.setId("labelting");
		grid.add(tilKonto, 0, 3);

		TextField modtagerfelt = new TextField();
		grid.add(modtagerfelt, 1, 3);

		CheckBox fastOverførsel = new CheckBox("Fast overførsel");
		fastOverførsel.setId("labelting");
		grid.add(fastOverførsel, 0, 4, 2, 4);

		ObservableList<String> options = FXCollections.observableArrayList("Dagligt", "Ugentligt", "Månedligt",
				"Kvartaligt", "Halvårligt", "Årligt");
		final ComboBox comboBox = new ComboBox(options);
		HBox hbBox = new HBox();
		hbBox.getChildren().add(comboBox);
		hbBox.setAlignment(Pos.CENTER_RIGHT);
		grid.add(hbBox, 1, 5);
		hbBox.setVisible(false);

		fastOverførsel.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.equals(true)) {
					hbBox.setVisible(true);
				} else {
					hbBox.setVisible(false);
				}
			}
		});

		Button btn = new Button("Overfør Beløb");
		HBox hbBtn = new HBox(10);
		hbBtn.getChildren().add(btn);
		btn.setId("KnapImenu");
		hbBtn.setStyle("-fx-padding: 10px 0px 20px 0px;");
		grid.add(hbBtn, 0, 6, 2, 6);

		Text fejl = new Text("");
		grid.add(fejl, 0, 8, 2, 8);
		fejl.setId("fejl");
		
		btn.setOnAction(e -> {
			fejl.setFill(Color.RED);
			fejl.setText("Overførsel fejlede (tror jeg?)");
			
			try {
				db.transfer(modtagerfelt.getText(), senderfelt.getPromptText(), Double.parseDouble(beløbfelt.getText()));
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		Scene scene = new Scene(grid, 400, 400);
		stage.initStyle(StageStyle.UNDECORATED);
		scene.getStylesheets().add(getClass().getResource("Brugermenu.css").toExternalForm());
		stage.setScene(scene);
		stage.show();

	}

}