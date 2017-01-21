function refreshPBX() {
	$.getJSON("dashboard/pbx/", function(data) {
		if (data.asterisk === 'ok') {
			var asteriskbox = $('#asterisk .info-box-icon');
			if (asteriskbox.hasClass('bg-red')) asteriskbox.removeClass('bg-red');
			if (!asteriskbox.hasClass('bg-red')) asteriskbox.addClass('bg-green');
			$('#asterisk .info-box-content .info-box-number').html(data.version);
			$('#asterisk .info-box-content .progress-description').html(data.uptime);
			
		} else {
			var asteriskbox = $('#asterisk .info-box-icon');
			if (asteriskbox.hasClass('bg-green')) asteriskbox.removeClass('bg-green');
			if (!asteriskbox.hasClass('bg-red')) asteriskbox.addClass('bg-red');
			$('#asterisk .info-box-content .info-box-number').html("DOWN");
			$('#asterisk .info-box-content .progress-description').html("");
		}
		
		if (data.actives !== undefined)
			$('#calls .info-box-content .info-box-number').html(data.actives);
		
		if (data.processed !== undefined)
			$('#calls .info-box-content .progress-description').html(data.processed + " processed");
		
		if (data.phonesOnline !== undefined) {
			$('#phonesOnline .info-box-number').html(data.phonesOnline);
			var percent = (data.phonesOnline / data.phones) * 100;
			$('#phonesOnline .progress .progress-bar').css('width', percent + '%');
			$('#phonesOnline .progress-description').html(data.phonesOnline + " of " + data.phones + " online");
		}
		
		if (data.trunksOnline !== undefined) {
			$('#trunksOnline .info-box-number').html(data.trunksOnline);
			var percent2 = (data.trunksOnline / data.trunks) * 100;
			$('#trunksOnline .progress .progress-bar').css('width', percent2 + '%');
			$('#trunksOnline .progress-description').html(data.trunksOnline + " of " + data.trunks + " online");
		}
	}).always(function() { setTimeout(refreshPBX, 10000); });
}

$(document).ready(function() {
	refreshPBX();
});