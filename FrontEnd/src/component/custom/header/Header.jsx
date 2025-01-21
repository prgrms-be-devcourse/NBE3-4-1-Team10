import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import Logo from "../logo/Logo";
import Drawer from "../drawer/Drawer";
import DrawerBtn from "./drawerBtn/DrawerBtn";
import Alert from "../../alert/Alert";
import { ProductService } from "../../../service/ProductService";

import "./Header.css";
import { getJwt } from "../../../constant/project";

export default function Header() {
  const location = useLocation();
  const [isScrolled, setIsScrolled] = useState(false);
  const [isDrawerOpen, setIsDrawerOpen] = useState(false);

  const onChangeDrawerOpen = () => {
    setIsDrawerOpen((prev) => !prev);
  };

  const handleScroll = () => {
    if (window.scrollY > 50) {
      setIsScrolled(true);
    } else {
      setIsScrolled(false);
    }

    if (timeoutRef.current) {
      clearTimeout(timeoutRef.current);
    }

    timeoutRef.current = setTimeout(() => {
      setIsScrolled(false);
    }, 400);
  };

  const timeoutRef = React.useRef(null);

  useEffect(() => {
    window.addEventListener("scroll", handleScroll);

    return () => {
      window.removeEventListener("scroll", handleScroll);
      if (timeoutRef.current) {
        clearTimeout(timeoutRef.current);
      }
    };
  }, []);

  const onClickCreatePost = async () => {
    try {
      const res = await ProductService.postProductLists();

      if (res?.status === 200) {
        Alert("상품이 \n 생성되었습니다.", "", "", () =>
          window.location.reload()
        );
      }
    } catch {
      return;
    }
  };

  return (
    <header id='header' className={isScrolled ? "scrolled" : ""}>
      <Logo />
      <div className='header-nav-create-btn'>
        {location.pathname === "/" && getJwt() && (
          <button onClick={onClickCreatePost}>상품 생성하기</button>
        )}
        <DrawerBtn isOpen={isDrawerOpen} onClick={onChangeDrawerOpen} />
      </div>

      <Drawer isOpen={isDrawerOpen} close={onChangeDrawerOpen} />
    </header>
  );
}
