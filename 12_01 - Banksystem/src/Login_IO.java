import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login_IO extends Application {
	public void start(Stage loginStage) {
		loginStage.setTitle("#Bestbank");
		primaryStage.getIcons().add(
				new Image("http://icons.iconarchive.com/icons/dtafalonso/android-lollipop/72/Calculator-icon.png"));
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(5);
		grid.setVgap(5);

		Text username = new Text("Brugernavn: ");
		grid.add(username, 0, 0);

		TextField usernameInput = new TextField();
		grid.add(usernameInput, 1, 0);

		Text password = new Text("Kodeord: ");
		grid.add(password, 0, 1);

		PasswordField passwordInput = new PasswordField();
		grid.add(passwordInput, 1, 1);

		Button gem = new Button("✓");
		HBox hbGem = new HBox(10);
		hbGem.getChildren().add(gem);
		grid.add(hbGem, 1, 2);

		gem.setOnAction(e -> {
			loginStage.hide();
		});

		Scene scene = new Scene(grid);
		loginStage.setScene(scene);
		scene.getStylesheets().add(Login_IO.class.getResource("popups.css").toExternalForm());
		loginStage.setResizable(false);
		loginStage.setAlwaysOnTop(true);
		loginStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
//
}