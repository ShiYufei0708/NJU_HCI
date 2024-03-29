package com.example.hotel.blImpl.saler;

import com.example.hotel.bl.saler.SalerService;
import com.example.hotel.data.AD.ADMapper;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.data.saler.SalerMapper;
import com.example.hotel.po.AD;
import com.example.hotel.po.Coupon;
import com.example.hotel.po.SalerCoupon;
import com.example.hotel.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class SalerServicelmpl implements SalerService {

    @Autowired
    SalerMapper salerMapper;


    @Autowired
    ADMapper adMapper;

    @Override
    public List<SalerCoupon> getWebAllCoupon()
    {
        return salerMapper.getWebCoupons();
    }

    @Override
    public SalerCouponVO addWebCoupon(SalerCouponVO salercouponVO) {
        SalerCoupon salercoupon = new SalerCoupon();
        salercoupon.setCouponName(salercouponVO.getName());
        salercoupon.setDescription(salercouponVO.getDescription());
        salercoupon.setCouponType(salercouponVO.getType());
        salercoupon.setTargetMoney(salercouponVO.getTargetMoney());
        salercoupon.setHotelId(salercouponVO.getHotelId());
        salercoupon.setDiscountMoney(salercouponVO.getDiscountMoney());
        salercoupon.setStartTime(salercouponVO.getStartTime());
        salercoupon.setEndTime(salercouponVO.getEndTime());
        salercoupon.setStatus(1);
        int result = salerMapper.insertSalerCoupon(salercoupon);
        salercouponVO.setId(result);
        return salercouponVO;
    }

    @Override
    public void updateSalerCouponInfo(SalerCouponVO salercouponVO){
        SalerCoupon salercoupon=new SalerCoupon();
        salercoupon.setCouponName(salercouponVO.getName());
        salercoupon.setDescription(salercouponVO.getDescription());
        salercoupon.setCouponType(salercouponVO.getType());
        salercoupon.setStartTime(salercouponVO.getStartTime());
        salercoupon.setEndTime(salercouponVO.getEndTime());
        salercoupon.setStatus(salercouponVO.getStatus());
        salercoupon.setId(salercouponVO.getId());
        salercoupon.setTargetMoney(salercouponVO.getTargetMoney());
        salercoupon.setDiscountMoney(salercouponVO.getDiscountMoney());
        System.out.println(salercouponVO.getStartTime());
        System.out.println("2555");
        salerMapper.updateSalerCoupon(salercoupon.getId(),salercoupon.getDescription(),salercoupon.getCouponName(),salercoupon.getCouponType(),
                salercoupon.getStatus(),salercoupon.getStartTime(),salercoupon.getEndTime(),salercoupon.getDiscountMoney(),salercoupon.getTargetMoney());
//        salerMapper.deleteWebCoupon(salercouponVO.getId());
//        salerMapper.insertSalerCoupon(salercoupon);
    }


    @Override

    public List<AD> getAD() {
        List<AD> temp=adMapper.getADs();

        return temp;
    }
    //用来检查获得所有广告的桩程序
    public List<AD> getAD_sub() {
        List<AD> res=new ArrayList<>();
        AD ad=new AD();
        ad.setUid("111");
        ad.setId(1);
        ad.setUrl("123");
        res.add(ad);
        return res;
    }



    @Override

    public ADVO addAD(ADVO adVO) {
        AD ad = new AD();
        ad.setId(adVO.getId());
        ad.setName(adVO.getName());
        ad.setStatus(adVO.getStatus());
        ad.setUid(adVO.getUid());
        ad.setUrl(adVO.getUrl());
        ad.setThumbUrl(adVO.getThumbUrl());
        int result = adMapper.insertAD(ad);
        adVO.setId(result);
        return adVO;
    }



    @Override
    public void deleteWebCoupon(Integer webCouponId){
        salerMapper.deleteWebCoupon(webCouponId);
    }

    @Override
    public SalerCouponVO getWebCouponById(Integer webCouponId){
        return salerMapper.getWebCouponByID(webCouponId);
    }

    public boolean isMatch_ (OrderVO orderVO, SalerCoupon salercoupon) throws Exception{

        //前置条件检查，契约式编程

        if(salercoupon.getCouponType()!=3)
        {
            throw new Exception("wrong type");

        }

        if (salercoupon.getCouponType() == 3
                && orderVO.getPrice() >= salercoupon.getTargetMoney()
                && salercoupon.getStatus()==1)
        {
            return true;
        }



        return false;
    }



}














