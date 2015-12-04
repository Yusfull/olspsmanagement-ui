/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olsps.olspsManagement.app.controller;

import com.olsps.olspsaccesscontrolapi.Group;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Eusuph
 */
@FacesConverter("groupsConveter")
public class GroupsConveter implements Converter{

    @Override
    public Group getAsObject(FacesContext context, UIComponent component, String value) {
       if (value != null && value.trim().length() > 0) {
            try {
                OlspsController service = (OlspsController) context.getExternalContext().getApplicationMap().get("groupsConveter");
                return (Group) service.getGroupsList().get(Integer.parseInt(value));
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            return String.valueOf(((Object) value));
        } else {
            return null;
        }
    }
    
}
