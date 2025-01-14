import React from "react";
import { PRODUCTS } from "../product/Dummy";
import "./Drawer.css";

const Drawer = ({ isOpen, close }) => {
  //   const { data, loading, error } = useFetch("Todo...");
  //   // 로딩 상태, 오류 상태 처리
  //   if (loading) return <div>로딩 중...</div>;
  //   if (error) return <div>에러: {error}</div>;
  //   if (!data || data.length === 0) return <div>데이터가 없습니다.</div>;
  const handleClick = (id) => {
    close();
    const element = document.getElementById(id);
    if (element) {
      element.scrollIntoView({ behavior: "smooth" });
    }
  };

  return (
    <nav className={`drawer-wrap ${isOpen}`}>
      <article>
        <ul>
          {PRODUCTS &&
            PRODUCTS.map((i) => (
              <li key={i.id}>
                <button
                  onClick={() => {
                    handleClick(i.id);
                  }}
                  aria-label={`go to ${i.name}`}>
                  <h2>{i.name}</h2>
                </button>
              </li>
            ))}
        </ul>
      </article>
    </nav>
  );
};

export default Drawer;
