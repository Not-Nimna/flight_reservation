import React, { useState, useEffect } from "react";
import axios from "axios"; // Import Axios if you prefer using it for HTTP requests

const SeatComponent = () => {
  const [seats, setSeats] = useState([]);

  useEffect(() => {
    // Fetch seats for a specific aircraft (replace 123 with the actual aircraft ID)
    axios
      .get("http://localhost:8080/api/seats/aircraft/2")
      .then((response) => setSeats(response.data))
      .catch((error) => console.error("Error fetching seats:", error));
  }, []);

  return (
    <div>
      <h3 className="title is-4">Seats for the Selected Aircraft</h3>
      <ul>
        {seats.map((seat) => (
          <li key={seat.id}>
            {seat.seatNumber} - {seat.seatClass}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default SeatComponent;
