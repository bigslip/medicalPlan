var state = true;
$(function() {
    $("#closeId").mouseout(function() {
        $("#closeId").attr("src", "images/close1.jpg");

    }

            );
});


$(function() {
    $("#closeId").mouseover(function() {
        $("#closeId").attr("src", "images/close02.jpg");
    }


            );

});


$(function() {
    $("#handleImage").mouseout(function() {
        if (!state) {
            $("#handleImage").attr("src", "web/09.jpg");
        }

    }


            );

});

$(function() {
    $("#handleImage").mouseover(function() {
        if (!state) {
            $("#handleImage").attr("src", "web/10.jpg");
        }

    }


            );

});

function action() {

    $("#group1").toggle("slow", null);
    if (state) {
        $("#handleImage").attr("src", "web/09.jpg");
        state = false;
    } else {
        $("#handleImage").attr("src", "web/14.jpg");
        state = true;
    }

    // $("#group1").fadeIn("slow",null)

}



