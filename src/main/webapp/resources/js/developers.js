/*Function, which runs at document.ready(). It maps all event handlers. */
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
        return refreshData($(this).attr("data-container-id"), true);
    }).on("click", "#markComplete", completeTaskAjax);
});

function completeTaskAjax() {
    $.ajax({
        method: "POST",
        url: "/development/completeTask",
        data: JSON.stringify({id: $("#markComplete").attr("value"), hoursSpent: $("#hoursSpent").val()})
    }).done(function (data) {
        refreshData("#currentTask", true, function () {
            return prependOrUpdate("#currentTask > div.alert", "#currentTask", formValidatingAlertBox(data, true));
        });
    }).fail(function (jqXHR) {
        if (jqXHR.status === 422) {
            prependOrUpdate("#currentTask > div.alert", "#currentTask",
                formValidatingAlertBox(JSON.parse(jqXHR.responseText), false));
        } else {
            showErrorsModal(jqXHR.responseText);
        }
    });
}