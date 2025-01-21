import React from "react";
import { useNavigate } from "react-router-dom";

import { getJwt } from "../../../constant/project";
import DrawerContents from "./contents/DrawerContents";

import { UserService } from "../../../service/UserService";

import "./Drawer.css";

const Drawer = ({ isOpen, close }) => {
  const isUser = getJwt();

  const router = useNavigate();

  const goToPage = async (id) => {
    if (id) {
      router(id);
      close();
    } else {
      await UserService.logOut().then(() => {
        window.location.reload();
      });
    }
  };

  return (
    <nav className={`drawer-wrap ${isOpen && "isOpen"}`}>
      <DrawerContents isUser={isUser} onClick={goToPage} />
    </nav>
  );
};

export default Drawer;
