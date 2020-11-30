package sample;

import java.sql.*;

public class DB_Manager
{
    String db_url = "jdbc:postgresql://localhost:5432/postgres";
    String user_name = "postgres";
    String password = "admin";


    Connection db;

    public DB_Manager() // constructer for the database
    {
        // when the class is created..

        // run the connect method to connect the to DB
        this.connect();

        // create the rooms table
        this.define( "CREATE TABLE IF NOT EXISTS rooms (room_ID SERIAL PRIMARY KEY , room_type varchar(30) , status varchar(30)  )" );
        // create the customers table
        this.define( "CREATE TABLE IF NOT EXISTS customers (customer_ID SERIAL PRIMARY KEY  , first_name varchar(255) , last_name varchar(255) , total_cost float )" );
        // create the services table
        this.define( "CREATE TABLE IF NOT EXISTS services (service_ID SERIAL PRIMARY KEY  , customer_ID int ,  service_type varchar(255) , cost float)" );
        // create the bookings table
        this.define( "CREATE TABLE IF NOT EXISTS bookings (booking_ID SERIAL PRIMARY KEY  , customer_ID int , room_ID int , check_in_date DATE, check_out_date Date)");
        // set up the foreign keys for bookings
        this.define( "ALTER TABLE bookings ADD FOREIGN KEY (customer_ID) REFERENCES customers(customer_ID)");
        this.define( "ALTER TABLE bookings ADD FOREIGN KEY (room_ID) REFERENCES rooms(room_ID)");
        // set up the foreign keys for services
        this.define( "ALTER TABLE services ADD FOREIGN KEY (Customer_ID) REFERENCES customers(CUSTOMER_ID)");

    }


   public void connect()
    {
        // wrap in try and catch as postgress may not be running
        try
        {
            // set the Connection the the DB using the url,username and password
            this.db = DriverManager.getConnection(db_url,user_name,password);
            System.out.println("connected");
        }
        catch (Exception e)
        {
            // any errors, print to console
            System.out.println(e);
        }
    }

    // close the connection to the db
   public void disconnect()
    {

        try
        {
            this.db.close();
        }
        catch ( Exception e)
        {

        }
    }

    // used for create and define statements
    public void define(String statement)
    {
        try // wrap in try and catch in case any errors
        {
            // create a new sql statement
            Statement sql_statement = this.db.createStatement();
            // using the input string as the statement, run the query.
            sql_statement.executeUpdate(statement);
        }
        catch (Exception e) // print any errors
        {
            System.out.println(e);
        }
    }

    // used for SQL insert query
    public ResultSet manipulate(String statement)
    {
        try // wrap in try and catch
        {
            // create a new statement
            Statement sql_statement = this.db.createStatement();
            // run the query on the DB and save the result back as a ResultSet
            ResultSet result_set = sql_statement.executeQuery(statement);
            return result_set; // return the result set
        }
        catch (Exception e) // print any errors
        {
            System.out.println(statement);
            System.out.println(e);
            return null;
        }
    }

    // create the rooms for the DB
    public void create_rooms(int single_rooms_to_add, int double_rooms_to_add, int executive_rooms_to_add , int presidential_rooms_to_add)  {


        // create single rooms if needed.

        /*
        This method works by first querying the DB to see how many of the rooms already exsist of the type.
        it then gets the difference between how many there are and how many there should be.
        it will then add as many rooms as needed until the inputted types are the same as what are in the DB.
         */

        // create single rooms if needed
        ResultSet result =  this.manipulate("SELECT COUNT(room_type) FROM rooms WHERE room_type = 'single';");
        int single_rooms_in_db = 0;

        try
        {
            result.next();
            single_rooms_in_db = result.getInt(1);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        if (single_rooms_to_add > single_rooms_in_db)
        {
            int num_off_rooms_to_add = single_rooms_to_add - single_rooms_in_db;

            for (int i=1; i<=num_off_rooms_to_add; i++ )
            {
                this.manipulate("INSERT INTO rooms (room_ID,room_type,status) VALUES ( DEFAULT , 'single' , 'empty' )");

            }
        }
        //

        // create double rooms if needed.
        result =  this.manipulate("SELECT COUNT(room_type) FROM rooms WHERE room_type = 'double';");
        int double_rooms_in_db = 0;

        try
        {
            result.next();
            double_rooms_in_db = result.getInt(1);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        if (double_rooms_to_add > double_rooms_in_db)
        {
            int num_off_rooms_to_add = double_rooms_to_add - double_rooms_in_db;

            for (int i=1; i<=num_off_rooms_to_add; i++ )
            {
                this.manipulate("INSERT INTO rooms (room_ID,room_type,status) VALUES ( DEFAULT , 'double' , 'empty' )");

            }
        }
        //

        // create executive rooms if needed.

        result =  this.manipulate("SELECT COUNT(room_type) FROM rooms WHERE room_type = 'executive';");
        int executive_rooms_in_db = 0;

        try
        {
            result.next();
            executive_rooms_in_db = result.getInt(1);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        if (executive_rooms_to_add > executive_rooms_in_db)
        {
            int num_off_rooms_to_add = executive_rooms_to_add - executive_rooms_in_db;

            for (int i=1; i<=num_off_rooms_to_add; i++ )
            {
                this.manipulate("INSERT INTO rooms (room_ID,room_type,status) VALUES ( DEFAULT , 'executive' , 'empty' )");

            }
        }
        //

        // create presidential rooms if needed.

        result =  this.manipulate("SELECT COUNT(room_type) FROM rooms WHERE room_type = 'presidential';");
        int presidential_rooms_in_db = 0;

        try
        {
            result.next();
            presidential_rooms_in_db = result.getInt(1);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        if (presidential_rooms_to_add > presidential_rooms_in_db)
        {
            int num_off_rooms_to_add = presidential_rooms_to_add - presidential_rooms_in_db;

            for (int i=1; i<=num_off_rooms_to_add; i++ )
            {
                this.manipulate("INSERT INTO rooms (room_ID,room_type,status) VALUES ( DEFAULT , 'presidential' , 'empty' )");

            }
        }
    }
}
