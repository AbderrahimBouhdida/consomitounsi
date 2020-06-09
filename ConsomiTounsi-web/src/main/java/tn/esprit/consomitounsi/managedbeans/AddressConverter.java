package tn.esprit.consomitounsi.managedbeans;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import tn.esprit.consomitounsi.entities.Adress;



@FacesConverter(value = "addressConverter")
public class AddressConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component, String beerId) {
		ValueExpression vex =
                ctx.getApplication().getExpressionFactory()
                        .createValueExpression(ctx.getELContext(),
                                "#{addressBean}", AddressBean.class);

		AddressBean beers = (AddressBean)vex.getValue(ctx.getELContext());
        return beers.getBeer(Integer.valueOf(beerId));
	}

	@Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object beer) {
        return String.valueOf(((Adress)beer).getId());
    }

}
