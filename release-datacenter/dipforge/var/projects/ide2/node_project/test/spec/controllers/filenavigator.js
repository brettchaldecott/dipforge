'use strict';

describe('Controller: FileNavigatorCtrl', function () {

  // load the controller's module
  beforeEach(module('ide2App'));

  var FileNavigatorCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    FileNavigatorCtrl = $controller('FileNavigatorCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(FileNavigatorCtrl.awesomeThings.length).toBe(3);
  });
});
