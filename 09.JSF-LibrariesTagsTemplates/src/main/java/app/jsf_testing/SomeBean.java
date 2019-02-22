package app.jsf_testing;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;

@Named
@RequestScoped
public class SomeBean {
    private String something;

    public String getSomething() {
        if (this.something == null)
            return "wazaaa";
        else
            return this.something;
    }

    public void setSomething(String something) {
        this.something = something;
    }

    public void resetPage() throws IOException {
//        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//        externalContext.redirect("/");
    }

    public String getStuff() {
        return "this is strange";
    }
}
