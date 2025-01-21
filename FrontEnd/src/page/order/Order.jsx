import React, { useEffect, useState } from "react";
import OrderList from "../../component/orderList/OrderList";
import Msg from "../../component/msg/Msg";
import { ProductService } from "../../service/ProductService";
import "./Order.css";

const Order = () => {
  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [selectedItems, setSelectedItems] = useState([]); // 배열로 변경
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

  // 선택된 상품 관리 함수
  const onClickOrder = (id) => {
    setSelectedItems((prevSelected) => {
      if (prevSelected.includes(id)) {
        // 이미 선택된 상품이라면 배열에서 제거
        return prevSelected.filter((itemId) => itemId !== id);
      } else {
        // 선택되지 않은 상품이라면 배열에 추가
        return [...prevSelected, id];
      }
    });
  };

  // 총 가격 계산 함수
  const calculateTotalPrice = () => {
    return products.reduce((total, item) => {
      if (selectedItems.includes(item.productId)) {
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
        products.map((item, index) => (
          <OrderList
            key={item.productId}
            item={item}
            quantity={quantities[item.id]}
            selected={selectedItems.includes(item.productId)}
            onQuantityChange={handleQuantityChange}
            onClick={onClickOrder}
          />
        ))
      )}
      <p className='total-price'>
        총 가격 : ₩ {new Intl.NumberFormat().format(calculateTotalPrice())}
      </p>

      <button className='button' type='submit' disabled={isLoading}>
        {isLoading ? "주문 중.." : "주문"}
      </button>
    </form>
  );
};

export default Order;
