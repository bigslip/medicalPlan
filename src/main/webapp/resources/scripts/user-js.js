function getFocus(idMSG) {
    $("#" + idMSG).html("");
}

function check(comp, idMSG) {
    var val = $(comp).val().length;
    if (val > 0) {
        if (4 > val || val > 20) {
            $("#" + idMSG).html("Error, min char is 4 and max is 20 !");
        }
    }
}
function emailValidation(comp, idMSG) {
    var email = $(comp).val();
    if (email.indexOf("@", 1) == -1) {
        $("#" + idMSG).html("Pleas,Enter Valid Email !");
    }

}
function equal(msg) {
    if ($('#password').val() != $(obj2).val()) {
        $('#Cpassword').html(msg);

    } else {
        $('#CpasswordMsg').html("");

    }
}
