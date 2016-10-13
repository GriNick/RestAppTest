'use strict';

/**
 * @ngdoc function
 * @name restFulClientApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the restFulClientApp
 */
angular.module('restFulClientApp')
  .controller('MainCtrl', function ($scope, $log, $filter, $http) {
$scope.restURL = "http://localhost:8080/RESTfulExample/rest/api/booking/";

$scope.startWorkingDay = generateTime(10, 0);
$scope.endWorkingDay = generateTime(19, 0);



$scope.requests=[];
$scope.addRequest = function() {
  var request = {
    submissionDate: {
      opened: false,
      open: function() {
        this.opened = true;
      },
      value: new Date()
    },
    submissionTime: {
      value: new Date()
    },
    requestedDate: {
      opened: false,
      open: function() {
        this.opened = true
      },
      value: new Date()
    },
    requestedTime: {
      value: generateTime(13, 0)
    },
    duration: "1",
    requester: "User1"
  }
  $scope.requests.push(request);
}
$scope.removeRequest = function(i) {
  $scope.requests.splice(i,1);
}

$scope.setStatusMessage =  function(mes) {
  $scope.status = mes;
}

function generateTime(hours, minutes) {
    var d = new Date();
    d.setHours(hours);
    d.setMinutes(minutes);
    return d;
}
function generateDay(year, month, day) {
    var d = new Date();
    d.setYear(year);
    d.setMonth(month);
    d.setDay(day);
    return d;
}
function createRequestsJSON() {
  var reqs = [];
  for (var i = 0; i<$scope.requests.length; i++) {
    var req = $scope.requests[i];
    var r = {};
    r.requester = req.requester;
    var sD = req.submissionDate.value;
    var sT = req.submissionTime.value;
    sD.setHours(sT.getHours());
    sD.setMinutes(sT.getMinutes());
    sD.setSeconds(sT.getSeconds());
    r.submissionDateTime = $filter('date')(sD,'yyyy-MM-dd HH:mm:ss');
    var rD = req.requestedDate.value;
    var rT = req.requestedTime.value;
    rD.setHours(rT.getHours());
    rD.setMinutes(rT.getMinutes());
    r.requestedDateTime = $filter('date')(rD,'yyyy-MM-dd HH:mm');
    r.duration = req.duration;
    reqs.push(r);
  }
  var result = {};
  result.startWorkingDay = $filter('date')($scope.startWorkingDay, 'HH:mm');
  result.endWorkingDay = $filter('date')($scope.endWorkingDay, 'HH:mm');
  result.request = reqs;
  return result;
};

$scope.sendRequest = function() {
  $scope.setStatusMessage("");
  var data = createRequestsJSON();
  console.log("data = "+JSON.stringify(data));
  $http({   method: 'POST',
            url: $scope.restURL,
            async: true,
            headers:  { 'Cache-Control' : 'no-cache' } ,
            data : JSON.stringify(data),
            contentType: 'application/json; charset=utf-8',
            dataType : 'json'
         })
  .then(
          function(response) {
            console.log(response);
            if (response.status && response.status == 200) {
               if (response.data && response.data.statusCode == 200) {
                  $scope.calendar = response.data.data;
                  $scope.setStatusMessage("Calendar has been successfully generated. Go to the booking calendar tab to have a look at it");
               } else {
                 $scope.setStatusMessage("Cannot perform this request");
                 $scope.calendar = null;
               }
            } else {
              $scope.setStatusMessage("Cannot get correct answer from the server");
              $scope.calendar = null;
            }
          },
          function(err) {
            scope.setStatusMessage("Cannot get correct answer from the server");
            $scope.calendar = null;
          });
};


for(var i=0; i<3; i++) {
  $scope.addRequest();
}

});
