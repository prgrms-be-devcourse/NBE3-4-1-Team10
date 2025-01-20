import React from "react";

import { FOOTER_ICONS } from "../../constant/constantLists";

import "./Footer.css";

export default function Footer() {
  return (
    <footer
      id='footer'
      aria-label='Footer with GitHub link, and copyright information'>
      {/* 깃헙과 노션페이지 안내 */}
      <h4 className='footer-info'>More Details about Team 10's project !</h4>
      <hr />
      <ul className='footer-icon-wrap'>
        {FOOTER_ICONS.map(({ href, label, IconComponent }, index) => (
          <li>
            <a
              key={index}
              href={href}
              target='_blank'
              rel='noopener noreferrer'
              aria-label={label}>
              <IconComponent />
            </a>
          </li>
        ))}
      </ul>
      <p className='copyright'>
        &copy; {new Date().getFullYear()}, Team 10 All Rights Reserved.
      </p>
    </footer>
  );
}
