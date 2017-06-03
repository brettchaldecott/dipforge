'use strict';

/**
 * @ngdoc function
 * @name ide2App.controller:ProjectModalCtrl
 * @description
 * # ProjectModalCtrl
 * Controller of the ide2App
 */
angular.module('ide2App')
    .controller('ProjectModalCtrl', function ($rootScope, $scope, $interval,ProjectModal) {
        var vm = this;
        
        vm.projectName = "";
        vm.projectDescription = "";
        
        // the method to create a project
        vm.createProject = function () {
            console.log("Create a project")
            ProjectModal.createProject({name:vm.projectName,description:vm.projectDescription}).then(function(response) {
                $rootScope.$broadcast('addProject', {name:vm.projectName,description:vm.projectDescription});
                vm.projectName=""
                vm.projectDescription=""
                $('#createProject').modal('hide');
            });
            
        }
        
        
});
