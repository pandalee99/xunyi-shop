document.write('<script src="/static/plugin/vue/vue.min.js"  type="text/javascript"></script>');



function search(){
    var obj=inf.obj.value
    if (obj==null){
        alert("搜索内容为空")
        return false
    }
    inf.submit()
}
