$(function () {
    calculateRegisteredProjectsChecks();

    $.ajaxSetup({
        // we need to add csrf header to every ajax request accordingly to Spring Security settings
        beforeSend: function (jqXHR) {
            jqXHR.setRequestHeader($("meta[name=_csrf_header]").attr("content"),
                $("meta[name=_csrf]").attr("content"));
        },
        contentType: "application/json",
        timeout: 10000,
    });

    // when project on bind devs modal is changing - update task select options
    $("#activeProjectSelect").change(changeActiveTaskSelectOptions);

    // ajax GET request for devs which match search criteria
    $("#searchDevsBtn").click(searchForDevsAjax);

    // in open "decline technical task" modal by clicking on cancel - clear manager commentary
    $("#declineModal").find("button[id!=declineButton]").click(function () {
        $("#declineManagerCommentary").val("");
    });

    // in open "decline" modal by clicking on continue - send ajax POST request
    $("#declineButton").click(function () {
        $(this).attr("data-entity-type") === "project" ? declineProjectAjax.call() : declineTechnicalTaskAjax.call();
    });

    $("body").on("click", "button[name^=decline]", function () {
        // on click change tt ID in "declineModal"
        $("#declineManagerCommentary").val('');
        showDeclineModal($(this).attr("value"), $(this).attr("data-entity-type"));
    }).on("click", "button[name=acceptTechnicalTask]", function () {
        // send accept tt and form it as project request
        return formTechnicalTaskAsProjectAjax($(this));
    }).on("click", "button[name=acceptProject]", function () {
        //
        return acceptProjectAjax($(this));
    }).on("click", "button[name=unbindDeveloper]", function () {
        // unbind dev from pending project task
        return unbindDeveloperAjax($(this));
    }).on("change", "input[title=servicesCost]", function () {
        return calculateProjectCheck($(this).attr("data-project-id"));
    }).on("click", "button[name=refresh]", function () {
        return refreshData($(this).attr("data-container-id"), true, $(this).attr("data-path"));
    }).on("click", "button[name=refreshProjects]", function () {
        return refreshData($(this).attr("data-container-id"), true, $(this).attr("data-path"), function () {
            calculateRegisteredProjectsChecks();
            updateBindSelectOptions();
        });
    });

    $("button[name~=refresh]").trigger("click");
});


function showDeclineModal(entityId, entityType) {
    $("#declineId").text(entityId);
    $("#declineButton").attr("data-entity-type", entityType);
    $("#declineModal").modal("show");
}

function changeActiveTaskSelectOptions() {
    var activeTaskSelect = $("#activeTaskSelect");
    var activeProjectId = $("#activeProjectSelect > option:selected").val();
    var selectValue;

    activeTaskSelect.children().remove();

    $("#pendingProject" + activeProjectId)
        .find("a[data-toggle=collapse]")
        .each(function (index, element) {
            selectValue = $(element).attr("data-task-id");

            $("<option></option>")
                .text($(element).text())
                .val(selectValue)
                .appendTo(activeTaskSelect);
        });
}

function displayDevsSearchResults(data) {
    var resultTableBody = $("#devsResultTable > tbody");
    var newRow, newColumn;

    if (data.length == 0) {
        resultTableBody.parent().fadeOut(300, function () {
            resultTableBody.parent().addClass("no-display");
            $("#noResults").removeClass("no-display").fadeTo(0, 1);
            resultTableBody.children().remove();
        });

    } else {
        resultTableBody.children().remove();
        $("#noResults").fadeOut(300, function () {
            $(this).addClass("no-display");
            resultTableBody.parent().removeClass("no-display").fadeTo(0, 1);
            ;
        });

        for (var i = 0; i < data.length; i++) {
            newRow = $("<tr></tr>").appendTo(resultTableBody);

            newColumn = $("<td></td>").appendTo(newRow);
            $("<a></a>")
                .attr("href", $("#devPath").text() + data[i].id)
                .text(data[i].firstName + " " + data[i].lastName)
                .appendTo(newColumn);

            $("<td></td>").text(data[i].hireCost + " \$").appendTo(newRow);

            newColumn = $("<td></td>").addClass("text-right").appendTo(newRow);
            $("<button></button>")
                .addClass("btn btn-info btn-sm")
                .attr("value", data[i].id)
                .attr("type", "button")
                .text($("#bind").text())
                .click(function () {
                    return bindDeveloperAjax($(this))
                }).appendTo(newColumn);
        }
    }
}

