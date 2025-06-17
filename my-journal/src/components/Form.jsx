import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../css/form.css";

function Form() {
   const [isActive, setIsActive] = useState(false);
   const [name, setName] = useState("");
   const [email, setEmail] = useState("");
   const [password, setPassword] = useState("");

   const navigate = useNavigate();

   const handleSignUpClick = () => {
      setIsActive(true);
      console.log("Sign Up Clicked! isActive:", true);
   };

   const handleSignInClick = () => {
      setIsActive(false);
      console.log("Sign In Clicked! isActive:", false);
   };

   const handleSubmit = async (endpoint) => {
   try {
      let response;

      if (endpoint === "register") {
         response = await fetch("http://localhost:8080/api/auth/register", {
            method: "POST",
            headers: {
               "Content-Type": "application/json",
            },
            body: JSON.stringify({
               username: name, // assuming `name` is your username
               email,
               password,
            }),
            credentials: "include", // just in case cookies are sent
         });
      } else if (endpoint === "login") {
         response = await fetch("http://localhost:8080/api/auth/login", {
            method: "POST",
            headers: {
               "Content-Type": "application/json",
            },
               body: JSON.stringify({
               username: name,   // use `name` to match register form
               password,
            }),
            credentials: "include",
         });
      }

      if (response.ok) {
         const data = await response.text();
         console.log("Success:", data);
         navigate("/dashboard"); // Redirect after success
      } else {
         const errorText = await response.text();
         console.error("Failed:", errorText);
         alert("Login/Register failed: " + errorText);
      }
   } catch (error) {
      console.log("Error occurred!", error);
   }
};

   return (
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
               <button onClick={() => handleSubmit("register")}>Sign Up</button>
            </div>  
         </div>
         <div className="form-container signIn-container">
            <div className="form">
               <h1>Sign In</h1>
               <input
                  type="text"
                  placeholder="Username"
                  value={name}
                  onChange={(e) => setName(e.target.value)}/>
               <input
                  type="password"
                  placeholder="Password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}/>
               <button onClick={() => handleSubmit("login")}>Sign In</button>
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
   );
}

export default Form;
