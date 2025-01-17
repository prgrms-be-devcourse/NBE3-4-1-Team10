import React from "react";

import Product from "../../component/product/Product";

import { PRODUCTS } from "../../component/product/Dummy";
import "./Home.css";

export default function Home() {
  return (
    <div className='home-wrap'>
      {PRODUCTS.map((item) => (
        <Product item={item} key={item.id} />
      ))}
    </div>
  );
}
