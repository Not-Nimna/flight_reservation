import React, { useState } from "react";
import { Link } from "react-router-dom";
import "bulma/css/bulma.min.css";

const BrowsePassengers = () => {
  // Dummy flight data with passengers
  const [flights] = useState([
    {
      id: 1,
      airline: "Airline 1",
      origin: "City A",
      destination: "City B",
      departureTime: "10:00 AM",
      arrivalTime: "12:00 PM",
      flightNumber: "FL123",
      passengers: ["Passenger A", "Passenger B", "Passenger C"],
    },
    {
      id: 2,
      airline: "Airline 2",
      origin: "City X",
      destination: "City Y",
      departureTime: "11:00 AM",
      arrivalTime: "1:00 PM",
      flightNumber: "FL456",
      passengers: ["Passenger X", "Passenger Y", "Passenger Z"],
    },
    // Add more flight objects as needed
  ]);

  const [selectedFlight, setSelectedFlight] = useState(null);

  const handleFlightSelection = (flightId) => {
    setSelectedFlight(flightId);
  };

  return (
    <div className="section">
      <div className="container">
        <h2 className="title has-text-centered">Browse Flights</h2>
        <div className="columns is-multiline is-centered">
          {flights.map((flight) => (
            <div key={flight.id} className="column is-one-third">
              <div
                className="card"
                onClick={() => handleFlightSelection(flight.id)}>
                <div className="card-content">
                  <p className="title">{flight.airline}</p>
                  <p className="subtitle">
                    Flight Number: {flight.flightNumber}
                  </p>
                  <p>
                    Origin: {flight.origin} - Destination: {flight.destination}
                  </p>
                  <p>
                    Departure Time: {flight.departureTime} - Arrival Time:{" "}
                    {flight.arrivalTime}
                  </p>
                </div>
              </div>
            </div>
          ))}
        </div>
        {selectedFlight !== null && (
          <div className="mt-4">
            <h3 className="title has-text-centered">
              Passengers for Flight {selectedFlight}
            </h3>
            <ul className="box">
              {flights[selectedFlight - 1].passengers.map(
                (passenger, index) => (
                  <li key={index}>{passenger}</li>
                )
              )}
            </ul>
          </div>
        )}
        <div className="has-text-centered mt-4">
          <Link to="/" className="button is-danger">
            Logout
          </Link>
        </div>
      </div>
    </div>
  );
};

export default BrowsePassengers;
