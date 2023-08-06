package apiPojo.apiReq.formulaBuilder;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.json.JSONObject;

/**
 * @author Prateek Sethi
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormulaExprn {
    A A;
    B B;
    C C;

    public apiPojo.apiReq.formulaBuilder.A getA() {
        return A;
    }
    @JsonSetter("A")
    public void setA(apiPojo.apiReq.formulaBuilder.A A) {
        this.A = A;
    }

    public apiPojo.apiReq.formulaBuilder.B getB() {
        return B;
    }
    @JsonSetter("B")
    public void setB(apiPojo.apiReq.formulaBuilder.B B) {
        this.B = B;
    }

    public apiPojo.apiReq.formulaBuilder.C getC() {
        return C;
    }
    @JsonSetter("C")
    public void setC(apiPojo.apiReq.formulaBuilder.C C) {
        this.C = C;
    }
}
