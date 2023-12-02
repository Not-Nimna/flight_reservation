import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "bulma/css/bulma.min.css";

const BookingPage = () => {
  const [bookingData, setBookingData] = useState({
    destination: "",
  });
  const [destinationOptions, setDestinationOptions] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    // Fetch the list of destinations from your API endpoint
    fetch("http://localhost:8080/api/public/cities")
      .then((response) => response.json())
      .then((data) => setDestinationOptions(data))
      .catch((error) => console.error("Error fetching destinations:", error));
  }, []); // Empty dependency array ensures the effect runs once on component mount

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setBookingData({ ...bookingData, [name]: value });
  };

  const handleBack = () => {
    navigate("/");
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    localStorage.setItem("destination", bookingData.destination);
    navigate(`/flightlist/${bookingData.destination}`);
    console.log(bookingData);
  };

  const handleViewMyFlights = () => {
    navigate("/myflights");
  };

  return (
    <div
      className="section has-background-primary"
      style={{ minHeight: "100vh" }}>
      <div className="container">
        <div className="card">
          <div className="card-content has-text-centered">
            <div className="level">
              <div className="level-left">
                <h1 className="title">Flight Booking System</h1>
              </div>
              <div className="level-right">
                <button
                  onClick={handleViewMyFlights}
                  className="button is-info">
                  View My Flights
                </button>
              </div>
            </div>
            <form onSubmit={handleSubmit}>
              <div className="field">
                <label className="label">Where would you like to fly to</label>
                <div className="control">
                  <div className="select">
                    <select
                      name="destination"
                      value={bookingData.destination}
                      onChange={handleInputChange}>
                      <option value="" disabled>
                        Select a destination
                      </option>
                      {destinationOptions.map((option) => (
                        <option key={option} value={option}>
                          {option}
                        </option>
                      ))}
                    </select>
                  </div>
                </div>
              </div>
              <div className="field">
                <div className="control" style={{ alignItems: "center" }}>
                  <button
                    onClick={handleSubmit}
                    type="submit"
                    className="button is-primary"
                    style={{ marginTop: "10px" }}>
                    Book Flight
                  </button>
                </div>
              </div>
            </form>
            <button
              onClick={handleBack}
              className="button is-danger"
              style={{ marginTop: "10px" }}>
              Log out
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default BookingPage;
