from flask import Flask, request
from flask_cors import CORS
from sheet_detection import process_answer_sheet
import cv2
import numpy as np
import base64

app = Flask(__name__)
CORS(app)

@app.route("/hi")
def home():
    return "Hello, World!"

@app.post("/process")
def process():
    data = request.json
    base64_str = data.get('image')

    if not base64_str:
        print("no image provided")
        return "No image provided", 400

    header, encoded = base64_str.split(',', 1)
    image_data = base64.b64decode(encoded)

    np_array = np.frombuffer(image_data, np.uint8)
    image = cv2.imdecode(np_array, cv2.IMREAD_COLOR)

    if image is None:
        return "Invalid image format", 400

    num_rows = request.args.get('num_rows', default=15, type=int)
    answers = process_answer_sheet(image, num_rows, 6, marked_threshold=0.7)
    print(answers)
    return answers




if __name__ == "__main__":
    app.run(host='0.0.0.0', port=5000, debug=True)