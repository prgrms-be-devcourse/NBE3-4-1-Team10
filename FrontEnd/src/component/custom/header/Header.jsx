import React, { useEffect, useState } from "react";

import Logo from "../logo/Logo";
import Drawer from "../drawer/Drawer";
import DrawerBtn from "./drawerBtn/DrawerBtn";

import "./Header.css";
import { ProductService } from "../../../service/ProductService";
import Alert from "../../alert/Alert";

export default function Header() {
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
      console.log(res);
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
      <div
        style={{
          display: "flex",
          gap: "2rem",
          zIndex: "100000",
        }}>
        <button style={{ fontSize: "1rem" }} onClick={onClickCreatePost}>
          생성하기
        </button>
        <DrawerBtn isOpen={isDrawerOpen} onClick={onChangeDrawerOpen} />
      </div>
      <Drawer isOpen={isDrawerOpen} close={onChangeDrawerOpen} />
    </header>
  );
}
