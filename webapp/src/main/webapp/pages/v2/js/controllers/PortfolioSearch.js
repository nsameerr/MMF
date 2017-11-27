app.controller('PortfolioSearch', ['$scope', function($scope) {
	$scope.page = {
			title: 'Portfolia Search'
	};
	$scope.selectCityOptions = ["Abhayapuri","Achabbal","Achhnera","Adalaj","Adari","Adilabad","Adityana","Adoni","Adoor","Adra, Purulia","Agartala","Agra","Ahiwara","Ahmedabad","Ahmedgarh","Ahmednagar","Aizawl","Ajmer","Akaltara","Akathiyoor","Akhnoor","Akola","Alang","Alappuzha","Aldona","Aligarh","Alipurduar","Allahabad","Almora","Along","Alwar","Amadalavalasa","Amalapuram","Amarpur","Ambagarh Chowki","Ambaji","Ambala","Ambaliyasan","Ambikapur","Amguri","Amlabad","Amli","Amravati","Amreli","Amritsar","Amroha","Anakapalle","Anand","Anandapur","Anandnagaar","Anantapur","Anantnag","Ancharakandy","Andada","Anjar","Anklav","Ankleshwar","Antaliya","Anugul","Ara","Arakkonam","Arambagh","Arambhada","Arang","Araria","Arasikere","Arcot","Areraj","Arki","Arnia","Aroor","Arrah","Aruppukkottai","Asankhurd","Asansol","Asarganj","Ashok Nagar","Ashtamichira","Asika","Asola","Assandh","Ateli","Attingal","Atul","Aurangabad","Avinissery","Awantipora","Azamgarh","Babiyal","Baddi","Bade Bacheli","Badepalle","Badharghat","Bagaha","Bahadurganj","Bahadurgarh","Baharampur","Bahraich","Bairgania","Bakhtiarpur","Balaghat","Balangir","Balasore","Baleshwar","Bali","Ballabhgarh","Ballia","Bally","Balod","Baloda Bazar","Balrampur","Balurghat","Bamra","Banda","Bandikui","Bandipore","Bangalore","Banganapalle","Banka","Bankura","Banmankhi Bazar","Banswara","Bapatla","Barahiya","Barakar","Baramati","Baramula","Baran","Barasat","Barauli","Barbigha","Barbil","Bardhaman","Bareilly","Bargarh","Barh","Baripada","Barmer","Barnala","Barpeta","Barpeta Road","Barughutu","Barwala","Basudebpur","Batala","Bathinda","Bazpur","Begusarai","Behea","Belgaum","Bellampalle","Bellary","Belpahar","Bemetra","Bethamcherla","Bettiah","Betul","Bhabua","Bhadrachalam","Bhadrak","Bhagalpur","Bhagha Purana","Bhainsa","Bharuch","Bhatapara","Bhavani","Bhavnagar","Bhawanipatna","Bheemunipatnam","Bhimavaram","Bhiwani","Bhongir","Bhopal","Bhuban","Bhubaneswar","Bhuj","Bidhan Nagar","Bihar Sharif","Bikaner","Bikramganj","Bilasipara","Bilaspur","Biramitrapur","Birgaon","Bobbili","Bodh Gaya","Bodhan","Bokaro Steel City","Bomdila","Bongaigaon","Brahmapur","Brajrajnagar","Budhlada","Burhanpur","Buxar","Byasanagar","Calcutta","Cambay","Chaibasa","Chakradharpur","Chalakudy","Chalisgaon","Chamba","Champa","Champhai","Chamrajnagar","Chandan Bara","Chandausi","Chandigarh","Chandrapura","Changanassery","Chanpatia","Charkhi Dadri","Chatra","Cheeka","Chendamangalam","Chengalpattu","Chengannur","Chennai","Cherthala","Cheruthazham","Chhapra","Chhatarpur","Chikkaballapur","Chikmagalur","Chilakaluripet","Chinchani","Chinna salem","Chinsura","Chintamani","Chirala","Chirkunda","Chirmiri","Chitradurga","Chittoor","Chittur-Thathamangalam","Chockli","Churi","Coimbatore","Colgong","Contai","Cooch Behar","Coonoor","Cuddalore","Cuddapah","Curchorem Cacora","Cuttack","Dabra","Dadri","Dahod","Dalhousie","Dalli-Rajhara","Dalsinghsarai","Daltonganj","Daman and Diu","Darbhanga","Darjeeling","Dasua","Datia","Daudnagar","Davanagere","Debagarh","Deesa","Dehradun","Dehri-on-Sone","Delhi","Deoghar","Deoria","Devarakonda","Devgarh","Dewas","Dhaka","Dhamtari","Dhanbad","Dhar","Dharampur, India","Dharamsala","Dharmanagar","Dharmapuri","Dharmavaram","Dharwad","Dhekiajuli","Dhenkanal","Dholka","Dhubri","Dhule","Dhuri","Dibrugarh","Digboi","Dighwara","Dimapur","Dinanagar","Dindigul","Diphu","Dipka","Dispur","Dombivli","Dongargarh","Dumka","Dumraon","Durg-Bhilai Nagar","Durgapur","Ellenabad 2","Eluru","Erattupetta","Erode","Etawah","Faridabad","Faridkot","Farooqnagar","Fatehabad","Fatehpur","Fatwah","Fazilka","Firozpur","Firozpur Cantt.","Forbesganj","Gadag","Gadwal","Ganaur","Gandhinagar","Gangtok","Garhwa","Gauripur","Gaya","Gharaunda","Ghatshila","Giddarbaha","Giridih","Goalpara","Gobindgarh","Gobranawapara","Godda","Godhra","Gogri Jamalpur","Gohana","Golaghat","Gomoh","Gooty","Gopalganj","Greater Noida","Gudalur","Gudivada","Gudur","Gulbarga","Gumia","Gumla","Gundlupet","Guntakal","Guntur","Gunupur","Gurdaspur","Gurgaon","Guruvayoor","Guwahati","Gwalior","Haflong","Haibat(Yamuna Nagar)","Hailakandi","Hajipur","Haldia","Haldwani","Hamirpur","Hansi","Hanuman Junction","Hardoi","Haridwar","Hassan","Hazaribag","Hilsa","Himatnagar","Hindupur","Hinjilicut","Hisar","Hisua","Hodal","Hojai","Hoshiarpur","Hospet","Howrah","Hubli","Hussainabad","Hyderabad","Ichalkaranji","Ichchapuram","Idar","Imphal","Indore","Indranagar","Irinjalakuda","Islampur","Itanagar","Itarsi","Jabalpur","Jagatsinghapur","Jagdalpur","Jagdispur","Jaggaiahpet","Jagraon","Jagtial","Jaipur","Jaisalmer","Jaitu","Jajapur","Jajmau","Jalalabad","Jalandhar","Jalandhar Cantt.","Jaleswar","Jalna","Jalore","Jamalpur","Jammalamadugu","Jammu","Jamnagar","Jamshedpur","Jamtara","Jamui","Jandiala","Jangaon","Janjgir","Jashpurnagar","Jaspur","Jatani","Jaunpur","Jehanabad","Jeypur","Jhajha","Jhajjar","Jhanjharpur","Jhansi","Jhargram","Jharsuguda","Jhumri Tilaiya","Jind","Joda","Jodhpur","Jogabani","Jogendranagar","Jorhat","Jowai","Junagadh","Kadapa","Kadi","Kadiri","Kadirur","Kagaznagar","Kailasahar","Kaithal","Kakching","Kakinada","Kalan Wali","Kalavad","Kalka","Kalliasseri","Kalol","Kalpetta","Kalpi","Kalyan","Kalyandurg","Kamareddy","Kanchipuram","Kandukur","Kanhangad","Kanjikkuzhi","Kanker","Kannur","Kanpur","Kantabanji","Kanti","Kapadvanj","Kapurthala","Karaikal","Karaikudi","Karanjia","Karimganj","Karimnagar","Karjan","Karkala","Karnal","Karoran","Kartarpur","Karungal","Karur","Karwar","Kasaragod","Kashipur","Kathua","Katihar","Katni","Kavali","Kavaratti","Kawardha","Kayamkulam","Kendrapara","Kendujhar","Keshod","Khagaria","Khambhalia","Khambhat","Khammam","Khanna","Kharagpur","Kharar","Kheda","Khedbrahma","Kheralu","Khordha","Khowai","Khunti","kichha","Kishanganj","Kochi","Kodinar","Kodungallur","Kohima","Kokrajhar","Kolar","Kolhapur","Kolkata","Kollam","Kollankodu","Kondagaon","Koothuparamba","Koraput","Koratla","Korba","Kot Kapura","Kota","Kotdwara","Kothagudem","Kothamangalam","Kothapeta","Kotma","Kottayam","Kovvur","Kozhikode","Kunnamkulam","Kurali","Kurnool","Kyathampalle","Lachhmangarh","Ladnu","Ladwa","Lahar","Laharpur","Lakheri","Lakhimpur","Lakhisarai","Lakshmeshwar","Lal Gopalganj Nindaura","Lalganj","Lalgudi","Lalitpur","Lalsot","Lanka","Lar","Lathi","Latur","Leh","Lilong","Limbdi","Lingsugur","Loha","Lohardaga","Lonar","Lonavla","Longowal","Loni","Losal","Lucknow","Ludhiana","Lumding","Lunawada","Lundi","Lunglei","Macherla","Machilipatnam","Madanapalle","Maddur","Madgaon","Madhepura","Madhubani","Madhugiri","Madhupur","Madikeri","Madurai","Magadi","Mahad","Mahalingpur","Maharajganj","Maharajpur","Mahasamund","Mahbubnagar","Mahe","Mahendragarh","Mahesana","Mahidpur","Mahnar Bazar","Mahuli","Mahuva","Maihar","Mainaguri","Makhdumpur","Makrana","Mal","Malajkhand","Malappuram","Malavalli","Malegaon","Malerkotla","Malkangiri","Malkapur","Malout","Malpura","Malur","Manasa","Manavadar","Manawar","Manchar","Mancherial","Mandalgarh","Mandamarri","Mandapeta","Mandawa","Mandi","Mandi Dabwali","Mandideep","Mandla","Mandsaur","Mandvi","Mandya","Maner","Mangaldoi","Mangalore","Mangalvedhe","Manglaur","Mangrol","Mangrulpir","Manihari","Manjlegaon","Mankachar","Manmad","Mansa","Manuguru","Manvi","Manwath","Mapusa","Margao","Margherita","Marhaura","Mariani","Marigaon","Markapur","Marmagao","Masaurhi","Mathabhanga","Mathura","Mattannur","Mauganj","Maur","Mavelikkara","Mavoor","Mayang Imphal","Medak","Medinipur","Meerut","Mehkar","Mehmedabad","Memari","Merta City","Mhaswad","Mhow Cantonment","Mhowgaon","Mihijam","Miraj","Mirganj","Miryalaguda","Modasa","Modinagar","Moga","Mogalthur","Mohali","Mokameh","Mokokchung","Monoharpur","Morena","Morinda","Morshi","Morvi","Motihari","Motipur","Mount Abu","Mudalgi","Mudbidri","Muddebihal","Mudhol","Mukerian","Mukhed","Muktsar","Mul","Mulbagal","Multai","Mumbai","Mundargi","Mungeli","Munger","Muradnagar","Murliganj","Murshidabad","Murtijapur","Murwara","Musabani","Mussoorie","Muvattupuzha","Muzaffarnagar","Muzaffarpur","Mysore","Nabadwip","Nabarangapur","Nabha","Nadbai","Nadiad","Nagaon","Nagapattinam","Nagar","Nagari","Nagarkurnool","Nagaur","Nagda","Nagercoil","Nagina","Nagla","Nagpur","Nahan","Naharlagun","Naihati","Naila Janjgir","Nainital","Nainpur","Najibabad","Nakodar","Nakur","Nalasopara","Nalbari","Namagiripettai","Namakkal","Nanded-Waghala","Nandgaon","Nandivaram-Guduvancheri","Nandura","Nandurbar","Nandyal","Nangal","Nanjangud","Nanjikottai","Nanpara","Narasapur","Narasaraopet","Naraura","Narayanpet","Nargund","Narkatiaganj","Narkhed","Narnaul","Narsinghgarh","Narsipatnam","Narwana","Nashik","Nasirabad","Natham","Nathdwara","Naugachhia","Naugawan Sadat","Nautanwa","Navalgund","Navi Mumbai","Navsari","Nawabganj","Nawada","Nawalgarh","Nawanshahr","Nawapur","Nedumangad","Neem-Ka-Thana","Neemuch","Nehtaur","Nelamangala","Nellikuppam","Nellore","Nepanagar","Neyveli","Neyyattinkara","Nidadavole","Nilanga","Nimbahera","Nipani","Nirmal","Niwai","Niwari","Nizamabad","Nohar","NOIDA","Nokha","Nongstoin","Noorpur","North Lakhimpur","Nowgong","Nowrozabad","Nuzvid","O Valley","Obra","Oddanchatram","Ongole","Orai","Osmanabad","Ottappalam","Ozar","P.N.Patti","Pachora","Pachore","Pacode","Padmanabhapuram","Padra","Padrauna","Paithan","Pakaur","Palacole","Palai","Palakkad","Palani","Palanpur","Palasa Kasibugga","Palghar","Pali","Palia Kalan","Palitana","Palladam","Pallapatti","Pallikonda","Palwal","Palwancha","Panagar","Panagudi","Panaji","Panchkula","Panchla","Pandharkaoda","Pandharpur","Pandhurna","Pandua","Panipat","Panna","Panniyannur","Panruti","Panvel","Pappinisseri","Paradip","Paramakudi","Parangipettai","Parasi","Paravoor","Parbhani","Pardi","Parlakhemundi","Parli","Parola","Partur","Parvathipuram","Pasan","Paschim Punropara","Pasighat","Patan","Pathanamthitta","Pathankot","Pathardi","Pathri","Patiala","Patna","Patran","Patratu","Pattamundai","Patti","Pattukkottai","Patur","Pauni","Pauri","Pavagada","Payyannur","Pedana","Peddapuram","Pehowa","Pen","Perambalur","Peravurani","Peringathur","Perinthalmanna","Periyakulam","Periyasemur","Pernampattu","Perumbavoor","Petlad","Phagwara","Phalodi","Phaltan","Phillaur","Phulabani","Phulera","Phulpur","Phusro","Pihani","Pilani","Pilibanga","Pilibhit","Pilkhuwa","Pindwara","Pinjore","Pipar City","Pipariya","Piro","Pithampur","Pithapuram","Pithoragarh","Pollachi","Polur","Pondicherry","Ponduru","Ponnani","Ponneri","Ponnur","Porbandar","Porsa","Port Blair","Powayan","Prantij","Pratapgarh","Prithvipur","Proddatur","Pudukkottai","Pudupattinam","Pukhrayan","Pulgaon","Puliyankudi","Punalur","Punch","Pune","Punganur","Punjaipugalur","Puranpur","Puri","Purna","Purnia","Purquazi","Purulia","Purwa","Pusad","Puttur","Qadian","Quilandy","Rabkavi Banhatti","Radhanpur","Rae Bareli","Rafiganj","Raghogarh-Vijaypur","Raghunathpur","Rahatgarh","Rahuri","Raichur","Raiganj","Raigarh","Raikot","Raipur","Rairangpur","Raisen","Raisinghnagar","Rajagangapur","Rajahmundry","Rajakhera","Rajaldesar","Rajam","Rajampet","Rajapalayam","Rajauri","Rajgarh","Rajgarh (Alwar)","Rajgarh (Churu","Rajgir","Rajkot","Rajnandgaon","Rajpipla","Rajpura","Rajsamand","Rajula","Rajura","Ramachandrapuram","Ramagundam","Ramanagaram","Ramanathapuram","Ramdurg","Rameshwaram","Ramganj Mandi","Ramnagar","Ramngarh","Rampur","Rampur Maniharan","Rampura Phul","Rampurhat","Ramtek","Ranaghat","Ranavav","Ranchi","Rangia","Rania","Ranibennur","Rapar","Rasipuram","Rasra","Ratangarh","Rath","Ratia","Ratlam","Ratnagiri","Rau","Raurkela","Raver","Rawatbhata","Rawatsar","Raxaul Bazar","Rayachoti","Rayadurg","Rayagada","Reengus","Rehli","Renigunta","Renukoot","Reoti","Repalle","Revelganj","Rewa","Rewari","Rishikesh","Risod","Robertsganj","Robertson Pet","Rohtak","Ron","Roorkee","Rosera","Rudauli","Rudrapur","Rupnagar","Sabalgarh","Sadabad","Sadalgi","Sadasivpet","Sadri","Sadulshahar","Safidon","Safipur","Sagar","Sagwara","Saharanpur","Saharsa","Sahaspur","Sahaswan","Sahawar","Sahibganj","Sahjanwa","Saidpur, Ghazipur","Saiha","Sailu","Sainthia","Sakleshpur","Sakti","Salaya","Salem","Salur","Samalkha","Samalkot","Samana","Samastipur","Sambalpur","Sambhal","Sambhar","Samdhan","Samthar","Sanand","Sanawad","Sanchore","Sandi","Sandila","Sandur","Sangamner","Sangareddy","Sangaria","Sangli","Sangole","Sangrur","Sankarankoil","Sankari","Sankeshwar","Santipur","Sarangpur","Sardarshahar","Sardhana","Sarni","Sasaram","Sasvad","Satana","Satara","Sathyamangalam","Satna","Sattenapalle","Sattur","Saunda","Saundatti-Yellamma","Sausar","Savanur","Savarkundla","Savner","Sawai Madhopur","Sawantwadi","Sedam","Sehore","Sendhwa","Seohara","Seoni","Seoni-Malwa","Shahabad","Shahabad, Hardoi","Shahabad, Rampur","Shahade","Shahbad","Shahdol","Shahganj","Shahjahanpur","Shahpur","Shahpura","Shajapur","Shamgarh","Shamli","Shamsabad, Agra","Shamsabad, Farrukhabad","Shegaon","Sheikhpura","Shendurjana","Shenkottai","Sheoganj","Sheohar","Sheopur","Sherghati","Sherkot","Shiggaon","Shikapur","Shikarpur, Bulandshahr","Shikohabad","Shillong","Shimla","Shimoga","Shirdi","Shirpur-Warwade","Shirur","Shishgarh","Shivpuri","Sholavandan","Sholingur","Shoranur","Shorapur","Shrigonda","Shrirampur","Shrirangapattana","Shujalpur","Siana","Sibsagar","Siddipet","Sidhi","Sidhpur","Sidlaghatta","Sihor","Sihora","Sikanderpur","Sikandra Rao","Sikandrabad","Sikar","Silao","Silapathar","Silchar","Siliguri","Sillod","Silvassa","Simdega","Sindgi","Sindhnur","Singapur","Singrauli","Sinnar","Sira","Sircilla","Sirhind Fatehgarh Sahib","Sirkali","Sirohi","Sironj","Sirsa","Sirsaganj","Sirsi","Siruguppa","Sitamarhi","Sitapur","Sitarganj","Sivaganga","Sivagiri","Sivakasi","Siwan","Sohagpur","Sohna","Sojat","Solan","Solapur","Sonamukhi","Sonepur","Songadh","Sonipat","Sopore","Soro","Soron","Soyagaon","Sri Madhopur","Srikakulam","Srikalahasti","Srinagar","Srinivaspur","Srirampore","Srivilliputhur","Suar","Sugauli","Sujangarh","Sujanpur","Sultanganj","Sultanpur","Sumerpur","Sunabeda","Sunam","Sundargarh","Sundarnagar","Supaul","Surandai","Surat","Suratgarh","Suri","Suriyampalayam","Suryapet","Tadepalligudem","Tadpatri","Taki","Talaja","Talcher","Talegaon Dabhade","Talikota","Taliparamba","Talode","Talwara","Tamluk","Tanda","Tandur","Tanuku","Tarakeswar","Tarana","Taranagar","Taraori","Tarikere","Tarn Taran","Tasgaon","Tehri","Tekkalakota","Tenali","Tenkasi","Tenu Dam-cum- Kathhara","Terdal","Tetri Bazar","Tezpur","Thakurdwara","Thammampatti","Thana Bhawan","Thanesar","Thangadh","Thanjavur","Tharad","Tharamangalam","Tharangambadi","Theni Allinagaram","Thirumangalam","Thirunindravur","Thiruparappu","Thirupuvanam","Thiruthuraipoondi","Thiruvalla","Thiruvallur","Thiruvananthapuram","Thiruvarur","Thodupuzha","Thoothukudi","Thoubal","Thrissur","Thuraiyur","Tikamgarh","Tilda Newra","Tilhar","Tindivanam","Tinsukia","Tiptur","Tirora","Tiruchendur","Tiruchengode","Tiruchirappalli","Tirukalukundram","Tirukkoyilur","Tirunelveli","Tirupathur","Tirupati","Tiruppur","Tirur","Tiruttani","Tiruvannamalai","Tiruvethipuram","Tirwaganj","Titlagarh","Tittakudi","Todabhim","Todaraisingh","Tohana","Tonk","Tuensang","Tuljapur","Tulsipur","Tumkur","Tumsar","Tundla","Tuni","Tura","Uchgaon","Udaipur","Udaipurwati","Udgir","Udhagamandalam","Udhampur","Udumalaipettai","Udupi","Ujhani","Ujjain","Umarga","Umaria","Umarkhed","Umarkote","Umbergaon","Umred","Umreth","Una","Unjha","Unnamalaikadai","Unnao","Upleta","Uran","Uran Islampur","Uravakonda","Urmar Tanda","Usilampatti","Uthamapalayam","Uthiramerur","Utraula","Vadakara","Vadakkuvalliyur","Vadalur","Vadgaon Kasba","Vadipatti","Vadnagar","Vadodara","Vaijapur","Vaikom","Valparai","Valsad","Vandavasi","Vaniyambadi","Vapi","Varanasi","Varkala","Vasai","Vedaranyam","Vellakoil","Vellore","Venkatagiri","Veraval","Vicarabad","Vidisha","Vijainagar","Vijapur","Vijayapura","Vijayawada","Vikramasingapuram","Viluppuram","Vinukonda","Viramgam","Virar","Virudhachalam","Virudhunagar","Visakhapatnam","Visnagar","Viswanatham","Vita","Vizianagaram","Vrindavan","Vyara","Wadgaon Road","Wadhwan","Wadi","Wai","Wanaparthy","Wani","Wankaner","Wara Seoni","Warangal","Wardha","Warhapur","Warisaliganj","Warora","Warud","Washim","Wokha","Yadgir","Yamunanagar","Yanam","Yavatmal","Yawal","Yellandu","Yemmiganur","Yerraguntla","Yevla","Zahirabad","Zaidpur","Zamania","Zira","Zirakpur","Zunheboto"];
	$scope.filtered =  false;
	$scope.fetchActionFilter =( $('#userUnavailable').val() == 'true') ? true : false;
	$scope.filterResult = function() {
		//console.log(x);
		var temp = [];
		var dataSet = $scope.portfolioDataMaster;
		/************	rp - riskprofile type			***************/
		
		var rp1 = $('#rp1').is(':checked');	//Conservative
		var rp2 = $('#rp2').is(':checked');	//Moderately Conservative 
		var rp3 = $('#rp3').is(':checked');	//Moderate
		var rp4 = $('#rp4').is(':checked');	//Moderately Aggressive
		var rp5 = $('#rp5').is(':checked');	//Aggressive 
		var rp6 = $('#rp6').is(':checked');	//Custom

		if(rp1){
			$scope.filtered =  true;
			temp =  temp.concat(dataSet.filter(function (port) { return (port.riskType == 'Conservative')}));
		}
		if(rp2){
			$scope.filtered =  true;
			temp =  temp.concat(dataSet.filter(function (port) { return (port.riskType == 'Moderately Conservative')}));
		}
		if(rp3){
			$scope.filtered =  true;
			temp =  temp.concat(dataSet.filter(function (port) { return (port.riskType == 'Moderate')}));
		}
		if(rp4){
			$scope.filtered =  true;
			temp =  temp.concat(dataSet.filter(function (port) { return (port.riskType == 'Moderately Aggressive')}));
		}
		if(rp5){
			$scope.filtered =  true;
			temp =  temp.concat(dataSet.filter(function (port) { return (port.riskType == 'Aggressive')}));
		}
		if(rp6){
			$scope.filtered =  true;
			temp =  temp.concat(dataSet.filter(function (port) { return (port.riskType == 'Custom')}));
		}
		if(temp.length != 0 || rp1 || rp2 || rp3 || rp4 || rp5 || rp6) {
			dataSet = temp; temp = []; //pass filter result to next filter
		}
		
		/************		min investment		***************/
		var selectMinInvest = $("#selectMinInvest").find(":selected").text();
		if(selectMinInvest != "" && selectMinInvest != null && selectMinInvest != "select"){
			//console.log(selectMinInvest);
			var lessThan = true;
			var limitAmt = 0;
			if (selectMinInvest == 'Rs 1,00,000 Plus'){
				lessThan = false;
				limitAmt = 100000;
			}
			else if (selectMinInvest == 'Rs 50,000 to Rs 99,999'){
				lessThan = true;
				limitAmt = 99999;
			}
			else if (selectMinInvest == 'Rs 25,000 to Rs 49,999'){
				lessThan = true;
				limitAmt = 49999;
			}
			else if (selectMinInvest == 'Rs 10,000 to Rs 24,999'){
				lessThan = true;
				limitAmt = 24999;
			}
			else if (selectMinInvest == 'Rs 5,000 to Rs 9,999'){
				lessThan = true;
				limitAmt = 9999;
			}
			else if (selectMinInvest == 'Rs 1,000 to Rs 4,999'){
				lessThan = true;
				limitAmt = 4999;
			}

			$scope.filtered =  true;
			if (lessThan){
				temp =  temp.concat(dataSet.filter(function (port) { return (port.minInvest <= limitAmt)}));	
			} else {
				temp =  temp.concat(dataSet.filter(function (port) { return (port.minInvest >= limitAmt)}));
			}
			
		}
		if(temp.length != 0 || (selectMinInvest != "" && selectMinInvest != null && selectMinInvest != "select")) {
			dataSet = temp; temp = []; //pass filter result to next filter
		}

		/************		city		***************/
		var selectedCity = $('#cityFilter').find(":selected").text();
		if(selectedCity != "" && selectedCity != null && selectedCity != "Select City"){
			$scope.filtered =  true;
			temp =  temp.concat(dataSet.filter(function (port) { return (port.city == selectedCity)}));
		}
		if(temp.length != 0 || (selectedCity != "" && selectedCity != null && selectedCity != "Select City")) {
			dataSet = temp; temp = []; //pass filter result to next filter
		}
		
			
		/************		rating		***************/	
		var ratingValue = $('#divRatings').raty('score');
		if (ratingValue !=0 && typeof lastname !== "undefined") {
			$scope.filtered =  true;
			temp =  temp.concat(dataSet.filter(function (port) { return (port.advisorCustRating <= ratingValue)}));
		}
		if(temp.length != 0 || (ratingValue !=0 && typeof lastname !== "undefined")) {
			dataSet = temp; temp = []; //pass filter result to next filter
		}
		
			
		/************		at - advisor type - individual or corporate	***************/
		var at1 = $('#at1').is(":checked"); // 'individual' true
		var at2 = $('#at2').is(":checked"); // 'corporate'  false
		if(at1){
			$scope.filtered =  true;
			temp =  temp.concat(dataSet.filter(function (port) { return (port.indvOrCprt == true)}));
		}
		if(at2){
			$scope.filtered =  true;
			temp =  temp.concat(dataSet.filter(function (port) { return (port.indvOrCprt == false)}));
		}
		if(temp.length != 0 || at1 || at2) {
			dataSet = temp; temp = []; //pass filter result to next filter
		}
		
		
		/************		at - advisor type - robo or human	***************/
		var atr1 = $('#atr1').is(":checked"); // Human
		var atr2 = $('#atr2').is(":checked"); // Robo 
		if(atr1){
			$scope.filtered =  true;
			temp =  temp.concat(dataSet.filter(function (port) { return (port.isRoboAdvisor == false)}));
		}
		if(atr2){
			$scope.filtered =  true;
			temp =  temp.concat(dataSet.filter(function (port) { return (port.isRoboAdvisor == true)}));
		}
		if(temp.length != 0 || atr1 || atr2) {
			dataSet = temp; temp = []; //pass filter result to next filter
		}
		

		/************		pt - portfolio type		***************/
		
		var pt1 = $('#pt1').is(":checked"); // 'mutual funds'
		var pt2 = $('#pt2').is(":checked"); // 'equatity'
		var pt3 = $('#pt3').is(":checked"); // 'hybrid'
		
		if (pt1){
			$scope.filtered =  true;
			temp =  temp.concat(dataSet.filter(function (port) { return (port.portfolioType == 'Mutual Funds')})); 
		}
		if (pt2){
			$scope.filtered =  true;
			temp =  temp.concat(dataSet.filter(function (port) { return (port.portfolioType == 'Equatity')})); 
		}
		if (pt3){
			$scope.filtered =  true;
			temp =  temp.concat(dataSet.filter(function (port) { return (port.portfolioType == 'Hybrid')})); 
		}
		
		if(temp.length != 0 || pt1 || pt2 || pt3) {
			dataSet = temp; temp = []; //pass filter result to next filter
		}
		
		
		if($scope.filtered){
			$scope.portfolioData =  dataSet;//dataSet.filter(function (port) { return (port.portfolioType == x)});						
		} else {
			$scope.portfolioData =  $scope.portfolioDataMaster;//$scope.portfolioDataMaster.filter(function (port) { return (port.portfolioType == x)});
		}
//		console.log('-----');
		$scope.list2 = [];
		$scope.lists = [];
		//Reset Buttons
		$('[id^=button-]').attr("disabled", false).css('opacity',1);
		angular.forEach($scope.portfolioData,function(value,index){
			var item1= {
					"advId": $scope.portfolioData[index].avdId,
					"portfolioName": $scope.portfolioData[index].portfolioName,
					"portfolioId": $scope.portfolioData[index].portfolioId,
					"actionText" : ''
			};
			$scope.list2.push(item1);
		});
		angular.forEach($scope.list2,function(value,index){
			if($scope.fetchActionFilter){
				//User Unavailable
	    		$scope.list2[index].actionText ='Invite';
			}else{
				$scope.list2[index].actionText =  $scope.getActionText($scope.list2[index].portfolioName,$scope.list2[index].advId,$scope.list2[index].portfolioId);
			}
		});
		$scope.lists = $scope.portfolioData.map(function(item, i) {
		    return {
		        item1: item,
		        item2: $scope.list2[i] || null
		    };
		});

		$scope.filtered =  false;
		if ($scope.$root.$$phase != '$apply' && $scope.$root.$$phase != '$digest') {
		    $scope.$apply();
		}
        
        setTimeout(function(){ 
        	$('.divAdvisorRatings').each(function(index){
    			$(this).raty({'readOnly':true , score : $(this).attr('value')});
//    			console.log(this);console.log('ratify');
    		});
        }, 100);
        
    }
	
	
	$scope.portfolioData = []	;
	$scope.list2 = [];
	$scope.lists = [];
	$scope.portfolioDataMaster = []	;
	setTimeout(function(){ 
		displayUserName = function (){
        	$("#displayOnlyUserName").text($("#userFirstName").val());
        }
    	displayUserName();
    	//Check URL whether Parameter is Passed
		var isRobo;
    	var baseurl = window.location.href;
    	var paramsurl =  baseurl.split('?');
		var paramSub = paramsurl[1];
		
    	if(paramSub  != null ){
    		var params = paramSub.split('&');
    		for(var i = 0;i<params.length;i++){
    			if(params[i].indexOf('robo') == 0 ){
    				isRobo = params[i].split('=')[1]; 
    			} 
    		}
    	}
//		console.log("isRobo");
		if(isRobo != "true"){
			if(isRobo != "false"){
				isRobo="all";
			}
			else{
				isRobo = false;
			}
		}else{
			isRobo =true;
		}
			
//    	console.log(isRobo);
    	
		var assetPointer;
		var assetChart;
		$.ajax({
			type: 'POST',
			url : _gc_url_portfolioSearch,
			success: function(rdata){
//				console.log(rdata);
				var d = $.parseJSON(rdata);
				$scope.fpNotCompleted = d.fpNotCompleted;
				$scope.riskProfileNotCompleted = d.riskProfileNotCompleted;
				$scope.portfolioData = d.portfolios;
				$scope.portfolioDataMaster = d.portfolios;
				$scope.portfolioSizeArray = d.portfolioSizeArray;
				$scope.totalFunds = d.totalFunds;
				if(isRobo != "all"){
					//Skip if values don't match
					$scope.portfolioData = jQuery.grep($scope.portfolioData, function(value) {
						  return value.isRoboAdvisor == isRobo;
					});
				}
				angular.forEach($scope.portfolioData,function(value,index){
					var item1= {
								"advId": $scope.portfolioData[index].avdId,
								"portfolioName": $scope.portfolioData[index].portfolioName,
								"portfolioId": $scope.portfolioData[index].portfolioId,
								"actionText" : ''
					};
					$scope.list2.push(item1);					
				});
				angular.forEach($scope.list2,function(value,index){
					if($scope.fetchActionFilter){
						//User Unavailable
			    		//Set Default Text
						$scope.list2[index].actionText ='Invite';
					}else{
						$scope.list2[index].actionText =  $scope.getActionText($scope.list2[index].portfolioName,$scope.list2[index].advId,$scope.list2[index].portfolioId);
					}
				});
				$scope.lists = $scope.portfolioData.map(function(item, i) {
				    return {
				    	item1: item,
				        item2: $scope.list2[i] || null
				    };
				});
				$scope.$apply();
				$('.divAdvisorRatings').each(function(index){
        			$(this).raty({'readOnly':true , score : $(this).attr('value')});
        		});
			},
			error: function(xhr, textStatus, error){
				console.log(xhr.statusText);
				console.log(textStatus);
				console.log(error);
			}
		});
		
		
        /*$("#riskoMeter").roundSlider({
            sliderType: "min-range",
            editableTooltip: false,
            radius: 105,
            width: 16,
            value: 81,
            handleSize: 0,
            handleShape: "square",
            circleShape: "half-top",
            startAngle: 360,
            tooltipFormat: "changeTooltip"
        });*/
        
        /*var rangeSalary = $("#range_allocationMMF");
        rangeSalary.ionRangeSlider({
        	min:0,
	         max:500000,
	         prefix: "â‚¹ ",
	         from:0,
	         onStart: function (data) {
	             //console.log('onStart');
	         },
	         onChange: function (data) {
	             //console.log('onChange');
	             
	         },
	         onFinish: function (data) {
	             if((data.from/this.max)*100 > 95 && this.max<5000000)
	           	 {
					maxSal=this.max;
	            	if(this.max==500000){maxSal=1000000;}
	            	else if(this.max==1000000){maxSal= 1500000;}
	            	else if(this.max==1500000){maxSal= 2500000;}
	            	else if(this.max==2500000){maxSal= 5000000;}
	            	sliderSalary.update({max: maxSal});
	            	sliderSalary.reset();
	            	$(".irs-single").css("display","block");
	           	 }
	             
	             if((data.from/this.max)*100 < 10 && this.max>500000)
	           	 {
					maxSal=this.max;
	            	if(this.max==1000000){maxSal=500000;}
	            	else if(this.max==1500000){maxSal= 1000000;}
	            	else if(this.max==2500000){maxSal= 1500000;}
	            	else if(this.max==5000000){maxSal= 2500000;}
	            	sliderSalary.update({max: maxSal});
	            	sliderSalary.reset();
	            	$(".irs-single").css("display","block");
	           	 }
	              
	             sliderSavings.update({max: (data.from + sliderSalarySpouse.result.from)});
	             $(".irs-single").css("display","block");
	    	     $('.irs-min').css('display','block');
	    	     $('.irs-max').css('display','block');
	         },
	         onUpdate: function (data) {
	             //console.log('onUpdate');
	         }
	     });
        sliderSalary = rangeSalary.data("ionRangeSlider");
	     $(".irs-single").css("display","block");
	     $('.irs-min').css('display','block');
	     $('.irs-max').css('display','block');*/
	     
	     $(':radio').each(function(){
	        	//console.log('hi');
	            var self = $(this);
	            label = self.next();
	            label_text = label.text();
	            //label.remove();
	            self.iCheck({
	            //checkboxClass: 'icheckbox_square-green',
	            radioClass: 'iradio_square-green'
	            });
	            
	        });
	});
	$(document).ready(function() {
		$('[id^=detail-]').hide();
	    $('[id^=detail-1]').show();
	    $('.toggle').click(function() {
	        $input = $( this );
	        $target = $('#'+$input.attr('data-toggle'));
	        $target.slideToggle();
	        
	        if($input.find('.col-xs-2 i').attr('class')=="fa fa-chevron-down pull-right")
	        {
	             $input.find('.col-xs-2 i').removeClass('fa-chevron-down'); 
	             $input.find('.col-xs-2 i').addClass('fa-chevron-up');
	        }
	        else
	        {
	             $input.find('.col-xs-2 i').removeClass('fa-chevron-up'); 
	             $input.find('.col-xs-2 i').addClass('fa-chevron-down');     
	        }
	       
	    });	    
	});
	
	$(".riskProfileCheckbox").change(function() {
	    if(this.checked) {
	    	//console.log(this);
	        riskProfileChecked(this);
		} else {
	    	riskProfileUnchecked(this);
	    }
	});
	function riskProfileChecked(element){
		
			$(element).parent().addClass("riskProfile-"+element.id+"-checked");

	}
	function riskProfileUnchecked(element){
		
			$(element).parent().removeClass("riskProfile-"+element.id+"-checked");
		
	}

	(function() {
		$("#rangeMinInv").slider({
		range: "min",
		max: 10000000,
		value: 1000,
		step:10000,
		slide: function(e, ui) {
			var amt = ui.value;
			$("#rangeMinInv #currentVal").html(amt.toLocaleString() + ' Rs');
			}
		});
		$("#rangeContractHorizon").slider({
		range: "min",
		max: 10,
		value: 1,
		slide: function(e, ui) {
			var amt = ui.value;
			$("#rangeContractHorizon #currentVal").html(amt.toLocaleString() + ' Years');
			}
		});
	}).call(this);
	
	$('#resetFilter').on('click',function(){
		$("input[type='checkbox']").each(function(index){
			$(this).attr('checked',false);
		});
		
		$("#rangeMinInv").slider({value:0});
		$("#rangeMinInv #currentVal").html('0');
		
		$("#rangeContractHorizon").slider({value:0});
		$("#rangeContractHorizon #currentVal").html('0');
		
		
     

		$('#cityFilter option:eq(0)').prop('selected', true);
		$('#selectMinInvest option:eq(0)').prop('selected', true);
		$scope.portfolioData =  $scope.portfolioDataMaster;
		if ($scope.$root.$$phase != '$apply' && $scope.$root.$$phase != '$digest') {
		    $scope.$apply();
		}
		$scope.$apply();
		   $('.divAdvisorRatings').each(function(index){
			$(this).raty({'readOnly':true , score : $(this).attr('value')});
			console.log($(this).attr('value'));
		});
		$scope.filterResult();   
	});
	

    $scope.selectedPortfolio = function(portfolio,avdId){
//    	console.log("portfolioIndex:"+ portfolio);
//    	console.log("avdid:"+ avdId);
//    	window.location.href = 'advisorProfileView.jsp?advId='+avdId+"&port="+portfolio;
//    	window.location.href =  'advisorProfileView.jsp?advId='+avdId+"&port="+encodeURIComponent(portfolio);
    	//Snehal:
    	window.location.href =  '/faces/pages/v3/advisorProfileView.jsp?advId='+avdId+"&port="+encodeURIComponent(portfolio);
    }
    
    $scope.callToAction = function(portfolio,avdId,portId,minInvest){
    	//Only if FP and RP complete, then proceed with action
    	console.log("FP : "+ $scope.riskProfileNotCompleted);
    	console.log("RP : "+ $scope.fpNotCompleted);
    	console.log(minInvest);
    	if($scope.riskProfileNotCompleted == true || $scope.fpNotCompleted == true){
    		var msg = "Profile Completion Check";
    		if($scope.fpNotCompleted == true && $scope.riskProfileNotCompleted == true){
    			msg = "Please complete your Financial Planner and Risk Profile Questionnaire to Proceed";
			}else if($scope.fpNotCompleted == true){
    			msg = "Please complete your Financial Planner to Proceed";
    		}
    		else if($scope.riskProfileNotCompleted == true)
    		{
    			msg = "Please complete your Risk Profile Questionnaire to Proceed";
    		}
    		alert(msg);
    		console.log("Profile Incomplete");
    	} else if ($scope.totalFunds < minInvest) {
    		msg = "You have insufficient funds.\n To take this portfolio Please transfer more funds in your MMF Account.";
    		alert(msg);
    		console.log("Profile Incomplete");
    	} else{    	   	
        	//Disable the button
        	$("#button-"+avdId+"-"+portId).attr("disabled","disabled").css('opacity',0.5);
    		//Disable other buttons for same advisor
//        	$('[id^="button-'+avdId+'-"]').attr("disabled","disabled").css('opacity',0.5);
        	//Hide the old result msg divs
        	$('[id^="div-'+avdId+'-"]').hide();
        	
        	showLoading();
        	var actionText=$("#button-"+avdId+"-"+portId).text().trim();
        	if(actionText == ''){
        		$("#button-"+avdId+"-"+portId).text('Cancel');
        	}
        	console.log("Invoking Action : "+actionText+" for Portfolio :"+portfolio+" of Advisor "+ avdId);
        	if (actionText === "Invite Advisor"){
        		clevertap.event.push("Invite Advisor");
        	}
            var settings = {
    		 "async": true,
             "crossDomain": true,
             "url": "/faces/investmentAdvProfile",
             "method": "POST",
             "headers": {
    		    "cache-control": "no-cache",
    			"content-type": "application/x-www-form-urlencoded"
    		  },
              "processData": false,
              "contentType": false,
              "mimeType": "multipart/form-data",
              "data":"action="+actionText+"&portfolio="+portfolio+"&advId="+avdId
    		}
    		
    		$.ajax(settings).done(function (res) {
    			try{
    	//			console.log(res);
    				res = JSON.parse(res);			
    				console.log(res);
    				if(res != null && res.redirectUrl != null){
    					if(res.redirectUrl.includes('contract') || res.redirectUrl.includes('portfolioAllocation') ){
    						//Redirect to another page
    						window.location.href = res.redirectUrl;						
    					}else if(res.redirectUrl.includes('investordashboard')){
    						$("#div-"+avdId+"-"+portId).text("You already signed contract with an advisor").css({"text-align":"center","font-weight": "bold","font-size":"18px"}).show();
    					}
    				}else if(res != null && res.msg != null){
    					//Show status message
    					$("#div-"+avdId+"-"+portId).text(res.msg).css({"text-align":"center","font-weight": "bold","font-size":"18px"}).show();
    					
    					if(res.msg.includes('success')){
    						//Change button texts, enable current portfolio and disable other portfolios of same advisor 
    						if (actionText === "Invite Advisor") {
    							$('[id^="button-'+avdId+'-"]').text('Invite Advisor').attr("disabled","disabled").css('opacity',0.5).show();
    							$("#button-"+avdId+"-"+portId).text('Withdraw').attr("disabled", false).css('opacity',1);							
    					    } else if (actionText === "Withdraw") {
    					    	$('[id^="button-'+avdId+'-"]').text('Invite Advisor').attr("disabled",false).css('opacity',1).show();
    					    } 
    					}
    				}
    			}catch(e){
    				console.log('Error');
    			}
    			hideLoading();
    		}).fail(function (response){
    			console.log(response);
    			hideLoading();
    		});   		
    	}        
    }
    
    $scope.getActionText = function(portfolio,advId,portId){
    	var resultDesc = '';	
//	    console.log("Method1 : "+advId+" : "+portfolio);
        	
    	var settings = {
		  "async": true,
          "crossDomain": true,
          "url": "/faces/investmentAdvProfile",
          "method": "POST",
          "headers": {
			    "cache-control": "no-cache",
				"content-type": "application/x-www-form-urlencoded"
			  },
		  "processData": false,
		  "contentType": false,
		  "mimeType": "multipart/form-data",
		  "data":"fetch=true&advId="+advId+"&portfolio="+portfolio
    	}
		
    	
		$.ajax(settings).done(function (res) {
			try{
//				console.log(res);
				res = JSON.parse(res);
//				console.log(res);
				if(res.length != 0) {
					var indexInvite = res.indexOf("Invite Advisor");
					var indexWithdraw = res.indexOf("Withdraw");
					if(indexInvite == -1 && indexWithdraw == -1){
						//Hide the button
						$("#button-"+advId+"-"+portId).hide();
					}else{
						if(indexInvite != -1){
							//Set the button text to Invite
							$("#button-"+advId+"-"+portId).text(res[indexInvite]);
						}	
						if( indexWithdraw != -1){
							//Set the button text to Withdraw
							$("#button-"+advId+"-"+portId).text(res[indexWithdraw]);
						}				
					}
//					console.log(resultDesc);				
				}				
			}catch(e){
				console.log('Error');
			}
		}).fail(function (response){
			console.log(response);
		});
		return resultDesc;		
    }
}]);