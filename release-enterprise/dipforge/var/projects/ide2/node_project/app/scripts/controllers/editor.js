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
                
                // add a new tab
                FileService.getFile(project,treeNode.fullPath).then(function(response) {
                    
                    let mode = response.data.fileExtension
                    if (response.data.fileExtension === "js") {
                        mode = "javascript"
                    } else if (response.data.fileExtension === "gsp") {
                        mode = "jsp"
                    } else if (response.data.fileExtension === "py") {
                        mode = "python"
                    }
                    console.log("The mode is extension is [%o] mode is [%o]",response.data.fileExtension,mode)
                    
                    let editorFile = {
                        id:id,
                        type:"file",
                        project:project,
                        treeNode:treeNode,
                        fileData:response.data,
                        mode: mode,
                        dirty: false,
                        editorChange:    function(editor) {
                                editor.$blockScrolling = 1
                                
                                editorFile.dirty = false
                                
                                editor.getSession().on("change", function() {
                                    
                                    editorFile.dirty = true
                                });
                                
                                // select the 
                                if (vm.selectTabId != null) {
                                    $("#" + vm.selectTabId).addClass("active");
                                    $("#tab-" + vm.selectTabId).addClass("active");
                                    console.log("The selected the tab %s",vm.selectTabId)
                                }
                            }
                        }
                    
                    vm.editorFiles.push(editorFile)
                    
                    vm.selectTab(id)
                });
                
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
                if (editorFile.dirty) {
                    FileService.saveFile({content:editorFile.fileData.contents,project:editorFile.project,path:editorFile.treeNode.fullPath})
                    editorFile.dirty = false
                }
                
                vm.editorFiles.splice(index, 1);
                
                
                
                break;
            }
        }
    }
    
    vm.closeTool = function(id) {
        for (let index in vm.editorFiles) {
            let editorFile = vm.editorFiles[index];
            if (editorFile.id === id) {
                console.log("Close the id : " + id);
                vm.editorFiles.splice(index, 1);
                
                break;
            }
        }
        
    }
    
    vm.saveFile = function(id) {
        let cloneFiles = vm.editorFiles.concat()
        for (let index in cloneFiles) {
            let editorFile = cloneFiles[index];
            if (editorFile.id === id) {
                FileService.saveFile({content:editorFile.fileData.contents,fileHash:editorFile.fileData.fileHash,project:editorFile.project,path:editorFile.treeNode.fullPath}).then(function(response) {
                    console.log("The content has been saved : " + response.data.status)
                    if (response.data.status == "updated") {
                        editorFile.fileData.fileHash = response.data.fileHash;
                        
                    } else {
                        console.log("There was a conflish on the file original hash [" + editorFile.fileData.fileHash + "] new hash [" + response.data.fileHash + "]")
                    }
                });
                editorFile.dirty = false
                break;
            }
        }
    }
    
    vm.saveEditorFile = function(editorFile) {
        FileService.saveFile({content:editorFile.fileData.contents,fileHash:editorFile.fileData.fileHash,project:editorFile.project,path:editorFile.treeNode.fullPath}).then(function(response) {
            console.log("The content has been saved : " + response.data.status)
            if (response.data.status == "updated") {
                editorFile.fileData.fileHash = response.data.fileHash;
                
            } else {
                console.log("There was a conflish on the file original hash [" + editorFile.fileData.fileHash + "] new hash [" + response.data.fileHash + "]")
            }
        });
        editorFile.dirty = false
    }
    
    
    vm.executeFile = function(id) {
        console.log("The execute method")
        let cloneFiles = vm.editorFiles.concat()
        console.log("The the files are [%o]",cloneFiles)
        for (let index in cloneFiles) {
            let editorFile = cloneFiles[index];
            if (editorFile.id === id) {
                FileService.saveFile({content:editorFile.fileData.contents,fileHash:editorFile.fileData.fileHash,project:editorFile.project,path:editorFile.treeNode.fullPath}).then(function(response) {
                    if (response.data.status == "updated") {
                        console.log("Attempt to execute the file")
                        editorFile.fileData.fileHash = response.data.fileHash;
                        FileService.executeFile(editorFile.project,editorFile.treeNode.fullPath).then(function(saveResponse) {
                            //console.log("The current hash [" + editorFile.fileData.fileHash + "]")
                            $rootScope.$broadcast("executeOutput",saveResponse.data);
                        });
                    } else {
                        console.log("There was a conflish on the file original hash [" + editorFile.fileData.fileHash + "] new hash [" + response.data.fileHash + "]")
                    }
                });
                break;
            }
        }
    }
    
    vm.refreshFile = function(id) {
        let cloneFiles = vm.editorFiles.concat()
        for (let index in cloneFiles) {
            let editorFile = cloneFiles[index];
            if (editorFile.id === id) {
                // add a new tab
                FileService.getFile(editorFile.project,editorFile.treeNode.fullPath).then(function(response) {
                    editorFile.fileData.contents = response.data.contents
                    editorFile.fileData.fileHash = response.data.fileHash
                    editorFile.dirty = false;
                });
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
    
    
    // the ace load method
    $scope.aceLoaded = function(editor) {
        editor.$blockScrolling = 1
        console.log("The ace load method is called [" + (vm.editorFiles.length - 1) + "]")
        let editorFile = vm.editorFiles[vm.editorFiles.length - 1]
        //editorFile.editor = editor
        console.log("The mode is : " + editorFile.fileData.fileExtension)
        let mode = editorFile.fileData.fileExtension
        if (editorFile.fileData.fileExtension === "js") {
            mode = "javascript"
        } else if (editorFile.fileData.fileExtension === "gsp") {
            mode = "jsp"
        } else if (editorFile.fileData.fileExtension === "py") {
            mode = "python"
        }
        console.log("The mode is extension is [%o] mode is [%o]",editorFile.fileData.fileExtension,mode)
        
        
        editor.getSession().setMode("ace/mode/" + mode)
        
        editorFile.dirty = false
        editorFile.mode = mode;
        editorFile.editor = editor;
        
        editor.getSession().on("change", function() {
            
            console.log("The editor session changes  %s",editorFile.mode)
            editorFile.dirty = true
            editorFile.editor.getSession().setMode("ace/mode/" + editorFile.mode)
            console.log("Set the editor mode  %s",editorFile.mode)
        });
        
        // select the 
        if (vm.selectTabId != null) {
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
    
    // loop through the editor files
    $interval(function() {
        let cloneFiles = vm.editorFiles.concat();
        for (let index in cloneFiles) {
            let editorFile = cloneFiles[index];
            if (editorFile.type == "file" && editorFile.dirty === true) {
                vm.saveEditorFile(editorFile);
                //FileService.saveFile({content:editorFile.fileData.contents,project:editorFile.project,path:editorFile.treeNode.fullPath})
                //editorFile.dirty = false
            }
        }
        
    }, 1000 * 10);
    
});
