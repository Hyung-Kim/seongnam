package songjong.com.seongnamgiftcard.CompanyFragment;

/**
 * Created by TaeHyungKim on 2017-08-16.
 */

public class Menu {
    String name;
    public Menu(String radioName){ this.name =  radioName; }
    public String getRadioName() {
        return name;
    }
    public void setRadioName(String radioName) {
        this.name = radioName;
    }
}
