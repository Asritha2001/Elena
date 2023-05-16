import React, { useEffect, useRef, useState } from 'react';
import axios from 'axios';
import './RotatingContainers.css';

const RotatingContainers = () => {
  const containerRef = useRef(null);
  const [reviews, setReviews] = useState([]);

  useEffect(() => {


    const readReviewsFromFile = async () => {
      try {
        const response = await axios.get('http://localhost:9000/api/reviews');
        setReviews(response.data.reviews);
      } catch (error) {
        console.error('Error reading reviews from file:', error);
      }
    };

    // Choose either fetchReviews or readReviewsFromFile based on your requirement
    //fetchReviews();
    readReviewsFromFile();

    const container = containerRef.current;
    const scrollWidth = container.scrollWidth;
    let scrollLeft = 0;

    const scrollContainers = () => {
      scrollLeft += 0.05;
      if (scrollLeft >= scrollWidth) {
        scrollLeft = 0;
      }
      container.scrollLeft = scrollLeft;
      requestAnimationFrame(scrollContainers);
    };

    requestAnimationFrame(scrollContainers);

    return () => {
      cancelAnimationFrame(scrollContainers);
    };
  }, []);

  return (
    <div className="rotating-containers" ref={containerRef}>
      {reviews.map((review, index) => (
        <div className="container4" key={index}>
          <p>{review}</p>
        </div>
      ))}
    </div>
  );
};

export default RotatingContainers;
