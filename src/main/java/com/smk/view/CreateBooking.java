package com.smk.view;

import com.smk.MainView;
import com.smk.Model.Booking;
import com.smk.Model.Location;
import com.smk.Model.Schedule;
import com.smk.Model.dto.ScheduleDTO;
import com.smk.dao.BookingDao;
import com.smk.dao.LocationDao;
import com.smk.dao.ScheduleDao;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.awt.print.Book;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

@PageTitle("Create Booking")
@Route(value = "create-booking", layout = MainView.class)
public class CreateBooking extends VerticalLayout {
    private LocationDao locationDao;
    private final ScheduleDao scheduleDao;
    private static final BookingDao bookingDao = new BookingDao();

    public CreateBooking() {
        locationDao = new LocationDao();
        scheduleDao = new ScheduleDao();
        createForm();
    }

    private void createForm() {
        setAlignItems(Alignment.STRETCH);
        ComboBox<Location> fromComboBox = new ComboBox("Dari");
        fromComboBox.setItems(locationDao.getAll());
        fromComboBox.setItemLabelGenerator(Location::getName);
        ComboBox<Location> toComboBox = new ComboBox("ke");
        toComboBox.setItems(locationDao.getAll());
        toComboBox.setItemLabelGenerator(Location::getName);
        DatePicker depatureDatePicker = new DatePicker("Tanggal Keberangkatan");
        DatePicker arrivalDatePicker = new DatePicker("Tangga Kepulangan");
        Button searchButton = new Button("search");
        searchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(fromComboBox, toComboBox, depatureDatePicker, arrivalDatePicker, searchButton);
        Grid<ScheduleDTO> grid = new Grid<>(ScheduleDTO.class, false);
        grid.addColumn(ScheduleDTO::getId).setHeader("Id");
        grid.addColumn(ScheduleDTO::getFlightNumber).setHeader("Nomor Pesawat");
        grid.addColumn(ScheduleDTO::getDepatureLocation).setHeader("Nomor keberangkatan");
        grid.addColumn(ScheduleDTO::getDepatureDate).setHeader("Waktu Keberangkatan");
        searchButton.addClickListener(buttonClickEvent -> {
            Collection<ScheduleDTO> scheduleDTOCollection = scheduleDao.searchSchedule(
                    fromComboBox.getValue().getId(),
                    toComboBox.getValue().getId(),
                    Date.from(depatureDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())
            );
            grid.setItems(scheduleDTOCollection);
        });

        grid.setItemDetailsRenderer(createBookingRenderer());


        add(
                toComboBox,
                depatureDatePicker,
                arrivalDatePicker,
                searchButton,
                grid);
    }

    private static class CreateBookingFormLayout extends FormLayout {
        private final TextField idTextField = new TextField();
        private final TextField fromTextField = new TextField("Dari");
        private final TextField toTextField = new TextField("Ke");
        private final DatePicker depatureDatePicker = new DatePicker("Tanggal Kenerangkatan");
        private final TextField nameTextField = new TextField("Nama");
        private final TextField priceTextField = new TextField("Harga");
        private final Button saveBooking = new Button("save");

        public CreateBookingFormLayout() {
            idTextField.setVisible(false);
            add(idTextField);
            fromTextField.setReadOnly(true);
            depatureDatePicker.setReadOnly(true);
            Stream.of(fromTextField, toTextField, depatureDatePicker, nameTextField, priceTextField, saveBooking).forEach(this::add);
            Booking booking = new Booking();
            booking.setScheduleId(Long.parseLong(idTextField.getValue()));
            booking.setName(nameTextField.getValue());
            booking.setPrice(Double.parseDouble(priceTextField.getValue()));
            Optional<Integer> id = bookingDao.save(booking);
            id.ifPresent(Integer -> {
                ConfirmDialog confirmDialog = new ConfirmDialog();
                confirmDialog.setText("booking created with id = " + Integer);
                confirmDialog.setCancelable(false);
                confirmDialog.setRejectable(false);
                confirmDialog.setConfirmText("Ok");
                confirmDialog.addConfirmListener(event -> {
                    confirmDialog.close();
                });
                add(confirmDialog);
                confirmDialog.open();
            });
        }

        public void setScheduleDTO(ScheduleDTO scheduleDTO) {
            idTextField.setValue(String.valueOf(scheduleDTO.getId()));
            fromTextField.setValue(scheduleDTO.getDepatureLocation());
            toTextField.setValue(scheduleDTO.getArrivalLocation());
            depatureDatePicker.setValue(LocalDate.parse(scheduleDTO.getDepatureDate().toString()));
        }
    }

    private static ComponentRenderer<CreateBookingFormLayout, ScheduleDTO> createBookingRenderer() {
        return new ComponentRenderer<>(CreateBookingFormLayout::new, CreateBookingFormLayout::setScheduleDTO);
    }
}
    // end of satu kurung kurawal

    //componentRenderer
    //grid.setItem

