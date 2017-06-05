/**
 * Created by 一合鱼 on 2017-05-26.
 */
//手机号验证函数
function phoneCheck(id) {
    var phone = $.trim($("#uNum").val());
    var regPhone = /^(13[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/;
    if (!phone) {//用户名为空
        error("手机号不能为空");
        return false;
    } else if (!regPhone.test(phone)) {//格式不正确时
        error("手机号格式不正确");
        return false;
    } else {
        return true;
    }
}