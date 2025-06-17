import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

function Dashboard() {
  const [loading, setLoading] = useState(true);
  const [authorized, setAuthorized] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    fetch("http://localhost:8080/api/auth/me", {
      credentials: "include",
    })
      .then((res) => {
        if (res.ok) {
          setAuthorized(true);
        } else {
          navigate("/"); // Not logged in, go to login page
        }
      })
      .catch(() => {
        navigate("/"); // Error = assume unauthorized
      })
      .finally(() => setLoading(false));
  }, []);

  const handleLogout = async () => {
    try {
      const res = await fetch("http://localhost:8080/api/auth/logout", {
        method: "POST",
        credentials: "include",
      });
      if (res.ok) {
        navigate("/");
      } else {
        alert("Logout failed.");
      }
    } catch (err) {
      console.error("Logout error:", err);
    }
  };

  if (loading) return <div>Loading...</div>;

  return (
    <div>
      <h1>Dashboard</h1>
      <p>You are logged in!</p>
      <button onClick={handleLogout}>Logout</button>
    </div>
  );
}

export default Dashboard;
