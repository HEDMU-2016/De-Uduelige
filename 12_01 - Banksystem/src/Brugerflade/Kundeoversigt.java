package Brugerflade;

import java.sql.SQLException;

import DB.DB;
import domain.Kunde;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Kundeoversigt {
	private Stage lokalstage;

	private void createTable(ObservableList<Kunde> kundelist) {

	}

	public void start(Stage stage) throws SQLException {
		lokalstage = stage;
		DB db = new DB();
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);

		Button close = new Button("x");
		close.setId("close");
		grid.add(close, 10, 0);
		close.setOnAction(e -> {
			stage.close();
		});

		final Label label = new Label("Kunde oversigt");
		label.setFont(new Font("arial", 20));
		
		ObservableList<Kunde> kundeliste;
		kundeliste = FXCollections.observableArrayList(db.listKunder());

		TableView<Kunde> kundeoversigt = new TableView<Kunde>();
		TableColumn<Kunde, String> nameCol = new TableColumn<Kunde, String>("name");
		nameCol.setCellValueFactory(new PropertyValueFactory<Kunde, String>("navn"));
		TableColumn<Kunde, String> emailCol = new TableColumn<Kunde, String>("email");
		emailCol.setCellValueFactory(new PropertyValueFactory<Kunde, String>("email"));

		kundeoversigt.setItems(kundeliste);
		kundeoversigt.getColumns().addAll(nameCol, emailCol);

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(label, kundeoversigt);

		Scene scene = new Scene(grid, 450, 400);
		stage.setScene(scene);
		scene.getStylesheets().add(Brugermenu.class.getResource("Brugermenu.css").toExternalForm());
		stage.setResizable(false);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();

	}
}
