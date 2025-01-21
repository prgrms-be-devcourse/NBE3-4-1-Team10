import React from "react";
import { useNavigate } from "react-router-dom";

import { User } from "./user/User";
import { Guest } from "./guest/Guest";
import { getCookie } from "../../../constant/project";
import "./Drawer.css";

const Drawer = ({ isOpen, close }) => {
  const isLogined = getCookie("accessToken");

  const router = useNavigate();

  const goToPage = (id) => {
    close();
    router(id);
  };

  return (
    <nav className={`drawer-wrap ${isOpen && "isOpen"}`}>
      {isLogined ? <User onClick={goToPage} /> : <Guest onClick={goToPage} />}
    </nav>
  );
};

export default Drawer;
