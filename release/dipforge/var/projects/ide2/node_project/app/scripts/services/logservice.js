/*
 * ide2: Description
 * Copyright (C) Wed Aug 09 18:07:41 UTC 2017 owner
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
 * logservice.js
 * @author admin
 */



angular.module('ide2App')
  .service('LogService', function ($http) {
    console.log("This is stuff")
    
    this.listLogs = function() {
        return $http.get("../com/dipforge/ide/ListLogs.groovy");
    };
    
    
    this.tailLog = function(logFile,endLine) {
        return $http.get("../com/dipforge/ide/TailLogFile.groovy?logFile=" + logFile + "&endLine=" + endLine);
    };

  });