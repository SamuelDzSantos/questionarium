import os
from flask import Flask, request, jsonify
from flask_cors import CORS
from langchain_core.messages import HumanMessage, SystemMessage

from custom_chatbot import CustomKnowledgeBot

app = Flask(__name__)
CORS(app)
data_path = r"data.txt"

knowledge_file_path = os.path.join(os.path.dirname(__file__), data_path)
chatbot = CustomKnowledgeBot(custom_knowledge_path=knowledge_file_path)


@app.route("/hi")
def home():
    return "Hello, World!"


@app.route("/chat", methods=['POST'])
def chat():
    try:
        data = request.json

        user_message = data.get('message', '')

        language = data.get('language', 'Brazilian Portuguese')

        messages = [
            SystemMessage(content="You are a helpful website assistant"),
            HumanMessage(content=user_message)
        ]

        response = chatbot.run_chat(messages, language)

        return jsonify({
            'response': response
        })

    except Exception as e:
        return jsonify({
            'error': str(e)
        }), 500


if __name__ == "__main__":
    app.run(host='0.0.0.0', port=5001, debug=True)