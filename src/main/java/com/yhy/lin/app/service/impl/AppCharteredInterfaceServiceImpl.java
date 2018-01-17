package com.yhy.lin.app.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yhy.lin.app.entity.AppAppendServiceEntity;
import com.yhy.lin.app.entity.AppCharteredPackageEntity;
import com.yhy.lin.app.entity.AppCharteredPackagePriceEntity;
import com.yhy.lin.app.entity.AppCharteredPriceEntity;
import com.yhy.lin.app.entity.AppCharteredaCarTypeEntity;
import com.yhy.lin.app.service.AppCharteredInterfaceService;
import com.yhy.lin.entity.CharteredPackageEntity;
import com.yhy.lin.entity.CharteredPriceEntity;

/**
* Description : 
* @author Administrator
* @date 2017年10月19日 下午4:10:53
*/
@Service("AppCharteredInterfaceService")
@Transactional
public  class AppCharteredInterfaceServiceImpl extends CommonServiceImpl implements AppCharteredInterfaceService {

	@Autowired
	private SystemService systemService;

	@Override
	public List<AppCharteredPriceEntity> getPackagePrice(String packageId, String carType, String charteredType,String actualMileage) {
		// TODO Auto-generated method stub
		StringBuffer str= new StringBuffer();
		str.append("select c.id,c.car_type,c.package_id,c.initiate_price,c.exceed_km_price,c.empty_return,p.kilometre,"
				+ "d.departname,d.org_code from chartered_price c LEFT JOIN chartered_package p on c.package_id=p.id LEFT JOIN "
				+ "t_s_base_user u on c.create_user_id = u.id LEFT JOIN t_s_user_org o on o.user_id=u.ID LEFT JOIN  t_s_depart t "
				+ "on o.org_id=t.ID,t_s_depart d where c.delete_flag='0' and (case when LENGTH(t.org_code)<6 then t.org_code else "
				+ "substring(t.org_code,1,6) END)=d.org_code");
		if (StringUtil.isNotEmpty(carType)) {
			str.append(" and c.car_type='"+carType+"'");	
		}else{
			str.append(" and c.car_type='0'");
		}

		if (StringUtil.isNotEmpty(packageId)) {
			str.append(" and c.package_id='"+packageId+"'");
		} 
		List<AppCharteredPriceEntity> appchelist = new ArrayList<AppCharteredPriceEntity>();
		List<Object> pricelist = this.systemService.findListbySql(str.toString());
		BigDecimal sumprice = new BigDecimal("0");
		if(pricelist.size()>0){
			for (int i = 0; i < pricelist.size(); i++) {
				Object[] ob = (Object[]) pricelist.get(i);
				AppCharteredPriceEntity chartPrice = new AppCharteredPriceEntity();
				String peiceId = ob[0].toString();
				String carTypes=ob[1].toString();
				String initiatePrice = ob[3].toString();
				String exceedKmPrice = ob[4].toString();
				String emptyReturn = ob[5].toString();
				String kilometre = ob[6].toString();
				String companyname = ob[7].toString();
				String departCode = ob[8].toString();
				
				chartPrice.setDepartCode(departCode);
				//chartPrice.setPackagePriceId(peiceId);
				chartPrice.setCompanyName(companyname);
				chartPrice.setCarType(carTypes);
				
				//计算出总价格
				sumprice = getSumPrice(charteredType,actualMileage,initiatePrice,exceedKmPrice,emptyReturn,kilometre);
				chartPrice.setPrice(sumprice.toString());
				
				List<String> brandlist =getCarBrand(carTypes);//获取车辆品牌
				if(brandlist.size()>0){
					chartPrice.setCarBrand(brandlist.toString());//车牌
				}
				
				List<AppAppendServiceEntity> servicelist = getAppAppendService(peiceId);//获取服务项内容
				if(servicelist.size()>0){
					chartPrice.setServiceId(servicelist);
				}
				
				appchelist.add(chartPrice);
				
			}
		}
		
		return appchelist;
	}

