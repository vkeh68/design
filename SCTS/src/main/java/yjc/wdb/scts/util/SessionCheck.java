package yjc.wdb.scts.util;

import javax.servlet.http.HttpSession;

/* 
 * 해당 클래스는 세션에 직원이 로그인 되어있는지 체크하기 위한 클래스로서
 * 클래스 생성없이 전역메서드 호출을 통해 실행 가능함
 */

public class SessionCheck {
	// 현재 직원이 로그인 되어있는지 체크하기 위한 함수
	public static boolean employeeCheck(HttpSession session) {
		String user_id = (String) session.getAttribute("user_id");
		int bhf_code = (Integer) session.getAttribute("bhf_code");
		
		if(user_id.isEmpty() || bhf_code == 0) {
			return false;
		}

		return true;
	}
}
