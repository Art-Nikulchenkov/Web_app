
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
    passwordFild2Label = document.createElement("label");
        passwordFild2Label.className = "label";
        passwordFild2Label.id = "Label";
        passwordFild2Label.setAttribute("for", "passwordField2");
        passwordFild2Label.innerHTML = "<h1>Repeat your password:</h1>";

    //initialize second passwordField
    passwordField2 = document.createElement("input");
        passwordField2.type = "password";
        passwordField2.className = "textField";
        passwordField2.id = "passwordField2";

    //initialize genderField
    genderField = document.createElement("input");
        genderField.type = "text";
        genderField.className = "genderField";
        genderField.id = "genderField";

    //initialize genderField-Label    
    genderLabel = document.createElement("label");
        genderLabel.className = "label";
        genderLabel.id = "genderLabel";
        genderLabel.setAttribute("for", "genderField");
        genderLabel.innerHTML = "<h1>Gender:</h1>";

    //initialize ageField    
    ageField = document.createElement("input");
        ageField.type = "text";
        ageField.className = "ageField";
        ageField.id = "ageField";

    //initialize age-Label
    ageLabel = document.createElement("label");
        ageLabel.className = "label";
        ageLabel.id = "ageLabel";
        ageLabel.setAttribute("for", "genderField");
        ageLabel.innerHTML = "<h1>Set your age:</h1>";


    if(congratulation){
        loginLabel.hidden = false;
        passwordLabel.hidden = false;

        loginField.hidden = false;
        passwordField.hidden = false;

        genderField.hidden = false;
        genderLabel.hidden = false;

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

        //Button text change
        buttonConfirm.innerHTML = "Create!";
        buttonConfirm.onclick = createAccount;
        buttonCreate.innerHTML = "Go back";

        //Add passwordField2
        inputContainer.appendChild(passwordFild2Label).appendChild(passwordField2);

        //Add genderField
        inputContainer.appendChild(genderLabel).appendChild(genderField);

        //Add ageField
        inputContainer.appendChild(ageLabel).appendChild(ageField);

        //Add picture
        document.getElementById("HeadContainer").appendChild(image);


        flag = true;
    }
    else{
        //Text change
        welcomeText.innerHTML = "Welcome to your";
        PHOTOGRAMtext.innerHTML = "PHOTOGRAM";

        //Label change
        loginLabel.innerHTML = "<h1>Enter your login:</h1>";
        passwordLabel.innerHTML = "<h1>Enter your password:</h1>";

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
            passwordField2.parentNode.removeChild(passwordField2);
            passwordFild2Label = document.getElementById("Label");
            passwordFild2Label.parentNode.removeChild(passwordFild2Label);

            passwordField2 = document.getElementById("genderField");
            passwordField2.parentNode.removeChild(passwordField2);
            passwordFild2Label = document.getElementById("genderLabel");
            passwordFild2Label.parentNode.removeChild(passwordFild2Label);

            passwordField2 = document.getElementById("ageField");
            passwordField2.parentNode.removeChild(passwordField2);
            passwordFild2Label = document.getElementById("ageLabel");
            passwordFild2Label.parentNode.removeChild(passwordFild2Label);
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

    alert(genderValue);
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

    alert("next");
    var xhr = new XMLHttpRequest();

        xhr.open('POST', '/add', true);
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

        xhr.onreadystatechange = function() {
            if (xhr.readyState != 4) {
                alert("!=4");
               return
            }

             if (xhr.status === 200) {
                 var response = xhr.responseText;
                 console.log(xhr.responseText);
               if(response == "true") {
                    congratulations(loginField.value);
               }
               else {
                alert("This user is already registred");
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

    //Hide fields
    var loginField = document.getElementById("loginField");
    var passwordField = document.getElementById("passwordField");
    loginField.hidden = true;
    passwordField.hidden = true;

    //Remove passwordField2
    var Label = document.getElementById("Label");
    var passwordField2 = document.getElementById("passwordField2");
    Label.parentNode.removeChild(Label);
    passwordField2.parentNode.removeChild(passwordField2);

    //Hide confirm button
    var buttonConfirm = document.getElementById("confirm");
    buttonConfirm.hidden = true;
}