package songjong.com.seongnamgiftcard.FieldClass;

/**
 * Created by TaeHyungKim on 2017-08-13.
 */

public class Company {
    String companyName;
    String companyNumber;
    String companyAddress;
    double companyLatitude;
    double companyLongitude;

    public Company(String companyName, String companyNumber, String companyAddress,String companyLatitude,
                   String companyLongitude) {
        this.companyName = companyName;
        this.companyNumber = companyNumber;
        this.companyAddress = companyAddress;
        this.companyLatitude = Double.parseDouble(companyLatitude);
        this.companyLongitude = Double.parseDouble(companyLongitude);
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }
    public void setCompanyNumber(String companyNumber) {this.companyNumber = companyNumber;}

    public String getCompanyAddress() {
        return companyAddress;
    }
    public void setCompanyAddress(String companyAddress) {this.companyAddress = companyAddress; }

    public double getCompanyLatitude() {
        return companyLatitude;
    }
    public void setCompanyLatitude(String companyLatitude) {this.companyLatitude = Double.parseDouble(companyLatitude); }

    public double getCompanyLongitude() {
        return companyLongitude;
    }
    public void setCompanyLongitude(String companyLongitude) {this.companyLongitude = Double.parseDouble(companyLongitude); }
}
