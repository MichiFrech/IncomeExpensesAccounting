package at.htl.iea.business.parser.model;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActiveOrHistoricCurrencyAndAmount", propOrder = {
    "value"
})
public class ActiveOrHistoricCurrencyAndAmount {

    @XmlValue
    protected BigDecimal value;
    @XmlAttribute(name = "Ccy", required = true)
    protected String ccy;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String value) {
        this.ccy = value;
    }

}
