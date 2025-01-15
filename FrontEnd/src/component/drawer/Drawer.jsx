import React from "react";
import { Link } from "react-router-dom";

import "./Drawer.css";

const Drawer = ({ isOpen, close }) => {
  const handleClick = (id) => {
    close();
    const element = document.getElementById(id);
    if (element) {
      element.scrollIntoView({ behavior: "smooth" });
    }
  };

  return (
    <nav className={`drawer-wrap ${isOpen && "isOpen"}`}>
      <ul>
        <NonLogin />
      </ul>
    </nav>
  );
};

export default Drawer;

const NonLogin = () => {
  return (
    <nav>
      <section className='no-login-wrap'>
        <h2 className='no-login-msg'>Your Bag is empty</h2>
        <span>
          <Link className='nav-login-btn'>sign in</Link>to buy if you want any
          items
        </span>
      </section>
      <section className='profile-wrap'>
        <h3>My Profile</h3>
        <ul>
          <li>orders</li>
          <li>Sign in</li>
          <li>Join</li>
        </ul>
      </section>
    </nav>
  );
};

//  {
//    PRODUCTS &&
//      PRODUCTS.map((i) => (
//        <li key={i.id}>
//          <button
//            onClick={() => {
//              handleClick(i.id);
//            }}
//            aria-label={`go to ${i.name}`}>
//            <h2>{i.name}</h2>
//          </button>
//        </li>
//      ));
//  }
