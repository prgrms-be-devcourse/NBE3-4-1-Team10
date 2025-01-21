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

  const handleUpdateOrder = (orderID, updatedOrder) => {
    setOrders((prevOrders) => {
      const updatedOrders = { ...prevOrders };
      ["today", "tomorrow"].forEach((key) => {
        updatedOrders[key] = updatedOrders[key].map((order) => {
          if (order.orderID === orderID) {
            return { ...order, ...updatedOrder };
          }
          return order;
        });
      });
      return updatedOrders;
    });
  };

  const handleUpdateProduct = (orderProductId, updatedProduct) => {
    setOrders((prevOrders) => {
      const updatedOrders = { ...prevOrders };
      ["today", "tomorrow"].forEach((key) => {
        updatedOrders[key] = updatedOrders[key].map((order) => {
          if (order.orderProduct) {
            order.orderProduct = order.orderProduct.map((product) => {
              if (product.orderProductId === orderProductId) {
                return { ...product, ...updatedProduct };
              }
              return product;
            });
          }
          return order;
        });
      });
      return updatedOrders;
    });
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
              onUpdateProduct={handleUpdateProduct}
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
              onUpdateProduct={handleUpdateProduct}
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
