package ua.devteam.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.devteam.entity.users.Developer;
import ua.devteam.entity.enums.DeveloperRank;
import ua.devteam.entity.enums.DeveloperSpecialization;
import ua.devteam.service.DevelopersService;
import ua.devteam.service.ProjectsService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manage")
public class ManagersParlorController {

    private ProjectsService projectsService;
    private DevelopersService developersService;

    /*@Autowired*/
    public void setDevelopersService(DevelopersService developersService) {
        this.developersService = developersService;
    }

    /*@Autowired*/
    public void setProjectsService(ProjectsService projectsService) {
        this.projectsService = projectsService;
    }

    @RequestMapping
    public String managersParlor() {
        return "managers-parlor";
    }

    @RequestMapping("/getDevelopers")
    @ResponseBody
    public List<Developer> getDevelopers(@RequestParam DeveloperSpecialization specialization,
                                         @RequestParam DeveloperRank rank,
                                         @RequestParam String lastName) {
        System.out.println("Query for devs: Specialization = " + specialization + ", Rank = " + rank
                + ", Last Name = " + lastName);

        return developersService.getAvailableDevelopers(specialization, rank, lastName);
    }

    @RequestMapping(value = "/bind", method = RequestMethod.POST)
    @ResponseBody
    public Developer bindDeveloper(@RequestBody Long id) {

        System.out.println("Bind: devId = " + id);
        return developersService.getAvailableDevelopers(null, null, null).get(Math.toIntExact(id) - 1);
    }

    @RequestMapping(value = "/unbind", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void unbindDeveloper(@RequestBody Long id) {

        System.out.println("Unbind: devId = " + id);
    }

    @RequestMapping(value = "/decline", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void declineProject(@RequestBody Long id) {

        System.out.println("Decline Project: id = " + id);
    }

    @RequestMapping(value = "/grab", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void grabProject(@RequestBody Long id) {

        System.out.println("Grab Project: id = " + id);
    }

    @RequestMapping(value = "/accept", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void formProject(@RequestBody Map<Long, Long[]> map) {

        System.out.println("Accept Project: devsMap = " + map);
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        /*model.addAttribute("projectRequests", projectsService.getAllTechnicalTasks());
        model.addAttribute("completeProjects", projectsService.getCompleteProjectsByManagerId((long) 0));
        model.addAttribute("pendingProjects", projectsService.getPendingTechnicalTasksByManagerId((long) 0));*/
        model.addAttribute("specializations", DeveloperSpecialization.values());
        model.addAttribute("ranks", DeveloperRank.values());
    }
}
