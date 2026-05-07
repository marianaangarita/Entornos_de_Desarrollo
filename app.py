import os
from flask import Flask
import psycopg2

app = Flask(__name__)

def get_db_connection():
    try:
        conn = psycopg2.connect(
            host="postgres",
            database="admin",
            user=os.environ.get("POSTGRES_USER", "root"),
            password=os.environ.get("POSTGRES_PASSWORD", "DoD_CRM_DATABASE_25"),
            port="5432"
        )
        return conn
    except Exception as e:
        print(f"Error conectando a la base de datos: {e}")
        return None

@app.route('/')
def hello():
    conn = get_db_connection()
    if conn:
        conn.close()
        return '¡Hola! Soy la aplicación Flask (Python) del simulacro de examen, conectada a PostgreSQL.'
    else:
        return '¡Hola! Soy la aplicación Flask, pero hubo un error conectando a PostgreSQL.'

if __name__ == '__main__':
    # Listen on all interfaces so it's accessible outside the container
    app.run(host='0.0.0.0', port=5000)
