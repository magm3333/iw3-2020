var app=angular.module('iw3',[]);

app.controller('Div1Controller',function($scope){
    $scope.titulo='Hola desde el controlador del div 1';

    $scope.fontSize=30;


    $scope.datos=[
        {nombre:'Ada', edad: 38},
        {nombre:'Pedro', edad: 13},
        {nombre:'Sebastián', edad: 9},
        {nombre:'Luisa', edad: 45},
        {nombre:'Gisella', edad: 23},
        {nombre:'Teodosio', edad: 112}        

    ];

    $scope.agregar=function(){
        if($scope.nombre && $scope.nombre.length>2 && $scope.edad && $scope.edad>=0) {
            $scope.datos.push({nombre:$scope.nombre, edad: $scope.edad});
        } else {
            alert("Datos incorrectos");
        }
    }
});

app.controller('Div2Controller',function($scope){
    $scope.titulo='Hola desde el controlador del div 2';
});

//Vista <--> ($scope) <--> Controller