'use strict';

describe('Directive: abnTree', function () {

  // load the directive's module
  beforeEach(module('ide2App'));

  var element,
    scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  it('should make hidden element visible', inject(function ($compile) {
    element = angular.element('<abn-tree></abn-tree>');
    element = $compile(element)(scope);
    expect(element.text()).toBe('this is the abnTree directive');
  }));
});
