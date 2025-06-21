'use strict';
// Class definition

var KTDatatableModal = function () {

    var initDatatable = function () {
        var el = $('#kt_datatable');

        var datatable = el.KTDatatable({
            // datasource definition
            data: {
                type: 'remote',
                source: {
                    read: {
                        method: 'GET',
                        url: serverBaseUrl + requestPath, //serverBaseUrl => js/custom/ajax.js
                    },
                },
                // type: 'local',
                // source: data,
                pageSize: 10, // display 20 records per page
                serverPaging: false,
                serverFiltering: false,
                serverSorting: false,
            },

            // layout definition
            layout: {
                theme: 'default',
                scroll: false,
                height: null,
                footer: false,
            },

            // column sorting
            sortable: true,

            pagination: true,

            search: {
                input: el.find('#kt_datatable_search_query'),
                key: 'generalSearch'
            },

            // columns definition
            columns: TableColumn,
        });

        var card = datatable.closest('.card');
        var queryString = window.location.search;
        var urlParams = new URLSearchParams(queryString);
        var search_type = urlParams.get('type');

        if (!search_type == "") {
            $("#kt_datatable_search_status select").val(search_type.toLowerCase()).change();
            datatable.search(search_type.toLowerCase(), 'status');
        }

        $('#kt_datatable_search_status').on('change', function () {
            datatable.search($(this).val().toLowerCase(), 'status');
        });

        $('#kt_datatable_search_type').on('change', function () {
            datatable.setDataSourceParam("type", $(this).val());
            datatable.reload();
        });

        $('#kt_datatable_search_status, #kt_datatable_search_type').selectpicker();

        $('#kt_datatable_search_schedule').on('change', function () {
            datatable.setDataSourceParam("schedule", $(this).val());
            datatable.reload();
        });

        $('#kt_datatable_search_query').on('keyup', function() {
            datatable.search($(this).val().toLowerCase(), 'title');
        });

        datatable.on('click', '[data-event-id]', function () {
            initSubDatatable($(this).data('event-id'));
            $('#kt_datatable_modal').modal('show');
        });

    };

    var initSubDatatable = function (id) {
        var el = $('#kt_datatable_sub');
        var datatable = el.KTDatatable({
            data: {
                type: 'remote',
                source: {
                    read: {
                        method: 'GET',
                        url: serverBaseUrl + modal_requestPath + id,
                    },
                },
                pageSize: 10,
                serverPaging: false,
                serverFiltering: false,
                serverSorting: false,
            },

            // layout definition
            layout: {
                theme: 'default',
                scroll: false,
                height: null,
                footer: false,
            },

            search: {
                input: el.find('#kt_datatable_search_query_2'),
                key: 'generalSearch'
            },

            sortable: true,

            // columns definition
            columns: TableColumn_modal,
        });

        var modal = datatable.closest('.modal');

        $('#kt_datatable_search_type_2').on('change', function () {
            datatable.search($(this).val(), 'Type');
        });

        $('#kt_datatable_search_status_2, #kt_datatable_search_type_2').selectpicker();

        // fix datatable layout after modal shown
        datatable.hide();
        modal.on('shown.bs.modal', function () {
            var modalContent = $(this).find('.modal-content');
            datatable.spinnerCallback(true, modalContent);
            datatable.spinnerCallback(false, modalContent);
        }).on('hidden.bs.modal', function () {
            el.KTDatatable('destroy');
        });

        datatable.on('datatable-on-layout-updated', function () {
            datatable.show();
            datatable.redraw();
        });
    };

    return {
        // public functions
        init: function () {
            initDatatable();
            initSubDatatable();
        }
    };
}();

jQuery(document).ready(function () {
    KTDatatableModal.init();
});