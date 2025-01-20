import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

import DrawerBtn from "./MHeader/drawerBtn/DrawerBtn";
import Logo from "../logo/Logo";
import Drawer from "../drawer/Drawer";
import OrderModal from "../orderModal/OrderModal";

import { CartLogo } from "../../constant/Icon";
import { USER_LIST } from "../../constant/user";

import "./Header.css";

const COLOR = "#FFFFFF";
const LOGO_SIZE = "35";

export default function Header() {
  const router = useNavigate();
  const [headerValue, setHeaderValue] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const closeModal = () => {
    setIsModalOpen(false);
  };
  const openModal = () => {
    setIsModalOpen(true);
  };

  const onChangeHeaderValue = () => {
    setHeaderValue((prev) => !prev);
  };

  return (
    <header id='header'>
      <article className='header-wrap'>
        <Logo size={LOGO_SIZE} color={COLOR} />
        <DrawerBtn isOpen={headerValue} onClick={onChangeHeaderValue} />
        <nav className='pc-header-nav hide-mobile'>
          <ul className='pc-login'>
            <li>
              <button className='cart-button hide-mobile' onClick={openModal}>
                <CartLogo />
              </button>
            </li>
            {USER_LIST &&
              USER_LIST.map((i) => (
                <li key={i.id}>
                  <button onClick={() => router(i.href)}>{i.title}</button>
                </li>
              ))}
          </ul>
        </nav>
      </article>
      <Drawer isOpen={headerValue} close={onChangeHeaderValue} />
      <OrderModal open={isModalOpen} close={closeModal} />
    </header>
  );
}
