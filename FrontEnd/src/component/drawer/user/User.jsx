import React from "react";
import "./User.css";

export const User = ({ open }) => {
  return (
    <nav>
      {/* 비로그인 상태 안내 */}
      <section className='user-wrap'>
        <h2 className='user-msg'>id</h2>
      </section>
      {/* 로그인 내비게이션 */}
      <section className='cart-wrap'>
        <ul className='cart-btn'>
          <li>
            <button onClick={open}>Order</button>
          </li>
        </ul>
      </section>
    </nav>
  );
};