	/**
	 * 	获取价格
	 * @param charteredType
	 * @param actualMileage
	 * @param initiatePrice
	 * @param exceedKmPrice
	 * @param emptyReturn
	 * @param kilometre
	 * @return
	 */
	public BigDecimal getSumPrice(String charteredType,String actualMileage,String initiatePrice,String exceedKmPrice,String emptyReturn,String kilometre){
		BigDecimal sumprice = new BigDecimal("0");
		BigDecimal actual = new BigDecimal(actualMileage);//实际公里数
		BigDecimal initiatePrices = new BigDecimal(initiatePrice);//起步价
		BigDecimal exceedKmPrices = new BigDecimal(exceedKmPrice);//超公里/元
		BigDecimal emptyReturns = new BigDecimal(emptyReturn);//空反费（公里/元）
		BigDecimal kilometres = new BigDecimal(kilometre);//基础公里数
		
		if("0".equals(charteredType)){//单程
			//0<=实际公里数<=基础公里数/2
			if(Double.parseDouble(kilometre)/2>=Double.parseDouble(actualMileage) && Double.parseDouble(actualMileage)>=0){
				sumprice=initiatePrices;//起步价
			//基础公里数/2<实际公里数<=基础公里数
			}else if(Double.parseDouble(kilometre)/2<Double.parseDouble(actualMileage) && Double.parseDouble(actualMileage)<=Double.parseDouble(kilometre)){
				BigDecimal num = actual.multiply(new BigDecimal("2")).subtract(kilometres);//ax2-基础公里数
				BigDecimal multiply = num.multiply(emptyReturns);//(ax2-基础公里数)x空返费
				sumprice=initiatePrices.add(multiply);//起步价+(ax2-基础公里数)x空返费
			//实际公里数>基础公里数
			}else if(Double.parseDouble(actualMileage)>Double.parseDouble(kilometre)){
				BigDecimal num =actual.subtract(kilometres);//a-基础公里数
				BigDecimal multiply = num.multiply(exceedKmPrices);//(a-基础公里数)x超公里价格
				BigDecimal num1 = actual.multiply(emptyReturns);//ax空返费
				sumprice=initiatePrices.add(multiply).add(num1);//起步价+(a-基础公里数)x超公里价格+ax空返费
			}
		}else if("1".equals(charteredType)){//往返
			//0<=实际公里数<=基础公里数/2
			if(Double.parseDouble(kilometre)/2>=Double.parseDouble(actualMileage) && Double.parseDouble(actualMileage)>=0){
				sumprice=initiatePrices;//起步价
			//基础公里数/2<实际公里数<=基础公里数
			}else if(Double.parseDouble(kilometre)/2<Double.parseDouble(actualMileage) && Double.parseDouble(actualMileage)<=Double.parseDouble(kilometre)){
				sumprice=initiatePrices;//起步价
			//实际公里数>基础公里数
			}else if(Double.parseDouble(actualMileage)>Double.parseDouble(kilometre)){
				BigDecimal num =actual.subtract(kilometres);//a-基础公里数
				BigDecimal multiply = num.multiply(exceedKmPrices);//(a-基础公里数)x超公里价格
				sumprice=initiatePrices.add(multiply);//起步价+(a-基础公里数)x超公里价格+ax空返费
			}
		}
	
		return sumprice;
	}
	
	/**
	 * 获取包车服务
	 * @param peiceId
	 * @return
	 */
	public List<AppAppendServiceEntity> getAppAppendService(String peiceId){
		List<AppAppendServiceEntity> appapplist = new ArrayList<>();
		
		String sql ="SELECT cs.service_name,cs.service_description from chartered_append_service cs LEFT JOIN chartered_price_append_service ps on cs.id="
				+ "ps.append_service_id where ps.chartered_price_id='"+peiceId+"'";
		
		List<Object> appendServicelist = this.systemService.findListbySql(sql);
		
		if(appendServicelist.size()>0){
			for (int i = 0; i < appendServicelist.size(); i++) {
				Object[] ob = (Object[]) appendServicelist.get(i);
				String serviceName = ob[0].toString();
				String description = ob[1].toString();
				AppAppendServiceEntity aas = new AppAppendServiceEntity();
				aas.setName(serviceName);
				aas.setDescription(description);
				appapplist.add(aas);
			}
		}
		return appapplist;
	}
	
	/**
	 * 获取车辆品牌
	 * @param carType
	 * @return
	 */
	public List<String> getCarBrand(String carType){
		List<String> brandlist = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("select t.typename from car_info c LEFT JOIN t_s_type t on c.car_brand=t.typecode LEFT JOIN t_s_typegroup p on "
				+ "t.typegroupid=p.ID where p.typegroupcode='carBrand' and c.delete_flag=0 and c.business_type='2' and c.car_status='0'");
		
		if (StringUtil.isNotEmpty(carType)){
			sql.append(" and c.car_type='"+carType+"'");
		}
		
		List<String> carlist  = systemService.findListbySql(sql.toString());
		
		if(carlist.size()>0){
			for (int i = 0; i < carlist.size(); i++) {
				String carBrand= carlist.get(i);
				brandlist.add(carBrand);
			}
		}
		
		return brandlist;
	}

