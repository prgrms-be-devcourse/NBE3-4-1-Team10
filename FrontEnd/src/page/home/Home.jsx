import React, { useEffect, useState } from "react";
import Modal from "../../component/modal/Modal";
import Product from "../../component/custom/product/Product";

import { ProductService } from "../../service/ProductService";

import SkeletonProduct from "../../component/custom/product/SkeletonProduct";

import "./Home.css";

export default function Home() {
  const [products, setProducts] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [currentProduct, setCurrentProduct] = useState(null);
  const [isLoading, setIsLoading] = useState(false);

  const openModal = (product) => {
    setCurrentProduct(product);
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setCurrentProduct(null);
  };

  const fetchProducts = async () => {
    setIsLoading(true);
    try {
      const productData = await ProductService.getProductLists();
      setProducts(productData);
      setIsLoading(false);
    } catch (error) {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  useEffect(() => {
    document.body.style.overflow = isModalOpen ? "hidden" : "auto";

    return () => {
      document.body.style.overflow = "auto";
    };
  }, [isModalOpen]);

  return (
    <div className='home-wrap'>
      {isLoading
        ? Array?.from({ length: 3 })?.map((_, index) => (
            <section className='product-wrap' key={index}>
              <SkeletonProduct />
            </section>
          ))
        : products.map((item) => (
            <React.Fragment key={item?.productId}>
              <section className='product-wrap' onClick={() => openModal(item)}>
                <Product item={item} />
              </section>

              {currentProduct &&
                currentProduct.productId === item.productId && (
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
