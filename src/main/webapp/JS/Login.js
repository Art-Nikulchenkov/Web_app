
function confirm(){
    var login = document.getElementById("loginField");
    var password = document.getElementById("passwordField");

    var loginValue = login.value;
    var passwordValue = password.value;
    if(loginValue.includes(" ")){
        alert("Error: Your login contains a space symbol ' '");
        login.value = "";
        password.value = "";
        return;
    }
    if(passwordValue.includes(" ")){
        alert("Error: Your password contains a space symbol ' '");
        password.value = "";
        return;
    }
    if(loginValue != "" && passwordValue != "") {
        // alert("Your login: " + login.value + "\nYour password: " + password.value);

        var xhr = new XMLHttpRequest();
        xhr.open('POST', '/add', true);
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");


        xhr.onreadystatechange = function() {
            if (xhr.readyState != 4) {
               return
            }

             if (xhr.status === 200) {
                 var response = xhr.responseText;
                 console.log(xhr.responseText);
               if(response == "true") {
                   console.log("Ok");
                   location.href = 'localhost:8887/JSP/HomePage.jsp';
               }
               if(response == "false"){
                   alert("Wrong password/login");
               }
            } else {
             console.log('error', xhr.responseText);
            }
        }
        var mes = "login=" + loginValue + "&password=" + passwordValue + "&create=false";
        xhr.send(mes);
    }
    else {
        alert("YOu must fill all fields");
    }
    password.value = "";
}

var flag = false;
var congratulation = false;





function create(){
    var welcomeText = document.getElementById("welcomeText");
    var PHOTOGRAMtext = document.getElementById("PHOTOGRAMtext");

    var loginLabel = document.getElementById("loginLabel");
    var passwordLabel = document.getElementById("passwordLabel");

    var loginField = document.getElementById("loginField");
    var passwordField = document.getElementById("passwordField");

    var buttonConfirm = document.getElementById("confirm");
    var buttonCreate = document.getElementById("create");

    var inputContainer = document.getElementById("inputContainer");
    
    var passwordField2;
    var passwordFild2Label;

    var ageField;
    var ageLabel;

    var genderField;
    var genderLabel;

    //initialize second passwordField-Label
    passwordFild2Label = document.getElementById("passwordLabel2");
    

    //initialize second passwordField
    passwordField2 = document.getElementById("passwordField2");

    //initialize genderField
    genderField = document.getElementById("genderField")

    //initialize genderField-Label    
    genderLabel = document.getElementById("genderLabel");

    //initialize ageField    
    ageField = document.getElementById("ageField");

    //initialize age-Label
    ageLabel = document.getElementById("ageLabel");


    if(congratulation){
        loginLabel.hidden = false;
        passwordLabel.hidden = false;

        loginField.hidden = false;
        passwordField.hidden = false;

        buttonConfirm.hidden = false;
    }

    if(!flag){
        //Text change
        welcomeText.innerHTML = "Create your";
        PHOTOGRAMtext.innerHTML = "PHOTOGRAM account";

        //Label change
        loginLabel.innerHTML = "<h1>Create login:</h1>";
        passwordLabel.innerHTML = "<h1>Create password:</h1>";

        loginField.value = "";
        passwordField.value = "";

        genderField.hidden = false;
        genderLabel.hidden = false;

        passwordField2.hidden = false;
        passwordFild2Label.hidden = false;

        ageField.hidden = false;
        ageLabel.hidden = false;

        //Button text change
        buttonConfirm.innerHTML = "Create!";
        buttonConfirm.onclick = createAccount;
        buttonCreate.innerHTML = "Go back";

        flag = true;
    }
    else{
        //Text change
        welcomeText.innerHTML = "Welcome to your";
        PHOTOGRAMtext.innerHTML = "PHOTOGRAM";

        //Label change
        loginLabel.innerHTML = "<h1>Your login:</h1>";
        passwordLabel.innerHTML = "<h1>Your password:</h1>";

        loginField.value = "";
        passwordField.value = "";

        //Button text change
        buttonConfirm.innerHTML = "Log in";
        buttonConfirm.onclick = confirm;
        buttonCreate.innerHTML = "Create account";

        //Remove passwordField2
        if(!congratulation){
            congratulation = false;

            passwordField2 = document.getElementById("passwordField2");
            passwordField2.hidden = true;
            passwordFild2Label = document.getElementById("passwordLabel2");
            passwordFild2Label.hidden = true;

            passwordField2 = document.getElementById("genderField");
            passwordField2.hidden = true;
            passwordFild2Label = document.getElementById("genderLabel");
            passwordFild2Label.hidden = true;

            passwordField2 = document.getElementById("ageField");
            passwordField2.hidden = true;
            passwordFild2Label = document.getElementById("ageLabel");
            passwordFild2Label.hidden = true;
        }

        flag = false;
    }
}

