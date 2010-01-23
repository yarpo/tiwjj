/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tiwjj.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author asus
 */
public class EmailValidator implements Validator {

    public void validate(FacesContext context, UIComponent component,
                                                        Object value)
                                                  throws ValidatorException {
        //Get the component's contents and cast it to a String
        String enteredEmail = (String)value;

        //Set the email pattern string
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");

        //Match the given string with the pattern
        Matcher m = p.matcher(enteredEmail);

        //Check whether match is found
        boolean matchFound = m.matches();

        if (!matchFound) {
            FacesMessage message = new FacesMessage();
            message.setDetail("Email not valid - The email must be in the format yourname@yourdomain.com");
            message.setSummary("Email not valid - The email must be in the format yourname@yourdomain.com");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
}
