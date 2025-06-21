var requestPath = "/ticket/employee/" + empId;
// var modal_requestPath = "/scheduler/event/";

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
    field: "id",
    title: "trx. No",
    sortable: "asc",
  },
  {
    field: "registrationDate",
    title: "Registration Date",
    template: function (row) {
      return new Date(row.registrationDate);
    },
  },
  {
    field: "eventTitle",
    title: "Event Title",
    template: function (row) {
      return (
        `
      <a href="/event/` +
        row.idEvent +
        `" class="btn btn-text-dark btn-icon-success font-weight-bold btn-hover-bg-light btn-sm">\
        ` + row.eventTitle + `\
      </a>
      `
      );
    },
  },
  {
    field: "contactPerson",
    title: "Contact Person",
  },
  {
    field: "actions",
    width: 130,
    title: "Actions",
    sortable: false,
    overflow: "visible",
    textAlign: "left",
    autoHide: false,
    template: function (row, index) {
      var attendance = row.isAttend ? "Join" : "Attend Event";
      return (
        `
      <a href="/attendance?ticket=` +
        row.id +
        `" class="btn btn-text-dark-50 btn-icon-success font-weight-bold btn-hover-bg-light btn-sm">\
        <i class="fas fa-calendar-check"></i> ` + attendance + `\
      </a>
        <a href="/ticket/certificate?ticket=` +
        row.id +
        `" class="btn btn-text-dark-50 btn-icon-primary font-weight-bold btn-hover-bg-light btn-sm" target="_blank">\
          <i class="flaticon-file-2"></i> Certificate\
        </a>
      </form>\
      `
      );
    },
  },
];
