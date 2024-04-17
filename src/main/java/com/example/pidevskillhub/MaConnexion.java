package com.example.pidevskillhub;



import java.sql.*;

public class MaConnexion {
    public static MaConnexion ct;
    public static MaConnexion getInstance() {
        if (ct == null) {
            ct = new MaConnexion();
        }
        return ct;
    }

    public final String url = "jdbc:mysql://localhost:3306/freelance";
    public final String user = "root";
    public final String pwd = "";
    private Connection cnx;

    private MaConnexion() {
        try {
            cnx = DriverManager.getConnection(url, user, pwd);
            System.out.println("Connexion etablie !!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }


    public Connection getCnx() {
        return cnx;
    }

}

