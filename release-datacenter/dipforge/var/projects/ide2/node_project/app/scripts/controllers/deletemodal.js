'use strict';

/**
 * @ngdoc function
 * @name ide2App.controller:DeleteModalCtrl
 * @description
 * # DeleteModalCtrl
 * Controller of the ide2App
 */
angular.module('ide2App')
    .controller('DeleteModalCtrl', function ($rootScope, $scope, $interval, DeleteModal, FileService) {
        
        var vm = this;
        vm.project = null;
        vm.folders = [];
        vm.folderFormFolders = [];
        vm.folderFormFolder = null;
        
        // files
        vm.filesFormFolders = [];
        
        function listProjectBase(project) {
            vm.project = project;
            vm.folders = []
            FileService.listFiles(project,"/").then(function(response) {
                for (var index in response.data) {
                    var folder = response.data[index]
                    if (folder.leafNode) {
                        continue;
                    }
                    vm.folders.push({
                        path: folder.path,
                        label: folder.label
                    });
                }
            });
        }
        
        vm.deleteFileFolderOrProject = function() {
            var path = null;
            var type = null;
            if ($("#_delete_project").hasClass("active")) {
                DeleteModal.deleteProject({name:vm.project}).then(function(response) {
                    $rootScope.$broadcast('removeProject', {name:vm.project});
                    vm.projectName=""
                    vm.projectDescription=""
                    $('#deleteFileFolderOrProject').modal('hide');
                });
                return
            } else if ($("#_delete_folder").hasClass("active")) {
                if(!$scope.deleteFolderForm.$valid) {
                    return;
                }
                path = vm.folderFormFolder + vm.deleteFolderPath
                type = "folder"
                console.log("The path is : "+ path)
            }
            
            // this is called to delete a folder
            DeleteModal.deleteFolderOrFile(vm.project,path,type).then(function(response){
                $('#deleteFileFolderOrProject').modal('hide');
            });
        }
        
        /**
         * This method changes the folder
         */
        vm.folderFormChangeFolder = function() {
            vm.folderFormFolders = [];
            vm.folderPath = ''
            DeleteModal.listFolders(vm.project,vm.folderFormFolder).then(function(response){
                for (var index in response.data) {
                    var folder = response.data[index]
                    if (folder.leafNode) {
                        continue;
                    }
                    vm.folderFormFolders.push(
                        stripPath(vm.folderFormFolder,folder.path)
                    );
                }
                
            });
        }
        
        
        $rootScope.$on('deleteFileFolderOrProject',function(event, data){
            vm.project = data.projectName
            listProjectBase(vm.project);
            $('#deleteFileFolderOrProject').modal('show');
        });
        
        /**
         * This method is called to strip a string
         */
        var stripPath = function(prefix,path) {
            return path.substring(prefix.length)
        }
        
    });
