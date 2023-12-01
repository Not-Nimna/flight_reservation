import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "bulma/css/bulma.min.css";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [userType, setUserType] = useState("user"); // Default to "user"
  const [loggedIn, setLoggedIn] = useState(false);
  const navigate = useNavigate();

  const handleLogin = () => {
    if (username !== "" && password !== "") {
      setLoggedIn(true);
      const email = username;
      const isCurrent = true;
      const loginUser = { email, password, userType, isCurrent };

      localStorage.setItem("customer", JSON.stringify(loginUser));

      const url = userType === "flightcrew" ? "/flightcrew" : "/booking";

      fetch("http://localhost:8080/api/users", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(loginUser),
      }).then((json) => {
        navigate(url);
        console.log(json);
      });
    } else {
      alert("Please enter both username and password");
    }
  };

  return (
    <div className="section">
      <div className="container">
        <div className="columns is-centered">
          <div className="column is-half">
            <div className="box">
              {loggedIn ? (
                <div>
                  <h2 className="title">Welcome, {username}!</h2>
                </div>
              ) : (
                <div>
                  <h2 className="title">Create Account</h2>
                  <form>
                    <div className="field">
                      <label className="label">Username:</label>
                      <div className="control">
                        <input
                          type="text"
                          value={username}
                          onChange={(e) => setUsername(e.target.value)}
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
