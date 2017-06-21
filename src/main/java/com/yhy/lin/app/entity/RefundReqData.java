package com.yhy.lin.app.entity;

import java.util.SortedMap;
import java.util.TreeMap;

import org.jeecgframework.core.util.UUIDGenerator;

import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.app.wechat.RequestHandler;

/**
 * Description : 微信退款接口需要的数据
 * 
 * @author Administrator
 * @date 2017年6月21日 下午2:28:56
 */
public class RefundReqData {

	// 每个字段具体的意思请查看API文档
	private String appid = "";
	private String mch_id = "";
	private String nonce_str = "";
	private String sign = "";
	private String transaction_id = "";
	private String out_trade_no = "";
	private String out_refund_no = "";
	private int total_fee = 0;
	private int refund_fee = 0;
	private String op_user_id = "";

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getOut_refund_no() {
		return out_refund_no;
	}

	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}

	public int getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}

	public int getRefund_fee() {
		return refund_fee;
	}

	public void setRefund_fee(int refund_fee) {
		this.refund_fee = refund_fee;
	}

	public String getOp_user_id() {
		return op_user_id;
	}

	public void setOp_user_id(String op_user_id) {
		this.op_user_id = op_user_id;
	}

	/**
	 * 请求退款服务
	 * 
	 * @param transactionID
	 *            是微信系统为每一笔支付交易分配的订单号，通过这个订单号可以标识这笔交易，它由支付订单API支付成功时返回的数据里面获取到。
	 *            建议优先使用
	 * @param outTradeNo
	 *            商户系统内部的订单号,transaction_id 、out_trade_no
	 *            二选一，如果同时存在优先级：transaction_id>out_trade_no
	 * @param outRefundNo
	 *            商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
	 * @param totalFee
	 *            订单总金额，单位为分
	 * @param refundFee
	 *            退款总金额，单位为分
	 */
	public RefundReqData(String transactionID, String outTradeNo, String outRefundNo, int totalFee, int refundFee) {

		// 微信分配的公众号ID（开通公众号之后可以获取到）
		setAppid(AppGlobals.WECHAT_ID);

		// 微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
		setMch_id(AppGlobals.MCH_ID);

		// transaction_id是微信系统为每一笔支付交易分配的订单号，通过这个订单号可以标识这笔交易，它由支付订单API支付成功时返回的数据里面获取到。
		setTransaction_id(transactionID);

		// 商户系统自己生成的唯一的订单号
		setOut_trade_no(outTradeNo);

		setOut_refund_no(outRefundNo);

		setTotal_fee(totalFee);

		setRefund_fee(refundFee);

		setOp_user_id(AppGlobals.MCH_ID);

		// 随机字符串，不长于32 位
		setNonce_str(UUIDGenerator.generate(16));

//		SortedMap<String, Object> parameters = new TreeMap<String, Object>();
//		parameters.put("appid", appid);
//		parameters.put("mch_id", mch_id);
//		parameters.put("nonce_str", nonce_str);
//		parameters.put("transaction_id", transaction_id);
//		parameters.put("out_trade_no", out_trade_no);
//		parameters.put("out_refund_no", out_refund_no);
//		parameters.put("total_fee", total_fee);
//		parameters.put("refund_fee", refund_fee);
//		parameters.put("op_user_id", op_user_id);

//		RequestHandler reqHandler = new RequestHandler(request, response);
		// reqHandler.init(AppGlobals.WECHAT_ID, AppGlobals.WECHAT_APP_SECRET,
		// AppGlobals.WECHAT_KEY);
//		String sign = reqHandler.createSign(parameters);
//		setSign(sign); // 把签名数据设置到Sign这个属性中

	}

	
	public SortedMap<String, String> getParameters() {
		// 根据API给的签名规则进行签名
		SortedMap<String, String> parameters = new TreeMap<String, String>();
		parameters.put("appid", appid);
		parameters.put("mch_id", mch_id);
		parameters.put("nonce_str", nonce_str);
		parameters.put("transaction_id", transaction_id);
		parameters.put("out_trade_no", out_trade_no);
		parameters.put("out_refund_no", out_refund_no);
		parameters.put("total_fee", total_fee + "");
		parameters.put("refund_fee", refund_fee + "");
		parameters.put("op_user_id", op_user_id);
		
		return parameters;
	}

}