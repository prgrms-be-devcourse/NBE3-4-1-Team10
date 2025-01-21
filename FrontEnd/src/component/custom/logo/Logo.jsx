import React from "react";
import { Link } from "react-router-dom";

import "./Logo.css";

const Logo = () => {
  return (
    <Link to='/' aria-current='page'>
      <h1 className='Logo-title'>G&C</h1>
    </Link>
  );
};

export default Logo;
