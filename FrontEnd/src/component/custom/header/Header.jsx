import React, { useEffect, useState } from "react";

import Logo from "../logo/Logo";
import Drawer from "../drawer/Drawer";
import DrawerBtn from "./drawerBtn/DrawerBtn";

import "./Header.css";

export default function Header() {
  const [isScrolled, setIsScrolled] = useState(false);
  const [isDrawerOpen, setIsDrawerOpen] = useState(false);

  const onChangeDrawerOpen = () => {
    setIsDrawerOpen((prev) => !prev);
  };

  // 스크롤 이벤트 핸들러
  const handleScroll = () => {
    if (window.scrollY > 50) {
      // 50px 이상 스크롤 시
      setIsScrolled(true);
    } else {
      setIsScrolled(false);
    }

    // 스크롤이 멈춘 후 일정 시간 동안 상태를 원상태로 되돌리기 위한 타이머
    if (timeoutRef.current) {
      clearTimeout(timeoutRef.current); // 이전 타이머를 취소
    }

    // 스크롤이 멈춘 후 200ms 뒤에 상태를 원상태로
    timeoutRef.current = setTimeout(() => {
      setIsScrolled(false);
    }, 400); // 200ms 동안 스크롤이 멈추면 원상태로 돌아감
  };

  // 타이머를 참조하기 위한 ref
  const timeoutRef = React.useRef(null);

  // 스크롤 이벤트 등록
  useEffect(() => {
    window.addEventListener("scroll", handleScroll);

    // 컴포넌트 언마운트 시 이벤트 리스너 정리
    return () => {
      window.removeEventListener("scroll", handleScroll);
      if (timeoutRef.current) {
        clearTimeout(timeoutRef.current); // 타이머 클리어
      }
    };
  }, []);

  return (
    <header id='header' className={isScrolled ? "scrolled" : ""}>
      <Logo />
      <DrawerBtn isOpen={isDrawerOpen} onClick={onChangeDrawerOpen} />
      <Drawer isOpen={isDrawerOpen} close={onChangeDrawerOpen} />
    </header>
  );
}
