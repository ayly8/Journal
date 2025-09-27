import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import EntryModal from '../components/EntryModal'
import EditModal from '../components/EditModal'
import DeleteModal from '../components/DeleteModal'
import '../css/dashboard.css'
import journaling from '../assets/journaling.png'

function Dashboard() {
  const [loading, setLoading] = useState(true);
  const [currentUser, setCurrentUser] = useState('');
  const [loggedIn, setLoggedIn] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [entries, setEntries] = useState([]);
  const navigate = useNavigate();
  const [editingEntry, setEditingEntry] = useState(null);
  const [deletingEntry, setDeletingEntry] = useState(null);

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
        setCurrentUser(data.username);
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
  }, [navigate]); 

  useEffect(() => {
    if (!loggedIn) return; 

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
  }, [loggedIn]); 

  if (loading) return <div>Loading...</div>;

  return (
    <div className="dashboard-container">
      <div className="dashboard-header">
        <div>
          <img id="journal-icon" src={journaling} alt="journal icon" loading="lazy"/>
        </div>
        <div>
          <p className="dashboard-description">
            Welcome to MyJournal! This is your personal space to write, reflect, and keep your thoughts organized.
            Create an entry to get started.
          </p>
          <button className="createentry-btn" onClick={() => setShowModal(true)}>
            Create Entry
          </button>
          {showModal && (
            <EntryModal
              onClose={() => setShowModal(false)}
              currentUser={currentUser}
              onCreate={(newEntry) => {
                setEntries([...entries, newEntry]);
                setShowModal(false);
              }}
            />
          )}
        </div>
      </div>
      <div className="entries-grid">
        {entries.length > 0 ? (
          entries.map((userEntry) => (
            <div key={userEntry.id} className="entry-card">
              <h3>{userEntry.title}</h3>
              <p>{userEntry.entry}</p>
              <p>{userEntry.dateSelected}</p>
              <div className="edit-btns">
                <button className="editentry-btn" onClick={() => setEditingEntry(userEntry)}>
                  Edit Entry
                </button>
                <button className="deleteentry-btn" onClick={() => setDeletingEntry(userEntry)}>
                  Delete Entry
                </button>
              </div>
            </div>
          ))
        ) : (
          <p>No entries yet. Create one to get started!</p>
        )}

        {editingEntry && (
          <EditModal 
            entry={editingEntry}
            onClose={() => setEditingEntry(null)}
            onUpdate={(updatedEntry) => {
              setEntries(entries.map(e => e.id === updatedEntry.id ? updatedEntry : e));
              setEditingEntry(null);
            }}
          />
        )}

        {deletingEntry && (
          <DeleteModal 
            entry={deletingEntry} 
            onClose={() => setDeletingEntry(null)} 
            onDelete={(id) => {
              setEntries(entries.filter(e => e.id !== id));
              setDeletingEntry(null);
            }}
          />
        )}

      </div>
    </div>
  );
}

export default Dashboard;
