$(function(){
		$(status).ready(function(){
			var status = ("#status").text();
			
			if(status == "貸し出し中"){
				$("#rent").prop("disabled", true);
				$("#delete").prop("disabled", true);
				 
			}
			if(status == "貸し出し可"){
				$("#return").prop("disabled", true);	
			}
			
		//$(error).on(function(){
})
});