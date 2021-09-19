package api.auth.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDto extends User implements Cloneable {
	private String nickName;
	private String token;

	public CustomUserDto(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public CustomUserDto(String username, String password, Collection<? extends GrantedAuthority> authorities,
			String nickName) {
		super(username, password, authorities);
		this.nickName = nickName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}	

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}