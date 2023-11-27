import React from "react";
import { Link } from "react-router-dom";
import "bulma/css/bulma.min.css";

// Dummy flight data
const flights = [
  {
    id: 1,
    airline: "Airline 1",
    origin: "Origin 1",
    destination: "Destination 1",
    departureTime: "10:00 AM",
    arrivalTime: "12:00 PM",
    price: "$100",
  },
  {
    id: 2,
    airline: "Airline 2",
    origin: "Origin 2",
    destination: "Destination 2",
    departureTime: "11:00 AM",
    arrivalTime: "1:00 PM",
    price: "$150",
  },
  {
    id: 2,
    airline: "Airline 2",
    origin: "Origin 2",
    destination: "Destination 2",
    departureTime: "11:00 AM",
    arrivalTime: "1:00 PM",
    price: "$150",
  },
  {
    id: 2,
    airline: "Airline 2",
    origin: "Origin 2",
    destination: "Destination 2",
    departureTime: "11:00 AM",
    arrivalTime: "1:00 PM",
    price: "$150",
  },
  {
    id: 2,
    airline: "Airline 2",
    origin: "Origin 2",
    destination: "Destination 2",
    departureTime: "11:00 AM",
    arrivalTime: "1:00 PM",
    price: "$150",
  },
  {
    id: 2,
    airline: "Airline 2",
    origin: "Origin 2",
    destination: "Destination 2",
    departureTime: "11:00 AM",
    arrivalTime: "1:00 PM",
    price: "$150",
  },
  {
    id: 2,
    airline: "Airline 2",
    origin: "Origin 2",
    destination: "Destination 2",
    departureTime: "11:00 AM",
    arrivalTime: "1:00 PM",
    price: "$150",
  },
  // Add more flight objects as needed
];

// Render flight cards
const FlightList = () => {
  return (
    <div className="section">
      <div className="container">
        <div className="columns is-multiline is-centered">
          {flights.map((flight) => (
            <div className="column is-one-third" key={flight.id}>
              <Link to={`/seatselectionpage`}>
                <div className="card">
                  <div className="card-content">
                    <p className="title">{flight.airline}</p>
                    <p className="subtitle">
                      {flight.origin} to {flight.destination}
                    </p>
                    <p>
                      Departure: {flight.departureTime} - Arrival:{" "}
                      {flight.arrivalTime}
                    </p>
                    <p>Price: {flight.price}</p>
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

export default FlightList;
