import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { useState, useEffect } from "react";
import NavBar from './components/NavBar.jsx';
import Form from './components/Form.jsx'
import Dashboard from './pages/Dashboard.jsx'

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [currentUser, setCurrentUser] = useState("");

  useEffect(() => {
    fetch("http://localhost:8080/api/auth/me", {
      credentials: "include",
    })
      .then(async (res) => {
        if (res.ok) {
          const user = await res.text();
          setCurrentUser(user);
          setIsLoggedIn(true);
        } else {
          setIsLoggedIn(false);
          setCurrentUser("");
        }
      })
      .catch(() => {
        setIsLoggedIn(false);
        setCurrentUser("");
      });
  }, []);

  return (
    <>
      <Router>
        <NavBar
          isLoggedIn={isLoggedIn}
          setIsLoggedIn={setIsLoggedIn}
          currentUser={currentUser}
          setCurrentUser={setCurrentUser} 
        />
        <Routes>
          <Route
            path="/"
            element={
              <Form
                setIsLoggedIn={setIsLoggedIn}
                setCurrentUser={setCurrentUser}
              />
            }
          />
          <Route
            path="/dashboard"
            element={
              <Dashboard
                isLoggedIn={isLoggedIn}
                currentUser={currentUser}
              />
            }
          />
        </Routes>
      </Router>
    </>
  )
}

export default App
