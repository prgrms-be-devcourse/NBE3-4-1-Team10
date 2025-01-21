import React, { useState } from "react";
import { DeleteIcon, EditIcon } from "../../../constant/Icon"; // 아이콘 가져오기
import OrderList from "../orderList/OrderList"; // 주문 상품 리스트
import "./RenderOrder.css"; // 스타일 import
import { OrderService } from "../../../service/OrderService";
import Alert from "../../alert/Alert";

const RenderOrder = ({ order, onUpdateOrder }) => {
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
  const onClickDeleteOrderList = async (id) => {
    try {
      const res = await OrderService.deleteOrderLists(id);
      if (res?.status === 200) {
        Alert("주문이 삭제되었습니다.", "", "", () => window.location.reload());
      }
    } catch (error) {}
  };

  return (
    <div key={order.orderID} className='order-card'>
      <div className='order-header'>
        <h3>주문 ID: {order.orderID}</h3>
        <p>{order.status}</p>

        {/* 수정 아이콘 또는 저장/취소 버튼 표시 */}
        <div className='edit-buttons'>
          {isEditing ? (
            <div className='edit-actions'>
              <button className='save-button' onClick={handleSaveClick}>
                저장
              </button>
              <button className='cancel-button' onClick={handleCancelClick}>
                취소
              </button>
            </div>
          ) : (
            <div className='edit-actions'>
              <button className='edit-button' onClick={handleEditClick}>
                <EditIcon />
              </button>
              <button
                className='delete-button'
                onClick={() => onClickDeleteOrderList(order.orderID)}>
                <DeleteIcon />
              </button>
            </div>
          )}
        </div>
      </div>

      {/* 수정 모드일 때 */}
      {isEditing ? (
        <div className='order-edit-form'>
          <div className='product-item'>
            <span className='label'>이메일</span>
            <strong>{order.email}</strong>
          </div>
          <div className='order-edit-form-wrap'>
            <label className='label'>주소</label>
            <input
              type='text'
              name='address'
              value={updatedOrder.address}
              onChange={handleInputChange}
            />
          </div>
          <div className='order-edit-form-wrap'>
            <label className='label'>우편번호</label>
            <input
              type='text'
              name='post'
              value={updatedOrder.post}
              onChange={handleInputChange}
            />
          </div>
          <div className='product-item'>
            <span className='label'>총 금액</span>
            <strong>{order.totalPrice}원</strong>
          </div>
        </div>
      ) : (
        // 수정 비활성화 상태일 때
        <div className='order-info'>
          <div className='product-item'>
            <span className='label'>이메일</span>
            <strong>{order.email}</strong>
          </div>
          <div className='product-item'>
            <span className='label'>주소</span>
            <strong>{order.address}</strong>
          </div>
          <div className='product-item'>
            <span className='label'>우편번호</span>
            <strong>{order.post}</strong>
          </div>
          <div className='product-item'>
            <span className='label'>총 금액</span>
            <strong>{order.totalPrice}원</strong>
          </div>
        </div>
      )}

      {/* 주문 상품 수정 */}
      <div className='order-products'>
        <h4>주문 상품</h4>
        <OrderList data={order.orderProduct} />
      </div>
    </div>
  );
};

export default RenderOrder;
