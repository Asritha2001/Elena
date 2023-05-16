import './RouteReview.css';
import { useNavigate } from 'react-router-dom';
import React, { useEffect, useRef, useState  } from 'react';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import RotatingContainers from './RotatingContainers';
import axios from 'axios';

function RouteReview() {
  const navigate = useNavigate();
  const mapRef = useRef(null);
  const [review, setReview] = useState('');

  const handleSubmit5 = () => {
      if (review === '') {
        alert('Please fill in the input fields.');
      } else {
        axios
          .post('http://localhost:9000/reviews', { review })
          .then(() => {
            console.log('Review added successfully');
            setReview('');
          })
          .catch((error) => {
            console.error('Error adding review:', error);
          });
      }
    };

  useEffect(() => {
    if (mapRef.current) {
      const map = L.map(mapRef.current).setView([42.359750, -72.536280], 13);

      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors',
      }).addTo(map);

      // Add any additional map functionality or markers as needed
    }
  }, []);

  const handleSubmit = () => {
    navigate(`/`);
  };

  return (
    <div className="App3">
      <header className="App-header">
        <div className="header">
          <button className="app-name" onClick={handleSubmit}>
            EleNA
          </button>
        </div>
      </header>
      <div className="container_1">
        <div className="left3">
        <div
          className="map-container-2 leaflet-container leaflet-touch leaflet-retina leaflet-fade-anim leaflet-grab leaflet-touch-drag leaflet-touch-zoom"
          tabIndex="0"
          style={{ position: 'relative', width: '600px', height: '480px' }}
          ref={mapRef}
        ></div></div>
        <div className="right3">

          <div className="routes2">
            <div className="transport1">
                        <button className="submit-button-2">
                          <img
                            src="https://w7.pngwing.com/pngs/305/951/png-transparent-computer-icons-nordic-walking-sport-people-icon-miscellaneous-angle-hand.png"
                            alt="Walk"
                            className="logo"
                          />
                        </button>
                        <button className="submit-button-2">
                          <img
                            src="https://w7.pngwing.com/pngs/941/1003/png-transparent-triathlon-cycling-computer-icons-sport-cycling-thumbnail.png"
                            alt="Cycle"
                            className="logo"
                          />
                        </button>
                        <button className="submit-button-2">
                          <img
                            src="https://w7.pngwing.com/pngs/208/784/png-transparent-car-drawing-car-outline-compact-car-car-cartoon-thumbnail.png"
                            alt="Car"
                            className="logo"
                          />
                        </button>
            </div>
            <div>
                <p>Distance : </p>
                <p>Time : </p>
                <p>Calories : </p>
                <p>Add Review </p>
                <input className="input-field-1" type="text" placeholder="Enter your review" value={review}
                                    onChange={(e) => setReview(e.target.value)}/>
                <button className="submit-button-3" onClick={handleSubmit5}>
                        Add review
                </button>
            </div>

          </div>
        </div>
      </div>
      <div className="container_2">
        <div className = "review_heading">
            <p>Reviews</p>
        </div>
        <div>
              <RotatingContainers />
            </div>
      </div>
    </div>
  );
}

export default RouteReview;
