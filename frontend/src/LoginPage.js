import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "bulma/css/bulma.min.css";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [userType, setUserType] = useState("user"); // Default to "user"
  const [loggedIn, setLoggedIn] = useState(false);
  const navigate = useNavigate();

  const handleLogin = () => {
    if (email !== "" && password !== "") {
      const loginCredentials = { username: email, password, userType };

      fetch("http://localhost:8080/api/auth/signin", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(loginCredentials),
      })
        .then((response) => response.json())
        .then((data) => {
          // Assuming your server returns the JWT cookie in the response headers
          const jwtCookie = data.headers.get("Set-Cookie");
          document.cookie = jwtCookie;
          setLoggedIn(true);
          navigate("/booking");
        })
        .catch((error) => {
          console.error("Error during login:", error);
        });
    } else {
      alert("Please enter both email and password");
    }
  };

  const handleSignup = () => {
    navigate("/createnewacc");
  };

  return (
    <div
      className="section has-background-primary"
      style={{ minHeight: "100vh" }}>
      <div className="container">
        <div className="columns is-centered">
          <div className="column is-half">
            <div className="box">
              {loggedIn ? (
                navigate("/booking")
              ) : (
                <div>
                  <h2 className="title">Login</h2>
                  <form>
                    <div className="field">
                      <label className="label">username:</label>
                      <div className="control">
                        <input
                          type="email"
                          value={email}
                          onChange={(e) => setEmail(e.target.value)}
                          className="input"
                        />
                      </div>
                    </div>
                    <div className="field">
                      <label className="label">Password:</label>
                      <div className="control">
                        <input
                          type="password"
                          value={password}
                          onChange={(e) => setPassword(e.target.value)}
                          className="input"
                        />
                      </div>
                    </div>
                    <div className="field">
                      <label className="label">User Type:</label>
                      <div className="control">
                        <div className="select">
                          <select
                            value={userType}
                            onChange={(e) => setUserType(e.target.value)}>
                            <option value="user">User</option>
                            <option value="flightcrew">Flight Crew</option>
                            <option value="admin">Admin</option>
                          </select>
                        </div>
                      </div>
                    </div>
                    <div className="field">
                      <div className="control">
                        <button
                          type="button"
                          onClick={handleLogin}
                          className="button is-primary">
                          Login
                        </button>
                      </div>
                    </div>
                    <div className="field">
                      <h2 className="subtitle">or</h2>
                    </div>
                    <div className="field">
                      <div className="control">
                        <button
                          type="button"
                          onClick={handleSignup}
                          className="button is-primary">
                          Create New Account
                        </button>
                      </div>
                    </div>
                  </form>
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
