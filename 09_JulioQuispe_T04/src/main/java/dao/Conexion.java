package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    protected static Connection cnx = null;

    public static Connection conectar() throws Exception {
        try {
           Class.forName("org.postgresql.Driver");
            cnx = DriverManager.getConnection("jdbc:postgresql://myserverdb.postgres.database.azure.com:5432/dvdrental", "devops", "vallegrande2021$");
            //cnx = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dvdrental", "postgres", "root");
        } catch (ClassNotFoundException | SQLException e) {
            throw e;
        }
        return cnx;
    }

    public static void cerrarCnx() throws Exception {
        if (Conexion.cnx != null) {
            cnx.close();
        }
    }

    public static void main(String[] args) throws Exception {
        Conexion cnx = new Conexion();
        if(cnx.conectar() != null){
            System.out.println("Cañon");
        }else{
            System.out.println("Falla");
        }
    }
}
