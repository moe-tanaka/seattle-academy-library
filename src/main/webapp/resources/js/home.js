$(function() {
  $('#all').change(function() {
    if ($(this).prop('checked')) {
      $('input[name="checkbox"]').prop('checked', true);
	} else {
		$('input[name="checkbox"]').prop('checked', false);
    }
  });

	$('#allDeleteBook').click(function(){
    	$( 'input[name="checkbox"]' ).change(function() {
        	var checklist = [];
        	$( 'input[name="checkbox"]:checked' ).each(function() {
            checklist.push( $( this ).val() );
        	});
			$('#bookList').val(checklist);
        	console.log( checklist );
			
			
			
		} ).eq( 0 ).change();
		
		if (!confirm('チェックされた書籍情報を全て削除してもよろしいですか？\n(貸し出し中の書籍は削除されません)')) {
			return false;
			}	
	});
});