	/**
	 * 获取收费详情
	 */
	@Override
	public List<AppCharteredPackageEntity> getChargesDetails(String departCode) {
		// TODO Auto-generated method stub
		List<AppCharteredPackageEntity> appPackagePricelist = new ArrayList<>();
		StringBuffer sql  = new StringBuffer();
		sql.append("select p.id,p.name,p.kilometre,p.time_length,d.departname from chartered_package p  LEFT JOIN t_s_base_user u"
				+ " on p.create_user_id = u.id LEFT JOIN t_s_user_org o on o.user_id=u.ID LEFT JOIN  t_s_depart t on o.org_id=t.ID,"
				+ "t_s_depart d where p.delete_flag='0' and p.status='0' and (case when LENGTH(t.org_code)<6 then t.org_code else"
				+ " substring(t.org_code,1,6) END)=d.org_code and d.org_code like '%A02A10'");
		sql.append(" and d.org_code like '%"+departCode+"'");
		
		List<Object> applist = systemService.findListbySql(sql.toString());
		List<AppCharteredaCarTypeEntity> charteredPackages = new ArrayList<>();
		List<AppCharteredaCarTypeEntity> charteredPackagelist = new ArrayList<>();
		AppCharteredPackageEntity appCharteredPackage = new AppCharteredPackageEntity();
		if(applist.size()>0){
			Object[] obj = (Object[])applist.get(0);
			String companyName = obj[4].toString();
			appCharteredPackage.setCompanyName(companyName);
			
			
			for (int i = 0; i < applist.size(); i++) {
				Object[] ob = (Object[]) applist.get(i);
				
				String packid = ob[0].toString();
				String packName = ob[1].toString();
				String kilometre = ob[2].toString();
				String timeLength = ob[3].toString();
				charteredPackages = getCharteredPackages(packid,packName,kilometre,timeLength);
				charteredPackagelist.addAll(charteredPackages);
			}
			appCharteredPackage.setCharteredPackages(charteredPackagelist);
			appPackagePricelist.add(appCharteredPackage);
		}
		return appPackagePricelist;
	}
	
	/**
	 * 获取套餐
	 * @param packid
	 * @param packName
	 * @param kilometre
	 * @param timeLength
	 * @return
	 */
	public List<AppCharteredaCarTypeEntity> getCharteredPackages(String packid,String packName,String kilometre,String timeLength){
		
		List<AppCharteredaCarTypeEntity> charteredPackages = new ArrayList<>();
		AppCharteredaCarTypeEntity appCharteredaCarTypeEntity = new AppCharteredaCarTypeEntity();
		appCharteredaCarTypeEntity.setCharteredName(packName);
		
		if (StringUtil.isNotEmpty(packid)) {
			List<CharteredPriceEntity> packlist = systemService.findHql(" from CharteredPriceEntity where packageId=? and deleteFlag=? and status=?", packid,0,"1");
			List<AppCharteredPackagePriceEntity> packageDetails = getPackageDetails(packlist,kilometre,timeLength);
			appCharteredaCarTypeEntity.setPackageDetails(packageDetails);
			charteredPackages.add(appCharteredaCarTypeEntity);
		}
		
		return charteredPackages;
	}
	
	/**
	 * 获取套餐价格详情
	 * @param packlist
	 * @param kilometre
	 * @param timeLength
	 * @return
	 */
	public List<AppCharteredPackagePriceEntity> getPackageDetails(List<CharteredPriceEntity> packlist,String kilometre,String timeLength){
		
		List<AppCharteredPackagePriceEntity> appCharteredPackagePricelist = new ArrayList<>();
		AppCharteredPackagePriceEntity appCharteredPackagePrice = new AppCharteredPackagePriceEntity();
		if(packlist.size()>0){
			for (int i = 0; i < packlist.size(); i++) {
				appCharteredPackagePrice.setCarType(packlist.get(i).getCarType());
				appCharteredPackagePrice.setEmptyReturn(packlist.get(i).getEmptyReturn());
				appCharteredPackagePrice.setExceedKmPrice(packlist.get(i).getExceedKmPrice());
				appCharteredPackagePrice.setExceedTimePrice(packlist.get(i).getExceedTimePrice());
				appCharteredPackagePrice.setInitiatePrice(packlist.get(i).getInitiatePrice());
				appCharteredPackagePrice.setKilometre(Integer.parseInt(kilometre));
				appCharteredPackagePrice.setTimeLength(Integer.parseInt(timeLength));
				appCharteredPackagePricelist.add(appCharteredPackagePrice);
			}
		}
		return appCharteredPackagePricelist;
	}

}
