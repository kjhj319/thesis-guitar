
//radar chart data
var data = {
    labels: ["Metric 1", "Metric 2", "Metric 3", "Metric 4", "Metric 5", "Metric 6", "Metric 7"],
    datasets: [
        {
            label: "My First dataset",
            fillColor: "rgba(220,220,220,0.2)",
            strokeColor: "rgba(220,220,220,1)",
            pointColor: "rgba(220,220,220,1)",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            pointHighlightStroke: "rgba(220,220,220,1)",
            data: [65, 59, 90, 81, 56, 55, 40]
        },
        {
            
            data: [28, 48, 40, 19, 96, 27, 100]
        }
    ]
};

var ctx2 = document.getElementById("barChart").getContext("2d");
var myNewChart = new Chart(ctx2).Bar(data);

new Chart(ctx2).Radar(data,options);
.1

