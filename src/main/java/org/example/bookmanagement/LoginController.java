package org.example.bookmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.security.MessageDigest;
public class LoginController
{

    @FXML
    private PasswordField password1;

    @FXML
    private Button resbtn;

    @FXML
    private Button loginbtn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    private Alert alert;


    public void close()
    {
        System.exit(0);
    }

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private double x = 0;
    private double y = 0;

    public void loginAdmin()
    {
        connect = database.connectDb();

        String sql = "SELECT * FROM admin WHERE username = ? and password = ?";

        try{
            Alert alert;

            prepare = connect.prepareStatement(sql);
            prepare.setString(1, username.getText());
            String hashedPassword = hashPassword(password.getText());
            prepare.setString(2, hashedPassword);


            result = prepare.executeQuery();

            if(username.getText().isEmpty() || password.getText().isEmpty()){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                if(result.next()){

                    getData.username = username.getText();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login");
                    alert.showAndWait();

                    loginbtn.getScene().getWindow().hide();

                    Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.initStyle(StageStyle.TRANSPARENT);

                    root.setOnMousePressed((MouseEvent event) ->{
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });

                    root.setOnMouseDragged((MouseEvent event) ->{
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                    });

                    stage.initStyle(StageStyle.TRANSPARENT);

                    stage.setScene(scene);
                    stage.show();

                }else{
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username/Password");
                    alert.showAndWait();
                }
            }

        }catch(Exception e){e.printStackTrace();}
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void registerAdmin() {
        connect = database.connectDb();
        String checkUserSQL = "SELECT * FROM admin WHERE username = ?";
        String insertUserSQL = "INSERT INTO admin (username, password) VALUES (?, ?)";

        try {
            String user = username.getText();
            String pass = password.getText();
            String confirmPass = password1.getText();


            if (user.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
                showAlert(AlertType.ERROR, "Lỗi", "Vui lòng điền đầy đủ thông tin!");
                return;
            }


            if (!pass.equals(confirmPass)) {
                showAlert(AlertType.ERROR, "Lỗi", "Mật khẩu không khớp!");
                return;
            }


            prepare = connect.prepareStatement(checkUserSQL);
            prepare.setString(1, user);
            result = prepare.executeQuery();

            if (result.next()) {
                showAlert(AlertType.ERROR, "Lỗi", "Username đã tồn tại!");
            } else {

                String hashedPassword = hashPassword(pass);
                prepare = connect.prepareStatement(insertUserSQL);
                prepare.setString(1, user);
                prepare.setString(2, hashedPassword);
                prepare.executeUpdate();

                showAlert(AlertType.INFORMATION, "Thành công", "Đăng ký thành công!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    private void showAlert(AlertType type, String title, String content) {
        alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void closeResources() {
        try {
            if (result != null) result.close();
            if (prepare != null) prepare.close();
            if (connect != null) connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}