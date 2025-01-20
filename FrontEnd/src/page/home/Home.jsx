import React, { useEffect, useState } from "react";
import Modal from "../../component/modal/Modal";
import Product from "../../component/product/Product";
import { ProductService } from "../../service/ProductService";

import "./Home.css";

export default function Home() {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [products, setProducts] = useState([]);

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
  // 상품 목록 가져오기
  const fetchProducts = async () => {
    try {
      const productData = await ProductService.getProductLists();
      setProducts(productData);
    } catch (error) {
      console.error("상품 목록 불러오기 오류:", error);
    }
  };

  useEffect(() => {
    fetchProducts();
    setIsLoading(false);
  }, []);

  return (
    <div className='home-wrap'>
      {products?.map((item) => (
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
      ))}
    </div>
  );
}
