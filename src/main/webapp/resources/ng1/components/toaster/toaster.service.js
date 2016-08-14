"use strict";

angular.module('toaster').service('toaster', ['toasty', toasterService]);

function toasterService(toasty) {
  return {
    success: function success(message) {
      toasty.success(message);
    },

    error: function(title, error) {
      let errorMessage;
      if(error.data && error.data.message) errorMessage = error.data.message;
      else if(error.data) errorMessage = error.data;
      else errorMessage = error;

      toasty.error({title: title, msg: errorMessage});
    }
  };
}