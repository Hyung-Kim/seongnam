package songjong.com.seongnamgiftcard.FieldClass;

/**
 * Created by TaeHyungKim on 2017-08-13.
 */

public class Company {
    String radioName;
    int radioArt;
    String radioDial;

    public Company(String radioName, int radioArt, String radioDial) {
        this.radioName = radioName;
        this.radioArt = radioArt;
        this.radioDial = radioDial;
    }
    public Company(String radioName){ this.radioName =  radioName; }
    public String getRadioName() {
        return radioName;
    }

    public void setRadioName(String radioName) {
        this.radioName = radioName;
    }

    public int getRadioArt() {
        return radioArt;
    }

    public void setRadioArt(int radioArt) {
        this.radioArt = radioArt;
    }

    public String getRadioDial() {
        return radioDial;
    }

    public void setRadioDial(String radioDial) {
        this.radioDial = radioDial;
    }

}
