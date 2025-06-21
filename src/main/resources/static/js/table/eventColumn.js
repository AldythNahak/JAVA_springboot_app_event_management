var requestPath = "/event";
var modal_requestPath = "/scheduler/event/";

var TableColumn = [
  {
    field: "no",
    title: "#",
    sortable: false,
    width: 30,
    textAlign: "center",
    template: function (row, index) {
      return index + 1;
    },
  },
  {
    field: "title",
    title: "Event Title",
    sortable: "asc",
  },
  {
    field: "registrationDeadline",
    title: "Registration Deadline",
    template: function (row) {
      return moment(row.registrationDeadline).format("YYYY-MM-DD hh:mm A");
    },
  },
  {
    field: "quota",
    title: "Quota / Participant",
    template: function (row) {
      return row.quota + " / " + row.participantQuantity;
    },
  },
  {
    field: "status",
    title: "Status",
    // callback function support for column rendering
    template: function (row) {
      var status = {
        1: {
          title: "Up Coming",
          class: "label-light-primary",
        },
        2: {
          title: "On Going",
          class: " label-light-success",
        },
        3: {
          title: "Ended",
          class: " label-light-success",
        },
        // 4: {
        //   title: "Canceled",
        //   class: " label-light-danger",
        // },
      };
      return (
        '<span class="label label-lg font-weight-bold' +
        status[row.status].class +
        ' label-inline">' +
        status[row.status].title +
        "</span>"
      );
    },
  },
  {
    field: "schedulers",
    title: "Type",
    autoHide: false,
    // callback function support for column rendering
    template: function (row) {
      var stat = [];
      var status_badge = [];
      var status = {
        true: {
          title: "Online",
          state: "primary",
        },
        false: {
          title: "Offline",
          state: "danger",
        },
      };
      for (var i = 0; i < row.schedulers.length; i++) {
        if (!stat.includes(status[row.schedulers[i].isOnline].title)) {
          stat.push(status[row.schedulers[i].isOnline].title);
          status_badge.push(
            '<span class="label label-' +
              status[row.schedulers[i].isOnline].state +
              ' label-dot mr-2"></span><span class="font-weight-bold text-' +
              status[row.schedulers[i].isOnline].state +
              '">' +
              status[row.schedulers[i].isOnline].title +
              "</span>"
          );
        }
      }
      return status_badge.join("<br>");
    },
  },
  {
    field: "actions",
    width: 130,
    title: "Actions",
    sortable: false,
    overflow: "visible",
    textAlign: "left",
    autoHide: false,
    template: function (row) {
      return (
        `
              <button data-event-id="` +
        row.id +
        `" class="btn btn-sm btn-clean" title="View records">\
                  <i class="flaticon2-document"></i> schedule
              </button>\
	                        <div class="dropdown dropdown-inline">\
	                            <a href="javascript:;" class="btn btn-sm btn-clean btn-icon mr-2" data-toggle="dropdown">\
	                                <span class="svg-icon svg-icon-md">\
	                                    <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="24px" height="24px" viewBox="0 0 24 24" version="1.1">\
	                                        <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">\
	                                            <rect x="0" y="0" width="24" height="24"/>\
	                                            <path d="M5,8.6862915 L5,5 L8.6862915,5 L11.5857864,2.10050506 L14.4852814,5 L19,5 L19,9.51471863 L21.4852814,12 L19,14.4852814 L19,19 L14.4852814,19 L11.5857864,21.8994949 L8.6862915,19 L5,19 L5,15.3137085 L1.6862915,12 L5,8.6862915 Z M12,15 C13.6568542,15 15,13.6568542 15,12 C15,10.3431458 13.6568542,9 12,9 C10.3431458,9 9,10.3431458 9,12 C9,13.6568542 10.3431458,15 12,15 Z" fill="#000000"/>\
	                                        </g>\
	                                    </svg>\
	                                </span>\
	                            </a>\
	                            <div class="dropdown-menu dropdown-menu-sm dropdown-menu-right">\
	                                <ul class="navi flex-column navi-hover py-2">\
	                                    <li class="navi-header font-weight-bolder text-uppercase font-size-xs text-primary pb-2">\
	                                        Choose an action:\
	                                    </li>\
	                                    <li class="navi-item">\
	                                        <a href="/event/${row.id}/edit" target="_blank" class="navi-link">\
	                                            <span class="navi-icon"><i class="flaticon-edit-1"></i></span>\
	                                            <span class="navi-text">Edit</span>\
	                                        </a>\
	                                    </li>\
	                                    <li class="navi-item">\
	                                        <a href="/event/${row.id}/participant" target="_blank" class="navi-link">\
	                                            <span class="navi-icon"><i class="flaticon2-group"></i></span>\
	                                            <span class="navi-text">Participant</span>\
	                                        </a>\
	                                    </li>\
	                                    <li class="navi-item">\
	                                        <a href="#" onclick="deleteEvent(${row.id})" class="navi-link">\
	                                            <span class="navi-icon"><i class="fas fa-trash"></i></span>\
	                                            <span class="navi-text">Delete</span>\
	                                        </a>\
	                                    </li>\
	                                </ul>\
	                            </div>\
	                        </div>\
              `
      );
    },
  },
];

var TableColumn_modal = [
  {
    field: "isOnline",
    title: "Type",
    autoHide: false,
    // callback function support for column rendering
    template: function (row) {
      var status = {
        true: {
          title: "Online",
          state: "primary",
        },
        false: {
          title: "Offline",
          state: "danger",
        },
      };

      return (
        '<span class="label label-' +
        status[row.isOnline].state +
        ' label-dot mr-2"></span><span class="font-weight-bold text-' +
        status[row.isOnline].state +
        '">' +
        status[row.isOnline].title +
        "</span>"
      );
    },
  },
  {
    field: "startTime",
    title: "Start Time",
    template: function (row) {
      return moment(row.startTime).format("YYYY-MM-DD hh:mm A");
    },
  },
  {
    field: "endTime",
    title: "End Time",
    template: function (row) {
      return moment(row.endTime).format("YYYY-MM-DD hh:mm A");
    },
  },
  {
    field: "location",
    title: "Location",
  },
  {
    field: "linkPlatform",
    title: "Link Platform",
    template: function (row) {
      return row.linkPlatform == null ? "-" : row.linkPlatform;
    },
  },
  {
    field: "",
    title: "Actions",
    width: 130,
    title: "Actions",
    sortable: false,
    overflow: "visible",
    textAlign: "left",
    autoHide: false,
    template: function (row) {
      return (
        `
              <a href="/participant/` +
        row.id +
        `" target="_blank" class="btn btn-sm btn-clean">\
                  <i class="flaticon2-group"></i> Attendance
              </button>`
      );
    },
  },
];

function deleteEvent(id) {
  Swal.fire({
    title: "Please Wait!",
    text: "Your Request Still in Progress",
    allowOutsideClick: false,
    allowEscapeKey: false,
    onOpen: function () {
      Swal.showLoading();
    },
  });
  ajax_delete("/ajax/event/" + id, callbackSuccess, callbackError);

  function callbackSuccess(message) {
    Swal.close();
    toastr.success(message, "success");
  }

  function callbackError(message) {
    Swal.close();
    toastr.error(message, "failed");
  }
  $('#kt_datatable').KTDatatable().reload();
}
