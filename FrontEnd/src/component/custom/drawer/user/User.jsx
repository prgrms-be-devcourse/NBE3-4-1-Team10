import React from "react";
import "./User.css";

export const User = ({ onClick }) => {
  return (
    <nav>
      {/* 로그인 내비게이션 */}
      <section className='cart-wrap'>
        <ul className='cart-btn'>
          <li>
            <button onClick={() => onClick("/order")}>Order</button>
          </li>
        </ul>
      </section>
    </nav>
  );
};
