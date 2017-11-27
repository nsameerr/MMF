var clevertap = {event:[], profile:[], account:[], onUserLogin:[], notifications:[]};
 //clevertap.account.push({"id": "CLEVERTAP_ACCOUNT_ID"});
 //replace with the CLEVERTAP_ACCOUNT_ID with the actual ACCOUNT ID value from your Dashboard -> Settings page : Account ID : 65R-RZ7-974Z 
//if(document.domain == "managemyfortune.com"){
if(document.domain.indexOf("managemyfortune.com") >= 0){
	 //Production Server
	clevertap.account.push({"id": "65R-RZ7-974Z"});
}else {
	 //Test Server
	 clevertap.account.push({"id": "TEST-75R-RZ7-974Z"});	
}
 (function () {
		 var wzrk = document.createElement('script');
		 wzrk.type = 'text/javascript';
		 wzrk.async = true;
		 wzrk.src = ('https:' == document.location.protocol ? 'https://d2r1yp2w7bby2u.cloudfront.net' : 'http://static.clevertap.com') + '/js/a.js';
		 var s = document.getElementsByTagName('script')[0];
		 s.parentNode.insertBefore(wzrk, s);
  })();