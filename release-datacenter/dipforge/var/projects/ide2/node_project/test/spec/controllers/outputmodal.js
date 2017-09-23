'use strict';

describe('Controller: OutputModalCtrl', function () {

  // load the controller's module
  beforeEach(module('ide2App'));

  var OutputModalCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    OutputModalCtrl = $controller('OutputModalCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(OutputModalCtrl.awesomeThings.length).toBe(3);
  });
});
