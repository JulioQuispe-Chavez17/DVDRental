package dao;

import model.Dashboard;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardImpl extends Conexion {
    // Cantidad de usuarios que han alquilado cada género.
    public List<Dashboard> cantidadClientePorGenero(){
        String sql = "SELECT c.name AS Genre, count(DISTINCT cu.customer_id) AS Total_rent_demand\n" +
                "FROM category c\n" +
                "JOIN film_category fc\n" +
                "USING(category_id)\n" +
                "JOIN film f\n" +
                "USING(film_id)\n" +
                "JOIN inventory i\n" +
                "USING(film_id)\n" +
                "JOIN rental r\n" +
                "USING(inventory_id)\n" +
                "JOIN customer cu\n" +
                "USING(customer_id)\n" +
                "GROUP BY 1\n" +
                "ORDER BY 2 DESC;";
        List<Dashboard> listado = new ArrayList<>();
        Dashboard modelo;
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelo = new Dashboard();
                modelo.setGenero(rs.getString(1));
                modelo.setCount(rs.getInt(2));
                listado.add(modelo);
            }
        } catch (Exception e) {
            Logger.getLogger(DashboardImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return listado;
    }
    // Tarifa de alquiler promedio por cada género.
    public List<Dashboard> tarifaAlquilerPromedio(){
        String sql = "SELECT c.name AS genre, ROUND(AVG(f.rental_rate), 2) AS Average_rental_rate\n" +
                "FROM category c\n" +
                "JOIN film_category fc\n" +
                "USING(category_id)\n" +
                "JOIN film f\n" +
                "USING(film_id)\n" +
                "GROUP BY 1\n" +
                "ORDER BY 2 desc;";
        List<Dashboard> listado = new ArrayList<>();
        Dashboard modelo;
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelo = new Dashboard();
                modelo.setGenero(rs.getString(1));
                modelo.setPromedio(rs.getInt(2));
                listado.add(modelo);
            }
        } catch (Exception e) {
            Logger.getLogger(DashboardImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return listado;
    }
    // Ventas totales por país y  BD de clientes por país
    public List<Dashboard> ventasPorPaisBD(){
        String sql = "SELECT country, count(DISTINCT customer_id) AS customer_base, SUM(amount) AS total_sales\n" +
                "FROM country\n" +
                "JOIN city\n" +
                "USING(country_id)\n" +
                "JOIN address\n" +
                "USING(city_id)\n" +
                "JOIN customer\n" +
                "USING(address_id)\n" +
                "JOIN payment\n" +
                "USING(customer_id)\n" +
                "GROUP BY 1\n" +
                "ORDER BY 2 DESC " +
                "LIMIT 10";
        List<Dashboard> listado = new ArrayList<>();
        Dashboard modelo;
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelo = new Dashboard();
                modelo.setCountry(rs.getString(1));
                modelo.setCount(rs.getInt(2));
                modelo.setTotal(rs.getInt(3));
                listado.add(modelo);
            }
        } catch (Exception e) {
            Logger.getLogger(DashboardImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return listado;
    }
    // 10 países con más ventas
    public List<Dashboard> topPaisesVentas(){
        String sql = "SELECT country, SUM(amount) AS total_sales\n" +
                "FROM country\n" +
                "JOIN city\n" +
                "USING(country_id)\n" +
                "JOIN address\n" +
                "USING(city_id)\n" +
                "JOIN customer\n" +
                "USING(address_id)\n" +
                "JOIN payment\n" +
                "USING(customer_id)\n" +
                "GROUP BY 1\n" +
                "ORDER BY 2 DESC\n" +
                "limit 10;";
        List<Dashboard> listado = new ArrayList<>();
        Dashboard modelo;
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelo = new Dashboard();
                modelo.setCountry(rs.getString(1));
                modelo.setTotal(rs.getInt(2));
                listado.add(modelo);
            }
        } catch (Exception e) {
            Logger.getLogger(DashboardImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return listado;
    }
    // 10 países con más clientes
    public List<Dashboard> topPaisesClientes(){
        String sql = "select country, count(customer_id) as total_no_customers\n" +
                "from country\n" +
                "inner join city on country.country_id = city.country_id\n" +
                "inner join address a on city.city_id = a.city_id\n" +
                "inner join customer c on a.address_id = c.address_id\n" +
                "group by 1\n" +
                "order by 2 desc\n" +
                "limit 10;";
        List<Dashboard> listado = new ArrayList<>();
        Dashboard modelo;
        try {
            PreparedStatement ps = this.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelo = new Dashboard();
                modelo.setCountry(rs.getString(1));
                modelo.setTotal(rs.getInt(2));
                listado.add(modelo);
            }
        } catch (Exception e) {
            Logger.getLogger(DashboardImpl.class.getName()).log(Level.SEVERE, null, e);
        }
        return listado;
    }
}
