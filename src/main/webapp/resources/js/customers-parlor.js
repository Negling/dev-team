/*For proper work of all functions, this script must be loaded after Bootstrap and jQuery libraries.*/

/*Global counters for dev rows in one task, and tasks in one project.
 After every task/project submitted it will be reset to 0.*/
var taskId = createIdCounter();
var devId = createIdCounter();

/*Function, which runs at document.ready(). It maps all event handlers. */
$(function () {
    var modal = $("#addTaskModal");

    // task modal events
    modal.find("#addTaskName, #addTaskDescription").keyup(validateTaskModal);
    modal.find("#addDevButton").click(addTaskModalDevRow);
    modal.find("#taskBackButton").click(clearTaskModal).click(hideTaskModal);
    modal.find("#addTaskButton").click(addTask).click(hideTaskModal).click(clearTaskModal).click(validateProject);

    modal.find("input[type=number]").keyup(function () {
        return checkTaskModalNumberInput($(this));
    });

    // new project events
    $("body")
        .on("keyup", "#projectName, #projectDescription", validateProject)
        .on("submit", "#projectForm", function () {
            event.preventDefault();
            submitProjectAjax();
        });

    $("#taskPrototype").find("button").click(function () {
        $(this).parentsUntil("div.panel-group").animate({opacity: 0}, 500, function () {
            $(this).parentsUntil("div.panel-group").remove();
        });
    }).delay(500).click(validateProject);
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
    taskModal.find("#addTaskName").val("");
    taskModal.find("#addTaskDescription").val("");
    taskModal.find("#addTaskButton").attr("disabled", "disabled");
}

/*Adds one extra dev row to task modal.*/
function addTaskModalDevRow() {
    var taskModal, newRow, delButton;

    taskModal = $("#addTaskModal");

    // max 12 dev rows in one task
    if (taskModal.find("tr").length > 12)
        return;

    // deep copy to save input check event on new row
    newRow = taskModal.find("tr:eq(1)").clone(true, true);

    // button to delete created row on demand
    delButton = $("<button> Delete</button>")
        .addClass("btn")
        .addClass("btn-default")
        .click(function () {
            newRow.animate({opacity: 0}, 300, function () {
                newRow.remove();
            });
        })
        .appendTo(newRow.children().last());

    $("<span></span>")
        .addClass("glyphicon")
        .addClass("glyphicon-remove").prependTo(delButton);


    // set default number input values and append new row
    newRow.find("input[type=number]").val(1);
    taskModal.find("tr:last").after(newRow);
}

/*Adds task section to project.*/
function addTask() {
    var newTask, newTaskId, newTaskDevRowId, newTaskName,
        newColumn, newRow, taskModal, currentTitle, currentInput, inputType;

    //initialization
    taskModal = $("#addTaskModal");
    newTaskId = "task" + taskId();
    newTaskName = taskModal.find("#addTaskName").val();

    // deep clone
    newTask = $("#taskPrototype").clone(true, true).removeClass("no-display").removeAttr("id");

    // binding inputs name attributes
    newTask.find("input").attr("name", "taskName").attr("value", newTaskName);
    newTask.find("textarea").attr("name", "taskDescription").text(taskModal.find("#addTaskDescription").val());
    newTask.find("a").text(newTaskName).attr("href", "#" + newTaskId);
    newTask.find("div.panel-collapse.collapse").attr("id", newTaskId);


    // filling dev's table by complete request
    taskModal.find("tbody tr").each(function () {
        newRow = $("<tr></tr>");
        newTaskDevRowId = devId();

        $(this).children().slice(0, 3).each(function (index, element) {
            newColumn = $("<td></td>").appendTo(newRow);
            if (index == 2) {
                newColumn.addClass("text-center");
            }

            currentInput = $(element).children();
            currentTitle = currentInput.attr("title");
            inputType = "text";

            createReadonlyInput(inputType,
                currentInput.val(),
                currentTitle,
                ("dev" + currentTitle))
                .appendTo(newColumn);
        });
        newTask.find("tbody").append(newRow);
    });

    $("#tasks").append(newTask);

    // task added, disable addTask button and reset dev counter
    taskModal.find("#addTaskButton").attr("disabled", "disabled");
    devId = createIdCounter();
}

function prepareProjectForm() {
    var projectForm, technicalTask, operations, requestsForDevelopers, operation, devRequest;

    projectForm = $("#projectForm");
    operations = [];
    technicalTask = {
        name: projectForm.find("#projectName").val(),
        description: projectForm.find("#projectDescription").val(),
        operations: operations
    };

    projectForm.find("div.panel-body").each(function (index, element) {
        requestsForDevelopers = [];
        operation = {
            name: $(element).find("[name=taskName]").val(),
            description: $(element).find("[name=taskDescription]").val(),
            requestsForDevelopers: requestsForDevelopers
        };

        $(element).find("tbody tr").each(function (index, element) {
            devRequest = {
                specialization: $(element).find("[name=devSpecialization]").val(),
                rank: $(element).find("[name=devRank]").val(),
                quantity: $(element).find("[name=devQuantity]").val()
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

    if (modal.find("#addTaskName").val().length >= 10
        && modal.find("#addTaskDescription").val().length >= 20) {

        submitTaskButton.removeAttr("disabled");
    } else {
        submitTaskButton.attr("disabled", "disabled");
    }
}

/*Toggles submitProjectButton 'disabled' attribute, depends on inputted values in project name,
 description fields and tasks amount.*/
function validateProject() {
    var project, submitButton;

    project = $("#createProject");
    submitButton = project.find("#submitProject");

    if (project.find("#projectName").val().length >= 10
        && project.find("#projectDescription").val().length >= 20
        && project.find("#tasks").children().length > 0) {

        submitButton.removeAttr("disabled");
    } else {
        submitButton.attr("disabled", "disabled");
    }
}

/*Sets dev quantity in task between 1 and 100.*/
function checkTaskModalNumberInput(input) {
    if (input.val() > 100) input.val(100);
    else if (input.val() < 1) input.val(1);
}

/*Creates readonly input with custom attributes.*/
function createReadonlyInput(type, value, title, name) {
    return $("<input readonly>")
        .attr("type", type)
        .attr("value", value)
        .attr("title", title)
        .attr("name", name);
}

/*Hides task modal with bootstrap function.*/
function hideTaskModal() {
    $("#addTaskModal").modal("hide");
}

/*Simple counter.*/
function createIdCounter() {
    var id = 0;
    return function () {
        return id++;
    }
}

function projectSubmitted(data) {
    $("#createProject").load(document.URL + " #createProject div:first", function () {
        var div = $("<div></div>")
            .addClass("text-center")
            .addClass("col-lg-8")
            .addClass("col-lg-offset-2")
            .addClass("alert")
            .addClass("alert-success")
            .addClass("alert-dismissible")
            .addClass("fade")
            .addClass("in")
            .append(data[1])
            .prependTo($("#createProject").children());

        $("<strong></strong>")
            .append(data[0] + "! ")
            .prependTo(div);

        $("<a>&times;</a>")
            .addClass("close")
            .attr("href", "#")
            .attr("data-dismiss", "alert")
            .attr("aria-label", "close")
            .prependTo(div);
    });
}

function submitProjectAjax() {
    var csrf_token = $("meta[name='_csrf']").attr("content");
    var csrf_header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        method: "POST",
        contentType: "application/json",
        url: "/parlor/submit",
        data: JSON.stringify(prepareProjectForm()),
        timeout: 10000,
        beforeSend: function (jqXHR, settings) {
            jqXHR.setRequestHeader(csrf_header, csrf_token);
        },
        success: function (data) {
            return projectSubmitted(data);
        },
        error: function (data) {
            console.log(data.responseText);
        }
    });
}