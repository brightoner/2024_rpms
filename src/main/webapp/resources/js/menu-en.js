$(window).load(function() {

	window.onscroll = function() {
		  //메뉴바
		  myFunction();
		  //이동바 보이기 이벤트
	};
});

function myFunction() {
	  var winScroll = document.body.scrollTop || document.documentElement.scrollTop;
	  var height =
	    document.documentElement.scrollHeight -
	    document.documentElement.clientHeight;
	  var scrolled = winScroll / height * 100;
	  if($('#myBar').length > 0 ){
		  document.getElementById("myBar").style.width = scrolled + "%";
	  }
}
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

                $('html,body').animate({scrollTop:$(this.hash).offset().top},800);
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

        }); $('body').removeClass('action');

    });

    var $footer = $('.footer-wrap .site-wrap');

    $footer.on('click',function(){
        if($footer.hasClass('on')){
            $footer.removeClass('on');
            $('.site-wrap .foot-link').slideUp();
            $footer.children('button').attr('title','open the list');
        }else{
            $footer.addClass('on');
            $('.site-wrap .foot-link').slideDown();
            $footer.children('button').attr('title','close the list');
        }
    });
});