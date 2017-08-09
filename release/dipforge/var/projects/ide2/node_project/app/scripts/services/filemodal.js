'use strict';

/**
 * @ngdoc service
 * @name ide2App.FileModal
 * @description
 * # FileModal
 * Service in the ide2App.
 */
angular.module('ide2App')
    .service('FileModal', function ($http,Upload) {
        this.listFolders = function(project,directoryCommaList) {
            return $http.get("../com/dipforge/ide/ListFolders.groovy?project=" + project + "&directoryCommaList=" + directoryCommaList);
        };
        
        
        this.createFile = function(project,path,type,context) {
            var jsonData = JSON.stringify({project:project,path:path,type:type,context:context});
            return $http.post("../com/dipforge/ide/CreateFile.groovy",jsonData);
        };
        
        
        this.uploadFile = function(project,path,file) {
            return Upload.upload({
                url:"../com/dipforge/ide/UploadFile.groovy",
                data: {project: project, path: path, file:file}
            });
        };
    });
