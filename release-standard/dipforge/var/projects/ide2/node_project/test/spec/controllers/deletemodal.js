'use strict';

describe('Controller: DeleteModalCtrl', function () {

  // load the controller's module
  beforeEach(module('ide2App'));

  var DeleteModalCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    DeleteModalCtrl = $controller('DeleteModalCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(DeleteModalCtrl.awesomeThings.length).toBe(3);
  });
});
