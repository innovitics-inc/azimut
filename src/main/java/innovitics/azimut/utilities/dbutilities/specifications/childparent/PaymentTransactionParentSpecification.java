package innovitics.azimut.utilities.dbutilities.specifications.childparent;

import org.springframework.stereotype.Component;

import innovitics.azimut.models.payment.PaymentTransaction;
import innovitics.azimut.models.user.User;
import innovitics.azimut.utilities.dbutilities.specifications.EntityChildParentSpecification;
import innovitics.azimut.utilities.dbutilities.specifications.EntityChildSpecification;

@Component
public class PaymentTransactionParentSpecification extends EntityChildParentSpecification<PaymentTransaction,User>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6627126140537475478L;

}
