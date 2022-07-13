import { Layout, HomePage, AnimalPage } from "../components/index";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { catFact, catImage, dogFact, dogImage, randomImageFact } from "../helpers/fetch.helpers";

function App() {

  return (
    <BrowserRouter>
      <Layout>
        <Routes>
          <Route exact path='/' element={<HomePage />} />

          <Route 
            path='cats' 
            element={<AnimalPage 
              image={catImage} 
              fact={catFact} 
              animal="Cat" />} 
          />

          <Route 
            path='dogs' 
            element={<AnimalPage 
              image={dogImage} 
              fact={dogFact} 
              animal="Dog" />} 
          />

          <Route 
            path='randoms' 
            element={<AnimalPage 
              func={randomImageFact}
              animal="Random" />} 
          />

          <Route path='*' element={<p>Opa!</p>}/>
        </Routes>
      </Layout>
    </BrowserRouter>
  );
}

export default App;