import React from "react";
import "./SeatComponent.css"; // Import your CSS file for additional styling

const SeatComponent = ({ seat, selectedSeat, confirmedSeat, onSeatClick }) => {
  const isBooked = seat.booked;
  const isSelected = selectedSeat === seat.seatNumber;
  const isConfirmed = confirmedSeat === seat.seatNumber;

  const getCardClassName = () => {
    let className = "card seat-card";
    if (isSelected) {
      className += " selected";
    } else if (isBooked) {
      className += " booked";
    }
    return className;
  };

  return (
    <div className="column is-2" key={seat.seatId}>
      <div
        className={getCardClassName()}
        onClick={() => onSeatClick(seat.seatNumber)}>
        <div className="card-content">
          <p className="title">{seat.seatNumber}</p>
          <p className="subtitle">{seat.seatClass}</p>
          <p className="subtitle">Price: ${seat.price.toFixed(2)}</p>
          <p className={`subtitle ${isBooked ? "booked-text" : ""}`}>
            {isBooked ? "Booked" : "Available"}
          </p>
        </div>
      </div>
    </div>
  );
};

export default SeatComponent;
