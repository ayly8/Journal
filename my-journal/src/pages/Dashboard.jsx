import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import EntryModal from '../components/EntryModal'
import '../css/dashboard.css'

function Dashboard({ isLoggedIn, currentUser }) {
  //const [loading, setLoading] = useState(true);
  const navigate = useNavigate();
  const [showModal, setShowModal] = useState(false);

  useEffect(() => {
    if (!isLoggedIn) {
      navigate("/");
    }
  }, [isLoggedIn, navigate]);

  // useEffect(() => {
  //   fetch("http://localhost:8080/api/auth/me", {
  //     credentials: "include",
  //   })
  //     .then((res) => {
  //       if (res.ok) {
  //         return;
  //       } else {
  //         navigate("/"); // Not logged in, go to login page
  //       }
  //     })
  //     .catch(() => {
  //       navigate("/"); // Error = assume unauthorized
  //     })
  //     .finally(() => setLoading(false));
  // }, [navigate]);

  // if (loading) return <div>Loading...</div>;

  return (
    <div>
      <h1>Dashboard</h1>
      <p>You are logged in!</p>
      <button className="createentry-btn" onClick={() => setShowModal(true)}>Create Entry</button>

      {showModal && (
        <EntryModal onClose={() => setShowModal(false)} />
      )}
    </div>
  );
}

export default Dashboard;
