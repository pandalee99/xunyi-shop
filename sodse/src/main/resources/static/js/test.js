$(document).ready(function () {
    $(".nav li").hover(
        function () {
            $(this).children(".down").stop(true, true).slideDown("fast");
            $(this).find("h2").addClass("nav_this_2");
        },
        function () {
            $(this).children(".down").stop(true, true).slideUp("fast");
            $(this).find("h2").removeClass("nav_this_2");
        }
    );

    $(".nav_2 li").hover(
        function () {
            $(this).children(".down_2").stop(true, true).slideDown("fast");
            $(this).find("h2").addClass("nav_this_2");
        },
        function () {
            $(this).children(".down_2").stop(true, true).slideUp("fast");
            $(this).find("h2").removeClass("nav_this_2");
        }
    );

    $(".nav_2 dl").hover(
        function () {
            $(this).children("dd").stop(true, true).fadeIn();
        },
        function () {
            $(this).children("dd").stop(true, true).fadeOut();
        }
    );

});