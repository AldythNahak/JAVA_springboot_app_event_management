"use strict";

// Shared Colors Definition
const primary = '#6993FF';
const success = '#1BC5BD';
const info = '#8950FC';
const warning = '#FFA800';
const danger = '#F64E60';

var KTApexChartsDemo = function () {
	// Private functions
	var _demo1 = function () {
		const apexChart = "#chart_1";
		var options;
        let url = serverBaseUrl+"/scheduler/year";
		$.ajax({
			type: "GET",
			url: url,
			dataType: "json",
			success: function (dataSchedule) {
				options = {
					series: [{
						name: "Desktops",
						data: [
							dataSchedule[0],dataSchedule[1],dataSchedule[2],
							dataSchedule[3],dataSchedule[4],dataSchedule[5],
							dataSchedule[6],dataSchedule[7],dataSchedule[8],
							dataSchedule[9],dataSchedule[10],dataSchedule[11]
						]
					}],
					chart: {
						height: 350,
						type: 'line',
						zoom: {
							enabled: false
						}
					},
					dataLabels: { 	
						enabled: false
					},
					stroke: {
						curve: 'straight'
					},
					grid: {
						row: {
							colors: ['#f3f3f3', 'transparent'], // takes an array which will be repeated on columns
							opacity: 0.5
						},
					},
					xaxis: {
						categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Des'],
					},
					colors: [primary]
				};
				var chart = new ApexCharts(document.querySelector(apexChart), options);
				chart.render();
			}
		});
	}


	return {
		// public functions
		init: function () {
			_demo1();
		}
	};
}();

jQuery(document).ready(function () {
	KTApexChartsDemo.init();
});
