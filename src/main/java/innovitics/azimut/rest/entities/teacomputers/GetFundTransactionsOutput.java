package innovitics.azimut.rest.entities.teacomputers;

import java.util.List;

import innovitics.azimut.utilities.crosslayerenums.OrderStatus;

public class GetFundTransactionsOutput extends TeaComputerOutput{

	
	private List<FundTransactionOutput> fundTransactionOutputs;
	private OrderStatus orderStatus;
	
	
	public List<FundTransactionOutput> getFundTransactionOutputs() {
		return fundTransactionOutputs;
	}

	public void setFundTransactionOutputs(List<FundTransactionOutput> fundTransactionOutputs) {
		this.fundTransactionOutputs = fundTransactionOutputs;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	
}
