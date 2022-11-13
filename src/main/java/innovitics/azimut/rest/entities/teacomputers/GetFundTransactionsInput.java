package innovitics.azimut.rest.entities.teacomputers;

import innovitics.azimut.utilities.crosslayerenums.OrderStatus;

public class GetFundTransactionsInput extends TeaComputerInput{

	private OrderStatus orderStatus;

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	
	
}
