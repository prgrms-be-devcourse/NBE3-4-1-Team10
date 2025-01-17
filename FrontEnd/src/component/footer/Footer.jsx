import React from "react";
import { GitHubIcon } from "../../constant/Icon";
import "./Footer.css";

const GIT_LINK = "https://github.com/prgrms-be-devcourse/NBE3-4-1-Team10";

export default function Footer() {
  return (
    <footer
      id='footer'
      aria-label='Footer with GitHub link, and copyright information'>
      {/* 깃헙과 노션페이지 안내 */}
      <section className='footer-wrap'>
        <h4 className='footer-info'>
          <span>More Details </span>
          <span>about Team 10's project!</span>
        </h4>
        <hr />
        <a
          className='footer-icon'
          href={GIT_LINK}
          target='_blank'
          rel='noopener noreferrer'
          aria-label="Team 10's GitHub Repository">
          <GitHubIcon color='#ffffff' size='60' />
        </a>
        {/* 저작권 */}
        <p className='copyright'>
          &copy; {new Date().getFullYear()}, Team 10 All Rights Reserved.
        </p>
      </section>
    </footer>
  );
}
