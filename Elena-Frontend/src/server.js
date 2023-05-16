const express = require('express');
const fs = require('fs');
const cors = require('cors');

const app = express();
const port = 9000;

app.use(express.json());

// Enable CORS for requests from http://localhost:3000
app.use(cors({
  origin: 'http://localhost:3000'
}));

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

app.get('/api/reviews', (req, res) => {
  fs.readFile('reviews.txt', 'utf8', (err, data) => {
    if (err) {
      console.error(err);
      res.status(500).send('Error reading reviews');
    } else {
      const reviews = data.split('\n').filter(review => review.trim() !== '');
      res.json({ reviews });
    }
  });
});

app.listen(port, () => {
  console.log(`Server running on port ${port}`);
});
