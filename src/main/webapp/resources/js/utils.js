$(function () {
    $("#logoutLink").click(function () {
        $("#logoutForm").submit();
    });
})

function refreshData(containerId, fadeOut, callback) {
    if (fadeOut && $(containerId).children().length > 0) {
        $(containerId).children().fadeOut(800, function () {
            $(containerId).load(document.URL + " " + containerId + " > *", callback);
        });
    } else {
        $(containerId).load(document.URL + " " + containerId + " > *", callback);
    }
}

function removeUntilParent(element, parentId, isFaded, callback) {
    if (isFaded) {
        $(element).parentsUntil(parentId).last().fadeOut(800, function () {
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

function removeAndRefreshIfEmpty(element, parentId, refreshCallback, removeCallback) {
    removeUntilParent(element, parentId, true, function () {
        if (removeCallback != undefined) {
            removeCallback.call();
        }

        if ($(parentId).children().length == 0) {
            refreshData(parentId, false, refreshCallback);
        }
    });
}

function reloadActiveTab() {
    $("#navTab > li").each(function (index, element) {
        if ($(element).hasClass("active")) {
            $(element).load(document.URL + " #navTab > li:eq(" + index + ") > *");
            return;
        }
    });
}

function decrementActiveTab() {
    var activeTab, activeTabBadge;

    activeTab = $("#navTab > li.active");
    activeTabBadge = activeTab.children("a").find("span.badge");


    if (activeTab.length === 1 && $.isNumeric(activeTabBadge.text())) {
        if (parseInt(activeTabBadge.text()) === 1) {
            activeTabBadge.text('');
        } else {
            activeTabBadge.text(parseInt(activeTabBadge.text()) - 1);
        }
    }
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

    modal.find("p").text(msg);
    modal.modal("show");
}