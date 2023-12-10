package admin;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;

public class LoginController implements ActionListener{
	@FXML
	private AnchorPane loginPanel;
	@FXML
	private Button loginBtn;
	@FXML
	private TextField usernameFld;
	@FXML
	private PasswordField passFld;
	@FXML
	private Button forgotBtn;
	@FXML
	SimpleDateFormat currTime = new SimpleDateFormat("hh:mm:ss a");
	@FXML
	SimpleDateFormat currDate = new SimpleDateFormat("MMMMM dd, yyyy");
	@FXML
	Stage loginWindow, cashierWindow;
	Parent forgotWindow, log;
	Scene forgotPass, cashier;
	
	private PreparedStatement prepare;
	private ResultSet rs;
	@FXML
	void forgotPass() throws IOException{	

		loginWindow = (Stage) forgotBtn.getScene().getWindow();
		
		forgotWindow = FXMLLoader.load(getClass().getResource("ForgotPass.fxml"));
		forgotPass = new Scene(forgotWindow);
		forgotPass.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
		
		loginWindow.setScene(forgotPass);
		loginWindow.setTitle("Momocha-a | Forgot Password");
		loginWindow.show();
	}
    
	@FXML
	void login(){
		
		try(Connection connect = DatabaseConnection.connectDB()){
		
			loginWindow = (Stage) forgotBtn.getScene().getWindow();
			
			String username = usernameFld.getText();
			String password = passFld.getText();
			
			if (username.isBlank() || password.isBlank()) {
				Alert blankFld = new Alert(AlertType.INFORMATION);
				blankFld.setTitle("Error");
				blankFld.setHeaderText(null);
				blankFld.setContentText("Please enter username and password");
				blankFld.showAndWait();
				
			} else {
	
				try {
				    String adminQuery = "SELECT * FROM accounts WHERE username = ? AND role = 'admin'";
				    PreparedStatement preparedStatement = connect.prepareStatement(adminQuery);
				    preparedStatement.setString(1, username);

				    ResultSet adminCheck = preparedStatement.executeQuery();

				    boolean isValid = false;

				    if (adminCheck.next()) {
				        String hashedPasswordFromDB = adminCheck.getString("password");

//				        if (BCrypt.checkpw(password, hashedPasswordFromDB)) {
				            isValid = true;

				            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainFormAdmin.fxml"));
				            Parent root = loader.load();
				           

				            Scene cashier = new Scene(root);
				            cashier.getStylesheets().add(getClass().getResource("admin.css").toExternalForm());

				            cashierWindow = new Stage();
							cashierWindow.setScene(cashier);
							cashierWindow.setTitle("Momocha-a | Admin Main Window");
							Image icon = new Image("/assets/momocha-icon.png");
							cashierWindow.getIcons().add(icon);
							cashierWindow.setMaximized(true);

				            

				            cashierWindow.setOnCloseRequest(event -> {
				                Alert maxReached = new Alert(AlertType.CONFIRMATION);
				                maxReached.setTitle("Confirm: Close Window");
				                maxReached.setHeaderText(null);
				                maxReached.setContentText("Are you sure you want to close this window? \n"
				                        + "You will be logged out.");
				                Optional<ButtonType> result = maxReached.showAndWait();

				                if (result.isPresent() && result.get() == ButtonType.OK) {
				                    cashierWindow.close();
				                } else {
				                    event.consume();
				                }
				            });

				            cashierWindow.show();
				            loginWindow.close();
				        
				    
				
//					Alert adminLogin = new Alert(AlertType.INFORMATION);
//					adminLogin.setTitle("Admin Daw");
//					adminLogin.setHeaderText(null);
//					adminLogin.setContentText("G lang, pasok!");
//					adminLogin.showAndWait();
					
//				} else if (cashierCheck.next()) {
					
//					String firstname = cashierCheck.getString("first_name");
//					String lastname = cashierCheck.getString("last_name");
//					String middlename = cashierCheck.getString("middle_name");
					
//					CashierController.cId = String.valueOf(cashierCheck.getInt("cashier_id"));
//					CashierController.cName = firstname + " " + middlename + " " + lastname;
					
//					isValid = true;
						
				} else {
					
					Alert blankFld = new Alert(AlertType.WARNING);
	    			blankFld.setTitle("Hala, may nakalimot");
	    			blankFld.setHeaderText(null);
	    			blankFld.setContentText("Paktay ka diha");
	    			blankFld.showAndWait();
					
				}
				
//				cashierCheck.close();
		        adminCheck.close();
//		        adminStatement.close();
		      
							
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
	}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}}
	@Override
	public void actionPerformed(java.awt.event.ActionEvent e){
		if((e.getSource()).equals(KeyEvent.VK_ENTER) && passFld.isFocused()) {

			login();	
			
		}
	}
}
