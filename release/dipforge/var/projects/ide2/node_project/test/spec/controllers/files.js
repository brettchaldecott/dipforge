'use strict';

describe('Controller: FilesCtrl', function () {

  // load the controller's module
  beforeEach(module('ide2App'));

  var FilesCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    FilesCtrl = $controller('FilesCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(FilesCtrl.awesomeThings.length).toBe(3);
  });
});
