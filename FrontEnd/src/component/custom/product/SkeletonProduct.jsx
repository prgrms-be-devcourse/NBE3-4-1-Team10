import React from "react";
import "./SkeletonProduct.css"; // Make sure to add styles in this file

const SkeletonProduct = () => {
  return (
    <>
      <figure className='product-img-wrap skeleton'>
        <div className='product-type skeleton-line'></div>
        <div className='product-img skeleton-img'></div>
      </figure>
      <figcaption className='product-info skeleton'>
        <div className='product-name skeleton-line'></div>
        <div className='product-price skeleton-line'></div>
      </figcaption>
    </>
  );
};

export default SkeletonProduct;
