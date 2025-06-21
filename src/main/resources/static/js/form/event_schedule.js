$(function () {
    if (path_url[3] == "edit") { //path from wizard
        ajax_get(serverBaseUrl + "/event/" + _idEvent, callbackScs);

        function callbackScs(data) {
            disableInput(data);
            $('#kt_form').find('input#kt_touchspin_4').val(data.schedulers.length);
            generateHtmlSchedule(data.schedulers.length, data);

            $("#kt_touchspin_4").change(function () {
                if ($(this).val() < data.schedulers.length) {
                    $('#kt_form').find('input#kt_touchspin_4').val(data.schedulers.length);
                    toastr.error("Please delete old Schedule First", "error");
                }
                generateHtmlSchedule($(this).val(), data);
            });
        }
    } else {
        generateHtmlSchedule($("#kt_touchspin_4").val());
        $("#kt_touchspin_4").change(function () {
            generateHtmlSchedule($("#kt_touchspin_4").val());
        });
    }
});

function generateHtmlSchedule(dayInput, data) {
    $("#event_schedule").empty();
    var html_schedule = [];
    var sch_id = [];

    for (var i = 1; i <= dayInput; i++) {

        if (data != null) {
            if (i > data.schedulers.length) {
                sch_id[i] = "";
            } else {
                sch_id[i] = data.schedulers[(i - 1)].id;
            }
        }

        if (data != null && sch_id[i] != "") {
            var button_delete = `<button type="button" onClick="deleteSchedule('` + sch_id + `');" class="btn btn-icon btn-sm btn-light-danger mr-1">\
                                <i class="flaticon2-trash icon-nm"></i>\
                                </button>`;
        } else {
            var button_delete = ``;
        }

        html_schedule.push(`
        <div class="card card-custom card-collapsed card-schedule" data-card="true" id="kt_card_` + i + `">
            <div class="card-header">
                <div class="card-title">
                    <h3 class="card-label">
                        <small>Day ` + i + `</small>
                    </h3>
                </div>
                <div class="card-toolbar">
                    ` + button_delete + `
                    <a href="#" class="btn btn-icon btn-sm btn-light-primary mr-1"
                        data-card-tool="toggle">
                        <i class="ki ki-arrow-down icon-nm"></i>
                    </a>
                </div>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-xl-4">
                        <!--begin::Input-->
                        <div class="form-group">
                        <input type="hidden" name="schedulers.id"/>
                            <label>Event Platform</label>
                            <div class="radio-inline">
                                <label class="radio radio-outline radio-success">
                                    <input type="radio" name="schedulers.isOnline` + i + `" class="offline_check` + i + `" id="offline_check` + i + `" value="false" checked="checked" />
                                    <span></span>
                                    Offline
                                </label>
                                <label class="radio radio-outline radio-success">
                                    <input type="radio" name="schedulers.isOnline` + i + `" class="offline_check` + i + `" id="online_check` + i + `" value="true" />
                                    <span></span>
                                    Online
                                </label>
                            </div>
                        </div>
                        <!--end::Input-->
                    </div>
                    <block id="e_s_location_offline` + i + `">
                        <div class="col-xl-8">
                            <div class="form-group">
                                <label>Location</label>
                                <input type="text"
                                    class="form-control form-control-solid form-control-lg"
                                    placeholder="enter a location" name="schedulers.location"style="width:auto;"/>
                                <span class="form-text text-muted">Please enter Location</span>
                            </div>
                        </div>
                    </block>
                    <block id="e_s_location_online` + i + `">
                        
                    </block>
                </div>
                <div class="form-group">
                    <label>Event Schdeule</label>
                    <div class="col-lg-12 col-md-9 col-sm-12">
                        <div class="row">
                            <div class="col">
                            <label>Start Time</label>
                                <div class="input-group date" id="kt_datetimepicker_` + i + `"
                                    data-target-input="nearest">
                                    <input type="text" class="form-control datetimepicker-input form-control-solid form-control-lg"
                                        placeholder="Start date" name="schedulers.startTime"
                                        data-target="#kt_datetimepicker_` + i + `" />
                                    <div class="input-group-append"
                                        data-target="#kt_datetimepicker_` + i + `"
                                        data-toggle="datetimepicker">
                                        <span class="input-group-text">
                                            <i class="ki ki-calendar"></i>
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <div class="col">
                            <label>End Time</label>
                                <div class="input-group date" id="kt_datetimepicker_` + i + `_2"
                                    data-target-input="nearest">
                                    <input type="text" class="form-control datetimepicker-input form-control-solid form-control-lg"
                                        placeholder="End date" name="schedulers.endTime"
                                        data-target="#kt_datetimepicker_` + i + `_2" />
                                    <div class="input-group-append"
                                        data-target="#kt_datetimepicker_` + i + `_2"
                                        data-toggle="datetimepicker">
                                        <span class="input-group-text">
                                            <i class="ki ki-calendar"></i>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>`);
    }
    $("#event_schedule").html(html_schedule.join(''));
    
    $("#kt_datetimepicker_r").datetimepicker('destroy');
    if (data != null) {
        $("#kt_datetimepicker_r").datetimepicker({
            minDate: moment(data.registrationDeadline).format("MM/DD/YYYY hh:mm A"),
        });
    } else {
        $("#kt_datetimepicker_r").datetimepicker({
            minDate: 'now',
        });
    }

    for (var i = 1; i <= dayInput; i++) {
        KTCardTools.init('kt_card_' + i);
        if (data != null && sch_id[i] != "") {
            showScheduleOption(i, data);
            fillForm(data, (i - 1));
        } else {
            showScheduleOption(i, null);
        }
        dateTimePicker(i);
    }
}

