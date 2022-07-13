import { Menu } from "./styled";
import { Link } from "react-router-dom";

function Header() {
  const list = document.getElementsByTagName("li");
  
  const toggleBG = (event) => {
    Array.from(list).forEach(animal => animal.classList.remove("clicked"));
    event.target.classList.add("clicked");
  }

  return (
    <Menu>
      <Link to="/" className="route-link">
        <li onClick={toggleBG}>
          Home
        </li> 
      </Link>
      
      <Link to="cats" className="route-link">
        <li onClick={toggleBG}>
          Cats
        </li>    
      </Link>

      <Link to="dogs" className="route-link">
        <li onClick={toggleBG}>
          Dogs
        </li>
      </Link>

      <Link to="randoms" className="route-link">
        <li onClick={toggleBG}>
          Random
        </li>
      </Link>
    </Menu>
  )
}

export default Header;