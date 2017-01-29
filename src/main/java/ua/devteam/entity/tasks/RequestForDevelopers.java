package ua.devteam.entity.tasks;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.devteam.entity.enums.DeveloperRank;
import ua.devteam.entity.enums.DeveloperSpecialization;

import java.io.Serializable;

/**
 * This class represents information about developers that requested by customer to perform specific project operation.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestForDevelopers implements Serializable {

    /* Id of operation that this request is bound to */
    private Long operationId;
    /* Specialization of developers */
    private DeveloperSpecialization specialization;
    /* Rank of developers */
    private DeveloperRank rank;
    /* Quantity of developers. May accept values between 1 and 100. */
    private Integer quantity;
}
