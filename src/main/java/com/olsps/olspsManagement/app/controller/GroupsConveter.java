/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olsps.olspsManagement.app.controller;

import com.olsps.olspsaccesscontrolapi.Group;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author Eusuph
 */
@FacesConverter("groupsConverter")
public class GroupsConveter implements Converter {
    @Inject
    OlspsController aOlspsController;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {                
                for (Group g: aOlspsController.getGroupsList()){
                    if (g.getName().equals(value)){
                        return g;
                    }
                }
                return null;
            } catch (ConverterException e) {
                e.printStackTrace();
            }
        } else {
            return null;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && value instanceof Group) {
            return ((Group) value).getName();
        } else {
            return null;
        }
    }

}
