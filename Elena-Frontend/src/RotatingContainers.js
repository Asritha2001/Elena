import React, { useEffect, useRef } from 'react';
import './RotatingContainers.css';

const RotatingContainers = () => {
  const containerRef = useRef(null);

    useEffect(() => {
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
      <div className="container4">
        <p>Container 1</p>
      </div>
      <div className="container4">
        <p>Container 2</p>
      </div>
      <div className="container4">
        <p>Container 3</p>
      </div>
      <div className="container4">
        <p>Container 4</p>
      </div>
      <div className="container4">
        <p>Container 5</p>
      </div>
    </div>
  );
};

export default RotatingContainers;
