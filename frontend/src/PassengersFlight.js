// PassengersFlight.js
import React from "react";
import { Link } from "react-router-dom";
import "bulma/css/bulma.min.css";
import BookingList from "./BookingList";
import { useParams } from "react-router-dom";

const PassengersFlight = () => {
  // Get the flightId from the URL

  // Replace '123' with the actual flightId
  const { flightId } = useParams();

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
        <BookingList flightId={flightId} />
      </div>
    </div>
  );
};

export default PassengersFlight;
