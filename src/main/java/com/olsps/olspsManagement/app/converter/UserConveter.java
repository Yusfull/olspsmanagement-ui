/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olsps.olspsManagement.app.converter;

import com.olsps.olspsManagement.app.controller.OlspsController;
import com.olsps.olspsaccesscontrolapi.User;
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
@FacesConverter("userConverter")
public class UserConveter implements Converter {

    @Inject
    OlspsController olspsController;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                for (User userObj : olspsController.getUserList()) {
                    if (userObj.getUserName().equals(value)) {
                        return userObj;
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
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value != null && value instanceof User) {
            return ((User) value).getUserName();
        } else {
            return null;
        }
    }
}
