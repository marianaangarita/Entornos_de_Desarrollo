FROM python:3.9-alpine
WORKDIR /app

# Copiamos las dependencias
COPY requirements.txt ./

# Instalamos las dependencias de Python
# Añadimos dependencias de sistema necesarias para psycopg2-binary
RUN apk add --no-cache postgresql-libs && \
    apk add --no-cache --virtual .build-deps gcc musl-dev postgresql-dev && \
    pip install --no-cache-dir -r requirements.txt && \
    apk --purge del .build-deps

# Copiamos el resto de la aplicación
COPY . .

# Exponemos el puerto (ejemplo: 5000 para Flask)
EXPOSE 5000

# Comando para iniciar la aplicación
CMD ["python", "app.py"]
