import React, { useEffect, useState } from "react";
import { PRODUCTS } from "../product/Dummy";
import Modal from "../modal/Modal";
import "./OrderModal.css";

const OrderModal = ({ open, close }) => {
  useEffect(() => {
    if (open) {
      document.body.style.overflow = "hidden";
    } else {
      document.body.style.overflow = "auto";
    }

    return () => {
      document.body.style.overflow = "auto";
    };
  }, [open]);

  return (
    <Modal
      type='order'
      title='상품 종류 '
      isOpen={open}
      onClose={close}
      contents={<OrderContents />}
    />
  );
};

export default OrderModal;

const OrderContents = () => {
  const [quantities, setQuantities] = useState(
    PRODUCTS.reduce((acc, item) => ({ ...acc, [item.id]: 1 }), {})
  );

  const [selectedItems, setSelectedItems] = useState({});
  const [emailId, setEmailId] = useState("");
  const [selectedDomain, setSelectedDomain] = useState("naver.com");
  const [customDomain, setCustomDomain] = useState("");

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

  const handleCheckboxChange = (id) => {
    setSelectedItems((prevSelectedItems) => {
      if (prevSelectedItems[id]) {
        const { [id]: _, ...rest } = prevSelectedItems;
        return rest;
      } else {
        return { ...prevSelectedItems, [id]: true };
      }
    });
  };

  const handleEmailIdChange = (e) => {
    setEmailId(e.target.value);
  };

  const handleDomainChange = (e) => {
    const value = e.target.value;
    setSelectedDomain(value);
    if (value !== "custom") {
      setCustomDomain("");
    }
  };

  const handleCustomDomainChange = (e) => {
    setCustomDomain(e.target.value);
  };

  const calculateTotalPrice = () => {
    return PRODUCTS.reduce((total, item) => {
      if (selectedItems[item.id]) {
        return total + item.price * quantities[item.id];
      }
      return total;
    }, 0);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const totalPrice = calculateTotalPrice();
    const fullEmail = `${emailId}@${
      selectedDomain === "custom" ? customDomain : selectedDomain
    }`;
    console.log("총 가격:", totalPrice);
    console.log("이메일:", fullEmail);
  };

  return (
    <form className='order-from-wrap' onSubmit={handleSubmit}>
      {PRODUCTS.map((item) => (
        <div key={item.id} className='order-item-container'>
          <div className='order-item-wrap'>
            <img src={item.src} alt={item.name} className='order-item-image' />
            <label>
              <input
                type='checkbox'
                name='order'
                value={item.name}
                checked={selectedItems[item.id] || false}
                onChange={() => handleCheckboxChange(item.id)}
              />

              <span className='order-name'> {item.name}</span>
              <span className='order-price'>
                <i>{new Intl.NumberFormat().format(item.price)}원 </i>
              </span>
            </label>
          </div>

          <div
            className={`quantity-container ${
              !selectedItems[item.id] && "hide"
            }`}>
            <button
              type='button'
              onClick={() => handleQuantityChange(item.id, "decrease")}
              className='quantity-button'>
              -
            </button>
            <span>{quantities[item.id]}</span>
            <button
              type='button'
              onClick={() => handleQuantityChange(item.id, "increase")}
              className='quantity-button'>
              +
            </button>
          </div>
        </div>
      ))}

      {/* 이메일 */}
      <div className='email-container'>
        <label>
          E-mail
          <div className='email-wrap'>
            <input
              type='text'
              value={emailId}
              onChange={handleEmailIdChange}
              className='email-input'
              required
              placeholder='이메일 아이디'
            />
            <span>@</span>
            <input
              type='text'
              value={
                selectedDomain !== "custom" ? selectedDomain : customDomain
              }
              onChange={handleCustomDomainChange}
              className='custom-domain-input'
              placeholder={selectedDomain !== "custom" ? selectedDomain : ""}
              disabled={selectedDomain !== "custom"}
              required
            />
            <select
              value={selectedDomain}
              onChange={handleDomainChange}
              className='email-domain-select'
              required>
              <option value='naver.com'>naver.com</option>
              <option value='gmail.com'>gmail.com</option>
              <option value='kakao.com'>kakao.com</option>
              <option value='custom'>직접 입력</option>
            </select>
          </div>
        </label>
      </div>

      <div className='total-price'>
        <p>
          총 가격 : {new Intl.NumberFormat().format(calculateTotalPrice())} 원
        </p>
      </div>
      <button type='submit' className='submit-button'>
        제출
      </button>
    </form>
  );
};
