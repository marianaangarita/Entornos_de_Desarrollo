# Simulacro de Examen Docker: Node.js y MongoDB

Este repositorio contiene la solución al ejercicio de simulación de examen de Docker, en el cual se despliega una aplicación web en Node.js que se conecta a una base de datos MongoDB. A continuación, se explica el proceso paso a paso de lo que se ha realizado y el porqué de cada decisión.

## Paso 1: Configuración de la Aplicación Node.js (`package.json`)

Para comenzar, se inicializó el proyecto Node.js y se configuraron las dependencias necesarias.
- **`express`**: Utilizado para crear el servidor web de forma sencilla.
- **`mongoose`**: Esencial para interactuar y conectar la aplicación con la base de datos MongoDB.

## Paso 2: Creación de la Lógica de Conexión (`index.js`)

Se creó el archivo principal de la aplicación (`index.js`), que cumple dos funciones principales:
1. **Servidor Web**: Levanta un servidor Express escuchando en el puerto 3000.
2. **Conexión a MongoDB**: Se estableció la URL de conexión `mongodb://root:DoD_CRM_DATABASE_25@mongodb:27017/admin`.
   - Se utiliza `mongodb` como el nombre del host (que coincide con el nombre del servicio en Docker Compose).
   - Se incluyen las credenciales de administrador (root y DoD_CRM_DATABASE_25) configuradas previamente en la base de datos.

## Paso 3: Dockerización de la Aplicación Node.js (`Dockerfile`)

Para empaquetar la aplicación y asegurar que se ejecute en cualquier entorno, se creó un `Dockerfile`:
- **Imagen Base (`FROM node:18-alpine`)**: Se eligió la versión Alpine por ser más ligera, reduciendo el tamaño final de la imagen y mejorando la seguridad.
- **Directorio de Trabajo (`WORKDIR /app`)**: Se define un directorio interno dentro del contenedor para aislar el código.
- **Caché de Dependencias (`COPY package*.json ./` y `RUN npm install`)**: Se copian primero los archivos de dependencias y se instalan antes de copiar el código fuente. Esto aprovecha la caché de Docker y acelera reconstrucciones futuras si el código cambia pero las dependencias no.
- **Copia del Código (`COPY . .`)**: Se copia el resto de los archivos al contenedor.
- **Exposición del Puerto (`EXPOSE 3000`)**: Se indica explícitamente el puerto por el que la aplicación escuchará tráfico.
- **Comando de Inicio (`CMD ["npm", "start"]`)**: Se define el comando que ejecutará la aplicación al iniciar el contenedor.

## Paso 4: Orquestación con Docker Compose (`docker-compose.yaml`)

Finalmente, para levantar simultáneamente tanto la aplicación como la base de datos, se configuró el archivo `docker-compose.yaml`:

### Servicio `mongodb`:
- Se utiliza la imagen oficial `mongo:latest`.
- Se definen variables de entorno (`MONGO_INITDB_ROOT_USERNAME` y `MONGO_INITDB_ROOT_PASSWORD`) para configurar un usuario administrador y contraseña (requerido para mayor seguridad y definido en el index.js).
- Se mapea un **volumen** (`mongodb_data:/data/db`) para garantizar la persistencia de los datos, de modo que la información no se pierda si el contenedor se destruye o reinicia.
- Se conecta a una red personalizada.

### Servicio `DoD` (Aplicación Node.js):
- Se construye a partir del `Dockerfile` actual (`build: .`).
- Se expone el puerto 3000 hacia la máquina host (`ports: - "3000:3000"`).
- Se declara una dependencia (`depends_on: - mongodb`) para asegurar que el contenedor de la aplicación espere al de la base de datos antes de iniciar (aunque Node.js maneja reintentos, es una buena práctica).
- Se conecta a la misma red personalizada que MongoDB (`DoD-CRM-NETWORK`) para que ambos contenedores puedan comunicarse entre sí usando sus nombres de servicio.

---
Con esta configuración, ejecutar `docker-compose up -d` descargará las imágenes, construirá la aplicación, configurará las redes y volúmenes, y dejará un entorno completo y funcional listo para ser utilizado.

