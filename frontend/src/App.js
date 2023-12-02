import React from "react";
import { Routes, Route } from "react-router-dom";
import Login from "./LoginPage";
import BookingPage from "./BookingPage";
import FlightCrewPage from "./FlightCrew";
import PassengersFlight from "./PassengersFlight";
import FlightList from "./FlightList";
import CreateNewAccPage from "./CreateNewAcc";
import SeatSelectionPage from "./SeatSelectionPage";
import PaymentPage from "./PaymentPage";
import MyFlights from "./MyFlights";
import BrowsePassengers from "./BrowsePassengers";
import "./App.css";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Login />} />
      <Route path="/createnewacc" element={<CreateNewAccPage />} />
      <Route path="/booking" element={<BookingPage />} />
      <Route path="/flightcrew" element={<FlightCrewPage />} />

      <Route
        path="/passengersflight/:flightId"
        element={<PassengersFlight />}
      />
      <Route path="/flightlist/:destination" element={<FlightList />} />
      <Route
        path="/seatselectionpage/:destination/:flightId"
        element={<SeatSelectionPage />}
      />
      <Route
        path="/paymentpage/:flightId/:seatNumber"
        element={<PaymentPage />}
      />
      <Route path="/myflights" element={<MyFlights />} />
      <Route path="/BrowsePassengers" element={<BrowsePassengers />} />
    </Routes>
  );
}

export default App;
