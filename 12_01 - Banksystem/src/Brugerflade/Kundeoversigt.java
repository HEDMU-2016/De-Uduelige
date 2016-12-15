package Brugerflade;

import java.sql.SQLException;

import DB.DB;
import domain.Kunde;
import domain.Login;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import utill.TableCreator;

public class Kundeoversigt {

	public void start(Stage stage) throws SQLException {
		TableCreator tablecreator = new TableCreator();
		DB db = new DB();
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
		grid.setAlignment(Pos.CENTER);

		stage.setTitle("Kundeoversigt - Lortebank A/S");
		stage.getIcons().add(new Image("Brugerflade/ico.png"));

		Text loginlabel = new Text("Kunder");
		HBox hbloginlabel = new HBox(10);
		grid.add(hbloginlabel, 0, 0);
		hbloginlabel.getChildren().add(loginlabel);
		loginlabel.setId("overskrift");

		Button close = new Button("X");
		HBox hbClose = new HBox(10);
		close.setId("close");
		hbClose.getChildren().add(close);
		hbClose.setAlignment(Pos.TOP_RIGHT);
		grid.add(hbClose, 3, 0);

		close.setOnAction(e -> {
			stage.close();
		});

		TableView<Kunde> kundeoversigt = tablecreator.kundeTable();
		kundeoversigt.setPrefWidth(350);
		// loginoversigt.getSelectionModel().setCellSelectionEnabled(true);
		// loginoversigt.setEditable(true);
		grid.add(kundeoversigt, 0, 1, 1, 4);

		Label navnlabel = new Label("Navn");
		navnlabel.setId("tekst");
		grid.add(navnlabel, 2, 1);

		TextField navnfelt = new TextField();
		navnfelt.setPrefWidth(200);
		grid.add(navnfelt, 3, 1);

		Label emaillabel = new Label("Email");
		emaillabel.setId("tekst");
		grid.add(emaillabel, 2, 2);

		TextField emailfelt = new TextField();
		grid.add(emailfelt, 3, 2);

		Label brugernavnlabel = new Label("Brugernavn");
		brugernavnlabel.setId("tekst");
		grid.add(brugernavnlabel, 2, 3);

		TextField brugernavnfelt = new TextField();
		grid.add(brugernavnfelt, 3, 3);

		Label fejl = new Label("");
		fejl.setId("fejl");
		grid.add(fejl, 2, 5, 3, 5);

		Button opret = new Button("Opret");
		grid.add(opret, 2, 4, 3, 4);
		opret.setId("KnapImenu");
		opret.setOnAction(e -> {
			if (navnfelt.getText().isEmpty() == false && brugernavnfelt.getText().isEmpty() == false
					&& emailfelt.getText().isEmpty() == false)
				try {
					db.addKunde(new Kunde(navnfelt.getText(), emailfelt.getText(), brugernavnfelt.getText().toLowerCase()));
					fejl.setTextFill(Color.GREEN);
					fejl.setText("Kunden er nu oprettet!");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			else {
				fejl.setTextFill(Color.RED);
				fejl.setText("Du skal lige udfylde alle felterne!");
			}
		});

		Scene scene = new Scene(grid);
		stage.setScene(scene);
		scene.getStylesheets().add(Brugermenu.class.getResource("Brugermenu.css").toExternalForm());
		stage.setResizable(false);
		//stage.initStyle(StageStyle.UTILITY);
		stage.show();
		
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
            }
        });
	}
}
