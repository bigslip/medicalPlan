/**
 * @author hadi tayebi
 */

function digitGrouping(value) {
    if (value.length > 3) {
        value = value.replace(/,/g, '');
        value = value.replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
    }
    return value;
}

function isNumeral(key) {
    return (key == 8 || key == 9 || (key >= 35 && key <= 40) || key == 46 || (key >= 48 && key <= 57) || (key >= 96 && key <= 105));
}

function startWith(str1, str2) {
    return str1.match(/str2.*/);
    //.substring(0,str2.length)==str2;
}
function endWith(str1, str2) {
    return  str1.match(str2 + '$');
}
function fixedNameMatcher(term, text) {
    return (term == '') || fixString(text).indexOf(fixString(term)) != -1;
}

var replacementCharacters = ['\u064A',
    '\u064A',
    '\u064A',
    '\u0648',
    '\u0627',
    '\u0643'];
var toRemoveCharacters = ['\u0020',
    '.',
    '-',
    '_',
    ','];
var toReplaceCharacters = ['\u06CC',
    '\u0626',
    '\u0621',
    '\u0624',
    '\u0622',
    '\u06A9'];
function fixString(sourceValue) {
    if (!sourceValue || sourceValue == '') {
        return '';
    }
    sourceValue = sourceValue.trim();

    var fixedString = '';
    for (var i = 0; i < sourceValue.length; i++) {
        var appended = false;
        for (var j = toReplaceCharacters.length - 1; j >= 0; j--) {
            if (sourceValue[i] == toReplaceCharacters[j]) {
                fixedString += replacementCharacters[j];
                appended = true;
                break;
            }
        }
        if (!appended) {
            for (j = toRemoveCharacters.length - 1; j >= 0; j--) {
                if (sourceValue[i] == toRemoveCharacters[j]) {
                    break;
                }
                if (j == 0) {
                    fixedString += sourceValue[i];
                }
            }
        }
    }
    return fixedString;
}