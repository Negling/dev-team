/*For proper work of all functions, this script must be loaded after Bootstrap, jQuery libraries and utils.js */

/* Maps all event handlers at document.ready(). */
$(function () {
    $.ajaxSetup({
        // we need to add csrf header to every ajax request accordingly to Spring Security settings
        beforeSend: function (jqXHR) {
            jqXHR.setRequestHeader($("meta[name=_csrf_header]").attr("content"),
                $("meta[name=_csrf]").attr("content"));
        },
        contentType: "application/json",
        timeout: 10000
    });

    $("body").on("click", "button[name=refresh]", function () {
        return refreshData($(this).attr("data-container-id"), true, $(this).attr("data-path"));
    }).on("click", "#markComplete", completeTaskAjax);

    $("button[name=refresh]").trigger("click");
});

/* Sends request to server to mark task as complete, with hours spent and task ID as JSON string. */
function completeTaskAjax() {
    $.ajax({
        method: "PUT",
        url: "/development/completeTask",
        data: JSON.stringify({id: $("#markComplete").attr("value"), hoursSpent: $("#hoursSpent").val()})
    }).done(function (data) {
        refreshData("#currentTask", true, "/fragments/development_active_task", function () {
            // append message box
            return prependOrUpdate("#currentTask > div.alert", "#currentTask", formAlertBox(data, true));
        });
    }).fail(function (jqXHR) {
        if (jqXHR.status === 422) {
            // append message box
            prependOrUpdate("#currentTask > div.alert", "#currentTask",
                formAlertBox(JSON.parse(jqXHR.responseText), false));
        } else {
            showErrorsModal(jqXHR.responseText);
        }
    });
}