FROM node:18-alpine
WORKDIR /app

# Copiamos el package.json y package-lock.json (si existe)
COPY package*.json ./

# Instalamos las dependencias
RUN npm install

# Copiamos el resto de la aplicación
COPY . .

# Exponemos el puerto (ejemplo: 3000)
EXPOSE 3000

# Comando para iniciar la aplicación
CMD ["npm", "start"]
