/**
 * Created by hadi.tayebi.
 */

var FORM_TARGET = {
    blank: '_blank',
    parent: '_parent',
    self: '_self',
    top: '_top'
};
var formObjectName;

$(function () {
    var date = $("input.base-inputdate");
    date.datepicker({
        dateFormat: 'yy/mm/dd',
        altField: '#alternate2',
        altFormat: 'DD، yy MM d',
        showButtonPanel: true
    });
    date.dateFormatter();
    $("input.currency").digits();
    $('form').submit(function (event) {
            $("input.currency").each(function (event) {
                this.value = this.value.replace(/,/g, '');
            });
        }
    );

});

function selectAllItems(elem, checkboxName) {
    $("input[type=checkbox][name=" + checkboxName + "]").prop("checked", elem.checked);
}
function loadPage(pageNumber) {
    if (pageNumber <= totalPage && pageNumber >= 1) {
        var form = $('#pagination').parents('form');
        var action = form.prop('action');
        $('#pageNumber').val(pageNumber);
        //form.prop('action', action);
        form.submit();
    }
}

function newWindow(btn) {
    $(btn).parents('form').prop('target', FORM_TARGET.blank);
}
function thisWindow(btn) {
    btn.form.target = FORM_TARGET.self;
}
function getKeyCode(event) {
    return (event.keyCode ? event.keyCode : event.which);
}
$(function () {
    $('body').on('keydown', 'input, select, textarea', function (event) {
        if (event.keyCode == 13) {
            var self = $(this),
                form = self.parents('form:eq(0)')
                , focusable
                , next;
            focusable = form.find('input,a,select,button,textarea').filter(':visible:enabled');
            next = focusable.eq(focusable.index(this) + 1);
            if (next && next.length) {
                next.focus();
            } else {
                form.submit();
            }
            return false;
        }
        return true;
    });
});
function setEventId(eventId) {
    $("#_eventId").val(eventId);
}
function setSelectedRow(rowIndex) {
    //$("#SELECTED_ROW_ID").val(rowIndex);
    //$("#"+tableUID+"_selected_rows").val(rowIndex);
    $("#role_selected_rows").val(rowIndex);
}
function setSelectedRows(tableUID) {
    if (!tableUID) {
        tableUID = formObjectName;
    }
   var selectedRows= getSelectedRows(tableUID);
    if (!selectedRows || selectedRows.length == 0) {
        alert("no selected any row");
        return false;
    }
    $("#"+tableUID+"_selected_rows").val(selectedRows);
    console.log(selectedRows);
}

function getSelectedRows(tableUID) {
    if (!tableUID) {
        tableUID = formObjectName;
    }
    var selectedRows = $("input[type=checkbox][name=" + tableUID + "_checkbox]").filter(':checked');
    if (!selectedRows || selectedRows.length == 0) {
        alert("no selected any row");
        return null;
    }
    var rows = "";
    for (var i = 0; i < selectedRows.length - 1; i++) {
        rows += $(selectedRows[i]).data("value") + ";";
    }
    rows += $(selectedRows[i]).data("value");
    return rows;
}

function onSearchClicked() {
    try {
        var searchCriterion = $('#searchField').find('option:selected').attr('criterion');
        console.log(searchCriterion);
        if (!searchCriterion) {
            alert('system.error');
            return false;
        }
        $('#searchCriterion').val(searchCriterion);
        return true;
    } catch (e) {
        throw e;
    }
}
function logout() {
    $.post('${contextPath}/logout', {'${_csrf.parameterName}': '${_csrf.token}'})
        .done(function (data) {
            location.reload();
        });
}
$.fn.dateFormatter = function () {
    this.blur(function (event) {
        var dateStr = this.value;
        if (!dateStr || dateStr == '') {
            return '';
        }
        if (dateStr.length < 6 || dateStr.length > 8) {
            return dateStr;
        }
        try {
            if (dateStr.length == 6) {
                this.value = dateStr.replace(/(\d\d)(\d\d)(\d\d)/g, '$1/$2/$3');
                this.value = '13' + this.value;
            }
            this.value = dateStr.replace(/(\d\d\d\d)(\d\d)(\d\d)/g, '$1/$2/$3');
            return this;
        } catch (e) {
            console.log(e);
            return this;
        }
    });

};
$.fn.digits = function () {
    this.keyup(function (event) {
        this.value = digitGrouping(this.value);
        return this;
    });
};
jQuery.fn.forceNumericOnly =
    function () {
        return this.each(function () {
            $(this).keypress(function (e) {
                var key = e.charCode || e.keyCode || 0;
                if (!isNumeral(key)) {
                    e.preventDefault();
                }
            });
        });
    };


