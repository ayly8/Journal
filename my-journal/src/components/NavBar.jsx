import { useNavigate } from "react-router-dom"
import PropTypes from "prop-types";

function NavBar({ isLoggedIn, setIsLoggedIn, currentUser, setCurrentUser }) {
   const navigate = useNavigate();

   const handleLogout = async () => {
      try {
         const res = await fetch("/api/auth/logout", {
            method: "POST",
            credentials: "include",
         });
         if (res.ok) {
            setIsLoggedIn(false);
            setCurrentUser("");
            navigate("/");
         } else {
            alert("Logout failed.");
         }
      } catch (err) {
         console.error("Logout error:", err);
      }
   };

   return (
      <nav id="nav">
      <h1 id="journal-title">
         {isLoggedIn ? `Welcome, ${currentUser}!` : "My Journal App!"}
      </h1>
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
  currentUser: PropTypes.string.isRequired,
  setCurrentUser: PropTypes.func.isRequired,
};

export default NavBar
