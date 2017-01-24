package ua.devteam.entity.enums;

/**
 * Used to represent current status of development.
 * {@link ua.devteam.entity.projects.TechnicalTask}
 * {@link ua.devteam.entity.projects.Project}
 * {@link ua.devteam.entity.tasks.ProjectTask}
 * {@link ua.devteam.entity.tasks.TaskDevelopmentData}
 */
public enum Status {
    NEW, PENDING, DECLINED, CANCELED, RUNNING, COMPLETE
}
