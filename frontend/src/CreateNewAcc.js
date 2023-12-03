import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "bulma/css/bulma.min.css";

const CreateNewAccPage = () => {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [userType, setUserType] = useState("user");
  const navigate = useNavigate();

  const handleSignup = () => {
    if (username !== "" && password !== "") {
      const email = username;
      const signUpUser = { username, email, password, role: [userType] };

      fetch("http://localhost:8080/api/auth/signup", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(signUpUser),
      })
        .then((response) => response.json())
        .then((data) => {
          alert(data.message);
        })
        .catch((error) => {
          console.error("Error during signup:", error);
        });
    } else {
      alert("Please enter both username and password");
    }

    alert("Account created successfully!");
    navigate("/");
  };

  return (
    <div
      className="section has-background-primary"
      style={{ minHeight: "100vh" }}>
      <div className="container">
        <div className="columns is-centered">
          <div className="column is-half">
            <div className="card">
              <div className="card-content">
                <h2 className="title has-text-centered">Create Account</h2>
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
                  <label className="label">Email:</label>
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
                        <option value="agent">Flight Crew</option>
                        <option value="admin">Admin</option>
                      </select>
                    </div>
                  </div>
                </div>

                <div className="field">
                  <div className="control">
                    <button
                      type="button"
                      onClick={handleSignup}
                      className="button is-primary is-fullwidth">
                      Signup
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CreateNewAccPage;
