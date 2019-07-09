window.onload=function(){
    var taxData;
    $(document).ready(function(){
        $.ajax({
            type: "GET",
            url: "data/tax_rates2.csv",
            dataType: "text",
            success: function(data) {readData(data);}
        });
    });
    function readData(text){
        let parsed=$.csv.toArrays(text);
        console.log(parsed);
        console.log(parsed.length);
        console.log(parsed[1][1]);
        $("#zip").keyup(function(){
           for(var i = 1; i < parsed.length; i++){
               if(document.getElementById("zip").value == parsed[i][1]){
                    document.getElementById("state").value = parsed[i][0];
                    document.getElementById("tax").value = parsed[i][3];
                    console.log(typeof(cast));
                    let cast2 = parseFloat(document.getElementById("tax").value);
                    console.log(typeof(cast2));
               }
           } 
        });

    }
    bootstrapValidate('#email', 'email:Enter a valid email address!');
    bootstrapValidate('#firstname', 'alpha:Please Enter your first name!');
    bootstrapValidate('#lastname', 'alpha:Please enter your last name!');
    bootstrapValidate('#phonenumber', 'integer:Please enter a valid number!');
    bootstrapValidate('#address', 'min:2:Please enter a valid address!');
    bootstrapValidate('#creditcard','min:14:Please enter a valid credit card number');
    bootstrapValidate('#cardname', 'alpha:Please Enter the name on card!');
    bootstrapValidate('#carddate','contains:/:Please Enter valid date!');
    bootstrapValidate('#cardcode','integer:please enter a valid code!');
}

document.addEventListener("DOMContentLoaded", function(even){
    console.log("DOM has loaded");
    console.log("appache is lagging");
    document.getElementById("submitClicked").onclick=function(){
    let mail =document.getElementById("email").value;
    let mailcheck = /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/;
    let firstname = document.getElementById("firstname").value;
    let firstnamecheck = /^[a-zA-Z ]+$/;
    let lastname = document.getElementById("lastname").value;
    let address = document.getElementById("address").value;
    let addresscheck = /^\d+\s[A-z]+\s[A-z]+/;
    let creditcard = document.getElementById("creditcard").value;
    let creditcardcheck = /\b(?:\d{4}[ -]?){3}(?=\d{4}\b)/;
    let phonenumber = document.getElementById("phonenumber").value;
    let phonenumbercheck = /^\s*(?:\+?(\d{1,3}))?[-. (]*(\d{3})[-. )]*(\d{3})[-. ]*(\d{4})(?: *x(\d+))?\s*$/;
    if(mail=="" || !mailcheck.test(mail)){
        alert("mail section is wrong");
        return false;
    }
    else if(firstname =="" || !firstnamecheck.test(firstname)){
        alert("first name section is wrong");
        return false;
    }
    else if(lastname =="" || !firstnamecheck.test(lastname)){
        alert("last name section is wrong");
        return false;
    }
    else if(address ==""|| !addresscheck.test(address)){
        alert("address section is wrong");
        return false;
    }
    else if(creditcard == "" || !creditcardcheck.test(creditcard)){
        alert("creditcard is invalid");
        return false;
    }
    else if(phonenumber =="" || !phonenumbercheck.test(phonenumber)){
        alert("phone number is invalid");
        return false;
    }
    else{
        document.getElementById("submitted").submit();
        location.replace("confirmation.jsp");
    }

    }
    
});
function getRandomInt(max){
    return Math.floor(Math.random() * Math.floor(max));
}