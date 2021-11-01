package com.codedifferently.phonebook.widgets.services;

import com.codedifferently.phonebook.widgets.exceptions.WidgetNotFoundException;
import com.codedifferently.phonebook.widgets.models.Widget;
import com.codedifferently.phonebook.widgets.models.WidgetPart;
import com.codedifferently.phonebook.widgets.repos.WidgetRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class WidgetServiceTest {

    @MockBean
    private WidgetRepo mockWidgetRepo;

    @Autowired
    private WidgetService widgetService;

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
    @DisplayName("Widget Service: Create Widget - Success")
    public void createWidgetTestSuccess(){
        BDDMockito.doReturn(mockResponseWidget1).when(mockWidgetRepo).save(ArgumentMatchers.any());
        Widget returnedWidget = widgetService.create(inputWidget);
        Assertions.assertNotNull(returnedWidget, "Widget should not be null");
        Assertions.assertEquals(returnedWidget.getId(),1 );
    }

    @Test
    @DisplayName("Widget Service: Get Widget by Id - Success")
    public void getWidgetByIdTestSuccess() throws WidgetNotFoundException {
        BDDMockito.doReturn(Optional.of(mockResponseWidget1)).when(mockWidgetRepo).findById(1);
        Widget foundWidget = widgetService.getWidgetById(1);
        Assertions.assertEquals(mockResponseWidget1.toString(), foundWidget.toString());
    }

    @Test
    @DisplayName("Widget Service: Get Widget by Id - Fail")
    public void getWidgetByIdTestFailed() {
        BDDMockito.doReturn(Optional.empty()).when(mockWidgetRepo).findById(1);
        Assertions.assertThrows(WidgetNotFoundException.class, () -> {
            widgetService.getWidgetById(1);
        });
    }

    @Test
    @DisplayName("Widget Service: Get All Widgets - Success")
    public void getAllWidgetsTestSuccess(){
        List<Widget> widgets = new ArrayList<>();
        widgets.add(mockResponseWidget1);
        widgets.add(mockResponseWidget2);

        BDDMockito.doReturn(widgets).when(mockWidgetRepo).findAll();

        List<Widget> responseWidgets = widgetService.getAllWidgets();
        Assertions.assertIterableEquals(widgets,responseWidgets);
    }

    @Test
    @DisplayName("Widget Service: Update Widget - Success")
    public void updateWidgetTestSuccess() throws WidgetNotFoundException {
        List<WidgetPart> parts = new ArrayList<>();
        parts.add(new WidgetPart("Part 1"));
        parts.add(new WidgetPart("Part 2"));
        Widget expectedWidgetUpdate = new Widget("After Update Widget", parts);

        BDDMockito.doReturn(Optional.of(mockResponseWidget1)).when(mockWidgetRepo).findById(1);
        BDDMockito.doReturn(expectedWidgetUpdate).when(mockWidgetRepo).save(ArgumentMatchers.any());

        Widget actualWidget = widgetService.updateWidget(1, expectedWidgetUpdate);
        Assertions.assertEquals(expectedWidgetUpdate.toString(), actualWidget.toString());
    }

    @Test
    @DisplayName("Widget Service: Update Widget - Fail")
    public void updateWidgetTestFail()  {
        List<WidgetPart> parts = new ArrayList<>();
        parts.add(new WidgetPart("Part 1"));
        parts.add(new WidgetPart("Part 2"));
        Widget expectedWidgetUpdate = new Widget("After Update Widget", parts);
        BDDMockito.doReturn(Optional.empty()).when(mockWidgetRepo).findById(1);
        Assertions.assertThrows(WidgetNotFoundException.class, ()-> {
            widgetService.updateWidget(1, expectedWidgetUpdate);
        });
    }

    @Test
    @DisplayName("Widget Service: Delete Widget - Success")
    public void deleteWidgetTestSuccess() throws WidgetNotFoundException {
        BDDMockito.doReturn(Optional.of(mockResponseWidget1)).when(mockWidgetRepo).findById(1);
        Boolean actualResponse = widgetService.deleteWidget(1);
        Assertions.assertTrue(actualResponse);
    }

    @Test
    @DisplayName("Widget Service: Delete Widget - Fail")
    public void deleteWidgetTestFail()  {
        BDDMockito.doReturn(Optional.empty()).when(mockWidgetRepo).findById(1);
        Assertions.assertThrows(WidgetNotFoundException.class, ()-> {
            widgetService.deleteWidget(1);
        });
    }

}
