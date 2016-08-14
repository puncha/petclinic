"use strict";

angular.module('toaster').config(['toastyConfigProvider', configToasty]);

function configToasty(toastyConfigProvider) {
  toastyConfigProvider.setConfig({
    clickToClose: true,
    position: 'top-right',
    theme: 'bootstrap'
  });
}
