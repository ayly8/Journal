import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import PropTypes from 'prop-types';
import EntryModal from '../components/EntryModal'
import '../css/dashboard.css'

function Dashboard({ currentUser }) {
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();
  const [showModal, setShowModal] = useState(false);

  useEffect(() => {
    fetch("/api/auth/me", {
      credentials: "include",
    })
      .then((res) => {
        if (res.ok) {
          return res.text(); // will show username
        } else {
          throw new Error("Not logged in");
        }
      })
      .then((username) => {
        console.log("User session restored:", username);
      })
      .catch(() => {
        navigate("/"); // Error = assume unauthorized
      })
      .finally(() => setLoading(false));
  }, [navigate]);

  if (loading) return <div>Loading...</div>;

  return (
    <div>
      <h1>Dashboard</h1>
      <p>You are logged in!</p>
      <button className="createentry-btn" onClick={() => setShowModal(true)}>Create Entry</button>

      {showModal && (
        <EntryModal onClose={() => setShowModal(false)} currentUser={currentUser} />
      )}
    </div>
  );
}

Dashboard.propTypes = {
  currentUser: PropTypes.string.isRequired,
};

export default Dashboard;
