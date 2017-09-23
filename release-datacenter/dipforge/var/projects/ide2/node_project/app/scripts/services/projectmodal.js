'use strict';

/**
 * @ngdoc service
 * @name ide2App.ProjectModal
 * @description
 * # ProjectModal
 * Service in the ide2App.
 */
angular.module('ide2App')
    .service('ProjectModal', function ($http) {
        this.createProject = function(jsonObject) {
            var jsonData = JSON.stringify(jsonObject);
            return $http.post("../com/dipforge/ide/CreateProject.groovy",jsonData);
        };
        
        this.deleteProject = function(jsonObject) {
            var jsonData = JSON.stringify(jsonObject);
            return $http.post("../com/dipforge/ide/DeleteProject.groovy",jsonData);
        };
    });
