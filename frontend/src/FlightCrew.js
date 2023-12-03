import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "bulma/css/bulma.min.css";

const FlightCrewPage = () => {
  const [flights, setFlights] = useState([]);

  useEffect(() => {
    const fetchFlights = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/flights");
        if (response.ok) {
          const data = await response.json();
          setFlights(data);
        } else {
          console.error("Failed to fetch flights for flight crew");
        }
      } catch (error) {
        console.error("Error fetching flights for flight crew:", error);
      }
    };

    fetchFlights();
  }, []);

  return (
    <div className="section">
      <div className="container">
        <div className="has-text-centered">
          <h2 className="title is-4 has-text-info">
            Flights for Airline Staff
          </h2>
        </div>
        <div className="columns is-multiline is-centered">
          {flights.map((flight) => (
            <div className="column is-one-third" key={flight.id}>
              <Link to={`/passengersflight/${flight.id}`}>
                <div className="card has-background-light">
                  <div className="card-content">
                    <p className="title has-text-primary">
                      {flight.flightNumber}
                    </p>
                    <p className="subtitle">
                      {flight.departureDestination} to{" "}
                      {flight.arrivalDestination}
                    </p>
                    <p>
                      Departure:{" "}
                      {new Date(flight.departureTime).toLocaleString()}
                      <br />
                      Arrival: {new Date(flight.arrivalTime).toLocaleString()}
                    </p>
                    <p>Status: {flight.status}</p>
                  </div>
                </div>
              </Link>
            </div>
          ))}
        </div>
        <div className="has-text-centered mt-4">
          <Link to="/">
            <button className="button is-danger mr-4">Logout</button>
          </Link>

          <Link to="/booking">
            <button className="button is-primary">Back to Booking Page</button>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default FlightCrewPage;
