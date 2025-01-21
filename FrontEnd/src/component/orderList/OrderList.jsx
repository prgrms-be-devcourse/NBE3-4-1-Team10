import React from "react";
import "./OrderList.css";

const OrderList = ({
  item,
  selected,
  quantity,
  onQuantityChange,
  onCheckboxChange,
}) => {
  return (
    <div className='order-item-container'>
      <div className='order-item-wrap'>
        <img
          className='order-item-img'
          src={item.imageUrl}
          alt={item.name}
          width={60}
          height={60}
        />
        <span className='order-name'>{item.name}</span>
        <span className='order-price'>
          <i>₩ {new Intl.NumberFormat().format(item.price)}</i>
        </span>
      </div>

      {selected && (
        <div className='quantity-container'>
          <button
            type='button'
            onClick={() => onQuantityChange(item.id, "decrease")}
            className='quantity-button'>
            -
          </button>
          <span>{quantity}</span>
          <button
            type='button'
            onClick={() => onQuantityChange(item.id, "increase")}
            className='quantity-button'>
            +
          </button>
        </div>
      )}
    </div>
  );
};

export default OrderList;

//  <input
//    type='checkbox'
//    name='order'
//    value={item.name}
//    checked={selected}
//    onChange={() => onCheckboxChange(item.id)}
//  />;
