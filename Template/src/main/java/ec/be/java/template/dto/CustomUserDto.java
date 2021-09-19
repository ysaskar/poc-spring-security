package ec.be.java.template.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@SuppressWarnings("serial")
public class CustomUserDto extends User {
	private String nickName;

	public CustomUserDto(String username, Collection<? extends GrantedAuthority> authorities, String nickName) {
		super(username, "---", authorities);
		this.nickName = nickName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}