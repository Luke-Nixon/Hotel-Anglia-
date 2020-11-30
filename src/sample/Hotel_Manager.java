package sample;

public class Hotel_Manager
{
    DB_Manager DB;
    public String admin_password = "l33t";

    public float single_room_cost;
    public float double_room_cost;
    public float executive_room_cost;
    public float presidential_room_cost;

    public float monthly_revenue;

    public Hotel_Manager() // constructer for the Hotel_Manager
    {
        System.out.println("hotel created");
        this.DB = new DB_Manager();


        // on start, populate the rooms into the database, if they are not already there.
        DB.create_rooms(20,30,10,5);

        // on start, set the defualt prices for the rooms
        this.single_room_cost = 5.00f;
        this.double_room_cost = 10.00f;
        this.executive_room_cost = 20.00f;
        this.presidential_room_cost = 40.00f;

        // set the monthly revenue to zero
        this.monthly_revenue = 0;

    }

}
