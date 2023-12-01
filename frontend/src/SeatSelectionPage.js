// SeatSelectionPage.js
import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import "./SeatSelectionPage.css"; // Import your CSS file for additional styling
import SeatComponent from "./SeatComponent"; // Replace with the actual path to SeatComponent

const SeatSelectionPage = () => {
  const [seats, setSeats] = useState([]);
  const [selectedSeat, setSelectedSeat] = useState("");
  const [confirmedSeat, setConfirmedSeat] = useState("");
  const navigate = useNavigate();
  const { flightId } = useParams();

  useEffect(() => {
    const fetchSeats = async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/api/flights/${flightId}/seatMap`
        );
        if (response.ok) {
          const data = await response.json();
          setSeats(data);
        } else {
          console.error("Failed to fetch seats");
        }
      } catch (error) {
        console.error("Error fetching seats:", error);
      }
    };
    fetchSeats();
  }, [flightId]);

  const handleSeatChange = (seatNumber) => {
    setSelectedSeat(seatNumber);
  };

  const handleBack = () => {
    navigate(-1); // Navigate back one step in the history
  };

  const handleConfirmation = (event) => {
    event.preventDefault();
    setConfirmedSeat(selectedSeat);
    const selectedSeatObject = seats.find(
      (seat) => seat.seatNumber === selectedSeat
    );

    if (selectedSeatObject) {
      setConfirmedSeat(selectedSeatObject);
      localStorage.setItem("seatId", selectedSeatObject.seatId);
      console.log("Selected Seat ID:", selectedSeatObject.seatId);
    } else {
      console.error("Selected seat not found in the seat data.");
    }
  };

  const handlePayment = (event) => {
    event.preventDefault();
    navigate(`/paymentpage/${flightId}/${confirmedSeat}`);
  };

  const renderSeats = () => {
    const seatsByRow = {};

    // Group seats by row
    seats.forEach((seat) => {
      if (!seatsByRow[seat.seatRow]) {
        seatsByRow[seat.seatRow] = [];
      }
      seatsByRow[seat.seatRow].push(seat);
    });

    // Sort each row by column
    Object.keys(seatsByRow).forEach((row) => {
      seatsByRow[row].sort((a, b) => a.seatColumn.localeCompare(b.seatColumn));
    });

    return Object.keys(seatsByRow).map((row) => (
      <div key={row} className="columns is-multiline">
        {seatsByRow[row].map((seat) => (
          <SeatComponent
            key={seat.seatId}
            seat={seat}
            selectedSeat={selectedSeat}
            confirmedSeat={confirmedSeat}
            onSeatClick={() => handleSeatChange(seat.seatNumber)}
          />
        ))}
      </div>
    ));
  };

  return (
    <div className="container">
      <section
        className="section has-background-primary"
        style={{ minHeight: "100vh" }}>
        <div className="container">
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
                  onChange={() => {}}
                />
              </div>
            </div>
            <div className="field">
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
              <div className="notification is-warning m-4 has-text-centered">
                Seat {confirmedSeat.seatNumber} confirmed!
              </div>
            )}
            {confirmedSeat && (
              <div className="has-text-centered">
                <button
                  className="button is-danger mt-3"
                  onClick={handlePayment}>
                  Proceed to Payment
                </button>
              </div>
            )}
          </div>

          <h2 className="title">Seat Selection Page</h2>

          <div className="columns is-multiline">{renderSeats()}</div>
        </div>
      </section>
    </div>
  );
};

export default SeatSelectionPage;
