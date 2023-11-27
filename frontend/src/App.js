import React from "react";
import { Routes, Route } from "react-router-dom";
import Login from "./LoginPage";
import BookingPage from "./BookingPage";
import FlightList from "./FlightList";
import SeatSelectionPage from "./SeatSelectionPage";
import PaymentPage from "./PaymentPage";
import MyFlights from "./MyFlights";
import BrowsePassengers from "./BrowsePassengers";
import "./App.css";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Login />} />
      <Route path="/booking" element={<BookingPage />} />
      <Route path="/flightlist" element={<FlightList />} />
      <Route path="/seatselectionpage" element={<SeatSelectionPage />} />
      <Route path="/paymentpage" element={<PaymentPage />} />
      <Route path="/myflights" element={<MyFlights />} />
      <Route path="/BrowsePassengers" element={<BrowsePassengers />} />
    </Routes>
  );
}

export default App;
