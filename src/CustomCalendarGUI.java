import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;


public class CustomCalendarGUI extends Application {

    private final ObservableList<Integer> yearsList = FXCollections.observableArrayList();
    private final ObservableList<Integer> monthsList = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9,
            10, 11, 12);

    @Override
    public void start(Stage primaryStage) {
        // 创建UI组件
        Label titleLabel = new Label("Custom Calendar");
        ChoiceBox<Integer> yearChoiceBox = new ChoiceBox<>(yearsList);
        ChoiceBox<Integer> monthChoiceBox = new ChoiceBox<>(monthsList);
        Button displayCalendarButton = new Button("Display Calendar");
        Button addScheduleButton = new Button("Add Schedule");
        Button displayAllSchedulesButton = new Button("Display All Schedules");
        TextArea scheduleTextArea = new TextArea();

        // 设置组件属性和布局
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(titleLabel, yearChoiceBox, monthChoiceBox, displayCalendarButton, addScheduleButton,
                displayAllSchedulesButton, scheduleTextArea);

        // 填充年份选择框
        for (int i = 2024; i <= 2034; i++) {
            yearsList.add(i);
        }

        // 为显示日历按钮添加事件处理
        displayCalendarButton.setOnAction(event -> {
            int year = yearChoiceBox.getValue();
            int month = monthChoiceBox.getValue();
            CustomCalendarConsole.displayMonthCalendar(year, month);
        });

        // 为添加日程按钮添加事件处理
        addScheduleButton.setOnAction(event -> {
            int year = yearChoiceBox.getValue();
            int month = monthChoiceBox.getValue();
            int day = Integer.parseInt(scheduleTextArea.getText().split("\n")[0]);
            String schedule = scheduleTextArea.getText().split("\n")[1];
            CustomCalendarConsole.addSchedule(year, month, day, schedule);
        });

        // 为显示所有日程按钮添加事件处理
        displayAllSchedulesButton.setOnAction(event -> {
            int year = yearChoiceBox.getValue();
            int month = monthChoiceBox.getValue();
            CustomCalendarConsole.displayAllSchedules(year, month);
        });

        // 创建场景并设置到舞台
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Custom Calendar");
        primaryStage.show();
    }

    // 这里可以复用之前控制台版本的displayMonthCalendar、addSchedule和displayAllSchedules方法

    public static void main(String[] args) {
        launch(args);
    }
}