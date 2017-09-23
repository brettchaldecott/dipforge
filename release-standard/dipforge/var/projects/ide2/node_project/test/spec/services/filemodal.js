'use strict';

describe('Service: FileModal', function () {

  // load the service's module
  beforeEach(module('ide2App'));

  // instantiate service
  var FileModal;
  beforeEach(inject(function (_FileModal_) {
    FileModal = _FileModal_;
  }));

  it('should do something', function () {
    expect(!!FileModal).toBe(true);
  });

});
