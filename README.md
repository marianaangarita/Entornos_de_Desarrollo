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
## Propuesta de Migración a Flask y PostgreSQL

Si en el futuro se requiriera cambiar la tecnología de la aplicación a **Flask (Python)** y la base de datos a **PostgreSQL**, estos serían los pasos a seguir y sus razones:

### 1. Actualizar las Dependencias (Python en lugar de Node.js)
- **El Cómo**: Eliminar `package.json` y `package-lock.json`. En su lugar, crear un archivo `requirements.txt` que incluya dependencias como `Flask` y `psycopg2-binary` (o `SQLAlchemy` para ORM).
- **El Porqué**: Flask es un microframework de Python, por lo que necesita el gestor de paquetes de Python (`pip`) para instalar sus bibliotecas. `psycopg2` es el adaptador más popular para conectar Python con PostgreSQL.

### 2. Reescribir la Aplicación (`app.py` en lugar de `index.js`)
- **El Cómo**: Crear un archivo `app.py` con la lógica de inicialización del servidor de Flask (`app = Flask(__name__)`) y establecer la cadena de conexión a PostgreSQL, por ejemplo: `postgresql://usuario:contraseña@postgres:5432/miproyecto`.
- **El Porqué**: La sintaxis y estructura de Express (Node.js) son exclusivas de JavaScript. Hay que reescribir las rutas en Python. La conexión usa el driver de PostgreSQL, que opera por el puerto estándar 5432 (a diferencia del 27017 de MongoDB).

### 3. Modificar el `Dockerfile`
- **El Cómo**: 
  - Cambiar la imagen base a `FROM python:3.9-alpine` (u otra versión de Python).
  - Cambiar el copiado de dependencias a `COPY requirements.txt ./` y el comando de instalación a `RUN pip install --no-cache-dir -r requirements.txt`.
  - Cambiar el puerto expuesto al típico de Flask (`EXPOSE 5000`).
  - Cambiar el comando de ejecución a `CMD ["python", "app.py"]` o mediante `gunicorn`.
- **El Porqué**: El entorno de ejecución pasa de ser Node a Python, y la imagen base, el gestor de dependencias y los comandos de inicio deben reflejar este nuevo lenguaje.

### 4. Actualizar el `docker-compose.yaml`
- **El Cómo**:
  - Cambiar el servicio de base de datos de `mongodb` a `postgres`, usando la imagen oficial `postgres:latest` o `postgres:15-alpine`.
  - Actualizar las variables de entorno para inicializar PostgreSQL (`POSTGRES_USER`, `POSTGRES_PASSWORD`, `POSTGRES_DB`).
  - Cambiar el mapeo del volumen interno a `/var/lib/postgresql/data` (la ruta donde PostgreSQL guarda sus datos).
  - En el servicio de la app (`DoD`), actualizar `depends_on` para que espere al servicio `postgres` y modificar los puertos (`5000:5000`).
- **El Porqué**: PostgreSQL requiere su propia imagen, variables de entorno específicas y una ruta de volumen distinta. El orquestador debe configurar la red para que Flask se conecte al nuevo contenedor de la base de datos relacional.
