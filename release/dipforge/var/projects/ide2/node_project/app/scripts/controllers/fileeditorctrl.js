/*
 * ide2: Description
 * Copyright (C) Tue Dec 19 03:47:45 UTC 2017 owner
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * fileeditorctrl.js
 * @author admin
 */



angular.module('ide2App')
  .controller('FileEditorCtrl', function ($rootScope, $scope, $interval,FileService,hotkeys) {
      
    let vm = this;
    vm.mode = "";
    vm.mode = "Unknown";
    vm.dirty = false;
    vm.contents = ""
    vm.status = "saved"
    vm.fileLoaded = false
    vm.contentsLoaded = false
    
    $scope.init = function(editorFile) {
        vm.editorFile = editorFile
        
        FileService.getFile(vm.editorFile.project,vm.editorFile.treeNode.fullPath).then(function(response) {
                    
            let mode = response.data.fileExtension
            if (response.data.fileExtension === "js") {
                mode = "javascript"
            } else if (response.data.fileExtension === "gsp") {
                mode = "jsp"
            } else if (response.data.fileExtension === "py") {
                mode = "python"
            }
            console.log("The mode is extension is [%o] mode is [%o]",response.data.fileExtension,mode)
            
            vm.mode = mode;
            vm.contents = response.data.contents;
            vm.fileData = response.data;
            vm.fileLoaded = true;
            
            //$scope.editorHTML = "Hello world"
            
        });
        
        
    }
      
    vm.editorLoad = function(editor) {
        vm.editor = editor;
        vm.dirty = false;
        vm.editor.$blockScrolling = 1
        
        
        editor.getSession().on("change", function(event) {
            console.log("The event is [%o]",event)
            if (!vm.contentsLoaded && event.action == "remove") {
               // ignore 
            } else if (!vm.contentsLoaded && event.action == "insert") {
                vm.dirty = false;
                vm.contentsLoaded = true;
            } else {
                vm.dirty = true;
                vm.status = "unsaved"
            }
        });
        
        $rootScope.$broadcast('toolLoaded', {tool: 'FileEditor',id: vm.editorFile.id});
    }
    
    vm.saveFile = function() {
        FileService.saveFile({content:vm.editor.getSession().getValue(),fileHash:vm.fileData.fileHash,project:vm.editorFile.project,path:vm.editorFile.treeNode.fullPath}).then(function(response) {
            console.log("The content has been saved : " + response.data.status)
            if (response.data.status == "updated") {
                vm.fileData.fileHash = response.data.fileHash;
                vm.status = "saved"
            } else {
                console.log("There was a conflish on the file original hash [" + vm.fileData.fileHash + "] new hash [" + response.data.fileHash + "]")
                vm.status = "syncerror"
            }
            vm.dirty = false;
        });
    }
    
    vm.executeFile = function() {
        FileService.saveFile({content:vm.editor.getSession().getValue(),fileHash:vm.fileData.fileHash,project:vm.editorFile.project,path:vm.editorFile.treeNode.fullPath}).then(function(response) {
            if (response.data.status == "updated") {
                console.log("Attempt to execute the file")
                vm.fileData.fileHash = response.data.fileHash;
                vm.status = "executing....";
                FileService.executeFile(vm.editorFile.project,vm.editorFile.treeNode.fullPath).then(function(saveResponse) {
                    //console.log("The current hash [" + editorFile.fileData.fileHash + "]")
                    vm.status = "executed!";
                    $rootScope.$broadcast("executeOutput",saveResponse.data);
                });
            } else {
                console.log("There was a conflish on the file original hash [" + vm.fileData.fileHash + "] new hash [" + response.data.fileHash + "]")
                vm.status = "syncerror"
            }
            vm.dirty = false;
        });
    }
    
    vm.refreshFile = function(id) {
        FileService.getFile(vm.editorFile.project,vm.editorFile.treeNode.fullPath).then(function(response) {
            vm.fileData = response.data.contents;
            vm.contents = response.data.contents;
            vm.dirty = false;
            vm.status = "refreshed"
        });
    }
    
    $scope.$on("$destroy", function() {
        console.log("The destroy method has been called [%o]",vm.fileData)
    });
    
    
    // loop through the editor files
    $interval(function() {
        if (vm.dirty) {
            vm.saveFile()
        }
    }, 1000 * 10);
    
  })