const express = require('express');
const fs = require('fs');
const app = express();
const port = 9000;

app.use(express.json());

app.post('/reviews', (req, res) => {
  const review = req.body.review;

  fs.appendFile('reviews.txt', review + '\n', (err) => {
    if (err) {
      console.error(err);
      res.status(500).send('Error writing to file');
    } else {
      console.log('Review added to file');
      res.sendStatus(200);
    }
  });
});

app.listen(port, () => {
  console.log(`Server running on port ${port}`);
});
