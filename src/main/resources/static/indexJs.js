$("#search").click(function () {
    status();
    scrape.scrapeLeague();
});
var scrape = {
    scrapeLeague: function () {
        var e = {};
        e["league"] = $("#LEAGUE").val();
        e["link"] = $("#LINK").val();

        var d = JSON.stringify(e);
        console.log("HO: " + d);

        $.ajax({
            url: '/scrape/league',
            dataType: 'json',
            contentType: "application/json",
            type: 'POST',
            data: d,
            success: function (data, textStatus, jqXHR) {

            },
            error: function (jqXHR, textStatus, errorThrown) {

            },
            beforeSend: function (xhr) {

            }
        });
    }
};

function status() {
    $.get("/status", function (data, status) {

        var xx = document.getElementById("display").value;
        var yy = document.getElementById("display").value;
        var textarea = document.getElementById("display");

        var last = xx.split("\n");
        console.log(last[last.length - 1]);
        if (last[last.length - 1] !== data) {
            document.getElementById("display").value = yy + "\n" + data;
            textarea.scrollTop = textarea.scrollHeight;
        }
    });
    var t = setTimeout(status, 100);
}