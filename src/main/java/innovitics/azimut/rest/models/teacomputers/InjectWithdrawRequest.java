package innovitics.azimut.rest.models.teacomputers;

public class InjectWithdrawRequest extends TeaComputerRequest{
	
	   	private String OrderDate;
	   	private Long ModuleTypeId;
	   	private Long CurrencyId;
	   	private Double  OrderValue;
	   	private String AccountNo;
	   	private String AccountId;
	   	private String TicketDoc;
	   	
	   	
		public String getOrderDate() {
			return OrderDate;
		}
		public void setOrderDate(String orderDate) {
			OrderDate = orderDate;
		}
		public Long getModuleTypeId() {
			return ModuleTypeId;
		}
		public void setModuleTypeId(Long moduleTypeId) {
			ModuleTypeId = moduleTypeId;
		}
		public Long getCurrencyId() {
			return CurrencyId;
		}
		public void setCurrencyId(Long currencyId) {
			CurrencyId = currencyId;
		}
		public Double getOrderValue() {
			return OrderValue;
		}
		public void setOrderValue(Double orderValue) {
			OrderValue = orderValue;
		}
		public String getAccountNo() {
			return AccountNo;
		}
		public void setAccountNo(String accountNo) {
			AccountNo = accountNo;
		}
		public String getTicketDoc() {
			return TicketDoc;
		}
		public void setTicketDoc(String ticketDoc) {
			TicketDoc = ticketDoc;
		}
		public String getAccountId() {
			return AccountId;
		}
		public void setAccountId(String accountId) {
			AccountId = accountId;
		}
	   	
	   	

}
