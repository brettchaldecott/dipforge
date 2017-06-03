'use strict';

/**
 * @ngdoc service
 * @name ide2App.FileModal
 * @description
 * # FileModal
 * Service in the ide2App.
 */
angular.module('ide2App')
    .service('FileModal', function ($http) {
        this.listFolders = function(project,directoryCommaList) {
            return $http.get("../com/dipforge/ide/ListFolders.groovy?project=" + project + "&directoryCommaList=" + directoryCommaList);
        };
        
        
        this.createFile = function(project,path,type,context) {
            var jsonData = JSON.stringify({project:project,path:path,type:type,context:context});
            return $http.post("../com/dipforge/ide/CreateFile.groovy",jsonData);
        };
    });
