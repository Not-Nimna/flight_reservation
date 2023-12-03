// BookingList.js
import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

const BookingList = () => {
  const [bookings, setBookings] = useState([]);
  const { flightId } = useParams();

  useEffect(() => {
    const fetchBookings = async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/api/flights/${flightId}/bookings`,
          {
            method: "GET",
            credentials: "include", // Include cookies
            headers: {
              "Content-Type": "application/json",
            },
          }
        );

        if (response.ok) {
          const data = await response.json();
          setBookings(data);
        } else {
          console.error("Failed to fetch bookings");
        }
      } catch (error) {
        console.error("Error fetching bookings:", error);
      }
    };

    fetchBookings();
  }, [flightId]);

  return (
    <div className="section has-background-success">
      <div className="container">
        <h1 className="title has-text-white">
          Booking List for Airline {flightId}
        </h1>
        {bookings.map((booking) => (
          <div key={booking.id} className="card mt-4">
            <div className="card-content">
              <p className="title">ID: {booking.id}</p>
              <p className="subtitle">
                Cancellation Code: {booking.cancellationCode}
              </p>
              <p>Price Paid: {booking.pricePaid}</p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default BookingList;
