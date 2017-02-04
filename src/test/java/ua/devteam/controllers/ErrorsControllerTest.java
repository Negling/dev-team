package ua.devteam.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static ua.devteam.controllers.WebTestUtils.*;

@RunWith(JUnit4.class)
public class ErrorsControllerTest {

    // controller to test
    private ErrorsController controller = new ErrorsController(getPagesBundle());

    // controller mock object
    private MockMvc mockMvc = getConfiguredWithPlaceholdersStandaloneMockMvcBuilder(controller)
            .setViewResolvers(getDefaultViewResolver()).build();

    @Test
    public void internalErrorTest() throws Exception {
        mockMvc.perform(get("/error-page-500"))
                .andExpect(status().isOk())
                .andExpect(view().name("/errors/500"));
    }

    @Test
    public void accessDeniedErrorTest() throws Exception {
        mockMvc.perform(get("/error-page-403"))
                .andExpect(status().isOk())
                .andExpect(view().name("/errors/403"));
    }

    @Test
    public void notFoundErrorTest() throws Exception {
        mockMvc.perform(get("/error-page-404"))
                .andExpect(status().isOk())
                .andExpect(view().name("/errors/404"));
    }
}
