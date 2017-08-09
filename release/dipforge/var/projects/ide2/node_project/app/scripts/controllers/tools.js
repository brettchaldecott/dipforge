'use strict';

/**
 * @ngdoc function
 * @name ide2App.controller:ToolsCtrl
 * @description
 * # ToolsCtrl
 * Controller of the ide2App
 */
angular.module('ide2App')
  .controller('ToolsCtrl', function ($rootScope, $scope) {
    var vm = this;
    
    vm.launchLogConsole = function() {
        console.log("Log console says hi")
        $rootScope.$broadcast('openTool', {tool: "LogConsole"});
    }
    
    vm.launchSparqlConsole = function() {
        console.log("Sparql console says hi")
        $rootScope.$broadcast('openTool', {tool: "Sparql"});
    }
    
    vm.launchSearchConsole = function() {
        console.log("Search console says hi")
    }
    
  });
