import React, { useEffect, useState } from "react";
import Logo from "../../logo/Logo";
import Drawer from "../../drawer/Drawer";
import { CartLogo, PlusIcon } from "../../../constant/Icon";
import "./MHeader.css";
import Modal from "../../modal/Modal";
import OrderModal from "../../orderModal/OrderModal";

const COLOR = "#FFFFFF";
const LOGO_SIZE = "35";

export default function MHeader() {
  const [headerValue, setHeaderValue] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const onChangeHeaderValue = () => {
    setHeaderValue((prev) => !prev);
  };

  useEffect(() => {
    if (headerValue) {
      document.body.style.overflow = "hidden";
    } else {
      document.body.style.overflow = "auto";
    }

    return () => {
      document.body.style.overflow = "auto";
    };
  }, [headerValue]);

  return (
    <React.Fragment>
      <article className='mobile-header-wrap'>
        <Logo size={LOGO_SIZE} color={COLOR} />
        <section className='mobile-header-icon'>
          <CartButton onClick={openModal} />
          <MenuButton isOpen={headerValue} onClick={onChangeHeaderValue} />
        </section>
      </article>
      <OrderModal open={isModalOpen} close={closeModal} />
      <Drawer isOpen={headerValue} close={onChangeHeaderValue} />
    </React.Fragment>
  );
}

const MenuButton = ({ isOpen, onClick }) => {
  return (
    <button
      className={`drawer-btn ${isOpen} hide-tablet hide-pc`}
      onClick={onClick}
      aria-current={isOpen}>
      <figure className={`drawer-btn-wrap ${isOpen}`}>
        <PlusIcon color={COLOR} size='14' />
      </figure>
    </button>
  );
};

const CartButton = ({ onClick }) => {
  return (
    <>
      <button className='cart-button' onClick={onClick}>
        <CartLogo />
      </button>
    </>
  );
};
