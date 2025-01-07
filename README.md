# EleNa - Elevation-based Navigation System

EleNa is a web application designed for runners, cyclists, and hikers to plan routes that balance distance and elevation. Whether you're looking for a leisurely stroll or an intense workout, EleNa gives you the power to choose the route that best suits your needs.

---

## Features

- **Route Optimization**: Provides 3+ real-time, accurate route options that prioritize either elevation gain or loss, tailored to user preferences.
- **Google API Integration**: Uses Google Maps API to deliver detailed and accurate elevation data.
- **Interactive User Interface**: Developed using React.js for a seamless and intuitive experience.
- **Customizable Preferences**: Optimize elevation by up to 20% to meet your fitness goals.

---

## Tech Stack

- **Frontend**: React.js
- **Backend**: Java, Node.js
- **APIs**: Google Maps API

---

## Installation and Setup

Follow these steps to run EleNa on your local machine:

1. Clone the repository:
   ```bash
   git clone https://github.com/Asritha2001/Elena
2. Move into the project directory:
   ```bash
   cd Elena
3. Configure API Keys:
  EleNa requires the Google Maps API Key for functionality.

  Create a .env file in the Elena-Frontend folder with the following content:
  ```bash
  GOOGLE_MAPS_API_KEY=your-google-maps-api-key-here
  ```
  Replace your-google-maps-api-key-here with your Google Maps API key.
  
---

## How to Run

1. To run the frontend:
Navigate to the frontend folder:
```bash
cd ELENA/Elena-Frontend
```
2. Install dependencies:
```bash
npm install
```
3. Start the development server:
```bash
npm start
```
Access the frontend at http://localhost:3000.

4. To run the backend:
Navigate to the backend folder:
```bash
cd ELENA/Elena-Backend/src/main/java/com/umass/elena
```
5. Start the Spring Boot server:
```bash
mvn spring-boot:run
```
  The backend server will be available at http://localhost:8080.

---

## How It Works

1. **Enter Your Starting Point and Destination**:
   - Simply input the start and end locations for your route.

2. **Customize Your Preferences**:
   - Select whether you want to optimize for elevation gain, loss, or a balance.

3. **Explore the Options**:
   - View multiple real-time route suggestions with detailed distance and elevation profiles.

4. **Choose Your Adventure**:
   - Pick the route that best suits your preferences and start your journey!

---

## Application Output

![Homepage](https://github.com/Asritha2001/Elena/blob/main/screenshots/Homepage.png)

![Homepage](https://github.com/Asritha2001/Elena/blob/main/screenshots/Routes1.png)

![Homepage](https://github.com/Asritha2001/Elena/blob/main/screenshots/Routes2.png)

![Homepage](https://github.com/Asritha2001/Elena/blob/main/screenshots/Review1.png)

![Homepage](https://github.com/Asritha2001/Elena/blob/main/screenshots/Review2.png)

---

## Contributors
This project was developed by a team of four, where I actively contributed to the development of both the frontend and backend components.
