'use strict';

/**
 * @ngdoc function
 * @name ide2App.controller:EditorCtrl
 * @description
 * # EditorCtrl
 * Controller of the ide2App
 */
angular.module('ide2App')
  .controller('EditorCtrl', function ($rootScope, $scope, $interval,FileService) {
    var vm = this;
    vm.editorFiles = []
    vm.selectTabId = null;
    
    vm.openFile = function(project,treeNode) {
        
        
        // generate a new id and check for a duplicate
        var id = (project + treeNode.fullPath).replace(/\//g, '');
        id = id.replace(/\./g,'')
        var found = false
        for (var index in vm.editorFiles) {
            var editorFile = vm.editorFiles[index];
            if (editorFile.id === id) {
                found = true;
                break;
            }
        }
        
        // if not found add a new tab and clear the previous one
        if (!found) {
            // this is a hack to reset the active tab using jquery
            if (vm.selectTabId != null) {
                $("#" + vm.selectTabId).removeClass("active");
                $("#tab-" + vm.selectTabId).removeClass("active");
                vm.selectTabId = null;
            }
            
            // add a new tab
            FileService.getFile(project,treeNode.fullPath).then(function(response) {
                vm.editorFiles.push({
                    id:id,
                    project:project,
                    treeNode:treeNode,
                    fileData:response.data,
                    dirty: true
                })
                //console.log(response.data.contents)
                
            });
        }
    }
    
    vm.closeFile = function(id) {
        for (var index in vm.editorFiles) {
            var editorFile = vm.editorFiles[index];
            if (editorFile.id === id) {
                if (editorFile.dirty) {
                    FileService.saveFile({content:editorFile.fileData.contents,project:editorFile.project,path:editorFile.treeNode.fullPath})
                    editorFile.dirty = false
                }
                vm.editorFiles.splice(index, 1);
                break;
            }
        }
    }
    
    $scope.aceLoaded = function(editor) {
        console.log("The ace load method is called")
        var editorFile = vm.editorFiles[vm.editorFiles.length - 1]
        editorFile.editor = editor
        console.log("The mode is : " + editorFile.fileData.fileExtension)
        var mode = editorFile.fileData.fileExtension
        if (editorFile.fileData.fileExtension === "js") {
            mode = "javascript"
        } else if (editorFile.fileData.fileExtension === "gsp") {
            mode = "jsp"
        }
        editor.getSession().setMode("ace/mode/" + mode)
        editor.getSession().setValue(editorFile.fileData.contents)
        editorFile.dirty = false
        editor.getSession().id = editorFile.id
        console.log("Set the id : " + editorFile.id)
        
        editor.getSession().on("change", function() {
            editorFile.dirty = true
            editorFile.fileData.contents = editor.getSession().getValue()
            console.log("The ace editor changed values : " + editorFile.id)
            
        });
    }
    
    $rootScope.$on('openFile', function(event, data){
        vm.openFile(data.project,data.treeNode)
    });
    
    // loop through the editor files
    $interval(function() {
        for (var index in vm.editorFiles) {
            var editorFile = vm.editorFiles[index];
            if (editorFile.dirty) {
                FileService.saveFile({content:editorFile.fileData.contents,project:editorFile.project,path:editorFile.treeNode.fullPath})
                editorFile.dirty = false
            }
        }
        
    }, 1000 * 10);
    
});
