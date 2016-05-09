package br.jus.trt12.paulopinheiro.bancooj.geral.jsf.converter;

import br.jus.trt12.paulopinheiro.bancooj.model.Bairro;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converteBairro", forClass = Bairro.class)
public class BairroConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) return null;

        int id = Integer.parseInt(value);
        UISelectOne soBairro = (UISelectOne) component;

        return bairroById(listaBairros(soBairro), id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof String) return "-1";

        Bairro bairro = (Bairro) value;

        return String.valueOf(bairro.getCodigo());
    }

    private Bairro bairroById(List<Bairro> listaBairros, int id) {
        Bairro resposta = null;

        if (listaBairros==null) return null;

        for (Bairro b : listaBairros) {
            if (b.getCodigo() == id) {
                resposta = b;
                break;
            }
        }
        return resposta;
    }

    private List<Bairro> listaBairros(UISelectOne selectOne) {
        UISelectItems siBairro = null;

        for (UIComponent ui : selectOne.getChildren()) {
            if (ui instanceof UISelectItems) {
                siBairro = (UISelectItems) ui;
                break;
            }
        }
        if (siBairro == null) {
            throw new RuntimeException("Problemas para validar objeto Bairro");
        }

        return (List<Bairro>) siBairro.getValue();
    }
}
