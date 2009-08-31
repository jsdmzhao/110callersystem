jQuery(document).ready(function(){
    jQuery('#dock').jqDock();
    jQuery('body').css({
        overflow: "hidden"
    });
    
    //initial hiding of dashboard + addition of 'closeZone'
    jQuery('#dashboardWrapper').css({
        position: 'absolute',
        top: '0px',
        left: '0px',
        width: '100%',
        height: '100%',
        opacity: '0'
    }).hide().append('<div id="closeZone"></div>');
    
    //Position, and hiding of 'CloseZone'
    jQuery('#closeZone').css({
        position: 'absolute',
        top: '0px',
        left: '0px',
        width: '100%',
        height: '100%',
        opacity: '0.1',
        background: '#000'
    })
    
    //Launch Dashboard + initiation of 'closeZone'
    jQuery('#dashboardLaunch').click(function(){
        jQuery('#dashboardWrapper').animateShow();
    });
    
    //closeZone's job: closing the Dashboard
    jQuery('#closeZone').click(function(){
        jQuery('#dashboardWrapper').animateHide();
    });

});

jQuery.extend({
    min: function(x,y){
		if(x>y){return y;}
		
		return x;
    },
	max:function(x,y){
		if(x<y){return y;}
		return x;
	}
});

jQuery.fn.extend({
	animateShow:function(){
		this.each(function(){
			$(this).show().animate({opacity:'1'},300);
		});
	},
	animateHide:function(){
		this.each(function(){
            $(this).animate({opacity:'0'},300).hide(1);
        });
	}
});
