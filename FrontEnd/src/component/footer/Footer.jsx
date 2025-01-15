import React from "react";
import { Link } from "react-router-dom";
import { GitHubIcon } from "../../constant/Icon";
import "./Footer.css";

export default function Footer() {
  return (
    <footer id='footer'>
      <section className='footer-sub'>
        <h4 className='sub-btn'>
          <span>More Details </span>
          <span>about Team 10's project!</span>
        </h4>
      </section>
      <Link
        to='https://github.com/prgrms-be-devcourse/NBE3-4-1-Team10'
        target='_blank'>
        <GitHubIcon />
      </Link>
      <p className='copyright'>©2024, Team 10 All Rights Reserved.</p>
    </footer>
  );
}
