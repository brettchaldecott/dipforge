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
    
    vm.deleteProject = function() {
        $rootScope.$broadcast('deleteProject', {projectName: vm.project});
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
    
    
    
    /*apple_selected = function(branch) {
      return $scope.output = "APPLE! : " + branch.label;
    };
      
    treedata_avm = [
      {
        label: 'Animal',
        children: [
          {
            label: 'Dog',
            data: {
              description: "man's best friend"
            }
          }, {
            label: 'Cat',
            data: {
              description: "Felis catus"
            }
          }, {
            label: 'Hippopotamus',
            data: {
              description: "hungry, hungry"
            }
          }, {
            label: 'Chicken',
            children:  ['White Leghorn', 'Rhode Island Red', 'Jersey Giant']
          }
        ]
      }, {
        label: 'Vegetable',
        noLeaf: true,
        data: {
          definition: "A plant or part of a plant used as food, typically as accompaniment to meat or fish, such as a cabbage, potato, carrot, or bean.",
          data_can_contain_anything: true
        },
        onSelect: function(branch) {
            console.info("The on select method for Vegetable")
            
            branch.children = [
          {
            label: 'Oranges'
          }, {
            label: 'Apples',
            children: [
              {
                label: 'Granny Smith',
                onSelect: apple_selected
              }, {
                label: 'Red Delicous',
                onSelect: apple_selected
              }, {
                label: 'Fuji',
                onSelect: apple_selected,
                children: [
                    {
                        label: "testing"
                    }
                    ]
              }
            ]
          }
        ]
            
          return $scope.output = "Vegetable: " + branch.data.definition;
        }
        
      }, {
        label: 'Mineral',
        children: [
          {
            label: 'Rock',
            children: ['Igneous', 'Sedimentary', 'Metamorphic']
          }, {
            label: 'Metal',
            children: ['Aluminum', 'Steel', 'Copper']
          }, {
            label: 'Plastic',
            children: [
              {
                label: 'Thermoplastic',
                children: ['polyethylene', 'polypropylene', 'polystyrene', ' polyvinyl chloride']
              }, {
                label: 'Thermosetting Polymer',
                children: ['polyester', 'polyurethane', 'vulcanized rubber', 'bakelite', 'urea-formaldehyde']
              }
            ]
          }
        ]
      }
    ];
    treedata_geography = [
      {
        label: 'North America',
        children: [
          {
            label: 'Canada',
            children: ['Toronto', 'Vancouver']
          }, {
            label: 'USA',
            children: ['New York', 'Los Angeles']
          }, {
            label: 'Mexico',
            children: ['Mexico City', 'Guadalajara']
          }
        ]
      }, {
        label: 'South America',
        children: [
          {
            label: 'Venezuela',
            children: ['Caracas', 'Maracaibo']
          }, {
            label: 'Brazil',
            children: ['Sao Paulo', 'Rio de Janeiro']
          }, {
            label: 'Argentina',
            children: ['Buenos Aires', 'Cordoba']
          }
        ]
      }
    ];
    $scope.my_data = treedata_avm;
    $scope.try_changing_the_tree_data = function() {
      if ($scope.my_data === treedata_avm) {
        return $scope.my_data = treedata_geography;
      } else {
        return $scope.my_data = treedata_avm;
      }
    };
    $scope.my_tree = tree = {};
    $scope.try_async_load = function() {
      $scope.my_data = [];
      $scope.doing_async = true;
      return $timeout(function() {
        if (Math.random() < 0.5) {
          $scope.my_data = treedata_avm;
        } else {
          $scope.my_data = treedata_geography;
        }
        $scope.doing_async = false;
        return tree.expand_all();
      }, 1000);
    };
    return $scope.try_adding_a_branch = function() {
      var b;
      b = tree.get_selected_branch();
      return tree.add_branch(b, {
        label: 'New Branch',
        data: {
          something: 42,
          "else": 43
        }
      });
    };*/
    
        
  });
