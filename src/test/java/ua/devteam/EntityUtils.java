package ua.devteam;


import ua.devteam.entity.projects.Check;
import ua.devteam.entity.projects.Project;
import ua.devteam.entity.projects.TechnicalTask;
import ua.devteam.entity.tasks.Operation;
import ua.devteam.entity.tasks.ProjectTask;
import ua.devteam.entity.tasks.RequestForDevelopers;
import ua.devteam.entity.tasks.TaskDevelopmentData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import static ua.devteam.entity.enums.CheckStatus.AWAITING;
import static ua.devteam.entity.enums.DeveloperRank.JUNIOR;
import static ua.devteam.entity.enums.DeveloperSpecialization.BACKEND;
import static ua.devteam.entity.enums.Status.NEW;
import static ua.devteam.entity.enums.Status.PENDING;

public abstract class EntityUtils {
    private final static String LENGTH_10 = "1234567890";
    private final static String LENGTH_30 = "123456789012345678901234567890";
    private final static long ID = 1;

    public static TechnicalTask getValidTechnicalTask(Long customerId) {
        return new TechnicalTask(ID, LENGTH_10, LENGTH_30, customerId, "", NEW, new ArrayList<Operation>() {{
            add(new Operation(ID, ID, LENGTH_10, LENGTH_30, new ArrayList<RequestForDevelopers>() {{
                add(new RequestForDevelopers(ID, BACKEND, JUNIOR, 1));
            }}));
        }});
    }

    public static Project getValidProject() {
        Project project = new Project(LENGTH_10, LENGTH_30, ID, ID, "", ID, new BigDecimal("0.00"), new Date(), new Date(), PENDING);

        project.setTasks(new ArrayList<ProjectTask>() {{
            add(new ProjectTask(ID, ID, ID, LENGTH_10, LENGTH_30, PENDING, 0,
                    new ArrayList<TaskDevelopmentData>() {{
                        add(new TaskDevelopmentData(ID, ID, BACKEND, JUNIOR));
                    }},
                    new ArrayList<RequestForDevelopers>() {{
                        add(new RequestForDevelopers(ID, BACKEND, JUNIOR, 1));
                    }}));
        }});

        return project;
    }

    public static Check getValidCheck() {
        return new Check(ID, "test", new BigDecimal("10.00"), new BigDecimal("10.00"), new BigDecimal("4.00"), AWAITING);
    }

    public static Check getInvalidCheck() {
        return new Check(ID, "test", new BigDecimal("10.00"), new BigDecimal("10.00"), new BigDecimal("5.00"), AWAITING);
    }
}
