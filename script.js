document.getElementById('bmi-form').addEventListener('submit', function(event) {
    event.preventDefault();

    const height = document.getElementById('height').value;
    const weight = document.getElementById('weight').value;
    const sex = document.getElementById('sex').value;

    const data = {
        height: height,
        weight: weight,
        sex: sex
    };

    fetch('/predict_bmi', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(result => {
            result.advice = undefined;
            result.bmi_category = undefined;
            const bmi = result.prediction.toFixed(2);
            const category = result.bmi_category;
            const advice = result.advice;

            document.getElementById('bmi-result').innerHTML = `
                <strong>BMI:</strong> ${bmi}<br>
                <strong>Kategori:</strong> ${category}<br>
                <strong>Saran:</strong> ${advice}
            `;
        })
        .catch(() => {
            document.getElementById('bmi-result').innerText = 'Error predicting BMI';
        });
});

document.getElementById('heart-form').addEventListener('submit', function(event) {
    event.preventDefault();

    const age = document.getElementById('age').value;
    const cholesterol = document.getElementById('cholesterol').value;
    const bloodPressure = document.getElementById('bloodPressure').value;

    const data = {
        age: age,
        cholesterol: cholesterol,
        bloodPressure: bloodPressure
    };

    fetch('/predict_heart', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(result => {
            result.prediction = undefined;
            const prediction = result.prediction;
            let heartResult;
            let advice;

            if (prediction < 0.3) {
                heartResult = 'Tidak Ada Tanda-Tanda Penyakit Jantung';
                advice = 'Risiko rendah, tetap jaga pola hidup sehat.';
            } else if (prediction < 0.6) {
                heartResult = 'Terdapat Tanda-Tanda Penyakit Jantung';
                advice = 'Harap periksakan lebih lanjut ke dokter.';
            } else {
                heartResult = 'Silakan Konsultasi Dengan Dokter Untuk Pemeriksaan Lebih Lanjut';
                advice = 'Segera periksakan kondisi Anda ke dokter untuk pemeriksaan lebih lanjut.';
            }

            document.getElementById('heart-result').innerHTML = `
                <strong>Prediksi:</strong> ${heartResult}<br>
                <strong>Saran:</strong> ${advice}
            `;
        })
        .catch(() => {
            document.getElementById('heart-result').innerText = 'Error predicting heart disease';
        });
});
