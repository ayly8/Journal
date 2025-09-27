import PropTypes from 'prop-types'
import '../css/tutorial.css'

function Tutorial(props) {
   
   return (
      <div className="tutorial">
         <h2 className="desc-title">{props.title}</h2>
         <h4 className="desc-duration">{props.desc}</h4>
         <img className="proj-img" src={props.img} alt={props.imgDesc}></img>
      </div>
   );
}

// specify what type of variable is required for each prop
Tutorial.propTypes= {
   title: PropTypes.string.isRequired,
   desc: PropTypes.string.isRequired,
   img: PropTypes.string.isRequired,
   imgDesc: PropTypes.string.isRequired,
}

export default Tutorial