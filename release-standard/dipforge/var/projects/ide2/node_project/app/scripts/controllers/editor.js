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
    vm.imageFiles = ["png","jpeg","jpg","gif","tiff","bmp"]
    
    
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
            //$("#tab-" + vm.selectTabId).removeClass("active");
            //$("#" + vm.selectTabId).removeClass("active");
            // loop through the windows
            for (let index in vm.editorFiles) {
                let editorFile = vm.editorFiles[index];
                if (editorFile.id === vm.selectTabId) {
                    let nextPos = (parseInt(index) + 1);
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
            //$("#tab-" + vm.selectTabId).addClass("active");
            //$("#" + vm.selectTabId).addClass("active");
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
            //$("#tab-" + vm.selectTabId).removeClass("active");
            //$("#" + vm.selectTabId).removeClass("active");
            // loop through the windows
            for (let index in vm.editorFiles) {
                let editorFile = vm.editorFiles[index];
                if (editorFile.id === vm.selectTabId) {
                    let nextPos = (parseInt(index) - 1);
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
            //$("#tab-" + vm.selectTabId).addClass("active");
            //$("#" + vm.selectTabId).addClass("active");
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
    
    vm.openTool = function(tool) {
        
        let found = false;
        for (let index in vm.editorFiles) {
            let editorFile = vm.editorFiles[index];
            if (editorFile.id === tool) {
                found = true;
                break;
            }
        }
        
        
        // if not found add a new tab and clear the previous one
        if (!found) {
            
            // add a new tab
            vm.editorFiles.push({
                id:tool,
                type:"tool",
                project:null,
                treeNode:null,
                fileData:null,
                dirty: false
            })
            vm.selectTab(tool)
        } else {
            vm.selectTab(tool)
        }
    }
    
    vm.openFile = function(project,treeNode) {
        
        // generate a new id and check for a duplicate
        let id = (project + treeNode.fullPath).replace(/\//g, '');
        id = id.replace(/\./g,'')
        let found = false;
        for (let index in vm.editorFiles) {
            let editorFile = vm.editorFiles[index];
            if (editorFile.id === id) {
                found = true;
                break;
            }
        }
        
        // if not found add a new tab and clear the previous one
        if (!found) {
            
            // check if we are dealing with am image
            if (vm.isImage(treeNode.fullPath)) {
                // strip view of path
                let fileData = treeNode.fullPath.replace(/\/views/,"")
                
                let editorFile = {
                    id:id,
                    type:"image",
                    project:project,
                    treeNode:treeNode,
                    fileData:fileData,
                    mode: null,
                    dirty: false
                    }
                
                vm.editorFiles.push(editorFile)
                
                vm.selectTab(id)
                
            
            } else { 
                
                
                let editorFile = {
                        id:id,
                        type:"file",
                        project:project,
                        treeNode:treeNode,
                        mode: null,
                        dirty: false
                }
                
                vm.editorFiles.push(editorFile)
                
                vm.selectTab(id)
                
            }
        } else {
            vm.selectTab(id)
        }
    }
    
    
    // this function is called to see if the path contains an image
    // this is a simple check based on the suffix of the file.
    vm.isImage = function(path) {
        
        let suffix = path.substr(path.lastIndexOf('.') + 1) || path;
        console.log("The suffix of the file is %s",suffix);
        return vm.imageFiles.includes(suffix);
    }
    
    vm.closeFile = function(id) {
        for (let index in vm.editorFiles) {
            let editorFile = vm.editorFiles[index];
            if (editorFile.id === id) {
                console.log("Close the id : " + id);
                
                vm.editorFiles.splice(index, 1);
                
                break;
            }
        }
    }
    
    vm.closeTool = function(id) {
        for (let index in vm.editorFiles) {
            let editorFile = vm.editorFiles[index];
            if (editorFile.id === id) {
                console.log("Close the id [%o][%o]",id,index);
                vm.editorFiles.splice(index, 1);
                
                break;
            }
        }
        
    }
    
    vm.deleteFile = function(id) {
        let cloneFiles = vm.editorFiles.concat()
        for (let index in cloneFiles) {
            let editorFile = vm.editorFiles[index];
            if (editorFile.id === id) {
                FileService.deleteFile(editorFile.project,editorFile.treeNode.fullPath,"file").then(function(response){
                    vm.editorFiles.splice(index, 1);
                });
                break;
            }
        }
    }
    
    
    vm.selectTab = function(id) {
        console.log("The select tab %s",vm.selectTabId)
        $(".tab-pane").removeClass("active");
        $("[id^=tab-]").removeClass("active");
        vm.selectTabId = null;
        
        vm.selectTabId = id
        if (id != null) {
            $("#" + vm.selectTabId).addClass("active");
            $("#tab-" + vm.selectTabId).addClass("active");
            console.log("The selected the tab %s",vm.selectTabId)
        }
    }
    
    $rootScope.$on('openFile', function(event, data){
        vm.openFile(data.project,data.treeNode)
    });
    
    $rootScope.$on('openTool', function(event, data){
        vm.openTool(data.tool)
    });
    
    $rootScope.$on('toolLoaded', function(event, data) {
        if (vm.selectTabId != null) {
            $("#" + vm.selectTabId).addClass("active");
            $("#tab-" + vm.selectTabId).addClass("active");
            console.log("[toolLoaded]The selected the tab %s",vm.selectTabId)
        }
    });
    
    $rootScope.$on('toolClosed', function(event, data) {
        if (vm.selectTabId != null) {
            $("#" + vm.selectTabId).addClass("active");
            $("#tab-" + vm.selectTabId).addClass("active");
            console.log("[toolLoaded]The selected the tab %s",vm.selectTabId)
        }
    });
});
