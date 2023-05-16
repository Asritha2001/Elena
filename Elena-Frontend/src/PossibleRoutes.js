import './PossibleRoutes.css';
import { useNavigate, useParams } from 'react-router-dom';
import React, { useEffect, useRef, useState  } from 'react';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';

function PossibleRoutes() {
  const { source, destination } = useParams();
  const [selectedAlgorithm, setSelectedAlgorithm] = useState('');
  const navigate = useNavigate();
  console.log('Source:', source);
  const mapRef = useRef(null);
  const percentage = 4;
  const url = `http://localhost:8081/google-maps/routes?source=${source}&destination=${destination}&x=${percentage}&mode=walking`;
  console.log(url);
  const [distances, setDistances] = useState([]);
  const [times, setTimes] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(url, { method: 'GET', mode: 'cors' });
        const data = await response.json();
        console.log(data);

        const newDistances = [];
        const newTimes = [];

        data.forEach(item => {
          const distance = item.legs[0]?.distance?.humanReadable;
          const time = item.legs[0]?.duration?.humanReadable;
          newDistances.push(distance);
          newTimes.push(time);
        });

        setDistances(prevDistances => [...prevDistances, ...newDistances]);
        setTimes(prevTimes => [...prevTimes, ...newTimes]);
        console.log(distances);
      } catch (error) {
        console.error(error);
      }
    };

    fetchData();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [url,setDistances]);

    const distancesLength = distances.length;
    console.log(distancesLength);

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

  const handleSubmit_2 = () => {
      navigate(`/routereview`);
    };

  const handleAlgorithmChange = (event) => {
      setSelectedAlgorithm(event.target.value);
    };

  return (
    <div className="App">
      <header className="App-header">
        <div className="header">
          <button className="app-name" onClick={handleSubmit}>
            EleNA
          </button>
        </div>
      </header>
      <div className="container">
        <div className="left2">
        <div
          className="map-container leaflet-container leaflet-touch leaflet-retina leaflet-fade-anim leaflet-grab leaflet-touch-drag leaflet-touch-zoom"
          tabIndex="0"
          style={{ position: 'relative', width: '855px', height: '830px' }}
          ref={mapRef}
        ></div></div>
        <div className="right2">
          <div className="location-data">
            <div className="data1">
              <p>Source</p>
              <div className="data-container">
                  <div className="data2">
                     <p>{source}</p>
                  </div>
              </div>
            </div>

            <div className="data1">
              <p>Destination</p>
              <div className="data-container">
                  <div className="data2">
                      <p>{destination}</p>
                   </div>
              </div>
            </div>
          </div>

          <div>
             <select value={selectedAlgorithm} onChange={handleAlgorithmChange} className="data-container2">
                 <option value="">Select Algorithm</option>
                 <option value="Algorithm_1">Algorithm 1</option>
                 <option value="Algorithm_2">Algorithm 2</option>
                 <option value="Algorithm_3">Algorithm 3</option>
             </select>
          </div>

          <div className="transport">
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

          <div className="routes" style={{ overflowY: 'auto', maxHeight: '450px' }}>
            <button className="routes1">
                <p>Possible Routes</p>
            </button>
            {distances.map((distance, index) => (
              <button className="route" key={index} onClick={handleSubmit_2}>
                <p>Distance: {distance}</p>
                <p>Elevation: {percentage}</p>
                <p>Time: {times[index]}</p>
              </button>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}

export default PossibleRoutes;
