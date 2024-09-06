$(document).ready(function(){

    var $header = $('#header');
    var $moMenu = $('.mo-menu .menu-box .menu > li > a');
    var scrollTop = $(window).scrollTop();

    $(window).on("load scroll", function(){
        scrollTop = $(window).scrollTop();
        if(scrollTop > 0){
            $header.addClass("fix");
        }else{
            $header.removeClass("fix");
        }
    });
    
    /*$('#header .gnb-wrap .gnb .menu > li').on('mouseover',function(){
        $header.addClass('on');
    });

    $header.on('mouseleave',function(){
        $header.removeClass('on');
    });
*/
   /* $('#header .gnb-wrap .gnb .menu > li').on('click',function(){
        $header.toggleClass('on');
        $(this).toggleClass('on');
    });*/
    
    $('#header .gnb-wrap .gnb .menu .sub-menu-box').on('mouseover',function(){
    	$(this).parents('li').addClass('on');
    });
    $('#header .gnb-wrap .gnb .menu .sub-menu-box').on('mouseleave',function(){
    	$(this).parents('li').removeClass('on');
    });

    $('#header .gnb-wrap .right-menu > li.user').on('mouseover',function(){
    	$('.userTooltip').addClass('on');
    });
    $('#header .gnb-wrap .right-menu > li.user').on('mouseleave',function(){
    	$('.userTooltip').removeClass('on');
    });
    
    $header.on('mouseleave',function(){
        $header.removeClass('on');
    });

/*    $(".sitemap-btn").on("click", function(e){
        e.preventDefault();

        $header.toggleClass('on');
    });*/
    
    
    $(".top-search-btn").on("click", function(e){
        e.preventDefault();
        if($(this).hasClass("on")){
            $(this).removeClass("on");
            $("#search-pop").hide();
            $(this).attr("title","통합검색창 열기");
            
        }else{
            $(this).addClass("on");
            $("#search-pop").show();
            $(this).attr("title","통합검색창 닫기");
        }
    });
    
    $('.nav > li > button').on('click',function(){
        $(this).toggleClass('on');
        $(this).siblings('.nav-02').slideToggle();
        if ($(this).hasClass('on')) {
        	$(this).attr("title","메뉴 닫기");
        } else {
        	$(this).attr("title","메뉴 열기");
        }
    });

    var scrollTop = $(window).scrollTop();
    var $spotHead = $("#spot").height();
    var $snbFix = $(".snb-fix-box");


    
    $(window).on("load scroll", function(){
        scrollTop = $(window).scrollTop();
        if(scrollTop > $spotHead){
            $snbFix.addClass("fix");
        }else{
            $snbFix.removeClass("fix");
        }
    });
    $('.top').on('click',function(e){
        e.preventDefault();

        $('html,body').animate({scrollTop:$(this.hash).offset().top},500);
    });


    var lastScroll = 0;

    $(window).on('scroll',function(){
        var scroll = $(this).scrollTop();

        if(scroll > 300){
            $('.top').addClass('active');
        }else{
            $('.top').removeClass('active');
        } 

        lastScroll = scroll;
    });


    $(window).on("resize", function(){
        $header.removeClass("mo-active");
        $(".sitemap-wrap").removeClass("active");
        $('body').removeClass('action');
    });

    $(".mo-menu-btn").on("click", function(e){
        e.preventDefault();

        $header.addClass("mo-active");
        $('body').addClass('action');
        $('#header .mo-menu').addClass('action');
    });

    $(".mo-menu-close").on("click", function(e){
        e.preventDefault();

        $header.removeClass("mo-active");
        $('body').removeClass('action');

    });

    $moMenu.on('click',function(e){
    	if($(this).attr('id').indexOf('momenu') > -1  ){
    	
			e.preventDefault();
	        if($(this).hasClass('active')){
	            $moMenu.removeClass('active').siblings('.menu-02').stop().slideUp();
	            $moMenu.attr('title','목록 열기');
	        }else{
	            $moMenu.removeClass('active').siblings('.menu-02').stop().slideUp();
	            $(this).addClass('active').siblings('.menu-02').stop().slideDown();
	            $moMenu.attr('title','목록 닫기');
	        }
    	} 
    });

        
    $('.lang-list > li > a').click(function () {
        $(this).toggleClass('active');
    });

    var $footer = $('.footer-wrap .site-wrap');

    $footer.on('click',function(){
        if($footer.hasClass('on')){
            $footer.removeClass('on');
            $('.site-wrap .foot-link').slideUp();
            $footer.children('button').attr("title","목록 열기");
        }else{
            $footer.addClass('on');
            $('.site-wrap .foot-link').slideDown();
            $footer.children('button').attr("title","목록 닫기");
        }
    });
});