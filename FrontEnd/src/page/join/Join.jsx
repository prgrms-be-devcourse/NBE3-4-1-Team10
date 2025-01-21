import React, { useRef, useState } from "react";

import Msg from "../../component/msg/Msg";
import { FORM_FIELD } from "../../constant/formFields";
import { UserService } from "../../service/UserService";

import "./Join.css";

const Join = () => {
  const emailRef = useRef(null);
  const pwdRef = useRef(null);
  const pwdCheckRef = useRef(null);
  const nicknameRef = useRef(null);

  const [body, setBody] = useState({
    email: "",
    pwd: "",
    pwdCheck: "",
    nickname: "",
  });

  const [message, setMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const joinFormFields = [
    {
      id: "email",
      label: "아이디",
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
    {
      id: "pwdCheck",
      label: "비밀번호 확인",
      name: "pwdCheck",
      type: "password",
      placeholder: "비밀번호를 확인하세요",
      ref: pwdCheckRef,
    },
    {
      id: "nickname",
      label: "닉네임",
      name: "nickname",
      type: "text",
      placeholder: "닉네임을 입력하세요",
      ref: nicknameRef,
    },
  ];

  const onChangeInput = (name, e) => {
    setBody((prev) => ({ ...prev, [name]: e }));
  };

  const handleKeyDown = (e) => {
    const keyCode = e?.keyCode;
    const Enter = 13;

    if (keyCode === Enter) {
      onClickJoin();
    }
  };
  const handleWarn = (ref, timeout = 2000) => {
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

    if (!body.pwdCheck) {
      setMessage("비밀번호 확인을 입력해 주세요.");
      handleWarn(pwdCheckRef);
      return false;
    }

    if (body.pwd !== body.pwdCheck) {
      setMessage("비밀번호가 서로 일치하지 않습니다.");
      handleWarn(pwdRef);
      handleWarn(pwdCheckRef, 3000);
      return false;
    }

    if (!body.nickname) {
      setMessage("닉네임을 입력해 주세요.");
      handleWarn(nicknameRef, 3000);
      return false;
    }

    return true;
  };

  const onClickJoin = async (e) => {
    e.preventDefault();
    setIsLoading(true);

    // 유효성 검사 후 처리
    if (!validateFields()) {
      setIsLoading(false);
      return;
    }

    try {
      const res = await UserService.signUp({
        email: body.email,
        password: body.pwd,
        nickname: body.nickname,
      });
      if (res?.status === 200) {
        alert("회원 가입 완료");
      }
    } catch (error) {
      alert(
        "회원가입 중 오류가 발생하였습니다. <br /> 잠시 후 다시 시도해주세요."
      );
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <section className='join-wrap'>
      <form className='join-form' onSubmit={onClickJoin}>
        <Msg type='title' text='Join' />

        {joinFormFields.map(({ id, label, name, type, placeholder, ref }) => (
          <div className='join-form-group'>
            <FORM_FIELD
              key={id}
              id={id}
              label={label}
              name={name}
              type={type}
              onKeyDown={handleKeyDown}
              placeholder={placeholder}
              value={body[name]}
              onChange={onChangeInput}
              ref={ref}
              isLoading={isLoading}
            />
          </div>
        ))}

        <Msg text={message} type='error' />
        <button className='button' type='submit' disabled={isLoading}>
          {isLoading ? "가입 중..." : "회원가입"}
        </button>
      </form>
    </section>
  );
};

export default Join;
