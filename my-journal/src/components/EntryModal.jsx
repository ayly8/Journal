import {useState} from 'react'
import PropTypes from 'prop-types';
import { Calendar } from 'primereact/calendar';
import '../css/entrymodal.css'

function EntryModal({ currentUser, onClose, onCreate }) {
   const [title, setTitle] = useState("");
   const [date, setDate] = useState(null);
   const [content, setContent] = useState("");

   const handleSubmit = async() => {
      console.log("submit clicked");
      const selectedDate = date ? date.toISOString().split('T')[0] : null;  // "YYYY-MM-DD"

      const newEntry = {
         title: title,
         entry: content,
         dateSelected: selectedDate,
         userId: currentUser?.id,
      };

      try {
         const response = await fetch("/api/entries", {
            method: "POST",
            headers: {
               "Content-Type": "application/json",
            },
            credentials: "include", 
            body: JSON.stringify(newEntry),
         });

         if (response.ok) {
            const createdEntry = await response.json();
            onCreate(createdEntry)
            console.log("Entry created!");

            setTitle("");
            setDate(null);
            setContent("");
         } else {
            const errorText = await response.text();
            console.error("Failed to create entry. Status:", response.status, errorText);
         }
      } catch (error) {
         console.error("Error submitting entry:", error);
      }
   };

   return (
      <>
         <div className="entrymodal-overlay">
            <div className="entrymodal-container"> 
               <div className="entrymodal-top">
                  <h2>Create New Entry</h2>
                  <button className="exit-btn" onClick={onClose}>X</button>
               </div>
               <div className="entrymodal-mid">
                  <input
                     className="modal-input"
                     type="text"
                     placeholder="Title..."
                     value={title}
                     onChange={(e) => setTitle(e.target.value)}/>
                  <Calendar
                     className="modal-calendar"
                     placeholder="Select Date"
                     value={date}
                     onChange={(e) => setDate(e.value)}
                     dateFormat="mm/dd/yy"
                     showIcon/>
               </div>           
               <textarea
                  className="modal-textarea"
                  rows={5}
                  placeholder="Write something here..."
                  value={content}
                  onChange={(e) => setContent(e.target.value)}/>
               <button className="create-btn" onClick={handleSubmit}>Create</button>
            </div>
         </div>
      </>
   );
}

EntryModal.propTypes = {
   currentUser: PropTypes.object,
   onClose: PropTypes.func.isRequired,
   onCreate: PropTypes.func.isRequired,
};

export default EntryModal;