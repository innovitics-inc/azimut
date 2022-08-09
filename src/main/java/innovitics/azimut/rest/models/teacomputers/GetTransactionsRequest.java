package innovitics.azimut.rest.models.teacomputers;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetTransactionsRequest extends TeaComputerRequest{

	/**
	 * 
	 */
	private String FromDate;
	private String ToDate;


	public String getFromDate() {
		return FromDate;
	}


	public void setFromDate(String fromDate) {
		FromDate = fromDate;
	}


	public String getToDate() {
		return ToDate;
	}


	public void setToDate(String toDate) {
		ToDate = toDate;
	}


	@Override
	public String toString() {
		return "GetTransactionsRequest [IdNumber=" + IdNumber + ", FromDate=" + FromDate + ", ToDate=" + ToDate
				+ ", Signature=" + Signature + ", IdTypeId=" + IdTypeId + ", UserName=" + UserName + ", Password="
				+ Password + "]";
	}
	
	
	
	
	
}
