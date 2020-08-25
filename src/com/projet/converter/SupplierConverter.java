package com.projet.converter;

import com.projet.entities.Supplier;
import com.projet.services.SupplierService;

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
@FacesConverter(value = "supplierConverter")
public class SupplierConverter implements Converter {
    private SupplierService service = new SupplierService(Supplier.class);

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
            return String.valueOf(((Supplier) o).getId());
        else
            return null;
    }
}
