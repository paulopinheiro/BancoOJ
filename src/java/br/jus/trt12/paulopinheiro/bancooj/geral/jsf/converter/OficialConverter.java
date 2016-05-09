package br.jus.trt12.paulopinheiro.bancooj.geral.jsf.converter;

import br.jus.trt12.paulopinheiro.bancooj.model.Oficial;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converteOficial", forClass = Oficial.class)
public class OficialConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) return null;

        int id = Integer.parseInt(value);
        UISelectOne soOficial = (UISelectOne) component;

        return oficialById(listaOficiais(soOficial), id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof String) return "-1";

        Oficial oficial = (Oficial) value;

        return String.valueOf(oficial.getCodigo());
    }

    private Oficial oficialById(List<Oficial> listaOficiais, int id) {
        Oficial resposta = null;

        for (Oficial p : listaOficiais) {
            if (p.getCodigo() == id) {
                resposta = p;
                break;
            }
        }
        return resposta;
    }

    private List<Oficial> listaOficiais(UISelectOne selectOne) {
        UISelectItems siOficial = null;

        for (UIComponent ui : selectOne.getChildren()) {
            if (ui instanceof UISelectItems) {
                siOficial = (UISelectItems) ui;
                break;
            }
        }
        if (siOficial == null) {
            throw new RuntimeException("Problemas para validar objeto Oficial");
        }

        return (List<Oficial>) siOficial.getValue();
    }
}
