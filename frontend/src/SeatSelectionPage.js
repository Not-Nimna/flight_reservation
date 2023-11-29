import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./SeatSelectionPage.css"; // Import your CSS file for additional styling
import SeatComponent from "./SeatComponent"; // Replace with the actual path to SeatComponent

const SeatSelectionPage = () => {
  const [selectedSeat, setSelectedSeat] = useState("");
  const [confirmedSeat, setConfirmedSeat] = useState("");
  const navigate = useNavigate();

  const handleSeatChange = (event) => {
    event.preventDefault();
    setSelectedSeat(event.target.value);
  };

  const handleConfirmation = (event) => {
    event.preventDefault();
    setConfirmedSeat(selectedSeat);
  };
  const handlePayment = (event) => {
    event.preventDefault();
    navigate("/paymentpage");
  };

  return (
    <div className="container">
      <section className="section ">
        <div className="container ">
          <form>
            <div className="field">
              <label className="label has-text-centered">
                Enter Seat Number:
              </label>
              <div className="control is-centered">
                <input
                  className="input is-offset-5 column is-2"
                  type="text"
                  value={selectedSeat}
                  onChange={handleSeatChange}
                />
              </div>
            </div>
            <div className="field ">
              <div className="control has-text-centered">
                <button
                  className="button is-info"
                  onClick={handleConfirmation}
                  disabled={!selectedSeat}>
                  Confirm Selection
                </button>
              </div>
            </div>
          </form>
          <div>
            {confirmedSeat && (
              <div className="notification is-success m-4 has-text-centered">
                Seat {confirmedSeat} confirmed!
              </div>
            )}
            {confirmedSeat && (
              <div className="has-text-centered">
                <button
                  className="button is-warning mt-3"
                  onClick={handlePayment}>
                  Proceed to Payment
                </button>
              </div>
            )}
          </div>

          <h2 className="title">Seat Selection Page</h2>
          <div className="columns is-multiline">
            <SeatComponent />
          </div>
        </div>
      </section>
    </div>
  );
};

export default SeatSelectionPage;
