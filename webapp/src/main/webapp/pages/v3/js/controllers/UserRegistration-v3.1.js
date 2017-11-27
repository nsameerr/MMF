app.controller('UserRegistration', ['$scope', function($scope) {
	//global init
	$scope.data = {};
	$scope.MAX_FILE_SIZE="10000000"; // file size 10 MB in bytes
	$scope.isMobile =(/android|webos|iphone|ipad|ipod|blackberry|iemobile|opera mini/i.test(navigator.userAgent.toLowerCase()));
	
	$scope.init = function (){
		try{
		showLoading();
		initCarousel();
		$scope.data = {};
		//prefill user data
		$scope.initPrefill();
//		$scope.$apply();	
		}catch(e){
			console.error(e);
		}
	} //init end

	$scope.preparePage = function(){
				//country list
		$scope.globalCountryList = [{value:'IN',name:'India'}];
		$scope.globalResidential = [{value :"Foreign National",name :"Foreign National"},{value :"Resident Individual",name :"Resident Individual"},{value :"Non Resident",name :"Non Resident"}];

		$scope.nationality = $scope.globalCountryList[0];
		$scope.data.nationality = $scope.nationality.value;

		$scope.status = $scope.globalResidential[1];
		$scope.data.status = $scope.status.value;

		//personal detail edit = true
		$scope.pdedit = 'yes';
		console.log($scope.pdedit);

		//married status
		$scope.data.mstatus = ($scope.data.mstatus == '' ? 'Single' : $scope.data.mstatus);
		console.log($scope.data.mstatus);

		//correspondence address proof 
		$scope.addressProofOptions =[
			{ value:"Aadhar",name : "Aadhar"},
			{ value:"Driving Licence",name : "Driving Licence"},
			{ value:"Latest Bank A/c Statement/Passbook (Not older than 3 months)",name : "Latest Bank A/c Statement/Passbook"},
			//{ value:"Latest Electricity Bill(Not older than 3 months)",name : "Latest Electricity Bill"},
			//{ value:"Latest Gas Bill(Not older than 3 months)",name : "Latest Gas Bill"},
			//{ value:"Latest Telephone Bill (only Land Line - Not older than 3 months)",name : "Latest Telephone Bill (only Land Line)"},
			{ value:"Passport",name : "Passport"},
			{ value:"Ration Card",name : "Ration Card"},
			{ value:"Registered Lease/Sale Agreement of Residence",name : "Registered Lease/Sale Agreement of Residence"},
			{ value:"Voter Identity Card",name : "Voter Identity Card"}
			];
		/*console.log($scope.globalCountryList[0]);
		console.log($scope.data.nationality);*/
		
		//$scope.cproof = ($scope.data.cproof == "" ? "" : $scope.data.cproof);
		angular.forEach($scope.addressProofOptions, function (option) {
		   if(option.value == $scope.data.cproof){
		   		$scope.cproof = option;
		   		return true;
		   }
		});

		//$scope.pproof = ($scope.data.pproof == "" ? "" : $scope.data.pproof);
		angular.forEach($scope.addressProofOptions, function (option) {
		   if(option.value == $scope.data.pproof){
		   		$scope.pproof = option;
		   		return true;
		   }
		});

		//address details edit
		$scope.adedit = 'yes';

		//corespondence address country
		$scope.country = $scope.globalCountryList[0];
		$scope.data.country = $scope.country.value;

		//corespondence address country
		$scope.pcountry = $scope.globalCountryList[0];
		$scope.data.pcountry = $scope.pcountry.value;

		if ($scope.data.fstHldrIncome != ""){
			//incomeRangeEdit
			$scope.incomeRangeEdit = 'networth';
		} else {
			//incomeRangeEdit
			$scope.incomeRangeEdit = 'income_range';
		}
		

		//data.nomineeExist
		$scope.data.nomineeExist  = ($scope.data.nomineeExist == false ? false : $scope.data.nomineeExist);

		//nominee address country
		$scope.nomCountry = $scope.globalCountryList[0];
		$scope.data.nomCountry = $scope.nomCountry.value;

		//data.minorExist
		$scope.data.minorExist = ($scope.data.minorExist == false ? false : $scope.data.minorExist);

		//minor address country
		$scope.mnrCountry = $scope.globalCountryList[0];
		$scope.data.mnrCountry = $scope.mnrCountry.value;


		//data.accountType
		$scope.data.accountType = ($scope.data.accountType == '' ? 'Savings Account' : $scope.data.accountType);

		//bcountry
		$scope.bcountry = $scope.globalCountryList[0];
		$scope.data.bcountry = $scope.bcountry.value;
		
		//init all select state boxes
		$scope.setSelectState();
		//init all select city boxes
		$scope.globalCityOptions = ["Abhayapuri","Achabbal","Achhnera","Adalaj","Adari","Adilabad","Adityana","Adoni","Adoor","Adra, Purulia","Agartala","Agra","Ahiwara","Ahmedabad","Ahmedgarh","Ahmednagar","Aizawl","Ajmer","Akaltara","Akathiyoor","Akhnoor","Akola","Alang","Alappuzha","Aldona","Aligarh","Alipurduar","Allahabad","Almora","Along","Alwar","Amadalavalasa","Amalapuram","Amarpur","Ambagarh Chowki","Ambaji","Ambala","Ambaliyasan","Ambikapur","Amguri","Amlabad","Amli","Amravati","Amreli","Amritsar","Amroha","Anakapalle","Anand","Anandapur","Anandnagaar","Anantapur","Anantnag","Ancharakandy","Andada","Anjar","Anklav","Ankleshwar","Antaliya","Anugul","Ara","Arakkonam","Arambagh","Arambhada","Arang","Araria","Arasikere","Arcot","Areraj","Arki","Arnia","Aroor","Arrah","Aruppukkottai","Asankhurd","Asansol","Asarganj","Ashok Nagar","Ashtamichira","Asika","Asola","Assandh","Ateli","Attingal","Atul","Aurangabad","Avinissery","Awantipora","Azamgarh","Babiyal","Baddi","Bade Bacheli","Badepalle","Badharghat","Bagaha","Bahadurganj","Bahadurgarh","Baharampur","Bahraich","Bairgania","Bakhtiarpur","Balaghat","Balangir","Balasore","Baleshwar","Bali","Ballabhgarh","Ballia","Bally","Balod","Baloda Bazar","Balrampur","Balurghat","Bamra","Banda","Bandikui","Bandipore","Bangalore","Banganapalle","Banka","Bankura","Banmankhi Bazar","Banswara","Bapatla","Barahiya","Barakar","Baramati","Baramula","Baran","Barasat","Barauli","Barbigha","Barbil","Bardhaman","Bareilly","Bargarh","Barh","Baripada","Barmer","Barnala","Barpeta","Barpeta Road","Barughutu","Barwala","Basudebpur","Batala","Bathinda","Bazpur","Begusarai","Behea","Belgaum","Bellampalle","Bellary","Belpahar","Bemetra","Bethamcherla","Bettiah","Betul","Bhabua","Bhadrachalam","Bhadrak","Bhagalpur","Bhagha Purana","Bhainsa","Bharuch","Bhatapara","Bhavani","Bhavnagar","Bhawanipatna","Bheemunipatnam","Bhimavaram","Bhiwani","Bhongir","Bhopal","Bhuban","Bhubaneswar","Bhuj","Bidhan Nagar","Bihar Sharif","Bikaner","Bikramganj","Bilasipara","Bilaspur","Biramitrapur","Birgaon","Bobbili","Bodh Gaya","Bodhan","Bokaro Steel City","Bomdila","Bongaigaon","Brahmapur","Brajrajnagar","Budhlada","Burhanpur","Buxar","Byasanagar","Calcutta","Cambay","Chaibasa","Chakradharpur","Chalakudy","Chalisgaon","Chamba","Champa","Champhai","Chamrajnagar","Chandan Bara","Chandausi","Chandigarh","Chandrapura","Changanassery","Chanpatia","Charkhi Dadri","Chatra","Cheeka","Chendamangalam","Chengalpattu","Chengannur","Chennai","Cherthala","Cheruthazham","Chhapra","Chhatarpur","Chikkaballapur","Chikmagalur","Chilakaluripet","Chinchani","Chinna salem","Chinsura","Chintamani","Chirala","Chirkunda","Chirmiri","Chitradurga","Chittoor","Chittur-Thathamangalam","Chockli","Churi","Coimbatore","Colgong","Contai","Cooch Behar","Coonoor","Cuddalore","Cuddapah","Curchorem Cacora","Cuttack","Dabra","Dadri","Dahod","Dalhousie","Dalli-Rajhara","Dalsinghsarai","Daltonganj","Daman and Diu","Darbhanga","Darjeeling","Dasua","Datia","Daudnagar","Davanagere","Debagarh","Deesa","Dehradun","Dehri-on-Sone","Delhi","Deoghar","Deoria","Devarakonda","Devgarh","Dewas","Dhaka","Dhamtari","Dhanbad","Dhar","Dharampur, India","Dharamsala","Dharmanagar","Dharmapuri","Dharmavaram","Dharwad","Dhekiajuli","Dhenkanal","Dholka","Dhubri","Dhule","Dhuri","Dibrugarh","Digboi","Dighwara","Dimapur","Dinanagar","Dindigul","Diphu","Dipka","Dispur","Dombivli","Dongargarh","Dumka","Dumraon","Durg-Bhilai Nagar","Durgapur","Ellenabad 2","Eluru","Erattupetta","Erode","Etawah","Faridabad","Faridkot","Farooqnagar","Fatehabad","Fatehpur","Fatwah","Fazilka","Firozpur","Firozpur Cantt.","Forbesganj","Gadag","Gadwal","Ganaur","Gandhinagar","Gangtok","Garhwa","Gauripur","Gaya","Gharaunda","Ghatshila","Giddarbaha","Giridih","Goalpara","Gobindgarh","Gobranawapara","Godda","Godhra","Gogri Jamalpur","Gohana","Golaghat","Gomoh","Gooty","Gopalganj","Greater Noida","Gudalur","Gudivada","Gudur","Gulbarga","Gumia","Gumla","Gundlupet","Guntakal","Guntur","Gunupur","Gurdaspur","Gurgaon","Guruvayoor","Guwahati","Gwalior","Haflong","Haibat(Yamuna Nagar)","Hailakandi","Hajipur","Haldia","Haldwani","Hamirpur","Hansi","Hanuman Junction","Hardoi","Haridwar","Hassan","Hazaribag","Hilsa","Himatnagar","Hindupur","Hinjilicut","Hisar","Hisua","Hodal","Hojai","Hoshiarpur","Hospet","Howrah","Hubli","Hussainabad","Hyderabad","Ichalkaranji","Ichchapuram","Idar","Imphal","Indore","Indranagar","Irinjalakuda","Islampur","Itanagar","Itarsi","Jabalpur","Jagatsinghapur","Jagdalpur","Jagdispur","Jaggaiahpet","Jagraon","Jagtial","Jaipur","Jaisalmer","Jaitu","Jajapur","Jajmau","Jalalabad","Jalandhar","Jalandhar Cantt.","Jaleswar","Jalna","Jalore","Jamalpur","Jammalamadugu","Jammu","Jamnagar","Jamshedpur","Jamtara","Jamui","Jandiala","Jangaon","Janjgir","Jashpurnagar","Jaspur","Jatani","Jaunpur","Jehanabad","Jeypur","Jhajha","Jhajjar","Jhanjharpur","Jhansi","Jhargram","Jharsuguda","Jhumri Tilaiya","Jind","Joda","Jodhpur","Jogabani","Jogendranagar","Jorhat","Jowai","Junagadh","Kadapa","Kadi","Kadiri","Kadirur","Kagaznagar","Kailasahar","Kaithal","Kakching","Kakinada","Kalan Wali","Kalavad","Kalka","Kalliasseri","Kalol","Kalpetta","Kalpi","Kalyan","Kalyandurg","Kamareddy","Kanchipuram","Kandukur","Kanhangad","Kanjikkuzhi","Kanker","Kannur","Kanpur","Kantabanji","Kanti","Kapadvanj","Kapurthala","Karaikal","Karaikudi","Karanjia","Karimganj","Karimnagar","Karjan","Karkala","Karnal","Karoran","Kartarpur","Karungal","Karur","Karwar","Kasaragod","Kashipur","Kathua","Katihar","Katni","Kavali","Kavaratti","Kawardha","Kayamkulam","Kendrapara","Kendujhar","Keshod","Khagaria","Khambhalia","Khambhat","Khammam","Khanna","Kharagpur","Kharar","Kheda","Khedbrahma","Kheralu","Khordha","Khowai","Khunti","kichha","Kishanganj","Kochi","Kodinar","Kodungallur","Kohima","Kokrajhar","Kolar","Kolhapur","Kolkata","Kollam","Kollankodu","Kondagaon","Koothuparamba","Koraput","Koratla","Korba","Kot Kapura","Kota","Kotdwara","Kothagudem","Kothamangalam","Kothapeta","Kotma","Kottayam","Kovvur","Kozhikode","Kunnamkulam","Kurali","Kurnool","Kyathampalle","Lachhmangarh","Ladnu","Ladwa","Lahar","Laharpur","Lakheri","Lakhimpur","Lakhisarai","Lakshmeshwar","Lal Gopalganj Nindaura","Lalganj","Lalgudi","Lalitpur","Lalsot","Lanka","Lar","Lathi","Latur","Leh","Lilong","Limbdi","Lingsugur","Loha","Lohardaga","Lonar","Lonavla","Longowal","Loni","Losal","Lucknow","Ludhiana","Lumding","Lunawada","Lundi","Lunglei","Macherla","Machilipatnam","Madanapalle","Maddur","Madgaon","Madhepura","Madhubani","Madhugiri","Madhupur","Madikeri","Madurai","Magadi","Mahad","Mahalingpur","Maharajganj","Maharajpur","Mahasamund","Mahbubnagar","Mahe","Mahendragarh","Mahesana","Mahidpur","Mahnar Bazar","Mahuli","Mahuva","Maihar","Mainaguri","Makhdumpur","Makrana","Mal","Malajkhand","Malappuram","Malavalli","Malegaon","Malerkotla","Malkangiri","Malkapur","Malout","Malpura","Malur","Manasa","Manavadar","Manawar","Manchar","Mancherial","Mandalgarh","Mandamarri","Mandapeta","Mandawa","Mandi","Mandi Dabwali","Mandideep","Mandla","Mandsaur","Mandvi","Mandya","Maner","Mangaldoi","Mangalore","Mangalvedhe","Manglaur","Mangrol","Mangrulpir","Manihari","Manjlegaon","Mankachar","Manmad","Mansa","Manuguru","Manvi","Manwath","Mapusa","Margao","Margherita","Marhaura","Mariani","Marigaon","Markapur","Marmagao","Masaurhi","Mathabhanga","Mathura","Mattannur","Mauganj","Maur","Mavelikkara","Mavoor","Mayang Imphal","Medak","Medinipur","Meerut","Mehkar","Mehmedabad","Memari","Merta City","Mhaswad","Mhow Cantonment","Mhowgaon","Mihijam","Miraj","Mirganj","Miryalaguda","Modasa","Modinagar","Moga","Mogalthur","Mohali","Mokameh","Mokokchung","Monoharpur","Morena","Morinda","Morshi","Morvi","Motihari","Motipur","Mount Abu","Mudalgi","Mudbidri","Muddebihal","Mudhol","Mukerian","Mukhed","Muktsar","Mul","Mulbagal","Multai","Mumbai","Mundargi","Mungeli","Munger","Muradnagar","Murliganj","Murshidabad","Murtijapur","Murwara","Musabani","Mussoorie","Muvattupuzha","Muzaffarnagar","Muzaffarpur","Mysore","Nabadwip","Nabarangapur","Nabha","Nadbai","Nadiad","Nagaon","Nagapattinam","Nagar","Nagari","Nagarkurnool","Nagaur","Nagda","Nagercoil","Nagina","Nagla","Nagpur","Nahan","Naharlagun","Naihati","Naila Janjgir","Nainital","Nainpur","Najibabad","Nakodar","Nakur","Nalasopara","Nalbari","Namagiripettai","Namakkal","Nanded-Waghala","Nandgaon","Nandivaram-Guduvancheri","Nandura","Nandurbar","Nandyal","Nangal","Nanjangud","Nanjikottai","Nanpara","Narasapur","Narasaraopet","Naraura","Narayanpet","Nargund","Narkatiaganj","Narkhed","Narnaul","Narsinghgarh","Narsipatnam","Narwana","Nashik","Nasirabad","Natham","Nathdwara","Naugachhia","Naugawan Sadat","Nautanwa","Navalgund","Navi Mumbai","Navsari","Nawabganj","Nawada","Nawalgarh","Nawanshahr","Nawapur","Nedumangad","Neem-Ka-Thana","Neemuch","Nehtaur","Nelamangala","Nellikuppam","Nellore","Nepanagar","Neyveli","Neyyattinkara","Nidadavole","Nilanga","Nimbahera","Nipani","Nirmal","Niwai","Niwari","Nizamabad","Nohar","NOIDA","Nokha","Nongstoin","Noorpur","North Lakhimpur","Nowgong","Nowrozabad","Nuzvid","O Valley","Obra","Oddanchatram","Ongole","Orai","Osmanabad","Ottappalam","Ozar","P.N.Patti","Pachora","Pachore","Pacode","Padmanabhapuram","Padra","Padrauna","Paithan","Pakaur","Palacole","Palai","Palakkad","Palani","Palanpur","Palasa Kasibugga","Palghar","Pali","Palia Kalan","Palitana","Palladam","Pallapatti","Pallikonda","Palwal","Palwancha","Panagar","Panagudi","Panaji","Panchkula","Panchla","Pandharkaoda","Pandharpur","Pandhurna","Pandua","Panipat","Panna","Panniyannur","Panruti","Panvel","Pappinisseri","Paradip","Paramakudi","Parangipettai","Parasi","Paravoor","Parbhani","Pardi","Parlakhemundi","Parli","Parola","Partur","Parvathipuram","Pasan","Paschim Punropara","Pasighat","Patan","Pathanamthitta","Pathankot","Pathardi","Pathri","Patiala","Patna","Patran","Patratu","Pattamundai","Patti","Pattukkottai","Patur","Pauni","Pauri","Pavagada","Payyannur","Pedana","Peddapuram","Pehowa","Pen","Perambalur","Peravurani","Peringathur","Perinthalmanna","Periyakulam","Periyasemur","Pernampattu","Perumbavoor","Petlad","Phagwara","Phalodi","Phaltan","Phillaur","Phulabani","Phulera","Phulpur","Phusro","Pihani","Pilani","Pilibanga","Pilibhit","Pilkhuwa","Pindwara","Pinjore","Pipar City","Pipariya","Piro","Pithampur","Pithapuram","Pithoragarh","Pollachi","Polur","Pondicherry","Ponduru","Ponnani","Ponneri","Ponnur","Porbandar","Porsa","Port Blair","Powayan","Prantij","Pratapgarh","Prithvipur","Proddatur","Pudukkottai","Pudupattinam","Pukhrayan","Pulgaon","Puliyankudi","Punalur","Punch","Pune","Punganur","Punjaipugalur","Puranpur","Puri","Purna","Purnia","Purquazi","Purulia","Purwa","Pusad","Puttur","Qadian","Quilandy","Rabkavi Banhatti","Radhanpur","Rae Bareli","Rafiganj","Raghogarh-Vijaypur","Raghunathpur","Rahatgarh","Rahuri","Raichur","Raiganj","Raigarh","Raikot","Raipur","Rairangpur","Raisen","Raisinghnagar","Rajagangapur","Rajahmundry","Rajakhera","Rajaldesar","Rajam","Rajampet","Rajapalayam","Rajauri","Rajgarh","Rajgarh (Alwar)","Rajgarh (Churu","Rajgir","Rajkot","Rajnandgaon","Rajpipla","Rajpura","Rajsamand","Rajula","Rajura","Ramachandrapuram","Ramagundam","Ramanagaram","Ramanathapuram","Ramdurg","Rameshwaram","Ramganj Mandi","Ramnagar","Ramngarh","Rampur","Rampur Maniharan","Rampura Phul","Rampurhat","Ramtek","Ranaghat","Ranavav","Ranchi","Rangia","Rania","Ranibennur","Rapar","Rasipuram","Rasra","Ratangarh","Rath","Ratia","Ratlam","Ratnagiri","Rau","Raurkela","Raver","Rawatbhata","Rawatsar","Raxaul Bazar","Rayachoti","Rayadurg","Rayagada","Reengus","Rehli","Renigunta","Renukoot","Reoti","Repalle","Revelganj","Rewa","Rewari","Rishikesh","Risod","Robertsganj","Robertson Pet","Rohtak","Ron","Roorkee","Rosera","Rudauli","Rudrapur","Rupnagar","Sabalgarh","Sadabad","Sadalgi","Sadasivpet","Sadri","Sadulshahar","Safidon","Safipur","Sagar","Sagwara","Saharanpur","Saharsa","Sahaspur","Sahaswan","Sahawar","Sahibganj","Sahjanwa","Saidpur, Ghazipur","Saiha","Sailu","Sainthia","Sakleshpur","Sakti","Salaya","Salem","Salur","Samalkha","Samalkot","Samana","Samastipur","Sambalpur","Sambhal","Sambhar","Samdhan","Samthar","Sanand","Sanawad","Sanchore","Sandi","Sandila","Sandur","Sangamner","Sangareddy","Sangaria","Sangli","Sangole","Sangrur","Sankarankoil","Sankari","Sankeshwar","Santipur","Sarangpur","Sardarshahar","Sardhana","Sarni","Sasaram","Sasvad","Satana","Satara","Sathyamangalam","Satna","Sattenapalle","Sattur","Saunda","Saundatti-Yellamma","Sausar","Savanur","Savarkundla","Savner","Sawai Madhopur","Sawantwadi","Sedam","Sehore","Sendhwa","Seohara","Seoni","Seoni-Malwa","Shahabad","Shahabad, Hardoi","Shahabad, Rampur","Shahade","Shahbad","Shahdol","Shahganj","Shahjahanpur","Shahpur","Shahpura","Shajapur","Shamgarh","Shamli","Shamsabad, Agra","Shamsabad, Farrukhabad","Shegaon","Sheikhpura","Shendurjana","Shenkottai","Sheoganj","Sheohar","Sheopur","Sherghati","Sherkot","Shiggaon","Shikapur","Shikarpur, Bulandshahr","Shikohabad","Shillong","Shimla","Shimoga","Shirdi","Shirpur-Warwade","Shirur","Shishgarh","Shivpuri","Sholavandan","Sholingur","Shoranur","Shorapur","Shrigonda","Shrirampur","Shrirangapattana","Shujalpur","Siana","Sibsagar","Siddipet","Sidhi","Sidhpur","Sidlaghatta","Sihor","Sihora","Sikanderpur","Sikandra Rao","Sikandrabad","Sikar","Silao","Silapathar","Silchar","Siliguri","Sillod","Silvassa","Simdega","Sindgi","Sindhnur","Singapur","Singrauli","Sinnar","Sira","Sircilla","Sirhind Fatehgarh Sahib","Sirkali","Sirohi","Sironj","Sirsa","Sirsaganj","Sirsi","Siruguppa","Sitamarhi","Sitapur","Sitarganj","Sivaganga","Sivagiri","Sivakasi","Siwan","Sohagpur","Sohna","Sojat","Solan","Solapur","Sonamukhi","Sonepur","Songadh","Sonipat","Sopore","Soro","Soron","Soyagaon","Sri Madhopur","Srikakulam","Srikalahasti","Srinagar","Srinivaspur","Srirampore","Srivilliputhur","Suar","Sugauli","Sujangarh","Sujanpur","Sultanganj","Sultanpur","Sumerpur","Sunabeda","Sunam","Sundargarh","Sundarnagar","Supaul","Surandai","Surat","Suratgarh","Suri","Suriyampalayam","Suryapet","Tadepalligudem","Tadpatri","Taki","Talaja","Talcher","Talegaon Dabhade","Talikota","Taliparamba","Talode","Talwara","Tamluk","Tanda","Tandur","Tanuku","Tarakeswar","Tarana","Taranagar","Taraori","Tarikere","Tarn Taran","Tasgaon","Tehri","Tekkalakota","Tenali","Tenkasi","Tenu Dam-cum- Kathhara","Terdal","Tetri Bazar","Tezpur","Thakurdwara","Thammampatti","Thana Bhawan","Thanesar","Thangadh","Thanjavur","Tharad","Tharamangalam","Tharangambadi","Theni Allinagaram","Thirumangalam","Thirunindravur","Thiruparappu","Thirupuvanam","Thiruthuraipoondi","Thiruvalla","Thiruvallur","Thiruvananthapuram","Thiruvarur","Thodupuzha","Thoothukudi","Thoubal","Thrissur","Thuraiyur","Tikamgarh","Tilda Newra","Tilhar","Tindivanam","Tinsukia","Tiptur","Tirora","Tiruchendur","Tiruchengode","Tiruchirappalli","Tirukalukundram","Tirukkoyilur","Tirunelveli","Tirupathur","Tirupati","Tiruppur","Tirur","Tiruttani","Tiruvannamalai","Tiruvethipuram","Tirwaganj","Titlagarh","Tittakudi","Todabhim","Todaraisingh","Tohana","Tonk","Tuensang","Tuljapur","Tulsipur","Tumkur","Tumsar","Tundla","Tuni","Tura","Uchgaon","Udaipur","Udaipurwati","Udgir","Udhagamandalam","Udhampur","Udumalaipettai","Udupi","Ujhani","Ujjain","Umarga","Umaria","Umarkhed","Umarkote","Umbergaon","Umred","Umreth","Una","Unjha","Unnamalaikadai","Unnao","Upleta","Uran","Uran Islampur","Uravakonda","Urmar Tanda","Usilampatti","Uthamapalayam","Uthiramerur","Utraula","Vadakara","Vadakkuvalliyur","Vadalur","Vadgaon Kasba","Vadipatti","Vadnagar","Vadodara","Vaijapur","Vaikom","Valparai","Valsad","Vandavasi","Vaniyambadi","Vapi","Varanasi","Varkala","Vasai","Vedaranyam","Vellakoil","Vellore","Venkatagiri","Veraval","Vicarabad","Vidisha","Vijainagar","Vijapur","Vijayapura","Vijayawada","Vikramasingapuram","Viluppuram","Vinukonda","Viramgam","Virar","Virudhachalam","Virudhunagar","Visakhapatnam","Visnagar","Viswanatham","Vita","Vizianagaram","Vrindavan","Vyara","Wadgaon Road","Wadhwan","Wadi","Wai","Wanaparthy","Wani","Wankaner","Wara Seoni","Warangal","Wardha","Warhapur","Warisaliganj","Warora","Warud","Washim","Wokha","Yadgir","Yamunanagar","Yanam","Yavatmal","Yawal","Yellandu","Yemmiganur","Yerraguntla","Yevla","Zahirabad","Zaidpur","Zamania","Zira","Zirakpur","Zunheboto"];
		//init bank select
		 $scope.globalBankList = ["ABHYUDAYA CO-OP BANK LTD","ABU DHABI COMMERCIAL BANK","AKOLA DISTRICT CENTRAL CO-OPERATIVE BANK","AKOLA JANATA COMMERCIAL COOPERATIVE BANK","ALLAHABAD BANK","ALMORA URBAN CO-OPERATIVE BANK LTD","ANDHRA BANK","ANDHRA PRAGATHI GRAMEENA BANK","APNA SAHAKARI BANK LTD","AUSTRALIA AND NEW ZEALAND BANKING GROUP LIMITED","AXIS BANK","BANK INTERNASIONAL INDONESIA","BANK OF AMERICA","BANK OF BAHRAIN AND KUWAIT","BANK OF BARODA","BANK OF INDIA","BANK OF CEYLON","BANK OF TOKYO-MITSUBISHI UFJ LTD","BANK OF MAHARASHTRA","BASSEIN CATHOLIC CO-OP BANK LTD","BARCLAYS BANK PLC","BNP PARIBAS","BHARATIYA MAHILA BANK LIMITED","CANARA BANK","CALYON BANK","CATHOLIC SYRIAN BANK LTD","CAPITAL LOCAL AREA BANK LTD","CHINATRUST COMMERCIAL BANK","CENTRAL BANK OF INDIA","CITIZENCREDIT CO-OPERATIVE BANK LTD","CITIBANK NA","CORPORATION BANK","CREDIT SUISSE AG","CITY UNION BANK LTD","COMMONWEALTH BANK OF AUSTRALIA","DEUTSCHE BANK","DEUTSCHE SECURITIES INDIA PRIVATE LIMITED","DBS BANK LTD","DENA BANK","DICGC","DOMBIVLI NAGARI SAHAKARI BANK LIMITED","DEVELOPMENT CREDIT BANK LIMITED","DHANLAXMI BANK LTD","GURGAON GRAMIN BANK","HDFC BANK LTD","FIRSTRAND BANK LIMITED","GOPINATH PATIL PARSIK JANATA SAHAKARI BANK LTD","IDFC Bank Ltd","IDRBT","IDBI BANK LTD","ICICI BANK LTD","HSBC","INDUSTRIAL AND COMMERCIAL BANK OF CHINA LIMITED","INDUSIND BANK LTD","INDIAN OVERSEAS BANK","INDIAN BANK","JANASEVA SAHAKARI BANK (BORIVLI) LTD","JANAKALYAN SAHAKARI BANK LTD","JALGAON JANATA SAHKARI BANK LTD","ING VYSYA BANK LTD","KALLAPPANNA AWADE ICH JANATA S BANK","JPMORGAN CHASE BANK N.A","JANATA SAHAKARI BANK LTD (PUNE)","JANASEVA SAHAKARI BANK LTD. PUNE","KOTAK MAHINDRA BANK","KURMANCHAL NAGAR SAHKARI BANK LTD","MAHANAGAR CO-OP BANK LTD","MAHARASHTRA STATE CO OPERATIVE BANK","KAPOL CO OP BANK","KARNATAKA BANK LTD","KARNATAKA VIKAS GRAMEENA BANK","KARUR VYSYA BANK","NATIONAL AUSTRALIA BANK","NEW INDIA CO-OPERATIVE BANK LTD","NKGSB CO-OP BANK LTD","NORTH MALABAR GRAMIN BANK","MASHREQBANK PSC","MIZUHO CORPORATE BANK LTD","MUMBAI DISTRICT CENTRAL CO-OP. BANK LTD","NAGPUR NAGRIK SAHAKARI BANK LTD","PRIME CO OPERATIVE BANK LTD","PRATHAMA BANK","PUNJAB AND SIND BANK","PUNJAB AND MAHARASHTRA CO-OP BANK LTD","OMAN INTERNATIONAL BANK SAOG","NUTAN NAGARIK SAHAKARI BANK LTD","PARSIK JANATA SAHAKARI BANK LTD","ORIENTAL BANK OF COMMERCE","SBERBANK","RESERVE BANK OF INDIA","SHRI CHHATRAPATI RAJARSHI SHAHU URBAN CO-OP BANK LTD","SHINHAN BANK","RABOBANK INTERNATIONAL (CCRB)","PUNJAB NATIONAL BANK","RAJKOT NAGARIK SAHAKARI BANK LTD","RAJGURUNAGAR SAHAKARI BANK LTD","STATE BANK OF INDIA","STATE BANK OF MAURITIUS LTD","STATE BANK OF BIKANER AND JAIPUR","STATE BANK OF HYDERABAD","SOUTH INDIAN BANK","STANDARD CHARTERED BANK","SOCIETE GENERALE","SOLAPUR JANATA SAHKARI BANK LTD.SOLAPUR","THANE BHARAT SAHAKARI BANK LTD","THE A.P. MAHESH CO-OP URBAN BANK LTD","SYNDICATE BANK","TAMILNAD MERCANTILE BANK LTD","STATE BANK OF TRAVANCORE","SUMITOMO MITSUI BANKING CORPORATION","STATE BANK OF MYSORE","STATE BANK OF PATIALA","THE FEDERAL BANK LTD","THE DELHI STATE COOPERATIVE BANK LTD","THE COSMOS CO-OPERATIVE BANK LTD","THE BHARAT CO-OPERATIVE BANK (MUMBAI) LTD","THE BANK OF RAJASTHAN LTD","THE BANK OF NOVA SCOTIA","THE ANDHRA PRADESH STATE COOP BANK LTD","THE AHMEDABAD MERCANTILE CO-OPERATIVE BANK LTD","THE KANGRA CENTRAL CO-OPERATIVE BANK LTD","THE KALYAN JANATA SAHAKARI BANK LTD","THE KALUPUR COMMERCIAL CO. OP. BANK LTD","THE JAMMU AND KASHMIR BANK LTD","THE JALGAON PEOPLES CO-OP BANK","THE GUJARAT STATE CO-OPERATIVE BANK LTD","THE GREATER BOMBAY CO-OP. BANK LTD","THE GADCHIROLI DISTRICT CENTRAL COOPERATIVE BANK LTD","THE RATNAKAR BANK LTD","THE RAJASTHAN STATE COOPERATIVE BANK LTD","THE SAHEBRAO DESHMUKH CO-OP. BANK LTD","THE ROYAL BANK OF SCOTLAND N.V","THE SEVA VIKAS CO-OPERATIVE BANK LTD (SVB)","THE SARASWAT CO-OPERATIVE BANK LTD","THE SURAT DISTRICT CO OPERATIVE BANK LTD","THE SHAMRAO VITHAL CO-OPERATIVE BANK LTD","THE KARAD URBAN CO-OP BANK LTD","THE KANGRA COOPERATIVE BANK LTD","THE LAKSHMI VILAS BANK LTD","THE KARNATAKA STATE APEX COOP. BANK LTD","THE MUNICIPAL CO OPERATIVE BANK LTD MUMBAI","THE MEHSANA URBAN COOPERATIVE BANK LTD","THE NASIK MERCHANTS CO-OP BANK LTD., NASHIK","THE NAINITAL BANK LIMITED","TJSB SAHAKARI BANK LTD","TUMKUR GRAIN MERCHANTS COOPERATIVE BANK LTD","UBS AG","UCO BANK","UNION BANK OF INDIA","UNITED BANK OF INDIA","UNITED OVERSEAS BANK","VASAI VIKAS SAHAKARI BANK LTD","THE SURAT PEOPLES CO-OP BANK LTD","THE SUTEX CO.OP. BANK LTD","THE TAMILNADU STATE APEX COOPERATIVE BANK LIMITED","THE THANE DISTRICT CENTRAL CO-OP BANK LTD","THE THANE JANATA SAHAKARI BANK LTD","THE VARACHHA CO-OP. BANK LTD","THE VISHWESHWAR SAHAKARI BANK LTD.,PUNE","THE WEST BENGAL STATE COOPERATIVE BANK LTD","WOORI BANK","WESTPAC BANKING CORPORATION","WEST BENGAL STATE COOPERATIVE BANK","VIJAYA BANK","ZILA SAHAKRI BANK LIMITED GHAZI","YES BANK","ZILA SAHKARI BANK LTD GHAZIABAD","YES BANK LTD"];
		
		$scope.globalStateList = [];

		$scope.cbSubscriberAgreement = true;
		
		//permnt addr
		if($scope.data.permenentAddress == "true")
			$('#ptaddrs').toggle('slow');

	}
	
	
//SM changes redone
	$scope.experienceList =  [];
	var maxYears = 50;
	for(var i=0;i <= maxYears; i++){
		$scope.experienceList.push(i);
	}    
//SM changes redone
	
	
	$scope.onChangeCorCity = function(){
		console.log($scope.data.ccity);
	}

	$scope.getStateWiseCites = function(stateElement,element){
			var state = $(stateElement).val();
			state = state.replace("string:",'');
			var form = new FormData();
	        form.append("stateName", state);
			var settings = {
			"async": true,
	          "crossDomain": true,
	          "url": _gc_url_citylist,
	          "method": "POST",
	          "headers": {
	            "cache-control": "no-cache",
	            "Access-Control-Allow-Origin" : "*"
	          },
	          "processData": false,
	          "contentType": false,
	          "mimeType": "multipart/form-data",
	          "data": form
			}
			
			$.ajax(settings).done(function (res) {
				//console.log(res);
				res = JSON.parse(res);
				var selectCityOptions = res;
				$(element +' option').remove();
				$(element).append('<option value="" selected="selected">City</option>');
				$(element).each(function(){
		    		for(index in selectCityOptions) {
		    		    this.options[this.options.length] = new Option(selectCityOptions[index], selectCityOptions[index]);
		    		}
		    	});
			}).fail(function (response){
			alert(response);
			});
	}
	
	$scope.onChangeNation = function(){
		console.log($scope.data.nationality);
	}
	
	$scope.onChangePDedit = function(){
		console.log($scope.pdedit);
	}

	//corresponding addresss changes
	$scope.onChangeCProof = function(){
		if($scope.cproof != null){
			$scope.data.cproof = $scope.cproof.value;
			console.log($scope.data.cproof);	
		} else{
			$scope.data.cproof = "";
		}
		console.log($scope.data.permenentAddress);
	}

	//permanent addresss changes
	$scope.onChangePProof = function(){
		if($scope.pproof != null){
			$scope.data.pproof = $scope.pproof.value;
			console.log($scope.data.pproof);	
		} else{
			$scope.data.pproof = "";
		}
		console.log($scope.data.permenentAddress);
	}

	// on change occupation
	$scope.onChangeOccup = function(){
		console.log($scope.data.fstHldrOccup);

	}

	//onChangeNomineeExist
	$scope.onChangeNomineeExist = function(){
		console.log($scope.data.nomineeExist);		
	    if ($scope.data.nomineeExist ==  false){
	    	resetNomineeDetails();
	    	resetMinorDetails();
	    }


	}
	
	resetNomineeDetails = function(){
		$scope.data.nameNominee = "";
		$scope.data.nomineeRelation = "";
		$scope.data.nomineeDob = "";
		$scope.data.nomEmail = "";
		$scope.data.nomineeAdrs1 = "";
		$scope.data.nomineeAdrs2 = "";
		$scope.data.nomState = "";
		$scope.data.nomCity = "";
		$scope.data.nomineePincode = "";
		//$scope.data.nomCountry = "";
		$scope.data.nomMobile = "";
		$scope.data.nomineeProof = "";
		$scope.data.nominePan = "";
		$scope.data.nomineAadhar = "";
		$scope.data.minorExist =  false;
	}
	resetMinorDetails = function(){
		$scope.data.minorGuard = "";
		$scope.data.mnrReltn = "";
		$scope.data.mnrMob = "";
		$scope.data.mnrEmail = "";
		$scope.data.mnrAdrs1 = "";
		$scope.data.mnrAdrs2 = "";
		$scope.data.mnrState = "";
		$scope.data.mnrCity = "";
		$scope.data.mnrPincode = "";
		//$scope.data.mnrCountry = "";
		$scope.data.mnrProof = "";
		$scope.data.mnrPan = "";
		$scope.data.mnrAadhar = "";
		$scope.data.mnrDob = "";
	}

	//onChangeMinorExist
	$scope.onChangeMinorExist = function(){
		console.log($scope.data.minorExist);
		if($scope.data.minorExist == false){
			resetMinorDetails();
		}
	}
	
	//set state
    $scope.setSelectState = function(){
			
			var settings = {
			"async": true,
			"crossDomain": true,
			"url": _gc_url_statelist,
			"method": "POST",
			"headers": {
			"cache-control": "no-cache",
			"content-type": "application/x-www-form-urlencoded"
			},
			"processData": false,
			"contentType": false,
			"mimeType": "multipart/form-data"
			//"data": formData
			}
			
			$.ajax(settings).done(function (res) {
				//console.log(res);
				res = JSON.parse(res);
				var selectStateOptions = res;
				/*$('.selectState').each(function(){
		    		for(index in selectStateOptions) {
		    		    this.options[this.options.length] = new Option(selectStateOptions[index], selectStateOptions[index]);
		    		}
		    	});*/
				$scope.globalStateList = selectStateOptions;
				$scope.$apply();
			}).fail(function (response){
			alert(response);
			});
    	
    }

	initCarousel = function(){
		
		$scope.goNext =  function(){

	 		currentSlide = $("#myCarousel").find('.active').index();
	 		if (currentSlide == 0) {
	 			if($scope.validatePanDob()){
	 				$scope.autosave();
		 			$('#myCarousel').carousel(1);
 					currentSlide = 1;
	 			}
	 		} else if (currentSlide == 1) {
	 			if($scope.validatePersonalDetails()){
	 				$scope.autosave();
		 			$('#myCarousel').carousel(2);
		 			currentSlide = 2;
	 			}
	 		} else if (currentSlide == 2) {
	 			if($scope.validateAddressDetails()){
	 				$scope.autosave();
	 				$('#myCarousel').carousel(3);
	 				currentSlide = 3;
	 			}
	 		} else if (currentSlide == 3) {
	 			if($scope.validateBackgroundDetails()){
	 				$scope.autosave();
	 				$('#myCarousel').carousel(4);
	 				currentSlide = 4;
	 			}
	 		} else if (currentSlide == 4) {
	 			if ($scope.nomineeExistCheck()){
	 				$scope.autosave();//$scope.data.nomineeExist 
	 				if($scope.data.nomineeExist == true ){
			 			$('#myCarousel').carousel(5);
			 			currentSlide = 5;
		 			}
	 				else {
		 				$('#myCarousel').carousel(6);
		 				currentSlide = 6;
	 				}	
	 			}
	 		} else if (currentSlide == 5) {
	 			if ($scope.nomineeExistCheck() ){ //&& $scope.validateGuardian()
	 				$scope.autosave();
	 				$('#myCarousel').carousel(6);
	 				currentSlide = 6;	
	 			}
	 		} else if (currentSlide == 6) {
	 			if($scope.validateBankDetails()){
	 				$('#myCarousel').carousel(7);
	 				currentSlide = 7;
	 			}
	 		}
	 		$('html,body').scrollTop(0);
	 	
		}
		/*next btn logic*/ 
		$('.btnNext').on('click',function(){
			$scope.goNext();
		});
		
		
		$scope.goPrev = function(){

	 		currentSlide = $("#myCarousel").find('.active').index();
	 		if (currentSlide == 1) {
	 			$('#myCarousel').carousel(0);
	 			currentSlide = 0;
	 		} else if (currentSlide == 2) {
	 			$scope.autosave();
	 			$('#myCarousel').carousel(1);
	 			currentSlide = 1;
	 		} else if (currentSlide == 3) {
	 			$scope.autosave();
	 			$('#myCarousel').carousel(2);
	 			currentSlide = 2;
	 		} else if (currentSlide == 4) {
	 			$scope.autosave();
	 			$('#myCarousel').carousel(3);
	 			currentSlide = 3;
	 		} else if (currentSlide == 5) {
	 			$('#myCarousel').carousel(4);
	 			currentSlide = 4;
	 		} else if (currentSlide == 6) {
	 			$scope.autosave();
	 			if($scope.data.nomineeExist == true){
		 			$('#myCarousel').carousel(5);
		 			currentSlide = 5;
	 			}else {
		 			$('#myCarousel').carousel(4);
		 			currentSlide = 4;
	 			}
	 		} else if (currentSlide == 7) {
	 			$scope.autosave();
	 				$('#myCarousel').carousel(6);
	 				currentSlide = 6;
	 			}
	 		$('html,body').scrollTop(0);
	 	
		}
		
		/*previous btn logic*/
		$('.btnPrev').on('click',function(){
			$scope.goPrev();
		});
	}
	$(document).ready(function(){
		//set time out start
		setTimeout(function (){
			try{
	    	initCarousel();
		hideLoading();
			}
			catch(e){
		console.error(e);
			}	
		},1000);
		setTimeout(function (){
			
			/*//display user name
			displayUserName = function (){
	        	$("#displayOnlyUserName").text($("#userFirstName").val());
	        }
	    	displayUserName();*/
	    
			try{
	    	initCarousel();
			}
			catch(e){
		console.error(e);
			}	
	
			$(".alphaWithSpace").keypress(function (event) {
				var regex = new RegExp("^[a-zA-Z \b]+$");  // RegExp("^[a-zA-Z\b]+$");
				var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
				if (!regex.test(key)) {
				event.preventDefault();
				return false;
				}
			});
			$(".alphaNumericOnly").keypress(function (event) {
				var regex =new RegExp("^([a-zA-Z0-9\b])+$");// new RegExp("^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}+$");  // RegExp("^[a-zA-Z\b]+$");
				var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
				if (!regex.test(key)) {
				event.preventDefault();
				return false;
				}
			});

			$(".numericOnly").keypress(function (e) {
				if (String.fromCharCode(e.keyCode).match(/[^0-9]/g)) return false;
			});
			//cal_validity
			
			 $( function() {
		 		   // $( ".my_calendar" ).datepicker();
		 		  // $( ".my_calendar" ).datepicker( "option", "dateFormat", 'dd-mm-yy' );
		 		  $( ".my_calendar" ).datepicker({ 
		 		  	dateFormat: "dd-mm-yy" ,
				changeMonth: true,
				changeYear: true,
				yearRange: "-100:+0"	// last hundred years
				})
		 		  });
		 		  
	    	 $( function() {
	 		   // $( ".my_calendar" ).datepicker();
	 		  // $( ".my_calendar" ).datepicker( "option", "dateFormat", 'dd-mm-yy' );
	 		  $( ".cal_validity" ).datepicker({ 
	 		  	dateFormat: "dd-mm-yy" ,
				changeMonth: true,
				changeYear: true,
				yearRange: "0:+30",	// last hundred years
				minDate : 0
			})
	 		  });

	 		   
	 		   $('#permenentAddress').change(function () {	   
	 			    if (!this.checked) 
	 		        //  ^
	 		           $('#ptaddrs').toggle('slow');
	 		        else 
	 		            $('#ptaddrs').toggle('slow');
	 		    });

	 		   
	 		   	//set user name
	 		   	displayUserName = function (){
		        	$("#displayOnlyUserName").text($("#userFirstName").val());
		        }
		    	displayUserName();			


	 		  },100);
		//set time out end
	});
	
	
	//SM validation
	$scope.validatePanDob = function(){
//    	console.log('validating validatePanDob');
    	var userdob = $('#dob').val();
    	var userKycdobArray = userdob.split("-");
    	var userKycAge = getAge(userKycdobArray[2] + "-" + userKycdobArray[1] + "-" + userKycdobArray[0]);
    	var resultPan = validatePanNumber($('#kycpan'));
    	if($('#dob').val() == ''){
    		showToast('Please enter your Date of Birth');
    		return false;
    	}
    	else if(userKycAge < 18){
    		showToast('Applicant cannot be minor');
    		return false;
    	}
    	else if(!resultPan){
    		showToast('Please enter valid PAN.');
    		return false;
    	}
       	return true;
    }
    //SM validation

	// validation
	 $scope.validatePersonalDetails = function(){
//    	console.log('validating validatePersonalDetails');
    	var userdob = $('#dob').val();
    	var userdobArray = userdob.split("-");
    	var userAge = getAge(userdobArray[2] + "-" + userdobArray[1] + "-" + userdobArray[0]);
    	if($('#firstname').val() == 'First Name' || $('#firstname').val() == ''){
    		showToast('Please enter your First Name');
    		return false;
    	}
    	else if($('#lastname').val() == 'Last Name' || $('#lastname').val() == ''){
    		showToast('Please enter your Last Name');
    		return false;
    	}
    	else if($('#fatherSpouse').val() == 'Father/Spouse Name' || $('#fatherSpouse').val() == ''){
    		showToast('Please enter your Father/Spouse Name');
    		return false;
    	}
    	else if($('#motherName').val() == 'Mother Name' || $('#motherName').val() == ''){
    		showToast('Please enter your Mother Name');
    		return false;
    	}
    	else if($('#dob').val() == ''){
    		showToast('Please enter your Date of Birth');
    		return false;
    	}
    	else if(userAge < 18){
    		showToast('Applicant cannot be minor');
    		return false;
    	}
    	else if($('#gender').val() == ''){
    		showToast('Please enter your gender');
    		return false;
    	}
    	else if($('#mstatus').value == '' ){
    		console.log($('#mstatus').val());
    		// console.log($('#mstatus').val());
    		showToast('Please enter your Marital Status');
    		return false;
    	}
    	var resultPan = validatePanNumber($('#pan'));
    	if(!resultPan)
    		return false;
    	
    	return true;
    }

    //get age
    getAge =  function(DOB){
		    var today = new Date();
		    var birthDate = new Date(DOB);
		    var age = today.getFullYear() - birthDate.getFullYear();
		    var m = today.getMonth() - birthDate.getMonth();
		    if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
		        age--;
		    }    
		    return age;
		}

	//show toast
	 showToast = function(msg){
	 	var canShowToast = true;
    	if(canShowToast){
    		$.toast({
    		    heading: 'Error',
    		    text: msg,
    		    hideAfter: 4000,
    		    icon: 'error'
    		})
    	}
    	
    }

    //validate pan
         validatePanNumber = function(input){
     	//console.log(inpEmail.value);
      	var patt = new RegExp("[A-Z|a-z]{3}(p|P|c|C|h|H|f|F|a|A|t|T|b|B|l|L|j|J|g|G){1}[A-Z|a-z][0-9]{4}[A-Za-z]$");
     	     	var panValue = "";
      	if( input.value === undefined)
      		panValue = input.val();
      	else 
      		panValue = input.value;
      	var res = patt.test(panValue);
     	if(res) {
     		var form = new FormData();
     		form.append("type", "panNumber");
     		form.append("panNumber", panValue);
     		var formData = "type=panNumber"+
     						"&panNumber="+panValue;
     		var settings = {
     		  "async": true,
     		  "crossDomain": true,
     		  "url": _gc_url_home_post_validateData,
     		  "method": "POST",
     		  "headers": {
     		    "cache-control": "no-cache",
     			"content-type": "application/x-www-form-urlencoded"
     		  },
     		  "processData": false,
     		  "contentType": false,
     		  "mimeType": "multipart/form-data",
     		  "data": formData
     		}
     		if( input.value === undefined) {
     			settings.async = false;
     			var result = false;
     			$.when($.ajax(settings)).done(function (res) {
// 	    			console.log(res);
 	    			res = JSON.parse(res);
 	    		 	if(res.status==true){
 	    		 		$.toast({
 	    				    heading: 'Error',
 	    				    text: res.errorMsg,
 	    				    showHideTransition: 'fade',
 	    				    icon: 'error'
 	    				});
     		 			result = false;
     		 		} else {
     		 			result = true;
     		 		}
     			}).fail(function (response){
     				alert(response);
     				result = false;
     			});	
     			return result;
     		} else {
     			$.ajax(settings).done(function (res) {
 	    			console.log(res);
 	    			res = JSON.parse(res);
 	    		 	if(res.status==true){
 	    		 		$.toast({
 	    				    heading: 'Error',
 	    				    text: res.errorMsg,
 	    				    showHideTransition: 'fade',
 	    				    icon: 'error'
 	    				});
     		 		return false;
     		 		} else {
     		 		return true;
     		 		}
     			}).fail(function (response){
     				alert(response);
     				return false;
     			});	
     		}
     		
     	} else {
     		$.toast({
 			    heading: 'Error',
 			    text: 'Please enter valid PAN number',
 			    showHideTransition: 'fade',
 			    icon: 'error'
 			});
     		return false;
     	}

    }

    //address validation
    $scope.validateAddressDetails = function (){
    	
    	if($('#cproof').val() == ''){
    		showToast('Please select proof of address');
    		return false;
    	}
    	else if($('#caddressline1').val() == 'Address line 1' || $('#caddressline1').val() == ''){
    		showToast('Please enter your address');
    		return false;
    	}
    	else if($('#cstate').val() == '' || $('#cstate').val() == 'Select'){
    		showToast('Please select your state');
    		return false;
    	}
    	else if($('#ccity').val() == '' || $('#ccity').val() == null || $('#cstate').val() == 'Select'){
    		showToast('Please select your city');
    		return false;
    	}
    	else if($('#cpincode').val() == 'PIN code'){
    		showToast('Please enter Pin Code');
    		return false;
    	}
    	else if($('#cpincode').val().length != 6){
    		showToast('Invalid pincode');
    		return false;
    	}
    	else if($('#mobile').val() == 'Mobile Number' || $('#mobile').val()== ''){
    		showToast('Please enter your mobile number');
    		return false;
    	}
    	else if ($('#mobile').val().length != 10){
    		showToast('Please enter valid mobile number');
    		return false;
    	}
    	else if($('#email').val() == 'Email' || $('#email').val()== ''){
    		showToast('Please enter your Email Id');
    		return false;
    	}
    	else if(!$scope.isValidEmailAddress($scope.data.email)){
    		showToast('Invalid Email in correspondence address.');
			return false;
    	}
    	else if($('#permenentAddress').is(':checked')){
    		if($('#paddressline1').val() == 'Address line 1' || $('#paddressline1').val()== ''){
        		showToast('Please enter your Permanant Address');
        		return false;
        	}
    		else if($('#ppin').val() == 'PIN code' || $('#ppin').val()== ''){
        		showToast('Please enter Pin Code for your Permanant Address ');
        		return false;
        	}
    		else if($('#ppin').val().length < 6){
        		showToast('Invalid pincode in permanant Address');
        		return false;
        	}
    		else if($('#pstate').val() == 'Select' || $('#pstate').val()== ''){
        		showToast('Please select State for your Permanant Address ');
        		return false;
        	}
    		else if($('#pcity').val() == 'Select' || $('#pcity').val()== '' || $('#pcity').val() == null){
        		showToast('Please select City for your Permanant Address ');
        		return false;
        	}
    		else if($('#pproof').val() == 'Select' || $('#pproof').val()== ''){
        		showToast('Please select Address Proof for your Permanant Address ');
        		return false;
        	}
    	}
    	return true;
    	
    }

    // valid email address
    $scope.isValidEmailAddress = function(emailAddress) {
		    var pattern = /^([a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+(\.[a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+)*|"((([ \t]*\r\n)?[ \t]+)?([\x01-\x08\x0b\x0c\x0e-\x1f\x7f\x21\x23-\x5b\x5d-\x7e\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|\\[\x01-\x09\x0b\x0c\x0d-\x7f\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))*(([ \t]*\r\n)?[ \t]+)?")@(([a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.)+([a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.?$/i;
		    return pattern.test(emailAddress);
		};
		
	// validate background deatils
	$scope.validateBackgroundDetails = function(){
    	if($('#fstHldrOccup').val() == 'Select' || $('#fstHldrOccup').val()== ''){
    		showToast('Please enter your occupation');
    		return false;
    	}
    	else{
    		if($('#fstHldrOccup').val() == 'Student' || $('#fstHldrOccup').val() == 'Retired' || $('#fstHldrOccup').val() == 'Housewife' || $('#fstHldrOccup').val() == 'Agriculturist'){
    			
    		}else{
    			if($('#fstHldrOrg').val() == 'Name of your Employer/Company' || $('#fstHldrOrg').val()== ''){
    	    		showToast('Please enter your Employer/Company name');
    	    		return false;
    	    	}
    			else if($('#fstHldrDesig').val() == 'Designation' || $('#fstHldrDesig').val()== ''){
    	    		showToast('Please enter your Designation');
    	    		return false;
    	    	}
    		}
    	}
    	
    	if($('#fstHldrIncome').val() == 'Select' || $('#fstHldrIncome').val()== ''){
    		
    		if($('#fstHldrAmt').val() == 'Networth' || $('#fstHldrAmt').val()== ''){
    			showToast('Please enter Income details or Networth');
        		return false;
    		}
    		
    	}
    	if($('#experience').val() == 'Number of years of investment/Trading Experience' || $('#experience').val()== ''){
    		showToast('Please enter Number of years of investment/Trading Experience');
    		return false;
    	}
    	
    	/*if($('#nomineeExist').is(':checked')){
    		//validation for nomiee
    		var result  = validateNomination();
    		if(!result)
    			return false;
    	}
    	if ($("#minorExist").is(':checked')){
    		//validation for minor
    		var result =  validateGuardian();
    		if(!result)
    			return false;
    	}*/
    	return true;
    }

    //nomiee exist	
    $scope.nomineeExistCheck = function(){
    	if($scope.data.nomineeExist){
    		//validation for nomiee
    		var result  = $scope.validateNomination();
    		if(!result)
    			return false;
    	}
    	return true;
    }

    //nomiee validation
    $scope.validateNomination = function() {
    	
    	if($('#nameNominee').val() == 'Name of Nominee' || $('#nameNominee').val() == ''){
    		showToast('Please enter the name of nomination');
    		return false;
    	}
    	else if($('#nomineeRelation').val() == 'Relationship' || $('#nomineeRelation').val() == ''){
    		showToast('Please enter the relationship with applicant.');
    		return false;
    	}
    	else if($('#nomineeDob').val() == '' ){
    		showToast('Please enter the DOB of nominee.');
    		return false;    		
    	}
    	else if ($('#nomEmail').val() == 'Email' || $('#nomEmail').val() == '') {
			showToast('Please enter the Email  for nomiee.');
			return false;
		}
    	else if (!$scope.isValidEmailAddress($('#nomEmail').val())){
    		showToast('Invalid Email for nominee.');
			return false;
    	}
    	else if($('#nomineeAdrs1').val() == 'Address Line1' || $('#nomineeAdrs1').val() == ''){
    		showToast('Please enter the nominee address');
    		return false;
    	}
    	else if($('#nomState').val() == 'Select' || $('#nomState').val() == ''){
    		showToast('Please enter the State for nominee\'s address.');
    		return false;
    	}
    	else if($('#nomCity').val() == 'Select' || $('#nomCity').val() == '' || $('#nomCity').val() == null){
    		showToast('Please enter the City for nominee\'s address.');
    		return false;
    	}   
    	else if($('#nomineePincode').val().length < 6){
    		showToast('Invalid pincode in Nominee section');
    		return false;
    	}
    	else if($('#nomCountry').val() == 'Select' || $('#nomCountry').val() == ''){
    		showToast('Please enter the country for nominee\'s address.');
    		return false;
    	}
    	if ($('#nomMobile').val() == 'Mobile' || $('#nomMobile').val() == '') {
			showToast('Please enter the Mobile Number for Nomiee');
			return false;
		}
    	else if ($('#nomMobile').val().length != 10){
			showToast('Please enter the valid Mobile Number for Nomiee');
			return false;
    	}
    	else if($('#nomineeProof').val() == 'Select' || $('#nomineeProof').val() == ''){
    		showToast('Please select Proof of Identification for nominee.');
    		return false;
    	}
    	if($('#nomineeProof').val() == 'PAN'){
    		if ($('#nominePan').val() == 'PAN Number' || $('#nominePan').val() == '') {
    			showToast('Please enter the PAN Number of Nominee.');
    			return false;
    		}else if(!$scope.isValidPANNumber($('#nominePan').val())){
    			showToast('Invalid PAN Number of Nominee.');
    			return false;
    		}
    	}
    	if($('#nomineeProof').val() == 'Aadhar'){
    		if ($('#nomineAadhar').val() == 'UID/Aadhaar Number' || $('#nomineAadhar').val() == '') {
    			showToast('Please enter the Aadhar Number of Nominee');
    			return false;
    		}
    	}
    	
    	
    	return true;
    }

    //pan valaid regex
    $scope.isValidPANNumber = function (panNo) {
			var pattern = /[A-Z|a-z]{3}(p|P|c|C|h|H|f|F|a|A|t|T|b|B|l|L|j|J|g|G){1}[A-Z|a-z][0-9]{4}[A-Za-z]$/;
			return pattern.test(panNo);
		}

	//minor Exist Check
	$scope.minorExistCheck = function(){
		if ($scope.data.minorExist){
    		//validation for minor
    		var result =  $scope.validateGuardian();
    		if(!result)
    			return false;
    	}
    	return true;			
	}


	$scope.validateGuardian = function () {

    	var userdob = $('#nomineemnrDob').val();
    	var userdobArray = userdob.split("-");
    	var userAge = getAge(userdobArray[2] + "-" + userdobArray[1] + "-" + userdobArray[0]);
    	if($('#minorGuard').val() == 'Name of Guardian' || $('#minorGuard').val() == '') {
    		showToast('Please enter the Name of Guardian.');
    		return false;
    	} else if($('#mnrReltn').val() == 'Relationship with Guardian' || $('#mnrReltn').val() == '') {
    		showToast('Plese enter the Relationship with nominee.');
    		return false;
    	}
    	else if($('#mnrMob').val() == 'Mobile' || $('#mnrMob').val() == '') {
			showToast('Please enter the mobile number of Guardian.');
    		return false;
		}
		else if ($('#mnrMob').val().length != 10){
			showToast('Please enter the valid Mobile Number for Guardian');
			return false;
    	}
    	else if($('#mnrEmail').val() == 'Email' || $('#mnrEmail').val() == '') {
			showToast('Please enter the Email  of Guardian.');
    		return false;
		}
		else if (!$scope.isValidEmailAddress($('#mnrEmail').val())){
    		showToast('Invalid Email  for Guardian.');
			return false;
    	}
		else if($('#mnrAdrs1').val() == 'Address Line1' || $('#mnrAdrs1').val() == '') {
			showToast('Please enter the address of Guardian.');
    		return false;
		}
		else if($('#mnrState').val() == 'Select' || $('#mnrState').val() == '') {
			showToast('Please enter the State for Guardian\'s Address.');
    		return false;
		}
		else if($('#mnrCity').val() == '' || $('#mnrCity').val() == '' || $('#mnrCity').val() == null) {
			showToast('Please enter the City for Guardian\'s Address.');
    		return false;
		}
		else if($('#mnrPincode').val() == 'Pin Code' || $('#mnrPincode').val() == '') {
			showToast('Please enter the Guardian\'s pincode.');
    		return false;
		}
		else if($('#mnrPincode').val().length < 6){
    		showToast('Invalid pincode in Guardian section');
    		return false;
    	}
		else if($('#mnrCountry').val() == 'Select' || $('#mnrCountry').val() == '') {
			showToast('Please enter the country for Guardian\'s Address.');
    		return false;
		}
		else if($('#mnrProof').val() == 'Select' || $('#mnrProof').val() == ''){
    		showToast('Please select Proof of Identification for Guardian.');
    		return false;
    	}
    	else if( $('#nomineemnrDob').val() == 'Select' || $('#nomineemnrDob').val() == '' ){
    		showToast('Please select Date of birth of Guadian.');
    		return false;
    	}else if(userAge < 18 ){
    		showToast('Guadian cannot be a minor.');
    		return false;
    	}
		if($('#mnrProof').val() == 'PAN'){
    		if ($('#mnrPan').val() == 'PAN Number' || $('#mnrPan').val() == '') {
    			showToast('Please enter the PAN Number of Guardian.');
    			return false;
    		}else if(!$scope.isValidPANNumber($('#mnrPan').val())){
    			showToast('Invalid PAN Number of Guardian.');
    			return false;
    		}
    	}
    	if($('#mnrProof').val() == 'Aadhar'){
    		if ($('#mnrAadhar').val() == 'UID/Aadhaar Number' || $('#mnrAadhar').val() == '') {
    			showToast('Please enter the Aadhar Number of Guardian');
    			return false;
    		}
    	}
		
		
		return true;
    }	
	
	//validate bank details
	$scope.validateBankDetails = function (){
    	//var patt = new RegExp("[A-Z|a-z]{4}[0][0-9]{6}$");
		var patt = new RegExp("[A-Z|a-z]{4}[0][a-zA-Z0-9]{6}$");
   	 	var res = patt.test($('#bifsc').val());
    	if($('#bankname').val() == 'Select' || $('#bankname').val()== ''){
    		showToast('Please enter your Bank Name');
    		return false;
    	}
    	else if($('#accno').val() == 'Bank Account Number' || $('#accno').val()== ''){
    		showToast('Please enter your Bank Account Number');
    		return false;
    	}
    	else if($('#reAccno').val() == 'Re enter Bank Account Number' || $('#reAccno').val()== ''){
    		showToast('Please enter your Re enter Bank Account Number');
    		return false;
    	}
    	else if($('#bifsc').val() == 'IFSC No.' || $('#bifsc').val()== ''){
    		showToast('Please enter IFSC No.');
    		return false;
    	}
    	else if (!res){
    		showToast('Invalid IFSC No.');
    		return false;
    	}
    	else if($('#bmicr').val() == 'MICR No.' || $('#bmicr').val()== ''){
    		showToast('Please enter MICR No.');
    		return false;
    	}
    	else if($('#bmicr').val().length < 9){
    		showToast('Invalid MICR No.');
    		return false;
    	}
    	else if($('#baddressline1').val() == 'Address line 1' || $('#baddressline1').val()== ''){
    		showToast('Please enter Address line 1');
    		return false;
    	}
    	else if($('#bstate').val() == 'Select' || $('#bstate').val()== ''){
    		showToast('Please enter State');
    		return false;
    	}
    	else if($('#bcity').val() == 'Select' || $('#bcity').val()== '' ||  $('#bcity').val()== null){
    		showToast('Please enter City');
    		return false;
    	}
    	else if($('#bpincode').val().length < 6){
    		showToast('Invalid pincode in Bank section');
    		return false;
    	}
    	
    	 
    	return true;
    }
    //validate re account number
    reValidateAccno = function(reAccno) {
    	 if($('#reAccno').val() != $('#accno').val()){
     		showToast('Bank Account Number not matched.');
     		return false;
     	}
     }

     //validate ifsc
     validateIFSC = function(ifsc) {
    	 //var patt = new RegExp("[A-Z|a-z]{4}[0][0-9]{6}$");
    	 var patt = new RegExp("[A-Z|a-z]{4}[0][a-zA-Z0-9]{6}$");
    	 var res = patt.test(ifsc.value);
    	 if(!res){
    		 $.toast({
				    heading: 'Error',
				    text: "Invalid IFSC Code.",
				    showHideTransition: 'fade',
				    icon: 'error'
				});
    	 }
     }

     //final submit
     $scope.finalSubmit = function(){
    	if(!$scope.validatePersonalDetails()){
    		$('#myCarousel').carousel(1);
    		return;
    	}
    	if(!$scope.validateAddressDetails()){
    		$('#myCarousel').carousel(2);
    		return;
    	}
    	if(!$scope.validateBackgroundDetails()){
    		$('#myCarousel').carousel(3);
    		return;
    	}
    	if(!$scope.validateBankDetails()){
    		$('#myCarousel').carousel(6);
    		return;
    	}
    	if(!$scope.validateAgreement()){
    		return;
    	}
    	showLoading();
    	clevertap.event.push("Account Opening Form Submitted");
    	/*canShowToast = false;
	
		canShowToast = true;
		FINAL_SUBMIT = true;*/
		$scope.makePostJson();
		console.log($scope.data);
		
    	console.log('final save ......');
    	/*
    	$('#frmFinalFormSubmit').attr('action', _gc_url_ir_post_finalSave);
    	$('#frmFinalFormSubmit').submit();*/
    	
    }

    //make final json
    $scope.makePostJson = function(){
    	$scope.data.regId = null;
    	$scope.data.clandmark = "";
    	$scope.data.plandmark = "";
    	$scope.data.histd = "";
    	$scope.data.hstd = "";
    	$scope.data.risd = "";
    	$scope.data.rstd = "";
    	$scope.data.rtelephone = "";
    	$scope.data.fisd = "";
    	$scope.data.fstd = "";
    	$scope.data.ftelphone = "";
    	$scope.data.openAccountType = "Trading Account and NSDL Demat Account";
    	$scope.data.relationship = "Self";
    	$scope.data.smsFacility = "";
    	$scope.data.dp ="";
    	$scope.data.tradingtAccount ="";
    	$scope.data.dematAccount = "";
    	$scope.data.scndHldrExist = false;
    	$scope.data.scndHldrName = "";
    	$scope.data.scndHldrOccup = "";
    	$scope.data.scndHldrOrg = "";
    	$scope.data.scndHldrDesig = "";
    	$scope.data.scndHldrSms = "";
    	$scope.data.scndHldrIncome = "";
    	$scope.data.scndHldrNet = "";
    	$scope.data.scndHldrAmt = "";
    	$scope.data.scndPep = "";
    	$scope.data.scndRpep = "";
    	$scope.data.instrn1 = "";
    	$scope.data.instrn2 = "";
    	$scope.data.instrn3 = "";
    	$scope.data.instrn4 = "";
    	$scope.data.instrn5 = "";
    	$scope.data.depoPartcpnt = "";
    	$scope.data.deponame = "";
    	//$scope.data.beneficiary = "";
    	$scope.data.dpId = "";
    	$scope.data.docEvdnc = "";
    	$scope.data.contractNote = "";
    	$scope.data.intrntTrading = "";
    	$scope.data.alert = "Both";
    	$scope.data.otherInformation = "";
    	$scope.data.noisd = "";
    	$scope.data.nostd = "";
    	$scope.data.notelephone = "";
    	$scope.data.nrisd = "";
    	$scope.data.nrstd = "";
    	$scope.data.nRtelephone = "";
    	$scope.data.nfisd = "";
    	$scope.data.nfstd = "";
    	$scope.data.nomineeFax = "";
    	$scope.data.moisd = "";
    	$scope.data.mostd = "";
    	$scope.data.motel = "";
    	$scope.data.mrisd = "";
    	$scope.data.mrstd = "";
    	$scope.data.mrtel = "";
    	$scope.data.mfisd = "";
    	$scope.data.mfstd = "";
    	$scope.data.minorfax = "";
    	$scope.data.nomCityOther = "";
    	$scope.data.mnrCityOther = "";
    	$scope.data.docFilePath = "";
    	$scope.data.usNational = "";
    	$scope.data.usResident = "";
    	$scope.data.usBorn = "";
    	$scope.data.usAddress = "";
    	$scope.data.usTelephone = "";
    	$scope.data.usStandingInstruction = "";
    	$scope.data.usPoa = "";
    	$scope.data.usMailAddress = "";
    	$scope.data.individualTaxIdntfcnNmbr = "";
    	$scope.data.secondHldrPan = "";
    	$scope.data.secondHldrDependentRelation = "";
    	$scope.data.secondHldrDependentUsed = "";
    	$scope.data.firstHldrDependentUsed = "Both";
    }	

    $scope.validateAgreement = function(){
    	if($scope.cbSubscriberAgreement == false){
    		showToast('Please accept the Agreement at bottom of page.');
    		return false;
    	}
    	return true;
    }
    

    
    //generic code for file upload
    $scope.uploadFileToServer = function(filePath,url,callbackFunction){
		var form = new FormData();
		form.append("file", filePath);
		form.append("email",$scope.data.email);
		//form.append("regId", "123");

		var settings = {
		  "async": true,
		  "crossDomain": true,
		  "url": url,
		  "method": "POST",
		  "headers": {
		    "cache-control": "no-cache",
		    "Access-Control-Allow-Origin" : "*"
		  },
		  "processData": false,
		  "contentType": false,
		  "mimeType": "multipart/form-data",
		  "data": form
		}

		$.ajax(settings).done(function (response) {
			console.log(response);
			if(response.indexOf("error")==-1)
				callbackFunction(response);
			else {
				showToast(response);
			}
		});
	}


    //check is device is camera or desktop 
    //if mobile open mobile camera
    //if desktop open scriptcam camera
    $scope.onLabelClick = function(type){
    	$scope.isMobile =(/android|webos|iphone|ipad|ipod|blackberry|iemobile|opera mini/i.test(navigator.userAgent.toLowerCase()));
    	if(type == 'personal'){
    		if ($scope.isMobile){
    			//show mobile camera
    				setTimeout(function(){ $('#panFilePath').click();}, 100);
    		} else {
    			//show scriptcam
    			$scope.scriptcamsrc = 'pd';
    			$('#pdmyModal').modal('show');
    		}
    	} else if (type == "coraddr") {
    		if ($scope.isMobile){
    			//show mobile camera
    				setTimeout(function(){ $('#corsFilePath').click();}, 100);
    		} else {
    			//show scriptcam
    			$scope.scriptcamsrc = 'cad';
    			$('#pdmyModal').modal('show');
    		}
    	} else if (type == "peraddr") {
    		if ($scope.isMobile){
    			//show mobile camera
    				setTimeout(function(){ $('#prmntFilePath').click();}, 100);
    		} else {
    			//show scriptcam
    			$scope.scriptcamsrc = 'pad';
    			$('#pdmyModal').modal('show');
    		}
    	} else if (type == "bank") {
    		if ($scope.isMobile){
    			//show mobile camera
    				setTimeout(function(){ $('#bankFilePath').click();}, 100);
    		} else {
    			//show scriptcam
    			$scope.scriptcamsrc = 'bd';
    			$('#pdmyModal').modal('show');
    		}
    	}
    	
    }
    
    //file upload code for id proof
	$scope.uploadPanCard = function(src){
		var fileSize ;
		var file;
		if (src == 'camicon') {
			fileSize = $('#panFilePath')[0].files[0].size;
			file = $('#panFilePath')[0].files[0];
		} else if (src == 'uploadicon') {
			fileSize = $('#panFilePathFU')[0].files[0].size;
			file = $('#panFilePathFU')[0].files[0];
		}
    	//alert(fileSize);
    	if (fileSize <= $scope.MAX_FILE_SIZE){
        	$scope.uploadFileToServer(
        			file,
        			_gc_url_ir_fileUpload_pan,
        			function(responseData){
        				//sPanFilePath = responseData;
        				if (responseData.indexOf("error")== -1) {
        					$('#panFilePathMsg').text('File Uploaded Successfully.');
            				$('#panFilePathMsg').css('color','green');
            				console.log('upload success');
            				console.log(responseData);
            				//alert('file upload success');
            				$('#pdmyModal').modal('hide');
            				$scope.data.panFilePath = responseData;
            				$scope.$apply();
        				} else {
        					$('#panFilePathMsg').text('File Uploaded Failed. Please Try Again.');
            				$('#panFilePathMsg').css('color','red');
            				alert('file upload failed.!\n Please try again.');
        				}
        				//uploadUID();
        			}
        	);
    	} else {
    		alert('failed');
    		showToast('File size greater than 2 MB.')
    	}

    }
    
    //script cam code for id proof
    $scope.uploadPanCardScriptCam = function(){
    	var fileSize = $('#pdimage').attr("src").length;
    	//alert(fileSize);
    	if (fileSize <= $scope.MAX_FILE_SIZE){
        	$scope.uploadFileToServer(
        			$('#pdimage').attr("src"),
        			_gc_url_ir_fileUpload_id_url,
        			function(responseData){
        				//sPanFilePath = responseData;
        				if (responseData.indexOf("error")== -1) {
        					$('#panFilePathMsg').text('File Uploaded Successfully.');
            				$('#panFilePathMsg').css('color','green');
            				console.log('upload success');
            				console.log(responseData);
            				//alert('file upload success');
            				$('#pdmyModal').modal('hide');
            				$scope.data.panFilePath = responseData;
            				$scope.$apply();
        				} else {
        					$('#panFilePathMsg').text('File Uploaded Failed. Please Try Again.');
            				$('#panFilePathMsg').css('color','red');
            				alert('file upload failed.!\n Please try again.');
        				}
        				//uploadUID();
        			}
        	);
    	} else {
    		alert('failed');
    		showToast('File size greater than 10 MB.')
    	}

    }
    
    //file upload code for cor addr
    $scope.uploadCorsAdress = function(src){
    	var fileSize;
    	var file;
    	if (src == 'camicon') {
			fileSize = $('#corsFilePath')[0].files[0].size;
			file = $('#corsFilePath')[0].files[0];
		} else if (src == 'uploadicon') {
			fileSize = $('#corsFilePathFU')[0].files[0].size;
			file = $('#corsFilePathFU')[0].files[0];
		}
    	//var fileSize = $('#corsFilePath')[0].files[0].size;
    	if (fileSize <= $scope.MAX_FILE_SIZE){
        	$scope.uploadFileToServer(
        			//$('#corsFilePath')[0].files[0],
        			file,
        			_gc_url_ir_fileUpload_coresAddrs,
        			function(responseData){
        				sCorsFilePath = responseData;
        				//uploadPrmntAdress();
        				if (responseData.indexOf("error")== -1) {
            				$('#corsFilePathMsg').text('File Uploaded Successfully.');
            				$('#corsFilePathMsg').css('color','green');
            				console.log('upload success');
            				console.log(responseData);
            				//alert('file upload success');
            				$('#pdmyModal').modal('hide');
            				$scope.data.corsFilePath = responseData;
            				$scope.$apply();
        				} else {
        					$('#corsFilePathMsg').text('File Uploaded Failed. Please Try Again.');
            				$('#corsFilePathMsg').css('color','red');
            				alert('file upload failed.!\n Please try again.');
        				}
        			}
        	);
    	} else {
    		showToast('File size greater than 10 MB.')
    	}

    }
    
    //script cam code for cor addr
    $scope.uploadCorsAdressScriptCam = function(){
    	var fileSize = $('#cadimage').attr("src").length;
    	//alert(fileSize);
    	if (fileSize <= $scope.MAX_FILE_SIZE){
        	$scope.uploadFileToServer(
        			$('#cadimage').attr("src"),
        			_gc_url_ir_fileUpload_cor_addr_url,
        			function(responseData){
        				//sPanFilePath = responseData;
        				if (responseData.indexOf("error")== -1) {
        					$('#panFilePathMsg').text('File Uploaded Successfully.');
            				$('#panFilePathMsg').css('color','green');
            				console.log('upload success');
            				console.log(responseData);
            				//alert('file upload success');
            				$('#pdmyModal').modal('hide');
            				$scope.data.corsFilePath = responseData;
            				$scope.$apply();
        				} else {
        					$('#panFilePathMsg').text('File Uploaded Failed. Please Try Again.');
            				$('#panFilePathMsg').css('color','red');
            				alert('file upload failed.!\n Please try again.');
        				}
        				//uploadUID();
        			}
        	);
    	} else {
    		alert('failed');
    		showToast('File size greater than 10 MB.')
    	}

    }
    
    //file upload code for permnt addr
    $scope.uploadPrmntAdress = function(src){
    	var fileSize;
    	var file;
    	if (src == 'camicon') {
			fileSize = $('#prmntFilePath')[0].files[0].size;
			file = $('#prmntFilePath')[0].files[0];
		} else if (src == 'uploadicon') {
			fileSize = $('#prmntFilePathFU')[0].files[0].size;
			file = $('#prmntFilePathFU')[0].files[0];
		}
    	//var fileSize = $('#prmntFilePath')[0].files[0].size;
    	if (fileSize <= $scope.MAX_FILE_SIZE){
        	$scope.uploadFileToServer(
        			//$('#prmntFilePath')[0].files[0],
        			file,
        			_gc_url_ir_fileUpload_permAdrs,
        			function(responseData){
        			    sPrmntFilePath = responseData;
        				//makePostJson();
        			    if (responseData.indexOf("error")== -1) {
            				$('#prmntFilePathMsg').text('File Uploaded Successfully.');
            				$('#prmntFilePathMsg').css('color','green');
            				console.log('upload success');
            				console.log(responseData);
            				//alert('file upload success');
            				$('#pdmyModal').modal('hide');
            				$scope.data.prmntFilePath = responseData;
            				$scope.$apply();
        				} else {
        					$('#prmntFilePathMsg').text('File Uploaded Failed. Please Try Again.');
            				$('#prmntFilePathMsg').css('color','red');
            				alert('file upload failed.!\n Please try again.');
        				}
        			}
        	);
    	} else {
    		showToast('File size greater than 10 MB.')
    	}

    }
    
    //scriptcam code for permnt addr
    $scope.uploadPrmntAdressScriptCam = function(){
    	var fileSize = $('#padimage').attr("src").length;
    	//alert(fileSize);
    	if (fileSize <= $scope.MAX_FILE_SIZE){
        	$scope.uploadFileToServer(
        			$('#padimage').attr("src"),
        			_gc_url_ir_fileUpload_perm_addr_url,
        			function(responseData){
        				//sPanFilePath = responseData;
        				if (responseData.indexOf("error")== -1) {
        					$('#panFilePathMsg').text('File Uploaded Successfully.');
            				$('#panFilePathMsg').css('color','green');
            				console.log('upload success');
            				console.log(responseData);
            				//alert('file upload success');
            				$('#pdmyModal').modal('hide');
            				$scope.data.prmntFilePath = responseData;
            				$scope.$apply();
        				} else {
        					$('#panFilePathMsg').text('File Uploaded Failed. Please Try Again.');
            				$('#panFilePathMsg').css('color','red');
            				alert('file upload failed.!\n Please try again.');
        				}
        				//uploadUID();
        			}
        	);
    	} else {
    		alert('failed');
    		showToast('File size greater than 10 MB.')
    	}

    }
    
    //file upload code for bank
    $scope.uploadBankProof = function(src){
    	var fileSize;
    	var file;
    	if (src == 'camicon') {
			fileSize = $('#bankFilePath')[0].files[0].size;
			file = $('#bankFilePath')[0].files[0];
		} else if (src == 'uploadicon') {
			fileSize = $('#bankFilePathFU')[0].files[0].size;
			file = $('#bankFilePathFU')[0].files[0];
		}
    	//var fileSize = $('#prmntFilePath')[0].files[0].size;
    	if (fileSize <= $scope.MAX_FILE_SIZE){
        	$scope.uploadFileToServer(
        			//$('#prmntFilePath')[0].files[0],
        			file,
        			_gc_url_ir_fileUpload_bank,
        			function(responseData){
        			    sPrmntFilePath = responseData;
        				//makePostJson();
        			    if (responseData.indexOf("error")== -1) {
            				$('#prmntFilePathMsg').text('File Uploaded Successfully.');
            				$('#prmntFilePathMsg').css('color','green');
            				console.log('upload success');
            				console.log(responseData);
            				//alert('file upload success');
            				$('#pdmyModal').modal('hide');
            				$scope.data.bankFilePath = responseData;
            				$scope.$apply();
        				} else {
        					$('#prmntFilePathMsg').text('File Uploaded Failed. Please Try Again.');
            				$('#prmntFilePathMsg').css('color','red');
            				alert('file upload failed.!\n Please try again.');
        				}
        			}
        	);
    	} else {
    		showToast('File size greater than 10 MB.')
    	}

    }
    
    //scriptcam code for bank
    $scope.uploadBankProofScriptCam = function(){
    	var fileSize = $('#bdimage').attr("src").length;
    	//alert(fileSize);
    	if (fileSize <= $scope.MAX_FILE_SIZE){
        	$scope.uploadFileToServer(
        			$('#bdimage').attr("src"),
        			_gc_url_ir_fileUpload_bank_url,
        			function(responseData){
        				//sPanFilePath = responseData;
        				if (responseData.indexOf("error")== -1) {
        					$('#panFilePathMsg').text('File Uploaded Successfully.');
            				$('#panFilePathMsg').css('color','green');
            				console.log('upload success');
            				console.log(responseData);
            				//alert('file upload success');
            				$('#pdmyModal').modal('hide');
            				$scope.data.bankFilePath = responseData;
            				$scope.$apply();
        				} else {
        					$('#panFilePathMsg').text('File Uploaded Failed. Please Try Again.');
            				$('#panFilePathMsg').css('color','red');
            				alert('file upload failed.!\n Please try again.');
        				}
        				//uploadUID();
        			}
        	);
    	} else {
    		alert('failed');
    		showToast('File size greater than 10 MB.')
    	}

    }
    
    
    //prefill user data
    $scope.initPrefill =  function(){
		$.ajax({
			type: "POST",
			url: _gc_url_ir_get_userDetails,
			data: null,
			dataType: 'json',
			contentType:"application/json; charset=utf-8",
			success: function(data){
				try{
					//data = JSON.parse(data);
					var tempData = data.toString();
					if(tempData.indexOf("ERROR")== -1){
						data = data;
						//prefillData(data);
						$scope.data = data;
						$scope.preparePage();
						$scope.$apply();
						console.log(data);
						console.log($scope.data);
						$('html,body').scrollTop(0);
					}
					else{
						$('html,body').scrollTop(0);
						console.log("first time user registring");
					}
					
				}
				catch(e){
					$('html,body').scrollTop(0);
					$('#divContent').css("display","block");
					$('#divLoading').css("display","none");
				}
			},
			error: function(){
				console.log('error');
			},
			fail: function(){
				console.log('fail');
			}
		});
		$('html,body').scrollTop(0);
	}
    
    //submit userform
    $scope.submitForm = function(){
    	
    	if(!$scope.validatePersonalDetails()){
    		$('#myCarousel').carousel(1);
    		return;
    	}
    	if(!$scope.validatePanDob()){
    		$('#myCarousel').carousel(0);
    		return;
    	}
    	if(!$scope.validateAddressDetails()){
    		$('#myCarousel').carousel(2);
    		return;
    	}
    	if(!$scope.validateBackgroundDetails()){
    		$('#myCarousel').carousel(3);
    		return;
    	}
    	
    	if($scope.data.nomineeExist){
    		if(!$scope.validateNomination()){
        		$('#myCarousel').carousel(4);
        		return;
        	}
    	}
    	if($scope.data.minorExist == true){
    		if(!$scope.validateGuardian()){
        		$('#myCarousel').carousel(5);
        		return;
        	}
    	}
    	
    	if(!$scope.validateBankDetails()){
    		$('#myCarousel').carousel(6);
    		return;
    	}
    	if(!$scope.validateAgreement()){
    		return;
    	}
    	showLoading();
    	clevertap.event.push("Account Opening Form Submitted");
    	/*canShowToast = false;
	
		canShowToast = true;
		FINAL_SUBMIT = true;*/
		//$scope.makePostJson();
		console.log($scope.data);
		
    	//console.log('final save ......')
    	
    	showLoading();
    	
    	$.ajax({
			  type: "POST",
			  url: _gc_url_ir_post_autoSave,
			  data: JSON.stringify($scope.data),
			  dataType: 'json',
			  contentType:"application/json; charset=utf-8",
			  success: function(data){
				  //console.log('autosaved.');
				  console.log(data);
				  /*if(FINAL_SUBMIT){
					  console.log("doing submit");
				    	$('#frmFinalFormSubmit').attr('action', _gc_url_ir_post_finalSave);
				    	$('#frmFinalFormSubmit').submit();
				  }*/
				  
				  //final submit start
				  $.ajax({
					  type: "POST",
					  url: _gc_url_ir_post_finalSave,
					  data: $scope.data,
					  dataType: 'json',
					  contentType:"application/json; charset=utf-8",
					  success: function(data){
						  //console.log('autosaved.');
						  console.log(data);
						  $('#myCarousel').carousel(7);
						  hideLoading();
					  },
						error: function(){
							console.log('error');
							hideLoading();
						},
						fail: function(){
							console.log('fail');
							hideLoading();
						}
				});
				  //final submit end
			  },
			error: function(){
				console.log('error');
				hideLoading();
			},
			fail: function(){
				console.log('fail');
				hideLoading();
			}
    	
		});
    }
    
    //autosave
    $scope.autosave = function(){
    	$.ajax({
			  type: "POST",
			  url: _gc_url_ir_post_autoSave,
			  data: JSON.stringify($scope.data),
			  dataType: 'json',
			  contentType:"application/json; charset=utf-8",
			  success: function(data){
				  //console.log('autosaved.');
				 // console.log(data);
			  }
		});
    }
    
    //get pdf
    $scope.getUserFormPdf = function(){
    	var settings = {
    			  "async": true,
    			  "crossDomain": true,
    			  "url": _gc_url_fetch_reg_id,
    			  "method": "POST",
    			  "headers": {
    			    "cache-control": "no-cache",
    			    "postman-token": "3f58b7bb-cb50-0bc9-1c87-450fc9005806"
    			  }
    			}
    			
    			$.ajax(settings).done(function (data) {
    					//$('#myFrame').attr('src',_gc_url_reg_pdf + data +'.pdf');
    				window.open(_gc_url_reg_pdf + data +'.pdf','_blank');
    			}); 
    }
    
    //confirm
    $scope.confirm = function(){
    	if(!$scope.validateAgreement()){
    		return;
    	}
    	showLoading();
    	clevertap.event.push('PDF Confirmed');
    	$('#confirmUserPdf').attr('action',_gc_url_reg_pdf_post_view);
    	$('#confirmUserPdf').submit();
    }


	$scope.fetchBankDetails = function(){
		console.log('IFSC');
		//Set Default 
		$scope.bank = {};

		$scope.data.baddressline1 = "";
		$scope.data.baddressline2 = "";
		$scope.data.bankname = "";

		var ifsc=$scope.data.bifsc;   		
		$.ajax({
			type: "GET",
			url: _gc_url_getBankDetailsUsingIFSC, 
			data:"ifscCode="+ifsc, 
			success: function(result){
				if(result != ""){
					try{
						result = JSON.parse(result);						
						$scope.bank=result;						
						var limit = $scope.getPosition($scope.bank.ADDRESS,',',2);				
						$scope.data.baddressline1=$scope.bank.ADDRESS.substring(0,limit);
						$scope.data.baddressline2=$scope.bank.ADDRESS.substring(limit+1);				
						$scope.data.bankname=$scope.bank.BANK.toUpperCase();
						$scope.data.bstate=$scope.toTitleCase($scope.bank.STATE);				
						$scope.data.bcity=$scope.toTitleCase($scope.bank.CITY);	
						$scope.$apply();
					}catch(e){
						console.log(e);
					} 
				} 
			},
			error: function (xhr, errorText) {
			console.log('Error ' + xhr.responseText);
			} 
		});
	} 

	$scope.toTitleCase= function(str){
	    return str.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();});
	}

	$scope.getPosition = function(string, subString, index) {
 	  return string.split(subString, index).join(subString).length;
	}

}]);
