$(function () {
    $("#logoutLink").click(function () {
        $("#logoutForm").submit();
    });

    $("a[data-locale]").click(function (event) {
        event.preventDefault();
        changeLocale($(this));
    });
});

function changeLocale(link) {
    var URL;

    if (location.search.length > 0 && location.search.indexOf("language=") > 0) {
        URL = location.href.replace(/language=\w+(?=&?)/, "language=" + link.attr("data-locale"));
    } else if (location.search.length > 0) {
        URL = location.href + "&language=" + link.attr("data-locale");
    } else {
        URL = location.href + "?language=" + link.attr("data-locale");
    }

    window.open(URL, "_self");
}

function refreshData(containerId, fadeOut, source, callback) {
    var URL = source === undefined ? location.href.split('?')[0] + " " : location.href.split('?')[0] + source + " ";

    if (fadeOut) {
        $(containerId).fadeTo(600, 0.01, function () {
            $(containerId).load(URL + containerId + " > *", callback);
        }).delay(600).fadeTo(600, 1);
    } else {
        $(containerId).load(URL + containerId + " > *", callback);
    }
}

function removeUntilParent(element, parentId, isFaded, callback) {
    if (isFaded) {
        $(element).parentsUntil(parentId).last().fadeOut(600, function () {
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

function removeAndRefreshIfEmpty(element, parentId, source, callback) {
    removeUntilParent(element, parentId, true, function () {
        if ($(parentId).children().length == 0) {
            refreshData(parentId, true, source, callback);
        } else if (callback != undefined) {
            callback.call()
        }
    });
}

function prependOrUpdate(contentSelector, targetSelector, content) {
    var expectedContent = $(contentSelector);

    if (expectedContent.length != 0) {
        expectedContent.remove();
    }

    $(targetSelector).prepend(content);
}

function formValidatingAlertBox(data, isSuccessful) {
    var alertBox, msgContainer;

    alertBox = $("<div></div>").addClass("alert alert-dismissible fade in");
    alertBox.addClass(isSuccessful ? "alert-success" : "alert-warning");

    $("<a>&times;</a>")
        .addClass("close")
        .attr("href", "#")
        .attr("data-dismiss", "alert")
        .attr("aria-label", "close")
        .prependTo(alertBox);

    msgContainer = $("<div></div>").append(data[1]).appendTo(alertBox);

    $("<strong></strong>").append(data[0] + "! ").prependTo(msgContainer);

    if (data.length > 2) {
        var list = $("<ul></ul>").appendTo(msgContainer);

        for (var i = 2; i < data.length; i++) {
            $("<li></li>").append(data[i]).appendTo(list);
        }
    }

    return alertBox;
}

function showErrorsModal(msg) {
    var modal = $("#errorsModal");

    $("div.modal[role=dialog]").modal("hide");

    modal.find("p").text(msg);
    modal.modal("show");
}