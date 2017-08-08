package yjc.wdb.scts.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import yjc.wdb.scts.bean.Branch_officeVO;
import yjc.wdb.scts.bean.CouponVO;
import yjc.wdb.scts.bean.Coupon_holdVO;
import yjc.wdb.scts.dao.CouponDAO;
import yjc.wdb.scts.service.CouponService;


@Service
public class CouponServiceImpl implements CouponService {

	@Inject
	private CouponDAO dao;

	@Transactional
	@Override
	public void insertCoupon(CouponVO couponVO, String select) throws Exception {
		// TODO Auto-generated method stub
		dao.insertCoupon(couponVO);

		if(select.equals("product")){
			dao.applyCoupon(couponVO);

		}else{
			dao.applyDetailCoupon(couponVO);
		}

	}


	@Transactional
	@Override
	public List<CouponVO> insertAdCoupon(CouponVO couponVO, int[] branch_office, String select) throws Exception {
		dao.insertCoupon(couponVO);

		for(int i = 0; i < branch_office.length; i++){

			int bhf_code = branch_office[i];
			couponVO.setBhf_code(bhf_code);

			if(select.equals("product")){
				dao.applyCoupon(couponVO);

			}else{
				dao.applyDetailCoupon(couponVO);
			}

		}

		return dao.couponAdList();


	}

	@Override
	public List<CouponVO> selectCouponList(int bhf_code) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectCouponList(bhf_code);
	}

	@Override
	public CouponVO selectSendAndroidCoupon() throws Exception {
		// TODO Auto-generated method stub
		return dao.selectSendAndroidCoupon();
	}

	@Transactional
	@Override
	public void updateCoupon(CouponVO couponVO, int bhf_code, String select) throws Exception {

		dao.deleteCoupon_detailcategory_creation(couponVO.getCoupon_code(), bhf_code);
		dao.deleteCoupon_goods_creation(couponVO.getCoupon_code(), bhf_code);
		dao.updateCoupon(couponVO);
		
		couponVO.setBhf_code(bhf_code);

		if(select.equals("product")){
		
			dao.modifyGoodsCoupon(couponVO);

		}else if(select.equals("category")){
			dao.modifyDetailCoupon(couponVO);
		}




	}

	@Transactional
	@Override
	public void deleteCoupon(int coupon_code, int bhf_code) throws Exception {

		dao.deleteCoupon_goods_creation(coupon_code, bhf_code);
		dao.deleteCoupon_detailcategory_creation(coupon_code, bhf_code);
		dao.delCouponBasket(coupon_code);
		dao.deleteCoupon(coupon_code);
	}

	@Override
	public CouponVO selectCouponOne(int coupon_code, int bhf_code) throws Exception {

		return dao.selectCouponOne(coupon_code, bhf_code);
	}

	@Override
	public int selectCode() throws Exception {
		return dao.selectCode();
	}

	@Override
	public List<Branch_officeVO> selectAllbranchOffice() throws Exception {
		// TODO Auto-generated method stub
		return dao.selectAllbranchOffice();
	}

	@Override
	public List<Branch_officeVO> searchingBranchOffice(String bhf_nm) throws Exception {
		// TODO Auto-generated method stub
		return dao.searchingBranchOffice(bhf_nm);
	}

	@Override
	public List<HashMap> selectAllCategory() throws Exception {
		return dao.selectAllCategory();
	}


	@Transactional
	@Override
	public void updateAdCoupon(CouponVO couponVO, int bhf_code, String select, int[] branch_office) throws Exception {

		dao.deleteCoupon_detailcategory_creation(couponVO.getCoupon_code(), bhf_code);
		dao.deleteCoupon_goods_creation(couponVO.getCoupon_code(), bhf_code);
		dao.updateCoupon(couponVO);

		for(int i = 0; i < branch_office.length; i++){

			couponVO.setBhf_code(branch_office[i]);

			if(select.equals("product")){
				
				dao.modifyGoodsCoupon(couponVO);

			}else if(select.equals("category")){
				dao.modifyDetailCoupon(couponVO);
			}


		}

	}
}