function showScheduleOption(index, data) {
    if (data != null) {
        if (data.schedulers[(index - 1)].isOnline) {
            $('input:radio[name="schedulers.isOnline' + index + '"]').change(function () {
                renderSch($(this), index);
                fillForm(data, (index - 1));
            });
        }
    }

    $('input:radio[name="schedulers.isOnline' + index + '"]').change(function () {
        renderSch($(this), index);
        fillForm(data, (index - 1));
    });

    function renderSch(radioButton, index) {
        if (radioButton.is(':checked') && radioButton.val() == "true") {
            $("#e_s_location_offline" + index).empty();
            $("#e_s_location_online" + index).html(`
            <div class="col-xl-6">
                <!--begin::Input-->
                <div class="form-group">
                    <label>Platform</label>
                    <input type="text"
                        class="form-control form-control-solid form-control-lg"
                        placeholder="platform" name="schedulers.location" style="width:auto;/>
                    <span class="form-text text-muted">Please enter a Platform</span>
                </div>
                <!--end::Input-->
            </div>
            <div class="col-xl-6">
                <!--begin::Input-->
                <div class="form-group">
                    <label>Link</label>
                    <input type="text" class="form-control form-control-solid form-control-lg"
                        placeholder="link" name="schedulers.linkPlatform" style="width:auto;/>
                    <span class="form-text text-muted">Please enter link</span>
                </div>
                <!--end::Input-->
            </div>`);
        } else {
            $("#e_s_location_online" + index).empty();
            $("#e_s_location_offline" + index).html(`
            <div class="col-xl-8">
                <div class="form-group">
                    <label>Location</label>
                    <input type="text" class="form-control form-control-solid form-control-lg"
                        placeholder="enter a location" name="schedulers.location" style="width:auto;"/>
                    <span class="form-text text-muted">Please enter Location</span>
                </div>
            </div>`);
        }
    }
}

