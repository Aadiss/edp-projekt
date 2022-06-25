package com.example.edpprojekt2.credentials;

public class ValidationResult {
    private Status status;

    public ValidationResult(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getErrorDescription(){
        switch (this.status){
            case OK:
                return "Verification successful";
            case USED_EMAIL_OR_USERNAME:
                return "Email or Username already in use";
            case INVALID_USERNAME:
                return "Username is not long enough";
            case INVALID_PASSWORD:
                return "Password is not valid, must containt at least 6 characters";
            case PASSWORDS_DOES_NOT_MATCH:
                return "Passwords are different";
            case USER_NOT_FOUND:
                return "Such user does not exist";
            case INCORRECT_PASSWORD:
                return "Password is not correct";
            case INCORRECT_EMAIL:
                return "Email is not valid, does not contain '@' char";
            case EMAIL_DOES_NOT_EXIST:
                return "Email does not exist or notification error occurred. Provide correct email or try again";
            default:
                return "";
        }
    }
}
