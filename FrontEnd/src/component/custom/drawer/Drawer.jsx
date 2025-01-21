import React from "react";
import { useNavigate } from "react-router-dom";
import DrawerContents from "./contents/DrawerContents";
import { getJwt } from "../../../constant/project";

import "./Drawer.css";

const Drawer = ({ isOpen, close }) => {
  const isUser = getJwt();

  const router = useNavigate();

  const goToPage = (id) => {
    if (id) {
      router(id);
    } else {
    }
    close();
  };

  return (
    <nav className={`drawer-wrap ${isOpen && "isOpen"}`}>
      <DrawerContents isUser={isUser} onClick={goToPage} />
    </nav>
  );
};

export default Drawer;
