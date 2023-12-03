import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useParams } from "react-router-dom";

const PaymentPage = () => {
  const { flightId, seatNumber } = useParams();

  const navigate = useNavigate();
  const [customer, setCustomer] = useState({
    name: "",
    email: "",
  });
  const storedSeatId = localStorage.getItem("seatId");
  console.log("seatNumber", storedSeatId);
  console.log("flightId", flightId);
  console.log("customer", customer);

  const [creditCardInfo, setCreditCardInfo] = useState({
    cardNumber: "",
    cardHolderName: "",
    expirationDate: "",
    cvv: "",
  });
  console.log("creditCardInfo", creditCardInfo);

  const [errors, setErrors] = useState({
    cardNumber: "",
    cardHolderName: "",
    expirationDate: "",
    cvv: "",
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setCreditCardInfo((prevInfo) => ({
      ...prevInfo,
      [name]: value,
    }));

    setErrors((prevErrors) => ({
      ...prevErrors,
      [name]: "", // Clear error when user starts typing
    }));

    // setcustomer info using credit card info
    setCustomer((prevInfo) => ({
      ...prevInfo,
      [name]: value,
    }));

    // Validation checks
    switch (name) {
      case "cardNumber":
        const cleanValue = value.replace(/-/g, "").replace(/\D/g, "");
        const formattedValue = cleanValue
          .match(/.{1,4}/g)
          ?.join("-")
          .slice(0, 19);

        setCreditCardInfo((prevInfo) => ({
          ...prevInfo,
          cardNumber: formattedValue,
        }));

        if (!/^\d{16}$/.test(cleanValue)) {
          setErrors((prevErrors) => ({
            ...prevErrors,
            cardNumber: "Card number must be a 16-digit number",
          }));
        }
        break;

      case "cardHolderName":
        if (!/^[a-zA-Z ]+$/.test(value)) {
          setErrors((prevErrors) => ({
            ...prevErrors,
            cardHolderName: "Cardholder name must be a valid string",
          }));
        }
        break;
      case "expirationDate":
        if (!/^(0[1-9]|1[0-2])\/\d{4}$/.test(value)) {
          setErrors((prevErrors) => ({
            ...prevErrors,
            expirationDate: "Invalid expiration date. Use MM/YYYY format",
          }));
        }
        break;
      case "cvv":
        if (!/^\d{3}$/.test(value)) {
          setErrors((prevErrors) => ({
            ...prevErrors,
            cvv: "CVV must be a 3-digit number",
          }));
        }
        break;
      default:
        break;
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Basic validation
    const newErrors = {};
    if (!creditCardInfo.cardNumber) {
      newErrors.cardNumber = "Card number is required";
    }
    if (!creditCardInfo.cardHolderName) {
      newErrors.cardHolderName = "Cardholder name is required";
    }
    if (!creditCardInfo.expirationDate) {
      newErrors.expirationDate = "Expiration date is required";
    }
    if (!creditCardInfo.cvv) {
      newErrors.cvv = "CVV is required";
    }

    // Check if there are errors before submitting
    if (Object.keys(newErrors).length === 0) {
      try {
        // set customer info
        setCustomer((prevInfo) => ({
          ...prevInfo,
          name: creditCardInfo.cardHolderName,
        }));

        // Send payment data to the server

        const response = await fetch(
          `http://localhost:8080/api/public/flighs/${flightId}/bookings/${storedSeatId}`,
          {
            method: "POST",
            // include cookies in the request
            credentials: "include",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              customerDetails: {
                name: customer.name,
                email: customer.email,
              },
              paymentDetails: {
                cardNumber: creditCardInfo.cardNumber,
                cardExpiryMonth: creditCardInfo.expirationDate.split("/")[0], // Extract MM from MM/YYYY
                cardExpiryYear: creditCardInfo.expirationDate.split("/")[1], // Extract YYYY from MM/YYYY
                cardCvv: creditCardInfo.cvv,
              },
            }),
          }
        );

        if (!response.ok) {
          // Handle server error
          throw new Error("Server error during payment");
        }

        console.log("Payment successful!");
        navigate("/booking");
        // Reset form fields
        setCreditCardInfo({
          cardNumber: "",
          cardHolderName: "",
          expirationDate: "",
          cvv: "",
        });
        setErrors({
          cardNumber: "",
          cardHolderName: "",
          expirationDate: "",
          cvv: "",
        });
      } catch (error) {
        console.error("Error during payment:", error.message);
        // Handle the server error, show a message to the user, etc.
      }
    } else {
      setErrors(newErrors);
    }
  };

  const handleGoBack = () => {
    navigate(-1);
  };

  return (
    <div className="container has-text-centered">
      <section
        className="section has-background-primary"
        style={{ minHeight: "100vh" }}>
        <div className="box">
          <h2 className="title">Payment Page</h2>
          <form onSubmit={handleSubmit}>
            <div className="field">
              <label className="label">Email</label>
              <div className="control is-offset-2 column is-8">
                <input
                  className={`input ${errors.email ? "is-danger" : ""}`}
                  type="email"
                  name="email"
                  value={customer.email}
                  onChange={(e) => {
                    setCustomer((prevInfo) => ({
                      ...prevInfo,
                      email: e.target.value,
                    }));

                    handleInputChange(e);
                  }}
                  placeholder="Enter email address"
                />
              </div>
              {errors.email && (
                <p className="help is-danger has-text-danger-bold">
                  {errors.email}
                </p>
              )}
            </div>
            <div className="field">
              <label className="label">Card Number</label>
              <div className="control is-offset-4 column is-4 ">
                <input
                  className={`input ${
                    errors.cardNumber ? "is-danger" : ""
                  } has-text-centered`}
                  type="text"
                  name="cardNumber"
                  value={creditCardInfo.cardNumber}
                  onChange={handleInputChange}
                  placeholder="Enter card number"
                />
              </div>
              {errors.cardNumber && (
                <p className="help is-danger has-text-danger-bold">
                  {errors.cardNumber}
                </p>
              )}
            </div>
            <div className="field">
              <label className="label">Cardholder Name</label>
              <div className="control is-offset-2 column is-8">
                <input
                  className={`input ${
                    errors.cardHolderName ? "is-danger" : ""
                  }`}
                  type="text"
                  name="cardHolderName"
                  value={creditCardInfo.cardHolderName}
                  onChange={handleInputChange}
                  placeholder="Enter cardholder name"
                />
              </div>
              {errors.cardHolderName && (
                <p className="help is-danger has-text-danger-bold">
                  {errors.cardHolderName}
                </p>
              )}
            </div>
            <div className="field ">
              <div className="field-label has-text-centered">
                <label className="label ">Expiration Date</label>
              </div>
              <div className="field-body">
                <div className="field">
                  <div className="control is-offset-5 column is-2">
                    <input
                      className={`input ${
                        errors.expirationDate ? "is-danger" : ""
                      }`}
                      type="text"
                      name="expirationDate"
                      value={creditCardInfo.expirationDate}
                      onChange={handleInputChange}
                      placeholder="MM/YYYY"
                    />
                  </div>
                  {errors.expirationDate && (
                    <p className="help is-danger has-text-danger-bold">
                      {errors.expirationDate}
                    </p>
                  )}
                </div>
              </div>
            </div>

            <div className="field">
              <label className="label">CVV</label>
              <div className="control is-offset-5 column is-2">
                <input
                  className={`input ${errors.cvv ? "is-danger" : ""}`}
                  type="text"
                  name="cvv"
                  value={creditCardInfo.cvv}
                  onChange={handleInputChange}
                  placeholder="Enter CVV"
                />
              </div>
              {errors.cvv && (
                <p className="help is-danger has-text-danger-bold">
                  {errors.cvv}
                </p>
              )}
            </div>
            <div className="field">
              <div className="control">
                <button
                  className="button is-primary mx-4"
                  type="submit"
                  onClick={handleSubmit}>
                  Submit Payment
                </button>
                <button className="button is-danger" onClick={handleGoBack}>
                  cancel payment
                </button>
              </div>
            </div>
          </form>
        </div>
      </section>
    </div>
  );
};

export default PaymentPage;
