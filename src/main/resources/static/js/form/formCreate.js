var base64;

function readFile() {

    if (this.files && this.files[0]) {

        var FR = new FileReader();
        FR.addEventListener("load", function (e) {
            base64 = e.target.result.split(',')[1];
        });

        FR.readAsDataURL(this.files[0]);
    } else {
        base64 = null;
    }
    return base64;
}

//get image base 64
document.getElementById("customFile").addEventListener("change", readFile);

function form_event() {
    var form = $('#kt_form');
    var arrayOfSchedule = [];
    var sch = $('.card-schedule');

    sch.each(function (i) {
        if (sch.eq(i).find('input:radio[name="schedulers.isOnline' + (i + 1) + '"]:checked').val() == "true") {
            arrayOfSchedule.push({
                isOnline: true,
                location: sch.eq(i).find('input[name="schedulers.location"]').val(),
                linkPlatform: sch.eq(i).find('input[name="schedulers.linkPlatform"]').val(),
                startTime: setDateTime(sch.eq(i).find('input[name="schedulers.startTime"]').val()),
                endTime: setDateTime(sch.eq(i).find('input[name="schedulers.endTime"]').val())
            });
        } else {
            arrayOfSchedule.push({
                isOnline: false,
                location: sch.eq(i).find('input[name="schedulers.location"]').val(),
                startTime: setDateTime(sch.eq(i).find('input[name="schedulers.startTime"]').val()),
                endTime: setDateTime(sch.eq(i).find('input[name="schedulers.endTime"]').val())
            });
        }
        
        if(path_url[3] == "edit") {
            var id_sch = sch.eq(i).find('input[name="schedulers.id"]').val();
            if (id_sch != "") {
                arrayOfSchedule[i]['id'] = parseInt(id_sch);
            }
        }
    });

    var data_event = {
        title: form.find('input[name="title"]').val(),
        description: form.find('textarea[name="description"]').val(),
        contactPerson: form.find('input[name="contactPerson"]').val(),
        registrationDeadline: setDateTime(form.find('input[name="registrationDeadline"]').val()),
        quota: parseInt(form.find('input[name="quota"]').val()),
        schedulers: arrayOfSchedule,
        image: base64
    };

    if (path_url[3] == "edit") {
        data_event.title = $("#eventTitle").text();
        data_event.description = $("#eventDescription").text();
        if (data_event.image == null && $("#eventImage").val() != "") {
            data_event.image = $("#eventImage").val();
        }
    }

    return data_event;
}

function generateReviewForm() {
    var data = form_event();
    var sch_table_tr = [];
    var sch_table_head =
        `
    <table class="table">
        <thead>
            <tr>
                <th scope="col">Day</th>
                <th scope="col">Type</th>
                <th scope="col">Location</th>
                <th scope="col">Link</th>
                <th scope="col">Start Time</th>
                <th scope="col">End Time</th>
            </tr>
        </thead>
        <tbody>`;
    var sch_table_tail = `</tbody></table>`;

    $("#review_image").attr("src", "data:image/png;base64," + data.image);
    $("#review_title").text(data.title);
    $("#review_description").text(data.description);
    $("#review_pic").text(data.contactPerson);
    $("#review_reg_deadline").text(new Date(data.registrationDeadline));
    $("#review_quota").text(data.quota);

    for (var i = 0; i < data.schedulers.length; i++) {
        var td_online;
        var td_link;
        if (data.schedulers[i].isOnline) {
            td_online = `<span class="label label-inline label-light-success font-weight-bold">Online</span>`;
            td_link = data.schedulers[i].linkPlatform;
        } else {
            td_online = `<span class="label label-inline label-light-danger font-weight-bold">Offline</span>`;
            td_link = ` - `;
        }
        sch_table_tr.push(`
        <tr>
            <th scope="row">`+(i+1)+`</th>
            <td>`+td_online+`</td>
            <td>`+data.schedulers[i].location+`</td>
            <td>`+td_link+`</td>
            <td>`+new Date(data.schedulers[i].startTime)+`</td>
            <td>`+new Date(data.schedulers[i].endTime)+`</td>
        </tr>`);
    }
    var sch_table_body = sch_table_tr.join('');
    var sch_table = sch_table_head + sch_table_body + sch_table_tail;
    $("#review_schedule").html(sch_table);


}

function setDateTime(date) {
    return moment(date).format("YYYY-MM-DDTHH:mm:ss");
}