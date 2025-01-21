import React, { useRef, useState } from "react";

import { Link, useNavigate } from "react-router-dom";

import Msg from "../../component/msg/Msg";
import Alert from "../../component/alert/Alert";

import { FORM_FIELD } from "../../constant/formFields";
import { UserService } from "../../service/UserService";

import "./Login.css";

const Login = () => {
  const emailRef = useRef(null);
  const pwdRef = useRef(null);
  const router = useNavigate();

  const loginFormFields = [
    {
      id: "email",
      label: "이메일",
      name: "email",
      type: "text",
      placeholder: "example@example.com",
      ref: emailRef,
    },
    {
      id: "pwd",
      label: "비밀번호",
      name: "pwd",
      type: "password",
      placeholder:
        "비밀번호, 대문자+소문자 조합, 특수문자 + 숫자 조합, 연속된 문자 3개이상 금지",
      ref: pwdRef,
    },
  ];

  const [body, setBody] = useState({ email: "", pwd: "" });
  const [message, setMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const onChangeInput = (name, e) => {
    setBody((prev) => ({ ...prev, [name]: e }));
  };

  // 경고 클래스 처리
  const handleWarn = (ref, timeout = 3000) => {
    ref.current.classList.add("warn");
    setTimeout(() => ref.current.classList.remove("warn"), timeout);
  };
  const validateFields = () => {
    if (!body.email) {
      setMessage("아이디를 입력해 주세요.");
      handleWarn(emailRef);
      return false;
    }
    if (!body.pwd) {
      setMessage("비밀번호를 입력해 주세요.");
      handleWarn(pwdRef);
      return false;
    }
    return true;
  };
  const handleKeyDown = (e) => {
    const keyCode = e?.keyCode;
    const Enter = 13;

    if (keyCode === Enter) {
      onClickLogin();
    }
  };

  const onClickLogin = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    if (!validateFields()) {
      setIsLoading(false);
      return;
    }
    try {
      const res = await UserService.signIn({
        username: body.email,
        password: body.pwd,
      });
      if (!res) {
        Alert("아이디와 비밀번호를 \n 정확히 입력해 주세요.", "", "", () =>
          setIsLoading(false)
        );
      } else {
        router("/");
      }
    } catch (error) {
      Alert("로그인에 실패했습니다.", "", "", () => setIsLoading(false));
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <section className='login-wrap'>
      <form className='login-form' onSubmit={onClickLogin}>
        <Msg type='title' text='Sign In' />

        {loginFormFields.map(({ id, label, name, type, placeholder, ref }) => (
          <FORM_FIELD
            key={id}
            id={id}
            label={label}
            name={name}
            type={type}
            placeholder={placeholder}
            value={body[name]}
            onKeyDown={handleKeyDown}
            onChange={onChangeInput}
            ref={ref}
            isLoading={isLoading}
          />
        ))}

        <Msg text={message} type='error' />
        <button className='button' type='submit' disabled={isLoading}>
          {isLoading ? "로그인 중..." : "로그인"}
        </button>
      </form>

      <Link to='/join'>
        <span>회원가입</span>
      </Link>
    </section>
  );
};

export default Login;
