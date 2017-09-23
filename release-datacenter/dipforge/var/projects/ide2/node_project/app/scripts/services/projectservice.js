'use strict';

/**
 * @ngdoc service
 * @name ide2App.ProjectService
 * @description
 * # ProjectService
 * Service in the ide2App.
 */
angular.module('ide2App')
    .service('ProjectService', function ($http) {
        this.listProjects = function() {
            return $http.get("../com/dipforge/ide/ListProjects.groovy");
        };
      
    
        this.publishProject = function(project) {
            return $http.get("../com/dipforge/ide/PublishProject.groovy?project=" + project);
        };  
    });
