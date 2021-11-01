package com.codedifferently.phonebook.widgets.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Widget {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<WidgetPart> widgetParts;

    public Widget() {
    }

    public Widget(String name, List<WidgetPart> widgetParts) {
        this.name = name;
        this.widgetParts = widgetParts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WidgetPart> getWidgetParts() {
        return widgetParts;
    }

    public void setWidgetParts(List<WidgetPart> widgetParts) {
        this.widgetParts = widgetParts;
    }

    @Override
    public String toString() {
        return "Widget{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", widgetParts=" + widgetParts +
                '}';
    }
}
