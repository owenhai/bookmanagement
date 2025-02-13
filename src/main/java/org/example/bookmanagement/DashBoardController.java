package org.example.bookmanagement;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class DashBoardController implements Initializable {
    @FXML
    private Button AvailableBooks_addbtn;

    @FXML
    private TextField AvailableBooks_author;

    @FXML
    private TextField AvailableBooks_bookid;

    @FXML
    private TextField AvailableBooks_booktitle;

    @FXML
    private Button AvailableBooks_clearbtn;

    @FXML
    private TableColumn<BookData, String> AvailableBooks_col_author;

    @FXML
    private TableColumn<BookData, String> AvailableBooks_col_bookid;

    @FXML
    private TableColumn<BookData, String> AvailableBooks_col_booktitle;

    @FXML
    private TableColumn<BookData, String> AvailableBooks_col_date;

    @FXML
    private TableColumn<BookData, String> AvailableBooks_col_genre;

    @FXML
    private TableColumn<BookData, String> AvailableBooks_col_price;


    @FXML
    private TableColumn<BookData, String> AvailableBooks_col_123;

    @FXML
    private DatePicker AvailableBooks_date;

    @FXML
    private Button AvailableBooks_deletebtn;

    @FXML
    private TextField AvailableBooks_genre;

    @FXML
    private ImageView AvailableBooks_imageview;

    @FXML
    private Button AvailableBooks_importbtn;

    @FXML
    private TextField AvailableBooks_price;

    @FXML
    private TextField AvailableBooks_search;

    @FXML
    private TableView<BookData> AvailableBooks_tableView;

    @FXML
    private Button AvailableBooks_updbtn;

    @FXML
    private AnchorPane AvailableBooks_form;

    @FXML
    private Button AvailableBooks_btn;

    @FXML
    private Button close;

    @FXML
    private Label dashboard_AB;

    @FXML
    private Label dashboard_TC;

    @FXML
    private Label dashboard_TI;

    @FXML
    private Button dashboard_btn;

    @FXML
    private BarChart<?, ?> dashboard_customerchart;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private AreaChart<?, ?> dashboard_incomechart;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button minimize;

    @FXML
    private Button purchase_addbtn;

    @FXML
    private ComboBox<?> purchase_bookid;

    @FXML
    private ComboBox<?> purchase_booktitle;


    @FXML
    private Button purchase_dltbtn;

    @FXML
    private Button purchase_btn;

    @FXML
    private TableView<CustomerData> purchase_tableView;

    @FXML
    private TableColumn<CustomerData, String> purchase_col_author;

    @FXML
    private TableColumn<CustomerData, String> purchase_col_bookid;

    @FXML
    private TableColumn<CustomerData, String> purchase_col_genre;

    @FXML
    private TableColumn<CustomerData, String> purchase_col_price;

    @FXML
    private TableColumn<CustomerData, String> purchase_col_quantity;

    @FXML
    private TableColumn<CustomerData, String> purchase_col_title;

    @FXML
    private AnchorPane purchase_form;

    @FXML
    private TextField purchase_cusmail;

    @FXML
    private Label purchase_info_author;

    @FXML
    private Label purchase_info_bookid;

    @FXML
    private Label purchase_info_date;

    @FXML
    private Label purchase_info_genre;

    @FXML
    private Label purchase_info_title;

    @FXML
    private Spinner<Integer> purchase_quantity;

    @FXML
    private Button purchase_paybtn;

    @FXML
    private Label purchase_total;

    @FXML
    private TextField purchase_cashier;

    @FXML
    private Label username;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;
    private Image image;



    public void dashboardic()
    {
        dashboard_incomechart.getData().clear();

        String sql = "SELECT date, SUM(total) FROM customer_info GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 6";

        connect = database.connectDb();

        try{
            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            dashboard_incomechart.getData().add(chart);

        }catch(Exception e){e.printStackTrace();}

    }

    public void dashboardcc()
    {

        dashboard_customerchart.getData().clear();

        String sql = "SELECT date, COUNT(id) FROM customer_info GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 4";

        connect = database.connectDb();

        try{
            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            dashboard_customerchart.getData().add(chart);

        }catch(Exception e){e.printStackTrace();}

    }

    public void dashboardAB(){

        String sql = "SELECT COUNT(id) FROM book";

        connect = database.connectDb();
        int countAB = 0;
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if(result.next()){
                countAB = result.getInt("COUNT(id)");
            }

            dashboard_AB.setText(String.valueOf(countAB));

        }catch(Exception e){e.printStackTrace();}
    }

    public void dashboardTI(){

        String sql = "SELECT SUM(total) FROM customer_info";

        connect = database.connectDb();
        double sumTotal = 0;
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if(result.next()){
                sumTotal = result.getDouble("SUM(total)");
            }

            dashboard_TI.setText("$" + String.valueOf(sumTotal));

        }catch(Exception e){e.printStackTrace();}
    }

    public void dashboardTC(){
        String sql = "SELECT COUNT(id) FROM customer_info";

        connect = database.connectDb();
        int countTC = 0;
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if(result.next()){
                countTC = result.getInt("COUNT(id)");
            }

            dashboard_TC.setText(String.valueOf(countTC));

        }catch(Exception e){e.printStackTrace();}

    }

    private void printBillToPDF(int customerId, double totalAmount, java.sql.Date date, String cashierName) {
        try {
            String pdfFilePath = "Bill_" + customerId + ".pdf";

            PdfWriter writer = new PdfWriter(pdfFilePath);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            StringBuilder billContent = new StringBuilder();
            billContent.append("BOOK STORE INVOICE\n");
            billContent.append("=============================\n");
            billContent.append("Customer ID: ").append(customerId).append("\n");
            billContent.append("Cashier: ").append(cashierName).append("\n"); // Thêm tên thu ngân
            billContent.append("Date: ").append(date).append("\n");
            billContent.append("--------------------------------------------------\n");

            billContent.append(String.format("%-30s %-10s %-10s\n", "Title", "Quantity", "Price"));
            billContent.append("--------------------------------------------------\n");

            String sql = "SELECT title, quantity, price FROM customer WHERE customer_id = ?";
            try (PreparedStatement prepare = connect.prepareStatement(sql)) {
                prepare.setInt(1, customerId);
                try (ResultSet result = prepare.executeQuery()) {
                    while (result.next()) {
                        String title = result.getString("title");
                        int quantity = result.getInt("quantity");
                        double price = result.getDouble("price");

                        billContent.append(String.format("%-30s %-10d $%-9.2f\n", title, quantity, price));
                    }
                }
            }

            billContent.append("--------------------------------------------------\n");
            billContent.append("Total: $").append(totalAmount).append("\n");
            billContent.append("=============================\n");
            billContent.append("Thank you for your purchase!\n");

            document.add(new Paragraph(billContent.toString()));

            document.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bill Generated");
            alert.setHeaderText(null);
            alert.setContentText("Bill has been saved to: " + pdfFilePath);
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void purchasePay() {
        String sql = "INSERT INTO customer_info (customer_id, total, date, cashier_name) VALUES (?, ?, ?, ?)";
        this.connect = database.connectDb();

        try {
            Alert alert;
            if (displayTotal == 0) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Payment Confirmation?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.isPresent() && option.get() == ButtonType.OK) {
                    prepare = connect.prepareStatement(sql);
                    prepare.setInt(1, customerId);
                    prepare.setDouble(2, displayTotal);
                    java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
                    prepare.setDate(3, sqlDate);

                    String cashierName = purchase_cashier.getText();
                    prepare.setString(4, cashierName);

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Payment Successful!");
                    alert.showAndWait();

                    printBillToPDF(customerId, displayTotal, sqlDate, cashierName);

                    String email = purchase_cusmail.getText();
                    if (email != null && !email.isEmpty()) {
                        sendDiscountEmail(email);
                    } else {
                        alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText(null);
                        alert.setContentText("Customer email is empty. Discount email not sent.");
                        alert.showAndWait();
                    }

                    purchase_tableView.getItems().clear();
                    purchase_total.setText("$0.00");
                    purchase_bookid.getSelectionModel().clearSelection();
                    purchase_booktitle.getSelectionModel().clearSelection();
                    purchase_quantity.getValueFactory().setValue(0);
                    purchase_cusmail.clear();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void sendDiscountEmail(String email) {
        String discountCode = generateRandomDiscountCode();

        String subject = "Your Discount Code from Book Store!";
        String message = "Thank you for your purchase!\n"
                + "Use this discount code for 10% off on your next purchase: " + discountCode;

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String senderEmail = "guimaresetmk@gmail.com";
        String senderPassword = "nyvi pjyb cmlq hzvi";

        try {
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
            });

            Message emailMessage = new MimeMessage(session);
            emailMessage.setFrom(new InternetAddress(senderEmail)); // Email gửi
            emailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(email)); // Email nhận
            emailMessage.setSubject(subject);
            emailMessage.setText(message);

            Transport.send(emailMessage);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Email Sent");
            alert.setHeaderText(null);
            alert.setContentText("Discount code has been sent to: " + email);
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Email Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to send discount email. Please check your email settings.");
            alert.showAndWait();
        }
    }

    private String generateRandomDiscountCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder discountCode = new StringBuilder();
        int codeLength = 8;

        for (int i = 0; i < codeLength; i++) {
            int randomIndex = (int) (Math.random() * characters.length());
            discountCode.append(characters.charAt(randomIndex));
        }

        return discountCode.toString();
    }



    private double displayTotal;
    public void purchaseDisplayTotal()
    {
        purchaseCutomerID();
        String sql = "SELECT SUM(price) FROM customer WHERE customer_id = '" +customerId+"'";

        connect = database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if(result.next())
            {
                displayTotal = result.getDouble("SUM(price)");

            }
            purchase_total.setText("$" + String.valueOf(displayTotal));
        }
        catch (Exception e)
        {
         e.printStackTrace();
        }
    }

    public void purchaseBookDelete()
    {
        CustomerData selectedItem = purchase_tableView.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            String sql = "DELETE FROM customer WHERE customer_id = ? AND book_id = ?";

            connect = database.connectDb();
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete the selected item?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.isPresent() && option.get() == ButtonType.OK) {
                    prepare = connect.prepareStatement(sql);
                    prepare.setInt(1, selectedItem.getCustomerId());
                    prepare.setInt(2, selectedItem.getBookId());
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Item deleted successfully!");
                    alert.showAndWait();

                    purchaseShowCustomerListData();
                    purchaseDisplayTotal();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select an item to delete.");
            alert.showAndWait();
        }
    }


    public ObservableList<CustomerData> purchaseListData()
    {
        purchaseCutomerID();
        String sql = "SELECT * FROM customer WHERE customer_id = '" + customerId +  "'";

        ObservableList<CustomerData> listData = FXCollections.observableArrayList();

        connect = database.connectDb();

        try{
            prepare  = connect.prepareStatement(sql);

            result = prepare.executeQuery();

            CustomerData customerD;

            while(result.next()){
                customerD = new CustomerData(result.getInt("customer_id")
                        , result.getInt("book_id")
                        , result.getString("title")
                        , result.getString("author")
                        , result.getString("genre")
                        , result.getInt("quantity")
                        , result.getDouble("price")
                        , result.getDate("date"));

                listData.add(customerD);
            }

        }catch(Exception e){e.printStackTrace();}
        return listData;
    }

    private ObservableList<CustomerData> purchaseCustomerList;
    public void purchaseShowCustomerListData(){
        purchaseCustomerList = purchaseListData();

        purchase_col_bookid.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        purchase_col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        purchase_col_author.setCellValueFactory(new PropertyValueFactory<>("author"));
        purchase_col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        purchase_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        purchase_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        purchase_tableView.setItems(purchaseCustomerList);

    }

    private SpinnerValueFactory<Integer> spinner;

    public void purchaseDisplayQTY(){
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
        purchase_quantity.setValueFactory(spinner);
    }

    private int qty;

    public void purchaseQty(){
        qty = purchase_quantity.getValue();
    }

    private int customerId;
    public void purchaseCutomerID()
    {
        String sql = "SELECT MAX(customer_id) FROM customer";
        int checkCID = 0 ;
        connect = database.connectDb();

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if(result.next()){
                customerId = result.getInt("MAX(customer_id)");
            }

            String checkData = "SELECT MAX(customer_id) FROM customer_info";

            prepare = connect.prepareStatement(checkData);
            result = prepare.executeQuery();

            if(result.next()){
                checkCID = result.getInt("MAX(customer_id)");
            }

            if(customerId == 0){
                customerId += 1;
            }else if(checkCID == customerId){
                customerId = checkCID + 1;
            }

        }catch(Exception e){e.printStackTrace();}

    }

    public void purchasebookInfo()
    {
        String sql = "SELECT * FROM book WHERE title = '"
                +purchase_booktitle.getSelectionModel().getSelectedItem()+"'";

        connect = database.connectDb();

        String bookId = "";
        String title = "";
        String author = "";
        String genre = "";
        String date = "";

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if(result.next()){
                bookId = result.getString("book_id");
                title = result.getString("title");
                author = result.getString("author");
                genre = result.getString("genre");
                date = result.getString("pub_date");
            }

            purchase_info_bookid.setText(bookId);
            purchase_info_title.setText(title);
            purchase_info_author.setText(author);
            purchase_info_genre.setText(genre);
            purchase_info_date.setText(date);

        }catch(Exception e){e.printStackTrace();}

    }

    public void purchaseBookid()
    {
        String sql = "SELECT book_id FROM book ";

        connect = database.connectDb();

        try
        {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList ListData = FXCollections.observableArrayList();

            while (result.next())
            {
                ListData.add(result.getString("book_id"));
            }

            purchase_bookid.setItems(ListData);
            purchaseBookTitle();
        }
        catch (Exception e){e.printStackTrace();}
    }

    public void purchaseBookTitle()
    {
        String sql = "SELECT book_id, title FROM book WHERE book_id = '" +
                        purchase_bookid.getSelectionModel().getSelectedItem() + "'";
        connect = database.connectDb();
        try
        {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList ListData = FXCollections.observableArrayList();

            while (result.next())
            {
                ListData.add(result.getString("title"));
            }

            purchase_booktitle.setItems(ListData);

            purchasebookInfo();
        }
        catch (Exception e){e.printStackTrace();}
    }

    public void searchBook(){

        FilteredList<BookData> filter = new FilteredList<>(availableBooksList, e -> true);

        AvailableBooks_search.textProperty().addListener((Observable, oldValue, newValue) ->{

            filter.setPredicate(predicateBookData -> {

                if(newValue == null || newValue.isEmpty()){
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if(predicateBookData.getBookId().toString().contains(searchKey)){
                    return true;
                }else if(predicateBookData.getTitle().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateBookData.getAuthor().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateBookData.getGenre().toLowerCase().contains(searchKey)){
                    return true;
                }else if(predicateBookData.getDate().toString().contains(searchKey)){
                    return true;
                }else if(predicateBookData.getPrice().toString().contains(searchKey)){
                    return true;
                }else return false;
            });
        });

        SortedList<BookData> sortList = new SortedList(filter);
        sortList.comparatorProperty().bind(AvailableBooks_tableView.comparatorProperty());
        AvailableBooks_tableView.setItems(sortList);

    }


    private double totalP;
    public void purchaseAdd()
    {
        purchaseCutomerID();
        String sql = "INSERT INTO customer (customer_id, book_id,  title, author, genre, quantity, price, date) "
        + "VALUE (?,?,?,?,?,?,?,?)";

        connect  = database.connectDb();
        try {

            Alert alert;

            if(purchase_booktitle.getSelectionModel().getSelectedItem() == null||
                purchase_bookid.getSelectionModel().getSelectedItem() == null)
            {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please select a book");
                alert.showAndWait();
            }
            else {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, String.valueOf(customerId));
                prepare.setString(2, purchase_info_bookid.getText());
                prepare.setString(3, purchase_info_author.getText());
                prepare.setString(4, purchase_info_title.getText());
                prepare.setString(5, purchase_info_genre.getText());
                prepare.setString(6, String.valueOf(qty));

                String checkData = "SELECT title, price FROM book WHERE title = '" + purchase_booktitle.getSelectionModel().getSelectedItem() + "'";

                double priceD = 0;

                statement = connect.createStatement();

                result = statement.executeQuery(checkData);

                if (result.next()) {
                    priceD = result.getDouble("price");
                }

                totalP = (qty * priceD);

                prepare.setString(7, String.valueOf(totalP));

                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                prepare.setDate(8, sqlDate);

                prepare.executeUpdate();
                purchaseDisplayTotal();
                purchaseShowCustomerListData();
            }
        }catch (Exception e) {e.printStackTrace();}
    }

    public void availableBooksAdd(){

        String sql = "INSERT INTO book (book_id, title, author, genre, pub_date, price, image) "
                + "VALUES(?,?,?,?,?,?,?)";

        connect = database.connectDb();

        try{
            Alert alert;

            if(AvailableBooks_bookid.getText().isEmpty()
                    || AvailableBooks_booktitle.getText().isEmpty()
                    || AvailableBooks_author.getText().isEmpty()
                    || AvailableBooks_genre.getText().isEmpty()
                    || AvailableBooks_date.getValue() == null
                    || AvailableBooks_price.getText().isEmpty()
                    || getData.path == null || getData.path == ""){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                // CHECK IF BOOK ID IS ALREADY EXIST
                String checkData = "SELECT book_id FROM book WHERE book_id = '"
                        +AvailableBooks_bookid.getText()+"'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if(result.next()){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Book ID: " + AvailableBooks_bookid.getText() + " was already exist!");
                    alert.showAndWait();
                }else{

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, AvailableBooks_bookid.getText());
                    prepare.setString(2, AvailableBooks_booktitle.getText());
                    prepare.setString(3, AvailableBooks_author.getText());
                    prepare.setString(4, AvailableBooks_genre.getText());
                    prepare.setString(5, String.valueOf(AvailableBooks_date.getValue()));
                    prepare.setString(6, AvailableBooks_price.getText());

                    String uri = getData.path;
                    uri = uri.replace("\\", "\\\\");

                    prepare.setString(7, uri);

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    // TO BE UPDATED THE TABLEVIEW
                    availableBooksShowListData();
                    // CLEAR FIELDS
                    availableBooksClear();
                }
            }
        }catch(Exception e){e.printStackTrace();}

    }

    public void availableBooksUpdate(){

        String uri = getData.path;
        uri = uri.replace("\\", "\\\\");

        String sql = "UPDATE book SET title = '"
                +AvailableBooks_booktitle.getText()+"', author = '"
                +AvailableBooks_author.getText()+"', genre = '"
                +AvailableBooks_genre.getText()+"', pub_date = '"
                +AvailableBooks_date.getValue()+"', price = '"
                +AvailableBooks_price.getText()+"', image = '"
                +uri+"' WHERE book_id = '"+AvailableBooks_bookid.getText()+"'";

        connect = database.connectDb();

        try{
            Alert alert;

            if(AvailableBooks_bookid.getText().isEmpty()
                    || AvailableBooks_booktitle.getText().isEmpty()
                    || AvailableBooks_author.getText().isEmpty()
                    || AvailableBooks_genre.getText().isEmpty()
                    || AvailableBooks_date.getValue() == null
                    || AvailableBooks_price.getText().isEmpty()
                    || getData.path == null || getData.path == "")
            {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }
            else
            {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Book ID: " + AvailableBooks_bookid.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK)){
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successful Updated!");
                    alert.showAndWait();

                    availableBooksShowListData();

                    availableBooksClear();
                }
            }
        }catch(Exception e){e.printStackTrace();}

    }

    public void availableBooksDelete(){

        String sql = "DELETE FROM book WHERE book_id = '"
                +AvailableBooks_bookid.getText()+"'";

        connect = database.connectDb();

        try{
            Alert alert;

            if(AvailableBooks_bookid.getText().isEmpty()
                    || AvailableBooks_booktitle.getText().isEmpty()
                    || AvailableBooks_author.getText().isEmpty()
                    || AvailableBooks_genre.getText().isEmpty()
                    || AvailableBooks_date.getValue() == null
                    || AvailableBooks_price.getText().isEmpty()
                    || getData.path == null || getData.path == "")
            {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Book ID: " + AvailableBooks_bookid.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK)){
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successful Delete!");
                    alert.showAndWait();

                    // TO BE UPDATED THE TABLEVIEW
                    availableBooksShowListData();
                    // CLEAR FIELDS
                    availableBooksClear();
                }
            }
        }catch(Exception e){e.printStackTrace();}

    }


    public void availableBooksClear()
    {
        AvailableBooks_bookid.setText("");
        AvailableBooks_booktitle.setText("");
        AvailableBooks_author.setText("");
        AvailableBooks_genre.setText("");
        AvailableBooks_date.setValue(null);
        AvailableBooks_price.setText("");

        getData.path = "";
        AvailableBooks_imageview.setImage(null);

    }

    public void AvailableBooksInsertImage()
    {
        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png"));

        File file = open.showOpenDialog(main_form.getScene().getWindow());
        if(file != null)
        {
            getData.path =  file.getAbsolutePath();

            image = new Image(file.toURI().toString(),130,124,false,true);
            AvailableBooks_imageview.setImage(image);
        }
    }

    public ObservableList<BookData> AvailableBooksListData()
    {
        ObservableList<BookData> listdata = FXCollections.observableArrayList();

        String sql = "SELECT * FROM book ";

        connect = database.connectDb();

        try
        {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            BookData bookData;

            while (result.next())
            {
                bookData = new BookData(result.getInt("book_id"),
                        result.getString("title"),
                        result.getString("author"),
                        result.getString("genre"),
                        result.getDate("pub_date"),
                        result.getDouble("price"),
                        result.getString("image"));

                listdata.add(bookData);
            }
        }
        catch (SQLException e){e.printStackTrace();}
        return listdata;
    }

    private ObservableList<BookData> availableBooksList;
    public void availableBooksShowListData()
    {
        availableBooksList = AvailableBooksListData();

        AvailableBooks_col_bookid.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        AvailableBooks_col_booktitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        AvailableBooks_col_author.setCellValueFactory(new PropertyValueFactory<>("author"));
        AvailableBooks_col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        AvailableBooks_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        AvailableBooks_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        AvailableBooks_tableView.setItems(availableBooksList);
    }

    public void availableBooksSelect()
    {
        BookData bookD = AvailableBooks_tableView.getSelectionModel().getSelectedItem();
        int num = AvailableBooks_tableView.getSelectionModel().getSelectedIndex();

        if((num - 1)  <  -1 ){return;}

        AvailableBooks_bookid.setText(String.valueOf(bookD.getBookId()));
        AvailableBooks_booktitle.setText(bookD.getTitle());
        AvailableBooks_author.setText(bookD.getAuthor());
        AvailableBooks_genre.setText(bookD.getGenre());
        AvailableBooks_date.setValue(LocalDate.parse(String.valueOf(bookD.getDate())));
        AvailableBooks_price.setText(String.valueOf(bookD.getPrice()));

        getData.path = bookD.getImage();
        String uri = "file:" + bookD.getImage();
        image = new Image(uri, 130,124,false,true );
        AvailableBooks_imageview.setImage(image);
    }


    public void displayUsername()
    {
        String user = getData.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);
        username.setText(user);
    }

    public void switchForm(ActionEvent event)
    {
        if(event.getSource() == dashboard_btn)
        {
            dashboard_form.setVisible(true);
            AvailableBooks_form.setVisible(false);
            purchase_form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color: linear-gradient(to top right, #72513c, #ab853e)");
            AvailableBooks_btn.setStyle("-fx-background-color: transparent");
            purchase_btn.setStyle("-fx-background-color: transparent");

            dashboardAB();
            dashboardTI();
            dashboardTC();
            dashboardic();
            dashboardcc();
        }
        else if (event.getSource() == AvailableBooks_btn)
        {
            dashboard_form.setVisible(false);
            AvailableBooks_form.setVisible(true);
            purchase_form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color: transparent");
            AvailableBooks_btn.setStyle("-fx-background-color: linear-gradient(to top right, #72513c, #ab853e)");
            purchase_btn.setStyle("-fx-background-color: transparent");

            availableBooksShowListData();
        }
        else if (event.getSource() == purchase_btn)
        {
            dashboard_form.setVisible(false);
            AvailableBooks_form.setVisible(false);
            purchase_form.setVisible(true);

            dashboard_btn.setStyle("-fx-background-color: transparent");
            AvailableBooks_btn.setStyle("-fx-background-color: transparent");
            purchase_btn.setStyle("-fx-background-color: linear-gradient(to top right, #72513c, #ab853e)");

            purchaseBookTitle();
            purchaseBookid();
            purchaseShowCustomerListData();
            purchaseDisplayQTY();
            purchaseDisplayTotal();

        }
    }

    private double x = 0;
    private double y = 0;

    public void logout()
    {
        try
        {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals( ButtonType.OK))
            {
                logout.getScene().getWindow().hide();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml.fxml"));

                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                scene.setFill(javafx.scene.paint.Color.TRANSPARENT);

                root.setOnMousePressed((MouseEvent event) ->
                {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });


                root.setOnMouseDragged((MouseEvent event) ->
                {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) ->
                {
                    stage.setOpacity(1);
                });


                stage.initStyle(StageStyle.TRANSPARENT);


                stage.setScene(scene);
                stage.show();
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    public void close()
    {
        System.exit(0);
    }

    public void minimize()
    {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        displayUsername();

        availableBooksShowListData();

        purchaseBookTitle();

        purchaseBookid();

        purchaseShowCustomerListData();

        purchaseDisplayQTY();

        purchaseDisplayTotal();
    }
}