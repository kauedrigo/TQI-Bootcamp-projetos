import styled from "styled-components";

const Home = styled.div`
  width: 100%;
  height: 50vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  h1 {
    text-transform: uppercase;
  }

  p {
    font-size: 1.5em;
  }
`;

function HomePage() {

  return (
    <Home>
      <h1>¡Welcome!</h1>
      <p>Choose an animal on top to generate random images and facts.</p>
      <p>Let your inner cat-person or dog-person out!</p>
      <p>You can also let it be a random animal and get some info about it.</p>
    </Home>
  );
}

export default HomePage;