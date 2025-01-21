import React from "react";
import { USER_LIST } from "../../../../constant/constantLists";

import "./Guest.css";

export const Guest = ({ onClick }) => {
  return (
    <nav>
      {/* 비로그인 상태 안내 */}
      <section className='guest-wrap'>
        <h2 className='guest-msg'>Your Bag is empty</h2>
        <p>
          <button className='nav-login-btn' onClick={() => onClick("/login")}>
            {USER_LIST[0].title}
          </button>
          to buy if you want any items
        </p>
      </section>
      {/* 로그인 내비게이션 */}
      <section className='profile-wrap'>
        <ul className='profile-btn'>
          {USER_LIST &&
            USER_LIST.map((i) => (
              <li key={i.id}>
                <button onClick={() => onClick(i.href)}>{i.title}</button>
              </li>
            ))}
        </ul>
      </section>
    </nav>
  );
};
