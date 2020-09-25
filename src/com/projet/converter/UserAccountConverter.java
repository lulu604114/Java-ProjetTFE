package com.projet.converter;

import com.projet.entities.UserAccount;
import com.projet.services.UserAccountService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project Projet TFE
 * Date: 26/08/2020
 * Time: 23:25
 * =================================================================
 */
@Named
@FacesConverter(value = "userAccountConverter")
public class UserAccountConverter implements Converter {
    private UserAccountService service = new UserAccountService();

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s != null && s.trim().length() > 0) {
            int id = Integer.parseInt(s);

            return service.getById(id);
        } else
            return null;

    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o != null)
            return String.valueOf(((UserAccount) o).getId());
        else
            return null;
    }
}
