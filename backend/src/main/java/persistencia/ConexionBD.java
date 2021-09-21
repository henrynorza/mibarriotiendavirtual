/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author H193250
 */
public class ConexionBD {
    private String DB_driver ="";
    private String url = "";
    private String db = "";
    private String host = "";
    private String username = "";
    private String password = "";
    public Connection conexion = null;
    //el resultset sera el tipo de datos que me va a retornar esta clase. 
    //Entonces, cada vez que pida info a DB, me va a devolver datos de este tipo
    public ResultSet rs = null;
    public Statement stmt=null;
    //constructor. Se puede crear automaticamente con click secundario, 
    //insert code, constructor
    public ConexionBD() {
        DB_driver = "com.mysql.jdbc.Driver";
        host="localhost:3306";
        db="world";
        url="jdbc:mysql://" + host + "/" + db;
        username = "root";
        password = "";
        //haremos un try catch por si llega a fallar la conexion a DB
        try{
            //Asignacion del driver
            //Antes de usar el driver, segun la documentacion debemos asignar el driver
            Class.forName(DB_driver);
        } catch(ClassNotFoundException ex){
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE,null,ex);
            //System.out.println("Error al asignar driver");
        }
        try{
            //Realizar conexion entre Java y mysql
            conexion = DriverManager.getConnection(url,username,password);
            System.out.println("Conexion exitosa");
        }catch(SQLException ex){
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    //metodo para retornar conexion a db
    public Connection getConnection(){
        return conexion;
    }
    //siempre debo cerrar la conexion despues de usarla. 
    //cerrar conexion
    public void closeConnection(){
        if(conexion!=null){
            try{
                conexion.close();
            }catch(SQLException ex){
                Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE,null,ex);
            }
        }
    }
    //metodo para devolver datos de una consulta
    public ResultSet consultarBD(String sentencia){
        try{
            //se crea el statement para consultas, solo para leer datos.
            stmt = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sentencia);
            
        }catch(SQLException | RuntimeException sqlex){
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE,null,sqlex);
        }
        return rs;
    }
    //Insertar a la base de datos, como no devuelve datos esta accion, lo 
    //dejaremos con booleano que nos diga si logro hacer la operacion
    public boolean insertarBD(String sentencia){
        try{
            stmt = conexion.createStatement();
            stmt.execute(sentencia);
            return true;
        }catch(SQLException | RuntimeException sqlex){
            System.out.println("Error insertarDB");
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE,null,sqlex);
            return false;
        }
    }
    //Borrar de laBD
    public boolean borrarBD(String sentencia){
        try{
            stmt = conexion.createStatement();
            stmt.execute(sentencia);
            return true;
        }catch(SQLException | RuntimeException sqlex){
            System.out.println("Error borrarDB");
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE,null,sqlex);
            return false;
        }
    }
   //Actualizar
    public boolean actualizarBD(String sentencia){
            try {
            stmt = conexion.createStatement();
            stmt.execute(sentencia);
            return true;
        } catch (SQLException | RuntimeException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error al actualizar en la BD");
            return false;
        }
    }
    //este metodo va a tener un parametro para confirmar que haga commit automaticamente
    public boolean setAutoCommitBD(boolean commit){
        try {
            conexion.setAutoCommit(commit);
            return true;
        } catch (SQLException | RuntimeException ex) {
            System.out.println("Error en set Autocommit");
            return false;
        }
    }
    //SI uso el metodo anteriro para no hacer autocommit, el siguiente metodo es para confirmar que haga el commit
    public boolean commitBD(){
        try {
            conexion.commit();
            return true;
        } catch (SQLException | RuntimeException ex) {
            System.out.println("Error en commit a la BD");
            return false;
        }
    }
    //Que devuelva los cambios que se hicieron. Este es para devolver los cambios antes de yo hacer el cambio
    public boolean rollbackBD(){
            try {
            conexion.rollback();
            return true;
        } catch (SQLException | RuntimeException ex) {
            System.out.println("Error en rollback a la BD");
           return false;
        }
    }
}
