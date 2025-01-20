import React, { useEffect, useState } from "react";

import Product from "../../component/product/Product";

import { PRODUCTS } from "../../component/product/Dummy";

import Modal from "../../component/modal/Modal";

import "./Home.css";

export default function Home() {
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
    <div className='home-wrap'>
      {isLoading ? (
        <div className='skeleton-loader' />
      ) : (
        PRODUCTS.map((item) => (
          <React.Fragment key={item.id}>
            <section className='product-wrap' onClick={openModal}>
              <Product item={item} />
            </section>
            <Modal
              title={item.name}
              isOpen={isModalOpen}
              contents={item.content}
              onClose={closeModal}
              external
            />
          </React.Fragment>
        ))
      )}
    </div>
  );
}
