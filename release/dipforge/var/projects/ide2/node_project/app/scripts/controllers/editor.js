'use strict';

/**
 * @ngdoc function
 * @name ide2App.controller:EditorCtrl
 * @description
 * # EditorCtrl
 * Controller of the ide2App
 */
angular.module('ide2App')
  .controller('EditorCtrl', function ($rootScope, $scope, $interval,FileService,hotkeys) {
    var vm = this;
    vm.editorFiles = []
    vm.selectTabId = null;
    
    hotkeys.add({
        combo: 'ctrl+down',
        description: 'Tab between windows',
        callback: function() {
            if (vm.editorFiles.length == 0) {
                return
            }
            if (!vm.selectTabId) {
                vm.selectTabId = vm.editorFiles[vm.editorFiles.length -1].id
            }
            $("#tab-" + vm.selectTabId).removeClass("active");
            $("#" + vm.selectTabId).removeClass("active");
            // loop through the windows
            for (var index in vm.editorFiles) {
                var editorFile = vm.editorFiles[index];
                if (editorFile.id === vm.selectTabId) {
                    var nextPos = (parseInt(index) + 1);
                    console.log("The pos is : " + nextPos)
                    if (nextPos < vm.editorFiles.length) {
                        console.log("Setting to the next tab index")
                        vm.selectTabId = vm.editorFiles[nextPos].id
                    } else {
                        console.log("The select tab id is getting reset")
                        vm.selectTabId = vm.editorFiles[0].id
                    }
                    break;
                }
            }
            $("#tab-" + vm.selectTabId).addClass("active");
            $("#" + vm.selectTabId).addClass("active");
        }
    });
    hotkeys.add({
        combo: 'ctrl+up',
        description: 'Tab between windows',
        callback: function() {
            if (vm.editorFiles.length == 0) {
                return
            }
            if (!vm.selectTabId) {
                vm.selectTabId = vm.editorFiles[vm.editorFiles.length -1].id
            }
            $("#tab-" + vm.selectTabId).removeClass("active");
            $("#" + vm.selectTabId).removeClass("active");
            // loop through the windows
            for (var index in vm.editorFiles) {
                var editorFile = vm.editorFiles[index];
                if (editorFile.id === vm.selectTabId) {
                    var nextPos = (parseInt(index) - 1);
                    console.log("The pos is : " + nextPos)
                    if (nextPos == -1) {
                        console.log("The select tab id is getting reset")
                        vm.selectTabId = vm.editorFiles[vm.editorFiles.length -1].id
                    } else {
                        
                        console.log("Setting to the next tab index")
                        vm.selectTabId = vm.editorFiles[nextPos].id
                    }
                    break;
                }
            }
            $("#tab-" + vm.selectTabId).addClass("active");
            $("#" + vm.selectTabId).addClass("active");
        }
    });
    hotkeys.add({
        combo: 'ctrl+s',
        description: 'Called to save a file',
        callback: function(event) {
            if (vm.editorFiles.length == 0) {
                return
            }
            if (!vm.selectTabId) {
                vm.selectTabId = vm.editorFiles[vm.editorFiles.length -1].id
            }
            vm.saveFile(vm.selectTabId)
            event.preventDefault();
        }
    });
    
    
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
                console.log("Close the id : " + id);
                if (editorFile.dirty) {
                    FileService.saveFile({content:editorFile.fileData.contents,project:editorFile.project,path:editorFile.treeNode.fullPath})
                    editorFile.dirty = false
                }
                if (vm.selectTabId) {
                    $("#" + vm.selectTabId).removeClass("active");
                    $("#tab-" + vm.selectTabId).removeClass("active");
                }
                vm.editorFiles.splice(index, 1);
                break;
            }
        }
    }
    
    vm.saveFile = function(id) {
        for (var index in vm.editorFiles) {
            var editorFile = vm.editorFiles[index];
            if (editorFile.id === id) {
                FileService.saveFile({content:editorFile.fileData.contents,project:editorFile.project,path:editorFile.treeNode.fullPath})
                editorFile.dirty = false
                break;
            }
        }
    }
    
    
    vm.executeFile = function(id) {
        console.log("The execute method")
        for (var index in vm.editorFiles) {
            var editorFile = vm.editorFiles[index];
            if (editorFile.id === id) {
                FileService.saveFile({content:editorFile.fileData.contents,project:editorFile.project,path:editorFile.treeNode.fullPath}).then(function(response) {
                    FileService.executeFile(editorFile.project,editorFile.treeNode.fullPath).then(function(response) {
                        $rootScope.$broadcast("executeOutput",response.data);
                    });
                });
                break;
            }
        }
    }
    
    vm.refreshFile = function(id) {
        for (var index in vm.editorFiles) {
            var editorFile = vm.editorFiles[index];
            if (editorFile.id === id) {
                // add a new tab
                FileService.getFile(editorFile.project,editorFile.treeNode.fullPath).then(function(response) {
                    editorFile.fileData.contents = response.data.contents
                    editorFile.dirty = false;
                });
                break;
            }
        }
    }
    
    vm.deleteFile = function(id) {
        for (var index in vm.editorFiles) {
            var editorFile = vm.editorFiles[index];
            if (editorFile.id === id) {
                FileService.deleteFile(editorFile.project,editorFile.treeNode.fullPath,"file").then(function(response){
                    vm.editorFiles.splice(index, 1);
                });
                break;
            }
        }
    }
    
    
    $scope.aceLoaded = function(editor) {
        console.log("The ace load method is called [" + (vm.editorFiles.length - 1) + "]")
        var editorFile = vm.editorFiles[vm.editorFiles.length - 1]
        //editorFile.editor = editor
        console.log("The mode is : " + editorFile.fileData.fileExtension)
        var mode = editorFile.fileData.fileExtension
        if (editorFile.fileData.fileExtension === "js") {
            mode = "javascript"
        } else if (editorFile.fileData.fileExtension === "gsp") {
            mode = "jsp"
        }
        editor.getSession().setMode("ace/mode/" + mode)
        editorFile.dirty = false
        
        editor.getSession().on("change", function() {
            editorFile.dirty = true
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