function dateTimePicker(index) {
    $('#kt_datetimepicker_r').on('change.datetimepicker', function (e) {
        $('#kt_datetimepicker_' + index).datetimepicker('destroy');
        $('#kt_datetimepicker_' + index).datetimepicker('minDate', e.date);
    });

    $('#kt_datetimepicker_' + index).on('change.datetimepicker', function (e) {
        // if (index > 1) {
        //     $(this).datetimepicker('destroy');
        //     var m = moment($('#kt_datetimepicker_' + (index-1) + " input").val()).startOf('day').add('days', 1).format("MM/DD/YYYY hh:mm A");
        //     $(this).datetimepicker({
        //         minDate: m
        //     });
        // }
        $datepicker = $('#kt_datetimepicker_' + index + '_2');
        $datepicker.datetimepicker('destroy');
        $datepicker.datetimepicker({
            setDate: e.date,
            maxDate: moment(e.date).endOf('day'),
            minDate: e.date
        });
    });
}

function disableInput(data) {
    $(".eventTitle , .eventDescription").empty();
    $(".eventTitle").html(`
        <h6 class="font-weight-bolder mb-3">EVENT TITLE:</h6>
            <div class="text-dark-50 line-height-lg">
                <div id="eventTitle">` + data.title + `</div>
            </div>
        <div class="separator separator-dashed my-5"></div>
    `);
    $(".eventDescription").html(`
        <h6 class="font-weight-bolder mb-3">EVENT Description:</h6>
            <div class="text-dark-50 line-height-lg">
                <div id="eventDescription">` + data.description + `</div>
            </div>
        <div class="separator separator-dashed my-5"></div>
    `);
}

function fillForm(data, indexSch) {
    var form = $('#kt_form');
    // if (indexSch == null) {
    form.find('input[name="contactPerson"]').val(data.contactPerson);
    form.find('input[name="registrationDeadline"]').datetimepicker({
        defaultDate: fillDate(data.registrationDeadline)
    });
    form.find('input[name="quota"]').val(data.quota);
    form.find('input#eventImage').val(data.image);
    // }

    var sch = $('.card-schedule');
    if (indexSch == null) {
        sch.each(function (i) {
            setFill(i);
        });
    } else {
        setFill(indexSch);
    }

    function setFill(i) {
        if (i < data.schedulers.length) {
            var sch_location = data.schedulers[i].location;
            var sch_sTime = fillDate(data.schedulers[i].startTime);
            var sch_eTime = fillDate(data.schedulers[i].endTime);
        } else {
            var sch_location = sch.eq(i).find('input[name="schedulers.location"]').val();
            var sch_sTime = sch.eq(i).find('input[name="schedulers.startTime"]').val();
            var sch_eTime = sch.eq(i).find('input[name="schedulers.endTime"]').val();
        }

        if (data.schedulers[i].isOnline) {
            if (i < data.schedulers.length) {
                var sch_lP = data.schedulers[i].linkPlatform;
            } else {
                var sch_lP = sch.eq(i).find('input[name="schedulers.linkPlatform"]').val();
            }

            sch.eq(i).find('#offline_check' + (i + 1)).removeAttr('checked');
            sch.eq(i).find('#online_check' + (i + 1)).attr('checked', 'checked');
            sch.eq(i).find('input[name="schedulers.linkPlatform"]').val(sch_lP);
        }

        sch.eq(i).find('input[name="schedulers.id"]').val(data.schedulers[i].id);
        sch.eq(i).find('input[name="schedulers.location"]').val(sch_location);
        sch.eq(i).find('input[name="schedulers.startTime"]').datetimepicker({
            defaultDate: fillDate(sch_sTime)
        });
        sch.eq(i).find('input[name="schedulers.endTime"]').datetimepicker({
            defaultDate: fillDate(sch_eTime)
        });
    }
}

function deleteSchedule(id) {
    Swal.fire({
        title: "Are you sure?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Yes, delete it!"
    }).then(function (result) {
        if (result.value) {
            ajax_delete(serverBaseUrl + "/scheduler/" + id, callbackSuccess);

            function callbackSuccess(message) {
                Swal.fire(
                    "Deleted!",
                    "Your schedule has been deleted.",
                    "success"
                )
                window.location.reload();
            }
        }
    });

}

function fillDate(date) {
    return moment(date).format("MM/DD/YYYY hh:mm A");
}