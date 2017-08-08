package yjc.wdb.scts.service;

import java.util.HashMap;
import java.util.List;

import yjc.wdb.scts.bean.Branch_officeVO;
import yjc.wdb.scts.bean.CouponVO;
import yjc.wdb.scts.bean.Coupon_holdVO;

public interface CouponService {
	public void insertCoupon(CouponVO couponVO, String select) throws Exception;
	public void updateCoupon(CouponVO couponVO, int bhf_code, String select) throws Exception;
	public void updateAdCoupon(CouponVO couponVO, int bhf_code, String select, int[] branch_office) throws Exception;
	public List<CouponVO> selectCouponList(int bhf_code) throws Exception;
	public void deleteCoupon(int coupon_code, int bhf_code) throws Exception;
	public CouponVO selectSendAndroidCoupon() throws Exception;
	public CouponVO selectCouponOne(int coupon_code, int bhf_code) throws Exception;
	public int selectCode() throws Exception;
	public List<Branch_officeVO> selectAllbranchOffice() throws Exception;
	public List<Branch_officeVO> searchingBranchOffice(String bhf_nm) throws Exception;
	public List<HashMap> selectAllCategory() throws Exception;
	                              
	public List<CouponVO> insertAdCoupon(CouponVO couponVO, int[] branch_office, String select) throws Exception;
}
                                