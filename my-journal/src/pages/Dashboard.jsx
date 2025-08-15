import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import EntryModal from '../components/EntryModal'
import '../css/dashboard.css'

function Dashboard() {
  const [loading, setLoading] = useState(true);
  const [currentUser, setCurrentUser] = useState('');
  const [loggedIn, setLoggedIn] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [entries, setEntries] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const res = await fetch("/api/auth/me", {
          credentials: "include",
        });

        if (!res.ok) {
          throw new Error(`HTTP error! status: ${res.status}`);
        }

        const data = await res.json();
        setCurrentUser(data.username); // or however you store it
        setLoggedIn(true);
      } catch (err) {
        console.error('Auth check failed:', err);
        setLoggedIn(false);
        navigate("/");
      } finally {
        setLoading(false);
      }
    };
    fetchUser();
  }, [navigate]); // runs only once

  useEffect(() => {
    if (!loggedIn) return; // Don't fetch until user confirmed

    const fetchDashboardData = async () => {
      try {
        const res = await fetch("/api/entries", {
          credentials: "include",
        });

        if (!res.ok) {
          throw new Error(`HTTP error! status: ${res.status}`);
        }

        const data = await res.json();
        setEntries(data);
      } catch (err) {
        console.error('Dashboard fetch failed:', err);
      }
    };

    fetchDashboardData();
  }, [loggedIn]); // only runs when user login status changes

  if (loading) return <div>Loading...</div>;

  return (
    <div className="dashboard-container">
      <div className="dashboard-header">
        <button className="createentry-btn" onClick={() => setShowModal(true)}>
          Create Entry
        </button>
        <p className="dashboard-description">
          Welcome to MyJournal! This is your personal space to write, reflect, and keep your thoughts organized.
        </p>
        {showModal && (
          <EntryModal onClose={() => setShowModal(false)} currentUser={currentUser} />
        )}
      </div>
      <div className="entries-grid">
        {entries.length > 0 ? (
          entries.map((userEntry) => (
            <div key={userEntry.id} className="entry-card">
              <h3>{userEntry.title}</h3>
              <p>{userEntry.entry}</p>
              <p>{userEntry.dateSelected}</p>
            </div>
          ))
        ) : (
          <p>No entries yet. Create one to get started!</p>
        )}
      </div>
    </div>
  );
}

export default Dashboard;
