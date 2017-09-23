'use strict';

describe('Controller: ProjectModalCtrl', function () {

  // load the controller's module
  beforeEach(module('ide2App'));

  var ProjectModalCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ProjectModalCtrl = $controller('ProjectModalCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(ProjectModalCtrl.awesomeThings.length).toBe(3);
  });
});
