import React, { useEffect, useState } from "react";
import "./Product.css";
import Modal from "../modal/Modal";

const Product = ({ item }) => {
  // 모달의 상태와 선택된 아이템 상태를 관리
  const [isModalOpen, setIsModalOpen] = useState(false);

  // 모달 열기
  const openModal = () => {
    setIsModalOpen(true);
  };

  // 모달 닫기
  const closeModal = () => {
    setIsModalOpen(false);
  };

  useEffect(() => {
    if (isModalOpen) {
      document.body.style.overflow = "hidden";
    } else {
      document.body.style.overflow = "auto";
    }

    return () => {
      document.body.style.overflow = "auto";
    };
  }, [isModalOpen]);

  return (
    <>
      <section className='product-wrap' onClick={openModal}>
        <figure className='product-img-wrap' id={item.id} key={item.id}>
          <span className='product-type'>{item.type}</span>
          <img className='product-img' src={item.src} alt={item.alt} />
        </figure>
        <figcaption className='product-content'>
          <p className='product-info'>
            <span className='product-name'>{item.name}</span>
            <span>{`₩ ${item.price}`}</span>
          </p>
        </figcaption>
      </section>
      <Modal
        title={item.name}
        isOpen={isModalOpen}
        contents={item.content}
        onClose={closeModal}
        external
      />
    </>
  );
};

export default Product;
