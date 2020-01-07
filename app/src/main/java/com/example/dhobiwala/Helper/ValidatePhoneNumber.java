package com.example.dhobiwala.Helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatePhoneNumber {

    public String sPhoneNumber;
//    public String sPhoneNumber ="111";

    private boolean phoneNumberValid;

    public ValidatePhoneNumber() {
        phoneNumberValid = false;
        sPhoneNumber="7218340297" ;
        checkValidity();
    }

    private void checkValidity() {
        //String sPhoneNumber = "605-88899991";
        //String sPhoneNumber = "605-888999A";

        Pattern pattern = Pattern.compile("^((\\+)?(\\d{2}[-]))?([9]{10})[9]{1}?$");
        Matcher matcher = pattern.matcher(sPhoneNumber);

        if (matcher.matches()) {
            phoneNumberValid = true;
        } else {
            phoneNumberValid = false;
        }

    }

    public Boolean getPhoneNumberValid() {
        return phoneNumberValid;
    }
}