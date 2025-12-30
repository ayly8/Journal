import PropTypes from 'prop-types'
import '../css/tutorial.css'
import { API_BASE_URL } from '../Url';

// this component creates a tutorial description section
function Tutorial(props) {
   
   return (
      <div className="tutorial">
         <h2 className="desc-title">{props.title}</h2>
         <h4 className="desc-duration">{props.desc}</h4>
      </div>
   );
}

// specify what type of variable is required for each prop
Tutorial.propTypes= {
   title: PropTypes.string.isRequired,
   desc: PropTypes.string.isRequired,
}

export default Tutorial