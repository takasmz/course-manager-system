package com.coursemanager.dto;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class RegisterDto  implements Serializable{
	private static final long serialVersionUID = -8120224502344944929L;

    /**
     * 登录用户名
     */
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
    /**
     * 登录密码
     */
    @NotBlank
    private String password;
    /**
     * 验证码
     */
    @NotBlank
    private String vercode;
    
    private String nickname;
    
    @NotBlank
    @Email
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVercode() {
        return vercode;
    }

    public void setVercode(String vercode) {
        this.vercode = vercode;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
