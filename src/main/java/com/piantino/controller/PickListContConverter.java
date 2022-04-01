/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.model.Alquiler;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Gabriel
 */
@FacesConverter(value = "empConverter")
public class PickListContConverter implements Converter {
  @Override
  public Object getAsObject(FacesContext fc, UIComponent comp, String value) {
      DualListModel<Alquiler> model = (DualListModel<Alquiler>) ((PickList) comp).getValue();
      for (Alquiler employee : model.getSource()) {
          if ((employee.getId()+"").equals(value)) {
              return employee;
          }
      }
      for (Alquiler employee : model.getTarget()) {
          if ((employee.getId()+"").equals(value)) {
              return employee;
          }
      }
      return null;
  }

  @Override
  public String getAsString(FacesContext fc, UIComponent comp, Object value) {
      return ((Alquiler) value).getId()+"";
  }
}