var _eventId = $("#_eventId").val();
var _firstSchedule = $("#_firstSchedule").val();
var requestPath = "/ticket/event/" + _eventId + "?schedule=" + _firstSchedule;
var modal_requestPath = "";
var TableColumn_modal = [];

var TableColumn = [{
    field: 'no',
    title: '#',
    sortable: false,
    width: 30,
    textAlign: 'center',
    template: function (row, index) {
        return index + 1;
    },
}, {
    field: 'name',
    title: 'Name',
    sortable: 'asc',
}, {
    field: 'registrationDate',
    title: 'Registration Date',
    template: function (row) {
        return new Date(row.registrationDate);
    },
}, {
    field: 'contactPerson',
    title: 'CP',
}, {
    field: 'isAttend',
    title: 'Attendance',
    template: function (row) {
        var attendState = row.isAttend ? "PRESENTED" : "ABSENTED";
        var attendClass = row.isAttend ? "primary" : "danger";
        return '</span><span class="font-weight-bold text-' + attendClass + '">' + attendState + '</span>';
    },
}, {
    field: "actions",
    width: 130,
    title: 'Actions',
    sortable: false,
    overflow: 'visible',
    textAlign: 'left',
    autoHide: false,
    template: function (row) {
        if (row.isAttend) {
            return ``;
        } else {
            return `
                  <button type="button" onClick="attendance('`+row.id+`');" class="btn btn-sm btn-clean">\
                      <i class="fas fa-calendar-check"></i> Attendance
                  </button>`;
        }
    },
}];

function attendance(id) {
    var json_id = {
        idTicket: id
    };
    
    Swal.fire({
        title: "Are you sure?",
        text: "this person is attend this event?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Yes!"
    }).then(function(result) {
        if (result.value) {
            ajax_post(serverBaseUrl + "/attendance", JSON.stringify(json_id), callScs, callErr, callData);

            function callData(data) {
                if (data.started) {
                    toastr.success("user attended", "success");
                } else {
                    toastr.error("user not attended", "error");
                }
            }

            function callScs(message) {
            }
        
            function callErr(message) {
                toastr.error("there is a problem", "error");
            }
        }
    });
    

}