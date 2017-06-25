package dao;

import oracle.ConnectToDatabase;
import tables.Login;
import tables.Oferty;
import tables.Uzytkownicy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Wojtek on 13.06.2017.
 */
public class OfertyUser {

    //*************************************
    //SELECT  FROM oferty
    //*************************************
    public static ArrayList<Oferty> searchOfertyUs () throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM oferty WHERE ILOSC_MIEJSC !=0 ";

        //Execute SELECT statement
        try {
            //Get ResultSet from executeSelect method
            ResultSet resultOferty = ConnectToDatabase.executeSelect(selectStmt);

            //Send ResultSet to the getEmployeeFromResultSet method and get employee object
            ArrayList<Oferty> ofertyList = getOfertyListUs(resultOferty);

            //Return oferty object
            return ofertyList;
        } catch (SQLException e) {
            System.out.println("ERROR !!!! " + e);
            //Return exception
            throw e;
        }
    }

    //*************************************
    //Dodawanie elementow do listy
    //*************************************
    public static ArrayList<Oferty> getOfertyListUs(ResultSet rs) throws SQLException
    {
        ArrayList<Oferty> ofertyList = new ArrayList<>();

        while (rs.next()) {
            Oferty ofe = new Oferty();
            ofe.setId_oferty(rs.getInt("ID_OFERTY"));
            ofe.setOpis(rs.getString("OPIS"));
            ofe.setCena(rs.getDouble("CENA"));
            ofe.setData_pocz(rs.getDate("DATA_POCZ"));
            ofe.setData_konc(rs.getDate("DATA_KONC"));
            ofe.setIlosc_miejsc(rs.getInt("ILOSC_MIEJSC"));
            ofertyList.add(ofe);
        }
        return ofertyList;
    }
    //*************************************
    // Szuka loginu
    //*************************************
    public static Login checkLogintoLabel (Integer id) throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT login FROM uzytkownicy WHERE id_uzytkownika="+id+"";

        //Execute SELECT statement
        try {
            //Get ResultSet from executeSelect method
            ResultSet resultLog = ConnectToDatabase.executeSelect(selectStmt);

            //Send ResultSet to the getEmployeeFromResultSet method and get employee object
            Login lg = getLogin(resultLog);

            //Return oferty object
            return lg;
        } catch (SQLException e) {
            System.out.println(" an error occurred: " + e);
            //Return exception
            throw e;
        }
    }
    //*************************************
    // zapisuje znaleziony login
    //*************************************
    private static Login getLogin(ResultSet rs) throws SQLException
    {
        Login ofe = null;
        if (rs.next()) {
            ofe = new Login();
            ofe.setLogin(rs.getString("LOGIN"));
        }
        return ofe;
    }
    //*************************************
    // dodaje zamownienie
    //*************************************
    public static void addZam (Uzytkownicy uz) throws SQLException, ClassNotFoundException {
        //Declare a insert statement
        String updateStmt = "insert into ZAMOWIENIA values(zamowienia_seq.nextval,"+uz.getId_uzytkownika()+","+uz.getId_wycieczki()+",'"+uz.getUbezpieczenie()+"','Nie')";
        //Execute insert
        try {
            ConnectToDatabase.executeUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while insert Operation: " + e);
            throw e;
        }
    }
    // pomniejsza ilosc miejsc przy skladaniu zamowienia
    public static void decreaseIloscMiejsc (Oferty ofe) throws SQLException, ClassNotFoundException {
        ofe.setIlosc_miejsc(ofe.getIlosc_miejsc()-1);
        String updateStmt = "UPDATE oferty SET ILOSC_MIEJSC="+ofe.getIlosc_miejsc()+" WHERE id_oferty= "+ofe.getId_oferty()+"";
        //Execute insert
        try {
            ConnectToDatabase.executeUpdate(updateStmt);
        } catch (SQLException e) {
            System.out.print("Error occurred while insert Operation: " + e);
            throw e;
        }
    }

    public static ArrayList<Oferty> szukajOferty(Oferty ofe) {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM oferty WHERE OPIS LIKE  '%"+ofe.getOpis()+"%' ";
        ArrayList<Oferty> ofertyList =null;
        //Execute SELECT statement
        try {
            //Get ResultSet from executeSelect method
            ResultSet resultOferty = ConnectToDatabase.executeSelect(selectStmt);

            //Send ResultSet to the getEmployeeFromResultSet method and get employee object
            ofertyList = getszukajOferty(resultOferty);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ofertyList;
    }

    public static ArrayList<Oferty> getszukajOferty(ResultSet rs) throws SQLException
    {
        ArrayList<Oferty> ofertyList = new ArrayList<>();
        while (rs.next()) {
            Oferty ofe = new Oferty();
            ofe.setId_oferty(rs.getInt("ID_OFERTY"));
            ofe.setOpis(rs.getString("OPIS"));
            ofe.setCena(rs.getDouble("CENA"));
            ofe.setData_pocz(rs.getDate("DATA_POCZ"));
            ofe.setData_konc(rs.getDate("DATA_KONC"));
            ofe.setIlosc_miejsc(rs.getInt("ILOSC_MIEJSC"));
            ofertyList.add(ofe);
        }
        return ofertyList;
    }
}