package frontend.Boundary;

import frontend.Control.AdminLoginControl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPageController
{
    @FXML
    private TextField input_id;
    @FXML
    private PasswordField input_pwd;
    @FXML
    private Button backBtn;
    @FXML
    private Button loginBtn;
    @FXML
    private void handleloginBtn() throws IOException, ClassNotFoundException {
        String id = input_id.getText();
        String pwd = input_pwd.getText();

        if (AdminLoginControl.adminVerification(id, pwd)) {
            moveToDBUpdatePage();
        }
        else{
            showLoginFailedAlert();
        }
    }
    private void showLoginFailedAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("로그인 실패");
        alert.setHeaderText(null);
        alert.setContentText("아이디 또는 비밀번호가 잘못되었습니다.");
        alert.showAndWait();
    }
    private void moveToDBUpdatePage()throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view/DBupdatePage.fxml"));
        Parent otherPage = loader.load();
        //MainPageController mainPageController = FXMLLoader.getController();

        Scene currentScene = loginBtn.getScene();
        currentScene.setRoot(otherPage);
        Stage primaryStage = (Stage) currentScene.getWindow();
        primaryStage.setTitle("DBupdate Page");
        primaryStage.setWidth(600);
        primaryStage.setHeight(400);
        //mainPageController.initialize();
    }

    @FXML
    private void handleBackBtn() throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view/MainPage.fxml"));
        Parent otherPage = loader.load();
        //MainPageController mainPageController = FXMLLoader.getController();

        Scene currentScene = backBtn.getScene();
        currentScene.setRoot(otherPage);
        Stage primaryStage = (Stage) currentScene.getWindow();
        primaryStage.setWidth(600);
        primaryStage.setHeight(400);
        //mainPageController.initialize();
    }
}