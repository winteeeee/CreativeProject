package frontend.Boundary;

import frontend.Boundary.All.DongAnalysis;
import frontend.Boundary.All.LargeCategoryAnalysis;
import frontend.Boundary.All.MonthAnalysis;
import frontend.Boundary.All.TotalAnalysis;
import frontend.Enum.Sectors;
import frontend.Enum.Town;
import frontend.Enum.Village;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AllAnalysisController {
    @FXML
    private Button backBtn;
    @FXML
    private Button foreginAnalysisBtn;

    @FXML
    private Button totalChartbtn;
    @FXML
    private Button dongChartbtn;
    @FXML
    private Button largeCategoryChartbtn;
    @FXML
    private Button monthChartshow4btn;

    private Town town;
    private Village village;
    private Sectors sectors;

    public void initialize() {

    }
    @FXML
    private void moveToForeignerAnalysisPage()throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view/ForeignerAnalysisPage.fxml"));
        Parent otherPage = loader.load();

        ForeignerAnalysisController controller = loader.getController();
        controller.initData(town,village,sectors);

        Scene currentScene = foreginAnalysisBtn.getScene();
        currentScene.setRoot(otherPage);
        Stage primaryStage = (Stage) currentScene.getWindow();
        primaryStage.setTitle("외국인 분석 페이지");
        primaryStage.setWidth(860);
        primaryStage.setHeight(560);
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

    @FXML
    private void handleShowChart(ActionEvent event) throws Exception {
        Button clickedButton = (Button) event.getSource();
        if (clickedButton.getId().equals("totalChartbtn")) {
            Stage primaryStage = (Stage) clickedButton.getScene().getWindow();
            TotalAnalysis totalAnalysis = new TotalAnalysis(town, village, sectors);
            totalAnalysis.start(primaryStage);
        } else if (clickedButton.getId().equals("dongChartbtn")) {
            Stage primaryStage = (Stage) clickedButton.getScene().getWindow();
            DongAnalysis dongStats = new DongAnalysis(town, village, sectors);
            dongStats.start(primaryStage);
        }else if (clickedButton.getId().equals("largeCategoryChartbtn")) {
            Stage primaryStage = (Stage) clickedButton.getScene().getWindow();
            LargeCategoryAnalysis largeCategoryStats = new LargeCategoryAnalysis(town, village, sectors);
            largeCategoryStats.start(primaryStage);
        }else if (clickedButton.getId().equals("monthChartshow4btn")) {
            Stage primaryStage = (Stage) clickedButton.getScene().getWindow();
            MonthAnalysis monthStats = new MonthAnalysis(town, village, sectors);
            monthStats.start(primaryStage);
        }
    }
    public void initData(Town town, Village village, Sectors sectors) {
        this.town = town;
        this.village = village;
        this.sectors = sectors;
    }

}
