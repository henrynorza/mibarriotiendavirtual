/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global bootstrap, angular */

var app = angular.module("aplicacion", []);
app.controller("Controller01", function ($scope, $http) {
    //*Codigo para alertas
    let alertPlaceholder = document.getElementById('liveAlertPlaceholder');
    function alert(message, type) {
      let wrapper = document.createElement('div');
      wrapper.innerHTML = '<div class="alert alert-' + type + ' alert-dismissible" role="alert">' + 
              message + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button></div>';
      alertPlaceholder.append(wrapper);
    }
    $scope.guardarProducto = function(){
        let producto = {
            proceso:'guardarProducto',
            producto_id:$scope.producto_id,
            cantidad:$scope.cantidad,
            nombre:$scope.nombre,
            precio:$scope.precio,
            tienda_id:1
        };
        console.log(producto);
        $http({
            method: 'POST', 
            url: 'peticionesProducto.jsp', 
            params: producto
        }).then(function (respuesta){
            console.log(respuesta);
            alert('Producto Agregado al inventario','success');
            $scope.listarProductos();
        }).catch(function(error){
            alert('Por favor revise que los datos sean congruentes y esten completos','warning');
            console.log(error);
        });
    };
    
    $scope.listarProductos = function () {
        console.log("Entra listar productos");
        $scope.mostrarListaProductos = true;//*para ocultar el formulario
        let params = {
            proceso: 'listarProductos'
        };
        $http({
            method: 'POST', 
            url: 'peticionesProducto.jsp',
            params: params
        }).then(function (respuesta) {
            console.log(respuesta);
            $scope.productos = respuesta.data.productos; 
            console.log($scope.productos);
        }).catch(function(error){
                alert('Funcion no disponible, por favor intentelo mas tarde','warning');
                console.log(error);
            });
    };
    $scope.eliminarProducto = function (producto_id) {
        let params = {
            proceso: "eliminarProducto",
            producto_id: producto_id

        };
        $http({
            method: 'GET',
            url: 'peticionesProducto.jsp',
            params: params
        }).then(function (respuesta) {
            console.log(respuesta);
            alert('Producto eliminado','success');
            $scope.listarProductos();
        }).catch(function(error){
                alert('Funcion no disponible, por favor intentelo mas tarde','warning');
                console.log(error);
            });
    };
    
    $scope.abrirModal= function(producto_id){
        $scope.Producto_idParaEliminar=producto_id;//*Creamos la variable para poder acceder a el desde cualquier lugar del scope
        console.log($scope.Producto_idParaEliminar);
        var myModal = new bootstrap.Modal(document.getElementById('exampleModal'), {
            keyboard: false
        });
        myModal.show();
    };
    
    $scope.actualizarProducto = function () {
        let producto = {
            proceso: "actualizarProducto",
            producto_id:$scope.producto_id,
            cantidad:$scope.cantidad,
            nombre:$scope.nombre,
            precio:$scope.precio,
            tienda_id:1
            
        };
        $http({
            method: 'POST', 
            url: 'peticionesProducto.jsp',
            params: producto
        }).then(function(respuesta){
            console.log(respuesta);
            if(respuesta.data.actualizarProducto){
                alert('Producto editado','success');
                $scope.listarProductos();
            }else{
                alert('Producto no editado. Revise la informacion','warning');
            };
        }).catch(function(error){
                alert('Funcion no disponible, por favor intentelo mas tarde','warning');
                console.log(error);
        });
    };
    $scope.mostrarFormularioActualizar=function(producto){
        $scope.mostrarListaProductos=false;
        $scope.actualizar=true;
        $scope.producto_id=producto.producto_id;
        $scope.cantidad=producto.cantidad;
        $scope.nombre=producto.nombre;
        $scope.precio=producto.precio;
        $scope.tienda_id=1;
        
    };
    $scope.mostrarFormularioMetodo = function () {
        $scope.mostrarListaProductos = false;
        $scope.mostrarFormulario = true;
        $scope.actualizar=false;
        $scope.producto_id=undefined;
        $scope.cantidad=undefined;
        $scope.nombre=undefined;
        $scope.precio=undefined;
        $scope.tienda_id=1;
    };
    
});