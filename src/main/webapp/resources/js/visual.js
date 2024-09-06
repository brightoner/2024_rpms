$(document).ready(function() {
	
$('#day_img').click(function(){
	$(".col-mon").addClass('hide');
	$(".col-week").addClass('hide');
	$(".col-day").removeClass('hide');
	
	var srcday = $("#day_img").attr("src");
	var srcweek = $("#week_img").attr("src");
	var srcmon = $("#mon_img").attr("src");
    $(this).attr('src', srcday.replace('_off', '_on'));
	$("#week_img").attr('src', srcweek.replace('_on', '_off'));
	$("#mon_img").attr('src', srcmon.replace('_on', '_off'));

});

$('#week_img').click(function(){
		$(".col-day").addClass('hide');
		$(".col-mon").addClass('hide');
		$(".col-week").removeClass('hide');

		var srcday = $("#day_img").attr("src");
		var srcweek = $("#week_img").attr("src");
		var srcmon = $("#mon_img").attr("src");
		$(this).attr('src', srcweek.replace('_off', '_on'));
		$("#day_img").attr('src', srcday.replace('_on', '_off'));
		$("#mon_img").attr('src', srcmon.replace('_on', '_off'));
		
		
	});

$('#mon_img').click(function(){
		$(".col-day").addClass('hide');
		$(".col-week").addClass('hide');
		$(".col-mon").removeClass('hide');

		var srcday = $("#day_img").attr("src");
		var srcweek = $("#week_img").attr("src");
		var srcmon = $("#mon_img").attr("src");
		$(this).attr('src', srcmon.replace('_off', '_on'));
		$("#day_img").attr('src', srcday.replace('_on', '_off'));
		$("#week_img").attr('src', srcweek.replace('_on', '_off'));
		
		
	});



/*서브페이지 달력 탭*/
$('#day_img2').click(function(){
	$(".col-mon2").addClass('hide');
	$(".col-week2").addClass('hide');
	$(".col-day2").removeClass('hide');
	
	var srcday = $("#day_img2").attr("src");
	var srcweek = $("#week_img2").attr("src");
	var srcmon = $("#mon_img2").attr("src");
    $(this).attr('src', srcday.replace('_off', '_on'));
	$("#week_img2").attr('src', srcweek.replace('_on', '_off'));
	$("#mon_img2").attr('src', srcmon.replace('_on', '_off'));

});

$('#week_img2').click(function(){
		$(".col-day2").addClass('hide');
		$(".col-mon2").addClass('hide');
		$(".col-week2").removeClass('hide');

		var srcday = $("#day_img2").attr("src");
		var srcweek = $("#week_img2").attr("src");
		var srcmon = $("#mon_img2").attr("src");
		$(this).attr('src', srcweek.replace('_off', '_on'));
		$("#day_img2").attr('src', srcday.replace('_on', '_off'));
		$("#mon_img2").attr('src', srcmon.replace('_on', '_off'));
		
		
	});

$('#mon_img2').click(function(){
		$(".col-day2").addClass('hide');
		$(".col-week2").addClass('hide');
		$(".col-mon2").removeClass('hide');

		var srcday = $("#day_img2").attr("src");
		var srcweek = $("#week_img2").attr("src");
		var srcmon = $("#mon_img2").attr("src");
		$(this).attr('src', srcmon.replace('_off', '_on'));
		$("#day_img2").attr('src', srcday.replace('_on', '_off'));
		$("#week_img2").attr('src', srcweek.replace('_on', '_off'));
		
		
	});

	/*메뉴 개수 별로 가로 크기 수정*/
	var gnbWidth = $('#gnbPage').width();
	var gnbc  = $("#gnbPage > li").length;
	var menuWidth = 790 / gnbc;

	$("#gnbPage > li > a").css('width', menuWidth);

		if (gnbc <= 5)	{
			$('#gnbPage .sub1').css('margin-left', '180px');
			$('#gnbPage .sub2').css('margin-left', '350px');
			$('#gnbPage .sub3').css('margin-left', '360px');
			$('#gnbPage .sub4').css('margin-left', '660px');
			$('#gnbPage .sub5').css('margin-left', '815px');
		};

/* 스케줄 팝업 */
$(function() {
	$('body').click(function(e) {
		/*$('.schedule').css('display', 'none');*/
		var pop = $('.schedule');

		if (!pop.is(e.target) // 타겟이 var pop가 아닌경우
		 && pop.has(e.target).length === 0) // var pop에 속한 엘리먼트가 아닌경우
		{
		    pop.hide();
		}


	});
});




/*인트로 오버*/
$('.intro ul li a').mouseover(function(){
	$(this).each(function(){
		$(this).find("img").attr("src" , $(this).find("img").attr("src").replace("_bg.png" , "_on.png"));
	});

	/*$(this).find("img").attr("src" , $(this).find("img").attr("src").replace("_bg.png" , "_on.png"));
        return false;*/
	/*var srcintro = $(".intro ul li a").attr("src");
    $(this).attr('src', srcintro.replace('_bg', '_on'));*/
});
$('.intro ul li a').mouseout(function(){
	$(this).each(function(){
		$(this).find("img").attr("src" , $(this).find("img").attr("src").replace("_on.png" , "_bg.png"));
	});
});

/*기관별홍보실 동영상 게시판

$('.movie a').mouseover(function(){
	$(this).find('.title').css('color', '#2ab3ed');
	$(this).find('.play_btn').stop().fadeIn();
});
$('.movie a').mouseout(function(){
	$(this).find('.title').css('color', '#626262');
	$(this).find('.play_btn').stop().fadeOut();
});

/*기관별홍보실*/
$('#sub03_prm li a').mouseover(function(){
	$(this).find('.over_box').stop().fadeIn();
});
$('#sub03_prm li a').mouseout(function(){
	$(this).find('.over_box').stop().fadeOut();
});



	// 메인일 때
/*	if($('body').attr('class').match('main')) {	
		initMain();
	}*/

	// gnb
	initGnb();
	
	// lnb
	initLnb();

	// 앵커
	$('a[href=#], a[href=#none]').bind('click', function(event){
		event.preventDefault();
	});

	// 롤오버
    $('img.rollover').each(function() {
        $(this).mouseover(function() {
            if ($(this).attr('src').match('_off')) {
                $(this).attr('src', $(this).attr('src').replace('_off', '_on'));
                $(this).mouseout(function() {
                    $(this).attr('src', $(this).attr('src').replace('_on', '_off'));
                });
            }
        });
    });

    // table style, markup
	$('#gnb ul li li:first-child').addClass('first-child');
	$('#gnb > ul > li:last-child').addClass('last-child');
    $('table tr:first-child').addClass('first');
    $('table tr :last-child').addClass('last-child');
    $('table.table-a').wrap('<div class="table-a-wrap"></div>');
	$('ul.tab-b').wrap('<div class="tab-b-wrap"></div>').parent().append('<span class="bg01"></span><span class="bg02"></span>');

	// input box 스타일링
	$('input[type=text], input[type=password]').addClass('txt');
	$('input[type=file]').addClass('file');
	$('input[type=image]').addClass('image');
	$('input[type=submit]').addClass('button');
	$('input[type=checkbox]').addClass('check');
	$('input[type=radio]').addClass('radio');
	$('textarea').addClass('textarea');

	// 기본 탭 자동 토글 
	$('ul.tab-toggler a').bind('click', function(event){
		event.preventDefault();
		var obj = $(this).attr('href').replace('#','');
		$(this).parent().siblings().children().removeClass('current');
		$(this).addClass('current');
		$(this).parent().parent().next().children().each(function(){
			if($(this).attr('id').match(obj)){
				$(this).show();
			} else {
				$(this).hide();
			}
		});
	});

	// FAQ
	$('table.faq td.subject a').bind('click', function(event){
		$('table.faq tr').removeClass('current');
		$(this).parent().parent().addClass('current');
		$('table.faq tr.toggle').each(function(){
			if($(this).prev().attr('class').match('current')){
				$(this).toggle();
			} else {
				$(this).hide();
			}
		});
	});

	// skyscraper
	if($('#skyscraper').val()!=null){
		var skyscraperPos = $('#skyscraper').position();
		var inskysrpTop = Math.round(skyscraperPos.top);
		$(window).bind('scroll', function(){
			var top = $(window).scrollTop();
			var pos = top + 50; // 최소 여백
			if (pos < inskysrpTop){
				pos = inskysrpTop;
			}
			$('#skyscraper').animate({
					top: pos
				},{
					queue: false,
					duration: 300,
					easing: 'swing'
				}
			);
		});
	}
});

