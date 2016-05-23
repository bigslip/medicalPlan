/**
 * Created with IntelliJ IDEA.
 * User: hadi
 * Date: 9/20/13
 * Time: 11:40 AM
 * To change this template use File | Settings | File Templates.
 */
(function ($) {
    "use strict";

    $.extend($.fn.select2.defaults, {
        formatNoMatches: function () { return MESSAGE_KEY.noMatchFound; },
        formatInputTooShort: function (input, min) { var n = min - input.length; return "Please enter " + n + " more character" + (n == 1 ? "" : "s"); },
        formatInputTooLong: function (input, max) { var n = input.length - max; return "Please enter " + n + " less character" + (n == 1? "" : "s"); },
        formatSelectionTooBig: function (limit) { return "You can only select " + limit + " item" + (limit == 1 ? "" : "s"); },
        formatLoadMore: function (pageNumber) { return "Loading more results..."; },
        formatSearching: function () { return "Searching..."; }
    });
})(jQuery);
