//js增强组件，实现popup选择用户
//窗口宽度
var windowWidth_user = 1000;
//窗口高度
var windowHeight_user = 600;
//已选择用户输入框宽度
var userNameInputWidth_user = 80;
//用于记录隐藏的input的id
var selectedNamesInputId_car = "licencePlateId";
//用于展示在界面上的input的id
var selectedNamesInputName_car = "licencePlateName";
//用于展示在界面上的座位数的id
var selectedNamesInputName_seat = "seat";

//司机名称
var selectedDriver_id = "driverId";
//司机id
var selectedDriver_name = "driverName";

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
function openCarSelect_car() {
	var lpId = $("#prevLicencePlateId").val();
    $.dialog.setting.zIndex = 10000;
    $.dialog({content: 'url:lineArrangeController.do?addCar&lpId='+lpId, zIndex: 10000, title_user: title_user, lock: true, width:windowWidth_user, height:windowHeight_user, opacity: 0.4, button: [
        {name:'确定',callback:callbackCarSelect_car,focus:true},
        {name:'取消',callback:function (){}}
    ]}).zindex();
}
function callbackCarSelect_car(){
    var iframe = this.iframe.contentWindow;
    var rowsData = iframe.$('#carInfoList').datagrid('getSelections');
    if (!rowsData || rowsData.length==0) {
        //alert("必须选择一条信息");
        return;
    }else{
    	var licencePlate = rowsData[0].licencePlate;
    	var id = rowsData[0].id;
    	var seat = rowsData[0].seat;
    	//司机姓名和id
    	var name = rowsData[0].name;
    	var driverId = rowsData[0].driverId;
    	$("#"+selectedNamesInputId_car).val(id);
    	$("#"+selectedNamesInputName_car).val(licencePlate);
    	$("#"+selectedNamesInputName_seat).val(seat);
    	$("#"+selectedNamesInputId_car).blur();
    	$("#"+selectedNamesInputName_car).blur();
    	$("#"+selectedNamesInputName_seat).blur();
    	
    	$("#"+selectedDriver_id).val(driverId);
    	$("#"+selectedDriver_name).val(name);
    	$("#"+selectedDriver_id).blur(driverId);
    	$("#"+selectedDriver_name).blur(name);
    	
    }
}