import React from "react";
import { Outlet } from "react-router-dom";

import Header from "../../component/header/Header";
import Footer from "../../component/footer/Footer";

import "./Layout.css";

export default function Layout() {
  return (
    <div className='layout'>
      <Header />
      <main>
        <Outlet />
      </main>
      <Footer />
    </div>
  );
}
