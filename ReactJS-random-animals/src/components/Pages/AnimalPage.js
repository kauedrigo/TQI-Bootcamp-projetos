import { useState, useEffect } from "react";
import { AnimalContainer } from "../index";

function AnimalPage(props) {

  const [fact, setFact] = useState();
  const [image, setImage] = useState();

  const handleClick = () => {
    switch (props.animal) {
      case "Random":
        props.func()
          .then(info => {
            setImage(info.image_link);
            setFact(<>
              Name: {info.name} <br/>
              Diet: {info.diet} <br/>
              Habitat: {info.habitat} <br/>
              Type: {info.animal_type}
            </>);
          })
        break;
      
      case "Cat":
      case "Dog":
        props.image()
          .then(image => setImage(image));
    
        props.fact()
          .then(fact => setFact(fact));
        break;
      default:
        break;
    }
  }

  useEffect(() => {
    handleClick();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <>
      <button onClick={handleClick}>{props.animal} it up!</button>
      <AnimalContainer 
        animalImage={image}
        animalFact={fact}
      />
    </>
  );
}

export default AnimalPage;