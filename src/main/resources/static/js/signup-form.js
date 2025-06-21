"use strict";

// Class Definition
var KTLogin = (function () {
  var _handleSignUpForm = function (e) {
    var validation;
    var form = KTUtil.getById("kt_login_signup_form");

    // Init form validation rules. For more info check the FormValidation plugin's official documentation:https://formvalidation.io/
    validation = FormValidation.formValidation(form, {
      fields: {
        fullname: {
          validators: {
            notEmpty: {
              message: "Username is required",
            },
          },
        },
        email: {
          validators: {
            notEmpty: {
              message: "Email address is required",
            },
            emailAddress: {
              message: "The value is not a valid email address",
            },
          },
        },
        phoneNumber: {
          validators: {
            notEmpty: {
              message: "phone Number is required",
            },
            regexp: {
              regexp: /^(\+62|62|0)8[1-9][0-9]{6,9}$/,
              message: "Indonesia Phone Number only",
            },
          },
        },
        password: {
          validators: {
            notEmpty: {
              message: "The password is required",
            },
          },
        },
        cpassword: {
          validators: {
            notEmpty: {
              message: "The password confirmation is required",
            },
            identical: {
              compare: function () {
                return form.querySelector('[name="password"]').value;
              },
              message: "The password and its confirm are not the same",
            },
          },
        },
      },
      plugins: {
        trigger: new FormValidation.plugins.Trigger(),
        bootstrap: new FormValidation.plugins.Bootstrap(),
      },
    });

    $("#kt_login_signup_submit").on("click", function (e) {
      Swal.fire({
        title: "Please Wait!",
        text: "Your Request Still in Progress",
        allowOutsideClick: false,
        allowEscapeKey: false,
        onOpen: function () {
          Swal.showLoading();
        },
      });
      e.preventDefault();

      validation.validate().then(function (status) {
        if (status == "Valid") {
          ajax_post(
            serverBaseUrl + "/auth/register",
            formToJson($("#kt_login_signup_form")),
            callbackScs,
            callbackErr
          );

          function callbackScs(data) {
            swal.close();
            swal
              .fire({
                text: "Success Register Please check your email",
                icon: "success",
                buttonsStyling: false,
                confirmButtonText: "Ok, got it!",
                customClass: {
                  confirmButton: "btn font-weight-bold btn-light-primary",
                },
              })
              .then(function () {
                KTUtil.scrollTop();
                location.href = serverUrl;
              });
          }

          function callbackErr(data) {
            swal.close();
            swal
              .fire({
                text: "Sorry, looks like there are some errors detected, please try again.",
                icon: "error",
                buttonsStyling: false,
                confirmButtonText: "Ok, got it!",
                customClass: {
                  confirmButton: "btn font-weight-bold btn-light-primary",
                },
              })
              .then(function () {
                KTUtil.scrollTop();
              });
          }
        } else {
          swal.close();
          swal
            .fire({
              text: "Sorry, looks like there are some errors detected, please try again.",
              icon: "error",
              buttonsStyling: false,
              confirmButtonText: "Ok, got it!",
              customClass: {
                confirmButton: "btn font-weight-bold btn-light-primary",
              },
            })
            .then(function () {
              KTUtil.scrollTop();
            });
        }
      });
    });

    // Handle cancel button
    $("#kt_login_signup_cancel").on("click", function (e) {
      e.preventDefault();

      location.href = serverUrl;
    });
  };

  // Public Functions
  return {
    // public functions
    init: function () {
      _handleSignUpForm();
    },
  };
})();

// Class Initialization
jQuery(document).ready(function () {
  KTLogin.init();
});
