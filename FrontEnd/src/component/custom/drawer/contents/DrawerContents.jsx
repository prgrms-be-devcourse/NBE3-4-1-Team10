import React from "react";
import { GUEST_LIST, USER_LIST } from "../../../../constant/constantLists";

import "./DrawerContents.css";

const DrawerContents = ({ onClick, isUser }) => {
  const list = isUser ? USER_LIST : GUEST_LIST;
  const message = isUser ? "Your Bag is empty" : "You Can Order";
  const actionText = isUser
    ? "to buy if you want any items"
    : "to buy if you want more items";

  return (
    <nav>
      {/* 안내 메시지 */}
      <section className='guest-wrap'>
        <h2 className='guest-msg'>{message}</h2>
        <p>
          <button className='nav-login-btn' onClick={() => onClick("/login")}>
            {list[0].title}
          </button>
          {actionText}
        </p>
      </section>

      {/* 내비게이션 버튼 */}
      <section className='profile-wrap'>
        <ul className='profile-btn'>
          {list &&
            list.map((item) => (
              <li key={item.id}>
                <button onClick={() => onClick(item.href)}>{item.title}</button>
              </li>
            ))}
        </ul>
      </section>
    </nav>
  );
};

export default DrawerContents;
