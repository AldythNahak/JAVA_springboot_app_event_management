var queryString = window.location.search;
var urlParams = new URLSearchParams(queryString);
var search_param = urlParams.get("search");

$(function () {
  $("#kt_header_menu > ul > li a").click(function () {
    $("#kt_header_menu > ul > li").removeClass(
      "menu-item-open menu-item-here menu-item-submenu menu-item-rel"
    );
    if (
      !$(this)
        .parents(".menu-item")
        .hasClass(
          "menu-item-open menu-item-here menu-item-submenu menu-item-rel"
        )
    ) {
      $(this)
        .parents(".menu-item")
        .toggleClass(
          "menu-item-open menu-item-here menu-item-submenu menu-item-rel"
        );
    }
  });

  if (search_param != null) {
    urlEvent = new URL(serverBaseUrl + "/event?title=" + search_param);
    $("#search_result_main_page").append(createSearchResult(search_param));
    getEventListCard(urlEvent);
  } else {
    getEventListCard(new URL(serverBaseUrl + "/event"));
  }
});

$("#menu-list a").click(function () {
  $("#search_result_main_page").remove();
  var urlEvent = new URL(serverBaseUrl + "/event");
  urlEvent.searchParams.set("type", $(this).attr("value"));
  getEventListCard(urlEvent);
});

function getEventListCard(urlEvent) {
  ajax_get(urlEvent, callbackGet);
  function callbackGet(data) {
    $("#event_pagination").pagination({
      className: "paginationjs-theme-blue paginationjs-big",
      dataSource: data,
      showGoInput: true,
      showGoButton: true,
      pageSize: 3,
      callback: function (data, pagination) {
        $("#event_list").empty();
        $.each(data, function (i, eventData) {
          $("#event_list").append(createEventCard(eventData));
        });
        $("[data-toggle=popover]").popover();
      },
    });
  }
}

function createSearchResult(searchResult) {
  var html_searchResult =
    `<div class="d-flex justify-content-center mb-5">
          <div class="header-menu header-menu-mobile header-menu-layout-default">
              <div class="menu-nav">
                  <div class="menu-item menu-item-open menu-item-here menu-item-submenu menu-item-rel"
                      aria-haspopup="true">
                      <div href="#" class="menu-link">
                          <span class="menu-text">
                              Result for '` +
    searchResult +
    `' :
                          </span>
                      </div>
                  </div>
              </div>
          </div>
      </div>`;
  return html_searchResult;
}

