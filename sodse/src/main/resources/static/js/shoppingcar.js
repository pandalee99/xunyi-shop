window.onload = function () {

    var checkAll = document.getElementsByClassName('checkAll');
    var checkAll_flag = false;

    var checks = document.getElementsByClassName('check');
    var checks_flag = initChecks_Flags(checks);

    var reduceBtn = document.getElementsByClassName('reduce');
    var addBtn = document.getElementsByClassName('add');
    var count = document.getElementsByClassName('count');

    var price = document.getElementsByClassName('price');
    var total = document.getElementsByClassName('total');


    //添加商品数量
    for (var i = 0; i < reduceBtn.length; i++) {
        (function (i) {
            reduceBtn[i].onclick = function () {
                var num = parseInt(count[i].value);
                if (num != 1) {
                    num--;
                }
                count[i].value = num;
                getSubTotal(i);
                getTotalPrice(checks_flag);
            }
        })(i);
    }
    //减少商品数量
    for (var i = 0; i < addBtn.length; i++) {
        (function (i) {
            addBtn[i].onclick = function () {
                var num = parseInt(count[i].value);
                num++;
                count[i].value = num;
                getSubTotal(i);
                getTotalPrice(checks_flag);
            }
        })(i);
    }
    //全选按钮
    for (var i = 0; i < checkAll.length; i++) {
        checkAll[i].onclick = function () {
            if (checkAll_flag == false) {
                for (var j = 0; j < checks.length; j++) {
                    setSrc(checks[j], !checkAll_flag);
                }
                for (var k = 0; k < checkAll.length; k++) {
                    setSrc(checkAll[k], !checkAll_flag);
                }
                for (var m = 0; m < checks.length; m++) {
                    checks_flag[m] = true;
                }
                checkAll_flag = true;
            } else {
                for (var j = 0; j < checks.length; j++) {
                    setSrc(checks[j], !checkAll_flag);
                }
                for (var k = 0; k < checkAll.length; k++) {
                    setSrc(checkAll[k], !checkAll_flag);
                }
                for (var m = 0; m < checks.length; m++) {
                    checks_flag[m] = false;
                }
                checkAll_flag = false;
            }
            getItems(checks_flag);
            getTotalPrice(checks_flag);
        }
    }
    //选择按钮
    for (var i = 0; i < checks.length; i++) {
        (function (i) {
            checks[i].onclick = function () {
                if (checks_flag[i] == false) {
                    this.setAttribute('src', '/static/img/cartSelected.png');
                    checks_flag[i] = true;
                } else {
                    this.setAttribute('src', '/static/img/cartNotSelected.png');
                    checks_flag[i] = false;
                }
                getItems(checks_flag);
                getTotalPrice(checks_flag);
            }
        })(i);
    }
    //设置选择图片
    function setSrc(object, flag) {
        if (flag) {
            object.setAttribute('src', '/static/img/cartSelected.png');
        } else {
            object.setAttribute('src', '/static/img/cartNotSelected.png');
        }
    }
    //初始化判断旗帜数组
    function initChecks_Flags(checks) {
        var list = [];
        for (var i = 0; i < checks.length; i++) {
            list[i] = false;
        }
        return list;
    }
    //小计
    function getSubTotal(index) {
        var price = document.getElementsByClassName('price');
        var total = document.getElementsByClassName('total');
        var count = document.getElementsByClassName('count');

        var price = parseFloat(price[index].innerHTML);
        var count = parseInt(count[index].value);

        var subTotal = parseFloat(price * count);

        total[index].innerHTML = subTotal.toFixed(2);
    }
    //获得已选数量
    function getItems(checks_flag) {
        var itemsNub = document.getElementById('itemsNum');
        var num = 0;
        for (var i = 0; i < checks_flag.length; i++) {
            if (checks_flag[i] == true) {
                num++;
            }
        }
        itemsNub.innerHTML = num;
    }

    function getTotalPrice(checks_flag) {
        var totalprice = document.getElementById('totalprice');
        var items = document.getElementsByClassName('total');
        var money = 0;
        for (var i = 0; i < checks_flag.length; i++) {
            if (checks_flag[i] == true) {
                var price = parseFloat(items[i].innerHTML);
                money += price;   
            }
        }
        totalprice.innerHTML = money.toFixed(2);
    }
}