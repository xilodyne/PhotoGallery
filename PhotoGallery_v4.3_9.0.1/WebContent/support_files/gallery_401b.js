function setCookie(c_name, value) {
	var c_value = escape(value);
	document.cookie = c_name + "=" + c_value;
}
function getCookie(c_name) {
	var i, x, y, ARRcookies = document.cookie.split(";");
	for (i = 0; i < ARRcookies.length; i++) {
		x = ARRcookies[i].substr(0, ARRcookies[i].indexOf("="));
		y = ARRcookies[i].substr(ARRcookies[i].indexOf("=") + 1);
		x = x.replace(/^\s+|\s+$/g, "");
		if (x == c_name) {
			return unescape(y);
		}
	}
}

var toggleCaption = function() {
	document.getElementById('toggleCaptionText').classList.toggle('hideText');
	var elembut = document.getElementById("buttoncaption");

	if (elembut.innerHTML.match("Show Caption")) {
		elembut.innerHTML = "Hide Caption";
		setCookie("caption", "hide");
	} else {
		elembut.innerHTML = "Show Caption";
		setCookie("caption", "show");
	}
}

var toggleMeta = function() {
	document.getElementById('toggleMetaText').classList.toggle('hideText');
	var elembut = document.getElementById("buttonmeta");

	if (elembut.innerHTML.match("Show Metadata")) {
		elembut.innerHTML = "Hide Metadata";
		setCookie("metadata", "hide");
	} else {
		elembut.innerHTML = "Show Metadata";
		setCookie("metadata", "show");
	}
}

function setImageSize() {

	var myImage = document.getElementById("mainImage");
	var myImageWidth = myImage.naturalWidth;
	var myImageHeight = myImage.naturalHeight;

	var myImageIsHorizontal = myImageWidth >= myImageHeight;
	var myWindowIsHorizontal = window.outerWidth >= window.outerHeight;

	var boundboxWidth = $('DIV.mainImg-boundingbox').width();
	var boundboxHeight = $('DIV.mainImg-boundingbox').height();

	/*
	 * document.getElementById("ow").innerHTML = window.outerWidth;
	 * document.getElementById("oh").innerHTML = window.outerHeight;
	 * document.getElementById("iw").innerHTML = window.innerHeight;
	 * document.getElementById("ih").innerHTML = window.innerHeight;
	 * document.getElementById("sw").innerHTML = window.screen.width;
	 * document.getElementById("sh").innerHTML = window.screen.height;
	 * document.getElementById("imgw").innerHTML = myImageWidth;
	 * document.getElementById("imgh").innerHTML = myImageHeight;
	 * document.getElementById("boxw").innerHTML = boundboxWidth;
	 * document.getElementById("boxh").innerHTML = boundboxHeight;
	 * document.getElementById("imghor").innerHTML = myImageIsHorizontal;
	 * document.getElementById("winhor").innerHTML = myWindowIsHorizontal;
	 * document.getElementById("fgho").innerHTML =
	 * $('FIGURE.fmainImg').css('height');
	 * document.getElementById("fgwo").innerHTML =
	 * $('FIGURE.fmainImg').css('width');
	 */
	// if horiztonal photo and horizontal window
	// height determines size
	// if vertical photo and horizontal window
	// height determines size
	if ((myImageIsHorizontal && myWindowIsHorizontal)
			|| (!myImageIsHorizontal && myWindowIsHorizontal)) {
		$('FIGURE.fmainImg').css('height', ((boundboxHeight) + 'px'));
		$('FIGURE.fmainImg').css('max-height', ((boundboxHeight) + 'px'));
		$('FIGURE.fmainImg').css('width', '');
		$('FIGURE.fmainImg').css('max-width', '');
		document.getElementById('mainImage').setAttribute('height',
				(boundboxHeight));
	}

	// if horizontal photo and vertical window
	// width determines size
	// if vertical photo and vertical window
	// width determines size

	if ((!myImageIsHorizontal && !myWindowIsHorizontal)
			|| (myImageIsHorizontal && !myWindowIsHorizontal)) {
		$('FIGURE.fmainImg').css('height', '');
		$('FIGURE.fmainImg').css('max-height', '');
		$('FIGURE.fmainImg').css('width', ((boundboxWidth) + 'px'));
		$('FIGURE.fmainImg').css('max-width', ((boundboxWidth) + 'px'));
		document.getElementById('mainImage').setAttribute('width',
				(boundboxWidth));
	}

	// document.getElementById("fghi").innerHTML =
	// $('FIGURE.fmainImg').css('height');
	// document.getElementById("fgwi").innerHTML =
	// $('FIGURE.fmainImg').css('width');

}

document.addEventListener('touchstart', handleTouchStart, false);
document.addEventListener('touchmove', handleTouchMove, false);

var xDown = null;
var yDown = null;
var preimg = '';
var nxtimg = '';
var topimg = '';

function handleTouchStart(evt) {
	xDown = evt.touches[0].clientX;
	yDown = evt.touches[0].clientY;
};

function handleTouchMove(evt) {
	if (!xDown || !yDown) {
		return;
	}

	var xUp = evt.touches[0].clientX;
	var yUp = evt.touches[0].clientY;

	var xDiff = xDown - xUp;
	var yDiff = yDown - yUp;

	if (Math.abs(xDiff) > Math.abs(yDiff)) { // most significant
		if (xDiff > 0) {
			window.location.href = nxtimg;
	//		alert("It's a swipe!");
		} else {
			window.location.href = preimg;
	//		alert("It's a swipe!");
		}
	} else {
		if (yDiff > 0) {
		//	window.location.href = topimg;
		} else {
			window.location.href = topimg;
		}
	}
	// reset values
	xDown = null;
	yDown = null;
}
