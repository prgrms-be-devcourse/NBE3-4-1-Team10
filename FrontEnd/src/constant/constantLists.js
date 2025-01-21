import { GitHubIcon, NotionIcon } from "./Icon";

const USER_LIST = [
  {
    id: 0,
    title: "Sign In",
    href: "/login",
  },
  {
    id: 1,
    title: "Join",
    href: "/join",
  },
];

const FOOTER_ICONS = [
  {
    id: 0,
    href: "https://github.com/prgrms-be-devcourse/NBE3-4-1-Team10",
    label: "Team 10's GitHub Repository",
    IconComponent: GitHubIcon,
  },
  {
    id: 1,
    href: "https://www.notion.so/10-6db32119e0ae48409434378435f69fca?pvs=32",
    label: "Team 10's Notion Repository",
    IconComponent: NotionIcon,
  },
];

export { FOOTER_ICONS, USER_LIST };
