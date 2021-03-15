//usando as bibliotecas e definindo a logica do client
var stompClient = null;
// const Stomp = require("stompjs");
// let SockJS = require("sockjs-client");
//as principais peças que devemos prestar a atencao é connected e sendName(no caso troquei pra send message)
function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

//aqui usa-se o sockjs e o stompjs para abrir conexão,
// no qual o nosso sockjs server aguarda por conexões.
//Uma vez que a conexão foi estabelecida,
//o cliente inscreve o destino onde o server irá mandar as mensagens
//quando a mensagem for recebida nesse destino
// ela vai anexar o paragrafo ao dom para mostrar a mensagem(/teste/topic/greetings)
function connect() {

    let socket = new SockJS('http://localhost:8080/teste');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            console.log(greeting.body)
            showGreeting(JSON.parse(greeting.body).name);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

//a funcao que recupera a mensagem enviada, e usa o client Stomp para enviar para o destino (/app/hello)
function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});