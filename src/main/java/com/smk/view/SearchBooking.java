package com.flightbooking.view;

import com.smk.MainView;
import com.smk.Model.Booking;
import com.smk.view.BookingService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Search Booking")
@Route(value = "search-booking", layout = MainView.class)
public class SearchBooking extends VerticalLayout {
    private final BookingService bookingService;
    private final Grid<Booking> grid;
    private final TextField filterText = new TextField();

    public SearchBooking(BookingService bookingService) {
        this.bookingService = bookingService;
        this.grid = new Grid<>(Booking.class);
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureFilter();
        add(filterText, grid);
        updateList();
    }

    private void configureFilter() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
    }

    private void configureGrid() {
        grid.addClassName("booking-grid");
        grid.setSizeFull();
        grid.setColumns("name", "email", "phone", "date", "time", "guests", "table");
    }

    private void updateList() {
        grid.setItems(bookingService.findAll(filterText.getValue()));
    }
}
