import React from "react";
import "./OrderList.css";

const OrderList = ({ item, selected, quantity, onQuantityChange, onClick }) => {
  const handleQuantityChange = (action, e) => {
    e.stopPropagation();
    onQuantityChange(item.productId, action);
  };

  return (
    <div
      className={`order-item-container ${selected ? "isSeleceted" : ""}`}
      onClick={() => onClick(item.productId)}>
      <div className='order-item-wrap'>
        <img
          className='order-item-img'
          src={item.imageUrl}
          alt={item.name}
          width={60}
          height={60}
        />
        <ul className='order-item-info'>
          <li className='type'>
            <span>{item.type}</span>
          </li>
          <li className='name'>
            <span>{item.name}</span>
          </li>
        </ul>
        <span className='order-price'>
          <i>₩ {new Intl.NumberFormat().format(item.price)}</i>
        </span>
        <div className={`quantity-container ${selected ? "show" : ""}`}>
          <button
            className='quantity-button'
            type='button'
            onClick={(e) => handleQuantityChange("increase", e)}>
            ^
          </button>
          <span>{quantity}</span>
          <button
            className='quantity-button'
            type='button'
            onClick={(e) => handleQuantityChange("decrease", e)}>
            ^
          </button>
        </div>
      </div>
    </div>
  );
};

export default OrderList;
