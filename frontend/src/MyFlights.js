import React, { useState } from "react";
import { Link } from "react-router-dom";
import "bulma/css/bulma.min.css";

const MyFlights = () => {
  // Dummy flight data
  const [flights, setFlights] = useState([
    {
      id: 1,
      airline: "Airline 1",
      origin: "Origin 1",
      destination: "Destination 1",
      departureTime: "10:00 AM",
    },
    {
      id: 2,
      airline: "Airline 2",
      origin: "Origin 2",
      destination: "Destination 2",
      departureTime: "11:00 AM",
    },
    // Add more flight objects as needed
  ]);

  const handleCancel = (flightId) => {
    // Logic to cancel the flight (remove from the list)
    setFlights((prevFlights) =>
      prevFlights.filter((flight) => flight.id !== flightId)
    );
  };

  return (
    <div className="section">
      <div className="container">
        <h2 className="title has-text-centered">My Flights</h2>
        <div className="columns is-multiline is-centered">
          {flights.map((flight) => (
            <div key={flight.id} className="column is-one-third">
              <div className="card">
                <div className="card-content">
                  <p className="title">{flight.airline}</p>
                  <p className="subtitle">
                    {flight.origin} to {flight.destination}
                  </p>
                  <p>Departure Time: {flight.departureTime}</p>
                  <button
                    className="button is-danger"
                    onClick={() => handleCancel(flight.id)}
                  >
                    Cancel
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>
        <div className="has-text-centered">
          <Link to="/booking" className="button is-primary m-2">
            Book More Flights
          </Link>
          <Link to="/" className="button is-danger m-2">
            Logout
          </Link>
        </div>
      </div>
    </div>
  );
};

export default MyFlights;
