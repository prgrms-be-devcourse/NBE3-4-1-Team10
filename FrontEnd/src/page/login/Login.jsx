import React, { useRef, useState } from "react";
import { FORM_FIELD } from "../../constant/formFields";
import { UserService } from "../../service/UserService";
import "./Login.css";

const Login = () => {
  const emailRef = useRef(null);
  const pwdRef = useRef(null);

  const loginFormFields = [
    {
      id: "email",
      label: "이메일",
      name: "email",
      type: "text",
      placeholder: "이메일을 입력하세요. (example@example.com)",
      ref: emailRef,
    },
    {
      id: "pwd",
      label: "비밀번호",
      name: "pwd",
      type: "password",
      placeholder: "비밀번호를 입력하세요.",
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

  // 로그인 처리
  const onClickLogin = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    // 유효성 검사 후 처리
    if (!validateFields()) {
      setIsLoading(false);
      return;
    }

    try {
      const res = await UserService.signIn({
        username: body.email,
        password: body.pwd,
      });
      if (res?.status === 200) {
        alert("로그인 완료");
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
    <section className='login-wrap'>
      <form className='login-form' onSubmit={onClickLogin}>
        <h2 className='login-title'>Sign In</h2>

        {loginFormFields.map(({ id, label, name, type, placeholder, ref }) => (
          <FORM_FIELD
            key={id}
            id={id}
            label={label}
            name={name}
            type={type}
            placeholder={placeholder}
            value={body[name]}
            onChange={onChangeInput}
            ref={ref}
            isLoading={isLoading}
          />
        ))}

        {message && <p className='error-message'>{message}</p>}
        <button className='button' type='submit' disabled={isLoading}>
          {isLoading ? "로그인 중..." : "로그인"}
        </button>
      </form>
    </section>
  );
};

export default Login;
