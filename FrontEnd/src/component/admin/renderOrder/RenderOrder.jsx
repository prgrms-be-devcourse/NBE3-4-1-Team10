import React, { useState } from "react";
import { EditIcon } from "../../../constant/Icon"; // 아이콘 가져오기
import OrderList from "../orderList/OrderList"; // 주문 상품 리스트
import "./RenderOrder.css"; // 스타일 import

const RenderOrder = ({ order, onUpdateOrder, onUpdateProduct }) => {
  const [isEditing, setIsEditing] = useState(false); // 전체 수정 상태
  const [updatedOrder, setUpdatedOrder] = useState({
    email: order.email,
    address: order.address,
    post: order.post,
  });

  // 수정 시작
  const handleEditClick = () => {
    setIsEditing(true);
  };

  // 취소 버튼 클릭 시, 수정 취소
  const handleCancelClick = () => {
    setIsEditing(false);
    setUpdatedOrder({
      email: order.email,
      address: order.address,
      post: order.post,
    });
  };

  // 주문 정보 저장
  const handleSaveClick = () => {
    onUpdateOrder(order.orderID, updatedOrder);
    setIsEditing(false); // 수정 모드 종료
  };

  // 인풋 값 변경 시, updatedOrder 상태 업데이트
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUpdatedOrder((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  return (
    <div key={order.orderID} className='order-card'>
      <div className='order-header'>
        <h3>주문 ID: {order.orderID}</h3>
        <p>{order.status}</p>

        {/* 수정 아이콘 */}
        <button className='edit-button' onClick={handleEditClick}>
          <EditIcon />
        </button>
      </div>

      {/* 수정 모드일 때 */}
      {isEditing ? (
        <div className='order-edit-form'>
          <div>
            <label>이메일</label>
            <input
              type='email'
              name='email'
              value={updatedOrder.email}
              onChange={handleInputChange}
            />
          </div>
          <div>
            <label>주소</label>
            <input
              type='text'
              name='address'
              value={updatedOrder.address}
              onChange={handleInputChange}
            />
          </div>
          <div>
            <label>우편번호</label>
            <input
              type='text'
              name='post'
              value={updatedOrder.post}
              onChange={handleInputChange}
            />
          </div>

          <div className='order-edit-actions'>
            <button onClick={handleSaveClick}>저장</button>
            <button onClick={handleCancelClick}>취소</button>
          </div>
        </div>
      ) : (
        // 수정 비활성화 상태일 때
        <div className='order-info'>
          <div className='product-item'>
            <strong>{order.email}</strong>
            <span className='label'>이메일</span>
          </div>
          <div className='product-item'>
            <strong>{order.address}</strong>
            <span className='label'>주소</span>
          </div>
          <div className='product-item'>
            <strong>{order.post}</strong>
            <span className='label'>우편번호</span>
          </div>
          <div className='product-item'>
            <strong>{order.totalPrice}원</strong>
            <span className='label'>총 금액</span>
          </div>
        </div>
      )}

      {/* 주문 상품 수정 */}
      <div className='order-products'>
        <h4>주문 상품</h4>
        <OrderList
          data={order.orderProduct}
          isEditing={isEditing}
          onUpdateProduct={onUpdateProduct}
        />
      </div>
    </div>
  );
};

export default RenderOrder;
