

var is_tab2_load =false;
var is_tab3_load =false;
var tabSlide3;

$(document).ready(function(){

    var $sectionTab =  $("#section-02 .tab-tit-list > li > a");

    $sectionTab.on("click", function(e){
        e.preventDefault();
        var tabItem = $(this).attr("href");
        var itemImg = $(this).find("img").attr("src");
        var tabIndex = $(tabItem).find(".swiper-slide-active").find(".con-box").attr("data-tab");
        
        /*
        switch (tabItem) {
			case '#tab02-01': tabSlide1.autoplay.start(); // 자동 재생 시작
				break;
			case '#tab02-02': tabSlide2.autoplay.start(); // 자동 재생 시작
				break;
		default:
			break;
		}*/
        
        if(tabItem == '#tab02-02' && is_tab2_load == false){
        	fn_getData('index_2');
        	is_tab2_load =true;
    	}
        if(tabItem == '#tab02-03'){
	    	if(is_tab3_load == false){
	        	fn_getCrisData('cris_1');
	        	is_tab3_load =true; 
	    	}else{
	    		/*tabSlide3.autoplay.start(); // 자동 재생 시작
*/	    	}
        }
        
        $sectionTab.removeClass("active");
        $("#se02-tabcon").find(".tab-con").removeClass("active");

        $("#tab-img").attr("src",itemImg);

        $(this).addClass("active");
        $(tabItem).addClass("active");

       /* $(tabItem).find(".info-box").removeClass("active");
        $(tabItem).find(".info-box[data-con="+tabIndex+"]").addClass("active");*/

    });

    var tabSlide1 = new Swiper('#tab02-01 .swiper-container', {
        slidesPerView: 6,
        spaceBetween: 20,
        autoplay: false,
        observer: true,
        observeParents: true,
        loop: false,
        slideToClickedSlide:true,
        breakpoints: {
        	1200: {
                slidesPerView: 5,
                spaceBetween: 10
            },
            1024: {
                slidesPerView: 4,
                spaceBetween: 0
            },
            600: {
                slidesPerView: 3,
                spaceBetween: 10
            },
            480: {
                slidesPerView: 1,
                spaceBetween:0
            }
        },
        navigation: {
            nextEl: '#next01',
            prevEl: '#prev01'
        },
        on: {
            observerUpdate:function(){
                var objActive = $(".status-list-1 .swiper-slide-active").find(".con-box").attr("data-tab");
                $(".info-tab-con[data-item='info-tab-01'] .info-box").removeClass("active");
                $(".info-tab-con[data-item='info-tab-01'] .info-box[data-con="+objActive+"]").addClass("active");
            },
            slideChangeTransitionEnd: function () {
              var objActive = $(".status-list-1 .swiper-slide-active").find(".con-box").attr("data-tab");
                $(".info-tab-con[data-item='info-tab-01'] .info-box").removeClass("active");
                $(".info-tab-con[data-item='info-tab-01'] .info-box[data-con="+objActive+"]").addClass("active");
            },
        }
    });


    var tabSlide2 = new Swiper('#tab02-02 .swiper-container', {
        slidesPerView: 6,
        spaceBetween: 20,
        autoplay: false,
        observer: true,
        observeParents: true,
        loop: false,
        slideToClickedSlide:true,
        navigation: {
            nextEl: '#next02',
            prevEl: '#prev02'
        },
        breakpoints: {
        	1200: {
                slidesPerView: 5,
                spaceBetween: 10
            },
            1024: {
                slidesPerView: 4,
                spaceBetween: 10
            },
            600: {
                slidesPerView: 3,
                spaceBetween: 10
            },
            480: {
                slidesPerView: 1,
                spaceBetween: 10
            }
        },
        on: {
            observerUpdate:function(){
                var objActive = $(".status-list-2 .swiper-slide-active").find(".con-box").attr("data-tab");
                $(".info-tab-con[data-item='info-tab-02'] .info-box").removeClass("active");
                $(".info-tab-con[data-item='info-tab-02'] .info-box[data-con="+objActive+"]").addClass("active");
            },
            slideChangeTransitionEnd: function () {
                var objActive = $(".status-list-2 .swiper-slide-active").find(".con-box").attr("data-tab");
                $(".info-tab-con[data-item='info-tab-02'] .info-box").removeClass("active");
                $(".info-tab-con[data-item='info-tab-02'] .info-box[data-con="+objActive+"]").addClass("active");
            },
        }
    });

    tabEvent("#section-03 .tab-tit-box > li > a","#se03-tabcon","active");
});


function tabEvent(select,tabcontent,active) {
    $(select).on("click", function(e){
        e.preventDefault();

        var tabItem = $(this).attr("href");
        $(select).removeClass(active);
        $(tabcontent).find(".tab-con").removeClass(active);

        $(this).addClass(active);
        $(tabItem).addClass(active);

    });
}