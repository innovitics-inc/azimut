package innovitics.azimut.rest.models.valify;

public class ValifyPassportResult extends ValifyResult{

	private String name;
	private String surname;
	private String passport_number;
	private String expiration_date;
	private String date_of_birth;
	private String sex;
	private String nationality;
	private int validity;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPassport_number() {
		return passport_number;
	}
	public void setPassport_number(String passport_number) {
		this.passport_number = passport_number;
	}
	public String getExpiration_date() {
		return expiration_date;
	}
	public void setExpiration_date(String expiration_date) {
		this.expiration_date = expiration_date;
	}
	public String getDate_of_birth() {
		return date_of_birth;
	}
	public void setDate_of_birth(String date_of_birth) {
		this.date_of_birth = date_of_birth;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public int getValidity() {
		return validity;
	}
	public void setValidity(int validity) {
		this.validity = validity;
	}
	@Override
	public String toString() {
		return "ValifyPassportResult [name=" + name + ", surname=" + surname + ", passport_number=" + passport_number
				+ ", expiration_date=" + expiration_date + ", date_of_birth=" + date_of_birth + ", sex=" + sex
				+ ", nationality=" + nationality + ", validity=" + validity + "]";
	}
	
	

}
