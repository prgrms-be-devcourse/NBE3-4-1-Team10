import React, { useRef, useState } from "react";
import "./Login.css";

const Login = () => {
  const emailRef = useRef(null);
  const pwdRef = useRef(null);
  const loginFormFields = [
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
  ];
  const [body, setBody] = useState({ email: "", pwd: "" });
  const [message, setMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const onChangeInput = (name, e) => {
    setBody((prev) => ({ ...prev, [name]: e }));
  };

  const handleLogin = async (e) => {
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
    if (body.id === "admin" && body.pwd === "password123") {
      alert("로그인 성공");
      window.location.replace("/");
      setMessage("");
    } else {
      setMessage("아이디 또는 비밀번호가 잘못되었습니다.");
    }
  };

  return (
    <div className='login-container'>
      <section className='login-wrap'>
        <form className='login-form' onSubmit={handleLogin}>
          <h2 className='login-title'>Login</h2>
          {loginFormFields.map(
            ({ id, label, name, type, placeholder, ref }) => (
              <div className='login-form-group' key={id}>
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
            )
          )}
          {message && <p className='error-message'>{message}</p>}
          <button className='button' type='submit'>
            로그인
          </button>
        </form>
      </section>
    </div>
  );
};

export default Login;