---
## Manual de Migración: De Node.js + MongoDB a Flask + PostgreSQL

Si en el futuro se requiere cambiar la tecnología de la aplicación a **Flask (Python)** y la base de datos a **PostgreSQL**, este es el manual paso a paso a seguir para realizar la migración de forma exitosa.

### Paso 1: Eliminar archivos de Node.js
Lo primero es limpiar el entorno de las dependencias antiguas de JavaScript.

**Terminal:**
```bash
rm package.json package-lock.json index.js
```
- **El Porqué**: La aplicación dejará de estar basada en el ecosistema de JavaScript/Node.js, por lo que estos archivos ya no tienen utilidad y podrían causar conflictos o confusión en el proyecto.

### Paso 2: Crear y definir las Dependencias de Python
Ahora crearemos el archivo que Python utiliza para gestionar las librerías necesarias.

**Terminal:**
```bash
echo "Flask==3.0.0" > requirements.txt
echo "psycopg2-binary==2.9.9" >> requirements.txt
```
- **El Porqué**: Flask es el framework web en Python que reemplazará a Express. `psycopg2-binary` es el adaptador necesario para que Python pueda comunicarse de forma nativa y eficiente con la base de datos PostgreSQL.

### Paso 3: Reescribir la Lógica de la Aplicación en Python
Crearemos el nuevo archivo principal del servidor.

**Terminal:**
```bash
touch app.py
```
*(Luego, abrir `app.py` en un editor y programar la lógica de Flask)*

- **El Porqué**: Python usa una sintaxis completamente diferente a Node.js. En este archivo `app.py` se debe inicializar Flask (`app = Flask(__name__)`) y establecer la cadena de conexión a la nueva base de datos usando el puerto estándar de PostgreSQL, que es el `5432` (ej. `postgresql://root:DoD_CRM_DATABASE_25@postgres:5432/admin`).

### Paso 4: Actualizar el entorno en el `Dockerfile`
Debemos modificar las instrucciones de construcción del contenedor para que use Python.

**Terminal (abrir Dockerfile y reemplazar su contenido):**
```dockerfile
FROM python:3.9-alpine
WORKDIR /app
COPY requirements.txt ./
RUN pip install --no-cache-dir -r requirements.txt
COPY . .
EXPOSE 5000
CMD ["python", "app.py"]
```
- **El Porqué**: El entorno de ejecución cambia. Necesitamos una imagen base de Python (`python:3.9-alpine`). El comando para instalar librerías pasa a ser `pip install`. Por último, Flask expone tradicionalmente el puerto `5000`, y el comando de arranque debe invocar a Python para ejecutar `app.py`.

### Paso 5: Modificar la Orquestación en `docker-compose.yaml`
Es vital adaptar el orquestador para que levante la nueva base de datos y la conecte a la nueva app.

**Terminal (ejemplo de configuración parcial a cambiar):**
```yaml
# En el servicio postgres (reemplaza a mongodb)
    image: postgres:15-alpine
    environment:
      POSTGRES_USER: root
      POSTGRES_PASSWORD: DoD_CRM_DATABASE_25
      POSTGRES_DB: admin
    volumes:
      - postgres_data:/var/lib/postgresql/data
```
- **El Porqué**: PostgreSQL es un motor relacional completamente distinto a MongoDB. Requiere su propia imagen oficial de Docker, usa distintas variables de entorno para su configuración inicial y guarda sus datos en una ruta interna diferente. Actualizar el orquestador garantiza que se despliegue la base correcta y que la app Flask sepa conectarse a ella en la red (`DoD-CRM-NETWORK`).

### Paso 6: Levantar el Nuevo Entorno
Finalmente, se reconstruyen las imágenes y se levantan los servicios.

**Terminal:**
```bash
docker-compose up -d --build
```
- **El Porqué**: El parámetro `--build` fuerza a Docker a ignorar la imagen de Node.js previamente construida en caché, forzando la lectura del nuevo `Dockerfile` para descargar Python, instalar los `requirements.txt` y levantar los contenedores con la nueva arquitectura migrada.
