package br.jus.trt12.paulopinheiro.bancooj.geral.jsf.converter;

import br.jus.trt12.paulopinheiro.bancooj.model.Cidade;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converteCidade", forClass = Cidade.class)
public class CidadeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) return null;

        int id = Integer.parseInt(value);
        UISelectOne soCidade = (UISelectOne) component;

        return cidadeById(listaCidades(soCidade), id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof String) return "-1";

        Cidade cidade = (Cidade) value;

        return String.valueOf(cidade.getCodigo());
    }

    private Cidade cidadeById(List<Cidade> listaCidades, int id) {
        Cidade resposta = null;

        if (listaCidades==null) return null;

        for (Cidade c : listaCidades) {
            if (c.getCodigo() == id) {
                resposta = c;
                break;
            }
        }
        return resposta;
    }

    private List<Cidade> listaCidades(UISelectOne selectOne) {
        UISelectItems siCidade = null;

        for (UIComponent ui : selectOne.getChildren()) {
            if (ui instanceof UISelectItems) {
                siCidade = (UISelectItems) ui;
                break;
            }
        }
        if (siCidade == null) {
            throw new RuntimeException("Problemas para validar objeto Cidade");
        }

        return (List<Cidade>) siCidade.getValue();
    }    
}
