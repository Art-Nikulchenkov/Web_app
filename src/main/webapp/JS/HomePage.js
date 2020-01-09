var sessId;

function getFriends(){
    xhr = new XMLHttpRequest();

    xhr.open('POST', '/add', true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function() {
        if (xhr.readyState != 4) {
           return
        }

        if (xhr.status === 200) {
            console.log("Result: " + XMLHttpRequest.responseText);
            sessId = JSON.parse(xhr.responseText);
            console.log(JSON.parse(xhr.responseText));
            console.log(JSON.parse(xhr.responseText));
            // addFriendsToList(sessId);
        }
        else {
         console.log('error', xhr.responseText);
        }
    }
    var mes = "id=" + true;
    xhr.send(mes);
}

function logout(){
    xhr = new XMLHttpRequest();

    xhr.open('GET', '/Logout', true);
    // xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    console.log("logout");

    xhr.onreadystatechange = function() {
        if (xhr.readyState != 4) {
           return
        }

        if (xhr.status === 200) {
            if(xhr.responseText == true){
                location.href = 'localhost:8887/HTML/Login.html';
            }
        }
        else {
         console.log('error', xhr.responseText);
        }
    }
    var mes = "logout=" + true;
    xhr.send(mes);
}

var chosen = false;
var chosenLogin;

function chooseFriend(friendLogin){
    if(galleryActive){
        leftGallery();
    }
    var messages;
    if(!chosen){
        chosen = true;
        messages = document.getElementById("MessagesContainer");
        messages.hidden = false;

        var textButton = document.getElementById("sendPicButton");
            textButton.hidden = false;
        var textButton = document.getElementById("sendButton");
            textButton.hidden = false;
        var textArea = document.getElementById("messageField");
            textArea.hidden = false;

        messages = document.getElementById("scrollMessages");
        messages.innerHTML = "<h1>Dialog with " + friendLogin + "</h1>";
        chosenLogin = friendLogin;
        refreshMessages();
    }
    else{
        leftDialog();
        messages = document.getElementById("scrollMessages");
        messages.innerHTML="<h1>Dialog with " + friendLogin + "</h1>";
        messages = document.getElementById("MessagesContainer");
        messages.hidden = false;
        var textButton = document.getElementById("sendButton");
            textButton.hidden = false;
            var textButton = document.getElementById("sendPicButton");
            textButton.hidden = false;
        var textArea = document.getElementById("messageField");
            textArea.hidden = false;
        chosen = true;
        chosenLogin = friendLogin;
        refreshMessages();
    }

}

function leftDialog(){
    if(chosen){
        var messages = document.getElementById("scrollMessages");
        messages.innerHTML = "";
        messages = document.getElementById("MessagesContainer");
        messages.hidden = true;
        chosen = false;
        picChosen = false;

        var textButton = document.getElementById("sendButton");
            textButton.hidden = true;
        var textButton = document.getElementById("sendPicButton");
            textButton.hidden = true;    
        var textArea = document.getElementById("messageField");
            textArea.hidden = true;
        var textArea = document.getElementById("messageField");
            textArea.innerHTML= "";
        chosenLogin = "";
        dropChosenPictures();
    }
}

function openSearchById(){
    console.log("openSearch");
    var button = document.getElementById("searchButton");
    button.innerText = "Search";
    button.onclick = searchById;
    var searchField = document.getElementById("searchField");
    searchField.hidden = false;
}

function searchById(){
    var searchField = document.getElementById("searchField");
    var friendId = searchField.value;
    if(friendId == ""){
        alert("Write some id");
        return;
    }
    console.log("searchById");
    var button = document.getElementById("searchButton");
    button.innerText = "Search by id";
    button.onclick = openSearchById;
    
    xhr = new XMLHttpRequest();

    xhr.open('POST', '/addFriend', true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function() {
        if (xhr.readyState != 4) {
           return
        }

        if (xhr.status === 200) {
            console.log("searchById");
            if(xhr.responseText == true){
                alert("Friend added");
                searchField.hidden = true;
            }
            else
                alert("Wrong ID");
        }
        else {
         console.log('error', xhr.responseText);
        }
    }
    var mes = "addFriend=" + true + "&friendId=" + friendId;
    xhr.send(mes);
}

function send(){
    // alert("sending to " + chosenLogin);
    var messageField = document.getElementById("messageField");
    var message = messageField.value;
    if(message == "" && !picChosen){
        alert("Message field is empty")
        return;
    }

    xhr = new XMLHttpRequest();

    xhr.open('POST', '/Send', true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function() {
        if (xhr.readyState != 4) {
           return
        }

        if (xhr.status === 200) {
            console.log("sended");
            refreshMessages();
        }
        else {
         console.log('error', xhr.responseText);
        }
    }
    if(picChosen){
        chosenPict++;
        var mes = "message=" + chosenPict + "&to=" + chosenLogin + "&isPicture=" + chosenPict;
    }
    else{
        var mes = "message=" + message + "&to=" + chosenLogin + "&isPicture=" + 0;

    }
    
    xhr.send(mes);
    messageField.value = "";
}

var allMessages;
function refreshMessages(){
    if(chosen){
        xhr = new XMLHttpRequest();

        xhr.open('POST', '/GetMessages', true);
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

        xhr.onreadystatechange = function() {
            if (xhr.readyState != 4) {
            return
            }

            if (xhr.status === 200) {
                console.log("sended");
                allMessages = xhr.responseText;
                var scrollMessages = document.getElementById("scrollMessages");
                scrollMessages.innerHTML = allMessages;
            }
            else {
            console.log('error', xhr.responseText);
            }
        }
        var mes = "getMessages=" + true + "&friendLogin=" + chosenLogin;
        xhr.send(mes);
    }
}

var galleryActive = false;
function showGallery(){
    if(galleryActive){
        return;
    }
    if(chosen){
        leftDialog();
    }
    if(picChosen){
        dropChosenPictures();
    }
    console.log("Show gallery")
    xhr = new XMLHttpRequest();

    xhr.open('POST', '/GetGallery', true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function() {
        if (xhr.readyState != 4) {
        return
        }

        if (xhr.status === 200) {
            var galleryCont = document.getElementById("MessagesContainer");
                galleryCont.hidden = false;


            var textArea = document.getElementById("messageField");
            textArea.hidden = true;
            var textButton = document.getElementById("sendButton");
            textButton.hidden = true;
            var textButton = document.getElementById("sendPicButton");
            textButton.hidden = true;

            allMessages = xhr.responseText;
            var scrollMessages = document.getElementById("scrollMessages");
            scrollMessages.innerHTML = "<h3>Your gallery</h3>" + allMessages;
            var galleryButton = document.getElementById("galleryButton");
            console.log("Arrived")
            galleryButton.innerHTML = "Left gallery";
            galleryButton.onclick = leftGallery;
            galleryActive = true;
        }
        else {
        console.log('error', xhr.responseText);
        }
    }
    var mes = "getGallery=" + true;
    xhr.send(mes);
    console.log("Sended")
}

function leftGallery(){
    if(!galleryActive){
        console.log("not active");
        return;
    }
    if(chosen){
        console.log("choosen");
        return;
    }
    var scrollMessages = document.getElementById("scrollMessages");
    scrollMessages.innerHTML = "";
    galleryActive = false;
    galleryButton.innerHTML = "Show gallery";
    galleryButton.onclick = showGallery;

    var textButton = document.getElementById("sendButton");
        textButton.hidden = true;
    var textButton = document.getElementById("sendPicButton");
        textButton.hidden = true;
    var textArea = document.getElementById("messageField");
        textArea.hidden = true;
    var galleryCont = document.getElementById("MessagesContainer");
        galleryCont.hidden = true;
}


var chosenPict = -1;
var picChosen = false;
function chosePicture(){

    xhr = new XMLHttpRequest();

    xhr.open('POST', '/ChosePic', true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function() {
        if (xhr.readyState != 4) {
           return
        }

        if (xhr.status === 200) {
            var pictures = document.getElementById("chosePic");
            pictures.innerHTML = "<h1>Pictures:</h1>" + xhr.responseText;
            var buttn = document.getElementById("sendPicButton");
            buttn.onclick = dropChosenPictures;
            buttn.innerHTML = "Drop choosen pict";
        }
        else {
         console.log('error', xhr.responseText);
        }
    }
    var mes = "chosePic=" + true;
    xhr.send(mes);
}

function choseThisPic(picId){
    alert("Pic " + picId + " chosen");
    chosenPict = picId;
    picChosen = true;
}

function dropChosenPictures(){
    chosenPict = -1;
    picChosen = false;
    var pictures = document.getElementById("chosePic");
    pictures.innerHTML = "";
    var buttn = document.getElementById("sendPicButton");
            buttn.onclick = chosePicture;
            buttn.innerHTML = "Chose pic";
}

