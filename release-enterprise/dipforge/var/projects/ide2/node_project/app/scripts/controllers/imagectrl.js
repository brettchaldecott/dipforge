/*
 * ide2: Description
 * Copyright (C) Wed Aug 09 04:32:48 UTC 2017 owner
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
 * imagectrl.js
 * @author admin
 */



angular.module('ide2App')
  .controller('ImageCtrl', function ($rootScope, $scope, $interval,ProjectService) {
    var vm = this;
    
    
    $scope.init = function(id, project, path) {
        vm.id = id;
        vm.project = project;
        vm.path = path;
        
        $scope.imageTag = '<img src="/DipforgeWeb/' 
            + vm.project + '/' 
            + vm.path 
            + '" class="img-thumbnail"/>';
        
        console.log("The image tag is %s",vm.imageTag)
        
        $rootScope.$broadcast('toolLoaded', {tool: 'Image',id: vm.id});
    }
    
  });