// GNB
function initGnb() {
	//$('#gnb li.current img').attr('src', $('#gnb li.current img').attr('src').replace('_off', '_on'));
	$('#gnb > ul > li').hover(function(){
		$(this).find('.sub').show();
		/*$(this).addClass('on');*/
		$obj = $(this).find('img');
		//$obj.attr('src', $obj.attr('src').replace('_off', '_on'));
	}, function(){
		/*$(this).removeClass('on');*/
		$('#gnb .sub').hide();
		/*$obj.attr('src', $obj.attr('src').replace('_on', '_off'));*/
		//$('#gnb li.current img').attr('src', $('#gnb li.current img').attr('src').replace('_off', '_on'));
	});
}

















// LNB
function initLnb() {
	$('#lnb ol').hide();
	$('#lnb li.d1-current ol').slideDown('slow');
}

// Main
function initMain() {

	// 메인 게시판
	$('div.notify h3 a').click(function(event) {
		event.preventDefault();
		$('div.notify h3 a').removeClass('current');
		$(this).addClass('current');
		$('div.notify div.cont').hide();
		$(this).parent().next().show();
	});
	$('div.data h3 a').click(function(event) {
		event.preventDefault();
		$('div.data h3 a').removeClass('current');
		$(this).addClass('current');
		$('div.data div.cont').hide();
		$(this).parent().next().show();
	});
}








