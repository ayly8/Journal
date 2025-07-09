import {useState} from 'react'
import PropTypes from 'prop-types';
import { Calendar } from 'primereact/calendar';
import '../css/entrymodal.css'

function EntryModal({ onClose }) {
   const [title, setTitle] = useState("");
   const [date, setDate] = useState(null);
   const [content, setContent] = useState("");

   return (
      <>
         <div className="modal-overlay">
            <div className="modal-container"> 
               <div className="modal-top">
                  <h2>Create New Entry</h2>
                  <button className="exit-btn" onClick={onClose}>X</button>
               </div>
               <div className="modal-mid">
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