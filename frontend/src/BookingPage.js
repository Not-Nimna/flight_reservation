import React, { useState, useEffect, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import "bulma/css/bulma.min.css";
import { UserContext } from './UserContext'; 


const BookingPage = ({}) => {

  const { isLoggedIn, setIsLoggedIn } = useContext(UserContext);

  const [bookingData, setBookingData] = useState({
    destination: "",
  });
  const [destinationOptions, setDestinationOptions] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    // Fetch the list of destinations from your API endpoint
    fetch("http://localhost:8080/api/public/cities")
      .then((response) => response.json())
      .then((data) => setDestinationOptions(data))
      .catch((error) => console.error("Error fetching destinations:", error));
  }, []); // Empty dependency array ensures the effect runs once on component mount

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setBookingData({ ...bookingData, [name]: value });
  };

  const handleLogIn = () => {
    setIsLoggedIn(true);
    navigate("/");
  }

  const handleLogOut = () => {
    setIsLoggedIn(false);
    signout();
    navigate("/");
  }

  const signout = () => {
    // Call the /signout endpoint to sign out the user
    fetch("http://localhost:8080/api/auth/signout", {
      method: "POST",
      credentials: "include",
    })
      .then((response) => {
        if (!response.ok) {
          // If the response status is not OK, log the response
          console.error("Error signing out. Status:", response.status);
          return Promise.reject("Sign-out failed");
        }
        return response.json(); // Parse the JSON data if available
      })
      .then((data) => {
        // Log the response from the server
        console.log(data);
        // Redirect to the home page
        navigate("/");
      })
      .catch((error) => {
        console.error("Error signing out:", error);
      });
    navigate("/");
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    localStorage.setItem("destination", bookingData.destination);
    navigate(`/flightlist/${bookingData.destination}`);
    console.log(bookingData);
  };

  const handleViewMyFlights = () => {
    navigate("/myflights");
  };

  return (
    <div
      className="section has-background-primary"
      style={{ minHeight: "100vh" }}>
      <div className="container">
        <div className="card">
          <div className="card-content has-text-centered">
            <div className="level">
              <div className="level-left">
                <Link to="/registerUser" className="button is-info">
                  Register
                </Link>
              </div>
              <div className="level-item">
                <h1 className="title">Flight Booking System</h1>
              </div>
              <div className="level-right">
                <button
                  onClick={handleViewMyFlights}
                  className="button is-info">
                  View My Flights
                </button>
              </div>
            </div>
            <form onSubmit={handleSubmit}>
              <div className="field">
                <label className="label">Where would you like to fly to</label>
                <div className="control">
                  <div className="select">
                    <select
                      name="destination"
                      value={bookingData.destination}
                      onChange={handleInputChange}>
                      <option value="" disabled>
                        Select a destination
                      </option>
                      {destinationOptions.map((option) => (
                        <option key={option} value={option}>
                          {option}
                        </option>
                      ))}
                    </select>
                  </div>
                </div>
              </div>
              <div className="field">
                <div className="control" style={{ alignItems: "center" }}>
                  <button
                    onClick={handleSubmit}
                    type="submit"
                    className="button is-primary"
                    style={{ marginTop: "10px" }}>
                    Book Flight
                  </button>
                </div>
              </div>
            </form>

            {isLoggedIn ? 
              <button onClick={handleLogOut} className="button is-danger" style={{ marginTop: "10px" }}>
                Log out
              </button>
              : 
              <button onClick={handleLogIn} className="button is-info" style={{ marginTop: "10px" }}>
                Log In
              </button>
            }
            
          </div>
        </div>
      </div>
    </div>
  );
};

export default BookingPage;
