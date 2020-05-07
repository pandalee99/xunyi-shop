window.onload = function () {

    var tabs = document.getElementsByClassName("tab");
    var tables = document.getElementsByClassName("table");
    var status_pay = document.getElementsByClassName("status_pay");
    var status_send = document.getElementsByClassName("status_send");
    var status_receive = document.getElementsByClassName("status_receive");
    var status_evaluate = document.getElementsByClassName("status_evaluate");
    var status_complete = document.getElementsByClassName("status_complete");
    //标签 栏目 切换
    for (var i = 0; i < tabs.length; i++) {
        (function (i) {
            tabs[i].onclick = function () {
                for (var j = 0; j < tabs.length; j++) {
                    tabs[j].classList.remove("selected");
                }
                tabs[i].classList.add("selected");
                //全部栏目
                if(i == 0){
                    for (var j = 0; j < status_pay.length; j++) {
                        status_pay[j].style.display = "block";
                    }
                    for (var j = 0; j < status_send.length; j++) {
                        status_send[j].style.display = "block";
                    }
                    for (var j = 0; j < status_receive.length; j++) {
                        status_receive[j].style.display = "block";
                    }
                    for (var j = 0; j < status_evaluate.length; j++) {
                        status_evaluate[j].style.display = "block";
                    }
                    for (var j = 0; j < status_complete.length; j++) {
                        status_complete[j].style.display = "block";
                    }
                }
                //待付款
                if(i == 1){
                    for(var j=0;j<tables.length;j++){
                        tables[j].style.display = "none";
                    }
                    for (var j = 0; j < status_pay.length; j++) {
                        status_pay[j].style.display = "block";
                    }
                }
                //待发货
                if(i == 2){
                    for(var j=0;j<tables.length;j++){
                        tables[j].style.display = "none";
                    }
                    for (var j = 0; j < status_send.length; j++) {
                        status_send[j].style.display = "block";
                    }
                }
                //待收货
                if(i == 3){
                    for(var j=0;j<tables.length;j++){
                        tables[j].style.display = "none";
                    }
                    for (var j = 0; j < status_receive.length; j++) {
                        status_receive[j].style.display = "block";
                    }
                }
                //待评价
                if(i == 4){
                    for(var j=0;j<tables.length;j++){
                        tables[j].style.display = "none";
                    }
                    for (var j = 0; j < status_evaluate.length; j++) {
                        status_evaluate[j].style.display = "block";
                    }
                }
                //已成交
                if(i == 5){
                    for(var j=0;j<tables.length;j++){
                        tables[j].style.display = "none";
                    }
                    for (var j = 0; j < status_complete.length; j++) {
                        status_complete[j].style.display = "block";
                    }
                }
            }
        })(i);
    }
}