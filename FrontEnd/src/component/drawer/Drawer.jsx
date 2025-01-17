import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

import { Guest } from "./guest/Guest";
import { User } from "./user/User";
import OrderModal from "../orderModal/OrderModal";
import "./Drawer.css";

const Drawer = ({ isOpen, close }) => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const isLoggedIn = localStorage.getItem("isLoggedIn") === "true";
  const router = useNavigate();

  const openModal = () => {
    close();
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const goToPage = (id) => {
    close();
    router(id);
  };

  return (
    <nav className={`drawer-wrap ${isOpen && "isOpen"}`}>
      {isLoggedIn ? <User open={openModal} /> : <Guest onClick={goToPage} />}
      <OrderModal open={isModalOpen} close={closeModal} />
    </nav>
  );
};

export default Drawer;
