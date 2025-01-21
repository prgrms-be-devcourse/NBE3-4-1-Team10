import React, { useEffect, useState } from "react";
import OrderList from "../../component/orderList/OrderList";

import Msg from "../../component/msg/Msg";

import { ProductService } from "../../service/ProductService";

import "./Order.css";

const Order = () => {
  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [selectedItems, setSelectedItems] = useState({});
  const [quantities, setQuantities] = useState({});

  // 상품 목록 가져오기
  const fetchProducts = async () => {
    try {
      const productData = await ProductService.getProductLists();
      setProducts(productData);
      // 상품 수량 초기화
      const initialQuantities = productData.reduce((acc, product) => {
        acc[product.id] = 0;
        return acc;
      }, {});
      setQuantities(initialQuantities);
      setIsLoading(false);
    } catch (error) {
      console.error("상품 목록 불러오기 오류:", error);
      setIsLoading(false);
    }
  };

  // 상품 목록 불러오기
  useEffect(() => {
    fetchProducts();
  }, []);

  // 수량 변경 함수
  const handleQuantityChange = (id, action) => {
    setQuantities((prevQuantities) => {
      const newQuantity =
        action === "increase"
          ? prevQuantities[id] + 1
          : prevQuantities[id] > 1
          ? prevQuantities[id] - 1
          : 1;
      return { ...prevQuantities, [id]: newQuantity };
    });
  };

  // 체크박스 상태 변경 함수 (각 상품별로 선택 상태를 반영)
  const handleCheckboxChange = (id) => {
    setSelectedItems((prevSelectedItems) => {
      // 상품의 선택 상태를 반전시킴 (true -> false, false -> true)
      const newSelectedItems = { ...prevSelectedItems };
      newSelectedItems[id] = !newSelectedItems[id]; // 선택 상태 반전
      return newSelectedItems;
    });
  };

  // 총 가격 계산 함수
  const calculateTotalPrice = () => {
    return products.reduce((total, item) => {
      if (selectedItems[item.id]) {
        return total + item.price * quantities[item.id];
      }
      return total;
    }, 0);
  };

  return (
    <form className='order-wrap'>
      <Msg text='Order' type='title' />

      {isLoading ? (
        <div>로딩 중...</div>
      ) : (
        products.map(
          (item, index) => (
            console.log(item),
            (
              <OrderList
                key={item.id}
                item={item}
                quantity={quantities[index]}
                selected={selectedItems[index] || false}
                onQuantityChange={handleQuantityChange}
                onCheckboxChange={handleCheckboxChange}
              />
            )
          )
        )
      )}
      <p className='total-price'>
        총 가격 : ₩ {new Intl.NumberFormat().format(calculateTotalPrice())}
      </p>

      <button className='button' type='submit'>
        제출
      </button>
    </form>
  );
};

export default Order;
