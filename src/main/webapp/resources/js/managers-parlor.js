var activeTask;

$(function () {
    changeActiveTask();

    $("#activeProjectSelect").change(changeActiveTaskSelectOptions);
    $("#activeTaskSelect").change(function () {
        changeActiveTask();
    });

    $("#searchDevsBtn").click(searchForDevsAjax);

    $("body").on("click", "button[name=declineProject]", function () {
        return declineProjectAjax($(this));
    }).on("click", "button[name=grabProject]", function () {
        return grabProjectAjax($(this));
    }).on("click", "button[name=acceptProject]", function () {
        return acceptProjectAjax($(this));
    });
});

function validateTask(taskId) {
    var specialization, rank, quantity, validated, devsTable, requestTable, nameLink;

    nameLink = $("#" + taskId + "Link");
    requestTable = $("#" + taskId + "Requested");
    devsTable = $("#" + taskId + "Hired");
    validated = true;

    requestTable.find("tbody").find("tr").each(function (index, element) {
        specialization = $(element).children().first();
        rank = specialization.next();
        quantity = rank.next();

        if (devsTable.find("tr:contains('" + specialization.text() + "')")
                .filter(":contains('" + rank.text() + "')").length != quantity.text()) {
            return validated = false;
        }
    });

    if (validated) {
        $("<span></span>")
            .addClass("pull-right")
            .addClass("glyphicon")
            .addClass("glyphicon-ok")
            .appendTo(nameLink);
    } else {
        nameLink.find("span").remove();
    }

    validateProject(taskId.match(/[0-9]+/)[0]);
}

function validateProject(projectId) {
    var nameLink, tasks, acceptButton;

    nameLink = $("#project" + projectId + "Link");
    tasks = $("#pendingProject" + projectId).find("a[id^=project" + projectId + "Task]");
    acceptButton = $("#pendingProject" + projectId).find("button[name=acceptProject]");

    if (tasks.length == tasks.find("span").length) {
        $("<span></span>")
            .addClass("pull-right")
            .addClass("glyphicon")
            .addClass("glyphicon-ok")
            .appendTo(nameLink);

        acceptButton.removeAttr("disabled");
    } else {
        nameLink.find("span").remove();
        acceptButton.attr("disabled", "disabled");
    }
}

function changeActiveTaskSelectOptions() {
    var activeTaskSelect = $("#activeTaskSelect");
    var activeProjectId = $("#activeProjectSelect").find("option:selected").val();
    var selectValue;

    activeTaskSelect.children().remove();

    $("#pendingProject" + activeProjectId)
        .find("a[data-toggle=collapse]")
        .each(function (index, element) {
            selectValue = "p" + $(element).attr("href").slice(8);

            $("<option></option>")
                .text($(element).text())
                .val(selectValue)
                .appendTo(activeTaskSelect);
        });

    changeActiveTask();
}

function changeActiveTask() {
    activeTask = $("#activeTaskSelect").find("option:selected").val();
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

            newColumn = $("<td></td>").addClass("text-right").appendTo(newRow);
            $("<button></button>")
                .addClass("btn")
                .addClass("btn-info")
                .addClass("btn-sm")
                .attr("value", data[i].id)
                .attr("type", "button")
                .text($("#bind").text())
                .click(function () {
                    return bindDeveloperAjax($(this))
                }).appendTo(newColumn);
        }
    }
}

function bindDeveloper(button, data) {
    var link, unbindButton, newRow, tableBody, resultTableBody;

    tableBody = $("#" + activeTask + "Hired").find("tbody");
    resultTableBody = $("#devsResultTable").find("tbody");

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
        .addClass("btn")
        .addClass("btn-info")
        .addClass("btn-sm")
        .attr("value", data.id)
        .attr("type", "button")
        .text($("#unbind").text())
        .click(function () {
            return unbindDeveloperAjax($(this));
        });

    $("<td></td>").append(link).appendTo(newRow);
    $("<td></td>").append(data.specialization).appendTo(newRow);
    $("<td></td>").append(data.rank).appendTo(newRow);
    $("<td></td>").append(unbindButton).appendTo(newRow);

    validateTask(activeTask);
}

function unbindDeveloper(button) {
    var table = $(button).closest("table");

    $(button).parentsUntil("tbody").remove();

    if (table.find("tbody").children().length == 0) {
        table.prev().removeClass("no-display");
        table.addClass("no-display");
    }

    validateTask(table.attr("id").slice(0, table.attr("id").indexOf("Hired")));
}

function removeAndReload(button, containerId) {
    $(button).parentsUntil(containerId).animate({opacity: 0}, 500, function () {
        $(this).remove();

        if ($(containerId).children().length == 0) {
            $(containerId).parent().load(document.URL + " " + containerId);
        }
    });
}

function formAcceptedProjectData(projectId) {
    var result, devs;

    result = {};

    $("table[id ^=project" + projectId + "Task][id $=Hired]").each(function (index, element) {
        devs = [];
        $(element).find("button").each(function (index, element) {
            devs.push($(element).attr("value"));
        });

        result[$(element).attr("id").match(/[0-9]+/g)[1]] = devs;
    });

    return result;
}

function declineProjectAjax(button) {
    $.ajax({
        method: "POST",
        contentType: "application/json",
        url: "/manage/decline",
        data: JSON.stringify($(button).attr("value")),
        timeout: 10000,
        success: function () {
            return removeAndReload(button, "#pendingProjectsAccordion");
        },
        error: function () {
            console.log("SucPzdc");
        }
    });
}

function acceptProjectAjax(button) {
    var data = formAcceptedProjectData($(button).attr("value"));

    $.ajax({
        method: "POST",
        contentType: "application/json",
        url: "/manage/accept",
        data: JSON.stringify(data),
        timeout: 10000,
        success: function () {
            return removeAndReload(button, "#pendingProjectsAccordion");
        },
        error: function () {
            console.log("SucPzdc");
        }
    });
}

function grabProjectAjax(button) {
    $.ajax({
        method: "POST",
        contentType: "application/json",
        url: "/manage/grab",
        data: JSON.stringify($(button).attr("value")),
        timeout: 10000,
        success: function () {
            return removeAndReload(button, "#projectRequestsAccordion");
        },
        error: function () {
            console.log("SucPzdcYOBA");
        }
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
        console.log("pizec");
    });
}

function bindDeveloperAjax(button) {
    $.ajax({
        method: "POST",
        contentType: "application/json",
        url: "/manage/bind",
        data: JSON.stringify($(button).attr("value")),
        timeout: 10000,
        success: function (data) {
            return bindDeveloper(button, data);
        },
        error: function (e) {
            alert("HUEVA");
        }
    });
}

function unbindDeveloperAjax(button) {
    $.ajax({
        method: "POST",
        contentType: "application/json",
        url: "/manage/unbind",
        data: JSON.stringify($(button).attr("value")),
        timeout: 10000,
        success: function () {
            return unbindDeveloper(button);
        },
        error: function (e) {
            alert("HUEVA");
        }
    });
}