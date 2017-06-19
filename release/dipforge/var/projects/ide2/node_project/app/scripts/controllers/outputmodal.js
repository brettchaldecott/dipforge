'use strict';

/**
 * @ngdoc function
 * @name ide2App.controller:OutputModalCtrl
 * @description
 * # OutputModalCtrl
 * Controller of the ide2App
 */
angular.module('ide2App')
  .controller('OutputModalCtrl', function ($rootScope) {
    
    var vm = this;
    vm.executeStatus = ""
    vm.executeOutput = ""
    
    $rootScope.$on("executeOutput",function(event, data) {
        vm.executeStatus = data.status;
        vm.executeOutput = data.result;
        $('#outputModal').modal('show');
    })
  });
