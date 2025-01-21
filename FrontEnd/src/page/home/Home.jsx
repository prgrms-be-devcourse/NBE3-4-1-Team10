import React, { useEffect, useState } from "react";
import Modal from "../../component/modal/Modal";
import Product from "../../component/custom/product/Product";
import { ProductService } from "../../service/ProductService";

import "./Home.css";

export default function Home() {
  const [products, setProducts] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [currentProduct, setCurrentProduct] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  const openModal = (product) => {
    setCurrentProduct(product);
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setCurrentProduct(null);
  };

  useEffect(() => {
    document.body.style.overflow = isModalOpen ? "hidden" : "auto";

    return () => {
      document.body.style.overflow = "auto";
    };
  }, [isModalOpen]);

  const fetchProducts = async () => {
    try {
      const productData = await ProductService.getProductLists();
      setProducts(productData);
      setIsLoading(false);
    } catch (error) {
      console.error("상품 목록 불러오기 오류:", error);
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  if (isLoading) {
    return (
      <div className='home-wrap'>
        {Array.from({ length: 3 }).map((_, index) => (
          <div className='skeleton-loader' key={index} />
        ))}
      </div>
    );
  }

  return (
    <div className='home-wrap'>
      {products.map((item) => (
        <React.Fragment key={item.id}>
          <section className='product-wrap' onClick={() => openModal(item)}>
            <Product item={item} />
          </section>

          {currentProduct && currentProduct.id === item.id && (
            <Modal
              title={currentProduct.name}
              isOpen={isModalOpen}
              contents={currentProduct.content}
              onClose={closeModal}
              external
            />
          )}
        </React.Fragment>
      ))}
    </div>
  );
}
