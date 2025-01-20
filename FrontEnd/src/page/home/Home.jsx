import React, { useEffect, useState } from "react";

import Product from "../../component/product/Product";

import { PRODUCTS } from "../../component/product/Dummy";

import Modal from "../../component/modal/Modal";

import "./Home.css";
import { ProductService } from "../../service/ProductService";

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
      const productData = await ProductService.getProductLists(); // 상품 목록 가져오기
      setProducts(productData);
      setIsLoading(false);
    } catch (error) {
      console.error("상품 목록 불러오기 오류:", error);
      setIsLoading(false);
    }
  };

  // 상품 추가 및 목록 갱신
  const addProduct = async () => {
    try {
      await ProductService.postProductLists({
        type: "커피콩",
        name: "Columbia Nariñó",
        imageUrl: "https://i.imgur.com/HKOFQYa.jpeg",
        content: "커피콩 Columbia Nariñó의 예시 설명글입니다.",
        price: 5000,
      });
      // 상품 추가 후, 목록을 다시 불러옵니다.
      fetchProducts(); // 상품 목록을 새로 불러옵니다.
    } catch (error) {
      console.error("상품 추가 오류:", error);
      setIsLoading(false); // 오류 발생 시에도 로딩 상태 해제
    }
  };

  useEffect(() => {
    fetchProducts(); // 컴포넌트가 마운트되면 상품 목록을 처음 가져옵니다.
  }, []);
  console.log(products);

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