function calculateRegisteredProjectsChecks() {
    var devsHireCost, projectId;

    $("div[data-project-id]").each(function (index, project) {
        projectId = $(project).attr("data-project-id");
        devsHireCost = 0.00;

        $(project).find("button[name=unbindDeveloper]").each(function (index, button) {
            devsHireCost += parseFloat($(button).attr("data-developer-hire-cost"));
        });

        $("#project" + projectId + "DevsCost").val(devsHireCost);
        calculateProjectCheck(projectId);
    });
}

function calculateProjectCheck(projectId) {
    var devsHireCost, servicesCost, taxes, totalCost, servicesAndDevs;

    devsHireCost = $("#project" + projectId + "DevsCost");
    servicesCost = $("#project" + projectId + "Services");
    taxes = $("#project" + projectId + "Taxes");
    totalCost = $("#project" + projectId + "TotalCost");

    taxes.val(((parseFloat(devsHireCost.val()) + parseFloat(servicesCost.val())) * 0.2).toFixed(0));
    totalCost.val(parseFloat(devsHireCost.val()) + parseFloat(servicesCost.val()) + parseFloat(taxes.val()));
}

function updateBindSelectOptions() {

    //TODO: find a solution to upload bind options without load request

    var URL = location.href.split('?')[0] + "/fragments/manage_form_project";

    $("#activeProjectSelect").load(URL + " #activeProjectSelect > option");
    $("#activeTaskSelect").load(URL + " #activeTaskSelect > option");
}

function bindDeveloper(button, data) {
    var link, unbindButton, newRow, tableBody, resultTableBody, projectId, projectDevsCost, currentTaskId;

    currentTaskId = $("#activeTaskSelect > option:selected").attr("value");
    projectId = $("#activeProjectSelect > option:selected").attr("value");
    tableBody = $("#task" + currentTaskId + "Hired").find("tbody");
    projectDevsCost = $("#project" + projectId + "DevsCost");
    resultTableBody = $("#devsResultTable > tbody");

    projectDevsCost.val(parseFloat(projectDevsCost.val()) + parseFloat(data.hireCost));

    $(button).parentsUntil("tbody").remove();

    if (resultTableBody.children().length == 0) {
        resultTableBody.parent().addClass("no-display");
        $("#noResults").removeClass("no-display").fadeTo(0, 1);
    }

    if (tableBody.children().length == 0) {
        tableBody.parent().prev().addClass("no-display");
        tableBody.parent().removeClass("no-display");
    }

    newRow = $("<tr></tr>").appendTo(tableBody);
    link = $("<a></a>")
        .attr("href", $("#devPath").text() + data.developerId)
        .text(data.developerFirstName + " " + data.developerLastName);
    unbindButton = $("<button></button>")
        .addClass("btn btn-info btn-sm")
        .attr("data-developer-hire-cost", data.hireCost)
        .attr("data-developer-id", data.developerId)
        .attr("data-project-id", projectId)
        .attr("type", "button")
        .text($("#unbind").text())
        .click(function () {
            return unbindDeveloperAjax($(this));
        });

    $("<td></td>").append(link).appendTo(newRow);
    $("<td></td>").append(data.specialization).appendTo(newRow);
    $("<td></td>").append(data.rank).appendTo(newRow);
    $("<td></td>").append(data.hireCost + " \$").appendTo(newRow);
    $("<td></td>").append(unbindButton).appendTo(newRow);

    calculateProjectCheck(projectId)
}

function unbindDeveloper(button) {
    var table = $(button).closest("table");
    var projectId = $(button).attr("data-project-id");
    var projectDevsCost = $("#project" + projectId + "DevsCost");

    projectDevsCost.val(parseFloat(projectDevsCost.val()) - parseFloat($(button).attr("data-developer-hire-cost")));
    $(button).parentsUntil("tbody").remove();

    if (table.find("tbody").children().length == 0) {
        table.prev().removeClass("no-display");
        table.addClass("no-display");
    }

    calculateProjectCheck(projectId);
}

