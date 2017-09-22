package com.yhy.lin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;

import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.app.util.AppUtil;
import com.yhy.lin.app.wechat.WeixinPayUtil;
import com.yhy.lin.entity.DealerInfoEntity;
import com.yhy.lin.service.DealerInfoServiceI;

import net.sf.json.JSONObject;

/**
 * @Title: Controller
 * @Description: 渠道商信息
 * @author zhangdaihao
 * @date 2017-06-29 17:51:18
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/dealerInfoController")
public class DealerInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DealerInfoController.class);

	@Autowired
	private DealerInfoServiceI dealerInfoService;
	@Autowired
	private SystemService systemService;

	/**
	 * 渠道商信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "dealerInfoList")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("yhy/dealer/dealerInfoList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(DealerInfoEntity dealerInfo, HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {

		/*
		 * String status = request.getParameter("status"); String auditStatus =
		 * request.getParameter("auditStatus");
		 */
		String username = request.getParameter("username");
		String departname = request.getParameter("departname");
		boolean hasPermissionP = checkRole(AppGlobals.PLATFORM_DEALER_AUDIT);
		boolean hasPermissionC = checkRole(AppGlobals.COMMERCIAL_MANAGER);
		JSONObject jObject = dealerInfoService.getDatagrid(dataGrid, dealerInfo, username, hasPermissionP, hasPermissionC, departname);
		
		responseDatagrid(response, jObject);
	}

	/**
	 * 删除渠道商信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(DealerInfoEntity dealerInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		dealerInfo = systemService.getEntity(DealerInfoEntity.class, dealerInfo.getId());
		message = "渠道商信息删除成功";
		dealerInfoService.delete(dealerInfo);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);

		j.setMsg(message);
		return j;
	}

	/**
	 * 添加渠道商信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(DealerInfoEntity dealerInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();

		String departId = ResourceUtil.getSessionUserName().getCurrentDepart().getId();
		String userId = ResourceUtil.getSessionUserName().getId();
		dealerInfo.setCreateUserId(userId);
		dealerInfo.setDepartId(departId);

		if (StringUtil.isNotEmpty(dealerInfo.getId())) {
			message = "渠道商信息更新成功";
			DealerInfoEntity t = dealerInfoService.get(DealerInfoEntity.class, dealerInfo.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(dealerInfo, t);
				dealerInfoService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "渠道商信息更新失败";
			}
		} else {
			message = "渠道商信息添加成功";
			dealerInfo.setCreateDate(AppUtil.getDate());
			dealerInfo.setScanCount(0);
			dealerInfo.setStatus("1");
			dealerInfo.setAuditStatus("-1");
			dealerInfo.setQrCodeUrl("");
			dealerInfo.setDealerFilePath("");

			dealerInfoService.save(dealerInfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 生成二维码
	 * 
	 * @return
	 */
	@RequestMapping(params = "generateQRCode")
	@ResponseBody
	public AjaxJson generateQRCode(DealerInfoEntity dealerInfo, HttpServletRequest req) {
		String message = null;
		AjaxJson j = new AjaxJson();

		dealerInfo = dealerInfoService.getEntity(DealerInfoEntity.class, dealerInfo.getId());
		try {
			// 文件存储路径
			String path = AppGlobals.QR_CODE_FILE_PATH + req.getParameter("id") + ".jpg";
			logger.info("linux全路径：" + AppGlobals.IMAGE_BASE_FILE_PATH + path);
			logger.info("数据库存储路径：" + path);
			WeixinPayUtil.getQRCode(req.getParameter("id"), AppGlobals.IMAGE_BASE_FILE_PATH + path);
			dealerInfo.setQrCodeUrl(path);
			dealerInfoService.saveOrUpdate(dealerInfo);
		} catch (Exception e) {
			message = "服务器异常";
			e.printStackTrace();
		}
		message = "二维码生成成功";
		j.setMsg(message);
		return j;
	}

	/**
	 * 申请停用
	 * 
	 * @return
	 */
	@RequestMapping(params = "dealerDisable")
	@ResponseBody
	public AjaxJson dealerDisable(HttpServletRequest req) {
		String message = null;
		AjaxJson j = new AjaxJson();

		String id = req.getParameter("id");

		DealerInfoEntity dealerInfo = dealerInfoService.getEntity(DealerInfoEntity.class, id);
		try {

			dealerInfo.setCommitApplyDate(AppUtil.getDate());
			dealerInfo.setAuditStatus("0");
			dealerInfo.setApplyType("1");
			dealerInfo.setCommitApplyUser(ResourceUtil.getSessionUserName().getId());

			// 清空审核状态
			dealerInfo.setAuditDate(null);
			dealerInfo.setAuditUser("");
			dealerInfo.setRejectReason("");
			dealerInfo.setLastAuditDate(null);
			dealerInfo.setLastAuditUser("");
			dealerInfo.setLastAuditStatus("");
			dealerInfo.setLastRejectReason("");
			
			dealerInfoService.saveOrUpdate(dealerInfo);
		} catch (Exception e) {
			message = "服务器失败";
			e.printStackTrace();
		}
		message = "申请成功";
		j.setMsg(message);
		return j;
	}

	/**
	 * 提交申请
	 * 
	 * @return
	 */
	@RequestMapping(params = "dealerApply")
	@ResponseBody
	public AjaxJson dealerApply(HttpServletRequest req) {
		String message = null;
		AjaxJson j = new AjaxJson();

		String id = req.getParameter("id");

		DealerInfoEntity dealerInfo = dealerInfoService.getEntity(DealerInfoEntity.class, id);
		try {
			dealerInfo.setCommitApplyDate(AppUtil.getDate());
			dealerInfo.setAuditStatus("0");
			dealerInfo.setApplyType("0");
			dealerInfo.setCommitApplyUser(ResourceUtil.getSessionUserName().getId());

			// 清空审核状态
			dealerInfo.setAuditDate(null);
			dealerInfo.setAuditUser("");
			dealerInfo.setRejectReason("");
			dealerInfo.setLastAuditDate(null);
			dealerInfo.setLastAuditUser("");
			dealerInfo.setLastAuditStatus("");
			dealerInfo.setLastRejectReason("");

			dealerInfoService.saveOrUpdate(dealerInfo);
		} catch (Exception e) {
			message = "服务器失败";
			e.printStackTrace();
		}
		message = "申请成功";
		j.setMsg(message);
		return j;
	}

	/**
	 * 专员列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "getAttacheList")
	public ModelAndView getAttacheList(HttpServletRequest req) {
		String ids = req.getParameter("ids");
		req.setAttribute("ids", ids);
		return new ModelAndView("yhy/dealer/dealerAttacheList");
	}

	/**
	 * 分配专员
	 * 
	 * @return
	 */
	@RequestMapping(params = "allotAttache")
	@ResponseBody
	public AjaxJson allotAttache(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		String message = null;

		String userId = req.getParameter("userId");
		String ids = req.getParameter("ids");

		List<DealerInfoEntity> list = new ArrayList<>();

		try {
			String[] idArr = ids.split(",");
			for (int i = 0; i < idArr.length; i++) {

				DealerInfoEntity dealerInfo = dealerInfoService.getEntity(DealerInfoEntity.class, idArr[i]);
				dealerInfo.setCreateUserId(userId);
				list.add(dealerInfo);
			}
			dealerInfoService.saveAllEntitie(list);
		} catch (Exception e) {
			message = "服务器异常";
			e.printStackTrace();
		}
		message = "审核成功";
		j.setMsg(message);
		return j;
	}

	/**
	 * 同意审核
	 * 
	 * @return
	 */
	@RequestMapping(params = "dealerAgree")
	@ResponseBody
	public AjaxJson dealerAgree(HttpServletRequest req) {
		String message = null;
		AjaxJson j = new AjaxJson();

		String id = req.getParameter("id");

		DealerInfoEntity dealerInfo = dealerInfoService.getEntity(DealerInfoEntity.class, id);

		try {
			String apply = dealerInfo.getApplyType();
			String status = dealerInfo.getAuditStatus();
			String recheck = dealerInfo.getLastAuditStatus();

			if ("0".equals(status)) { // 如果初审状态是待审核状态，则进行初审
				dealerInfo.setAuditDate(AppUtil.getDate());
				dealerInfo.setAuditStatus("1");
				dealerInfo.setAuditUser(ResourceUtil.getSessionUserName().getUserName());
				dealerInfo.setLastAuditStatus("0");
			} else if ("0".equals(recheck)) { // 如果复审状态是待审核状态，则进行复审
				dealerInfo.setLastAuditDate(AppUtil.getDate());
				dealerInfo.setLastAuditStatus("1");
				dealerInfo.setLastAuditUser(ResourceUtil.getSessionUserName().getUserName());
			}

			// 如果是复审，则要改变渠道商合作状态
			if ("0".equals(recheck)) {
				if ("0".equals(apply)) {
					dealerInfo.setStatus("0");
				} else {
					dealerInfo.setStatus("2");
				}
			}

			dealerInfoService.saveOrUpdate(dealerInfo);
		} catch (Exception e) {
			message = "服务器异常";
			e.printStackTrace();
		}
		message = "审核成功";
		j.setMsg(message);
		return j;
	}

	/**
	 * 拒绝审核
	 * 
	 * @return
	 */
	@RequestMapping(params = "dealerReject")
	@ResponseBody
	public AjaxJson dealerReject(HttpServletRequest req) {
		String message = null;
		AjaxJson j = new AjaxJson();

		String id = req.getParameter("id");
		String rejectReason = req.getParameter("rejectReason");

		DealerInfoEntity dealerInfo = dealerInfoService.getEntity(DealerInfoEntity.class, id);
		try {

			String status = dealerInfo.getAuditStatus();
			String recheck = dealerInfo.getLastAuditStatus();

			if ("0".equals(status)) { // 如果初审状态是待审核状态，则进行初审
				dealerInfo.setAuditDate(AppUtil.getDate());
				dealerInfo.setAuditStatus("2");
				dealerInfo.setAuditUser(ResourceUtil.getSessionUserName().getUserName());
				dealerInfo.setRejectReason(rejectReason);
			} else if ("0".equals(recheck)) { // 如果复审状态是待审核状态，则进行复审
				
				dealerInfo.setLastAuditDate(AppUtil.getDate());
				dealerInfo.setLastAuditStatus("2");
				dealerInfo.setLastAuditUser(ResourceUtil.getSessionUserName().getUserName());
				dealerInfo.setLastRejectReason(rejectReason);
			}

			dealerInfoService.saveOrUpdate(dealerInfo);
		} catch (Exception e) {
			message = "服务器异常";
			e.printStackTrace();
		}
		message = "审核成功";
		j.setMsg(message);
		return j;
	}

	// 获取拒绝原因
	@RequestMapping(params = "getReason")
	@ResponseBody
	public AjaxJson getReason(HttpServletRequest request) {

		AjaxJson j = new AjaxJson();

		String id = request.getParameter("id");// id

		DealerInfoEntity t = dealerInfoService.getEntity(DealerInfoEntity.class, id);
		String reasont = "";
		if ("2".equals(t.getAuditStatus())) {
			reasont = t.getRejectReason();
		} else {
			reasont = t.getLastRejectReason();
		}

		j.setSuccess(true);
		j.setMsg(reasont);
		return j;
	}

	/**
	 * 渠道商信息列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(DealerInfoEntity dealerInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(dealerInfo.getId())) {
			dealerInfo = dealerInfoService.getEntity(DealerInfoEntity.class, dealerInfo.getId());
			req.setAttribute("dealerInfoPage", dealerInfo);
		}
		return new ModelAndView("yhy/dealer/dealerInfo");
	}
	
	/**
	 * 渠道商信息列表详情页跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "dealerDetail")
	public ModelAndView dealerDetail(DealerInfoEntity dealerInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(dealerInfo.getId())) {
			dealerInfo = dealerInfoService.getEntity(DealerInfoEntity.class, dealerInfo.getId());
			String userId = dealerInfo.getCommitApplyUser();
			if(StringUtil.isNotEmpty(userId)){
				TSUser user = dealerInfoService.getEntity(TSUser.class, userId);
				req.setAttribute("userName",user.getUserName());
			}
			req.setAttribute("dealerInfoPage", dealerInfo);
		}
		return new ModelAndView("yhy/dealer/dealerDetail");
	}

	/**
	 * 渠道商材料上传跳转页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "dealerUploadFile")
	public ModelAndView dealerUploadFile(HttpServletRequest request) {
		String id = request.getParameter("id");
		request.setAttribute("dealerId", id);
		return new ModelAndView("yhy/dealer/dealerUploadFile");
	}

	int i = 0;

	/**
	 * 保存文件
	 * 
	 * @return
	 */
	@RequestMapping(params = "saveFile")
	@ResponseBody
	public AjaxJson saveFile(HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println(i++);
		AjaxJson j = new AjaxJson();
		j.setSuccess(false);
		
		String id = request.getParameter("did");
		System.out.println(id);
		if (!StringUtil.isNotEmpty(id)) {
			return j;
		}
		
		InputStream input = null;
		FileOutputStream fos = null;
		
		try {
			MultipartHttpServletRequest mRequest = null;
			MultipartFile file = null;
			mRequest = (MultipartHttpServletRequest) request;// request强制转换注意
			file = mRequest.getFile("file");
			
			input = file.getInputStream();
			
			String filePath = AppGlobals.IMAGE_BASE_FILE_PATH + AppGlobals.DEALER_FILE_PATH;
			
			// 文件夹是否存在
			boolean mkDir = false;
			File f = new File(filePath);
			if (!f.isDirectory()) {
				mkDir = f.mkdirs();
			} else {
				mkDir = true;
			}
			
			// String suffix =
			// file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			
			String DBUrl = AppGlobals.DEALER_FILE_PATH + System.currentTimeMillis() + "&&"
					+ file.getOriginalFilename();
			// 一个渠道商只保存一份文件
			// String DBUrl = AppGlobals.DEALER_FILE_PATH + id + suffix;
			
			if (mkDir) {
				fos = new FileOutputStream(AppGlobals.IMAGE_BASE_FILE_PATH + DBUrl);
				int size = 0;
				byte[] buffer = new byte[1024];
				while ((size = input.read(buffer, 0, 1024)) != -1) {
					fos.write(buffer, 0, size);
				}
				
				DealerInfoEntity dealerInfo = systemService.get(DealerInfoEntity.class, id);
				File df = new File(AppGlobals.IMAGE_BASE_FILE_PATH + dealerInfo.getDealerFilePath());
				if (df.exists()) {
					df.delete();
				}
				dealerInfo.setDealerFilePath(DBUrl);
				systemService.saveOrUpdate(dealerInfo);
				j.setSuccess(true);
				j.setMsg("上传文件成功");
			}

		} catch (Exception e) {
			j.setMsg("服务器异常");
			e.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
				if (input != null)
					input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return j;
	}

	/**
	 * 下载文件
	 * 
	 * @return
	 */
	@RequestMapping(params = "fileDown")
	@ResponseBody
	public void fileDown(HttpServletRequest request, HttpServletResponse response) {

		String id = request.getParameter("did");

		DealerInfoEntity dealerInfo = systemService.get(DealerInfoEntity.class, id);

		String fileName = AppGlobals.IMAGE_BASE_FILE_PATH + dealerInfo.getDealerFilePath(); // 原来文件的路径
		String filepath = dealerInfo.getDealerFilePath()
				.substring(dealerInfo.getDealerFilePath().lastIndexOf("&&") + 2); // 修改后的文件名

		// 新建文件输入输出流
		OutputStream output = null;
		FileInputStream fis = null;
		try {
			response.setHeader("Content-Disposition",
					"attachment; filename=" + new String(filepath.getBytes("utf-8"), "iso-8859-1"));
			// 新建File对象
			File f = new File(fileName);
			// 新建文件输入输出流对象
			output = response.getOutputStream();
			fis = new FileInputStream(f);
			// 设置每次写入缓存大小
			byte[] b = new byte[(int) f.length()];
			// out.print(f.length());
			// 把输出流写入客户端
			int i = 0;
			while ((i = fis.read(b)) > 0) {
				output.write(b, 0, i);
				System.out.println("打印" + i);
			}
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
				if (output != null)
					output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 检查公司社会信用代码是否存在
	 */
	@RequestMapping(params = "checkCreditCode")
	@ResponseBody
	public AjaxJson checkCreditCode(HttpServletRequest request) {
		String message = "";
		boolean success = false;
		AjaxJson j = new AjaxJson();
		try {
			String creditCode = request.getParameter("creditCode");
			List<DealerInfoEntity> list = this.systemService
					.findHql("from DealerInfoEntity where creditCode=? and status !=2 ", creditCode);
			if (list.size() > 0) {
				message = "公司社会信用代码已经存在";
				success = false;
			} else {
				message = "添加社会信用代码成功！";
				success = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);

		j.setSuccess(success);
		j.setMsg(message);
		return j;
	}
}