function createEventCard(data) {
  // $("#event_list").empty();
  var html_eventList = [];

  var s_start_time;
  var s_end_time;
  var s_location;

  if (data.schedulers.length > 1) {
    var s_time = [];
    var e_time = [];
    var sch_location = [];
    for (var j = 0; j < data.schedulers.length; j++) {
      var _online = data.schedulers[j].isOnline ? "- online" : "";
      s_time.push(
        `
                    Day ` +
          (j + 1) +
          `
                    <span class='label label-inline font-weight-bold label-light-primary'>` +
          moment(data.schedulers[j].startTime).format("YYYY-MM-DD hh:mm A") +
          `</span> <br>
                `
      );
      e_time.push(
        `
                    Day ` +
          (j + 1) +
          `
                    <span class='label label-inline font-weight-bold label-light-warning'>` +
          moment(data.schedulers[j].endTime).format("YYYY-MM-DD hh:mm A") +
          `</span> <br>
                `
      );
      sch_location.push(
        `
                    Day ` +
          (j + 1) +
          `
                    <span class='label label-inline font-weight-bold label-light-info'>` +
          data.schedulers[j].location +
          _online +
          `</span><br>
                `
      );
    }
    s_start_time =
      `<button type="button" class="btn btn-light-primary btn-sm font-weight-bold btn-upper btn-text" data-toggle="popover" title="Start Time" data-html="true" 
            data-content="` +
      s_time.join("") +
      `">
                click to see
            </button>`;
    s_end_time =
      `<button type="button" class="btn btn-light-warning btn-sm font-weight-bold btn-upper btn-text" data-toggle="popover" title="End Time" data-html="true" 
            data-content="` +
      e_time.join("") +
      `">
                click to see
            </button>`;
    s_location =
      `<button type="button" class="btn btn-light-info btn-sm font-weight-bold btn-upper btn-text" data-toggle="popover" title="End Time" data-html="true" 
            data-content="` +
      sch_location.join("") +
      `">
                <i class="fas fa-map-marked-alt text-info mr-5"></i>click to see
            </button>`;
  } else {
    var _online = data.schedulers[0].isOnline
      ? "flaticon-laptop text-success"
      : "flaticon-map-location text-info";
    s_start_time =
      `<span class="btn btn-light-primary btn-sm font-weight-bold btn-upper btn-text">` +
      moment(data.schedulers[0].startTime).format("YYYY-MM-DD hh:mm A") +
      `</span>`;
    s_end_time =
      `<span class="btn btn-light-warning btn-sm font-weight-bold btn-upper btn-text">` +
      moment(data.schedulers[0].endTime).format("YYYY-MM-DD hh:mm A") +
      `</span>`;
    s_location =
      `<span class="font-weight-bolder font-size-h5 pt-1"><span class="font-weight-bold text-dark-50"><i class="` +
      _online +
      ` mr-5"></i></span>` +
      data.schedulers[0].location +
      `</span>`;
  }
  var status;
  switch (data.status) {
    case 1:
      status = "Up Comming";
      statusState = "success";
      break;
    case 2:
      status = "On Going";
      statusState = "warning";
      break;
    case 3:
      status = "Ended";
      statusState = "danger";
      break;
    case 4:
      status = "Canceled";
      statusState = "danger";
      break;
    default:
      status = "";
      statusState = "";
  }
  var joinable = "";
  if (
    data.quota > data.participantQuantity &&
    data.status == 1 &&
    moment(Date.now()).isBefore(moment(data.registrationDeadline))
  ) {
    joinable =
      `<a href="/event/` +
      data.id +
      `/checkout"
                  class="btn btn-primary btn-sm text-uppercase font-weight-bolder mt-5 mt-sm-0 mr-auto mr-sm-0 ml-sm-auto">JOIN</a>`;
  }

  html_eventList.push(
    `
        <div class="col-xl-4">
        <!--begin::Card-->
        <div class="card card-custom gutter-b card-stretch mycard">
            <!--begin::Body-->
            <div class="card-body">
                <!--begin::Section-->
                <div class="d-flex align-items-center">
                    <!--begin::Pic-->
                    <div class="flex-shrink-0 mr-4 symbol symbol-65 symbol-circle">
                        <img src="data:image/png;base64, ` +
      data.image +
      `"
                            alt="image" />
                    </div>
                    <!--end::Pic-->
                    <!--begin::Info-->
                    <div class="d-flex flex-column mr-auto">
                        <!--begin: Title-->
                        <a href="/event/` +
      data.id +
      `"
                            class="card-title text-hover-primary font-weight-bolder font-size-h5 text-dark mb-1">` +
      data.title +
      `</a>
                        <!--end::Title-->
                    </div>
                    <!--end::Info-->
                </div>
                <!--end::Section-->
                <!--begin::Content-->
                <div class="d-flex flex-wrap mt-14">
                    <div class="mr-4 d-flex flex-column mb-3">
                        <span class="d-block font-weight-bold mb-4">Start Date</span>
                        ` +
      s_start_time +
      `
                    </div>
                    <div class="mr-4 d-flex flex-column mb-3">
                        <span class="d-block font-weight-bold mb-4">Due Date</span>
                        ` +
      s_end_time +
      `
                    </div>
                    <div class="mr-4 d-flex flex-column mb-3">
                        <span class="d-block font-weight-bold mb-4">Registration Deadline</span>
                        <span class="btn btn-light-danger btn-sm font-weight-bold btn-upper btn-text">` +
      moment(data.registrationDeadline).format("YYYY-MM-DD hh:mm A") +
      `</span>
                    </div>
                </div>
                <!--end::Content-->
                <!--begin::Text-->
                <p class="mb-7 mt-3">` +
      data.description +
      `</p>
                <!--end::Text-->
                <!--begin::Blog-->
                <div class="d-flex flex-wrap">
                    <!--begin: Item-->
                    <div class="mr-4 d-flex flex-column mb-7">
                        <span class="font-weight-bolder mb-4">Location</span>
                        ` +
      s_location +
      `
                    </div>
                    <!--end::Item-->
                    <!--begin::Item-->
                    <div class="mr-4 d-flex flex-column mb-7">
                        <span class="font-weight-bolder mb-4">Contact Person</span>
                        <span class="font-weight-bolder font-size-h5 pt-1">
                            <span class="font-weight-bold text-dark-50"><i
                                    class="far fa-address-card text-primary mr-5"></i></span>` +
      data.contactPerson +
      `</span>
                    </div>
                    <!--end::Item-->
                    <!--begin::Item-->
                    <div class="mr-12 d-flex flex-column mb-7">
                        <span class="font-weight-bolder mb-4">Quota</span>
                        <span class="font-weight-bolder font-size-h6 pt-1">
                            <span class="font-weight-bold text-dark-50"><i
                                    class="fas fa-users text-success mr-5"></i></span>` +
      (data.quota - data.participantQuantity) +
      ` available</span>
                    </div>
                    <!--end::Item-->
                    <!--begin::Item-->
                    <div class="mr-12 d-flex flex-column mb-7">
                        <span class="font-weight-bolder mb-4">Status</span>
                        <span class="font-weight-bolder font-size-h6 pt-1">
                            <span class="font-weight-bold text-dark-50"><i
                                    class="fas fa-info-circle mr-5 text-` +
      statusState +
      `"></i></span>` +
      status +
      ` </span>
                    </div>
                    <!--end::Item-->
                </div>
                <!--end::Blog-->
            </div>
            <!--end::Body-->
            <!--begin::Footer-->
            <div class="card-footer d-flex align-items-center">
                ` +
      joinable +
      `
            </div>
            <!--end::Footer-->
        </div>
        <!--end::Card-->
    </div>
        `
  );

  return html_eventList.join("");
}
