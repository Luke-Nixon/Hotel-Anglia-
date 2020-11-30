package sample;

public class Checking_Data
{
    // displayed in the table
    public String check_in_first_name;
    public String check_in_last_name;
    public String check_in_room_type;
    public String check_in_room_id;
    public String check_in_status;

    // not displayed in table, but still used
    public String check_in_customer_id;
    public String check_in_booking_id;




    public Checking_Data()
    {
        this.check_in_first_name = "";
        this.check_in_last_name = "";
        this.check_in_room_type = "";
        this.check_in_room_id = "";
        this.check_in_status = "";

        this.check_in_customer_id = "";
        this.check_in_booking_id = "";
    }

    public Checking_Data(String first_name, String last_name, String room_type, String room_id, String status, String customer_id, String booking_id)
    {
        this.check_in_first_name = first_name;
        this.check_in_last_name = last_name;
        this.check_in_room_type = room_type;
        this.check_in_room_id = room_id;
        this.check_in_status = status;

        this.check_in_customer_id = customer_id;
        this.check_in_booking_id = booking_id;
    }


    public String getCheck_in_first_name() {
        return check_in_first_name;
    }

    public void setCheck_in_first_name(String check_in_first_name) {
        this.check_in_first_name = check_in_first_name;
    }

    public String getCheck_in_last_name() {
        return check_in_last_name;
    }

    public void setCheck_in_last_name(String check_in_last_name) {
        this.check_in_last_name = check_in_last_name;
    }

    public String getCheck_in_room_type() {
        return check_in_room_type;
    }

    public void setCheck_in_room_type(String check_in_room_type) {
        this.check_in_room_type = check_in_room_type;
    }

    public String getCheck_in_room_id() {
        return check_in_room_id;
    }

    public void setCheck_in_room_id(String check_in_room_id) {
        this.check_in_room_id = check_in_room_id;
    }

    public String getCheck_in_status() {
        return check_in_status;
    }

    public void setCheck_in_status(String check_in_status) {
        this.check_in_status = check_in_status;
    }

    public String getCheck_in_customer_id() {
        return check_in_customer_id;
    }

    public void setCheck_in_customer_id(String check_in_customer_id) {
        this.check_in_customer_id = check_in_customer_id;
    }

    public String getCheck_in_booking_id() {
        return check_in_booking_id;
    }

    public void setCheck_in_booking_id(String check_in_booking_id) {
        this.check_in_booking_id = check_in_booking_id;
    }
}
