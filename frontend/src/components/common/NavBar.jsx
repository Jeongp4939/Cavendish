import { Link, useLocation } from "react-router-dom";
import "styles/css/common.css";

const main = "Cavendish";

const naviList = [
  { name: "recommend", to: "/recommend" },
  { name: "part", to: "/part" },
  { name: "board", to: "/board" },
  { name: "quotation", to: "/quotation" },
];

const userInfo = (isLogin) => {
  return isLogin
    ? [
        { name: "mypage", to: "/mypage" },
        { name: "logout", to: "/logout" },
      ]
    : [{ name: "login", to: "/login" }];
};

const Btn = ({ name, to, pathname }) => {
  const toggle = to === pathname ? "btn-active" : "btn-inactive";
  return (
    <Link to={to} className={`${toggle}`}>
      {name}
    </Link>
  );
};

const NavBar = () => {
  const location = useLocation();
  const pathname = location.pathname;
  const isLogin = false; // 향후 리덕스 상태변수로 토글할 예정
  const userInfoList = userInfo(isLogin);

  return (
    <>
      <div className="common-navbar">
        <span className="btn-title">{main}</span>
        {naviList.map((item) => {
          const props = {
            name: item.name,
            to: item.to,
            pathname,
          };
          return <Btn key={item.name} {...props} />;
        })}
        <div className="jc-end">
          {userInfoList.map((item) => {
            const props = {
              name: item.name,
              to: item.to,
              pathname,
            };
            return <Btn key={item.name} {...props}></Btn>;
          })}
        </div>
      </div>
    </>
  );
};

export default NavBar;