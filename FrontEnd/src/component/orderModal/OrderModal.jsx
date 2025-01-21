import React, { useEffect, useState } from "react";
import { ProductService } from "../../service/ProductService";
import Modal from "../modal/Modal";
import "./OrderModal.css";
import { PRODUCTS } from "../custom/product/Dummy";

// OrderModal 컴포넌트
const OrderModal = ({ open, close }) => {
  const [products, setProducts] = useState([]);

  // 상품 목록 가져오기
  useEffect(() => {
    if (open) {
      document.body.style.overflow = "hidden";
      fetchProducts();
    } else {
      document.body.style.overflow = "auto";
    }

    return () => {
      document.body.style.overflow = "auto";
    };
  }, [open]);

  // 상품 목록 가져오기 함수
  const fetchProducts = async () => {
    try {
      //   const productData = await ProductService.getProductLists();
      const productData = PRODUCTS;
      setProducts(productData);
    } catch (error) {
      console.error("상품 목록 불러오기 오류:", error);
    }
  };

  return (
    <Modal
      type='order'
      title='상품 종류'
      isOpen={open}
      onClose={close}
      contents={<OrderContents data={products} />}
    />
  );
};

export default OrderModal;

// OrderContents 컴포넌트
const OrderContents = ({ data }) => {
  const [quantities, setQuantities] = useState(initializeQuantities(data));
  const [selectedItems, setSelectedItems] = useState({});
  const [emailId, setEmailId] = useState("");
  const [selectedDomain, setSelectedDomain] = useState("naver.com");
  const [customDomain, setCustomDomain] = useState("");

  // 수량 초기화 함수
  const initializeQuantities = (data) => {
    return data.reduce((acc, item) => ({ ...acc, [item.id]: 1 }), {});
  };

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

  // 체크박스 변경 함수
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

  // 이메일 관련 상태 관리 함수
  const handleEmailIdChange = (e) => setEmailId(e.target.value);

  const handleDomainChange = (e) => {
    const value = e.target.value;
    setSelectedDomain(value);
    if (value !== "custom") setCustomDomain("");
  };

  const handleCustomDomainChange = (e) => setCustomDomain(e.target.value);

  // 총 가격 계산 함수
  const calculateTotalPrice = () => {
    return data.reduce((total, item) => {
      if (selectedItems[item.id]) {
        return total + item.price * quantities[item.id];
      }
      return total;
    }, 0);
  };

  // 폼 제출 함수
  const handleSubmit = (e) => {
    e.preventDefault();
    const totalPrice = calculateTotalPrice();
    const fullEmail = `${emailId}@${
      selectedDomain === "custom" ? customDomain : selectedDomain
    }`;
    // 제출할 데이터 처리 로직 추가
  };

  return (
    <form className='order-form-wrap' onSubmit={handleSubmit}>
      {data.map((item) => (
        <OrderItem
          key={item.id}
          item={item}
          quantity={quantities[item.id]}
          selected={selectedItems[item.id] || false}
          onQuantityChange={handleQuantityChange}
          onCheckboxChange={handleCheckboxChange}
        />
      ))}

      <EmailSection
        emailId={emailId}
        selectedDomain={selectedDomain}
        customDomain={customDomain}
        onEmailIdChange={handleEmailIdChange}
        onDomainChange={handleDomainChange}
        onCustomDomainChange={handleCustomDomainChange}
      />

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

// OrderItem 컴포넌트: 각 상품을 나타내는 컴포넌트
const OrderItem = ({
  item,
  quantity,
  selected,
  onQuantityChange,
  onCheckboxChange,
}) => {
  return (
    <div className='order-item-container'>
      <div className='order-item-wrap'>
        <img src={item.imageUrl} alt={item.name} className='order-item-image' />
        <label>
          <input
            type='checkbox'
            name='order'
            value={item.name}
            checked={selected}
            onChange={() => onCheckboxChange(item.id)}
          />
          <span className='order-name'>{item.name}</span>
          <span className='order-price'>
            <i>{new Intl.NumberFormat().format(item.price)}원</i>
          </span>
        </label>
      </div>

      {selected && (
        <div className='quantity-container'>
          <button
            type='button'
            onClick={() => onQuantityChange(item.id, "decrease")}
            className='quantity-button'>
            -
          </button>
          <span>{quantity}</span>
          <button
            type='button'
            onClick={() => onQuantityChange(item.id, "increase")}
            className='quantity-button'>
            +
          </button>
        </div>
      )}
    </div>
  );
};

// EmailSection 컴포넌트: 이메일 입력 관련 부분
const EmailSection = ({
  emailId,
  selectedDomain,
  customDomain,
  onEmailIdChange,
  onDomainChange,
  onCustomDomainChange,
}) => {
  return (
    <div className='email-container'>
      <label>
        E-mail
        <div className='email-wrap'>
          <input
            type='text'
            value={emailId}
            onChange={onEmailIdChange}
            className='email-input'
            required
            placeholder='이메일 아이디'
          />
          <span>@</span>
          <input
            type='text'
            value={selectedDomain !== "custom" ? selectedDomain : customDomain}
            onChange={onCustomDomainChange}
            className='custom-domain-input'
            placeholder={selectedDomain !== "custom" ? selectedDomain : ""}
            disabled={selectedDomain !== "custom"}
            required
          />
          <select
            value={selectedDomain}
            onChange={onDomainChange}
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
  );
};
