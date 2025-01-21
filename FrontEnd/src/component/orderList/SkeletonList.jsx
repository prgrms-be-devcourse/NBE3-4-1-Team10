import React from "react";
import "./SkeletonList.css";

const SkeletonList = () => {
  return (
    <div className='order-item-container skeleton'>
      <div className='order-item-wrap skeleton'>
        <div className='order-item-img skeleton-img' />
        <ul className='order-item-info skeleton-info'>
          <li className='type skeleton-line' />
          <li className='name skeleton-line' />
        </ul>
        <span className='order-price skeleton-line' />
      </div>
    </div>
  );
};

export default SkeletonList;
