const express = require('express');
const mongoose = require('mongoose');

const app = express();
const port = 3000;

// Construimos la URL de conexión usando las credenciales del docker-compose.yaml
// mongod es el nombre del servicio en docker-compose
const mongoUrl = 'mongodb://root:DoD_CRM_DATABASE_25@mongodb:27017/admin';

mongoose.connect(mongoUrl)
  .then(() => console.log('Conectado a MongoDB correctamente'))
  .catch(err => console.error('Error conectando a MongoDB:', err));

app.get('/', (req, res) => {
  res.send('¡Hola! Soy la aplicación Node.js del simulacro de examen.');
});

app.listen(port, () => {
  console.log(`Aplicación DoD escuchando en el puerto ${port}`);
});
