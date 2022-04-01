package com.piantino.controller;


import com.piantino.ejb.ClienteFacadeLocal;
import com.piantino.model.Cliente;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;


@Named
@FacesConverter(value = "themeConverter")
public class ThemeConverter implements Converter {
 
    @EJB
    private ClienteFacadeLocal clienteEJB;
     

 
 

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
                if(value != null && value.trim().length() > 0) {
            try {
                return clienteEJB.findAll().get(Integer.parseInt(value));
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        }
        else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
                if(value != null) {
            return String.valueOf(((Cliente) value).getId());
        }
        else {
            return null;
        }
    }
}
