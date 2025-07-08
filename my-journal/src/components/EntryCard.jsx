import PropTypes from 'prop-types'
import "../css/entrycard.css";

function EntryCard(props) {
   return (
      <div className="entry-card">
         <h3>{props.title}</h3>
         <p>{props.content}</p>
      </div>
   );
}

EntryCard.propTypes= {
   title: PropTypes.string.isRequired,
   content: PropTypes.string.isRequired,
}

export default EntryCard;