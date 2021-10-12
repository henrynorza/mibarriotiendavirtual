<%-- 
    Document   : index
    Created on : Oct 5, 2021, 8:19:16 PM
    Author     : Henry Norza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Productos</title>

        <%--carga de bootstrap--%>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-uWxY/CJNBR+1zjPWmfnSnVxwRheevXITnMqoEIeG1LJrdI0GlVs/9cVSyPYXdcSF" crossorigin="anonymous">
        <%--carga de angular--%>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.15/angular.min.js"></script>
        <link rel="stylesheet" href="css.css">
    </head>
    <body>
        <div ng-app="aplicacion" ng-controller="Controller01">
            <div class="row">
                <nav class="navbar navbar-expand-lg navbar-light" style="background-color: #E64B7B;opacity: 0.8;">
                    <div class="container-fluid">
                        <a class="navbar-brand" href="#">
                            <img src="mibarrio.png" alt="" height="80px">
                        </a>
                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse" id="navbarSupportedContent">
                            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                                <li class="nav-item">
                                    <a class="nav-link active" aria-current="page" href="#" id="tabtext" ng-click="mostrarFormularioMetodo()">Nuevo Producto</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link active" aria-current="page" href="#" id="tabtext" ng-click="listarProductos()">Catalogo</a>
                                </li>
                            </ul>

                        </div>
                    </div>
                </nav>
            </div>
            <div id="liveAlertPlaceholder"></div>
            
            

            <div class="row" style="opacity: 0.85;">
                <div class="col-2"></div>
                <div class="col-8" style="background-color: white;">
                    <div ng-show="!mostrarListaProductos">
                    <h1 ng-show="!actualizar">Agregar nuevo Producto</h1>
                    <h1 ng-show="actualizar">Editar Producto Existente</h1>
                    </div>
                    <div ng-show="mostrarListaProductos">
                    <h1>Catalogo de productos</h1>
                    </div>
                    <div class="row" ng-show="!mostrarListaProductos">
                        <div class="col-5" id="imagenFormProducto">
                            <img src="storeart.jpg" class="img-fluid" alt="Responsive image">
                        </div>
                        <div class="col-7">
                            <div class="row">
                                <div class="form-group">
                                    <label for="inputNombre">Nombre</label>
                                    <input type="text" class="form-control" id="inputNombre" aria-describedby="productoIdHelp" placeholder="Entre el nombre del producto" ng-model="nombre"><br/>
                                    <label for="inputProductoId">ID de producto</label>
                                    <input type="number" class="form-control" id="inputProductoId" aria-describedby="productoIdHelp" placeholder="Entre el ID unico para el producto" ng-model="producto_id" ng-disabled="actualizar"><br/>
                                    <label for="inputCantidad">Cantidad</label>
                                    <input type="number" class="form-control" id="inputCantidad" aria-describedby="productoIdHelp" placeholder="Cantidad de producto" ng-model="cantidad"><br/>
                                    <label for="inputPrecio">Precio unitario</label>
                                    <input type="number" class="form-control" id="inputPrecio" aria-describedby="productoIdHelp" placeholder="Precio por unidad" ng-model="precio"><br/>
                                    <button type="button" class="btn btn-danger" ng-click="guardarProducto()" ng-show="!actualizar">Agregar</button><br/>
                                    <button type="button" class="btn btn-danger" ng-click="actualizarProducto()" ng-show="actualizar">Actualizar</button><br/>
                                </div>

                            </div>
                        </div>
                    </div>
                    <div class="row" ng-show="mostrarListaProductos">
                        <div class="album py-5 bg-light">
                            <div class="container">

                                <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
                                    <div class="col" ng-repeat="producto in productos">
                                        <div class="card shadow-sm">
                                            <svg class="bd-placeholder-img card-img-top" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="#55595c"></rect><text x="50%" y="50%" fill="#eceeef" dy=".3em">Thumbnail</text></svg>

                                            <div class="card-body">
                                                <p class="card-text">{{producto.nombre}}<br>Disponibles {{producto.cantidad}}</p>
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <div class="btn-group">
                                                        <button type="button" class="btn btn-sm btn-outline-secondary" ng-click="mostrarFormularioActualizar(producto)">Editar</button>
                                                        <button type="button" class="btn btn-sm btn-outline-secondary" ng-click="abrirModal(producto.producto_id)">Borrar</button>
                                                    </div>
                                                    <small class="text-muted">$ {{producto.precio}}</small>
                                                </div>
                                            </div>
                                        </div>
                                    </div>


                                </div>
                            </div>
                        </div>



                    </div>

                </div>
                <div class="col-2"></div>
            </div>

            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Borrar producto</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            Esta seguro que desea eliminar el producto?
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                            <button type="button" class="btn btn-primary" ng-click="eliminarProducto(Producto_idParaEliminar)" data-bs-dismiss="modal">Confirmar</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>





    </body>
    <script src="controlador.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-kQtW33rZJAHjgefvhyyzcGF3C5TFyBQBA13V1RKPf4uH+bwyzQxZ6CmMZHmNBEfJ" crossorigin="anonymous"></script>
</html>
