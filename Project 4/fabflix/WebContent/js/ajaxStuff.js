/**
 * 
 */
function getSuggests(){
	var ajaxRequest;  // The variable that makes Ajax possible!
	
	try{
		// Opera 8.0+, Firefox, Safari
		ajaxRequest = new XMLHttpRequest();
	} catch (e){
		// Internet Explorer Browsers
		try{
			ajaxRequest = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try{
				ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e){
				// Something went wrong
				alert("Your browser broke!");
				return false;
			}
		}
	}
	if (ajaxRequest.readyState == 4 || ajaxRequest.readyState == 0) {
		var str = escape(document.getElementById('topSearchBar').value);
		ajaxRequest.open("GET", 'Autocompletion?search=' + str, true);
		ajaxRequest.onreadystatechange = handleSearchSuggest; 
	}
	
	function handleSearchSuggest() {
		if (ajaxRequest.readyState == 4) {
			var ss = document.getElementById('suggestions')
			ss.innerHTML = '';
			var str = ajaxRequest.responseText.split("\n");
			if( str.length == 1)
			{
				document.getElementById("suggestions").style.display='none';
			}
			else
			{
				document.getElementById("suggestions").style.display='block';
			}
			for(i=0; i < str.length - 1; i++) {
				//Build our element string.  This is cleaner using the DOM, but
				//IE doesn't support dynamically added attributes.
				var suggest = '<div onmouseover="javascript:suggestOver(this);" ';
				suggest += 'onmouseout="javascript:suggestOut(this);" ';
				suggest += 'onclick="javascript:setSearch(this.innerHTML);" ';
				suggest += 'class="suggest_link">' + str[i] + '</div>';
				ss.innerHTML += suggest;
			}
		}
	}
	
	ajaxRequest.send(null); 
}

//Mouse over function
function suggestOver(div_value) {
	div_value.className = 'suggest_link_over';
}
//Mouse out function
function suggestOut(div_value) {
	div_value.className = 'suggest_link';
}

//Click function
function setSearch(value) {
	document.getElementById('topSearchBar').value = value;
	document.getElementById('suggestions').innerHTML = '';
	document.getElementById("suggestions").style.display='none';
	
}

function popUpDetails(ctrl){
	var ajaxRequest;  // The variable that makes Ajax possible!
	
	try{
		// Opera 8.0+, Firefox, Safari
		ajaxRequest = new XMLHttpRequest();
	} catch (e){
		// Internet Explorer Browsers
		try{
			ajaxRequest = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try{
				ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e){
				// Something went wrong
				alert("Your browser broke!");
				return false;
			}
		}
	}
	var str;
	if (ajaxRequest.readyState == 4 || ajaxRequest.readyState == 0) {
		str = escape(ctrl);
		ajaxRequest.open("GET", 'PopupWindow?movie_id=' + str, true);
		ajaxRequest.onreadystatechange = handlePopupWindow; 
	}
	
	function handlePopupWindow() {
		if (ajaxRequest.readyState == 4) {
			var ss = document.getElementById('movie_pop_up' + ctrl)
			ss.innerHTML = '';
			var str = ajaxRequest.responseText;
			ss.classList.add('show');
			ss.innerHTML += str;
		}
	}
	
	ajaxRequest.send(null); 
	event.stopPropagation();
}

//Mouse out function
function popOutOff(div_value) {
	document.getElementById("movie_pop_up" + div_value).classList.remove('show');
	event.stopPropagation();
}

//Mouse out function
function popOutOffAll() {
	var ss =	document.getElementsByClassName("show");
	for( i =0; i < ss.length; i++) 
	{
		ss[i].classList.remove('show');
	}
}