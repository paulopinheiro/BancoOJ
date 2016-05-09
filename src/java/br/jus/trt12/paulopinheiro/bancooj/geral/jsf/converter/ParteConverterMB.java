package br.jus.trt12.paulopinheiro.bancooj.geral.jsf.converter;

import br.jus.trt12.paulopinheiro.bancooj.ejb.ParteFacade;
import br.jus.trt12.paulopinheiro.bancooj.model.Parte;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

@ManagedBean
@RequestScoped
public class ParteConverterMB implements Converter, Serializable {
    @EJB private ParteFacade parteFacade;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
        try {
            if (value==null) return null;
            return (Parte) parteFacade.find(Long.valueOf(value));
        } catch (Exception ex) {
            throw new ConverterException(new FacesMessage(String.format("Não foi possível determinar a parte de código %s", value)), ex);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
        try {
            if (!(value instanceof Parte)||((Parte)value).getCodigo()==null) return null;
            return String.valueOf(((Parte)value).getCodigo());
        } catch (Exception ex) {
            throw new ConverterException(new FacesMessage(String.format("Não foi possível determinar a parte de código %s", value)), ex);
        }
    }
    
}
