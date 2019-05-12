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
//            "order": [[ 1, "desc" ]],
//            "processing": true,
//            "paging": true,
//            "searching": true,
//            "pagingType": "full_numbers"
        }
    );
});
