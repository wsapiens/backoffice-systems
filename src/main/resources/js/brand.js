var table;
$(document).ready(function() {
    table = $('#brands').dataTable(
        {
            "ajax": { url: "service/v1/brands/inventories",
                      accepts: "application/json; charset=utf-8"
                    },
            "columns": [
                { data: null,
                    searchable: false,
                    orderable: false,
                    width: '1%',
                    className: 'dt-body-center',
                    render: function () {
                                return '<input type="checkbox">';
                            }
                },
                { data: "brandId", "width" : "33%" },
                { data: "brandName", "width" : "33%" },
                { data: "quantity","width" : "33%"}
            ],
//            "aoColumnDefs": [
//                { 'bSortable': false, 'aTargets': [ 1, 2, 3 ] }
//            ],
            "order": [[ 1, "asc" ]],
//            "processing": true,
//            "paging": true,
//            "searching": true,
//            "pagingType": "full_numbers"
        }
    );

    // Handle click on checkbox
    $('#brands tbody').on('click', 'input[type="checkbox"]', function(e){
       var $row = $(this).closest('tr');
       // Get row data
       var data = table.api().row($row).data();
       // Determine whether row ID is in the list of selected row IDs
       var index = $.inArray(data, rows_selected);

       // If checkbox is checked and row ID is not in list of selected row IDs
       if(this.checked && index === -1){
          rows_selected.push(data);
       // Otherwise, if checkbox is not checked and row ID is in list of selected row IDs
       } else if (!this.checked && index !== -1){
          rows_selected.splice(index, 1);
       }

       if(this.checked){
          $row.addClass('selected');
       } else {
          $row.removeClass('selected');
       }

       // Update state of "Select all" control
       updateDataTableSelectAllCtrl(table);

       // Prevent click event from propagating to parent
       e.stopPropagation();
    });

    // Handle click on table cells with checkboxes
    $('#brands').on('click', 'tbody td, thead th:first-child', function(){
       $(this).parent().find('input[type="checkbox"]').trigger('click');
    });

    // Handle click on "Select all" control
    $('thead input[name="select_all"]', table.api().table().container()).on('click', function(e){
       if(this.checked){
          $('#brands tbody input[type="checkbox"]:not(:checked)').trigger('click');
       } else {
          $('#brands tbody input[type="checkbox"]:checked').trigger('click');
       }

       // Prevent click event from propagating to parent
       e.stopPropagation();
    });

    // Handle table draw event
    table.on('draw', function(){
       // Update state of "Select all" control
       updateDataTableSelectAllCtrl(table);
    });
});


$(document).on('click', '#add_brand', function(){
	$('<div></div>').appendTo('body')
	.html('<div><h5>Do you want to add new brand with unique name?</h5></div>' +
			'Name: <input id="brand_name" name="brand_name" type="text"><br><br>')
	.dialog({
		model: true,
		title: 'Add Brand',
		zIndex: 10000,
		autoOpen: true,
		width: 'auto',
		resizable: true,
		buttons: {
			Save: function() {
				var brand_name = detectHtml( $('input[name="brand_name"]').val() );
				if(brand_name == null || brand_name === "") {
					popup("Please Enter Brand Name!");
				} else {
					$.ajax({
						url:"service/v1/brands",
						type: "POST",
						data: JSON.stringify({brands:[{name:brand_name}]}),
						contentType: "application/json; charset=utf-8",
						dataType: "json",
						statusCode: {
							200: function() {
								refreshTable(table);
							},
							400: function(response) {
								resultPopup(response);
							},
							401: function() {
								location.reload();
							},
							500: function(response) {
								resultPopup(response);
							}
						}
					});
					$(this).dialog("close");
				}
			},
			Cancel: function () {
				$(this).dialog("close");
			}
		},
		close: function() {
			$(this).remove();
		}
	});
});


$(document).on('click', '#delete_brand', function(){
	if(0 === rows_selected.length) {
		popup("Please Select Brands by checking CheckBoxes");
	} else {
		$('<div></div>').appendTo('body')
		.html('<div><h5>Do you want to delete ' + rows_selected.length + ' property?</h5></div>')
		.dialog({
			model: true,
			title: 'Delete Brands',
			zIndex: 10000,
			autoOpen: true,
			width: 'auto',
			resizable: true,
			buttons: {
				Delete: function() {
					var delete_candidates = [];
					for(let i=0; i < rows_selected.length; i++) {
						delete_candidates.push({id:rows_selected[i].brandId,name:rows_selected[i].brandName});
					}
					$.ajax({
						url:"service/v1/brands",
						type: "DELETE",
						data: JSON.stringify({brands:delete_candidates}),
						contentType: "application/json; charset=utf-8",
						dataType: "json",
						statusCode: {
							200: function() {
								refreshTable(table);
							},
							400: function(response) {
								resultPopup(response);
							},
							401: function() {
								location.reload();
							},
							500: function(response) {
								resultPopup(response);
							}
						}
					});
					$(this).dialog("close");
				},
				Cancel: function () {
					$(this).dialog("close");
				}
			},
			close: function() {
				$(this).remove();
			}
		});
	}
});