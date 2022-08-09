package innovitics.azimut.rest.entities.valify;

public class ValifyAccessTokenOutput extends ValifyOutput {
	private String accessToken;
    private int expiresIn;
    private String tokenType;
    private String scope;
    private String refreshToken;
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	@Override
	public String toString() {
		return "ValifyAccessTokenOutput [accessToken=" + accessToken + ", expiresIn=" + expiresIn + ", tokenType="
				+ tokenType + ", scope=" + scope + ", refreshToken=" + refreshToken + "]";
	}
    
    
    
}
