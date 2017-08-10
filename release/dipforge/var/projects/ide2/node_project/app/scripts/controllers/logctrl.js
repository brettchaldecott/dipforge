/*
 * ide2: Description
 * Copyright (C) Wed Aug 09 18:00:56 UTC 2017 owner
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
 * logctrl.js
 * @author admin
 */



angular.module('ide2App')
  .controller('LogCtrl', function ($rootScope, $scope, $interval,ProjectService,LogService) {
    var vm = this;
    
    // controllers
    vm.logList = []
    vm.logFileName = null
    vm.logContents = null;
    vm.logResponse = {
        lines:"",
        endLine: 0,
        gap: true
    };
    vm.pollLogFile = false;
    
    
    // setup the log
    vm.setupLog = function() {
        LogService.listLogs().then(function(response) {
                vm.logList = response.data
        });
        
        $rootScope.$broadcast('toolLoaded', {tool: 'LogConsole'});
    }
    
    
    vm.handleChangeLogFile = function() {
        vm.pollLogFile = true
        vm.logContents = ""
    }
    
    
    vm.tailLogFile = function() {
        if (!vm.pollLogFile) {
            return
        }
        LogService.tailLog(vm.logFileName,vm.logResponse.endLine).then(function(response) {
                vm.logResponse = response.data[0];
                console.log("The log response is %o",response)
                console.log("The set log response is %o",vm.logResponse)
                vm.logContents += vm.logResponse.lines
        });
    }
    
    
    // control methods
    vm.play = function() {
        vm.pollLogFile = true;
    }
    
    
    vm.clear = function() {
        vm.logContents = ""
    }
    
    vm.stop = function() {
        vm.pollLogFile = false;
    }
    
    
    // the on init
    vm.$onInit = function() {
        vm.setupLog()
    }
    
    
    // loop through the editor files
    $interval(function() {
        vm.tailLogFile()
        
    }, 1000 * 5);
});
