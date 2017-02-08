USE dev_team;

DROP PROCEDURE IF EXISTS check_status;
DROP PROCEDURE IF EXISTS update_project_status;

DELIMITER //
CREATE PROCEDURE check_status(IN id BIGINT UNSIGNED)
BEGIN
 
DECLARE task_data INT DEFAULT 0;
DECLARE complete_task_data INT DEFAULT 0;
DECLARE tasks INT DEFAULT 0;
DECLARE complete_tasks INT DEFAULT 0;
 
SELECT COUNT(*) INTO task_data FROM task_development_data WHERE task_id = id;
SELECT COUNT(*) INTO complete_task_data FROM task_development_data WHERE task_id = id AND status = 'COMPLETE';

IF task_data = complete_task_data THEN
UPDATE project_tasks SET status = 'COMPLETE' WHERE project_tasks.id = id;

SELECT COUNT(*) INTO tasks FROM project_tasks WHERE project_id = id;
SELECT COUNT(*) INTO complete_tasks FROM project_tasks WHERE project_id = id AND status = 'COMPLETE';

IF tasks = complete_tasks THEN
UPDATE projects SET status = 'COMPLETE', end_date = current_timestamp() WHERE projects.id = id;
UPDATE technical_tasks SET status = 'COMPLETE' WHERE technical_tasks.id = (SELECT technical_task_id FROM projects WHERE projects.id = id);
END IF;

END IF;

END//

CREATE PROCEDURE update_project_status(IN id BIGINT UNSIGNED, IN new_status VARCHAR(20))
BEGIN

UPDATE projects SET status = new_status WHERE projects.id = id;
UPDATE technical_tasks SET status = new_status WHERE technical_tasks.id = (SELECT technical_task_id FROM projects WHERE projects.id = id);

END//
DELIMITER ;