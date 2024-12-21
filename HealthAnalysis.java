package com.example.service;

import com.example.model.HealthData;

public class HealthAnalysis {

    public static double calculateBMI(HealthData data) {
        return data.getWeight() / Math.pow(data.getHeight() / 100, 2);
    }

    public static String getBMIRecommendation(double bmi) {
        String bmiCategory;
        String advice;

        if (bmi < 18.5) {
            bmiCategory = "Kurus";
            advice = "Disarankan untuk meningkatkan asupan kalori dan berkonsultasi dengan profesional kesehatan.";
        } else if (bmi >= 18.5 && bmi < 24.9) {
            bmiCategory = "Berat badan normal";
            advice = "Pertahankan pola makan sehat dan rutin berolahraga.";
        } else if (bmi >= 25 && bmi < 29.9) {
            bmiCategory = "Kelebihan Berat Badan";
            advice = "Pertimbangkan untuk mengatur pola makan dan meningkatkan aktivitas fisik.";
        } else {
            bmiCategory = "Obesitas";
            advice = "Sebaiknya segera berkonsultasi dengan dokter atau ahli gizi untuk merencanakan perubahan gaya hidup.";
        }

        return bmiCategory + ": " + advice;
    }

    public static String analyzeHeartDiseaseRisk(double prediction) {
        String diseaseCategory;
        String advice;

        if (prediction < 0.5) {
            diseaseCategory = "Tidak ada penyakit jantung";
            advice = "Anda tampaknya tidak memiliki risiko penyakit jantung. Pertahankan gaya hidup sehat.";
        } else if (prediction >= 0.5 && prediction < 0.7) {
            diseaseCategory = "Ada tanda-tanda penyakit jantung, harap periksakan ke dokter";
            advice = "Ada kemungkinan Anda berisiko terhadap penyakit jantung. Disarankan untuk memeriksakan diri ke dokter dan mengatur gaya hidup Anda.";
        } else {
            diseaseCategory = "Penyakit jantung terdeteksi, harap segera periksakan ke dokter";
            advice = "Terdeteksi adanya kemungkinan penyakit jantung. Segera konsultasikan dengan dokter untuk pemeriksaan lebih lanjut.";
        }

        return diseaseCategory + ": " + advice;
    }
}
