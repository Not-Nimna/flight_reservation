import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "bulma/css/bulma.min.css";

const MyFlights = () => {
  const [flights, setFlights] = useState([]);

  const handleCancel = (flightId) => {
    // Find the flight by id
    const flightToCancel = flights.find((flight) => flight.id === flightId);

    if (!flightToCancel) {
      console.error(`Flight with id ${flightId} not found.`);
      return;
    }

    // Make a DELETE request to cancel the flight
    fetch(
      `http://localhost:8080/api/public/bookings/${flightToCancel.cancellationCode}`,
      {
        method: "DELETE",
        credentials: "include",
      }
    )
      .then((response) => response.json())
      .then((data) => {
        alert(data.message);

        // If the cancellation was successful, update the state
        if (data.success) {
          setFlights((prevFlights) =>
            prevFlights.filter((flight) => flight.id !== flightId)
          );
        }
      })
      .catch((error) => {
        console.error("Error cancelling flight:", error);
      })
      //refresh the page
      .then(() => {
        alert('Cancellation successful')
        window.location.reload();
      });
    alert("Flight Cancelled");
  };

  useEffect(() => {
    fetch("http://localhost:8080/api/user/bookings", {
      credentials: "include", // Include credentials (cookies) with the request
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => response.json())
      .then((data) => {
        // Assuming data is an array of flights
        setFlights(
          data.map((flight) => ({
            id: flight.id,
            name: flight.name,
            email: flight.email,
            cancellationCode: flight.cancellationCode,
            pricePaid: flight.pricePaid,
          }))
        );
      })
      .catch((error) => {
        console.error("Error fetching flights:", error);
      });
  }, []);

  return (
    <div
      className="section has-background-primary "
      style={{ minHeight: "100vh" }}>
      <div className="container">
        <h2 className="title has-text-centered">My Flights</h2>
        <div className="columns is-multiline is-centered">
          {flights.map((flight) => (
            <div key={flight.id} className="column is-one-third">
              <div className="card">
                <div className="card-content">
                  <p className="title">{flight.name}</p>
                  <p className="subtitle">{flight.email}</p>
                  <p>Price Paid: {flight.pricePaid}</p>
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
          <Link to="/booking" className="button is-info m-2">
            Book More Flights
          </Link>
          <Link to="/" className="button is-danger m-2">
            GoBack
          </Link>
        </div>
      </div>
    </div>
  );
};

export default MyFlights;
