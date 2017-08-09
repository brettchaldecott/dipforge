'use strict';

/**
 * @ngdoc function
 * @name ide2App.controller:FileModalCtrl
 * @description
 * # FileModalCtrl
 * Controller of the ide2App
 */
angular.module('ide2App')
    .controller('FileModalCtrl', function ($rootScope, $scope, $interval, FileModal, FileService) {
        var vm = this;
        vm.folders = []
        vm.folderFormFolders = [];
        vm.folderFormFolder = null;
        
        // controllers
        vm.controllerFormFolders = []
        
        // service form folders
        vm.serviceFormFolders = [];
        
        // package form
        vm.packageFormFolders = [];
        
        // flow forms
        vm.flowFormFolders = [];
        
        // views
        vm.viewsFormFolders = [];
        
        // files
        vm.filesFormFolders = [];
        
        // files
        vm.uploadFilesFormFolders = [];
        
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
                $('#createFileOrFolder').modal('show');
            });
        }
        
        
        vm.createFileOrFolder = function() {
            console.log("The file or folder")
            
            try {
                var upload = false;
                
                var path = null;
                var type = null;
                var context = null;
                if ($("#_folder").hasClass("active")) {
                    if(!$scope.folderForm.$valid) {
                        return;
                    }
                    path = vm.folderFormFolder + "/" + vm.folderPath
                    type = "folder"
                    context = vm.folderFormFolder
                    
                } else if ($("#_controller").hasClass("active")) {
                    if(!$scope.controllerForm.$valid) {
                        return;
                    }
                    path = "/controls/" + vm.controllerPath.replace(/\./g,"/") + ".groovy"
                    type = "groovy"
                    context = "/controls"
                    
                } else if ($("#_service").hasClass("active")) {
                    if(!$scope.serviceForm.$valid) {
                        return;
                    }
                    path = "/services/" + vm.servicePath.replace(/\./g,"/") + ".groovy"
                    type = vm.serviceType
                    context = "/services"
                    
                    
                } else if ($("#_package").hasClass("active")) {
                    if(!$scope.packageForm.$valid) {
                        return;
                    }
                    path = "/packages/" + vm.packagePath.replace(/\./g,"/") + ".groovy"
                    type = "groovy"
                    context = "/packages"
                    
                } else if ($("#_workflow").hasClass("active")) {
                    if(!$scope.workflowForm.$valid) {
                        return;
                    }
                    path = "/flows/" + vm.flowPath + ".lwf"
                    type = "lwf"
                    context = "/flows"
                    
                } else if ($("#_view").hasClass("active")) {
                    if(!$scope.viewForm.$valid) {
                        return;
                    }
                    path = "/views/" + vm.viewPath + "." + vm.viewType
                    type = vm.viewType
                    context = "/views"
                } else if ($("#_file").hasClass("active")) {
                    if(!$scope.fileForm.$valid) {
                        return;
                    }
                    var type = vm.filePath.substring(vm.filePath.lastIndexOf(".")+1)
                    path = vm.filesFormFolder + vm.filePath
                    type = type
                    context = vm.filesFormFolder
                } else if ($("#_upload").hasClass("active")) {
                    console.log("Upload has been called");
                    if(!$scope.uploadForm.$valid) {
                        return;
                    }
                    path = vm.uploadFilesFormFolder + vm.uploadFilePath + "/" + vm.uploadFileName
                    upload = true;
                }
                
                console.log("Attempt to either upload a file or create a file.");
                
                if (!upload) {
                    console.log("Create a file");
                    FileModal.createFile(vm.project,path,type,context).then(function(response){
                        if (response.type != "folder") {
                            
                            var label = stripLabel(path)
                            
                            var treeNode = {
                                "fullPath": path, 
                                "project": vm.project,
                                "path": path,
                                "leafNode": true,
                                "iconCls": "na",
                                "fileExtension":response.type,
                                "label": label
                            }
                            $rootScope.$broadcast('openFile', {project: vm.project,treeNode:treeNode});
                        }
                        $('#createFileOrFolder').modal('hide');
                    })
                } else {
                    console.log("File upload has been called");
                    var type = path.substring(path.lastIndexOf(".")+1)
                    var label = stripLabel(path);
                    
                    FileModal.uploadFile(vm.project,path,vm.fileToUpload).then(function(response){
                        console.log("The upload has has been called.")
                        
                        var treeNode = {
                            "fullPath": path, 
                            "project": vm.project,
                            "path": path,
                            "leafNode": true,
                            "iconCls": "na",
                            "fileExtension":type,
                            "label": label
                        }
                        $rootScope.$broadcast('openFile', {project: vm.project,treeNode:treeNode});
                        
                        $('#createFileOrFolder').modal('hide');
                    })
                }
            } catch (exception) {
                console.log("Failed to create a file %o",exception)
            }
        }
        
        
        
        
        /**
         * This method changes the folder
         */
        vm.folderFormChangeFolder = function() {
            vm.folderFormFolders = [];
            vm.folderPath = ''
            FileModal.listFolders(vm.project,vm.folderFormFolder).then(function(response){
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
        
        /**
         * This method changes the folder
         */
        vm.loadControllerForm = function() {
            vm.controllerFormFolders = [];
            vm.controllerPath =  ''
            FileModal.listFolders(vm.project,"/controls").then(function(response){
                for (var index in response.data) {
                    var folder = response.data[index]
                    if (folder.leafNode) {
                        continue;
                    }
                    vm.controllerFormFolders.push(
                        stripPath("/controls/",folder.path).replace(/\//g,".")
                    );
                }
                
            });
        }
        
        /**
         * This method changes the folder
         */
        vm.loadServiceForm = function() {
            vm.serviceFormFolders = [];
            vm.servicePath =  ''
            FileModal.listFolders(vm.project,"/services").then(function(response){
                for (var index in response.data) {
                    var folder = response.data[index]
                    if (folder.leafNode) {
                        continue;
                    }
                    vm.serviceFormFolders.push(
                        stripPath("/services/",folder.path).replace(/\//g,".")
                    );
                }
                
            });
        }
        
        /**
         * This method changes the folder
         */
        vm.loadPackageForm = function() {
            vm.packageFormFolders = [];
            vm.packagePath =  ''
            FileModal.listFolders(vm.project,"/packages").then(function(response){
                for (var index in response.data) {
                    var folder = response.data[index]
                    if (folder.leafNode) {
                        continue;
                    }
                    vm.packageFormFolders.push(
                        stripPath("/packages/",folder.path).replace(/\//g,".")
                    );
                }
                
            });
        }
        
        /**
         * This method changes the folder
         */
        vm.loadFlowForm = function() {
            vm.flowFormFolders = [];
            vm.flowPath =  ''
            FileModal.listFolders(vm.project,"/flows").then(function(response){
                for (var index in response.data) {
                    var folder = response.data[index]
                    if (folder.leafNode) {
                        continue;
                    }
                    
                    vm.flowFormFolders.push(
                        stripPath("/flows",folder.path)
                    );
                }
                
            });
        }
        
        
        /**
         * This method changes the folder
         */
        vm.loadViewForm = function() {
            vm.viewsFormFolders = [];
            vm.viewPath =  ''
            FileModal.listFolders(vm.project,"/views").then(function(response){
                for (var index in response.data) {
                    var folder = response.data[index]
                    if (folder.leafNode) {
                        continue;
                    }
                    
                    vm.viewsFormFolders.push(
                        stripPath("/views",folder.path)
                    );
                }
                
            });
        }
        
        
        /**
         * This method changes the folder
         */
        vm.filesFormChangeFolder = function() {
            console.log("This has been calleds")
            vm.filesFormFolders = [];
            vm.filesPath = ''
            FileModal.listFolders(vm.project,vm.filesFormFolder).then(function(response){
                for (var index in response.data) {
                    var folder = response.data[index]
                    if (folder.leafNode) {
                        continue;
                    }
                    vm.filesFormFolders.push(
                        stripPath(vm.filesFormFolder,folder.path)
                    );
                }
                
            });
        }
        
        
        /**
         * This method changes the folder
         */
        vm.uploadFilesFormChangeFolder = function() {
            console.log("This has been calleds")
            vm.uploadFilesFormFolders = [];
            vm.uploadFilePath = ''
            FileModal.listFolders(vm.project,vm.uploadFilesFormFolder).then(function(response){
                for (var index in response.data) {
                    var folder = response.data[index]
                    if (folder.leafNode) {
                        continue;
                    }
                    vm.uploadFilesFormFolders.push(
                        stripPath(vm.uploadFilesFormFolder,folder.path)
                    );
                }
                
            });
        }
        
        
        
        /**
         * handle the change file
         * 
         */
        vm.handleChangeFile = function(file) {
            if (file) {
                vm.uploadFileName = file.name;
            }
        }
        
        
        /**
         * launch the file modal and load the data
         */
        $rootScope.$on('launchFileModal', function(event, data){
            console.log("The project : " + data.project)
            listProjectBase(data.project)
            vm.loadControllerForm()
            vm.loadServiceForm()
            vm.loadPackageForm()
            vm.loadFlowForm()
            vm.loadViewForm()
        });
        
        
        /**
         * This method is called to strip a string
         */
        var stripPath = function(prefix,path) {
            return path.substring(prefix.length)
        }
        
        
        var stripLabel = function(path) {
            console.log("The path : " + path)
            return path.substring(path.lastIndexOf("/") + 1)
        }
        
    });
