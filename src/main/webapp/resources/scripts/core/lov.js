/**
 * @author: hadi tayebi
 */

var ENTITY_LIST_URL;
var ENTITY_INFO_URL;
var noMatchFoundMsg;
var loadingDataMsg;
var LOAD_LOV_SHORT_KEY = 120;
var SEARCH_LOV_SHORT_KEY = 32;
var CLOSE_DIALOG_SHORT_KEY = 27;
var PRIMARY_FIELD_SUFFIX = "_primaryField";
var LABEL_FIELD_SUFFIX = "_labelField";
var showDialog = false;
var currentLov = null;
var PREVIOUS_PAGE = 'PREVIOUS_PAGE';
var NEXT_PAGE = 'NEXT_PAGE';
var LAST_PAGE = 'LAST_PAGE';
var FIRST_PAGE = 'FIRST_PAGE';

$.fn.lovComponent = function (options) {
    var model = new Model();
    model.entityName = this.attr('entityName');
    model.primaryFieldElement = this;
    model.target = this.attr('target').replace(/\./g, '\\.');
    model.labelFieldElement = $('#' + model.target + LABEL_FIELD_SUFFIX);
    if (!options) {
        options = [];
    }
    return new Lov(model, options);
};
function Model() {
    this.target = null;
    this.primaryFieldElement = null;
    this.labelFieldElement = null;
    this.entityName = '';
    this.primaryField = '';
    this.labelField = '';
    this.searchField = '';
}

/**
 * @param {Model} model
 * @param {Array} options
 */
