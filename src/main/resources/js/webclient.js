function toggle_menu_visibility(id) {
	var maximizedDiv = document.getElementById('maximizedMenu');
	var minimizedDiv = document.getElementById('minimizedMenu');
	var contentWrapperDiv = document.getElementById('wrapper');

	if (id == 'minimizedMenu') {
		minimizedDiv.style.display = 'none';
		maximizedDiv.style.display = 'block';
		contentWrapperDiv.style.top = '130px';
		$('input.sizing').attr('value', 'false');
	} else {
		minimizedDiv.style.display = 'block';
		maximizedDiv.style.display = 'none';
		contentWrapperDiv.style.top = '105px';
		$('input.sizing').attr('value', 'true');
	}
}

function toggle_sub_menu_visibility() {
	var navigationDiv = document.getElementById('navigation');
	var contentDiv = document.getElementById('content');
	var navigationTreeDiv = document.getElementById('submenu');

	if (navigationDiv.style.width != '1%') {
		navigationDiv.style.width = '1%';
		contentDiv.style.width = '99%';
		navigationTreeDiv.style.display = 'none';
	} else {
		navigationDiv.style.width = '12%';
		contentDiv.style.width = '88%';
		navigationTreeDiv.style.display = 'block';
	}
}

$(document).ready(function() {

	$("#clientSelect").change(function() {
		$("#clientSelectForm").submit();
	});
	
	var classContent = $('#minimizedMenu').attr('class');
	if (classContent.indexOf("hide-menu") > -1) {
		var contentWrapperDiv = document.getElementById('wrapper');
		contentWrapperDiv.style.top = '130px';
	} else {
		var contentWrapperDiv = document.getElementById('wrapper');
		contentWrapperDiv.style.top = '105px';
	}
});