import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "bulma/css/bulma.min.css";

const MyFlights = () => {
  const [flights, setFlights] = useState([]);

  const handleCancel = (flightId) => {
    // Make a DELETE request to cancel the flight
    fetch(`http://localhost:8080/student/delete/${flightId}`, {
      method: "DELETE",
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data); // Log the response from the server

        // If the flight was deleted successfully, update the state
        if (data.startsWith("Student with ID")) {
          setFlights((prevFlights) =>
            prevFlights.filter((flight) => flight.id !== flightId)
          );
        }
      })
      .catch((error) => {
        console.error("Error canceling flight:", error);
      });
  };

  useEffect(() => {
    fetch("http://localhost:8080/api/flights")
      .then((response) => response.json())
      .then((data) => {
        // Assuming data is an array of flights
        setFlights(
          data.map((flight) => ({
            id: flight.id,
            airline: flight.aircraftCode, // You can adjust this based on your data structure
            origin: flight.departureDestination,
            destination: flight.arrivalDestination,
            departureTime: new Date(flight.departureTime).toLocaleString(), // Format the date as needed
          }))
        );
      })
      .catch((error) => {
        console.error("Error fetching flights:", error);
      });
  }, []);

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
                    onClick={() => handleCancel(flight.id)}>
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
