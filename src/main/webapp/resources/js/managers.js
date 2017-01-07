$(function () {
    calculateRegisteredProjectsChecks();

    $.ajaxSetup({
        // we need to add csrf header to every ajax request accordingly to Spring Security settings
        beforeSend: function (jqXHR) {
            jqXHR.setRequestHeader($("meta[name=_csrf_header]").attr("content"),
                $("meta[name=_csrf]").attr("content"));
        },
        contentType: "application/json",
        timeout: 10000
    });

    // when project on bind devs modal is changing - update task select options
    $("#activeProjectSelect").change(changeActiveTaskSelectOptions);

    // ajax GET request for devs which match search criteria
    $("#searchDevsBtn").click(searchForDevsAjax);

    // in open "decline technical task" modal by clicking on cancel - clear manager commentary
    $("#declineTechnicalTaskModal").find("button[id!=declineTechnicalTask]").click(function () {
        $("#declineTechnicalTaskManagerCommentary").val("");
    });

    // in open "decline technical task" modal by clicking on continue - send ajax POST request
    $("#declineTechnicalTask").click(function () {
        declineTechnicalTaskAjax();
    });


    $("body").on("click", "button[name=declineTechnicalTaskButton]", function () {
        // on click change tt ID in "declineTechnicalTask" modal
        $("#declineTechnicalTaskId").text($(this).attr("value"));
    }).on("click", "button[name=declineProject]", function () {
        // send decline project request
        return declineProjectAjax($(this));
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
    });
});


function changeActiveTaskSelectOptions() {
    var activeTaskSelect = $("#activeTaskSelect");
    var activeProjectId = $("#activeProjectSelect").find("option:selected").val();
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
    var resultTableBody = $("#devsResultTable").find("tbody");
    var newRow, newColumn;

    if (data.length == 0) {
        resultTableBody.parent().animate({opacity: 0}, 300, function () {
            resultTableBody.parent().addClass("no-display");
            $("#noResults").removeClass("no-display").css("opacity", "");
            resultTableBody.children().remove();
        });

    } else {
        resultTableBody.children().remove();
        $("#noResults").animate({opacity: 0}, 300, function () {
            $(this).addClass("no-display");
            resultTableBody.parent().removeClass("no-display").css("opacity", "");
        });

        for (var i = 0; i < data.length; i++) {
            newRow = $("<tr></tr>").appendTo(resultTableBody);

            newColumn = $("<td></td>").appendTo(newRow);
            $("<a></a>")
                .attr("href", "#")
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

function bindDeveloper(button, data) {
    var link, unbindButton, newRow, tableBody, resultTableBody, projectId, projectDevsCost, currentTaskId;

    currentTaskId = $("#activeTaskSelect").find("option:selected").attr("value");
    projectId = $("#activeProjectSelect").find("option:selected").attr("value");
    tableBody = $("#task" + currentTaskId + "Hired").find("tbody");
    projectDevsCost = $("#project" + projectId + "DevsCost");
    resultTableBody = $("#devsResultTable").find("tbody");

    projectDevsCost.val(parseFloat(projectDevsCost.val()) + parseFloat(data.hireCost));

    $(button).parentsUntil("tbody").remove();

    if (resultTableBody.children().length == 0) {
        resultTableBody.parent().addClass("no-display");
        $("#noResults").removeClass("no-display").css("opacity", "");
    }

    if (tableBody.children().length == 0) {
        tableBody.parent().prev().addClass("no-display");
        tableBody.parent().removeClass("no-display");
    }

    newRow = $("<tr></tr>").appendTo(tableBody);
    link = $("<a></a>")
        .attr("href", "#")
        .text(data.firstName + " " + data.lastName);
    unbindButton = $("<button></button>")
        .addClass("btn btn-info btn-sm")
        .attr("data-developer-hire-cost", data.hireCost)
        .attr("data-developer-id", data.id)
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

function declineProjectAjax(button) {
    $.ajax({
        method: "POST",
        url: "/manage/decline",
        data: JSON.stringify($(button).attr("value"))
    }).done(function () {
        removeAndReload(button, "#pendingProjectsAccordion");
        $("#activeProjectSelect").load(document.URL + " #activeProjectSelect > option");
        $("#activeTaskSelect").load(document.URL + " #activeTaskSelect > option");
    }).fail(function () {
        alert("Some server internal error occurred! Please try again later, or contact support.");
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
        method: "POST",
        url: "/manage/accept",
        data: JSON.stringify(check)
    }).done(function (data) {
        return removeAndReload(button, "#pendingProjectsAccordion", function () {
            displayAlertBox(data, "pendingProjectsAlertBox", "pendingProjectsAccordionParent", true);
            calculateRegisteredProjectsChecks();
        });
    }).fail(function (jqXHR) {
        if (jqXHR.status === 422) {
            displayAlertBox(JSON.parse(jqXHR.responseText), "pendingProjectsAlertBox", "pendingProjectsAccordionParent", false);
        } else {
            alert("Some server internal error occurred! Please try again later, or contact support.");
        }
    });
}

function declineTechnicalTaskAjax() {
    var technicalTaskId = $("#declineTechnicalTaskId").text();
    var data = {
        technicalTaskId: technicalTaskId,
        managerCommentary: $("#declineTechnicalTaskManagerCommentary").val()
    };

    $.ajax({
        method: "POST",
        url: "/manage/declineTechnicalTask",
        data: JSON.stringify(data)
    }).done(function () {
        removeAndReload($("button[value=" + technicalTaskId + "][name=declineTechnicalTaskButton]"),
            "#technicalTasksAccordion");
        $("#declineTechnicalTaskManagerCommentary").val("");
    }).fail(function () {
        alert("Some server internal error occurred! Please try again later, or contact support.");
    });
}

function formTechnicalTaskAsProjectAjax(button) {
    $.ajax({
        method: "POST",
        url: "/manage/formAsProject",
        data: JSON.stringify($(button).attr("value"))
    }).done(function () {
        removeAndReload(button, "#technicalTasksAccordion");
        $("#pendingProjectsAccordion").parent().load(document.URL + " #pendingProjectsAccordion");
        $("#activeProjectSelect").load(document.URL + " #activeProjectSelect > option");
        $("#activeTaskSelect").load(document.URL + " #activeTaskSelect > option");
        calculateRegisteredProjectsChecks();
    }).fail(function () {
        alert("Some server internal error occurred! Please try again later, or contact support.");
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
    }).fail(function () {
        alert("Some server internal error occurred! Please try again later, or contact support.");
    });
}

function bindDeveloperAjax(button) {
    var data = {
        devId: $(button).attr("value"),
        taskId: $("#activeTaskSelect").find("option:selected").attr("value")
    };

    $.ajax({
        method: "POST",
        url: "/manage/bind",
        data: JSON.stringify(data)
    }).done(function (data) {
        return bindDeveloper(button, data);
    }).fail(function () {
        alert("Some server internal error occurred! Please try again later, or contact support.");
    });
}

function unbindDeveloperAjax(button) {
    $.ajax({
        method: "POST",
        url: "/manage/unbind",
        data: JSON.stringify($(button).attr("data-developer-id"))
    }).done(function (data) {
        return unbindDeveloper(button);
    }).fail(function () {
        alert("Some server internal error occurred! Please try again later, or contact support.");
    });
}