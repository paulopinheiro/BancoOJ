package br.jus.trt12.paulopinheiro.bancooj.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ContextoJSF {
  public static ExternalContext getExternalContext() {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    return facesContext.getExternalContext();
  }

  public static FacesContext getFacesContext() {
      return FacesContext.getCurrentInstance();
  }

  public static HttpSession getFacesSession() {
    return (HttpSession)getExternalContext().getSession(true);
  }

  public static String getRequestParameter(String parameterName) {
    Map paramMap = getExternalContext().getRequestParameterMap();
    return (String) paramMap.get(parameterName);
  }

  public static String getCaminhoReal(String relativo) {
      HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
      return request.getSession().getServletContext().getRealPath(relativo);
  }

  public static Object getRequestAttribute(String attributeName) {
    Map attrMap = getExternalContext().getRequestMap();
    return attrMap.get(attributeName);
  }

    public static void setHttpSessionAttribute(String attributeName, Object attribute) {
        //funciona, mas quando o contexto do JSF encerra os atributos são apagados...
        getFacesSession().setAttribute(attributeName, attribute);
    }

  public static void setSessionAttribute(String attributeName, Object attribute) {
      Map attrMap = getExternalContext().getSessionMap();
      attrMap.put(attributeName, attribute);
  }

  public static Object getSessionAttribute(String attributeName) {
    Map attrMap = getExternalContext().getSessionMap();
    return attrMap.get(attributeName);
  }  

  public static Object getApplicationAttribute(String attributeName) {
    Map reqAttrMap = getExternalContext().getApplicationMap();
    return reqAttrMap.get(attributeName);
  }

  public static void setApplicationAttribute(String attributeName, Object attribute) {
    Map appAttrMap = getExternalContext().getApplicationMap();
    appAttrMap.put(attributeName, attribute);
  }

  public static void addGlobalMessage(String message) {
    FacesMessage facesMessage = new FacesMessage(message);
    FacesContext.getCurrentInstance().addMessage(null, facesMessage);    
  }

}
