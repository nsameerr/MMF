app.directive('mmfFooterNew', function() { 
  return { 
    restrict: 'E', 
    scope: { 
      info: '=' 
    }, 
    templateUrl: 'mmfFooterNew.html' 
  }; 
});
app.directive('mmfHeaderNew', function() { 
  return { 
    restrict: 'E', 
    scope: { 
      info: '=' 
    }, 
    templateUrl: 'mmfHeaderNew.html' 
  }; 
});