//js增强组件，实现popup选择用户
//窗口宽度
var windowWidth_user = 1000;
//窗口高度
var windowHeight_user = 600;
//已选择用户输入框宽度
var userNameInputWidth_user = 80;
//用于记录隐藏的input的id
var selectedNamesInputId_driver = "driverId";
//用于展示在界面上的input的id
var selectedNamesInputName_driver = "driverName";
//组件名称
var lblName_user = "真实姓名";
//弹出框标头
var title_user = "用户列表";
UserSelectApi = {};
UserSelectApi.popup = function(key){
    /*var first = key.substring(0,1);
    if(first == "#"){
        //id选择器
        $("div[name='searchColums']").append($(key).html());
        $(key).remove();
        /!*$(key).find("input").on("click",function(){
            openUserSelect_user();
        });*!/
    }else if(first == "."){
        //类选择器
    }else{
        //标签名
    }*/
    $("div[name='searchColums']").append($(key).html());
    $(key).remove();
};
function openDriverSelect_driver() {
    $.dialog.setting.zIndex = 9999;
    $.dialog({content: 'url:lineArrangeController.do?addDriver', zIndex: 2100, title_user: title_user, lock: true, width:windowWidth_user, height:windowHeight_user, opacity: 0.4, button: [
        {name:'确定',callback:callbackDriverSelect_driver,focus:true},
        {name:'取消',callback:function (){}}
    ]}).zindex();
}
function callbackDriverSelect_driver(){
    var iframe = this.iframe.contentWindow;
    var rowsData = iframe.$('#driversInfoList').datagrid('getSelections');
    if (!rowsData || rowsData.length==0) {
        alert("必须选择一个司机");
        return;
    }
    var name = rowsData[0].name;
    var id = rowsData[0].id;
    $("#"+selectedNamesInputId_driver).val(id);
    $("#"+selectedNamesInputName_driver).val(name);
    $("#"+selectedNamesInputId_driver).blur();
    $("#"+selectedNamesInputName_driver).blur();
}