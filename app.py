from flask import Flask, request, jsonify, render_template, redirect, url_for, session
import joblib
import numpy as np
import logging
import pandas as pd
import secrets

app = Flask(__name__, template_folder='frontend/templates', static_folder='frontend/static')
app.secret_key = secrets.token_hex(16)  

model_bmi = joblib.load('models/decision_tree_model.pkl')
model_heart, feature_names = joblib.load('models/linear_regression_model_with_3_features.pkl')

logging.basicConfig(level=logging.DEBUG)

@app.route('/')
def home():
    session.clear()
    if 'username' in session:
        return redirect(url_for('dashboard'))
    else:
        return redirect(url_for('login'))

@app.route('/login', methods=['GET', 'POST'])
def login():
    if request.method == "POST":
        data = request.form
        username = data.get('username')
        password = data.get('password')

        if username == 'xsnfhia_' and password == 'root':
            session['username'] = username
            return redirect(url_for('dashboard'))
        else:
            return render_template('login.html', error='Username atau password salah.')

    return render_template('login.html')

@app.route('/register', methods=['GET', 'POST'])
def register():
    if request.method == 'POST':
        username = request.form['username']
        email = request.form['email']
        return redirect(url_for('login'))
    return render_template('register.html')

@app.route('/dashboard')
def dashboard():
    if 'username' not in session:
        return redirect(url_for('login'))
    return render_template('dashboard.html')

@app.route('/logout')
def logout():
    session.pop('username', None)
    return redirect(url_for('login'))

@app.route('/predict_bmi', methods=['POST'])
def predict_bmi():

    if 'username' not in session:
        return redirect(url_for('login'))

    try:
        data = request.get_json()
        logging.debug(f"Data yang diterima untuk BMI: {data}")

        height = float(data['height'])
        weight = float(data['weight'])
        sex = int(data['sex'])

        bmi = weight / (height / 100) ** 2

        if bmi < 18.5:
            bmi_category = "Kurus"
            advice = "Disarankan untuk meningkatkan asupan kalori dan berkonsultasi dengan profesional kesehatan."
        elif 18.5 <= bmi < 24.9:
            bmi_category = "Berat badan normal"
            advice = "Pertahankan pola makan sehat dan rutin berolahraga."
        elif 25 <= bmi < 29.9:
            bmi_category = "Kelebihan Berat Badan"
            advice = "Pertimbangkan untuk mengatur pola makan dan meningkatkan aktivitas fisik."
        else:
            bmi_category = "Obesitas"
            advice = "Sebaiknya segera berkonsultasi dengan dokter atau ahli gizi untuk merencanakan perubahan gaya hidup."

        return jsonify({'prediction': bmi, 'bmi_category': bmi_category, 'advice': advice})

    except Exception as e:
        logging.error(f"Error: {str(e)}")
        return jsonify({'error': str(e)}), 400

@app.route('/predict_heart', methods=['POST'])
def predict_heart():

    if 'username' not in session:
        return redirect(url_for('login'))

    try:
        data = request.get_json()
        logging.debug(f"Data diterima: {data}")

        if not data or not all(k in data for k in ['age', 'cholesterol', 'bloodPressure']):
            return jsonify({"error": "Missing required fields: 'age', 'cholesterol', 'bloodPressure'"}), 400

        features = {
            'age': float(data['age']),
            'chol': float(data['cholesterol']),
            'trestbps': float(data['bloodPressure'])
        }
        logging.debug(f"Fitur untuk prediksi (sebelum diurutkan): {features}")

        features_ordered = pd.DataFrame([[features[feature] for feature in feature_names]], columns=feature_names)
        logging.debug(f"DataFrame untuk prediksi (diurutkan): {features_ordered}")

        prediction = model_heart.predict(features_ordered)[0]
        logging.debug(f"Hasil prediksi: {prediction}")

        if prediction < 0.5:
            disease_category = "Tidak ada penyakit jantung"
            advice = "Anda tampaknya tidak memiliki risiko penyakit jantung. Pertahankan gaya hidup sehat."
        elif 0.5 <= prediction < 0.7:
            disease_category = "Ada tanda-tanda penyakit jantung, harap periksakan ke dokter"
            advice = "Ada kemungkinan Anda berisiko terhadap penyakit jantung. Disarankan untuk memeriksakan diri ke dokter dan mengatur gaya hidup Anda."
        else:
            disease_category = "Penyakit jantung terdeteksi, harap segera periksakan ke dokter"
            advice = "Terdeteksi adanya kemungkinan penyakit jantung. Segera konsultasikan dengan dokter untuk pemeriksaan lebih lanjut."

        return jsonify({"prediction": float(prediction), "disease_category": disease_category, "advice": advice})

    except Exception as e:
        logging.error(f"Unexpected error: {str(e)}")
        return jsonify({"error": "An unexpected error occurred.", "details": str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True)
