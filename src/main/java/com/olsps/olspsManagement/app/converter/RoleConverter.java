/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olsps.olspsManagement.app.converter;

import com.olsps.olspsManagement.app.controller.OlspsController;
import com.olsps.olspsaccesscontrolapi.Role;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author Eusuph
 */
@FacesConverter("roleConverter")
public class RoleConverter implements Converter {

    @Inject
    OlspsController olspsController;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                for (Role roleObject : olspsController.getRolesList()) {
                    if (roleObject.getName().equals(value)) {
                        return roleObject;
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            return null;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value != null && value instanceof Role){
            return ((Role)value).getName();
        } 
        else{
            return null;
        }
    }

}
