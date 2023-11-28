import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "bulma/css/bulma.min.css";

const MyFlights = () => {
  const [student, setStudent] = useState([]);
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
    fetch("http://localhost:8080/student/getAll")
      .then((response) => response.json())
      .then((data) => {
        setStudent(data);

        // Replace the dummy flight data with the fetched student data
        setFlights(
          data.map((student) => ({
            id: student.id,
            airline: student.name,
            origin: student.address,
            destination: "", // Set appropriate value or leave it empty based on your requirements
            departureTime: "", // Set appropriate value or leave it empty based on your requirements
          }))
        );
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
