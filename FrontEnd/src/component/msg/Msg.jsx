import React from "react";
import "./Msg.css";

const Msg = ({ type, text }) => {
  switch (type) {
    case "error": {
      return <p className='error'>{text}</p>;
    }
    case "title": {
      return <h2 className='title'>{text}</h2>;
    }
    default:
      return <span>{text}</span>;
  }
};

export default Msg;
