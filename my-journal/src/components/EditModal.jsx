import { useState } from "react";
import PropTypes from 'prop-types';
import { Calendar } from 'primereact/calendar';
import '../css/editmodal.css'

function EditModal({ onClose, entry }) {
   const [editTitle, setEditTitle] = useState(entry.title);
   const [editDate, setEditDate] = useState(entry.dateSelected ? new Date(entry.dateSelected) : new Date());
   const [editContent, setEditContent] = useState(entry.entry);

   const handleSubmit = async() => {
      const updatedEntry = {
         title: editTitle,
         entry: editContent,
         dateSelected: editDate.toISOString().split("T")[0],
      };

      try {
         const response = await fetch(`/api/entries/${entry.id}`, {
            method: "PUT",
            headers: {
               "Content-Type": "application/json",
            },
            credentials: "include",
            body: JSON.stringify(updatedEntry),
         });

         if (response.ok) {
            console.log("Updated entry!");
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
         <div className="modal-overlay">
            <div className="modal-container"> 
               <div className="modal-top">
                  <h2>Edit Entry</h2>
                  <button className="exit-btn" onClick={onClose}>X</button>
               </div>
               <div className="modal-mid">
                  <input
                     className="modal-input"
                     type="text"
                     value={editTitle}
                     onChange={(e) => setEditTitle(e.target.value)}/>
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

EditModal.propTypes = {
   onClose: PropTypes.func.isRequired,
   entry: PropTypes.object.isRequired,
};

export default EditModal;