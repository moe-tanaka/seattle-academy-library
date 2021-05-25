$(function(){
		var $status = $("#status").text();
		if($status == "貸出し中"){
			$("#delete").hide();
			$("#rent").hide();
		}
		
		var $status = $("#status").text();
		if($status == "貸出し可"){
			$("#return").hide();
		}
    	
		$("#delete").click(function(){
			if (!confirm('書籍情報を削除してもよろしいですか？')) {
			return false;
			}
})
});