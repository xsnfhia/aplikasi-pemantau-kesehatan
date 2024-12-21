package com.example.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class FlaskAPIHandler {

    public static String sendBMIRequest(int height, int weight, int sex) {
        try {
            String apiUrl = "http://127.0.0.1:5000/predict_bmi";
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            JSONObject jsonData = new JSONObject();
            jsonData.put("height", height);
            jsonData.put("weight", weight);
            jsonData.put("sex", sex);

            OutputStream os = connection.getOutputStream();
            os.write(jsonData.toString().getBytes("utf-8"));
            os.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String response = br.readLine();
            JSONObject jsonResponse = new JSONObject(response);

            return "BMI Prediction: " + jsonResponse.getDouble("prediction");

        } catch (Exception e) {
            e.printStackTrace();
            return "Error connecting to Flask API";
        }
    }

    public static String sendHeartDiseaseRequest(int age, int bloodPressure, int cholesterol) {
        try {
            String apiUrl = "http://127.0.0.1:5000/predict_heart";
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            JSONObject jsonData = new JSONObject();
            JSONArray featuresArray = new JSONArray();
            featuresArray.put(age);
            featuresArray.put(bloodPressure);
            featuresArray.put(cholesterol);

            jsonData.put("features", featuresArray);

            OutputStream os = connection.getOutputStream();
            os.write(jsonData.toString().getBytes("utf-8"));
            os.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            JSONObject jsonResponse = new JSONObject(response.toString());
            if (jsonResponse.has("prediction")) {
                return "Heart Disease Prediction: " + jsonResponse.getInt("prediction");
            } else {
                return "Error in response: " + jsonResponse.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error connecting to Flask API";
        }
    }
}
