import React, { useRef, useState } from "react";
import "./Join.css";

const Join = () => {
  const emailRef = useRef(null);
  const pwdRef = useRef(null);
  const pwdCheckRef = useRef(null);
  const [body, setBody] = useState({ email: "", pwd: "", pwdCheck: "" });
  const [message, setMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const onChangeInput = (name, e) => {
    setBody((prev) => ({ ...prev, [name]: e }));
  };

  const handleJoin = async (e) => {
    setIsLoading(true);
    e.preventDefault();
    if (!body?.email) {
      setMessage("아이디를 입력해 주세요.");
      setIsLoading(false);
      emailRef.current.classList.add("warn");
      setTimeout(() => emailRef.current.classList.remove("warn"), 2000);
      return emailRef.current.focus();
    }
    if (!body.pwd) {
      setMessage("비밀번호를 입력해 주세요.");
      pwdRef.current.classList.add("warn");
      setTimeout(() => pwdRef.current.classList.remove("warn"), 2000);
      setIsLoading(false);
      return pwdRef.current.focus();
    }
    if (!body.pwdCheck) {
      setMessage("비밀번호를 입력해 주세요.");
      pwdCheckRef.current.classList.add("warn");
      setTimeout(() => pwdCheckRef.current.classList.remove("warn"), 2000);
      setIsLoading(false);
      return pwdCheckRef.current.focus();
    }
    if (body.pwd !== body.pwdCheck) {
      setMessage("비밀번호가 서로 일치하지 않습니다.");
      pwdRef.current.classList.add("warn");
      pwdCheckRef.current.classList.add("warn");
      setTimeout(() => pwdRef.current.classList.remove("warn"), 2000);
      setTimeout(() => pwdCheckRef.current.classList.remove("warn"), 2000);
      setIsLoading(false);
      return pwdRef.current.focus();
    }
  };

  return (
    <div className='join-container'>
      <section className='join-wrap'>
        <form className='join-form' onSubmit={handleJoin}>
          <h2 className='join-title'>Join</h2>
          <div className='join-form-group'>
            <label className='join-label' htmlFor='email'>
              아이디
            </label>
            <input
              id='email'
              name='email'
              type='text'
              value={body?.email}
              onChange={(e) => onChangeInput("email", e.target.value)}
              placeholder='아이디를 입력하세요'
              disabled={isLoading}
              ref={emailRef}
            />
          </div>
          <div className='join-form-group'>
            <label className='join-label' htmlFor='pwd'>
              비밀번호
            </label>
            <input
              id='pwd'
              name='pwd'
              type='password'
              value={body?.pwd}
              onChange={(e) => onChangeInput("pwd", e.target.value)}
              placeholder='비밀번호를 입력하세요'
              disabled={isLoading}
              ref={pwdRef}
            />
          </div>
          <div className='join-form-group'>
            <label className='join-label' htmlFor='pwdCheck'>
              비밀번호 확인
            </label>
            <input
              id='pwd'
              name='pwd'
              type='password'
              value={body?.pwdCheck}
              onChange={(e) => onChangeInput("pwdCheck", e.target.value)}
              placeholder='비밀번호를 입력하세요'
              disabled={isLoading}
              ref={pwdCheckRef}
            />
          </div>

          {message && <p className='error-message'>{message}</p>}
          <button className='join-btn' type='submit'>
            회원가입
          </button>
        </form>
      </section>
    </div>
  );
};

export default Join;
