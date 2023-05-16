import './Home.css';
import React, { useState } from 'react';
import { useNavigate,Link } from 'react-router-dom';

export const Home = ({ children }) => {

      const navigate = useNavigate();

      const [source, setSource] = useState('');
      const [destination, setDestination] = useState('');
      const [percentage, setPercentage] = useState('');

      const handleSubmit = () => {
        if (source === '' || destination === '' || percentage === '') {
          alert('Please fill in all input fields.');
        } else {

          sendDataToAnotherFile(source);
          navigate(`/possibleroutes/${source}/${destination}`);
          console.log('Source:', source);
          console.log('Destination:', destination);
          console.log('Percentage:', percentage);

          setSource('');
          setDestination('');
          setPercentage('');
        }

      };

      <Link to={`/possibleroutes/${source}`}>Possible Routes</Link>

      const sendDataToAnotherFile = (data) => {
          // Do something with the data in another JavaScript file
          // Example: import and call a function from another file
          // AnotherFile.processData(data);
          return source;
        };


  return (
    <div className="Home">
      <header className="App-header">
        <div className="header">
            <div className="app-name">EleNA</div>
        </div>
      </header>
      <div className="container">
         <div className="left">
            <div className="container1">
                <div className="title">
                    <p>Elevation-based <br /> Navigation System</p>
                </div>
                <div className="description">
                    <p>Navigating system that can show possible routes that are shortest and also consider elevation gain.</p>
                </div>
            </div>

         </div>
         <div className="right">
            <div className="input-form">
                  <input
                    className="input-field"
                    type="text"
                    placeholder="Enter your source"
                    value={source}
                    onChange={(e) => setSource(e.target.value)}
                  />
                  <input
                    className="input-field"
                    type="text"
                    placeholder="Enter your destination"
                    value={destination}
                    onChange={(e) => setDestination(e.target.value)}
                  />
                  <input
                    className="input-field"
                    type="text"
                    placeholder="Enter value of x%"
                    value={percentage}
                    onChange={(e) => setPercentage(e.target.value)}
                  />
                    <button className="submit-button" onClick={handleSubmit}>
                        Search possible routes
                    </button>
                </div>
         </div>
      </div>
    </div>
  );
}

export default Home;
