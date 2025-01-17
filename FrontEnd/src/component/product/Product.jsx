import React, { useEffect, useState } from "react";
import Modal from "../modal/Modal";

import "./Product.css";

const Product = ({ item }) => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(true);

  const openModal = () => {
    setIsModalOpen(true);
  };

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

  useEffect(() => {
    setIsLoading(false);
  }, [isLoading]);

  return (
    <>
      <section className='product-wrap' onClick={openModal}>
        {!isLoading ? (
          <figure className='product-img-wrap' id={item.id} key={item.id}>
            <span className='product-type'>{item.type}</span>
            <img className='product-img' src={item.src} alt={item.alt} />
          </figure>
        ) : (
          <div className='skeleton-loader' />
        )}
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
