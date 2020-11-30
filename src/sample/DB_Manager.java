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
        System.out.println("making the DB");

        this.connect();

        this.define( "CREATE TABLE IF NOT EXISTS rooms (room_ID SERIAL PRIMARY KEY , room_type varchar(30) , status varchar(30)  )" );

        this.define( "CREATE TABLE IF NOT EXISTS customers (customer_ID SERIAL PRIMARY KEY  , first_name varchar(255) , last_name varchar(255) , total_cost float )" );

        this.define( "CREATE TABLE IF NOT EXISTS services (service_ID SERIAL PRIMARY KEY  , customer_ID int ,  service_type varchar(255) , cost float)" );

        this.define( "CREATE TABLE IF NOT EXISTS bookings (booking_ID SERIAL PRIMARY KEY  , customer_ID int , room_ID int , check_in_date DATE, check_out_date Date)");

        // foreign keys for bookings
        this.define( "ALTER TABLE bookings ADD FOREIGN KEY (customer_ID) REFERENCES customers(customer_ID)");
        this.define( "ALTER TABLE bookings ADD FOREIGN KEY (room_ID) REFERENCES rooms(room_ID)");

        // foreign keys for services
        this.define( "ALTER TABLE services ADD FOREIGN KEY (Customer_ID) REFERENCES customers(CUSTOMER_ID)");

    }


   public void connect()
    {
        try
        {
            this.db = DriverManager.getConnection(db_url,user_name,password);
            System.out.println("connected");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

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

    public boolean get_connection_status()
    {
        //this.db
        return true;
    }

    public void define(String statement)
    {
        try
        {
            Statement sql_statement = this.db.createStatement();
            sql_statement.executeUpdate(statement);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public ResultSet manipulate(String statement)
    {
        try
        {
            Statement sql_statement = this.db.createStatement();
            ResultSet result_set = sql_statement.executeQuery(statement);
            return result_set;
        }
        catch (Exception e)
        {
            System.out.println(statement);
            System.out.println(e);
            return null;
        }
    }

    public void create_rooms(int single_rooms_to_add, int double_rooms_to_add, int executive_rooms_to_add , int presidential_rooms_to_add)  {


        // create single rooms if needed.
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
