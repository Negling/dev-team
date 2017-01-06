/*For proper work of all functions, this script must be loaded after Bootstrap and jQuery libraries.*/

/*Global counter for tasks accordion in project. After project submitted it will be reset to 0.*/
var taskId = createIdCounter();

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

    var modal = $("#addTaskModal");

    // task modal events
    modal.find("#taskName, #taskDescription").keyup(validateTaskModal);
    modal.find("#addDevButton").click(addTaskModalDevRow);
    modal.find("#taskBackButton").click(clearTaskModal);
    modal.find("#addTaskButton").click(function () {
        addTask();
        clearTaskModal();
        $("#addTaskModal").modal("hide");
    });

    modal.find("input[type=number]").keyup(function () {
        return checkTaskModalNumberInput($(this));
    });

    $("#submitTechnicalTask").click(function () {
        return submitTechnicalTaskAjax($(this));
    })

    $("#taskPrototype").find("button").click(function () {
        $(this).parentsUntil("div.panel-group").animate({opacity: 0}, 500, function () {
            $(this).parentsUntil("div.panel-group").remove();
        });
    });
});


/*Clears all inputs and text-areas in task modal.
 Text variables is set to "" and numbers to 1.*/
function clearTaskModal() {
    var tableRows, taskModal;

    taskModal = $("#addTaskModal");
    tableRows = taskModal.find("tr");

    // remove all extra dev inputs
    tableRows.slice(2).remove();

    // reset values
    tableRows.find("input[type=number]").val(1);
    taskModal.find("#taskName").val("");
    taskModal.find("#taskDescription").val("");
    taskModal.find("#addTaskButton").prop('disabled', true);
}

/*Adds one extra dev row to task modal.*/
function addTaskModalDevRow() {
    var taskModal, newRow, delButton;

    taskModal = $("#addTaskModal");

    // max 12 dev rows in one task
    if (taskModal.find("tr").length > 12) {
        return;
    }

    // deep copy to save input check event on new row
    newRow = taskModal.find("tr:eq(1)").clone(true, true);

    // button to delete created row on demand
    delButton = $("<button> Delete</button>")
        .addClass("btn btn-default")
        .click(function () {
            newRow.animate({opacity: 0}, 300, function () {
                newRow.remove();
            });
        })
        .appendTo(newRow.children().last());

    $("<span></span>").addClass("glyphicon glyphicon-remove").prependTo(delButton);


    // set default number input values and append new row
    newRow.find("input[type=number]").val(1);
    taskModal.find("tr:last").after(newRow);
}

/*Adds task section to project.*/
function addTask() {
    var newTask, newTaskId, newColumn, newRow, taskModal;

    //initialization
    taskModal = $("#addTaskModal");
    newTaskId = "task" + taskId();

    // deep clone
    newTask = $("#taskPrototype").clone(true, true).removeClass("no-display").removeAttr("id");

    // binding data values
    newTask.find("p[title=taskDescription]").text(taskModal.find("#taskDescription").val());
    newTask.find("a").text(taskModal.find("#taskName").val()).attr("href", "#" + newTaskId);
    newTask.find("div.panel-collapse.collapse").attr("id", newTaskId);


    // filling dev's table
    taskModal.find("tbody tr").each(function () {
        newRow = $("<tr></tr>");

        $(this).children().slice(0, 3).each(function (index, element) {
            newColumn = $("<td></td>").append($(element).children().val()).appendTo(newRow);
            if (index == 2) {
                newColumn.addClass("text-center");
            }
        });
        newTask.find("tbody").append(newRow);
    });

    $("#tasks").append(newTask);

    // task added, disable addTask button
    taskModal.find("#addTaskButton").prop('disabled', true);
}

function createTechnicalTask() {
    var tasks, technicalTask, operations, requestsForDevelopers, operation, devRequest, tableRow;

    tasks = $("#tasks");
    operations = [];
    technicalTask = {
        name: $("#technicalTaskName").val(),
        description: $("#technicalTaskDescription").val(),
        customerId: $("#userId").text(),
        status: 'New',
        operations: operations
    };

    tasks.children().each(function (index, element) {
        requestsForDevelopers = [];
        operation = {
            name: $(element).find("a").text(),
            description: $(element).find("p[title=taskDescription]").text(),
            requestsForDevelopers: requestsForDevelopers
        };

        $(element).find("tbody tr").each(function (index, element) {
            tableRow = $(element).children();

            devRequest = {
                specialization: tableRow.eq(0).text(),
                rank: tableRow.eq(1).text(),
                quantity: tableRow.eq(2).text()
            };

            requestsForDevelopers.push(devRequest);
        });

        operations.push(operation);
    });

    return technicalTask;
}

/*Toggles addTaskButton 'disabled' attribute, depends on inputted values in task name and description fields.*/
function validateTaskModal() {
    var modal, submitTaskButton;

    modal = $("#addTaskModal");
    submitTaskButton = modal.find("#addTaskButton");

    if (modal.find("#taskName").val().length >= 10 && modal.find("#taskDescription").val().length >= 30) {

        submitTaskButton.prop('disabled', false);
    } else {
        submitTaskButton.prop('disabled', true);
    }
}

/*Sets dev quantity in task between 1 and 100.*/
function checkTaskModalNumberInput(input) {
    if (input.val() > 100) input.val(100);
    else if (input.val() < 1) input.val(1);
}

/*Simple counter.*/
function createIdCounter() {
    var id = 0;
    return function () {
        return id++;
    }
}

function submitTechnicalTaskAjax(button) {
    $.ajax({
        method: "POST",
        url: "/cabinet/submit",
        data: JSON.stringify(createTechnicalTask())
    }).done(function (data) {
        return removeAndReload(button, "#formTechnicalTaskParent", function () {
            displayAlertBox(data, "formTechnicalTaskAlertBox", "formTechnicalTask", true);
            taskId = createIdCounter();
        });
    }).fail(function (jqXHR) {
        if (jqXHR.status === 422) {
            displayAlertBox(JSON.parse(jqXHR.responseText), "formTechnicalTaskAlertBox", "formTechnicalTask", false);
        } else {
            alert("Some server internal error occurred! Please try again later, or contact support.");
        }
    });
}