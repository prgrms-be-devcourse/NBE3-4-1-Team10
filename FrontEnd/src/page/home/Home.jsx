import React from "react";

import Product from "../../component/product/Product";

import { PRODUCTS } from "../../component/product/Dummy";
import "./Home.css";

export default function Home() {
  //   const { data, loading, error } = useFetch("Todo...");
  //   // 로딩 상태, 오류 상태 처리
  //   if (loading) return <div>로딩 중...</div>;
  //   if (error) return <div>에러: {error}</div>;
  //   if (!data || data.length === 0) return <div>데이터가 없습니다.</div>;
  return (
    <div className='home-wrap'>
      {PRODUCTS.map((item) => (
        <Product item={item} key={item.id} />
      ))}
    </div>
  );
}
