window.onload = function () {

    var bigImg = document.getElementById("bigImg");
    var imgs = document.getElementsByClassName("imgs");
    var imgs_url = init(imgs);
    var sizes = document.getElementsByClassName("sp");
    var colors = document.getElementsByClassName("image");
    var amount = document.getElementById("amount");
    var arrowup = document.getElementById("arrowup");
    var arrowdown = document.getElementById("arrowdown");
    var detailBtn = document.getElementById("detail");
    var evaluateBtn = document.getElementById("evaluate");
    var details = document.getElementById("details");
    var evaluates = document.getElementById("evaluates");

    detailBtn.onclick = function () {
        detailBtn.classList.add("selected");
        details.style.display = "block";
        evaluateBtn.classList.remove("selected");
        evaluates.style.display = "none";
    }

    evaluateBtn.onclick = function () {
        evaluateBtn.classList.add("selected");
        evaluates.style.display = "block";
        detailBtn.classList.remove("selected");
        details.style.display = "none";
    }

    //数量 +
    arrowdown.onclick = function () {
        var num = parseInt(amount.value);
        if (num != 1) {
            num--;
        }
        amount.value = num;
    }
    //数量 -
    arrowup.onclick = function () {
        var num = parseInt(amount.value);
        num++;
        amount.value = num;
    }
    //尺码
    for (var i = 0; i < sizes.length; i++) {
        (function (i) {
            sizes[i].onclick = function () {
                for (var j = 0; j < sizes.length; j++) {
                    sizes[j].style.border = "1px solid rgb(184, 183, 189)";
                }
                sizes[i].style.border = "1px solid red";
            }
        })(i);
    }
    //颜色
    for (var i = 0; i < colors.length; i++) {
        (function (i) {
            colors[i].onclick = function () {
                for (var j = 0; j < colors.length; j++) {
                    colors[j].style.border = "1px solid rgb(184, 183, 189)";
                }
                colors[i].style.border = "1px solid red";
            }
        })(i);
    }
    //商品示例图片
    for (var i = 0; i < imgs.length; i++) {
        (function (i) {
            imgs[i].onmouseover = function () {
                bigImg.setAttribute("src", imgs_url[i]);
            }
        })(i);
    }

    function init(obj) {
        var list = [];
        for (var i = 0; i < obj.length; i++) {
            list[i] = imgs[i].getAttribute("src");
        }
        return list;
    }

    //初始化判断旗帜数组
    function init_Flags(checks) {
        var list = [];
        for (var i = 0; i < checks.length; i++) {
            list[i] = false;
        }
        return list;
    }
}