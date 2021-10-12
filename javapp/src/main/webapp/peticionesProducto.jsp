<%-- 
    Document   : peticionesProducto
    Created on : Oct 5, 2021, 8:32:07 PM
    Author     : Henry Norza
--%>
<%@page import="com.google.gson.Gson"%>
<%@page import="java.util.List"%>
<%@page import="logica.producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%
    producto p = new producto(); //se piden los parámetros del contacto que se quiere guardar
    String respuesta ="{";
    //de la siguiente manera logro obtener parametros,con esta haremos cualquier proceso que quiero hacer desde el front-end
    //el request va a obtener todo lo que quiero hacer. Este es un request http
    //a los request se les puede pasar parámetros
    //se va a validar el tipo de proceso
    String proceso = request.getParameter("proceso");
    //vamos a validar cada uno de los casos que tiene el proceso
    switch(proceso){
        case "guardarProducto":
            //debido a que creamos el siguiente parametro como int en producto.java y en la DB, lo vamos a convertir a entero
            System.out.println("Guardar Producto");
            p.setProducto_id(Integer.parseInt(request.getParameter("producto_id")));
            p.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
            p.setNombre(request.getParameter("nombre"));
            p.setPrecio(Float.parseFloat(request.getParameter("precio")));
            p.setTienda_id(Integer.parseInt(request.getParameter("tienda_id")));
            if(p.guardarProducto()){
                //si guarda bien el contacto, se concatena otros datos para el json
                    respuesta += "\"" + proceso + "\":true"; // el \ se usa para concatenar en json indicando que se hizo el proceso (true)
            }else{
                respuesta += "\"" + proceso + "\":false"; // el \ se usa para concatenar en json indicando que NO se hizo el proceso (false)
            }

            break;
        case "actualizarProducto":
            System.out.println("Actualizar producto");
            p.setProducto_id(Integer.parseInt(request.getParameter("producto_id")));
            p.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
            p.setNombre(request.getParameter("nombre"));
            p.setPrecio(Float.parseFloat(request.getParameter("precio")));
            p.setTienda_id(Integer.parseInt(request.getParameter("tienda_id")));
            if(p.actualizarProducto()){
                respuesta += "\"" + proceso + "\":true";
            }else{
                respuesta+= "\"" + proceso + "\":false";
            }
            
            break;
        case "eliminarProducto":
            System.out.println("eliminar Producto");
            int producto_id = Integer.parseInt(request.getParameter("producto_id"));
            if(p.borrarProducto(producto_id)){
                respuesta += "\"" + proceso + "\":true";
            }else{
                respuesta += "\"" + proceso + "\":false";
            }
            break;
        case "listarProductos":
            System.out.println("listarProductos");
            List<producto> listaProductos = p.listarProductos();
            if(listaProductos.isEmpty()){
                respuesta += "\"" + proceso + "\":false,\"productos\":[]";
            }else{
                respuesta += "\"" + proceso + "\":true,\"productos\":"+ new Gson().toJson(listaProductos);
            }
            break;
        case "obtenerProducto":
            System.out.println("obtener Producto");
            int prodi = Integer.parseInt(request.getParameter("producto_id"));
            producto productoi = p.obtenerProducto(prodi);
            if(productoi==null){
                respuesta += "\"" + proceso + "\":false,\"producto\":";
            }else{
                respuesta += "\"" + proceso + "\":true,\"producto\":"+new Gson().toJson(productoi);
            }
            break;
        default:
            respuesta += "\"ok\": false,";
            respuesta += "\"error\": \"INVALID\",";
            respuesta += "\"errorMsg\": \"Lo sentimos, los datos que ha enviado,"
                    + " son inválidos. Corrijalos y vuelva a intentar por favor.\"";
    }
    respuesta += "}";
    response.setContentType("application/json;charset=iso-8859-1");
    out.print(respuesta);
%>