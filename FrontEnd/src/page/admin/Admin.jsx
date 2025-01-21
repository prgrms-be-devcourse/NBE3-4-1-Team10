import React, { useState, useEffect } from "react";
import RenderOrder from "../../component/admin/renderOrder/RenderOrder";
import { OrderService } from "../../service/OrderService";

import "./Admin.css";

const Admin = () => {
  const [orders, setOrders] = useState({ tomorrow: [], today: [] });
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchOrders = async () => {
    setIsLoading(true);
    setError(null);
    try {
      const tomorrowOrderData = await OrderService.getTommorowOrderLists();
      const todayOrderData = await OrderService.getTodayOrderLists();
      setOrders({ tomorrow: tomorrowOrderData, today: todayOrderData });
    } catch (err) {
      setError("주문 데이터를 불러오는 중에 문제가 발생했습니다.");
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchOrders();
  }, []);
  const handleUpdateOrder = async (orderID, updatedOrder) => {
    try {
      setOrders((prevOrders) => {
        const updatedOrders = { ...prevOrders };

        ["today", "tomorrow"].forEach((key) => {
          updatedOrders[key] = updatedOrders[key].map(async (order) => {
            if (order.orderID === orderID) {
              const updatedFullOrder = {
                ...order,
                ...updatedOrder,
              };

              const updatedOrderProductDto = updatedFullOrder.orderProduct.map(
                (product) => {
                  const { quantity, productId } = product;

                  return {
                    productId,
                    count: quantity,
                  };
                }
              );

              const body = {
                address: updatedFullOrder.address,
                post: updatedFullOrder.post,
                totalPrice: updatedFullOrder.totalPrice,
                orderProductDto: updatedOrderProductDto,
              };

              const response = await OrderService.putOrderLists(body, orderID);
              if (response) {
                console.log("주문이 성공적으로 수정되었습니다.");
              }

              return {
                ...updatedFullOrder,
                orderProduct: updatedOrderProductDto,
              };
            }

            return order;
          });
        });

        return updatedOrders;
      });
    } catch (error) {
      console.error("주문 업데이트 중 오류 발생:", error);
    }
  };

  if (isLoading) {
    return <div className='loading-message'>로딩 중...</div>;
  }

  if (error) {
    return <div className='error-message'>{error}</div>;
  }

  return (
    <div className='container'>
      <h2 className='section-title'>오늘의 주문</h2>
      <div className='order-list'>
        {orders.today.length > 0 ? (
          orders.today.map((order) => (
            <RenderOrder
              key={order.orderID}
              order={order}
              onUpdateOrder={handleUpdateOrder}
            />
          ))
        ) : (
          <p className='empty-message'>오늘의 주문이 없습니다.</p>
        )}
      </div>

      <h2 className='section-title'>내일의 주문</h2>
      <div className='order-list'>
        {orders.tomorrow.length > 0 ? (
          orders.tomorrow.map((order) => (
            <RenderOrder
              key={order.orderID}
              order={order}
              onUpdateOrder={handleUpdateOrder}
            />
          ))
        ) : (
          <p className='empty-message'>내일의 주문이 없습니다.</p>
        )}
      </div>
    </div>
  );
};

export default Admin;
