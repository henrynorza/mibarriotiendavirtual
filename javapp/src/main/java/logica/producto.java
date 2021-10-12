/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import persistencia.ConexionBD;

/**
 *
 * @author Henry Norza
 */
public class producto {
    private int producto_id;
    private int cantidad;
    private String nombre;
    private float precio;
    private int tienda_id;

    public producto() {
    }

    public int getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(int producto_id) {
        this.producto_id = producto_id;
    }
    
    
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getTienda_id() {
        return tienda_id;
    }

    public void setTienda_id(int tienda_id) {
        this.tienda_id = tienda_id;
    }
    
    public boolean guardarProducto(){
        ConexionBD conexion= new ConexionBD();
        String sentencia = "INSERT INTO producto(producto_id,cantidad, nombre, precio, tienda_id) "
                + " VALUES ( '" +this.producto_id+ "','"  + this.cantidad + "','" + this.nombre + "',"
                + "'" + this.precio + "','" + this.tienda_id +"');  ";
        //Vamos a configurar el setAutocommit de la conexionBD a falso
        if(conexion.setAutoCommitBD(false)){
            if(conexion.insertarBD(sentencia)){
                conexion.commitBD();
                conexion.closeConnection();
                return true;
            }else{//si no logro insertar en la BD
                conexion.rollbackBD();
                conexion.closeConnection();
                return false;
            }   
        }else{
            conexion.closeConnection();
            return false;
        }
    }
    
    public boolean borrarProducto(int producto_id){
        ConexionBD conexion = new ConexionBD();
        String sentencia  = "DELETE FROM producto WHERE producto_id ='" + producto_id +"';";
        if(conexion.setAutoCommitBD(false)){
            if(conexion.borrarBD(sentencia)){
                conexion.commitBD();
                conexion.closeConnection();
                return true;
            }else{
                conexion.rollbackBD();
                conexion.closeConnection();
                return false;
            }
        }else{
            conexion.closeConnection();
            return false;
        }
    }
    
    public boolean actualizarProducto(){
        ConexionBD conexion = new ConexionBD();
        String sentencia  = "UPDATE `producto` SET cantidad='" + this.cantidad 
                + "',nombre='" + this.nombre + "',precio='" + this.precio
                + "',tienda_id='" + this.tienda_id
                +  "' WHERE producto_id=" + this.producto_id +";";
        if(conexion.setAutoCommitBD(false)){
            if(conexion.actualizarBD(sentencia)){
                conexion.commitBD();
                conexion.closeConnection();
                return true;
            }else{
                conexion.rollbackBD();
                conexion.closeConnection();
                return false;
            }
        }else{
            conexion.closeConnection();
            return false;
        }
    }
    
    public List<producto> listarProductos() throws SQLException{
        ConexionBD conexion = new ConexionBD();
        String sentencia = "SELECT * FROM producto ORDER BY producto_id ASC;";
        List<producto> listaProductos = new ArrayList<>();
        ResultSet rs =  conexion.consultarBD(sentencia);
        producto productoi;
        while (rs.next()) {
            productoi = new producto();
            productoi.setCantidad(rs.getInt("cantidad"));
            productoi.setNombre(rs.getString("nombre"));
            productoi.setPrecio(rs.getFloat("precio"));
            productoi.setTienda_id(rs.getInt("tienda_id"));
            productoi.setProducto_id(rs.getInt("producto_id"));
            listaProductos.add(productoi);
        }
        conexion.closeConnection();
        return listaProductos;
        
    }
    
    public producto obtenerProducto(int producto_id) throws SQLException {
        ConexionBD conexion = new ConexionBD();
        String sentencia = "SELECT * FROM producto WHERE producto_id='"+producto_id+"'";
        ResultSet rs =  conexion.consultarBD(sentencia);
        if(rs.next()){
            producto productoi= new producto();
            productoi.setProducto_id(rs.getInt("producto_id"));
            productoi.setCantidad(rs.getInt("cantidad"));
            productoi.setNombre(rs.getString("nombre"));
            productoi.setPrecio(rs.getFloat("precio"));
            productoi.setTienda_id(rs.getInt("tienda_id"));
            
            return productoi;
            
        }else{
            conexion.closeConnection();
            return null; //no había producto
        }
    }
}
