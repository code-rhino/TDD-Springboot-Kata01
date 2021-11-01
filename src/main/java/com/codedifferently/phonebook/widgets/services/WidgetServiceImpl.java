package com.codedifferently.phonebook.widgets.services;

import com.codedifferently.phonebook.widgets.exceptions.WidgetNotFoundException;
import com.codedifferently.phonebook.widgets.models.Widget;
import com.codedifferently.phonebook.widgets.repos.WidgetRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WidgetServiceImpl implements WidgetService{

    private static Logger logger = LoggerFactory.getLogger(WidgetServiceImpl.class);

    private WidgetRepo widgetRepo;

    @Autowired
    public WidgetServiceImpl(WidgetRepo widgetRepo){
        this.widgetRepo = widgetRepo;
    }

    @Override
    public Widget create(Widget widget) {
        Widget savedWidget = widgetRepo.save(widget);
        return savedWidget;
    }

    @Override
    public Widget getWidgetById(Integer id) throws WidgetNotFoundException {
        Optional<Widget> widgetOptional = widgetRepo.findById(id);
        if(widgetOptional.isEmpty()){
            logger.error("Widget with id {} does not exist", id);
            throw new WidgetNotFoundException("Widget not found");
        }
        return widgetOptional.get();
    }

    @Override
    public List<Widget> getAllWidgets() {
        return (List) widgetRepo.findAll();
    }

    @Override
    public Widget updateWidget(Integer id, Widget widget) throws WidgetNotFoundException {
        Optional<Widget> widgetOptional = widgetRepo.findById(id);
        if(widgetOptional.isEmpty()){
            throw new WidgetNotFoundException("Widget does not exists, can not update");
        }
        Widget savedWidget = widgetOptional.get();
        savedWidget.setWidgetParts(widget.getWidgetParts());
        savedWidget.setName(widget.getName());

        return widgetRepo.save(savedWidget);
    }

    @Override
    public Boolean deleteWidget(Integer id) throws WidgetNotFoundException {
        Optional<Widget> widgetOptional = widgetRepo.findById(id);
        if(widgetOptional.isEmpty()){
            throw new WidgetNotFoundException("Widget does not exist, can not delete");
        }
        Widget widgetToDelete = widgetOptional.get();
        widgetRepo.delete(widgetToDelete);
        return true;
    }
}
