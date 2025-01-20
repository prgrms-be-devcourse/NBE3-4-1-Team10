import React, { useRef, useState } from "react";
import "./Join.css";
import { UserService } from "../../service/UserService";

const Join = () => {
  const emailRef = useRef(null);
  const pwdRef = useRef(null);
  const pwdCheckRef = useRef(null);
  const nicknameRef = useRef(null);
  const joinFormFields = [
    {
      id: "email",
      label: "아이디",
      name: "email",
      type: "text",
      placeholder: "아이디를 입력하세요",
      ref: emailRef,
    },
    {
      id: "pwd",
      label: "비밀번호",
      name: "pwd",
      type: "password",
      placeholder: "비밀번호를 입력하세요",
      ref: pwdRef,
    },
    {
      id: "pwdCheck",
      label: "비밀번호 확인",
      name: "pwdCheck",
      type: "password",
      placeholder: "비밀번호를 입력하세요",
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
      emailRef.current.focus();
      return false; // 실패시 false 반환
    }

    if (!body.pwd) {
      setMessage("비밀번호를 입력해 주세요.");
      pwdRef.current.classList.add("warn");
      setTimeout(() => pwdRef.current.classList.remove("warn"), 2000);
      setIsLoading(false);
      pwdRef.current.focus();
      return false; // 실패시 false 반환
    }

    if (!body.pwdCheck) {
      setMessage("비밀번호 확인을 입력해 주세요.");
      pwdCheckRef.current.classList.add("warn");
      setTimeout(() => pwdCheckRef.current.classList.remove("warn"), 2000);
      setIsLoading(false);
      pwdCheckRef.current.focus();
      return false; // 실패시 false 반환
    }

    if (body.pwd !== body.pwdCheck) {
      setMessage("비밀번호가 서로 일치하지 않습니다.");
      pwdRef.current.classList.add("warn");
      pwdCheckRef.current.classList.add("warn");
      setTimeout(() => pwdRef.current.classList.remove("warn"), 2000);
      setTimeout(() => pwdCheckRef.current.classList.remove("warn"), 2000);
      setIsLoading(false);
      pwdRef.current.focus();
      return false; // 실패시 false 반환
    }

    if (!body.nickname) {
      setMessage("닉네임을 입력해 주세요.");
      nicknameRef.current.classList.add("warn");
      setTimeout(() => nicknameRef.current.classList.remove("warn"), 2000);
      setIsLoading(false);
      nicknameRef.current.focus();
      return false; // 실패시 false 반환
    }

    // 모든 validation이 통과하면 true 반환
    return true;
  };

  const onClickJoin = async (e) => {
    // handleJoin에서 true 반환이 오지 않으면 가입을 진행하지 않음
    if (await handleJoin(e)) {
      setIsLoading(true);

      try {
        const res = await UserService.signup({
          email: body.email,
          password: body.pwd,
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
    }
  };

  console.log(body);

  return (
    <div className='join-container'>
      <section className='join-wrap'>
        <form className='join-form' onSubmit={onClickJoin}>
          <h2 className='join-title'>Join</h2>

          {joinFormFields.map(({ id, label, name, type, placeholder, ref }) => (
            <div className='join-form-group' key={id}>
              <label className='label' htmlFor={id}>
                {label}
              </label>
              <input
                id={id}
                className='input'
                name={name}
                type={type}
                value={body?.[name]}
                onChange={(e) => onChangeInput(name, e.target.value)}
                placeholder={placeholder}
                disabled={isLoading}
                ref={ref}
              />
            </div>
          ))}

          {message && <p className='error-message'>{message}</p>}
          <button className='button' type='submit'>
            회원가입
          </button>
        </form>
      </section>
    </div>
  );
};

export default Join;
