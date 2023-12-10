package admin;

import java.io.File;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class MainFormController implements Initializable {

	@FXML
	private TableView<data> accountsTableView;

	@FXML
	private Button accountsbtn;

	@FXML
	private TableColumn<productData, String> availability;

	@FXML
	private TextField availabilitytxt;

	@FXML
	private TableColumn<productData, String> category;

	@FXML
	private TableColumn<productData, String> description;

	@FXML
	private TextField descriptiontxt;

	@FXML
	private AnchorPane form1;

	@FXML
	private AnchorPane form1_1;

	@FXML
	private AnchorPane form2;

	@FXML
	private AnchorPane form2_2;

	@FXML
	private AnchorPane form3;

	@FXML
	private AnchorPane form3_3;

	@FXML
	private TableView<productData> inventoryTableView;

	@FXML
	private Button inventory_addbtn;

	@FXML
	private Button inventory_deletebtn;

	@FXML
	private Button inventory_clearbtn;

	@FXML
	private ImageView inventory_imageView;

	@FXML
	private Button inventory_importbtn;

	@FXML
	private Button inventory_updatebtn;

	@FXML
	private Button inventorybtn;

	@FXML
	private Button logout_btn;

	@FXML
	private BorderPane main_form;

	@FXML
	private TableColumn<productData, Integer> pricelrg;

	@FXML
	private TextField pricelrgtxt;

	@FXML
	private TableColumn<productData, Integer> pricemed;

	@FXML
	private TextField pricemedtxt;

	@FXML
	private TableColumn<productData, Integer> productid;

	@FXML
	private TableColumn<productData, Integer> qty;

	@FXML
	private TextField qtytxt;

	@FXML
	private Button transactionbtn;

	@FXML
	private Button dashboardbtn;

	@FXML
	private TabPane tabPane;

	@FXML
	private Tab tab0;

	@FXML
	private Tab tab1;

	@FXML
	private Tab tab2;

	@FXML
	private Tab tab3;

	@FXML
	private Alert alert;

	@FXML
	private Connection connect;
	private Statement statement;
	private PreparedStatement prepare;
	private ResultSet rs;

	public static String path;
	private Image image;

	@FXML
	public void importBtn() {

		FileChooser openFile = new FileChooser();
		openFile.getExtensionFilters().add(new ExtensionFilter("Open image file", "*png", "*jpg"));

		File file = openFile.showOpenDialog(main_form.getScene().getWindow());

		if (file != null) {

			path = file.getAbsolutePath();
			image = new Image(file.toURI().toString(), 145, 113, true, true);

			inventory_imageView.setImage(image);
		}
	}

	@FXML
	public ObservableList<productData> inventoryDataList() {

		ObservableList<productData> ListData = FXCollections.observableArrayList();

		String sql = "SELECT * FROM stocks";
		connect = DatabaseConnection.connectDB();

		try {

			prepare = connect.prepareStatement(sql);
			rs = prepare.executeQuery();

			productData prodData;

			while (rs.next()) {

				prodData = new productData(rs.getInt("product_id"), rs.getString("description"),
						rs.getString("category"), rs.getInt("qty"), rs.getInt("price_med"), rs.getInt("price_lrg"),
						rs.getString("availability"), rs.getString("image"));

				ListData.add(prodData);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return ListData;
	}

	@FXML
	private ObservableList<productData> inventoryListData;

	@FXML
	public void inventoryShowData() {
		inventoryListData = inventoryDataList();

		productid.setCellValueFactory(new PropertyValueFactory<>("productID"));
		description.setCellValueFactory(new PropertyValueFactory<>("description"));
		category.setCellValueFactory(new PropertyValueFactory<>("category"));
		qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
		pricemed.setCellValueFactory(new PropertyValueFactory<>("pricemed"));
		pricelrg.setCellValueFactory(new PropertyValueFactory<>("pricelrg"));
		availability.setCellValueFactory(new PropertyValueFactory<>("availability"));

		inventoryTableView.setItems(inventoryListData);
	}

	@FXML
	void InventoryAddbtn() {

		String description = descriptiontxt.getText();
		String pricemed = pricemedtxt.getText();
		String pricelrg = pricelrgtxt.getText();
		String qty = qtytxt.getText();

		if (description == null || description.isEmpty()
				|| inventory_categoryList.getSelectionModel().getSelectedItem() == null || pricemed == null
				|| pricemed.isEmpty() || pricelrg == null || pricelrg.isEmpty() || qty == null || qty.isEmpty()
				|| inventory_statusList.getSelectionModel().getSelectedItem() == null || ID == 0 && path == null) {

			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Please fill all the information");
			alert.showAndWait();

		} else {

			connect = DatabaseConnection.connectDB();

			try {

				statement = connect.createStatement();

				String insertData = "INSERT INTO stocks"
						+ "(description, category, price_med, price_lrg, qty, availability, image)"
						+ "VALUES(?,?,?,?,?,?,?)";

				prepare = connect.prepareStatement(insertData);
				prepare.setString(1, descriptiontxt.getText());
				prepare.setString(2, (String) inventory_categoryList.getSelectionModel().getSelectedItem());
				prepare.setString(3, pricemedtxt.getText());
				prepare.setString(4, pricelrgtxt.getText());
				prepare.setString(5, qtytxt.getText());
				prepare.setString(6, (String) inventory_statusList.getSelectionModel().getSelectedItem());

				if (path != null) {
					path = path.replace("\\", "\\\\");
					prepare.setString(7, path);

					alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirmation");
					alert.setHeaderText(null);
					alert.setContentText("Are you sure you want to add product?");
					Optional<ButtonType> option = alert.showAndWait();

					if (option.isPresent() && option.get().equals(ButtonType.OK)) {
						prepare.executeUpdate();

						alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Success");
						alert.setHeaderText(null);
						alert.setContentText("Successfully added!");
						alert.showAndWait();

						inventoryShowData();
						clean();
					} else {
						alert = new Alert(AlertType.ERROR);
						alert.setTitle("Cancelled");
						alert.setHeaderText(null);
						alert.setContentText("Cancelled");
						alert.showAndWait();
					}
				} else {
					// Handle the case when path is null
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText(null);
					alert.setContentText("Please provide a valid image path");
					alert.showAndWait();
				}

			} catch (Exception e) {
				e.printStackTrace();

			}
		}

	}

	@FXML
	void inventoryUpdatebtn() {

		String description = descriptiontxt.getText();
		String pricemed = pricemedtxt.getText();
		String pricelrg = pricelrgtxt.getText();
		String qty = qtytxt.getText();

		if (description == null || description.isEmpty()
				|| inventory_categoryList.getSelectionModel().getSelectedItem() == null || pricemed == null
				|| pricemed.isEmpty() || pricelrg == null || pricelrg.isEmpty() || qty == null || qty.isEmpty()
				|| inventory_statusList.getSelectionModel().getSelectedItem() == null || ID == 0 && path == null) {

			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Please fill all the informations needed");
			alert.showAndWait();

		} else {

			path = path.replace("\\", "\\\\");
			String updateData = "UPDATE stocks SET " + "description = '" + descriptiontxt.getText() + "', category = '"
					+ inventory_categoryList.getSelectionModel().getSelectedItem() + "', price_med = '"
					+ pricemedtxt.getText() + "', price_lrg = '" + pricelrgtxt.getText() + "', qty = '"
					+ qtytxt.getText() + "', availability = '"
					+ inventory_statusList.getSelectionModel().getSelectedItem() + "', image = '" + path
					+ "' WHERE product_id =  " + ID;

			connect = DatabaseConnection.connectDB();

			try {

				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation");
				alert.setHeaderText(null);
				alert.setContentText("Are you sure you want to update?");
				Optional<ButtonType> option = alert.showAndWait();

				if (option.get().equals(ButtonType.OK)) {
					prepare = connect.prepareStatement(updateData);
					prepare.executeUpdate();

					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Success");
					alert.setHeaderText(null);
					alert.setContentText("Successfully Updated!!");
					alert.showAndWait();

					inventoryShowData();
					clean();
				} else {

					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Cancelled");
					alert.setHeaderText(null);
					alert.setContentText("Cancelled");
					alert.showAndWait();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	void inventoryDeletebtn() {

		String description = descriptiontxt.getText();
		String pricemed = pricemedtxt.getText();
		String pricelrg = pricelrgtxt.getText();
		String qty = qtytxt.getText();

		if (description == null || description.isEmpty()
				|| inventory_categoryList.getSelectionModel().getSelectedItem() == null || pricemed == null
				|| pricemed.isEmpty() || pricelrg == null || pricelrg.isEmpty() || qty == null || qty.isEmpty()
				|| inventory_statusList.getSelectionModel().getSelectedItem() == null || ID == 0 && path == null) {

			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Please select a product");
			alert.showAndWait();

		} else {

			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to delete?");
			Optional<ButtonType> option = alert.showAndWait();

			if (option.get().equals(ButtonType.OK)) {

				String delete = "DELETE from stocks WHERE product_id = " + ID;

				try {

					prepare = connect.prepareStatement(delete);
					prepare.executeUpdate();

					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Success");
					alert.setHeaderText(null);
					alert.setContentText("Successfully deleted!!");
					alert.showAndWait();

					inventoryShowData();
					clean();

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Cancel");
				alert.setHeaderText(null);
				alert.setContentText("Cancelled");
				alert.showAndWait();
			}
		}
	}

	private int ID;

	@FXML
	void getSelected() {

		productData prodData = inventoryTableView.getSelectionModel().getSelectedItem();
		int num = inventoryTableView.getSelectionModel().getSelectedIndex();

		if ((num - 1) < -1) {
			return;
		}

		ID = prodData.getProductID();
		descriptiontxt.setText(prodData.getDescription());
		inventory_categoryList.getSelectionModel().select(prodData.getCategory());
		pricemedtxt.setText(prodData.getPricemed().toString());
		pricelrgtxt.setText(prodData.getPricelrg().toString());
		qtytxt.setText(prodData.getQty().toString());
		inventory_statusList.getSelectionModel().select(prodData.getAvailability());
		path = prodData.getImage();

		path = "file:" + prodData.getImage();
		image = new Image(path, 143, 110, true, false);
		inventory_imageView.setImage(image);
	}

	@FXML
	void clean() {
		descriptiontxt.setText(null);
		inventory_categoryList.getSelectionModel().clearSelection();
		pricemedtxt.setText(null);
		pricelrgtxt.setText(null);
		qtytxt.setText(null);
		inventory_statusList.getSelectionModel().clearSelection();
		path = "";
		inventory_imageView.setImage(null);

		inventory_categoryList.setPromptText("Choose Type");
	}

	@FXML
	private ComboBox<String> inventory_categoryList;

	private String[] categoryList = { "Classic Series", "Cheesecakes Series", "Momocha-a Favoritos", "Frappe Series",
			"House Blend Tea", "Coffee Series", "Hot Beverage", "Latte Series", "Fruit Tea Series", "Yogurt Series"};

	@FXML
	private ComboBox<String> inventory_statusList;

	private String[] statusList = { "Available", "Out of stock" };

	@FXML
	private void statusList() {
		List<String> status = new ArrayList<>();
		for (String list : statusList) {
			status.add(list);
		}
		ObservableList statuslist = FXCollections.observableArrayList(status);
		inventory_statusList.setItems(statuslist);
	}

	@FXML
	private void inventoryCategory() {

		List<String> category = new ArrayList<>();

		for (String data : categoryList) {
			category.add(data);
		}

		ObservableList listData = FXCollections.observableArrayList(category);
		inventory_categoryList.setItems(listData);
	}

//----ACCOUNTS TABLE----//

	@FXML
	private Button accountAddBtn;

	@FXML
	private Button accountDeleteBtn;

	@FXML
	private Button accountUpdateBtn;

	@FXML
	private TableColumn<data, Integer> id;

	@FXML
	private TableColumn<data, String> name;

	@FXML
	private TableColumn<data, String> username;

	@FXML
	private TableColumn<data, String> password;

	@FXML
	private TableColumn<data, String> contact;

	@FXML
	private TableColumn<data, String> address;

	@FXML
	private TableColumn<data, String> email;

	@FXML
	private TableColumn<data, String> role;

	@FXML
	private TextField nametxt;

	@FXML
	private TextField usernametxt;

	@FXML
	private TextField passwordtxt;

	@FXML
	private TextField contacttxt;

	@FXML
	private TextField addresstxt;

	@FXML
	private TextField emailtxt;

	@FXML
	private ComboBox<String> role_list;

	private String[] roleList = { "admin", "cashier" };

	@FXML
	private void roleList() {
		List<String> role = new ArrayList<>();
		for (String roles : roleList) {
			role.add(roles);
		}
		ObservableList roleData = FXCollections.observableArrayList(role);
		role_list.setItems(roleData);
	}

	@FXML
	public ObservableList<data> accountDataList() {

		ObservableList<data> Datas = FXCollections.observableArrayList();

		String sql = "SELECT * FROM accounts";
		connect = DatabaseConnection.connectDB();

		try {

			prepare = connect.prepareStatement(sql);
			rs = prepare.executeQuery();

			data accountData;

			while (rs.next()) {

				accountData = new data(rs.getInt("ID"), rs.getString("name"), rs.getString("username"),
						rs.getString("password"), rs.getString("contact_no"), rs.getString("address"),
						rs.getString("email"), rs.getString("role"));

				Datas.add(accountData);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return Datas;
	}

	@FXML
	private ObservableList<data> accountListData;

	@FXML
	public void accountShowData() {
		accountListData = accountDataList();

		id.setCellValueFactory(new PropertyValueFactory<>("ID"));
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		username.setCellValueFactory(new PropertyValueFactory<>("username"));
		password.setCellValueFactory(
				cellData -> new SimpleStringProperty(maskPassword(cellData.getValue().getPassword())));
		contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
		address.setCellValueFactory(new PropertyValueFactory<>("address"));
		email.setCellValueFactory(new PropertyValueFactory<>("email"));
		role.setCellValueFactory(new PropertyValueFactory<>("role"));

		accountsTableView.setItems(accountListData);
	}

	private String maskPassword(String actualPassword) {
		// You can implement your encryption/hashing logic here
		// For example, replace each character with an asterisk
		StringBuilder maskedPassword = new StringBuilder();
		for (int i = 0; i < actualPassword.length(); i++) {
			maskedPassword.append('*');
		}
		return maskedPassword.toString();
	}

	@FXML
	void accountyAddbtn() {

		String name = nametxt.getText();
		String username = usernametxt.getText();
		String password = passwordtxt.getText();

		String address = addresstxt.getText();
		String email = emailtxt.getText();

		if (name == null || name.isEmpty() || username == null || username.isEmpty() || password == null
				|| password.isEmpty() || contact == null || address == null || address.isEmpty() || email == null
				|| email.isEmpty() || role_list.getSelectionModel().getSelectedItem() == null) {

			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Please fill all the informations");
			alert.showAndWait();

		} else {

			connect = DatabaseConnection.connectDB();

			try {
				statement = connect.createStatement();

				String insertAccountData = "INSERT INTO accounts "
						+ "(name, username, password, contact_no, address, email, role) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

				prepare = connect.prepareStatement(insertAccountData);
				prepare.setString(1, nametxt.getText());
				prepare.setString(2, usernametxt.getText());

				prepare.setString(3, passwordtxt.getText());

				prepare.setString(4, contacttxt.getText());
				prepare.setString(5, addresstxt.getText());
				prepare.setString(6, emailtxt.getText());
				prepare.setString(7, (String) role_list.getSelectionModel().getSelectedItem());

				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation");
				alert.setHeaderText(null);
				alert.setContentText("Are you sure you want to add account?");

				Optional<ButtonType> option = alert.showAndWait();

				if (option.isPresent() && option.get() == ButtonType.OK) {
				    prepare.executeUpdate();

				    alert = new Alert(AlertType.INFORMATION);
				    alert.setTitle("Success");
				    alert.setHeaderText(null);
				    alert.setContentText("Successfully added!");
				    alert.showAndWait();

				    accountShowData();
				    clear();
				} else {
				    alert = new Alert(AlertType.ERROR);
				    alert.setTitle("Cancel");
				    alert.setHeaderText(null);
				    alert.setContentText("Cancelled");
				    alert.showAndWait();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

//	private String hashPassword(String plainTextPassword) {
//
//		int bcryptStrength = 12;
//		return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(bcryptStrength));
//	}

	void filterFld() {

		UnaryOperator<TextFormatter.Change> filter = change -> {
			String text = change.getControlNewText();
			if (Pattern.matches("\\d{0,11}", text)) {
				return change;
			}
			return null;
		};

		// Apply the filter using a TextFormatter
		TextFormatter<String> textFormatter = new TextFormatter<>(new DefaultStringConverter(), null, filter);
		TextFormatter<Integer> qtyFilter = new TextFormatter<>(new IntegerStringConverter(), null, filter);
		TextFormatter<Integer> priceMediumFilter = new TextFormatter<>(new IntegerStringConverter(), null, filter);
		TextFormatter<Integer> priceLargeFilter = new TextFormatter<>(new IntegerStringConverter(), null, filter);
		contacttxt.setTextFormatter(textFormatter);
		qtytxt.setTextFormatter(qtyFilter);
		pricemedtxt.setTextFormatter(priceMediumFilter);
		pricelrgtxt.setTextFormatter(priceLargeFilter);

	}

	@FXML
	void accountUpdatebtn() {
	    String name = nametxt.getText();
	    String username = usernametxt.getText();
	    String newPassword = passwordtxt.getText();  // Use a separate variable for display
	    String contact = contacttxt.getText();
	    String address = addresstxt.getText();
	    String email = emailtxt.getText();

	    if (name.isEmpty() || username.isEmpty() || newPassword.isEmpty() ||
	            contact.isEmpty() || address.isEmpty() || email.isEmpty() ||
	            role_list.getSelectionModel().getSelectedItem() == null) {

	        alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText(null);
	        alert.setContentText("Please fill all the information");
	        alert.showAndWait();
	    } else {
	        String updateAccountData = "UPDATE accounts SET name=?, username=?, password=?, contact_no=?, address=?, email=?, role=? WHERE ID=?";
	        connect = DatabaseConnection.connectDB();

	        try {
	            alert = new Alert(AlertType.CONFIRMATION);
	            alert.setTitle("");
	            alert.setHeaderText(null);
	            alert.setContentText("Are you sure you want to update?");
	            Optional<ButtonType> option = alert.showAndWait();

	            if (option.isPresent() && option.get() == ButtonType.OK) {
	                try (PreparedStatement preparedStatement = connect.prepareStatement(updateAccountData)) {
	                    preparedStatement.setString(1, name);
	                    preparedStatement.setString(2, username);
	                    preparedStatement.setString(3, newPassword);  // Use the new variable
	                    preparedStatement.setString(4, contact);
	                    preparedStatement.setString(5, address);
	                    preparedStatement.setString(6, email);
	                    preparedStatement.setString(7, role_list.getSelectionModel().getSelectedItem());
	                    preparedStatement.setInt(8, ID);

	                    preparedStatement.executeUpdate();

	                    alert = new Alert(AlertType.INFORMATION);
	                    alert.setTitle("Success");
	                    alert.setHeaderText(null);
	                    alert.setContentText("Successfully Updated!!");
	                    alert.showAndWait();

	                    accountShowData();
	                    clear();

	                } catch (SQLException e) {
	                    e.printStackTrace();
	                    // Handle specific SQL exception, if needed
	                }
	            } else {
	                alert = new Alert(AlertType.ERROR);
	                alert.setTitle("Cancelled");
	                alert.setHeaderText(null);
	                alert.setContentText("Cancelled");
	                alert.showAndWait();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            // Handle other exceptions, if needed
	        }
	    }
	}

		
	

	@FXML
	void deleteAccountBtn() {

		String name = nametxt.getText();
		String username = usernametxt.getText();
		String password = passwordtxt.getText();
		String contact = contacttxt.getText();
		String address = addresstxt.getText();
		String email = emailtxt.getText();

		if (name == null || name.isEmpty() || 
			username == null || username.isEmpty() || 
			password == null || password.isEmpty() || 
			contact == null || contact.isEmpty() ||
			address == null || address.isEmpty() ||
			email == null || email.isEmpty() || 
			role_list.getSelectionModel().getSelectedItem() == null) {

			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Please select the account you want to delete");
			alert.showAndWait();

		} else {

			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to delete?");
			Optional<ButtonType> option = alert.showAndWait();

			if (option.get().equals(ButtonType.OK)) {

				String deleteAccount = "DELETE from accounts WHERE ID = " + ID;

				try {
					prepare = connect.prepareStatement(deleteAccount);
					prepare.executeUpdate();

					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Success");
					alert.setHeaderText(null);
					alert.setContentText("Successfully deleted!!");
					alert.showAndWait();

					accountShowData();
					clear();

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Cancel");
				alert.setHeaderText(null);
				alert.setContentText("Cancelled");
				alert.showAndWait();
			}
		}
	}

	@FXML
	void getAccountSelected() {

		data Data = accountsTableView.getSelectionModel().getSelectedItem();
		int num= accountsTableView.getSelectionModel().getSelectedIndex();

		if ((num -1) < -1) {
			return;
		}	
		
		ID = Data.getID();
		nametxt.setText(Data.getName());
		usernametxt.setText(Data.getUsername());

		String password = Data.getPassword();
		String passwordToShow = "*".repeat(password.length());

		passwordtxt.setText(passwordToShow);

		contacttxt.setText(Data.getContact());
		addresstxt.setText(Data.getAddress());
		emailtxt.setText(Data.getEmail());
		role_list.getSelectionModel().select(Data.getRole());

		}
	

	@FXML
	void clear() {
		nametxt.setText(null);
		usernametxt.setText(null);
		passwordtxt.setText(null);
		contacttxt.setText(null);
		addresstxt.setText(null);
		emailtxt.setText(null);
		role_list.getSelectionModel().clearSelection();
	}

//----TRANSACTION TABLE----// 

	@FXML
	private TableView<transaction> transactionstableView;

	@FXML
	private TableColumn<transaction, String> action;

	@FXML
	private TableColumn<transaction, Date> date;

	@FXML
	private TableColumn<transaction, Integer> employeeid;

	@FXML
	private TableColumn<transaction, Integer> salesid;

	@FXML
	private TableColumn<transaction, Integer> orderid;

	@FXML
	private TableColumn<transaction, Time> time;

	@FXML
	private TableColumn<transaction, Integer> total;

	@FXML
	private PieChart pieChart;

	@FXML
	private BarChart<?, ?> barChart;

	@FXML
	private LineChart<?, ?> lineChart;

	@FXML
	public ObservableList<transaction> transactionDataList() {

		ObservableList<transaction> transactionData = FXCollections.observableArrayList();

		String transactionSql = "SELECT * FROM sales";
		connect = DatabaseConnection.connectDB();

		try {

			prepare = connect.prepareStatement(transactionSql);
			rs = prepare.executeQuery();

			transaction transacData;

			while (rs.next()) {

				transacData = new transaction(rs.getInt("sales_id"), rs.getInt("emp_id"),
						rs.getString("action"), rs.getInt("order_id"), rs.getInt("total"), rs.getTime("time"),
						rs.getDate("date"));

				transactionData.add(transacData);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return transactionData;
	}

	@FXML
	private ObservableList<transaction> transactionListData;

	@FXML
	public void transactionShowData() {
		transactionListData = transactionDataList();

		salesid.setCellValueFactory(new PropertyValueFactory<>("salesID"));
		employeeid.setCellValueFactory(new PropertyValueFactory<>("empID"));
		action.setCellValueFactory(new PropertyValueFactory<>("action"));
		orderid.setCellValueFactory(new PropertyValueFactory<>("orderID"));
		total.setCellValueFactory(new PropertyValueFactory<>("total"));
		time.setCellValueFactory(new PropertyValueFactory<>("time"));
		date.setCellValueFactory(new PropertyValueFactory<>("date"));

		transactionstableView.setItems(transactionListData);
	}

	@FXML
	public void bestSellerChart() {

		String sql = "SELECT stocks.description as product_name, COUNT(*) as order_count FROM orders JOIN stocks ON stocks.product_id = orders.product_id "
				+ "GROUP BY stocks.description ORDER BY order_count DESC LIMIT 3";

		connect = DatabaseConnection.connectDB();

		List<PieChart.Data> pieChartData = new ArrayList<>();

		try {
			prepare = connect.prepareStatement(sql);
			rs = prepare.executeQuery();

			while (rs.next()) {

				pieChartData.add(new PieChart.Data(rs.getString(1), rs.getInt(2)));
			}

			pieChart.getData().addAll(pieChartData);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void incomeChart() {

		String incomeSql = "SELECT date, SUM(total) AS total_sum\n"
				+ "FROM sales\n"
				+ "WHERE date BETWEEN CURDATE() - INTERVAL 6 DAY AND CURDATE()\n"
				+ "GROUP BY date\n"
				+ "ORDER BY date ASC";

		XYChart.Series incomeData = new XYChart.Series();
		connect = DatabaseConnection.connectDB();

		try {
			prepare = connect.prepareStatement(incomeSql);
			rs = prepare.executeQuery();

			while (rs.next()) {
				incomeData.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
			}

			barChart.getData().add(incomeData);
		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

	@FXML
	private Label totalCustomer;

	@FXML
	private Label totalSales;

	@FXML
	private Label lowestStock;

	@FXML
	public void displayTotalCustomer() {

		String sql = "SELECT COUNT(sales_id) FROM sales";
		connect = DatabaseConnection.connectDB();

		try {
			int nc = 0;
			prepare = connect.prepareStatement(sql);
			rs = prepare.executeQuery();

			if (rs.next()) {
				nc = rs.getInt("COUNT(sales_id)");
			}

			totalCustomer.setText(String.valueOf(nc));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void displayTotalSales() {

		String sql = "SELECT SUM(total) FROM sales WHERE action = 'Proccessed Order'";
		connect = DatabaseConnection.connectDB();

		try {
			int ts = 0;
			prepare = connect.prepareStatement(sql);
			rs = prepare.executeQuery();

			if (rs.next()) {
				ts = rs.getInt("SUM(total)");
			}

			totalSales.setText(String.valueOf("₱" + ts));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void displayLowestStock() {

		String sql = "SELECT description FROM stocks ORDER BY qty ASC LIMIT 3";
		connect = DatabaseConnection.connectDB();

		try {

			StringBuilder stockDescriptions = new StringBuilder();

			prepare = connect.prepareStatement(sql);
			rs = prepare.executeQuery();

			while (rs.next()) {
				String description = rs.getString("description");
				stockDescriptions.append(description).append("\n");
			}

			lowestStock.setText(stockDescriptions.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void switchTab(ActionEvent event) {

		if (event.getSource() == dashboardbtn) {

			tabPane.getSelectionModel().select(0);

		} else if (event.getSource() == inventorybtn) {

			tabPane.getSelectionModel().select(1);
		} else if (event.getSource() == accountsbtn) {

			tabPane.getSelectionModel().select(2);
		} else {

			tabPane.getSelectionModel().select(3);
		}
	}

	@FXML
	void logout() {

		try {

			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Error Messenge");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to logout?");
			Optional<ButtonType> option = alert.showAndWait();

			if (option.get().equals(ButtonType.OK)) {

				logout_btn.getScene().getWindow().hide();
				Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
				Stage stage = new Stage();
				Scene scene = new Scene(root);
				Image icon = new Image("/assets/momocha-icon.png");
				stage.getIcons().add(icon);	
				stage.setMaximized(false);
				stage.setResizable(false);
				stage.setTitle("Login");
				stage.setScene(scene);				
				stage.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		statusList();
		inventoryCategory();
		inventoryShowData();

		filterFld();

		roleList();
		accountShowData();

		bestSellerChart();
		incomeChart();
		transactionShowData();
		displayTotalCustomer();
		displayTotalSales();
		displayLowestStock();
	}
}
