package com.example.model;

import java.time.LocalDate;

public class HealthData {
    private int userId;
    private LocalDate date;
    private double weight;
    private double height;
    private int bloodPressure;
    private int heartRate;

    public HealthData(int userId, LocalDate date, double weight, double height, int bloodPressure, int heartRate) {
        this.userId = userId;
        this.date = date;
        this.weight = weight;
        this.height = height;
        this.bloodPressure = bloodPressure;
        this.heartRate = heartRate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(int bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    @Override
    public String toString() {
        return "HealthData{" +
                "userId=" + userId +
                ", date=" + date +
                ", weight=" + weight +
                ", height=" + height +
                ", bloodPressure=" + bloodPressure +
                ", heartRate=" + heartRate +
                '}';
    }
}
