/**
 * Created by һ���� on 2017-05-26.
 */
//�ֻ�����֤����
function phoneCheck(id) {
    var phone = $.trim($("#uNum").val());
    var regPhone = /^(13[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/;
    if (!phone) {//�û���Ϊ��
        error("�ֻ��Ų���Ϊ��");
        return false;
    } else if (!regPhone.test(phone)) {//��ʽ����ȷʱ
        error("�ֻ��Ÿ�ʽ����ȷ");
        return false;
    } else {
        return true;
    }
}