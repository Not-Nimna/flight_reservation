import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "bulma/css/bulma.min.css";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loggedIn, setLoggedIn] = useState(false);
  const navigate = useNavigate();

  const handleLogin = () => {
    if (username !== "" && password !== "") {
      setLoggedIn(true);
      navigate("/BrowsePassengers");
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
                  <h2 className="title">Login</h2>
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
                      <div className="control">
                        <button
                          type="button"
                          onClick={handleLogin}
                          className="button is-primary"
                        >
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
