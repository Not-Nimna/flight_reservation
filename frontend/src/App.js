import React from "react";
import { Routes, Route } from "react-router-dom";
import Login from "./LoginPage";
import BookingPage from "./BookingPage";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Login />} />
      <Route path="/booking" element={<BookingPage />} />
    </Routes>
  );
}

export default App;
