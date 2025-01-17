const CartLogo = () => {
  return (
    <svg
      xmlns='http://www.w3.org/2000/svg'
      width='30'
      height='30'
      viewBox='0 0 24 24'
      fill='none'
      stroke='#01242d'
      strokeWidth='2'
      strokeLinecap='round'
      strokeLinejoin='round'>
      <path d='M6 6h15l-1.68 9H7.68L6 6zM6 6L5 3H1' />
      <circle cx='10' cy='20' r='2' />
      <circle cx='18' cy='20' r='2' />
    </svg>
  );
};

const GitHubIcon = ({ color, size }) => {
  return (
    <svg
      xmlns='http://www.w3.org/2000/svg'
      width={`${size}`}
      height={`${size}`}
      viewBox='0 0 30 30'>
      <path
        fill={`${color}`}
        d='M15,3C8.373,3,3,8.373,3,15c0,5.623,3.872,10.328,9.092,11.63C12.036,26.468,12,26.28,12,26.047v-2.051 c-0.487,0-1.303,0-1.508,0c-0.821,0-1.551-0.353-1.905-1.009c-0.393-0.729-0.461-1.844-1.435-2.526 c-0.289-0.227-0.069-0.486,0.264-0.451c0.615,0.174,1.125,0.596,1.605,1.222c0.478,0.627,0.703,0.769,1.596,0.769 c0.433,0,1.081-0.025,1.691-0.121c0.328-0.833,0.895-1.6,1.588-1.962c-3.996-0.411-5.903-2.399-5.903-5.098 c0-1.162,0.495-2.286,1.336-3.233C9.053,10.647,8.706,8.73,9.435,8c1.798,0,2.885,1.166,3.146,1.481C13.477,9.174,14.461,9,15.495,9 c1.036,0,2.024,0.174,2.922,0.483C18.675,9.17,19.763,8,21.565,8c0.732,0.731,0.381,2.656,0.102,3.594 c0.836,0.945,1.328,2.066,1.328,3.226c0,2.697-1.904,4.684-5.894,5.097C18.199,20.49,19,22.1,19,23.313v2.734 c0,0.104-0.023,0.179-0.035,0.268C23.641,24.676,27,20.236,27,15C27,8.373,21.627,3,15,3z'></path>
    </svg>
  );
};

const PlusIcon = () => {
  return (
    <svg
      xmlns='http://www.w3.org/2000/svg'
      width='14'
      height='14'
      viewBox='0 0 13 13'
      fill='none'>
      <path
        fill='#ffffff'
        fillRule='evenodd'
        clipRule='evenodd'
        d='M8.59567 6.09631V7.09631H12.5958V6.09631H8.59567ZM7.09575 8.59612L6.09575 8.59612L6.09575 12.5962L7.09575 12.5962L7.09575 8.59612ZM7.09575 0.596203L7.09575 4.59612L6.09575 4.59612L6.09575 0.596203L7.09575 0.596203ZM4.59567 6.09631L0.595756 6.09631L0.595756 7.09631L4.59567 7.09631L4.59567 6.09631Z'
      />
    </svg>
  );
};

const LogoIcon = (color) => {
  return (
    <svg
      xmlns='http://www.w3.org/2000/svg'
      width='35'
      height='35'
      viewBox='0 0 225 225'>
      <path
        fill={`${color}` || "#FFFFFF"}
        stroke={`${color}` || "#FFFFFF"}
        d='M121.3 24c-1.2.5-2.7 1.9-3.3 3.1-.7 1.2-5.7 31.2-11.1 66.8-5.4 35.5-11.1 72.5-12.6 82.1-1.5 9.9-2.3 18.7-2 20.2 1.5 5.8 11 7.3 14.3 2.3 1-1.5 5-25.2 11.5-67.2 5.4-35.6 11.1-72.7 12.6-82.3 1.5-9.9 2.3-18.7 2-20.2-.4-1.6-2-3.4-3.7-4.3-3.3-1.7-4.5-1.8-7.7-.5zM56.8 50.1C53.2 52.2 1 108.2.4 110.6c-.4 1.4-.2 3.6.4 4.9 1.5 3.3 54 58.4 56.9 59.8 5.2 2.3 12.1-3.4 10.9-9-.5-2.1-8.2-11-24.6-28.4l-24-25.3 3.3-3.6c1.8-1.9 11.4-12.1 21.2-22.5 9.9-10.5 19.2-20.4 20.7-22 1.5-1.7 3-4.5 3.4-6.2.5-2.7.1-3.7-2.4-6.3-3.1-3.1-6.3-3.7-9.4-1.9zM158.8 52c-2.5 2.6-2.9 3.6-2.4 6.3.4 1.7 1.9 4.5 3.4 6.2 1.5 1.6 10.8 11.5 20.7 22 9.8 10.4 19.4 20.6 21.2 22.5l3.3 3.6-24 25.4c-16.1 17.1-24.1 26.4-24.6 28.3-1.2 5.6 5.7 11.3 10.9 9 2.9-1.4 55.4-56.5 56.9-59.8.6-1.3.8-3.5.4-4.9-.6-2.5-53.1-58.7-56.6-60.6-3.2-1.7-6-1.1-9.2 2z'
      />
    </svg>
  );
};

const EditIcon = () => {
  return (
    <svg
      xmlns='http://www.w3.org/2000/svg'
      viewBox='0 0 24 24'
      width='24'
      height='24'>
      <path
        d='M14.06 2.94c-.39-.39-1.02-.39-1.41 0L4 10.59V15h4.41l8.65-8.65c.39-.39.39-1.02 0-1.41l-3.88-3.88c-.39-.39-1.02-.39-1.41 0z'
        fill='none'
        stroke='currentColor'
        stroke-width='2'
      />
    </svg>
  );
};
const DeleteIcon = () => {
  return (
    <svg
      xmlns='http://www.w3.org/2000/svg'
      viewBox='0 0 24 24'
      width='24'
      height='24'>
      <path
        d='M3 6h18'
        stroke='currentColor'
        stroke-width='2'
        fill='none'
        stroke-linecap='round'
        stroke-linejoin='round'
      />

      <path
        d='M5 6v14c0 .55.45 1 1 1h12c.55 0 1-.45 1-1V6H5z'
        stroke='currentColor'
        stroke-width='2'
        fill='none'
        stroke-linecap='round'
        stroke-linejoin='round'
      />

      <path
        d='M9 2h6'
        stroke='currentColor'
        stroke-width='2'
        fill='none'
        stroke-linecap='round'
        stroke-linejoin='round'
      />
    </svg>
  );
};

export { CartLogo, GitHubIcon, PlusIcon, LogoIcon, EditIcon, DeleteIcon };
