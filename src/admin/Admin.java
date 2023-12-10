package admin;

import javafx.fxml.FXMLLoader;	
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.application.Application;
public class Admin extends Application {

	@Override
	public void start(Stage primaryStage) {
			try {
				
				Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
//				String css = this.getClass().getResource("admin.css").toExternalForm();
//				scene.getStylesheets().add(css);
				Image icon = new Image("/assets/momocha-icon.png");
				primaryStage.getIcons().add(icon);	
				primaryStage.setMaximized(false);
				primaryStage.setResizable(false);
				primaryStage.setTitle("MoMocha-a || Login");
				primaryStage.setScene(scene);
				primaryStage.show();
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	
	public static void main(String[] args) {
			launch(args);
	}

}