function createAccount(){
    var passwordField = document.getElementById("passwordField");
    var passwordField2 = document.getElementById("passwordField2");
    var loginField = document.getElementById("loginField");

    var age = document.getElementById("ageField");
        ageValue = age.value;
    var gender = document.getElementById("genderField");
        var genderValue = gender.value;

    if((passwordField.value + passwordField2.value + loginField.value + ageField.value + gender.alue) == ""){
        alert("Error: All fields are empty\n");
        return;
    }
    tmpString = loginField.value;
    if(loginField.value.includes(" ")){
        loginField.value = "";
        passwordField.value = "";
        passwordField2.value = "";
        alert("Error: Your login contains a space symbol ' '\n");
        return;
    }
    if(passwordField.value.includes(" ") || passwordField2.value.includes(" ")){
        passwordField.value = "";
        passwordField2.value = "";
        alert("Error: Your password contains a space symbol ' '\n");
        return;
    }
    if(passwordField.value != passwordField2.value){
        passwordField.value = "";
        passwordField2.value = "";
        alert("Error: password fields are not equal\n");
        return;
    }

    var xhr = new XMLHttpRequest();

        xhr.open('POST', '/add', true);
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

        xhr.onreadystatechange = function() {
            if (xhr.readyState != 4) {
               return
            }

             if (xhr.status === 200) {
                 var response = xhr.responseText;
                 console.log(xhr.responseText);
               if(response == "true") {
                    congratulations(loginField.value);
               }
               else {
                alert(xhr.responseText);
                }
            } 
        }
        var mes = "login=" + loginField.value + "&password=" + passwordField.value + "&create=true" + "&age=" + ageValue + "&gender=" + genderValue;
        xhr.send(mes);
}

function congratulations(login){
    congratulation = true;

    //Congratulations text
    var welcomeText = document.getElementById("welcomeText");
    var PHOTOGRAMtext = document.getElementById("PHOTOGRAMtext");
    welcomeText.innerHTML = "We glad to see you in";
    PHOTOGRAMtext.innerHTML = "PHOTOGRAM!";

    //Hide labels
    var loginLabel = document.getElementById("loginLabel");
    var passwordLabel = document.getElementById("passwordLabel");
    loginLabel.hidden = true;
    passwordLabel.hidden = true;

    var age = document.getElementById("ageField");
    age.hidden = true;
    var gender = document.getElementById("genderField");
    gender.hidden = true;
    var agel = document.getElementById("ageLabel");
    agel.hidden = true;
    var genderl = document.getElementById("genderLabel");
    genderl.hidden = true;

    //Hide fields
    var loginField = document.getElementById("loginField");
    var passwordField = document.getElementById("passwordField");
    loginField.hidden = true;
    passwordField.hidden = true;

    //Remove passwordField2
    var Label = document.getElementById("passwordLabel2");
    var passwordField2 = document.getElementById("passwordField2");
    Label.hidden = true;
    passwordField2.hidden = true;;

    //Hide confirm button
    var buttonConfirm = document.getElementById("confirm");
    buttonConfirm.hidden = true;
}