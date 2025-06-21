"use strict";
// Class definition
var KTKBootstrapTouchspin = function() {

    // Private functions
    var demos = function() {

        // vertical buttons with custom icons:
        $('#kt_touchspin_4, #kt_touchspin_2_4').TouchSpin({
            min: 1,
            max: 7,
            buttondown_class: 'btn btn-secondary',
            buttonup_class: 'btn btn-secondary',
            verticalbuttons: true,
            verticalup: '<i class="ki ki-plus"></i>',
            verticaldown: '<i class="ki ki-minus"></i>'
        });

        $('#kt_touchspin_1, #kt_touchspin_2_4').TouchSpin({
            min: 1,
            max: 1000,
            buttondown_class: 'btn btn-secondary',
            buttonup_class: 'btn btn-secondary',
            verticalbuttons: true,
            verticalup: '<i class="ki ki-plus"></i>',
            verticaldown: '<i class="ki ki-minus"></i>'
        });
    }

    return {
        // public functions
        init: function() {
            demos();
        }
    };
}();

jQuery(document).ready(function() {
    KTKBootstrapTouchspin.init();
});
