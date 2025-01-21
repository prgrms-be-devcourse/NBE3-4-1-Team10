import React, { useState } from "react";
import "./OrderList.css";

const OrderList = ({ data, isEditing, onUpdateProduct }) => {
  const [updatedProducts, setUpdatedProducts] = useState({});

  const handleInputChange = (e, orderProductId) => {
    const { name, value } = e.target;
    setUpdatedProducts((prev) => ({
      ...prev,
      [orderProductId]: {
        ...prev[orderProductId],
        [name]: value,
      },
    }));
  };

  const handleSaveClick = (orderProductId) => {
    onUpdateProduct(orderProductId, updatedProducts[orderProductId]);
  };

  if (!data || data.length === 0) {
    return <p>상품이 없습니다.</p>;
  }

  return (
    <div>
      {data.map((product) => (
        <div key={product.orderProductId} className='product-item'>
          {isEditing ? (
            <div className='product-form'>
              <input
                type='text'
                name='productName'
                value={
                  updatedProducts[product.orderProductId]?.productName ||
                  product.productName
                }
                onChange={(e) => handleInputChange(e, product.orderProductId)}
              />
              <input
                type='number'
                name='quantity'
                value={
                  updatedProducts[product.orderProductId]?.quantity ||
                  product.quantity
                }
                onChange={(e) => handleInputChange(e, product.orderProductId)}
              />
              <span className='price'>{product.price}원</span>
            </div>
          ) : (
            <div className='order-list-price-wrap'>
              <strong>{product.productName}</strong>
              <span className='quantity'> X </span>
              <span className='quantity'> {product.quantity} 개</span>
              <span className='price'>{product.price}원</span>
            </div>
          )}
        </div>
      ))}
    </div>
  );
};

export default OrderList;
