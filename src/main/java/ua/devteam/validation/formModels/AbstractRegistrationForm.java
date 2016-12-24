package ua.devteam.validation.formModels;


import ua.devteam.entity.users.User;

public abstract class AbstractRegistrationForm<T extends User> {
    private String firstName;
    private String lastName;
    private String email;
    private String confirmedEmail;
    private String phoneNumber;
    private String confirmedPhoneNumber;
    private String password;
    private String confirmedPassword;

    public AbstractRegistrationForm() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmedEmail() {
        return confirmedEmail;
    }

    public void setConfirmedEmail(String confirmedEmail) {
        this.confirmedEmail = confirmedEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getConfirmedPhoneNumber() {
        return confirmedPhoneNumber;
    }

    public void setConfirmedPhoneNumber(String confirmedPhoneNumber) {
        this.confirmedPhoneNumber = confirmedPhoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    public abstract T getEntity();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractRegistrationForm)) return false;

        AbstractRegistrationForm<?> that = (AbstractRegistrationForm<?>) o;

        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (confirmedEmail != null ? !confirmedEmail.equals(that.confirmedEmail) : that.confirmedEmail != null)
            return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
        if (confirmedPhoneNumber != null ? !confirmedPhoneNumber.equals(that.confirmedPhoneNumber) : that.confirmedPhoneNumber != null)
            return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;

        return confirmedPassword != null ? confirmedPassword.equals(that.confirmedPassword) : that.confirmedPassword == null;

    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (confirmedEmail != null ? confirmedEmail.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (confirmedPhoneNumber != null ? confirmedPhoneNumber.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (confirmedPassword != null ? confirmedPassword.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RegistrationForm{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", confirmedEmail='").append(confirmedEmail).append('\'');
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append(", confirmedPhoneNumber='").append(confirmedPhoneNumber).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", confirmedPassword='").append(confirmedPassword).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
