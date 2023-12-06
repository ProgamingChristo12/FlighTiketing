package com.smk.view;

import com.smk.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Search Booking")
@Route(value = "search-booking", layout = MainView.class)
public class SearchBooking extends VerticalLayout {

    public SearchBooking() {
        FormLayout searchForm = new FormLayout();
        TextField dari = new TextField("Dari");
        TextField ke = new TextField("Ke");
        DatePicker departureDate = new DatePicker("Departure Date");
        Button searchButton = new Button("Search Flights");

        searchForm.add(dari, ke, departureDate, searchButton);

        add(searchForm);
    }
}