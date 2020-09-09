package com.projet.converter;

import com.projet.entities.AccountCategory;
import com.projet.entities.Diary;
import com.projet.entities.Supplier;
import com.projet.services.AccountCategoryService;
import com.projet.services.DiaryService;
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
 * Date: 25/08/2020
 * Time: 13:03
 * =================================================================
 */
@Named
@FacesConverter(value = "accountCategoryConverter")
public class AccountCategoryConverter implements Converter {
    private AccountCategoryService service = new AccountCategoryService(AccountCategory.class);

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
            return String.valueOf(((AccountCategory) o).getId());
        else
            return null;
    }
}
