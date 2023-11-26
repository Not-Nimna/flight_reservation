import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "bulma/css/bulma.min.css";

const BookingPage = () => {
  const [bookingData, setBookingData] = useState({
    origin: "",
    destination: "",
    departureDate: "",
    returnDate: "",
  });

  const navigate = useNavigate();

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setBookingData({ ...bookingData, [name]: value });
  };

  const handleBack = () => {
    navigate("/");
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    // Perform any necessary actions with the bookingData object
    console.log(bookingData);
  };

  return (
    <div className="section">
      <div className="container">
        <div className="card">
          <div className="card-content">
            <h1 className="title">Flight Booking System</h1>
            <form onSubmit={handleSubmit}>
              <div className="field">
                <label className="label">Origin:</label>
                <div className="control">
                  <input
                    type="text"
                    name="origin"
                    value={bookingData.origin}
                    onChange={handleInputChange}
                    className="input"
                  />
                </div>
              </div>
              <div className="field">
                <label className="label">Destination:</label>
                <div className="control">
                  <input
                    type="text"
                    name="destination"
                    value={bookingData.destination}
                    onChange={handleInputChange}
                    className="input"
                  />
                </div>
              </div>
              <div className="field">
                <label className="label">Departure Date:</label>
                <div className="control">
                  <input
                    type="date"
                    name="departureDate"
                    value={bookingData.departureDate}
                    onChange={handleInputChange}
                    className="input"
                  />
                </div>
              </div>
              <div className="field">
                <label className="label">Return Date:</label>
                <div className="control">
                  <input
                    type="date"
                    name="returnDate"
                    value={bookingData.returnDate}
                    onChange={handleInputChange}
                    className="input"
                  />
                </div>
              </div>
              <div className="field">
                <div className="control" style={{ alignItems: "center" }}>
                  <button
                    type="submit"
                    className="button is-primary"
                    style={{ marginTop: "10px" }}
                  >
                    Book Flight
                  </button>
                </div>
              </div>
            </form>
            <button
              onClick={handleBack}
              className="button is-primary"
              style={{ marginTop: "10px" }}
            >
              Log out
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default BookingPage;
