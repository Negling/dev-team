package ua.devteam.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ResourceBundle;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static ua.devteam.controllers.WebTestUtils.*;

@RunWith(JUnit4.class)
public class ErrorsControllerTest {

    // controller to test
    private ErrorsController controller = new ErrorsController(getViewNamingsBundle());
    private ResourceBundle names = getViewNamingsBundle();
    private ResourceBundle mappings = getMappingBundle();

    // controller mock object
    private MockMvc mockMvc = getConfiguredWithPlaceholdersStandaloneMockMvcBuilder(controller)
            .setViewResolvers(getDefaultViewResolver()).build();

    @Test
    public void internalErrorTest() throws Exception {
        mockMvc.perform(get(mappings.getString("error.500")))
                .andExpect(status().isOk())
                .andExpect(view().name(names.getString("error.500")));
    }

    @Test
    public void accessDeniedErrorTest() throws Exception {
        mockMvc.perform(get(mappings.getString("error.403")))
                .andExpect(status().isOk())
                .andExpect(view().name(names.getString("error.403")));
    }

    @Test
    public void notFoundErrorTest() throws Exception {
        mockMvc.perform(get(mappings.getString("error.404")))
                .andExpect(status().isOk())
                .andExpect(view().name(names.getString("error.404")));
    }
}
