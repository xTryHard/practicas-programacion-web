const express = require('express');
const app = express();


app.get('/', (req, res) => {
  res.send('Hello World from Nodejs');
});


app.listen(5000, () => {
  console.log('Running...');
});