// Main
function initMain() {

	// 메인 게시판
	$('div.notify h3 a').click(function(event) {
		event.preventDefault();
		$('div.notify h3 a').removeClass('current');
		$(this).addClass('current');
		$('div.notify div.cont').hide();
		$(this).parent().next().show();

	});
}




//모바일 탭
$(document).ready(function() {
	
$('#mday_img').click(function(){
	$(".col-mon").addClass('hide');
	$(".col-week").addClass('hide');
	$(".col-day").removeClass('hide');
	
	var srcday = $("#mday_img").attr("src");
	var srcweek = $("#mweek_img").attr("src");
	var srcmon = $("#mmon_img").attr("src");
    $(this).attr('src', srcday.replace('_off', '_on'));
	$("#mweek_img").attr('src', srcweek.replace('_on', '_off'));
	$("#mmon_img").attr('src', srcmon.replace('_on', '_off'));

});

$('#mweek_img').click(function(){
		$(".col-day").addClass('hide');
		$(".col-mon").addClass('hide');
		$(".col-week").removeClass('hide');

		var srcday = $("#mday_img").attr("src");
		var srcweek = $("#mweek_img").attr("src");
		var srcmon = $("#mmon_img").attr("src");
		$(this).attr('src', srcweek.replace('_off', '_on'));
		$("#mday_img").attr('src', srcday.replace('_on', '_off'));
		$("#mmon_img").attr('src', srcmon.replace('_on', '_off'));
		
		
	});

$('#mmon_img').click(function(){
		$(".col-day").addClass('hide');
		$(".col-week").addClass('hide');
		$(".col-mon").removeClass('hide');

		var srcday = $("#mday_img").attr("src");
		var srcweek = $("#mweek_img").attr("src");
		var srcmon = $("#mmon_img").attr("src");
		$(this).attr('src', srcmon.replace('_off', '_on'));
		$("#mday_img").attr('src', srcday.replace('_on', '_off'));
		$("#mweek_img").attr('src', srcweek.replace('_on', '_off'));
		
		
	});
});