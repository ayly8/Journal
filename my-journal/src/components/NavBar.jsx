import { Link, useNavigate } from "react-router-dom"
import PropTypes from 'prop-types';

function NavBar({isLoggedIn, setIsLoggedIn}) {
   const navigate = useNavigate();

   const handleLogout = async () => {
      await fetch("http://localhost:8080/api/auth/logout", {
         method: "POST",
         credentials: "include",
      });
      setIsLoggedIn(false);
      navigate("/");
   };

   return (
      <nav id="nav">
      <Link to="/" id="journal-title">My Journal App!</Link>
         <ul className="nav-links">
            {isLoggedIn && (
               <li>
                  <button onClick={handleLogout}>Logout</button>
               </li>
            )}
         </ul>
      </nav>
   );
}

NavBar.propTypes = {
   isLoggedIn: PropTypes.bool.isRequired,
   setIsLoggedIn: PropTypes.func.isRequired,
};

export default NavBar
