package yjc.wdb.scts.util;

import javax.servlet.http.HttpSession;

/* 
 * �ش� Ŭ������ ���ǿ� ������ �α��� �Ǿ��ִ��� üũ�ϱ� ���� Ŭ�����μ�
 * Ŭ���� �������� �����޼��� ȣ���� ���� ���� ������
 */

public class SessionCheck {
	// ���� ������ �α��� �Ǿ��ִ��� üũ�ϱ� ���� �Լ�
	public static boolean employeeCheck(HttpSession session) {
		String user_id = (String) session.getAttribute("user_id");
		int bhf_code = (Integer) session.getAttribute("bhf_code");
		
		if(user_id.isEmpty() || bhf_code == 0) {
			return false;
		}

		return true;
	}
}
