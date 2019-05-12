/*
 * common view
 * Copyright (C) Oracle
 *
 * Oracle all right reserved
 */
var currentDate = new Date();
var twoWeekBefore = new Date();
twoWeekBefore.setDate(currentDate.getDate() - 14);
var rows_selected = [];
function updateDataTableSelectAllCtrl(table){
   var $table             = table.api().table().node();
   var $chkbox_all        = $('tbody input[type="checkbox"]', $table);
   var $chkbox_checked    = $('tbody input[type="checkbox"]:checked', $table);
   var chkbox_select_all  = $('thead input[name="select_all"]', $table).get(0);

   // If none of the checkboxes are checked
   if($chkbox_checked.length === 0){
      chkbox_select_all.checked = false;
      if('indeterminate' in chkbox_select_all){
         chkbox_select_all.indeterminate = false;
      }

   // If all of the checkboxes are checked
   } else if ($chkbox_checked.length === $chkbox_all.length){
      chkbox_select_all.checked = true;
      if('indeterminate' in chkbox_select_all){
         chkbox_select_all.indeterminate = false;
      }

   // If some of the checkboxes are checked
   } else {
      chkbox_select_all.checked = true;
      if('indeterminate' in chkbox_select_all){
         chkbox_select_all.indeterminate = true;
      }
   }
}


$(document).on('click', '#version', function(){
	$.ajax({
        type: "GET",
        url: "/versions",
        data: {},
        complete: function(response) {
            popup(response.responseText);
        }
    });
});

function isHtmlTagStart(value) {
    return (value.indexOf("<") >= 0 || value.indexOf("&lt;") >= 0 || value.indexOf("&#60;") >= 0) ;
}

function isHtmlTagEnd(value) {
    return (value.indexOf(">") >= 0 || value.indexOf("&gt;") >= 0 || value.indexOf("&#62;") >= 0);
}

function detectHtml(value) {
    if( isHtmlTagStart(value) && isHtmlTagEnd(value) ) {
        popup("Html input is not allowed!!!");
        throw new Error("Html input is not allowed!!!");
    }
    return value;
}

function popup(response) {
	var info = '';
	if(response !== null) {
		if(typeof response === 'string' || response instanceof String) {
			info = response;
		} else {
			info = response.responseText;
		}
	}
    $('<div></div>').appendTo('body')
        .html('<div>'+ info + '</div>')
        .dialog({
            modal: true,
            title: 'Info',
            zIndex: 10000,
            autoOpen: true,
            width: 'auto',
            resizable: false,
            buttons: {
                'OK': function () {
                          $(this).dialog("close");
                      }
            },
            close: function () {
                      $(this).remove();
                   }
        });
}

function resultPopup(response) {
	var popup_title = 'FAIL';
	if(response !== null && response.responseJSON !== null && response.responseJSON.success === true) {
		popup_title = 'SUCCESS';
	}
	if(response !== null) {
		if(response.responseText.indexOf("<html>")>-1) {
			var win = window.open();
			win.document.write(response.responseText);
		} else {
			$('<div></div>').appendTo('body')
				.html('<div>'+ response.responseText + '</div>')
				.dialog({
					modal: true,
					title: popup_title,
					zIndex: 10000,
					autoOpen: true,
					width: 'auto',
					resizable: false,
					buttons: {
						'OK': function () {
							$(this).dialog("close");
							if(window.location.pathname.indexOf("job_view") >= 0) {
								refreshTable(table);
							}
						}
					},
					close: function () {
						$(this).remove();
						if(window.location.pathname.indexOf("job_view") >= 0) {
							refreshTable(table);
						}
					}
			});
		}
	}
}

function refreshTable(table, after_deleted = false) {
	var ajax_url = table.api().ajax.url();
	if(ajax_url.indexOf("startDate") >= 0) {
		table.api().clear();
		table.api().ajax.reload();
	} else {
		var page_num = table.api().page.info().page;
		if(after_deleted) {
			table.api().clear();
			table.api().ajax.reload();
		} else {
			table.api().ajax.reload(null, false);
		}
		table.api().search( $('div.dataTables_filter input').val() ).draw();
		table.api().page( page_num ).draw(false);
	}
	rows_selected = [];
}
