import { useState } from "react";
import { useNavigate } from "react-router-dom";
import PropTypes from "prop-types";
import "../css/form.css";
import { API_BASE_URL } from '../Url';

// this component is for the login/register sliding window form
function Form({ setIsLoggedIn, setCurrentUser }) {
   const [isActive, setIsActive] = useState(false);
   const [name, setName] = useState("");
   const [email, setEmail] = useState("");
   const [password, setPassword] = useState("");
   const [userName, setUserName] = useState("");
   const [userPass, setUserPass] = useState("");

   const navigate = useNavigate();

   // signup and signin button states for which side is active
   const handleSignUpClick = () => {
      setIsActive(true);
      console.log("Sign Up Clicked! isActive:", true);
   };

   const handleSignInClick = () => {
      setIsActive(false);
      console.log("Sign In Clicked! isActive:", false);
   };

   // submit button logic
   const handleSubmit = async (endpoint) => {
      try {
         // if endpont is register, send user reponse to db and store new user
         // if endpoint is login, fetch user details from db and login if user exists
         const body =
            endpoint === "register"
               ? { username: name, email, password }
               : { username: userName, password: userPass };

         const response = await fetch(`${API_BASE_URL}/api/auth/${endpoint}`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            credentials: "include",
            body: JSON.stringify(body),
         });

         // if login works, take user to dashboard otherwise show user the error msg
         if (response.ok) {
            const userRes = await fetch(`${API_BASE_URL}/api/auth/me`, {
               credentials: "include",
            });

            if (userRes.ok) {
               const { username } = await userRes.json();
               setIsLoggedIn(true);
               setCurrentUser(username);
               navigate("/dashboard");
            }
         } else {
            alert("Login/Register failed");
         }
      } catch (err) {
            console.error("Error occurred!", err);
      }
   };

   return (
      <div className="form-wrapper">
         <div className={`container ${isActive ? "right-panel-active" : ""}`}>
            <div className="form-container signUp-container">
               <div className="form">
                  <h1>Sign Up</h1>
                  <input
                     type="text"
                     placeholder="Name"
                     value={name}
                     onChange={(e) => setName(e.target.value)}/>
                  <input
                     type="email"
                     placeholder="Email"
                     value={email}
                     onChange={(e) => setEmail(e.target.value)}/>
                  <input
                     type="password"
                     placeholder="Password"
                     value={password}
                     onChange={(e) => setPassword(e.target.value)}/>
                  <button className="register-btn" onClick={() => handleSubmit("register")}>Sign Up</button>
               </div>  
            </div>
            <div className="form-container signIn-container">
               <div className="form">
                  <h1>Sign In</h1>
                  <input
                     type="text"
                     placeholder="Username"
                     value={userName}
                     onChange={(e) => setUserName(e.target.value)}/>
                  <input
                     type="password"
                     placeholder="Password"
                     value={userPass}
                     onChange={(e) => setUserPass(e.target.value)}/>
                  <button className="signin-btn" onClick={() => handleSubmit("login")}>Sign In</button>
               </div>
            </div>
            <div className="overlay-container">
               <div className="overlay">
                  <div className="overlay-panel overlay-left">
                     <h1>Welcome back!</h1>
                     <p>Already have an account? Please sign in with your personal information.</p>
                     <button className="ghost" id="signIn" onClick={handleSignInClick}>Sign In</button>  
                  </div>
                  <div className="overlay-panel overlay-right">
                     <h1>Hello friend!</h1>
                     <p>Want to keep track of your memories? Sign up to get started today.</p>
                     <button className="ghost" id="signUp" onClick={handleSignUpClick}>Sign Up</button>
                  </div>
               </div>
            </div>
         </div>
      </div>
   );
}

// specify what type of variable is required for each prop
Form.propTypes = {
  setIsLoggedIn: PropTypes.func.isRequired,
  setCurrentUser: PropTypes.func.isRequired,
};

export default Form;
