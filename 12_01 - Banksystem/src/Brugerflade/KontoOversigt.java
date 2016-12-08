package Brugerflade;


import org.hsqldb.Table;

import domain.Konto;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.Login_IO;
public class KontoOversigt {

	private void createTable(ObservableList<Konto> kontolist){
	TableView<Konto> kontooversigt = new TableView<Konto>();
	TableColumn<Konto,String> ejerCol = new TableColumn("name");
	ejerCol.setCellValueFactory(new PropertyValueFactory<Konto,String>("Name"));
	kontooversigt.setItems(kontolist);
//	Table.getColumns().addAll(ejerCol);
	}
	
	public void start(Stage stage){
		stage = new Stage();
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
	

		
		
		
		
		Scene scene = new Scene(grid);
		stage.setScene(scene);
		scene.getStylesheets().add(Login_IO.class.getResource("login.css").toExternalForm());
		stage.setResizable(false);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
		}
	
}
