package innovitics.azimut.businessmodels.user;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Token {

	
	private  String tokenString;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Africa/Cairo")
	private  Date tokenExpiry;
	private  String refreshTokenString;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Africa/Cairo")
	private  Date refreshTokenExpiry;
	private  String propData;
	
	
	
	public String getTokenString() {
		return tokenString;
	}
	public void setTokenString(String tokenString) {
		this.tokenString = tokenString;
	}
	public Date getTokenExpiry() {
		return tokenExpiry;
	}
	public void setTokenExpiry(Date tokenExpiry) {
		this.tokenExpiry = tokenExpiry;
	}
	public String getRefreshTokenString() {
		return refreshTokenString;
	}
	public void setRefreshTokenString(String refreshTokenString) {
		this.refreshTokenString = refreshTokenString;
	}
	public Date getRefreshTokenExpiry() {
		return refreshTokenExpiry;
	}
	public void setRefreshTokenExpiry(Date refreshTokenExpiry) {
		this.refreshTokenExpiry = refreshTokenExpiry;
	}
	public String getPropData() {
		return propData;
	}
	public void setPropData(String propData) {
		this.propData = propData;
	}
	@Override
	public String toString() {
		return "Token [tokenString=" + tokenString + ", tokenExpiry=" + tokenExpiry + ", refreshTokenString="
				+ refreshTokenString + ", refreshTokenExpiry=" + refreshTokenExpiry + ", propData=" + propData + "]";
	}
	
	
	
}
