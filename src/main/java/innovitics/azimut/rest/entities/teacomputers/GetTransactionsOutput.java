package innovitics.azimut.rest.entities.teacomputers;

import java.util.List;

public class GetTransactionsOutput extends TeaComputerOutput{

	List<TransactionOutput> transactionOutputs;

	public List<TransactionOutput> getTransactionOutputs() {
		return transactionOutputs;
	}

	public void setTransactionOutputs(List<TransactionOutput> transactionOutputs) {
		this.transactionOutputs = transactionOutputs;
	}

	@Override
	public String toString() {
		return "GetTransactionsOutput [transactionOutputs=" + transactionOutputs + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	
}
