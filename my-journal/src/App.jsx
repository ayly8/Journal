import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { useState, useEffect } from "react";
import NavBar from './components/NavBar.jsx';
import Tutorial from './components/Tutorial.jsx'
import Form from './components/Form.jsx'
import Dashboard from './pages/Dashboard.jsx'
import { API_BASE_URL } from "./Url.jsx";

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [currentUser, setCurrentUser] = useState("");

  // check if user is already logged in or not for navbar component
  useEffect(() => {
    fetch(`${API_BASE_URL}/api/auth/me`, {
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

        {/* if user is not logged in, show homepage content */}
        {!isLoggedIn ? (
          <>
            <p id="welcome">Welcome to MyJournal, get started&nbsp;<a id="link" href="#form-id">now</a>!</p>
            <Tutorial
              title="Create Entry"
              desc="Add journal entries to stay organized."
            />
            <Tutorial
              title="Edit Entry"
              desc="Made a mistake? Update journal entries as needed."
            />
            <Tutorial
              title="Delete Entry"
              desc="Don't want an entry anymore? Delete journal entries as needed."
            />
            <p id="form-id"></p>
            <Form 
              setIsLoggedIn={setIsLoggedIn}
              setCurrentUser={setCurrentUser}
            />
          </>
        ) : (
          // if user is logged in show the dashboard page
          <Routes>
            <Route
              path="/dashboard"
              element={<Dashboard isLoggedIn={isLoggedIn} currentUser={currentUser} />} 
            />
          </Routes>
        )}

      </Router>
    </>
  )
}

export default App
