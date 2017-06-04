'use strict';

/**
 * @ngdoc function
 * @name ide2App.controller:FilesCtrl
 * @description
 * # FilesCtrl
 * Controller of the ide2App
 */
angular.module('ide2App')
  .controller('FilesCtrl', function ($rootScope, $scope, $interval,ProjectService,FileService) {
    var vm = this;
    vm.project_status = "";
    var apple_selected, tree, treedata_avm, treedata_geography;
    
    
    $scope.my_data = [];
    
    
    
    function listProjectBase(project,base) {
        FileService.listFiles(project,"/").then(function(response) {
            for (var index in response.data) {
                var folder = response.data[index]
                base.push({
                    fullPath: folder.path,
                    noLeaf: !folder.leafNode,
                    leafNode: folder.leafNode,
                    label: folder.label
                });
            }
        });
    }
    
    function listFolder(branch) {
        FileService.listFiles(vm.project,branch.fullPath).then(function(response) {
            branch.children = []
            for (var index in response.data) {
                var folder = response.data[index]
                branch.children.push({
                    fullPath: folder.path,
                    noLeaf: !folder.leafNode,
                    leafNode: folder.leafNode,
                    label: folder.label
                });
            }
        });
        
    }
    

    vm.init = function(project)
    {
        vm.project = project;
        listProjectBase(project,$scope.my_data)
    };
    
    vm.deleteFileFolderOrProject = function() {
        $rootScope.$broadcast('deleteFileFolderOrProject', {projectName: vm.project});
    }
    
    vm.refresh = function() {
        $scope.my_data = [];
        FileService.listFiles(vm.project,"/").then(function(response) {
            for (var index in response.data) {
                var folder = response.data[index]
                $scope.my_data.push({
                    fullPath: folder.path,
                    noLeaf: !folder.leafNode,
                    leafNode: folder.leafNode,
                    label: folder.label
                });
            }
        });
    }
    
    vm.launchFileModal = function() {
        console.log("The project : " + vm.project)
        $rootScope.$broadcast('launchFileModal', {project: vm.project});
    }
    
    vm.publishProject = function() {
        console.log("The project : " + vm.project)
        ProjectService.publishProject(vm.project).then(function(response) {
            vm.project_status = "published";
        });
    }
    
    
    $scope.my_tree_handler = function(branch) {
        var _ref;
        if (!branch.leafNode) {
            listFolder(branch)
        } else {
            $rootScope.$broadcast('openFile', {project: vm.project,treeNode:branch});
        }
        $scope.output = "You selected: " + branch.label;
        
        if ((_ref = branch.data) != null ? _ref.description : void 0) {
            return $scope.output += '(' + branch.data.description + ')';
        }
    };
    
        
  });
