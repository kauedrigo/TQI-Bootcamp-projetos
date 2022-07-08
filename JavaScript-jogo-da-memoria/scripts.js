// botões para iniciar o jogo
const botao = Array.from(document.getElementsByClassName("btn"));
const botaoDiv = document.getElementById("buttons");
botao[0].addEventListener("click", function(){main(4)});
botao[1].addEventListener("click", function(){main(8)});
botao[2].addEventListener("click", function(){main(16)});

// função principal que espera a entrada do jogador para inicializar
function main(qtdCartas) {
  
  // esconde a página inicial com os botões
  botaoDiv.style.visibility = "hidden";
  
  // cria o número de cartas escolhido
  for (let index = 0; index < qtdCartas; index++) {
    var div = document.createElement("div");
    var divBack = document.createElement("div");
    var divFront = document.createElement("div");
    div.setAttribute("class", "card");
    divBack.setAttribute("class", "card-back");
    divFront.setAttribute("class", "card-front");
    document.getElementById("main").appendChild(div);
    document.getElementsByClassName("card")[index].appendChild(divBack);
    document.getElementsByClassName("card")[index].appendChild(divFront); 
  }

  // array que representa cada carta
  const carta = Array.from(document.getElementsByClassName("card"));

  // adiciona o evento click a cada carta
  for (let index = 0; index < carta.length; index++) {
    carta[index].addEventListener("click", virarCarta);
  }

  // carregamento das imagens
  var pack1 = ["./card-A.png", "./card-A.png", "./card-K.png", "./card-K.png"];
  var pack2 = ["./card-J.png", "./card-J.png", "./card-Q.png", "./card-Q.png"];
  var pack3 = ["./card-Ab.png", "./card-Ab.png", "./card-Kb.png", "./card-Kb.png",
              "./card-Jb.png", "./card-Jb.png", "./card-Qb.png", "./card-Qb.png"];

  switch (qtdCartas) {
    case 4:
      var cartasFrentes = pack1;
      break;
    case 8:
      var cartasFrentes = pack1.concat(pack2);
      document.getElementById("main").style.width = "548px";
      break;
    case 16:
      var cartasFrentes = pack1.concat(pack2, pack3);
      document.getElementById("main").style.width = "548px";
      document.getElementById("main").style.height = "740px";
      break;
  }
    
  // sorteia uma carta aleatória
  function sorteio() {
    return Math.floor(Math.random() * cartasFrentes.length);
  }

  // atribuição aleatória das cartas
  for (let index = 0; index < carta.length; index++) {
    let cartaIndex = sorteio();
    let urlString = "url(" + cartasFrentes[cartaIndex] + ")"
    carta[index].childNodes[1].style.backgroundImage = urlString;

    // remove a carta sorteada do pool
    cartasFrentes.splice(cartaIndex, 1);
  }

  // identifica as cartas que foram viradas e a quantidade
  var cartasViradas = [];
  var qtdViradas = 0;

  // trava o jogo enquanto acontecem as animações 
  let lockBoard = false;
  let tentativas = 0;

  // ações provacadas pelo click na n-ésima carta
  function virarCarta() {
    
    // condicionais de segurança
    if (lockBoard === true) return;
    if (cartasViradas[0] === this) return;
    lockBoard = true;
    tentativas++;

    this.style.transform = "rotateY(180deg)";
    
    // registro das cartas viradas
    cartasViradas.push(this);
    
    // verifica se as cartas viradas são iguais
    if (cartasViradas.length === 2) {
      if (cartasViradas[0].isEqualNode(cartasViradas[1]) === true) {
        cartasViradas[0].removeEventListener("click", virarCarta);
        cartasViradas[1].removeEventListener("click", virarCarta);
        cartasViradas = [];
        qtdViradas = qtdViradas + 2;

        // checagem para finalizar o jogo
        if (qtdViradas === qtdCartas ){
          setTimeout(ganhou, 1000);
        }
      }
      else {
        setTimeout( () => {
          cartasViradas[0].style.transform = "rotateY(0deg)";
          cartasViradas[1].style.transform = "rotateY(0deg)";
          cartasViradas = [];
        }, 500); 
      }
    }
    
    setTimeout( () => {lockBoard = false}, 600);
  }

  // fim de jogo
  function ganhou() {
    //alert("Fim de jogo. Você ganhou em " + tentativas/2 + " tentativas.");
    document.getElementById("end-message").innerHTML = "Fim de jogo. Você ganhou em " + tentativas/2 + " tentativas.";
    document.getElementById("end-button").style.display = "block";
  }
}