function Lov(model, options) {
    this.model = model;
    this.options = options;
    this.totalPage = 0;
    this.totalRow = 0;
    this.currentPage = 1;
    this.entityListURL = options['entityListURL'];
    this.entityInfoURL = options['entityInfoURL'];
    if (this.entityListURL == null || this.entityListURL == undefined || this.entityListURL == '') {
        this.entityListURL = ENTITY_LIST_URL;
    }
    if (this.entityInfoURL == null || this.entityInfoURL == undefined || this.entityInfoURL == '') {
        this.entityInfoURL = ENTITY_INFO_URL;
    }
    this.entityFields = [];
    this.listEntity = [];
    this.searchableFields = [];
    this.anamitionTime = 600;
    this.setup();
}
Lov.prototype = {
    setup: function () {
        var that = this;
        this.getEntityInfo();

        var count = 0;
        for (var j = 0; j < this.entityFields.length; j++) {
            if (this.entityFields[j]['searchable']) {
                this.searchableFields[count++] = this.entityFields[j];
            }
        }

        this.model.primaryFieldElement.keydown(function (event) {
            if (getKeyCode(event) == LOAD_LOV_SHORT_KEY) {
                currentLov = that;
                that.loadLOV();
            }
        });
    }, loadLOV: function () {
        if (!this.entityName) {
            throw 'entity undefined !';
        }
        this.prepareList();
        this.showDialog();

    }, validation: function (model, searchField, searchValue) {
        return true;
    },
    createWhereClause: function (model, searchField, searchValue) {
        if (searchValue == '') {
            return '';
        }
        return searchField + "='" + searchValue + "'";
    }, cleanUp: function () {
        this.totalPage = 0;
        this.totalRow = 0;
        this.listEntity = [];
        $("#dialog-entityList-body").html('<tr><td colspan="10" style="font-weight: bold;background-color: #ffffff;">' + loadingDataMsg + '</td></tr>');
        $('#paginationState').text('');

    }, showDialog: function () {
        var dialogBox = $("#dialog-box");
        dialogBox.fadeIn(this.anamitionTime);
        dialogBox.dialog('open');
        var searchableField = $('#searchableField');

        if (this.searchableFields.length == 1) {
            searchableField.parent().html('<label style="font-weight: bolder;font-family: tahoma 12px" id="searchableField" for="searchField" >' + this.searchableFields[0]['titleKey'] + '</label>');
        } else {
            var selectElementHtml = '';
            for (var j = 0; j < this.searchableFields.length; j++) {
                selectElementHtml += '<option value=\"' + this.searchableFields[j]['name'] + '\">' + this.searchableFields[j]['titleKey'] + '</option>';
            }
            searchableField.html(selectElementHtml);
        }
        var searchField = $("#searchField");
        searchField.focus();
        searchField.val(this.model.primaryFieldElement.val());
        showDialog = true;
        var that = this;

        $('#lov-searchButton').click(function () {
            that.prepareList();
        });
        searchField.keydown(function (event) {
            if (getKeyCode(event) == 13) {
                that.prepareList();
            }
        });

        $("#dialog-entityList-body").on("click", "tr", function (event) {
            event.preventDefault();
            $("#dialog-entityList-body").html('');
            currentLov.onSelectRow(this.id);
            currentLov.closeDialog();
        });

    },
    onSelectRow: function (indx) {
        var entity = this.listEntity[indx];
        this.model.primaryFieldElement.val(entity[this.model.primaryField]);
        this.model.labelFieldElement.val(entity[this.model.labelField]);
        document.getElementById(this.model.target).value = entity.id;
    },
    closeDialog: function () {
        showDialog = false;
        $("#dialog-box").dialog('close');
        this.model.primaryFieldElement.focus();
        this.cleanUp();
    },
    updateDialog: function () {
        var list = this.listEntity;
        var tHeader = $("#dialog-entityList-header");
        tHeader.html('');
        var entityFields = this.entityFields;
        var fieldCount = entityFields.length;
        var j;
        var html = '<tr>';
        html += '<th>#</th>';
        for (j = 0; j < fieldCount; j++) {
            if (entityFields[j]['visible']) {
                html += '<th>' + entityFields[j]['titleKey'] + '</th>';
            }
        }
        html += '</tr>';
        tHeader.append(html);
        var tBody = $("#dialog-entityList-body");
        tBody.html('');
        var listCount = list.length;
        if (!listCount || listCount == 0) {
            html = '<tr><td style="text-align: right;" colspan="6">' + noMatchFoundMsg + '</td></tr>';
            tBody.html(html);
            return;
        }

        for (var i = 0; i < listCount; i++) {
            var entity = list[i];
            html = '<tr id="' + i + '">';
            html += '<td>' + (i + 1) + '</td>';
            for (j = 0; j < fieldCount; j++) {
                if (entityFields[j].visible) {
                    var fieldName = entityFields[j]['name'];
                    var cssClass = (j % 2 == 0) ? ' even ' : ' odd ';
                    cssClass += '  ';
                    html += '<td class="' + cssClass + '">' + entity[fieldName] + '</td>';
                }
            }
            html += '</tr>';
            tBody.append(html);
        }
        html = null;
        $('#paginationState').text('(' + this.currentPage + ' / ' + this.totalPage + ')');
    },
    prepareList: function () {
        this.cleanUp();
        var searchValue;
        var searchField;
        if (showDialog) {
            if (this.searchableFields.length == 1) {
                searchField = this.searchableFields[0]['name'];
            } else {
                searchField = $('#searchableField').find('option:selected').val();
            }
            searchValue = $('#searchField').val().replace(/%/g, '@');
        } else {
            searchValue = this.model.primaryFieldElement.val();
            searchField = this.model.primaryField;
        }

        this.validation(this.model, searchField, searchValue);

        var whereClause = this.createWhereClause(this.model, searchField, searchValue);

        var requestParameters = "entityName=" + this.entityName + "&clause=" + whereClause + "&currentPage=" + this.currentPage;
        var that = this;
        this.sendRequest(this.entityListURL, requestParameters, function (data) {
            if (data.success) {
                that.totalPage = parseInt(data['totalPage']);
                that.totalRow = parseInt(data['totalRow']);
                that.listEntity = data.result;
            } else {
                throw data.errorMessage;
            }
            that.updateDialog();
        });

    },
    sendRequest: function (requestURL, requestParameters, handler) {
        try {
            $.ajax({
                async: false,
                url: requestURL,
                type: 'GET',
                dataType: 'JSON',
                contentType:'application/json;charset=UTF-8',
                data: requestParameters,
                success: handler});
        } catch (e) {
            console.log(e);
            throw e;
        }
    },
    getEntityInfo: function () {
        var that = this;
        this.sendRequest(this.entityInfoURL, 'entityName=' + this.model.entityName, function (data) {
            if (data.success) {
                that.entityName = that.model.entityName;
                that.entityFields = data['entityFields'];
                that.model.primaryField = data['primaryField'];
                that.model.labelField = data['labelField'];
                that.model.searchField = data['searchField'];
            } else {
                throw data.errorMessage;
            }
        });
    }
};
$(document).keypress(function (event) {
    if (showDialog) {
        var keyCode = getKeyCode(event);
        if (keyCode == CLOSE_DIALOG_SHORT_KEY) {
            event.preventDefault();
            currentLov.closeDialog();
        }
    }
});

function getKeyCode(event) {
    return  (event.keyCode ? event.keyCode : event.which);
}
function changePage(command, event) {
    event.preventDefault();
    if (command == NEXT_PAGE) {
        currentLov.currentPage++;
        if (currentLov.currentPage > currentLov.totalPage) {
            currentLov.currentPage = 1;
        }
    } else if (command == FIRST_PAGE) {
        currentLov.currentPage = 1;
    } else if (command == PREVIOUS_PAGE) {
        currentLov.currentPage--;
        if (currentLov.currentPage < 1) {
            currentLov.currentPage = currentLov.totalPage;
        }
    } else if (command == LAST_PAGE) {
        currentLov.currentPage = currentLov.totalPage;
    }
    currentLov.prepareList();
}