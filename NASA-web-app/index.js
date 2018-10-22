
var rootRef = firebase.database().ref().child("devices");

rootRef.on("child_added", snap => {

	
	var deviceId = snap.child("deviceID").val();
	var deviceLoc = snap.child("devLocation").val();
	var status = snap.child("status").val();

	//$("#table-body").append("<tr><td>"+deviceId+"</td> <td>"+deviceLoc+"</td><td>"+status+"</td></tr>");
	
	var table = document.getElementsByTagName('table')[0];

	var newRow = table.insertRow(1);
	//alert("aSome");
	var cell_0 = newRow.insertCell(0);
	var cell_1 = newRow.insertCell(1);
	var cell_2 = newRow.insertCell(2);

	cell_0.innerHTML = deviceId;
	cell_1.innerHTML = deviceLoc;
	cell_2.innerHTML = status;



});


