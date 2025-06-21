var serverBaseUrl = "http://localhost:8089/ajax";
var serverUrl = "http://localhost:8089";

$.ajaxSetup({
  headers: {
    "X-CSRF-TOKEN": $('meta[name="_csrf"]').attr("content"),
  },
});

$(function () {
  var token = $("meta[name='_csrf']").attr("content");
  var header = $("meta[name='_csrf_header']").attr("content");
  $(document).ajaxSend(function (e, xhr, options) {
    xhr.setRequestHeader(header, token);
  });
});

(function ($) {
  $.fn.serializeFormJSON = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
      if (o[this.name]) {
        if (!o[this.name].push) {
          o[this.name] = [o[this.name]];
        }
        o[this.name].push(this.value || "");
      } else {
        o[this.name] = this.value || "";
      }
    });
    return o;
  };
})(jQuery);

function formToJson(jqueryForm) {
  var form = jqueryForm.serializeFormJSON();
  var jsonString = JSON.stringify(form);
  return jsonString;
}

function ajax_post(url, data, callbackSuccess, callbackError, callbackData) {
    $.ajaxSetup({
        headers: {
            'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
        }
    });

    $.ajax({
        url: url,
        type: 'POST',
        data: data,
        processData: false,
        contentType: "application/json",
        success: function (data) {
            if (data.message) {
                callbackSuccess(data.message);
            }
            callbackData(data.data);
        },
        error: function (message) {
            callbackError(message.responseJSON.message);
        },
        cache: false,
        timeout: 300000
    });
}

function ajax_put(url, data, callbackSuccess, callbackError) {
  $.ajaxSetup({
    headers: {
      "X-CSRF-TOKEN": $('meta[name="_csrf"]').attr("content"),
    },
  });
  $.ajax({
    url: url,
    type: "PUT",
    data: data,
    contentType: "application/json",
    success: function (data) {
      if (data.message) {
        callbackSuccess(data.message);
      }
    },
    error: function (message) {
      callbackError(message);
    },
    cache: false,
    timeout: 300000,
  });
}

function ajax_get(url, callbackGet) {
  $.ajaxSetup({
    headers: {
      "X-CSRF-TOKEN": $('meta[name="_csrf"]').attr("content"),
    },
  });

  $.ajax({
    url: url,
    type: "GET",
    success: function (data, status, xhr) {
      callbackGet(data.data);
    },
    error: function (data) {},
    cache: true,
    timeout: 15000,
    //        processData: false
  });
}

function ajax_delete(url, callbackSuccess, callbackError) {
  $.ajax({
    url: url,
    method: "DELETE",
    headers: {
      "X-CSRF-TOKEN": $('meta[name="csrf-token"]').attr("content"),
    },
    success: function (data) {
      if (data.message) {
          callbackSuccess(data.message);
      }
    },
    error: function (message) {
      callbackError(message);
    },
  });
}

function ajax_post_multipart(url, formData, callbackSuccess, callbackError) {
  var data = new FormData(formData);

  $.ajax({
    url: url,
    method: "POST",
    data: data,
    // dataType: 'JSON',
    contentType: false,
    cache: false,
    processData: false,
    success: function (data) {
      if (data.result === "success") {
        if (callbackSuccess) callbackSuccess(data);
      } else {
        if (callbackError) callbackError(data);
      }
    },
    error: function (data) {
      hideLoadingPage();
      if (callbackError) {
        callbackError(data);
      } else if (data.status == "500") {
        var message =
          "Oops something went wrong... please try again or call our support";
        sweetAlert({
          title: "Failed",
          html: "Error: " + message,
          type: "error",
          showCancelButton: false,
          confirmButtonColor: "#3085d6",
          cancelButtonColor: "#d33",
          confirmButtonText: "OK",
        });
      }
    },
  });
}
