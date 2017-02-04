package ua.devteam;


import ua.devteam.entity.projects.Check;
import ua.devteam.entity.projects.Project;
import ua.devteam.entity.projects.TechnicalTask;
import ua.devteam.entity.tasks.Operation;
import ua.devteam.entity.tasks.ProjectTask;
import ua.devteam.entity.tasks.RequestForDevelopers;
import ua.devteam.entity.tasks.TaskDevelopmentData;
import ua.devteam.entity.users.Customer;
import ua.devteam.entity.users.Developer;
import ua.devteam.entity.users.Manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ua.devteam.entity.enums.CheckStatus.AWAITING;
import static ua.devteam.entity.enums.DeveloperRank.JUNIOR;
import static ua.devteam.entity.enums.DeveloperSpecialization.BACKEND;
import static ua.devteam.entity.enums.DeveloperStatus.AVAILABLE;
import static ua.devteam.entity.enums.Role.*;
import static ua.devteam.entity.enums.Status.PENDING;

/**
 * An util class to retrieve on demand entity instances, for testing purposes.
 */
public abstract class EntityUtils {
    private final static String FIRST_NAME = "John";
    private final static String LAST_NAME = "Doe";
    private final static String EMAIL = "test@mail.com";
    private final static String PHONE_NUMBER = "+380967411654";
    private final static String PASSWORD = "password";
    private final static String LENGTH_10 = "1234567890";
    private final static String LENGTH_30 = "123456789012345678901234567890";
    private final static long ID = 1;

    /**
     * Returns valid instance of TechnicalTask with one valid Operation and one valid RequestForDevelopers within operation.
     * Manager commentary is empty string, task name is 10-chars length string, description is 30-chars length string.
     * Status of task is set to "NEW".
     *
     * @param customerId as entity param
     * @return {@link TechnicalTask} valid entity
     */
    public static TechnicalTask getValidTechnicalTask(Long customerId) {
        return new
                TechnicalTask.Builder(LENGTH_10, LENGTH_30, customerId).setId(ID)
                .setOperations(getListWith(getValidOperation()))
                .build();
    }

    /**
     * Returns valid instance of Customer. Name is set to "John", last name set to "Doe". Password set to "password",
     * email is set to "test@mail.com", and phone number is "+380967411654".
     *
     * @return {@link Customer} valid entity
     */
    public static Customer getValidCustomer() {
        return new
                Customer.Builder()
                .setId(ID)
                .setFirstName(FIRST_NAME)
                .setLastName(LAST_NAME)
                .setEmail(EMAIL)
                .setPhoneNumber(PHONE_NUMBER)
                .setPassword(PASSWORD)
                .setRole(CUSTOMER)
                .build();
    }

    /**
     * Returns valid instance of Developer. Name is set to "John", last name set to "Doe". Password set to "password",
     * email is set to "test@mail.com", and phone number is "+380967411654". Current task id is set to "0".
     * Specialization - "BACKEND", Rank - "JUNIOR".
     *
     * @return {@link Developer} valid entity
     */
    public static Developer getValidDeveloper() {
        return new
                Developer.Builder()
                .setId(ID)
                .setFirstName(FIRST_NAME)
                .setLastName(LAST_NAME)
                .setEmail(EMAIL)
                .setPhoneNumber(PHONE_NUMBER)
                .setPassword(PASSWORD)
                .setRole(DEVELOPER)
                .setCurrentTaskId(0L)
                .setHireCost(new BigDecimal("500.00"))
                .setSpecialization(BACKEND)
                .setRank(JUNIOR)
                .setStatus(AVAILABLE)
                .build();
    }

    /**
     * Returns valid instance of Manager. Name is set to "John", last name set to "Doe". Password set to "password",
     * email is set to "test@mail.com", and phone number is "+380967411654". Role - "Manager".
     *
     * @return {@link Manager} valid entity
     */
    public static Manager getValidManager() {
        return new
                Manager.Builder()
                .setId(ID)
                .setFirstName(FIRST_NAME)
                .setLastName(LAST_NAME)
                .setEmail(EMAIL)
                .setPhoneNumber(PHONE_NUMBER)
                .setPassword(PASSWORD)
                .setRole(MANAGER)
                .setTotalProjectsServed(0L)
                .build();
    }

