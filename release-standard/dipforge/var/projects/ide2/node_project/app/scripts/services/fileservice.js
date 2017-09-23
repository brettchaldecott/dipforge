'use strict';

/**
 * @ngdoc service
 * @name ide2App.FileService
 * @description
 * # FileService
 * Service in the ide2App.
 */
angular.module('ide2App')
  .service('FileService', function ($http) {
    console.log("This is stuff")
    
    this.listFiles = function(project,path) {
        return $http.get("../com/dipforge/ide/ListFiles.groovy?project=" + project + "&path=" + path);
    };
    
    this.getFile = function(project,path) {
        return $http.get("../com/dipforge/ide/GetFile.groovy?project=" + project + "&path=" + path);
    };
    
    this.saveFile = function(jsonObject) {
        var jsonData = JSON.stringify(jsonObject);
        return $http.post("../com/dipforge/ide/SaveFile.groovy",jsonData);
    };
    
    this.deleteFile = function(project,path,type) {
        var jsonData = JSON.stringify({project:project,path:path,type:type});
        return $http.post("../com/dipforge/ide/DeleteFile.groovy",jsonData);
    };
    
    this.deleteFile = function(project,path,type) {
        var jsonData = JSON.stringify({project:project,path:path,type:type});
        return $http.post("../com/dipforge/ide/DeleteFile.groovy",jsonData);
    };
    
    this.executeFile = function(project,path) {
        var jsonData = JSON.stringify({project:project,path:path});
        return $http.post("../com/dipforge/ide/ExecuteFile.groovy",jsonData);
    };
    
    
  });
