"use strict";
var path_url = window.location.pathname.split("/");
var _idEvent = $("#_idEvent").val();

// Class definition
var KTWizard1 = function () {
	// Base elements
	var _wizardEl;
	var _formEl;
	var _wizardObj;
	var _validations = [];

	// Private functions
	var _initValidation = function () {
		// Init form validation rules. For more info check the FormValidation plugin's official documentation:https://formvalidation.io/
		// Step 1
		_validations.push(FormValidation.formValidation(
			_formEl, {
				fields: {
					title: {
						validators: {
							notEmpty: {
								message: 'Title is required',
								enabled: path_url[3] == "edit" ? false : true,
							},
						}
					},
					image: {
						validators: {
							notEmpty: {
								message: 'Image is required',
								enabled: path_url[3] == "edit" ? false : true,
							},
							file:{
								extension: 'jpeg jpg, png',
								type: 'image/jpeg,image/png',
								message: 'Please choose a jpeg/jpg/png file',
							},
							file:{
								maxSize: 3145728,
								message: 'Image size must be smaller than 3 MB ',
							}
						}
					},
				},
				plugins: {
					trigger: new FormValidation.plugins.Trigger(),
					// Bootstrap Framework Integration
					bootstrap: new FormValidation.plugins.Bootstrap({
						//eleInvalidClass: '',
						eleValidClass: '',
					})
				}
			}
		));

		// Step 2
		_validations.push(FormValidation.formValidation(
			_formEl, {
				fields: {
					quota: {
						validators: {
							notEmpty: {
								message: 'quota is required'
							}
						}
					},
					registrationDeadline: {
						validators: {
							notEmpty: {
								message: 'Registration Deadline is required'
							},
						}
					},
					"eventType.location": {
						validators: {
							notEmpty: {
								message: 'Location is required'
							},
						}
					},
					"eventType.startTime": {
						validators: {
							notEmpty: {
								message: 'Event Start Time is required'
							},
						}
					},
					"eventType.endTime": {
						validators: {
							notEmpty: {
								message: 'Event End Time is required'
							},
						}
					}
				},
				plugins: {
					trigger: new FormValidation.plugins.Trigger(),
					// Bootstrap Framework Integration
					bootstrap: new FormValidation.plugins.Bootstrap({
						//eleInvalidClass: '',
						eleValidClass: '',
					})
				}
			}
		));
	}

	var _initWizard = function () {
		// Initialize form wizard
		_wizardObj = new KTWizard(_wizardEl, {
			startStep: 1, // initial active step number
			clickableSteps: false // allow step clicking
		});

		// Validation before going to next page
		_wizardObj.on('change', function (wizard) {
			if (wizard.getStep() > wizard.getNewStep()) {
				return; // Skip if stepped back
			}

			// Validate form before change wizard step
			var validator = _validations[wizard.getStep() - 1]; // get validator for currnt step

			if (validator) {
				validator.validate().then(function (status) {
					if (status == 'Valid') {
						wizard.goTo(wizard.getNewStep());

						// KTUtil.scrollTop();
					} else {
						Swal.fire({
							text: "Sorry, looks like there are some errors detected, please try again.",
							icon: "error",
							buttonsStyling: false,
							confirmButtonText: "Ok, got it!",
							customClass: {
								confirmButton: "btn font-weight-bold btn-light"
							}
						}).then(function () {
							// KTUtil.scrollTop();
						});
					}
				});
			}

			return false; // Do not change wizard step, further action will be handled by he validator
		});

		// Change event
		_wizardObj.on('changed', function (wizard) {
			// KTUtil.scrollTop();
			generateReviewForm();
		});

		// Submit event
		_wizardObj.on('submit', function (wizard) {
			Swal.fire({
				text: "All is good! Please confirm the form submission.",
				icon: "success",
				showCancelButton: true,
				buttonsStyling: false,
				confirmButtonText: "Yes, submit!",
				cancelButtonText: "No, cancel",
				customClass: {
					confirmButton: "btn font-weight-bold btn-primary",
					cancelButton: "btn font-weight-bold btn-default"
				}
			}).then(function (result) {
				if (result.value) {

					Swal.fire({
						title: "Please Wait!",
						text: "your event create still in progress",
						allowOutsideClick: false,
						allowEscapeKey: false,
						onOpen: function () {
							Swal.showLoading()
						}
					});

					if (path_url[3] == "edit") {
						ajax_put(serverBaseUrl + "/event/" + _idEvent, JSON.stringify(form_event()), callbackSuccess, callbackError);
					} else {
						ajax_post(serverBaseUrl + "/event", JSON.stringify(form_event()), callbackSuccess, callbackError);
					}

					function callbackSuccess(message) {
						swal.close();
						toastr.success(message, "success");
					}

					function callbackError() {
						swal.close();
						Swal.fire({
							text: "Oops, something went wrong.",
							icon: "error",
							buttonsStyling: false,
							confirmButtonText: "Ok, got it!",
							customClass: {
								confirmButton: "btn font-weight-bold btn-primary",
							}
						});
					}
				} else if (result.dismiss === 'cancel') {
					Swal.fire({
						text: "Your form has not been submitted!.",
						icon: "error",
						buttonsStyling: false,
						confirmButtonText: "Ok, got it!",
						customClass: {
							confirmButton: "btn font-weight-bold btn-primary",
						}
					});
				}
			});
		});
	}

	return {
		// public functions
		init: function () {
			_wizardEl = KTUtil.getById('kt_wizard');
			_formEl = KTUtil.getById('kt_form');

			_initValidation();
			_initWizard();
		}
	};
}();

jQuery(document).ready(function () {
	KTWizard1.init();
});