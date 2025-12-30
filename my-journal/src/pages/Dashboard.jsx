import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import EntryModal from '../components/EntryModal'
import EditModal from '../components/EditModal'
import DeleteModal from '../components/DeleteModal'
import '../css/dashboard.css'
import journaling from '../assets/journaling.png'
import { API_BASE_URL } from '../Url';

// this is the component for the user dashboard once they login
function Dashboard() {
  const [loading, setLoading] = useState(true);
  const [currentUser, setCurrentUser] = useState('');
  const [loggedIn, setLoggedIn] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [entries, setEntries] = useState([]);
  const navigate = useNavigate();
  const [editingEntry, setEditingEntry] = useState(null);
  const [deletingEntry, setDeletingEntry] = useState(null);

  // fetches current user and makes sure user is logged in to see the dashboard
  useEffect(() => {
    const fetchUser = async () => {
      try {
        const res = await fetch(`${API_BASE_URL}/api/auth/me`, {
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

  // fetches and displays the users entries from the db onto the dashboard
  useEffect(() => {
    if (!loggedIn) return; 

    const fetchDashboardData = async () => {
      try {
        const res = await fetch(`${API_BASE_URL}/api/entries`, {
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
          {/* entry popup modal shown when "create" button is clicked */}
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

      {/* each entry is displayed the same way to be uniform */}
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

        {/* edit and delete popup modals shown if corresponding buttons are clicked */}
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
