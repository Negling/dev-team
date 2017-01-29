package ua.devteam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * General class for any-level task. Contains 3 key fields that eny task obtain.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractTask {
    /* Unique long identification value */
    private Long id;
    /* Task name that shortly represents what it's about */
    private String name;
    /* Large and detailed description what it's about*/
    private String description;

    public abstract void setDeepId(Long id);
}
