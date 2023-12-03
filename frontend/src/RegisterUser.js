import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "bulma/css/bulma.min.css";

const RegisterUser = () => {
  const [email, setEmail] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [promotions, setPromotions] = useState([]);
  const [showForm, setShowForm] = useState(true);

  useEffect(() => {
    if (email && phoneNumber) {
      // Fetch promotions from the API
      fetch("http://localhost:8080/api/public/promos")
        .then((response) => response.json())
        .then((data) => setPromotions(data))
        .catch((error) => console.error("Error fetching promotions:", error));
    }
  }, [email, phoneNumber]);

  const handleEmailChange = (event) => {
    setEmail(event.target.value);
  };

  const handlePhoneNumberChange = (event) => {
    setPhoneNumber(event.target.value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    setShowForm(false); // Hide the input form
    // Perform registration logic here
    console.log(
      "User registered with email:",
      email,
      "and phone number:",
      phoneNumber
    );
  };

  return (
    <div
      className="section has-background-primary has-text-white"
      style={{ minHeight: "100vh" }}>
      <div className="container">
        <div className="card">
          <div className="card-content">
            <div className="level">
              <div className="level-left">
                <Link to="/booking" className="button is-primary">
                  Back to booking
                </Link>
              </div>
              <div className="level-right">
                <h1 className="title has-text-centered">Register User</h1>
              </div>
            </div>
            <div className="field"></div>
            {showForm && (
              <form onSubmit={handleSubmit}>
                <div className="field">
                  <label className="label">Email Address</label>
                  <div className="control">
                    <input
                      type="email"
                      className="input"
                      placeholder="Enter your email"
                      value={email}
                      onChange={handleEmailChange}
                      required
                    />
                  </div>
                </div>
                <div className="field">
                  <label className="label">Phone Number</label>
                  <div className="control">
                    <input
                      type="tel"
                      className="input"
                      placeholder="Enter your phone number"
                      value={phoneNumber}
                      onChange={handlePhoneNumberChange}
                      required
                    />
                  </div>
                </div>
                <div className="field">
                  <div className="control">
                    <button type="submit" className="button is-primary">
                      Register
                    </button>
                  </div>
                </div>
              </form>
            )}
            {!showForm && (
              <div>
                <h2 className="subtitle">Promotions:</h2>
                <ul>
                  {promotions.map((promo) => (
                    <li key={promo.id}>{promo.promoDescription}</li>
                  ))}
                </ul>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default RegisterUser;
