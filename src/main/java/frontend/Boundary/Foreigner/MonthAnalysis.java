package frontend.Boundary.Foreigner;

import frontend.Boundary.AllStatisticsPageController;
import frontend.Boundary.ForeignerAnalysisController;
import frontend.Control.AnalysisControl;
import backend.DB.DTO.ConsumptionAmountForeignerDTO;
import backend.DB.DTO.DTO;
import backend.DB.Protocol.ProtocolQuery;
import backend.DB.Protocol.ProtocolType;
import frontend.Enum.Sectors;
import frontend.Enum.Town;
import frontend.Enum.Village;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.*;

import javafx.application.Application;
import javafx.scene.control.Button;

public class MonthAnalysis extends Application {
    //법정동과 이용금액을 저장
    TreeMap<String, Double> monthAmountMap = new TreeMap<>();

    //이용금액 출력 포맷
    DecimalFormat decimalFormat = new DecimalFormat("#,##0");

    private String month = "";//달
    private double amount = 0.0; // 이용금액

    private Town town;
    private Village village;
    private Sectors sectors;

    private XYChart.Series<String, Number> series = new XYChart.Series<>();
    private ObservableList<XYChart.Series<String, Number>> chartData;


    public MonthAnalysis(Town town, Village village, Sectors sectors){
        this.town = town;
        this.village = village;
        this.sectors = sectors;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();
        // "이전" 버튼 생성
        Button backButton = new Button("되돌아가기");
        backButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getClassLoader().getResource("view/ForeignerAnalysisPage.fxml"));
                Parent statisticsPage = loader.load();
                ForeignerAnalysisController controller = loader.getController();
                controller.initData(town, village, sectors);
                Scene currentScene = backButton.getScene();
                currentScene.setRoot(statisticsPage);
                Stage prmaryStage = (Stage) currentScene.getWindow();
                prmaryStage.setTitle("Statistics Page");
                prmaryStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // DB에서 월별 소비금액 데이터 추출
        List<DTO> dtoList = AnalysisControl.selectRequest(ProtocolQuery.selectAll, ProtocolType.CAF);
        for (DTO dto : dtoList) {
            //월
            String month = String.valueOf(((ConsumptionAmountForeignerDTO) dto).getMonth());
            // 이용금액
            double amount = ((ConsumptionAmountForeignerDTO) dto).getAmount();

            // 해당 월이 이미 HashMap에 저장되어 있는 경우, 이용금액을 누적하여 합산
            double currentAmount = monthAmountMap.containsKey(month) ? monthAmountMap.get(month) : 0;
            monthAmountMap.put(month, currentAmount + amount);
        }

        // 엔트리를 List에 저장
        List<Map.Entry<String, Double>> entryList = new ArrayList<>(monthAmountMap.entrySet());

        // 엔트리를 금액을 기준으로 내림차순 정렬
        entryList.sort((entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()));

        // 최댓값 출력
        Map.Entry<String, Double> maxEntry = entryList.get(0);
        String maxMonth = maxEntry.getKey();
        double maxAmount = maxEntry.getValue();
        String formattedMaxAmount = decimalFormat.format(maxAmount);
        Label maxLabel = new Label("가장 많이 소비한 달은 " + maxMonth + "월에 " + formattedMaxAmount + "원 입니다.");

        // 최솟값 출력
        Map.Entry<String, Double> minEntry = entryList.get(entryList.size() - 1);
        String minMonth = minEntry.getKey();
        double minAmount = minEntry.getValue();
        String formattedMinAmount = decimalFormat.format(minAmount);
        Label minLabel = new Label("가장 적게 소비한 달은 " + minMonth + "월에 " + formattedMinAmount + "원 입니다.");

        // 데이터 생성
        series.getData().add(new XYChart.Data<>(maxMonth, maxAmount));
        series.getData().add(new XYChart.Data<>(minMonth, minAmount));

        // 그래프 생성 및 데이터 설정
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.getData().add(series);

        // root에 컴포넌트 추가
        root.getChildren().addAll(barChart, maxLabel, minLabel, backButton);

        // Scene 생성
        Scene scene = new Scene(root, 600, 400);

        // Stage 설정
        primaryStage.setTitle("외국인 달별 분석");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
