function validateForm() {
	  var a = document.forms["formulario"]["nombre"].value;
	  var b = document.forms["formulario"]["correo"].value;
	  var c = document.forms["formulario"]["rfc"].value;
	  var d = document.forms["formulario"]["password"].value;
	  if (a == "" || b == "" || c == "" || d == "" ) {
	    alert("Error\nVerificar datos");
	    return false;
	  }
	}
	$(document).ready(function() {
		//Asegurate que el id que le diste a la tabla sea igual al texto despues del simbolo #
		$('#userList').DataTable();
		
		var temp= 2; 
	    $("#roles").val(temp);
		$("#ocultarRol").hide();
		$("#nombreE").hide();
		$("#correoE").hide();
		$("#rfcE").hide();
		$("#rfcE2").hide();
		$("#passwordE").hide();
		$("#password2E").hide();
		
		$("#registrar").click(function(){
		    $("p").hide();
		    if ($("#nombre").val() == "") {
		        $("#nombre").css("border", "5px solid red");
		        $("#nombreE").show();
		  }else{
			  	$("#nombre").css("border", "1px solid grey");
		  }
		    if ($("#correo").val() == "") {
		        $("#correo").css("border", "5px solid red");
		        $("#correoE").show();
		  }else{
			  	$("#correo").css("border", "1px solid grey");
		  }
		    if ($("#rfc").val() == "") {
		        $("#rfc").css("border", "5px solid red");
		        $("#rfcE").show();
		  }else{
			  	$("#rfc").css("border", "1px solid grey");
		  }
		    if ($("#password").val() == "") {
		        $("#password").css("border", "5px solid red");
		        $("#passwordE").show();
		  }else{
			  	$("#password").css("border", "1px solid grey");
		  }
		    if ($("#password2").val() == "") {
		        $("#password2").css("border", "5px solid red");
		  }else{
			  	$("#password2").css("border", "1px solid grey");
		  }
		    
		    if (($("#password").val() != "" || $("#password2").val() != "") && ($("#password").val() != $("#password2").val())) {
		        $("#password").css("border", "5px solid red");
		        $("#password2").css("border", "5px solid red");
		        $("#password2E").show();
		  }
		    
		    var reg = /^([A-ZÑ&]{3,4}) ?(?:- ?)?(\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\d|3[01])) ?(?:- ?)?([A-Z\d]{2})([A\d])$/;
		    console.log($("#rfc").val().match(reg));
		    if($("#rfc").val().match(reg) == null && $("#rfc").val() != ""){
		    	alert("Error\nVerificar datos");
		    	$("#rfcE2").show();
		    	$("#rfc").css("border", "5px solid red");
		    	return false;
		    } 
		  });
	});