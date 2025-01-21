import React, { useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router-dom";

import Msg from "../../component/msg/Msg";
import Alert from "../../component/alert/Alert";
import OrderList from "../../component/orderList/OrderList";
import { PRODUCTS } from "../../component/custom/product/Dummy";
import SkeletonList from "../../component/orderList/SkeletonList";

import { FORM_FIELD } from "../../constant/formFields";

import { ProductService } from "../../service/ProductService";
import { OrderService } from "../../service/OrderService";

import "./Order.css";

const Order = () => {
  const [body, setBody] = useState({ address: "", post: "" });
  const [products, setProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [selectedItems, setSelectedItems] = useState([]);
  const [quantities, setQuantities] = useState({});
  const [message, setMessage] = useState("");
  const addressRef = useRef(null);
  const postRef = useRef(null);
  const router = useNavigate();

  const orderFormFields = [
    {
      id: "address",
      label: "주소",
      name: "address",
      type: "text",
      placeholder: "주소를 입력해주세요.",
      ref: addressRef,
    },
    {
      id: "post",
      label: "우편번호",
      name: "post",
      type: "text",
      placeholder: "우편번호를 입력해주세요.",
      ref: postRef,
    },
  ];

  const onChangeInput = (name, e) => {
    setBody((prev) => ({ ...prev, [name]: e }));
  };

  const handleQuantityChange = (productId, action) => {
    setQuantities((prevQuantities) => {
      const currentQuantity = prevQuantities[productId]?.quantity || 1;

      let newQuantity;
      if (action === "increase") {
        newQuantity = currentQuantity + 1;
      } else if (action === "decrease") {
        newQuantity =
          selectedItems.includes(productId) && currentQuantity > 1
            ? currentQuantity - 1
            : 1;
      }

      return {
        ...prevQuantities,
        [productId]: {
          ...prevQuantities[productId],
          quantity: newQuantity,
        },
      };
    });
  };

  const onClickSelectOrder = (productId) => {
    setSelectedItems((prevSelected) => {
      const newSelectedItems = prevSelected.includes(productId)
        ? prevSelected.filter((id) => id !== productId)
        : [...prevSelected, productId];

      if (!newSelectedItems.includes(productId)) {
        setQuantities((prevQuantities) => ({
          ...prevQuantities,
          [productId]: { ...prevQuantities[productId], quantity: 1 },
        }));
      }

      return newSelectedItems;
    });
  };

  const calculateTotalPrice = () => {
    return products.reduce((total, product) => {
      if (selectedItems.includes(product.productId)) {
        const quantity = quantities[product.productId]?.quantity || 1;
        const price = product.price;
        return total + price * quantity;
      }
      return total;
    }, 0);
  };

  const handleWarn = (ref, timeout = 3000) => {
    ref.current.classList.add("warn");

    setTimeout(() => {
      setMessage("");
      ref.current.classList.remove("warn");
    }, timeout);
  };

  const validateFields = () => {
    if (calculateTotalPrice() === 0) {
      setMessage("상품을 담아주세요.");
      return false;
    }
    if (!body.address) {
      setMessage("주소를 입력해 주세요.");
      handleWarn(addressRef);
      return false;
    }
    if (!body.post) {
      setMessage("우편번호를 입력해 주세요.");
      handleWarn(postRef);
      return false;
    }
    return true;
  };

  const handleKeyDown = (e) => {
    const keyCode = e?.keyCode;
    const Enter = 13;

    if (keyCode === Enter) {
    }
  };

  const onClickOrder = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    if (!validateFields()) {
      setIsLoading(false);
      return;
    }
    const orderProductDto = Object.keys(quantities)
      .filter((productId) => quantities[productId]?.quantity > 0)
      .map((productId) => ({
        productId: parseInt(productId),
        count: quantities[productId]?.quantity || 1,
      }));
    try {
      const totalPrice = calculateTotalPrice();
      const res = await OrderService.postOrderLists({
        address: body?.address,
        post: body?.post,
        totalPrice,
        orderProductDto,
      });

      if (!res) {
        Alert(
          "로그인 후 \n 사용해주세요.",
          "",
          "",
          () => setIsLoading(false),
          router("/login")
        );
      } else if (res) {
        Alert(
          "주문이 \n 완료되었습니다.",
          "",
          "",
          () => setIsLoading(false),
          router("/")
        );
      }
    } catch (error) {
      Alert("주문이 실패했습니다.", "", "", () => setIsLoading(false));
    } finally {
      setIsLoading(false);
    }
  };

  const fetchProducts = async () => {
    try {
      const initialQuantities = {};
      const productData = await ProductService.getProductLists();
      setProducts(productData);
      PRODUCTS.forEach((product) => {
        initialQuantities[product.productId] = {
          quantity: 1,
          price: product.price,
        };
      });
      setQuantities(initialQuantities);
      setIsLoading(false);
    } catch {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  return (
    <form className='order-wrap' onSubmit={onClickOrder}>
      <Msg text='Order' type='title' />

      {isLoading
        ? Array.from({ length: 4 }).map((_, index) => (
            <SkeletonList key={index} />
          ))
        : products.map((item) => (
            <OrderList
              key={item.productId}
              item={item}
              quantity={quantities[item.productId]?.quantity || 1}
              selected={selectedItems.includes(item.productId)}
              onQuantityChange={handleQuantityChange}
              onClick={onClickSelectOrder}
            />
          ))}
      <section className='order-user-info-wrap'>
        {orderFormFields.map(({ id, label, name, type, placeholder, ref }) => (
          <FORM_FIELD
            key={id}
            id={id}
            label={label}
            name={name}
            type={type}
            placeholder={placeholder}
            value={body[name]}
            onKeyDown={handleKeyDown}
            onChange={onChangeInput}
            ref={ref}
            isLoading={isLoading}
          />
        ))}
        <Msg text={message} type='error' />
      </section>
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
