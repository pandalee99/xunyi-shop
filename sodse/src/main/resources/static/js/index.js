window.onload = function () {
    autoMove('image', 'tag');
}

function autoMove(imgName, spanName) {
    var imgs = document.getElementsByName(imgName);
    var spans = document.getElementsByName(spanName);

    function initMove(index) {

        for (var i = 0; i < imgs.length; i++) {
            imgs[i].style.opacity = '0';
            spans[i].style.backgroundColor = 'rgb(163, 163, 163)';
        }

        imgs[index].style.opacity = '1';
        spans[index].style.backgroundColor = '#ffffff';

    }

    initMove(0);

    var count = 1;

    function fMove() {
        if (count == imgs.length) {
            count = 0;
        }
        initMove(count);
        count++;
    }

    var b1 = document.getElementById('tag1');
    var b2 = document.getElementById('tag2');
    var b3 = document.getElementById('tag3');
    var b4 = document.getElementById('tag4');
    var b5 = document.getElementById('tag5');

    b1.onclick = function(){
        clearInterval(scollMove);
        initMove(0);
        count = 1;
        scollMove = setInterval(fMove,2000);
    }

    b2.onclick = function(){
        clearInterval(scollMove);
        initMove(1);
        count = 2;
        scollMove = setInterval(fMove,2000);
    }

    b3.onclick = function(){
        clearInterval(scollMove);
        initMove(2);
        count = 3;
        scollMove = setInterval(fMove,2000);
    }

    b4.onclick = function(){
        clearInterval(scollMove);
        initMove(3);
        count = 4;
        scollMove = setInterval(fMove,2000);
    }

    b5.onclick = function(){
        clearInterval(scollMove);
        initMove(4);
        count = 5;
        scollMove = setInterval(fMove,2000);
    }

    var scollMove = setInterval(fMove, 2000);
}