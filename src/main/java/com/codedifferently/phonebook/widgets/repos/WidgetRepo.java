package com.codedifferently.phonebook.widgets.repos;

import com.codedifferently.phonebook.widgets.models.Widget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WidgetRepo extends JpaRepository<Widget, Integer> {
    Optional<Widget> findWidgetByName(String name);
}
