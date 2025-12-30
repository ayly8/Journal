import { useState } from "react";
import PropTypes from 'prop-types';
import { Calendar } from 'primereact/calendar';
import '../css/editmodal.css'
import { API_BASE_URL } from '../Url';

// this component is for edit entry popup modal
function EditModal({ entry, onClose, onUpdate }) {
   const [editTitle, setEditTitle] = useState(entry.title);
   const [editDate, setEditDate] = useState(entry.dateSelected ? new Date(entry.dateSelected) : new Date());
   const [editContent, setEditContent] = useState(entry.entry);

   // submit button logic
   const handleSubmit = async() => {
      // store updated entry response in a new variable
      const updatedEntry = {
         title: editTitle,
         entry: editContent,
         dateSelected: editDate.toISOString().split("T")[0], // "YYYY-MM-DD"
      };

      try {
         // fetch specific journal entry details from the db
         // update specific journal entry with the new variable
         const response = await fetch(`${API_BASE_URL}/api/entries/${entry.id}`, {
            method: "PUT",
            headers: {
               "Content-Type": "application/json",
            },
            credentials: "include",
            body: JSON.stringify(updatedEntry), // json format
         });

         // if the response goes through, save updated entry and close the modal 
         if (response.ok) {
            const saveUpdatedEntry = await response.json();
            onUpdate(saveUpdatedEntry)
            console.log("Saved the updated entry!");
            onClose();
         } else {
            const errorText = await response.text();
            console.error("Failed to update entry. Status:", response.status, errorText);
         }
      } catch (error) {
         console.error("Error submitting updated entry:", error);
      }
   };

   return (
      <>
         <div className="editmodal-overlay">
            <div className="editmodal-container"> 
               <div className="editmodal-top">
                  <h2>Edit Entry</h2>
                  <button className="exit-btn" onClick={onClose}>X</button>
               </div>
               <div className="editmodal-mid">
                  <input
                     className="modal-input"
                     type="text"
                     value={editTitle}
                     onChange={(e) => setEditTitle(e.target.value)}/>
                  {/* calendar is a react component */}
                  <Calendar
                     className="modal-calendar"
                     value={editDate}
                     onChange={(e) => setEditDate(e.value)}
                     dateFormat="mm/dd/yy"
                     showIcon/>
               </div>           
               <textarea
                  className="modal-textarea"
                  rows={5}
                  value={editContent}
                  onChange={(e) => setEditContent(e.target.value)}/>
               <button className="update-btn" onClick={handleSubmit}>Update</button>
            </div>
         </div>
      </>
   );
}

// specify what type of variable is required for each prop
EditModal.propTypes = {
   entry: PropTypes.object.isRequired,
   onClose: PropTypes.func.isRequired,
   onUpdate: PropTypes.func.isRequired,
};

export default EditModal;