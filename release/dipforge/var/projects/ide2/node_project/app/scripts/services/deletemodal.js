'use strict';

/**
 * @ngdoc service
 * @name ide2App.DeleteModal
 * @description
 * # DeleteModal
 * Service in the ide2App.
 */
angular.module('ide2App')
    .service('DeleteModal', function ($http) {
        this.listFolders = function(project,directoryCommaList) {
            return $http.get("../com/dipforge/ide/ListFolders.groovy?project=" + project + "&directoryCommaList=" + directoryCommaList);
        };
        
        
        this.deleteFolderOrFile = function(project,path,type) {
            var jsonData = JSON.stringify({project:project,path:path,type:type});
            return $http.post("../com/dipforge/ide/DeleteFile.groovy",jsonData);
        };
        
        
        this.deleteProject = function(jsonObject) {
            var jsonData = JSON.stringify(jsonObject);
            return $http.post("../com/dipforge/ide/DeleteProject.groovy",jsonData);
        };
    });
