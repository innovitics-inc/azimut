package innovitics.azimut.businessmodels.user;

import java.util.Date;
import java.util.List;

import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.utilities.CustomJsonRootName;


@CustomJsonRootName(plural = "credentials", singular = "credentials")
public class AuthenticationResponse extends BaseBusinessEntity {

	
	
		private Token token;
		private BusinessUser user;
		
	

		public BusinessUser getUser() {
			return user;
		}



		public void setUser(BusinessUser user) {
			this.user = user;
		}



		public Token getToken() {
			return token;
		}



		public void setToken(Token token) {
			this.token = token;
		}



		public AuthenticationResponse(Token token, BusinessUser user) {
			super();
			this.token = token;
			this.user = user;
		}



		@Override
		public String toString() {
			return "AuthenticationResponse [token=" + token + ", user=" + user + ", searchFromDate=" + searchFromDate
					+ ", searchToDate=" + searchToDate + ", searchDates=" + searchDates + ", azIdType=" + azIdType
					+ ", azId=" + azId + ", sorting=" + sorting + ", bankId=" + bankId + ", accountId=" + accountId
					+ "]";
		}






		
}
