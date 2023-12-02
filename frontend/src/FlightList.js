import React, { useState, useEffect } from "react";
import { Link, useParams } from "react-router-dom";
import "bulma/css/bulma.min.css";

const FlightList = () => {
  const [flights, setFlights] = useState([]);
  const { destination } = useParams();

  useEffect(() => {
    const fetchFlights = async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/api/public/search/${destination}`
        );
        if (response.ok) {
          const data = await response.json();
          setFlights(data);
        } else {
          console.error("Failed to fetch flights");
        }
      } catch (error) {
        console.error("Error fetching flights:", error);
      }
    };

    fetchFlights();
  }, [destination]);

  return (
    <div
      className="section has-background-primary"
      style={{ minHeight: "100vh" }}>
      <div className="container">
        <div className="has-text-centered">
          <h2 className="subtitle is-4">Flights to: {destination}</h2>
        </div>
        <div className="columns is-multiline is-centered">
          {flights.map((flight) => (
            <div className="column is-one-third" key={flight.id}>
              <Link to={`/seatselectionpage/${destination}/${flight.id}`}>
                <div className="card">
                  <div className="card-content">
                    <p className="title">{flight.flightNumber}</p>
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
            <button className="button is-warning">Back to Booking Page</button>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default FlightList;
