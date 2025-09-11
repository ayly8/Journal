import PropTypes from 'prop-types';
import '../css/deletemodal.css'

function DeleteModal({entry, onDelete, onClose}) {

   const handleDelete = async() => {
      try {
         const response = await fetch(`/api/entries/${entry.id}`, {
            method: "DELETE",
            credentials: "include",
         });

         if (response.ok) {
            console.log("Entry deleted!");
            onDelete(entry.id);
         } else {
            const errorText = await response.text();
            console.error("Failed to delete entry. Status:", response.status, errorText);
         }
      } catch (error) {
         console.error("Error deleting entry:", error);
      }
   };

   return (
      <>
         <div className="deletemodal-overlay">
            <div className="deletemodal-container">
               <div className="deletemodal-top">
                  <h2>Delete this entry forever?</h2>
                  <button className="deletemodal-exit-btn" onClick={onClose}>X</button>
               </div>
               <div className="confirm-btns">
                  <button className="yes-btn" onClick={handleDelete}>Yes</button>
                  <button className="no-btn" onClick={onClose}>No</button>
               </div>
            </div>
         </div>
      </>
   )
}

DeleteModal.propTypes = {
   entry: PropTypes.object.isRequired,
   onDelete: PropTypes.func.isRequired,
   onClose: PropTypes.func.isRequired,
};

export default DeleteModal;