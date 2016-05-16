/**
 * 
 */
function ajaxFunction(){
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
		ajaxRequest.send(null);
	}
	
	function handleSearchSuggest() {
		if (ajaxRequest.readyState == 4) {
			var ss = document.getElementById('suggestions')
			ss.innerHTML = '';
			var str = ajaxRequest.responseText.split("\n");
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
	
	ajaxRequest.open("GET", "Autocompletion", true);
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
	document.getElementById('search_suggest').innerHTML = '';
}