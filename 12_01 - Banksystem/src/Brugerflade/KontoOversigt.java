package Brugerflade;


import java.sql.SQLException;

import DB.DB;
import domain.Konto;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.Login_IO;
public class KontoOversigt {
ObservableList<Konto> list;
	
	private void createTable(ObservableList<Konto> kontolist){
	TableView<Konto> kontooversigt = new TableView<Konto>();
	TableColumn<Konto,String> ejerCol = new TableColumn("name");
	ejerCol.setCellValueFactory(new PropertyValueFactory<Konto,String>("ejer"));
	TableColumn<Konto,String> kontoIdCol = new TableColumn("ID");
	kontoIdCol.setCellValueFactory(new PropertyValueFactory<Konto,String>("kontonummer"));
	TableColumn<Konto,Double> saldoCol = new TableColumn("Saldo");
	saldoCol.setCellValueFactory(new PropertyValueFactory<Konto,Double>("saldo"));
	
	kontooversigt.setItems(kontolist);
	kontooversigt.getColumns().addAll(ejerCol, kontoIdCol, saldoCol);
	}
	
	public void start(Stage stage)throws SQLException{
		DB db = new DB();
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
	
		//Dette skal bruges på alle vinduer
		//
		//
		stage.setTitle("Konto Oversigt - Lortebank A/S");
		stage.getIcons().add(new Image("Brugerflade/ico.png"));
		//
		//
		//Dette skal bruges på alle vinduer
		
		
		Button close = new Button("x");
		close.setId("close");
		grid.add(close,0,0);
		close.setOnAction(e->{
		stage.close();
		});
		
		
		
		
		
		Scene scene = new Scene(grid);
		stage.setScene(scene);
		scene.getStylesheets().add(Brugermenu.class.getResource("Brugermenu.css").toExternalForm());
		stage.setResizable(false);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
		}
	
}
