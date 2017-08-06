/*
 * ide2: Description
 * Copyright (C) Thu Aug 03 15:30:52 UTC 2017 owner
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
 * sparql.js
 * @author admin
 */

//YASGUI.YASQE.defaults.sparql.endpoint = "/rdf/";
//YASQE.defaults.sparql.showQueryButton = true;
//YASQE.defaults.sparql.callbacks.success =  function(data){console.log("success %o", data);};

angular.module('ide2App')
  .controller('SparqlCtrl', function ($rootScope, $scope, $interval,ProjectService) {
    var vm = this;
    
    vm.selectTab = function(id) {
        
        if (id != null) {
            $("#" + id).addClass("active");
            $("#tab-" + id).addClass("active");
            console.log("The selected the tab %s",id)
        }
    }
    
    console.log("Hello world")
    vm.setupYasqe = function() {
        var yasqe = YASGUI(document.getElementById("yasqe_div"), {
        //Uncomment below to change the default endpoint
        //Note: If you've already opened the YASGUI page before, you should first clear your
        //local-storage cache before you will see the changes taking effect 
        yasqe:{sparql:{endpoint:'/rdf/'}}
      });
    
        vm.selectTab("Sparql")
    }
    
    vm.setupYasqe();
    
    
    
    
  });
