package sample;

import javafx.beans.property.SimpleStringProperty;

public class Booking_Data {


    public String first_name;
    public String last_name;
    public String room_type;
    public String check_in_date;
    public String check_out_date;
    public String customer_id;
    public String booking_id;
    public String room_id;



    public Booking_Data()
    {
        this.first_name = "";
        this.last_name = "";
        this.room_type = "";
        this.check_in_date = "";
        this.check_out_date = "";
        this.customer_id = "";
        this.booking_id = "";
        this.room_id = "";
    }

    public Booking_Data (String first_name,String last_name,String room_type,String check_in_date,String check_out_date,String customer_id,String booking_id,String room_id)
    {
        this.first_name = first_name;
        this.last_name = last_name;
        this.room_type = room_type;
        this.check_in_date = check_in_date;
        this.check_out_date = check_out_date;
        this.customer_id = customer_id;
        this.booking_id = booking_id;
        this.room_id = room_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getCheck_in_date() {
        return check_in_date;
    }

    public void setCheck_in_date(String check_in_date) {
        this.check_in_date = check_in_date;
    }

    public String getCheck_out_date() {
        return check_out_date;
    }

    public void setCheck_out_date(String check_out_date) {
        this.check_out_date = check_out_date;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }
}