    /**
     * Returns valid instance of Operation. Operation name is 10-chars length string, description is 30-chars length string.
     * Contains one valid RequestForDevelopers instance.
     *
     * @return {@link Operation} valid entity
     */
    public static Operation getValidOperation() {
        return new Operation.Builder(ID, LENGTH_10, LENGTH_30)
                .setId(ID)
                .setRequestsForDevelopers(getListWith(getValidRequestForDevelopers()))
                .build();
    }

    /**
     * Returns valid instance of RequestForDevelopers. Specialization is set to "BACKEND", Rank is set to "JUNIOR",
     * quantity is 1.
     *
     * @return {@link RequestForDevelopers} valid entity
     */
    public static RequestForDevelopers getValidRequestForDevelopers() {
        return new RequestForDevelopers(ID, BACKEND, JUNIOR, 1);
    }

    /**
     * Returns valid instance of ProjectTask. Task name is 10-chars length string, description is 30-chars length string.
     * Status is "PENDING". Contains one TaskDevelopmentData and RequestForDevelopers instances.
     *
     * @return {@link ProjectTask} valid entity
     */
    public static ProjectTask getValidProjectTask() {
        return new
                ProjectTask.Builder(ID, ID, LENGTH_10, LENGTH_30)
                .setId(ID)
                .setTotalHoursSpent(0)
                .setTaskStatus(PENDING)
                .setRequestsForDevelopers(getListWith(getValidRequestForDevelopers()))
                .setTasksDevelopmentData(getListWith(getValidTaskDevelopmentData()))
                .build();
    }

    /**
     * Returns valid instance of TaskDevelopmentData. Specialization is set to "BACKEND", Rank is set to "JUNIOR",
     * hours spent is 0. Status is "PENDING".
     *
     * @return {@link TaskDevelopmentData} valid entity
     */
    public static TaskDevelopmentData getValidTaskDevelopmentData() {
        return new TaskDevelopmentData.Builder(ID, ID, BACKEND, JUNIOR).setHoursSpent(0).setStatus(PENDING).build();
    }

    /**
     * Returns valid instance of Project with one valid ProjectTask. Manager commentary is empty string,
     * task name is 10-chars length string, description is 30-chars length string. Status of project is set to "PENDING".
     * End date and start date is date of invoking this method. Project cost is "0.00".
     *
     * @return {@link Project} valid entity
     */
    public static Project getValidProject() {
        return new
                Project.Builder(LENGTH_10, LENGTH_30, ID, ID)
                .setTechnicalTaskId(ID)
                .setManagerCommentary("")
                .setTotalProjectCost(new BigDecimal("0.00"))
                .setStartDate(new Date())
                .setEndDate(new Date())
                .setStatus(PENDING)
                .setTasks(getListWith(getValidProjectTask()))
                .build();
    }

    /**
     * Returns valid instance of Check. Project name is 10-chars length string. Services and Devs cost is set to "10.00".
     * Taxes value is "4.00" - 20% of sum of services and devs cost. Status of check is "AWAITING".
     *
     * @return {@link Check} valid entity
     */
    public static Check getValidCheck() {
        return new
                Check.Builder(new BigDecimal("10.00"), new BigDecimal("10.00"), new BigDecimal("4.00"), AWAITING)
                .setCustomerId(ID)
                .setProjectId(ID)
                .setProjectName(LENGTH_10)
                .build();
    }

    /**
     * Returns invalid instance of Check. At all, return instance is equal to "getValidCheck",
     * but taxes field has incorrect value, which not equal to 20% of sum of services and devs cost.
     *
     * @return {@link Check} invalid entity
     */
    public static Check getInvalidCheck() {
        return new
                Check.Builder(new BigDecimal("10.00"), new BigDecimal("10.00"), new BigDecimal("5.00"), AWAITING)
                .setCustomerId(ID)
                .setProjectId(ID)
                .setProjectName(LENGTH_10)
                .build();
    }

    private static <T> List<T> getListWith(T arg) {
        return new ArrayList<T>() {{
            add(arg);
        }};
    }
}
