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
        String enteredEmail = (String)value;
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(enteredEmail);

        if (!m.matches()) {
            FacesMessage message = new FacesMessage();
            message.setDetail("Nieprawidłowy adres email.  Prawidłowy format: nick@serwer.pl");
            message.setSummary("Nieprawidłowy adres email.  Prawidłowy format: nick@serwer.pl");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
}
