package innovitics.azimut.rest;

import innovitics.azimut.rest.entities.firebase.FirebaseInput;
import innovitics.azimut.rest.entities.firebase.FirebaseOutput;
import innovitics.azimut.rest.models.firebase.FirebaseRequest;
import innovitics.azimut.rest.models.firebase.FirebaseResponse;

public interface FirebaseBaseRestConsumer extends BaseRestConsumer<FirebaseRequest, FirebaseResponse, FirebaseInput,FirebaseOutput> {

}
