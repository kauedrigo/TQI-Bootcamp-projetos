function catImage() {
  return (
    fetch("https://aws.random.cat/meow")
      .then(response => response.json())
      .then(resp => resp.file)
  );
}

function catFact() {
  return (
    fetch("https://catfact.ninja/fact")
      .then(response => response.json())
      .then(resp => resp.fact)
  );
}

function dogImage() {
  return (
    fetch("https://dog.ceo/api/breeds/image/random")
      .then(response => response.json())
      .then(resp => resp.message)
  );
}

function dogFact() {
  return (
    fetch("https://www.dogfactsapi.ducnguyen.dev/api/v1/facts/?number=1")
      .then(response => response.json())
      .then(resp => resp.facts)
  );
}

function randomImageFact() {
  return (
    fetch("https://zoo-animal-api.herokuapp.com/animals/rand")
      .then(response => response.json())
  );
}

export { catImage, catFact, dogImage, dogFact, randomImageFact };