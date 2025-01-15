import React from "react";
import "./Modal.css";

const Modal = ({ isOpen, title, contents, onClose, external, type }) => {
  if (!isOpen) return null;

  const handleOverlayClick = () => {
    if (external) {
      onClose();
    }
  };

  return (
    <div className='modal-overlay' onClick={handleOverlayClick}>
      <div className={`modal ${type && type}`}>
        <div className='modal-header'>
          <h3 className='modal-info'>{title}</h3>
          <button className='close-btn' onClick={onClose}>
            <span className='close-icon'>X</span>
          </button>
        </div>
        <div className='modal-container'>
          <p>{contents}</p>
        </div>
      </div>
    </div>
  );
};

export default Modal;
