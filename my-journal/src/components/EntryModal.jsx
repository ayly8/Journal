import {useState} from 'react'
import PropTypes from 'prop-types';
import '../css/entrymodal.css'

function EntryModal({ onClose }) {
   const [title, setTitle] = useState("");
   const [content, setContent] = useState("");

   return (
      <>
         <div className="modal-overlay">
            <div className="modal-container"> 
               <div className="modal-top">
                  <h2>Create New Entry</h2>
                  <button className="exit-btn" onClick={onClose}>X</button>
               </div>
               <input
                  type="text"
                  placeholder="Title..."
                  value={title}
                  onChange={(e) => setTitle(e.target.value)}/>
               <textarea
                  rows={5}
                  placeholder="Write something here..."
                  value={content}
                  onChange={(e) => setContent(e.target.value)}/>
               <button className="create-btn">Create</button>
            </div>
         </div>
      </>
   );
}

EntryModal.propTypes = {
   onClose: PropTypes.func.isRequired,
};

export default EntryModal;