"use strict";

var KTCardTools = function () {

    // Demo 4
    var demo4 = function (id) {
        // This card is lazy initialized using data-card="true" attribute. You can access to the card object as shown below and override its behavior
        // var card_count = $(".card-schedule").length;
        // for (var i = 1; i <= card_count; i++) {
        var card = new KTCard(id);
        // Reload event handlers
        card.on('afterFullscreenOn', function (card) {
            var scrollable = $(card.getBody()).find('> .kt-scroll');

            if (scrollable) {
                scrollable.data('original-height', scrollable.css('height'));
                scrollable.css('height', '100%');

                KTUtil.scrollUpdate(scrollable[0]);
            }
        });

        card.on('afterFullscreenOff', function (card) {
            var scrollable = $(card.getBody()).find('> .kt-scroll');

            if (scrollable) {
                var scrollable = $(card.getBody()).find('> .kt-scroll');
                scrollable.css('height', scrollable.data('original-height'));

                KTUtil.scrollUpdate(scrollable[0]);
            }
        });
        // }

    }

    return {
        //main function to initiate the module
        init: function (id) {
            if (id) {
                demo4(id);
            }
        }
    };
}();

$(function () {
    KTCardTools.init('kt_card_1');
});