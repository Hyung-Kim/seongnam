package songjong.com.seongnamgiftcard.FieldClass;

/**
 * Created by TaeHyungKim on 2017-08-13.
 */

public class Company {

    private String companyName;
    private String companyNumber;
    private String companyAddress;
    private double companyLatitude;
    private double companyLongitude;
    private String companySubGroup;
    public Company(String companyName, String companyNumber, String companyAddress,String companyLatitude,
                   String companyLongitude, String companySubGroup) {
        this.companyName = companyName;
        this.companyNumber = companyNumber;
        this.companyAddress = companyAddress;
        this.companyLatitude = Double.parseDouble(companyLatitude);
        this.companyLongitude = Double.parseDouble(companyLongitude);
        this.companySubGroup = companySubGroup;
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

    public double getCompanyLongitude() {return companyLongitude;}
    public void setCompanyLongitude(String companyLongitude) {this.companyLongitude = Double.parseDouble(companyLongitude); }

    public String getCompanySubGroup() {return companySubGroup;}
    public void setCompanySubGroup(String companySubGroup) {this.companySubGroup = companySubGroup; }
}
