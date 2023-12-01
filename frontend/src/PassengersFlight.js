// BookingPage.js
import React from "react";
import { Link } from "react-router-dom";
import "bulma/css/bulma.min.css";
import BookingList from "./BookingList"; // Replace with the actual path to BookingList

const PassengersFlight = () => {
  return (
    <div className="section">
      <div className="container">
        <div className="field mt-4">
          <div className="control has-text-centered">
            <Link to="/flightcrew">
              <button className="button is-success">Back</button>
            </Link>
          </div>
        </div>
        <BookingList />
      </div>
    </div>
  );
};

export default PassengersFlight;
