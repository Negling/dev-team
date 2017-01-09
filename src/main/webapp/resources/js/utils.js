$(function () {
    $("#logoutLink").click(function () {
        $("#logoutForm").submit();
    });
})

function removeAndReload(button, containerId, functionAfterDone) {
    $(button).parentsUntil(containerId).animate({opacity: 0}, 500, function () {
        $(this).remove();

        if ($(containerId).children().length == 0) {
            $(containerId).parent().load(document.URL + " " + containerId, functionAfterDone);
        } else if (functionAfterDone != undefined) {
            functionAfterDone.call();
        }
    });
}

function displayAlertBox(data, alertBoxId, alertBoxParentId, isSuccessful) {
    var alertBox, msgContainer, list;

    alertBox = $("#" + alertBoxId);

    if (alertBox.length === 0) {
        alertBox = $("<div></div>")
            .attr("id", alertBoxId)
            .addClass("alert alert-dismissible fade in");

        if (isSuccessful) {
            alertBox.addClass("alert-success");
        } else {
            alertBox.addClass("alert-warning");
        }

        $("<a>&times;</a>")
            .addClass("close")
            .attr("href", "#")
            .attr("data-dismiss", "alert")
            .attr("aria-label", "close")
            .prependTo(alertBox);

        msgContainer = $("<div></div>").append(data[1]).appendTo(alertBox);

        alertBox.prependTo($("#" + alertBoxParentId));
    } else {
        msgContainer = alertBox.find("div");

        msgContainer.children().remove().append(data[1]);
        alertBox.removeClass("alert-success alert-warning");

        if (isSuccessful) {
            alertBox.addClass("alert-success");
        } else {
            alertBox.addClass("alert-warning");
        }
    }

    $("<strong></strong>").append(data[0] + "! ").prependTo(msgContainer);

    if (data.length > 2) {
        list = $("<ul></ul>").appendTo(msgContainer);

        for (var i = 2; i < data.length; i++) {
            $("<li></li>").append(data[i]).appendTo(list);
        }
    }
}

function updateNavsTab(navTabId) {
    $("#" + navTabId).load(document.URL + " #" + navTabId + " > li");
}
