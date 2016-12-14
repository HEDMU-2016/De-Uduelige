package Brugerflade;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import DB.DB;
import domain.Konto;
import domain.Login;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utill.TableCreator;

// PENIS!

public class OverførselsStage {
	DB db = new DB();

	@SuppressWarnings("deprecation")
	public void start(Stage stage, Login bruger) throws Exception {

		TableCreator tablecreator = new TableCreator();

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
		grid.add(beløbfelt, 1, 1);

		Label frakonto = new Label("fra kontonr: ");
		frakonto.setId("labelting");
		grid.add(frakonto, 0, 2);

		// ObservableList<String> Kundekontoer =
		// FXCollections.observableArrayList(db.listkonti(db.matchkundemedlogin(bruger)).toString());
		//
		//
		// final ComboBox senderfelt = new ComboBox(Kundekontoer);
		// HBox hbSendefelt = new HBox();
		// hbSendefelt.getChildren().add(senderfelt);
		// senderfelt.setPrefWidth(175);
		// grid.add(hbSendefelt, 1, 2);

		TextField senderfelt = new TextField();
		grid.add(senderfelt, 1, 2);

		Label tilKonto = new Label("til kontonr: ");
		tilKonto.setId("labelting");
		grid.add(tilKonto, 0, 3);

		TextField modtagerfelt = new TextField();
		grid.add(modtagerfelt, 1, 3);

		Button kontaktbogknap = new Button("Kontaktbog");
		grid.add(kontaktbogknap, 1, 4);
		kontaktbogknap.setId("Knap");
		kontaktbogknap.setPrefWidth(175);
		Kontaktbog kontaktbog = new Kontaktbog();
		kontaktbogknap.setOnAction(e -> {

			try {
				kontaktbog.start(new Stage(), bruger);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		CheckBox fastOverførsel = new CheckBox("Fast overførsel");
		fastOverførsel.setId("labelting");
		grid.add(fastOverførsel, 0, 5, 2, 5);

		ObservableList<String> options = FXCollections.observableArrayList("Dagligt", "Ugentligt", "Månedligt",
				"Kvartaligt", "Halvårligt", "Årligt");
		final ComboBox comboBox = new ComboBox(options);
		HBox hbBox = new HBox();
		hbBox.getChildren().add(comboBox);
		hbBox.setAlignment(Pos.CENTER_RIGHT);
		grid.add(hbBox, 1, 6);
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
		grid.add(hbBtn, 0, 7, 2, 7);


		Button fasteoverførsler = new Button("Se dine faste overførsler");
		fasteoverførsler.setId("Knap");
		grid.add(fasteoverførsler, 0, 4);
		fasteoverførsler.setOnAction(e -> {
			FastoverførselsOversigt fastoverførselsoversigt = new FastoverførselsOversigt();
			try {
				fastoverførselsoversigt.start(new Stage(), bruger);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		Text fejl = new Text("");
		grid.add(fejl, 0, 9, 2, 9);
		fejl.setId("fejl");
		
		Button overførknappen = new Button("Overfør Beløb");
		HBox overførknappensHB = new HBox(10);
		overførknappensHB.getChildren().add(overførknappen);
		overførknappen.setId("KnapImenu");
		overførknappensHB.setStyle("-fx-padding: 10px 0px 20px 0px;");
		grid.add(overførknappensHB, 0, 7, 2, 7);

		overførknappen.setOnAction(e -> {
			try {
				if (checklegitness(beløbfelt, senderfelt, modtagerfelt, bruger, fejl) == true) {
					
					fejl.setFill(Color.GREEN);
					try {

						if (fastOverførsel.isSelected() == false) {
							System.out.println("modtagerfelt:" + modtagerfelt.getText());
							System.out.println("senderfelt:" + senderfelt.getText());
							System.out.println("beløbfelt:" + beløbfelt.getText());
							System.out.println("en gangs overførsel");
							db.transfer(Integer.parseInt(senderfelt.getText()),
									Integer.parseInt(modtagerfelt.getText()),
									BigDecimal.valueOf(Double.parseDouble(beløbfelt.getText())));
							fejl.setFill(Color.GREEN);
							fejl.setText("Overførsel er nu udført!");
						}
						if (fastOverførsel.isSelected() == true) {
							System.out.println("Fast overførsel oprettes...");
							if (comboBox.getValue() == "Dagligt") {
								db.fastoverførsel(Date.valueOf(LocalDate.now().plusDays(1)),
										Integer.parseInt(senderfelt.getText()),
										Integer.parseInt(modtagerfelt.getText()),
										Double.parseDouble(beløbfelt.getText()), 1);
								fejl.setText("daglig overførsel er oprettet");
							}
							if (comboBox.getValue() == "Ugentligt") {
								db.fastoverførsel(Date.valueOf(LocalDate.now().plusWeeks(1)),
										Integer.parseInt(senderfelt.getText()),
										Integer.parseInt(modtagerfelt.getText()),
										Double.parseDouble(beløbfelt.getText()), 2);
								fejl.setText("ugentlig overførsel er oprettet");
							}
							if (comboBox.getValue() == "Månedligt") {
								db.fastoverførsel(Date.valueOf(LocalDate.now().plusMonths(1)),
										Integer.parseInt(senderfelt.getText()),
										Integer.parseInt(modtagerfelt.getText()),
										Double.parseDouble(beløbfelt.getText()), 3);
								fejl.setText("månedlig overførsel er oprettet");
							}
							if (comboBox.getValue() == "Kvartaligt") {
								db.fastoverførsel(Date.valueOf(LocalDate.now().plusMonths(3)),
										Integer.parseInt(senderfelt.getText()),
										Integer.parseInt(modtagerfelt.getText()),
										Double.parseDouble(beløbfelt.getText()), 4);
								fejl.setText("kvartaligt overførsel er oprettet");
							if (comboBox.getValue() == "Halvårligt") {
								db.fastoverførsel(Date.valueOf(LocalDate.now().plusMonths(6)),
										Integer.parseInt(senderfelt.getText()),
										Integer.parseInt(modtagerfelt.getText()),
										Double.parseDouble(beløbfelt.getText()), 5);
								fejl.setText("halvårlig overførsel er oprettet");
							}
							if (comboBox.getValue() == "Årligt") {
								db.fastoverførsel(Date.valueOf(LocalDate.now().plusYears(1)),
										Integer.parseInt(senderfelt.getText()),
										Integer.parseInt(modtagerfelt.getText()),
										Double.parseDouble(beløbfelt.getText()), 6);
								fejl.setText("årlig overførsel er oprettet");
							}
						}
						}
						} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					fejl.setFill(Color.RED);
					
				}
			} catch (NumberFormatException | SQLException e1) {
				e1.printStackTrace();
			}
		});
		Scene scene = new Scene(grid, 400, 400);
		stage.initStyle(StageStyle.UNDECORATED);
		scene.getStylesheets().add(getClass().getResource("Brugermenu.css").toExternalForm());
		stage.setScene(scene);
		stage.show();

	}

	private boolean checklegitness(TextField beløbfelt, TextField senderfelt, TextField modtagerfelt, Login bruger, Text fejl)
			throws SQLException {

		if (feltererudfyldt(senderfelt,beløbfelt,modtagerfelt)==true){
			System.out.println("fejlterne er udfyldt");
			
			if( checkifsenderejerkonto(bruger,senderfelt)==true) {
				System.out.println("konto nummer: "+senderfelt.getText()+" er din konto");
				
				if(bogstaverifelterne(senderfelt,modtagerfelt,beløbfelt)==false){
					System.out.println("der er ingen bogstaver i felterne");
					
					if(symbolerifelterne(senderfelt,modtagerfelt,beløbfelt,fejl)==false){
						System.out.println("der er ingen symboler i felterne");
						
						if(senderfelt.getText().equals(beløbfelt.getText())==false){
							System.out.println("sender og modtager er ikke den samme konto");
							
							return true; 
						}
						else fejl.setText("Kontoerne må ikke være ens");
						return false;
					}
					else fejl.setText("Ingen symboler");
					return false;
				}
				else fejl.setText("Ingen bogstaver");
				return false;
			}
			else fejl.setText("Du kan ikke sende penge fra andres konto");
			return false;
		}
		else fejl.setText("Alle felter skal udfyldes");			
		return false;
	}
	private boolean feltererudfyldt(TextField senderfelt, TextField modtagerfelt, TextField beløbfelt){
		if(beløbfelt.getText().isEmpty() == false 
		&& modtagerfelt.getText().isEmpty() == false
		&& senderfelt.getText().isEmpty() == false)
		return true;
		else return false;
	}
		
	private boolean checkifsenderejerkonto(Login bruger, TextField senderfelt) throws SQLException{
			System.out.println("checker om konto nummer: "+senderfelt.getText()+" er din konto");
			List<Konto> kontolist = db.listkonti(db.matchkundemedlogin(bruger));
				for (int i = 0; i < kontolist.size();i++ ) {
					if (kontolist.get(i).getKontonummer() == Integer.parseInt(senderfelt.getText())){			
					return true;
					}
				}
				return false;
				}
		private boolean bogstaverifelterne(TextField senderfelt, TextField modtagerfelt, TextField beløbfelt){
			String[] bogstaver = 
				{"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","æ","ø","å",
				"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","Æ","Ø","Å"};
				
			for(int i=0;i<=57;){
				if(beløbfelt.getText().contains(bogstaver[i]) 
				||senderfelt.getText().contains(bogstaver[i])
				||modtagerfelt.getText().contains(bogstaver[i]))
				return true;
				else i++;
				break;
				 
			}
			return false;
		}
/*		private boolean ingennegativer(TextField senderfelt, TextField modtagerfelt, TextField beløbfelt){
		if(Double.parseDouble(beløbfelt.getText()) > 0 
			&& Double.parseDouble(senderfelt.getText())>0
			&& Double.parseDouble(modtagerfelt.getText())>0)
			return true;	
			else return false;
		
		} metoden er ikke nødvendig fordi at "-" er et symbol*/
		
		private boolean symbolerifelterne(TextField senderfelt, TextField modtagerfelt, TextField beløbfelt, Text fejl){
			List<String> tal = new ArrayList<>();
			TextField[] tekstfelt = {senderfelt,modtagerfelt,beløbfelt};
			for(int i=0; i<=9;i++){
				tal.add(Integer.toString(i));
			}
			tal.add(".");
			for(int i=0; i<3;i++){
				for(int j=1; j<=tekstfelt[i].getText().length();){
					int g=j-1;	
						if(tal.contains(tekstfelt[i].getText().substring(g,j))){
							j++;						
					}
					else 
					return true;	
				}
			}
			return false;
		}




}