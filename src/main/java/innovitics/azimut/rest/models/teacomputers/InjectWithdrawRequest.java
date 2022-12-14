package innovitics.azimut.rest.models.teacomputers;

public class InjectWithdrawRequest extends TeaComputerRequest{
	
	   	private String OrderDate;
	   	private Long ModuleTypeId;
	   	private Long CurrencyId;
	   	private Double  OrderValue;
	   	private Long AccountNo;
	   	private Long AccountId;
	   	private Long BankId;
	   	private String TicketDoc;
	   	private String referenceno;
	   	
	   	
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
		public Long getAccountNo() {
			return AccountNo;
		}
		public void setAccountNo(Long accountNo) {
			AccountNo = accountNo;
		}
		public String getTicketDoc() {
			return TicketDoc;
		}
		public void setTicketDoc(String ticketDoc) {
			TicketDoc = ticketDoc;
		}
		public Long getAccountId() {
			return AccountId;
		}
		public void setAccountId(Long accountId) {
			AccountId = accountId;
		}
		public Long getBankId() {
			return BankId;
		}
		public void setBankId(Long bankId) {
			BankId = bankId;
		}
		public String getReferenceno() {
			return referenceno;
		}
		public void setReferenceno(String referenceno) {
			this.referenceno = referenceno;
		}
		
	   	
	   	

}
