package com.study.springboot.oauth2;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


import com.study.springboot.dao.IUserDao;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	@Autowired
	private HttpSession httpSession;
	@Autowired
	IUserDao userdao;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		String userNameAttributeName = userRequest.getClientRegistration()
				                                  .getProviderDetails()
				                                  .getUserInfoEndpoint()
				                                  .getUserNameAttributeName();
		
		System.out.println("customoauth userNameAttributeName : "+userNameAttributeName);
		
		OAuthAttributes attributes = OAuthAttributes.of(registrationId,
				                                        userNameAttributeName,
				                                        oAuth2User.getAttributes());

		SessionUser user = new SessionUser(attributes.getName(),
				                           attributes.getEmail(),
				                           attributes.getPicture());
//		System.out.println("Picture:"+attributes.getPicture());
		httpSession.setAttribute("user",user);

		if(userdao.IDcheckDao(attributes.getEmail())==0) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", attributes.getEmail());
			map.put("email", attributes.getEmail());
			map.put("name", attributes.getName());
			map.put("email_id", attributes.getEmail().split("@")[0]);
			map.put("email_domain", attributes.getEmail().split("@")[1]);
			map.put("authority", "ROLE_USER");
			map.put("userprofile", "profileImage.png");
			map.put("check", "y");

			userdao.InsertSNSInfo(map);
		}
		System.out.println("serdao.IDcheckDao(attributes.getName())" + userdao.IDcheckDao(attributes.getName())) ;

		return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
	}
}