function declineProjectAjax() {
    var projectId = $("#declineId").text();
    var data = {
        projectId: projectId,
        managerCommentary: $("#declineManagerCommentary").val()
    };

    $.ajax({
        method: "PUT",
        url: "/manage/declineProject",
        data: JSON.stringify(data)
    }).done(function () {
        return removeAndRefreshIfEmpty($("button[value=" + projectId + "][name=declineProject]"),
            "#pendingProjectsAccordion", "/fragments/manage_form_project", updateBindSelectOptions);
    }).fail(function (jqXHR) {
        showErrorsModal(jqXHR.responseText);
    });
}

function acceptProjectAjax(button) {
    var projectId = $(button).attr("value");
    var check = {
        projectId: projectId,
        developersCost: $("#project" + projectId + "DevsCost").val(),
        servicesCost: $("#project" + projectId + "Services").val(),
        taxes: $("#project" + projectId + "Taxes").val()
    };

    $.ajax({
        method: "PUT",
        url: "/manage/accept",
        data: JSON.stringify(check)
    }).done(function (data) {
        return removeAndRefreshIfEmpty(button, "#pendingProjectsAccordion", "/fragments/manage_form_project", function () {
                prependOrUpdate("#pendingProjectsAccordion > div.alert", "#pendingProjectsAccordion",
                    formValidatingAlertBox(data, true));
                updateBindSelectOptions();
            });
    }).fail(function (jqXHR) {
        if (jqXHR.status === 422) {
            prependOrUpdate("#pendingProjectsAccordion > div.alert", "#pendingProjectsAccordion",
                formValidatingAlertBox(JSON.parse(jqXHR.responseText), false));
        } else {
            showErrorsModal(jqXHR.responseText);
        }
    });
}

function declineTechnicalTaskAjax() {
    var technicalTaskId = $("#declineId").text();
    var data = {
        technicalTaskId: technicalTaskId,
        managerCommentary: $("#declineManagerCommentary").val()
    };

    $.ajax({
        method: "PUT",
        url: "/manage/declineTechnicalTask",
        data: JSON.stringify(data)
    }).done(function () {
        return removeAndRefreshIfEmpty($("button[value=" + technicalTaskId + "][name=declineTechnicalTask]"),
            "#technicalTasksAccordion", "/fragments/manage_technical_tasks");
    }).fail(function (jqXHR) {
        showErrorsModal(jqXHR.responseText);
    });
}

function formTechnicalTaskAsProjectAjax(button) {
    $.ajax({
        method: "POST",
        url: "/manage/formAsProject",
        data: JSON.stringify($(button).attr("value"))
    }).done(function () {
        return removeAndRefreshIfEmpty(button, "#technicalTasksAccordion", "/fragments/manage_technical_tasks");
    }).fail(function (jqXHR) {
        showErrorsModal(jqXHR.responseText);
    });
}

function searchForDevsAjax() {
    var requestParams = {
        specialization: $("#developerSpecialization").val(),
        rank: $("#developerRank").val(),
        lastName: $("#developerLastName").val()
    };

    $.getJSON("/manage/getDevelopers", requestParams, function (data) {
        displayDevsSearchResults(data)
    }).fail(function (jqXHR) {
        showErrorsModal(jqXHR.responseText);
    });
}

function bindDeveloperAjax(button) {
    var activeTask = $("#activeTaskSelect > option:selected").attr("value");

    if (activeTask === undefined) {
        return;
    }

    $.ajax({
        method: "POST",
        url: "/manage/bind",
        data: JSON.stringify({devId: $(button).attr("value"), taskId: activeTask})
    }).done(function (data) {
        return bindDeveloper(button, data);
    }).fail(function (jqXHR) {
        showErrorsModal(jqXHR.responseText);
    });
}

function unbindDeveloperAjax(button) {
    $.ajax({
        method: "DELETE",
        url: "/manage/unbind",
        data: JSON.stringify($(button).attr("data-developer-id"))
    }).done(function (data) {
        return unbindDeveloper(button);
    }).fail(function (jqXHR) {
        showErrorsModal(jqXHR.responseText);
    });
}