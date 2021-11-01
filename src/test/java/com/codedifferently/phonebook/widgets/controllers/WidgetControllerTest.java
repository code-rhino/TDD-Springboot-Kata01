package com.codedifferently.phonebook.widgets.controllers;

import com.codedifferently.phonebook.BaseControllerTest;
import com.codedifferently.phonebook.widgets.exceptions.WidgetNotFoundException;
import com.codedifferently.phonebook.widgets.models.Widget;
import com.codedifferently.phonebook.widgets.models.WidgetPart;
import com.codedifferently.phonebook.widgets.services.WidgetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.hamcrest.core.Is;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class WidgetControllerTest extends BaseControllerTest {

    @MockBean
    private WidgetService mockWidgetService;

    @Autowired
    private MockMvc mockMvc;

    private Widget inputWidget;
    private Widget mockResponseWidget1;
    private Widget mockResponseWidget2;

    @BeforeEach
    public void setUp(){
        List<WidgetPart> parts = new ArrayList<>();
        parts.add(new WidgetPart("Part 1"));
        parts.add(new WidgetPart("Part 2"));
        inputWidget = new Widget("Test Widget", parts);

        mockResponseWidget1 = new Widget("Test Widget 01", parts);
        mockResponseWidget1.setId(1);

        mockResponseWidget2 = new Widget("Test Widget 02", parts);
        mockResponseWidget2.setId(2);
    }

    @Test
    @DisplayName("Widget Post: /widgets - success")
    public void createWidgetRequestSuccess() throws Exception {
        BDDMockito.doReturn(mockResponseWidget1).when(mockWidgetService).create(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/widgets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(inputWidget)))

                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Is.is("Test Widget 01")));
    }

    @Test
    @DisplayName("GET /widgets/1 - Found")
    public void getWidgetByIdTestSuccess() throws Exception{
        BDDMockito.doReturn(mockResponseWidget1).when(mockWidgetService).getWidgetById(1);

        mockMvc.perform(get("/widgets/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Test Widget 01")));
    }

    @Test
    @DisplayName("GET /widgets/1 - Not Found")
    public void getWidgetByIdTestFail() throws Exception {
        BDDMockito.doThrow(new WidgetNotFoundException("Not Found")).when(mockWidgetService).getWidgetById(1);
        mockMvc.perform(get("/widgets/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /widgets/1 - Success")
    public void putWidgetTestNotSuccess() throws Exception{
        List<WidgetPart> parts = new ArrayList<>();
        parts.add(new WidgetPart("Part 1"));
        parts.add(new WidgetPart("Part 2"));
        Widget expectedWidgetUpdate = new Widget("After Update Widget", parts);
        expectedWidgetUpdate.setId(1);
        BDDMockito.doReturn(expectedWidgetUpdate).when(mockWidgetService).updateWidget(any(), any());
        mockMvc.perform(put("/widgets/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(inputWidget)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("After Update Widget")));
    }

    @Test
    @DisplayName("PUT /widgets/1 - Not Found")
    public void putWidgetTestNotFound() throws Exception{
        BDDMockito.doThrow(new WidgetNotFoundException("Not Found")).when(mockWidgetService).updateWidget(any(), any());
        mockMvc.perform(put("/widgets/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(inputWidget)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /widgets/1 - Success")
    public void deleteWidgetTestNotSuccess() throws Exception{
        BDDMockito.doReturn(true).when(mockWidgetService).deleteWidget(any());
        mockMvc.perform(delete("/widgets/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /widgets/1 - Not Found")
    public void deleteWidgetTestNotFound() throws Exception{
        BDDMockito.doThrow(new WidgetNotFoundException("Not Found")).when(mockWidgetService).deleteWidget(any());
        mockMvc.perform(delete("/widgets/{id}", 1))
                .andExpect(status().isNotFound());
    }
}
