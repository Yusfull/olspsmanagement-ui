/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olsps.olspsManagement.app.controller;

import com.olsps.olspsaccesscontrolapi.User;
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
@FacesConverter("userConverter")
public class UserConveter implements Converter {

    @Override
    public User getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                OlspsController service = (OlspsController) fc.getExternalContext().getApplicationMap().get("userConverter");
                return (User) service.getUserList().get(Integer.parseInt(value));
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            return String.valueOf(((Object) object));
        } else {
            return null;
        }
    }
}
