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
