$(document).ready(function(){
	"use strict";
    
/*==================================
* Author        : "ThemeSine"
* Template Name :  HTML Template
* Version       : 1.0
==================================== */




/*=========== TABLE OF CONTENTS ===========
1. Scroll To Top 
======================================*/

    // 1. Scroll To Top 
		$(window).on('scroll',function () {
			if ($(this).scrollTop() > 600) {
				$('.return-to-top').fadeIn();
			} else {
				$('.return-to-top').fadeOut();
			}
		});
		$('.return-to-top').on('click',function(){
				$('html, body').animate({
				scrollTop: 0
			}, 1500);
			return false;
		});
	$('#signup').click(function () {
		var firstName = $("#firstName").val();
		var lastName = $("#lastName").val();
		var email = $("#email").val();
		var password = $("#password").val();
		var retypePassword = $("#retypePassword").val();
		if(password&&retypePassword&&retypePassword===password){
			$.cookie("firstName",firstName,{ expires: 1 });
			$.cookie("lastName",lastName,{ expires: 1 });
			$.cookie("email",email,{ expires: 1 });
			$.cookie("password",password,{ expires: 1 });
			window.location = 'signin.html';
		}
		else{
			$("#tip").html("两次密码输入不一致!");
			$("#tip").css("visibility","visible");
		}

	})

	$("#signin").click(function () {
		var email = $("#email").val();
		var password = $("#password").val();
		var isRemember = $("#isRemember").prop("checked");
		$.cookie("isRemember",isRemember,{ expires: 1 });
		var firstName = $.cookie("firstName");
		var lastName = $.cookie("lastName");
		var email1 = $.cookie("email");
		var password1 = $.cookie("password");
		if(email && email1 && password && password1 && email === email1 && password === password1){
			$.cookie("isSignin",true,{ expires: 1 });
			window.location = 'index.html';
		}
		else if(email === "lyn@123.com" && password === "123456"){
			$.cookie("isSignin",true,{ expires: 1 });
			window.location = 'index.html';
		}
		else{
			$("#tip").html("邮箱或密码错误!");
			$("#tip").css("visibility","visible");
		}
	})
});	
	