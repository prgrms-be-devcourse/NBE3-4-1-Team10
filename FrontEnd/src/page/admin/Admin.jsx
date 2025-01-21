import React, { useState, useEffect } from "react";
import { DeleteIcon, EditIcon } from "../../constant/Icon";
import { ProductService } from "../../service/ProductService";
import { OrderService } from "../../service/OrderService";

import "./Admin.css";
import Msg from "../../component/msg/Msg";

const AdminPage = () => {
  const [tommorowOrderList, setTommorowOrderList] = useState([]);
  const [todayOrderList, setTodayOrderList] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  const fetchProducts = async () => {
    setIsLoading(true);
    try {
      const tomorrowOrderData = await OrderService.getTommorowOrderLists();
      const todayOrderData = await OrderService.getTodayOrderLists();
      setTommorowOrderList(tomorrowOrderData);
      setTodayOrderList(todayOrderData);
      setIsLoading(false);
    } catch {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  // 그룹화된 주문 리스트 생성
  const groupOrdersByAddressAndPost = (orderList) => {
    const groupedOrders = {};

    orderList.forEach((order) => {
      const key = `${order.post}_${order.address}`;

      if (!groupedOrders[key]) {
        groupedOrders[key] = {
          post: order.post,
          address: order.address,
          orderProduct: [],
          totalPrice: 0,
        };
      }

      // 제품 항목 합산 및 총 가격 계산
      order.orderProduct.forEach((product) => {
        groupedOrders[key].orderProduct.push(product);
        groupedOrders[key].totalPrice += product.price * product.quantity;
      });
    });

    return Object.values(groupedOrders);
  };

  const groupedTomorrowOrders = groupOrdersByAddressAndPost(tommorowOrderList);
  const groupedTodayOrders = groupOrdersByAddressAndPost(todayOrderList);

  console.log(tommorowOrderList);

  return (
    <main className='admin-wrap'>
      <header>
        <Msg type='title' text='ADMIN' />
      </header>

      <h3>배송 전</h3>
      <div className='order-table-container'>
        <table>
          <thead>
            <tr>
              <th>주문 번호</th>
              <th>주소</th>
              <th>주문내역</th>
              <th>총 금액</th>
              <th>상태</th>
            </tr>
          </thead>
          <tbody>
            {groupedTomorrowOrders.map((order, index) => (
              <tr key={index}>
                <td>{order.post}</td>
                <td>{order.address}</td>
                <td>
                  {order.orderProduct.map((product, productIndex) => (
                    <div key={productIndex}>
                      {product.productName} (수량: {product.quantity}, 가격:{" "}
                      {product.price.toLocaleString()} 원)
                    </div>
                  ))}
                </td>
                <td>{order.totalPrice.toLocaleString()} 원</td>
                <td>{order.status}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <h3>배송 완료</h3>
      <div className='order-table-container'>
        <table>
          <thead>
            <tr>
              <th>주문 번호</th>
              <th>주소</th>
              <th>주문내역</th>
              <th>총 금액</th>
              <th>상태</th>
            </tr>
          </thead>
          <tbody>
            {groupedTodayOrders.map((order, index) => (
              <tr key={index}>
                <td>{order.post}</td>
                <td>{order.address}</td>
                <td>
                  {order.orderProduct.map((product, productIndex) => (
                    <div key={productIndex}>
                      {product.productName} (수량: {product.quantity}, 가격:{" "}
                      {product.price.toLocaleString()} 원)
                    </div>
                  ))}
                </td>
                <td>{order.totalPrice.toLocaleString()} 원</td>
                <td>{order.status}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </main>
  );
};

export default AdminPage;
