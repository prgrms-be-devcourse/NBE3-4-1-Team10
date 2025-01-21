import React from "react";
import "./OrderList.css";

const OrderList = ({ data }) => {
  if (!data || data.length === 0) {
    return <p>상품이 없습니다.</p>;
  }

  return (
    <div>
      {data.map((product) => (
        <div key={product.orderProductId} className='product-item'>
          <div className='order-list-price-wrap'>
            <strong>{product.productName}</strong>
            <span className='quantity'> X </span>
            <span className='quantity'> {product.quantity} 개</span>
            <span className='price'>{product.price}원</span>
          </div>
        </div>
      ))}
    </div>
  );
};

export default OrderList;
