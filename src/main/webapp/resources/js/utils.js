$(function () {
    $("#logoutLink").click(function () {
        $("#logoutForm").submit();
    });
})

function refreshData(containerId, isFaded, callback) {
    if (isFaded) {
        $(containerId).children().animate({opacity: 0}, 800, function () {
            $(containerId).load(document.URL + " " + containerId + " > *", callback);
        });
    } else {
        $(containerId).load(document.URL + " " + containerId + " > *", callback);
    }
}

function updateActiveTab() {
    $("#navTab > li").each(function (index, element) {
        if ($(element).hasClass("active")) {
            $(element).load(document.URL + " #navTab > li:eq(" + index + ") > *");
            return;
        }
    });
}

function removeUntilParent(element, parentId, isFaded, callback) {
    if (isFaded) {
        $(element).parentsUntil(parentId).animate({opacity: 0}, 800, function () {
            $(this).remove();

            if (callback != undefined) {
                callback.call();
            }
        });
    } else {
        $(element).parentsUntil(parentId).remove();

        if (callback != undefined) {
            callback.call();
        }
    }
}

function displayAlertBox(data, alertBoxId, alertBoxParentId, isSuccessful) {
    var alertBox, msgContainer, list;

    alertBox = $(alertBoxId);

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

        alertBox.prependTo($(alertBoxParentId));
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

function showErrorsModal(msg) {
    var modal = $("#errorsModal");

    modal.find("p").text(msg);
    modal.modal("show");
}
