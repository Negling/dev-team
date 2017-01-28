/*Script for common project purposes. For proper work of all functions, this script must be loaded after Bootstrap and jQuery libraries. */

/* Maps all event handlers at document.ready(). */
$(function () {
    $("#logoutLink").click(function () {
        $("#logoutForm").submit();
    });

    $("a[data-locale]").click(function (event) {
        event.preventDefault();
        changeLocale($(this));
    });
});

/* Refreshes page with new locale value. Simply appends locale value as GET param based on clicked language dropdown option. */
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

/**
 * Refreshes element with specified id by GET request to server. Can be animated. FadeOut animation speed is set to 600.
 *
 * @param containerId id of element that should be refreshed
 * @param isFaded should element be animated or not
 * @param source current page URL
 * @param callback function that should be executed after work is done
 */
function refreshData(containerId, isFaded, source, callback) {
    var URL = source === undefined ? location.href.split('?')[0] + " " : location.href.split('?')[0] + source + " ";

    if (isFaded) {
        $(containerId).fadeTo(600, 0.01, function () {
            $(containerId).load(URL + containerId + " > *", callback);
        }).delay(600).fadeTo(600, 1);
    } else {
        $(containerId).load(URL + containerId + " > *", callback);
    }
}

/**
 * Removes elements in specific container(parentId). Can be animated. FadeOut animation speed is set to 600.
 *
 * @param parentId element which content should be removed
 * @param isFaded is fadeOut animation required? boolean value
 * @param callback function that should be executed after work is done
 */
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

/* Removes element from parent, if after operation element is empty - sends GET request to load new content.
 Can be animated. FadeOut animation speed is set to 600. */
function removeAndRefreshIfEmpty(element, parentId, source, callback) {
    removeUntilParent(element, parentId, true, function () {
        if ($(parentId).children().length == 0) {
            refreshData(parentId, true, source, callback);
        } else if (callback != undefined) {
            callback.call()
        }
    });
}

/* Prepends content to specific element that match to targetSelector.
 If that element has as children element, that match to contentSelector - it will be just updated. */
function prependOrUpdate(contentSelector, targetSelector, content) {
    var expectedContent = $(contentSelector);

    if (expectedContent.length != 0) {
        expectedContent.remove();
    }

    $(targetSelector).prepend(content);
}

/* Forms box with unordered list of messages. */
function formAlertBox(data, isSuccessful) {
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

/* Displays ERROR modal with msg param. Other modals will be closed. */
function showErrorsModal(msg) {
    var modal = $("#errorsModal");

    $("div.modal[role=dialog]").modal("hide");

    modal.find("h4").text(msg);
    modal.modal("show");
}