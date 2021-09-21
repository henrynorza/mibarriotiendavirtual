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
 * @author H193250
 */
public class Contacto {
    private int identificacion;
    private String nombre;
    private String apellido;
    private String genero;
    private String tipoIdentificacion;
    private String telefono;
    private String direccion;
    private String correo;

    public Contacto() {
    }

    
    public int getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(int identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public boolean guardarContacto(){
        ConexionBD conexion= new ConexionBD();
        String sentencia = "INSERT INTO contactos(identificacion, nombre, apellido, genero, tipoIdentificacion, telefono, direccion, correo) "
                + " VALUES ( '" + this.identificacion + "','" + this.nombre + "',"
                + "'" + this.apellido + "','" + this.genero + "','" + this.tipoIdentificacion + "',"
                + "'" + this.telefono + "','" + this.direccion + "','" + this.correo +  "');  ";
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
    
    public boolean borrarContacto(int identificacion){
        ConexionBD conexion = new ConexionBD();
        String sentencia  = "DELETE FROM contactos WHERE identificacion ='" + identificacion +"';";
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
    
    
    public boolean actualizarContacto(){
        ConexionBD conexion = new ConexionBD();
        String sentencia  = "UPDATE `contactos` SET nombre='" + this.nombre + "',apellido='" + this.apellido + "',genero='" + this.genero
                + "',tipoIdentificacion='" + this.tipoIdentificacion + "',telefono='" + this.telefono + "',direccion='" + this.direccion + "',correo='" + this.correo
                +  "' WHERE identificacion=" + this.identificacion +";";
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
    
    public List<Contacto> listarContactos() throws SQLException{
        ConexionBD conexion = new ConexionBD();
        String sentencia = "SELECT * FROM contactos ORDER BY identificacion ASC;";
        List<Contacto> listaContactos = new ArrayList<>();
        ResultSet rs =  conexion.consultarBD(sentencia);
        Contacto contacto;
        while (rs.next()) {
            contacto = new Contacto();
            contacto.setIdentificacion(rs.getInt("identificacion"));
            contacto.setNombre(rs.getString("nombre"));
            contacto.setApellido(rs.getString("apellido"));
            contacto.setGenero(rs.getString("genero"));
            contacto.setTipoIdentificacion(rs.getString("tipoIdentificacion"));
            contacto.setTelefono(rs.getString("telefono"));
            contacto.setDireccion(rs.getString("direccion"));
            contacto.setCorreo(rs.getString("correo"));
            listaContactos.add(contacto);
        }
        conexion.closeConnection();
        return listaContactos;
        
    }
    
    public Contacto obtenerContacto(int identificacion) throws SQLException {
        ConexionBD conexion = new ConexionBD();
        String sentencia = "SELECT * FROM contactos WHERE identificacion='"+identificacion+"'";
        ResultSet rs =  conexion.consultarBD(sentencia);
        if(rs.next()){
            Contacto contacto= new Contacto();
            contacto.setIdentificacion(rs.getInt("identificacion"));
            contacto.setNombre(rs.getString("nombre"));
            contacto.setApellido(rs.getString("apellido"));
            contacto.setGenero(rs.getString("genero"));
            contacto.setTipoIdentificacion(rs.getString("tipoIdentificacion"));
            contacto.setTelefono(rs.getString("telefono"));
            contacto.setDireccion(rs.getString("direccion"));
            contacto.setCorreo(rs.getString("correo"));
            return contacto;
            
        }else{
            conexion.closeConnection();
            return null; //no había contacto
        }
    }
    
}
