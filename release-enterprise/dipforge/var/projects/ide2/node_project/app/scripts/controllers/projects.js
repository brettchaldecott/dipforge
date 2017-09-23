'use strict';

/**
 * @ngdoc function
 * @name ide2App.controller:ProjectsCtrl
 * @description
 * # ProjectsCtrl
 * Controller of the ide2App
 */
angular.module('ide2App')
  .controller('ProjectsCtrl', function ($rootScope, $scope, $interval,ProjectService) {
    var vm = this;
    
    console.log("Hello world")
    function listProjects() {
        ProjectService.listProjects().then(function(response) {
            vm.projectLists = response.data;
        });
    }
    
    $rootScope.$on('addProject', function(event, data){
        vm.projectLists.push({name:data.name,description:data.description})
    });
    
    $rootScope.$on('removeProject', function(event, data){
        for (var index in vm.projectLists) {
            var projectEntry = vm.projectLists[index]
            if (projectEntry.name == data.name) {
                vm.projectLists.splice(index,1)
                break;
            }
        }
    });
    
    listProjects();
    
  });
