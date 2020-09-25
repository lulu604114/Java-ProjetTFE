package com.projet.converter;

import com.projet.entities.Patient;
import com.projet.services.PatientService;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author amaury Lapaque
 * @project Projet TFE Date: 25/08/2020 Time: 13:03 =================================================================
 */
@Named
@FacesConverter(value = "patientConverter")
public class PatientConverter implements Converter {
    private PatientService service = new PatientService();

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
            return String.valueOf(((Patient) o).getId());
        else
            return null;
    }
}
