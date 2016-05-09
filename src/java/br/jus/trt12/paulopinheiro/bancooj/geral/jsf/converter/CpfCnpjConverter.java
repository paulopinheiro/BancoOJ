package br.jus.trt12.paulopinheiro.bancooj.geral.jsf.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converteCpfCnpj")
public class CpfCnpjConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        String cpf_cnpj = value;
        if (value != null && !value.equals("")) {
            cpf_cnpj = value.replaceAll("\\.", "").replaceAll("\\-", "").replaceAll("\\/", "");
        }

        return cpf_cnpj;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String cpf_cnpj = (String) value;
        if (cpf_cnpj != null) {
            if (cpf_cnpj.length() == 11) {  //CPF
                return formatoCPF(cpf_cnpj);
            } else {
                if (cpf_cnpj.length()==14) { //CNPJ
                    return formatoCNPJ(cpf_cnpj);
                }
            }
        }
        return cpf_cnpj;
    }

    private String formatoCPF(String numero) {
        return numero.substring(0, 3) + "." + 
               numero.substring(3, 6) + "." +
               numero.substring(6, 9) + "-" +
               numero.substring(9, 11);
    }

    private String formatoCNPJ(String numero) {
        return numero.substring(0, 2)  + "." +
               numero.substring(2, 5)  + "." +
               numero.substring(5, 8)  + "/" +
               numero.substring(8, 12) + "-" +
               numero.substring(12,14);
               
    }
}