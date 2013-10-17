function newEntry(table,row,cell){
    document.getElementById('form_ppeCalendar:'+table+':'+row+':'+'hiddenBtn'+cell).click(); 
}  

function handleInsert(xhr, status, args, form) {  
    if(!args.validationFailed){
        if(!args.conflict) { 
            hiddenInsert = document.getElementById(''+form+':hiddenInsert').click();
            dlg_new_ppe.hide();
        } else { 
            dlg_confirm_insert.show();
        }  
    } else {
       dlg_new_ppe.jq.effect("shake", { times:2 }, 100);
    }
}

function handleInsertSimple(args, form) {  
    if(!args.validationFailed){
        document.getElementById(form+':hiddenInsert').click();
        dlg_new_entity.hide();
    } else {
       dlg_new_entity.jq.effect('shake', { times:2 }, 100);
    }
}
function handleDelete(form){
    document.getElementById(form+':hiddenDelete').click();
    dlg_confirm_delete.hide();
}
PrimeFaces.widget.Dialog.prototype.applyFocus = function() {
  var firstInput = this.jq.find(':not(:submit):not(:button):input:visible:enabled:first');
  if(!firstInput.hasClass('hasDatepicker')) {
      firstInput.focus();
  }
}

PrimeFaces.locales['de'] = {
    closeText: 'Schließen',
    prevText: 'Zurück',
    nextText: 'Weiter',
    monthNames: ['Januar', 'Februar', 'März', 'April', 'Mai', 'Juni', 'Juli', 'August', 'September', 'Oktober', 'November', 'Dezember'],
    monthNamesShort: ['Jan', 'Feb', 'Mär', 'Apr', 'Mai', 'Jun', 'Jul', 'Aug', 'Sep', 'Okt', 'Nov', 'Dez'],
    dayNames: ['Sonntag', 'Montag', 'Dienstag', 'Mittwoch', 'Donnerstag', 'Freitag', 'Samstag'],
    dayNamesShort: ['Son', 'Mon', 'Die', 'Mit', 'Don', 'Fre', 'Sam'],
    dayNamesMin: ['So', 'Mo', 'Di', 'Mi', 'Do', 'Fr', 'Sa'],
    weekHeader: 'Woche',
    firstDay: 1,
    isRTL: false,
    showMonthAfterYear: false,
    yearSuffix: '',
    timeOnlyTitle: 'Nur Zeit',
    timeText: 'Zeit',
    hourText: 'Stunde',
    minuteText: 'Minute',
    secondText: 'Sekunde',
    currentText: 'Aktuelles Datum',
    ampm: false,
    month: 'Monat',
    week: 'Woche',
    day: 'Tag',
    allDayText: 'Ganzer Tag'
};