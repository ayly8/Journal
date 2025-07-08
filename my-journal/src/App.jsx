import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Form from './components/Form.jsx'
import Dashboard from './pages/Dashboard.jsx'

function App() {

  return (
    <>
      <Router>
      <Routes>
        <Route path="/" element={<Form />} />
        <Route path="/dashboard" element={<Dashboard />} />
      </Routes>
    </Router>
    </>
  )
}

export default App
