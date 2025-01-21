import React from "react";

const FORM_FIELD = ({
  id,
  label,
  name,
  type,
  placeholder,
  value,
  onChange,
  ref,
  isLoading,
}) => {
  return (
    <>
      <label className='label' htmlFor={id}>
        {label}
        <input
          id={id}
          className='input'
          name={name}
          type={type}
          value={value}
          onChange={(e) => onChange(name, e.target.value)}
          placeholder={placeholder}
          disabled={isLoading}
          ref={ref}
        />
      </label>
    </>
  );
};

export { FORM_FIELD };
