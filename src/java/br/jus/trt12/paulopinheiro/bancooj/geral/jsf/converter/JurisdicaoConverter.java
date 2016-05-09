package br.jus.trt12.paulopinheiro.bancooj.geral.jsf.converter;

import br.jus.trt12.paulopinheiro.bancooj.model.Jurisdicao;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converteJurisdicao", forClass = Jurisdicao.class)
public class JurisdicaoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) return null;

        int id = Integer.parseInt(value);
        UISelectOne soJurisdicao = (UISelectOne) component;

        return jurisdicaoById(listaJurisdicoes(soJurisdicao), id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof String) return "-1";

        Jurisdicao jurisdicao = (Jurisdicao) value;

        return String.valueOf(jurisdicao.getCodigo());
    }

    private Jurisdicao jurisdicaoById(List<Jurisdicao> listaJurisdicoes, int id) {
        Jurisdicao resposta = null;

        if (listaJurisdicoes==null) return null;

        for (Jurisdicao j : listaJurisdicoes) {
            if (j.getCodigo() == id) {
                resposta = j;
                break;
            }
        }
        return resposta;
    }

    private List<Jurisdicao> listaJurisdicoes(UISelectOne selectOne) {
        UISelectItems siJurisdicao = null;

        for (UIComponent ui : selectOne.getChildren()) {
            if (ui instanceof UISelectItems) {
                siJurisdicao = (UISelectItems) ui;
                break;
            }
        }
        if (siJurisdicao == null) {
            throw new RuntimeException("Problemas para validar objeto Jurisdição");
        }

        return (List<Jurisdicao>) siJurisdicao.getValue();
    }        
}
