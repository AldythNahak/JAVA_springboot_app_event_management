/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function getByEmail() {
    ajax_get(serverBaseUrl + "/auth/username", callbackGet);

    function callbackGet(data) {
        $("#review_name").html(data.name);
        $("#review_phoneNumber").html(data.phoneNumber);
        $("#review_email").html(data.email);
        $('input[name="idEmployee"]').val(data.id);

    }
}

$(function () {
    getByEmail();

});

function createTicket() {
    Swal.fire({
        title: "Please Wait!",
        text: "Your Request Still in Progress",
        allowOutsideClick: false,
        allowEscapeKey: false,
        onOpen: function () {
            Swal.showLoading()
        }
    });
    
    ajax_post(serverBaseUrl + "/ticket", formToJson($("#checkout")), callbackSuccess, callbackError);

    function callbackSuccess(message) {
        Swal.close();
        toastr.success(message, "success");
    }

    function callbackError(message) {
        var msg = message.split(`\\"`);
        Swal.close();
        toastr.error(msg[1], "failed");
    }
}

