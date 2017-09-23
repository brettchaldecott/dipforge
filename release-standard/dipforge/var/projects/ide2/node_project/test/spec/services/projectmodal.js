'use strict';

describe('Service: ProjectModal', function () {

  // load the service's module
  beforeEach(module('ide2App'));

  // instantiate service
  var ProjectModal;
  beforeEach(inject(function (_ProjectModal_) {
    ProjectModal = _ProjectModal_;
  }));

  it('should do something', function () {
    expect(!!ProjectModal).toBe(true);
  });

});
