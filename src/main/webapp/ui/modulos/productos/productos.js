var moduloProductos=angular.module('productos',['ui.bootstrap','ui-notification','oitozero.ngSweetAlert']);

moduloProductos.controller('ProductosController',
	function($scope, $rootScope, SweetAlert, Notification, productosService) {
		$scope.title="Productos";
		
		$scope.data=[];
		
		$scope.refresh=function(buscar){
			productosService.list(buscar).then(
				function(resp){
					$scope.data=resp.data;
					$scope.totalDeItems = $scope.data.length;
				},
				function(err){}
			);
		}; //End refresh()
	
		$scope.refresh(false);
		
		
		$scope.verItemsPorPagina = 3;
 		$scope.totalDeItems = 0;
  		$scope.paginaActual = 1;
  		$scope.itemsPorPagina = $scope.verItemsPorPagina;
		$scope.setItemsPorPagina = function(num) {
			$scope.itemsPorPagina = num;
			$scope.paginaActual = 1;
		};//End setItemsPorPagina()
		
		
		$scope.addEdit=function(p){
			if(p) { //Editar
				
			} else { //Agregar
			
			}
		}; //End addEdit()
		
		$scope.remove=function(p) {
			SweetAlert.swal({
				title: "Eliminar producto",
				text: "Está segur@ que desea elilminar el producto <strong>"+p.nombre+"</strong>?",
				type: "warning",
			    showCancelButton: true,
				confirmButtonColor: "#DD6B55",
				confirmButtonText: "Si, eliminar producto!",
				cancelButtonText: "No",
				closeOnConfirm: true,
				html: true
			}, function(confirm){
				if(confirm) {
					productosService.remove(p).then(
						function(resp) {
							$scope.refresh();
							Notification.success({message:'El producto se ha eliminado',title:'Operación existosa!'});
						}, 
						function(err) {
							Notification.error({message:'No se ha podido eliminar el producto',title:'Operación fallida!'});
						}
					);
				}
			});
		};//End remove()
		
	} 
); //End ProductosController


moduloProductos.factory('productosService',
	function($http, URL_API_BASE, URL_BASE) {
		return {
			list: function(buscar) {
				var qs="";
				if(buscar)
					qs="?parte="+buscar;
				return $http.get(URL_API_BASE+"productos"+qs);
			},
			add: function(p) {
			},
			edit: function(p) {
			},
			remove: function(p) {
				return $http.delete(URL_API_BASE+"productos/"+p.id);
			}
			
			
		}
	}
); //End productosService