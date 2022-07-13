import styled from "styled-components";

export const Menu = styled.ul`
    background-color: #72a276;
    margin: 0;
    padding: 0;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    color: black;
    font-size: 2em;
    text-transform: uppercase;

    .route-link {
      text-decoration: none;
      color: inherit;
    }

    li {
      padding: 20px;
      cursor: pointer;
      text-decoration: none;
      list-style-type: none;
    }

    li:hover {
      border-bottom: 3px solid black;
      padding-bottom: 17px;
    }

    .clicked {
      background-color: white;
    }
`;