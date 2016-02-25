var mTag;
var app = {
	// Application Constructor
	initialize : function() {
		this.bindEvents();
	},
	// Bind Event Listeners
	//
	// Bind any events that are required on startup. Common events are:
	// 'load', 'deviceready', 'offline', and 'online'.
	bindEvents : function() {
		document.addEventListener('deviceready', this.onDeviceReady, false);
	},
	// deviceready Event Handler
	//
	// The scope of 'this' is the event. In order to call the 'receivedEvent'
	// function, we must explicitly call 'app.receivedEvent(...);'
	onDeviceReady : function() {
		app.receivedEvent('deviceready');

		console.log("nfc");

		// Read NDEF formatted NFC Tags
		nfc.addNdefListener(function(nfcEvent) {
			console.log("nfc.addNdefListener" + nfcEvent);
			// nfcEvent is not a json
			var tag = nfcEvent.tag, ndefMessage = tag.ndefMessage;
			mTag = nfcEvent.tag;
			// dump the raw json of the message
			// note: real code will need to decode
			// the payload from each record
			// console.log("nfcEvent:" + JSON.stringify(nfcEvent));
			console.log("tag:" + JSON.stringify(tag));
			console.log("ndefMessage:" + JSON.stringify(ndefMessage));

			// assuming the first record in the message has
			// a payload that can be converted to a string.
			var massageFromNfc = nfc.bytesToString(ndefMessage[0].payload)
					.substring(3);
			console.log("payload:" + massageFromNfc);
			$("#brandValue").html(massageFromNfc);
			$("body").css("background-image", 'url(img/brand.png)');
			verifyFromServerAjax();
		}, function() { // success callback
			console.log("nfc success , nfcEvent.tag:" + nfcEvent.tag);
		}, function(error) { // error callback
			alert("Error adding NDEF listener " + JSON.stringify(error));
		});
	},
	// Update DOM on a Received Event
	receivedEvent : function(id) {

	}
};

app.initialize();

$(document).ready(function() {
	$("body").css("background-image", 'url(img/index.png)');
	$("#writeNfcTagResult").html("OK: is written .");
	console.log("go");
});

function verifyFromServerAjax() {
	console.log("verifyFromServerAjax begin.mTag:" + JSON.stringify(mTag));
	var postData = {
		tagOriInfo : mTag,
		action : 'verify'
	};
	$("#checkFromServer").html("Begin connect to server ...");
	$.ajax({
		type : "post",
		url : mServerUrl+"VerifyTag",
		async : true,
		data : JSON.stringify(postData),
		dataType : "json",
		success : function(msg) {
			if (msg.status == "success") {
				$("#checkFromServer").html(msg.msg);
			}else{
				$("#checkFromServer").html("Verify failure !");
			}
			console.log("success to server");
		},
		error : function(msg) {
			$("#checkFromServer").html("Error to server !");
			console.log("error to server");
		}
	});
}