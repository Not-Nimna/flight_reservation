import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const PaymentPage = () => {
  const navigate = useNavigate();
  const [creditCardInfo, setCreditCardInfo] = useState({
    cardNumber: "",
    cardHolderName: "",
    expirationDate: "",
    cvv: "",
  });

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

    // Validation checks
    switch (name) {
      case "cardNumber":
        const cleanValue = value.replace(/-/g, "").replace(/\D/g, "");

        // Format the value with hyphens every four digits
        const formattedValue = cleanValue
          .match(/.{1,4}/g)
          ?.join("-")
          .slice(0, 19); // Limit to 19 characters (16 digits + 3 hyphens)

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

  const handleSubmit = (e) => {
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
      console.log("Credit Card Info:", creditCardInfo);
      // You might want to send this data to a server or API for further processing
    } else {
      setErrors(newErrors);
    }
  };

  const handlePayment = () => {
    alert("Payment successful!");
    navigate("/booking");
  };

  const handleGoBack = () => {
    navigate("/seatselectionpage");
  };

  return (
    <div className="container has-text-centered">
      <section className="section">
        <div className="box">
          <h2 className="title">Payment Page</h2>
          <form onSubmit={handleSubmit}>
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
                <p className="help is-danger">{errors.cardNumber}</p>
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
                <p className="help is-danger">{errors.cardHolderName}</p>
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
                    <p className="help is-danger">{errors.expirationDate}</p>
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
              {errors.cvv && <p className="help is-danger ">{errors.cvv}</p>}
            </div>
            <div className="field">
              <div className="control">
                <button
                  className="button is-primary mx-4"
                  type="submit"
                  onClick={handlePayment}
                >
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
