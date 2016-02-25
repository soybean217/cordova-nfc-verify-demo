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
			var tag = nfcEvent.tag, ndefMessage = tag.ndefMessage;
			mTag = nfcEvent.tag;

			// dump the raw json of the message
			// note: real code will need to decode
			// the payload from each record
			console.log("ndefMessage:" + JSON.stringify(ndefMessage));

			// assuming the first record in the message has
			// a payload that can be converted to a string.
			console.log("payload:"
					+ nfc.bytesToString(ndefMessage[0].payload).substring(3));
			$("#writeNfcTagResult").html("NFC ready");
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
	console.log("documentReady");
	$("#writeNfcTag").click(writeNfc);
});

function writeNfc() {
	var message = [ ndef.textRecord($("#textRecord").val()),
			ndef.uriRecord("https://github.com/qing0325") ];
	writeServerAjax();
	nfc.write(message, function() {
		console.log("writeNfcSuccess");
		$("#writeNfcTagResult").html(
				"OK:" + $("#textRecord").val() + " is written .");
		writeServerAjax();
	}, function() {
		console.log("writeNfcFailure");
	});
}

function writeServerAjax() {
	console.log("writeServerAjax begin.mTag:" + JSON.stringify(mTag));
	var postData = {
		tagOriInfo : mTag,
		action : 'updateOrInsert',
		wantStatus : $("input[name='stickStatus']:checked").val()
	};
	$("#serverResult").html("Begin send to server ...");
	$.ajax({
		type : "post",
		url : mServerUrl,
		async : true,
		data : JSON.stringify(postData),
		dataType : "json",
		success : function(msg) {
			if (msg.status == "success") {
				$("#serverResult").html("Server request success !");
			}else{
				$("#serverResult").html("Server request failure !");
			}
			console.log("success to server");
		},
		error : function(msg) {
			$("#serverResult").html("Error to server !");
			console.log("error to server");
		}
	});
}
