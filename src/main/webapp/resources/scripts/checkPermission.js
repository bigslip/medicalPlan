function changeView(param) {
    var value = $(param).attr("checked");
    var id = $(param).attr("id").toString();
    var idAdd = id.slice(0, id.length - 4) + "add";
    var idUpdate = id.slice(0, id.length - 4) + "update";
    var idDelete = id.slice(0, id.length - 4) + "delete";

    if (value == false) {
        $("#" + idAdd).attr("checked", value);
        $("#" + idUpdate).attr("checked", value);
        $("#" + idDelete).attr("checked", value);
    }

}

function changeAdd(param) {
    var value = $(param).attr("checked");
    var id = $(param).attr("id").toString();
    var idView = id.slice(0, id.length - 3) + "view";
    var idUpdate = id.slice(0, id.length - 3) + "update";
    var idDelete = id.slice(0, id.length - 3) + "delete";

    if (value == true) {
        $("#" + idView).attr("checked", value);

    } else {
        $("#" + idUpdate).attr("checked", value);
        $("#" + idDelete).attr("checked", value);
    }

}
function changeUpdate(param) {
    var value = $(param).attr("checked");
    var id = $(param).attr("id").toString();
    var idAdd = id.slice(0, id.length - 6) + "add";
    var idView = id.slice(0, id.length - 6) + "view";
    var idDelete = id.slice(0, id.length - 6) + "delete";

    if (value == true) {
        $("#" + idAdd).attr("checked", value);
        $("#" + idView).attr("checked", value);
    } else {
        $("#" + idDelete).attr("checked", value);
    }

}
function changeDelete(param) {
    var value = $(param).attr("checked");
    var id = $(param).attr("id").toString();
    var idAdd = id.slice(0, id.length - 6) + "add";
    var idView = id.slice(0, id.length - 6) + "view";
    var idUpdate = id.slice(0, id.length - 6) + "update";

    if (value == true) {
        $("#" + idView).attr("checked", value);
        $("#" + idUpdate).attr("checked", value);
        $("#" + idAdd).attr("checked", value);
    }


}