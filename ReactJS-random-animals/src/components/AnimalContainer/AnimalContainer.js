import { Container } from "./styled.js";

function AnimalContainer(props) {
  return (
    <Container>
      <img src={props.animalImage} alt="animal"></img>
      <p>{props.animalFact}</p>
    </Container>
  )
}

export default AnimalContainer;