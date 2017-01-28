package ua.devteam.service;


import ua.devteam.entity.projects.Check;

import java.util.List;

/**
 * Provides service operations to {@link Check check}.
 */
public interface ChecksService {

    /**
     * Changes status of check that bind to project with requested id to "PAID" and delegates to projects service
     * subsequent operations.
     *
     * @param projectId id of project that check is bind to.
     */
    void accept(Long projectId);

    /**
     * Changes status of check that bind to project with requested id to "DECLINED" and delegates to projects service
     * subsequent operations.
     *
     * @param projectId id of project that check is bind to.
     */
    void decline(Long projectId);

    /**
     * Registers check with "AWAITING" status, and delegates to projects service subsequent operations.
     *
     * @param check to register
     */
    void registerCheck(Check check);

    /**
     * Returns list of checks that bind to specified customer and have "AWAITING" status.
     *
     * @param customerId id as query param
     * @return List of Checks or empty lis
     */
    List<Check> getNewByCustomer(Long customerId);

    /**
     * Returns list of checks that bind to specified customer and have "PAID" or "DECLINED" status.
     *
     * @param customerId id as query param
     * @return List of Checks or empty lis
     */
    List<Check> getCompleteByCustomer(Long customerId);
}
