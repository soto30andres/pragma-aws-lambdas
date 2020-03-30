package domain;

import java.util.Date;

public class RequestClass {
	String id;
	String firstName;
    String lastName;
    String city;
    String identificationType;
    String identificationNumber;
    String birthday;
    int greaterOrEqual;
    
    public RequestClass(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

	public RequestClass() {
    }
	
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getIdentificationType() {
		return identificationType;
	}

	public void setIdentificationType(String identificationType) {
		this.identificationType = identificationType;
	}

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getGreaterOrEqual() {
		return greaterOrEqual;
	}

	public void setGreaterOrEqual(int greaterOrEqual) {
		this.greaterOrEqual = greaterOrEqual;
	}

}
