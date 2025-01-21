import React from "react";

import "./Product.css";

export default function Product({ item }) {
  return (
    <>
      <figure
        className='product-img-wrap'
        id={item.productId}
        key={item.productId}>
        <span className='product-type'>{item.type}</span>
        <img className='product-img' src={item.imageUrl} alt='product-img' />
      </figure>
      <figcaption className='product-info'>
        <span className='product-name'>{item.name}</span>
        <span>{`₩ ${item.price}`}</span>
      </figcaption>
    </>
  );
}
