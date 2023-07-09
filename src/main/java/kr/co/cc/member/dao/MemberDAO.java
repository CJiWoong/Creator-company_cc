package kr.co.cc.member.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kr.co.cc.member.dto.MemberDTO;


public interface MemberDAO {


	int join(MemberDTO dto);

	Map<String, Object> login(String user_id);

	int idChk(String user_id);
	
	MemberDTO getUserInfo(HashMap<String, String> params);
	
	MemberDTO getUserInfoPW(HashMap<String, String> params);


	boolean updateTemporaryPassword(MemberDTO userInfoPW);

	String loginid(String user_id);

	ArrayList<MemberDTO> departmentlist(HashMap<String, String> params);
	
	MemberDTO userInfo(Object attribute);

	void userfileWrite(String oriFileName, String newFileName, String classification, String userId);


	String ori_file_name(String id);

	int userInfoUpdate(HashMap<String, String> params);

	void removeFileName(String fileName);

	void userinfofileWrite(String oriFileName, String newFileName, String cls, String id);



}
