'use strict';

describe('Controller: FileModalCtrl', function () {

  // load the controller's module
  beforeEach(module('ide2App'));

  var FileModalCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    FileModalCtrl = $controller('FileModalCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(FileModalCtrl.awesomeThings.length).toBe(3);
  });
});
