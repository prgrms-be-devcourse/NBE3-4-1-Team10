import React, { useEffect } from "react";

import { PlusIcon } from "../../../../constant/Icon";

import "./DrawerBtn.css";

const DrawerBtn = ({ isOpen, onClick }) => {
  useEffect(() => {
    if (isOpen) {
      document.body.style.overflow = "hidden";
    } else {
      document.body.style.overflow = "auto";
    }
    return () => {
      document.body.style.overflow = "auto";
    };
  }, [isOpen]);

  return (
    <button
      className={`drawer-btn-wrap ${isOpen && "isOpen"} hide-tablet hide-pc`}
      onClick={onClick}
      aria-current={isOpen}>
      <figure className={`drawer-btn ${isOpen && "isOpen"}`}>
        <PlusIcon />
      </figure>
    </button>
  );
};

export default DrawerBtn;
