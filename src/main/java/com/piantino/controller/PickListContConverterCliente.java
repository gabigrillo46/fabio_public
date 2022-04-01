/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piantino.controller;

import com.piantino.model.Alquiler;
import com.piantino.model.Cliente;
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
@FacesConverter(value = "empConverterCliente")
public class PickListContConverterCliente implements Converter {
  @Override
  public Object getAsObject(FacesContext fc, UIComponent comp, String value) {
      DualListModel<Cliente> model = (DualListModel<Cliente>) ((PickList) comp).getValue();
      for (Cliente employee : model.getSource()) {
          if ((employee.getId()+"").equals(value)) {
              return employee;
          }
      }
      for (Cliente employee : model.getTarget()) {
          if ((employee.getId()+"").equals(value)) {
              return employee;
          }
      }
      return null;
  }

  @Override
  public String getAsString(FacesContext fc, UIComponent comp, Object value) {
      return ((Cliente) value).getId()+"";
  }
}