package com.example.gui;

import com.example.api.FlaskAPIHandler;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HealthTrackerGUI extends Application {

    @Override
    public void start(Stage primaryStage) {

        TextField heightField = new TextField("170");
        heightField.setPromptText("Height (cm)");
        TextField weightField = new TextField("70");
        weightField.setPromptText("Weight (kg)");
        TextField sexField = new TextField("1");
        sexField.setPromptText("Sex (1=Male, 0=Female)");

        TextField ageField = new TextField();
        ageField.setPromptText("Age");
        TextField bloodPressureField = new TextField();
        bloodPressureField.setPromptText("Blood Pressure");
        TextField cholesterolField = new TextField();
        cholesterolField.setPromptText("Cholesterol");

        ToggleGroup group = new ToggleGroup();
        RadioButton bmiButton = new RadioButton("BMI Prediction");
        bmiButton.setToggleGroup(group);
        bmiButton.setSelected(true);

        RadioButton heartButton = new RadioButton("Heart Disease Prediction");
        heartButton.setToggleGroup(group);

        Label resultLabel = new Label();

        Button predictButton = new Button("Predict");
        predictButton.setOnAction(e -> {
            try {
                String result = "";
                if (bmiButton.isSelected()) {
                    result = FlaskAPIHandler.sendBMIRequest(
                            Integer.parseInt(heightField.getText()),
                            Integer.parseInt(weightField.getText()),
                            Integer.parseInt(sexField.getText())
                    );
                } else if (heartButton.isSelected()) {
                    result = FlaskAPIHandler.sendHeartDiseaseRequest(
                            Integer.parseInt(ageField.getText()),
                            Integer.parseInt(bloodPressureField.getText()),
                            Integer.parseInt(cholesterolField.getText())
                    );
                }
                resultLabel.setText(result);
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid input! Please enter valid numbers.");
            }
        });

        VBox layout = new VBox(10, heightField, weightField, sexField, ageField, bloodPressureField, cholesterolField,
                bmiButton, heartButton, predictButton, resultLabel);
        layout.setPadding(new Insets(15));

        primaryStage.setScene(new Scene(layout, 400, 400));
        primaryStage.setTitle("Health Tracker");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
