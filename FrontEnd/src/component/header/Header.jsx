import React, { useState } from "react";

import Logo from "../logo/Logo";
import Drawer from "../drawer/Drawer";
import DrawerBtn from "./MHeader/drawerBtn/DrawerBtn";

import "./Header.css";

export default function Header() {
  const [isDrawerOpen, setIsDrawerOpen] = useState(false);

  const onChangeDrawerOpen = () => {
    setIsDrawerOpen((prev) => !prev);
  };

  return (
    <header id='header'>
      <Logo />
      <DrawerBtn isOpen={isDrawerOpen} onClick={onChangeDrawerOpen} />
      <Drawer isOpen={isDrawerOpen} close={onChangeDrawerOpen} />
    </header>
  );
}
