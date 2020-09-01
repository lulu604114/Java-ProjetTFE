package com.projet.converter;

import com.projet.entities.Supplier;
import com.projet.entities.UserSupplier;
import com.projet.services.SupplierService;
import com.projet.services.UserSupplierService;

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
 * Date: 20/08/2020
 * Time: 17:47
 * =================================================================
 */
@Named
@FacesConverter(value = "userSupplierConverter")
public class UserSupplierConverter implements Converter {
    private UserSupplierService service = new UserSupplierService(UserSupplier.class);

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
            return String.valueOf(((UserSupplier) o).getId());
        else
            return null;
    }
}
