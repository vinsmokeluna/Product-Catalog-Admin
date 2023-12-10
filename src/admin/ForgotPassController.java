package admin;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ForgotPassController {
	@FXML
	private AnchorPane loginPanel;
	@FXML
	private Button forgotverifyBtn;
	@FXML
	private TextField usernameFld;
	@FXML
	private TextField emailFld;
	@FXML
	private TextField contactFld;
	@FXML
	private Button forgotBtn;
	
	@FXML
	Stage ForgotPassWindow;
	
	@FXML
	void empVerification() throws IOException {

		ForgotPassWindow = (Stage) forgotverifyBtn.getScene().getWindow();
		
		Parent root = FXMLLoader.load(getClass().getResource("Authentication.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
		
		ForgotPassWindow.setScene(scene);
		ForgotPassWindow.setTitle("Momocha-a | Employee Verification");
		ForgotPassWindow.show();
			
		 
	}
	
	@FXML
	void back() throws IOException {
		ForgotPassWindow = (Stage) forgotverifyBtn.getScene().getWindow();
		
		Parent login = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene scene = new Scene(login);
		scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
		
		ForgotPassWindow.setScene(scene);
		ForgotPassWindow.setTitle("Momocha-a | Employee Login");
		ForgotPassWindow.show();
	}
}
