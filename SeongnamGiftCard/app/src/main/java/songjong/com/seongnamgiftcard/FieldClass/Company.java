package songjong.com.seongnamgiftcard.FieldClass;

/**
 * Created by TaeHyungKim on 2017-08-13.
 */

public class Company {

    private int companyId;
    private int companyMenu;
    private String companyName;
    private String companyNumber;
    private String companyAddress;
    private double companyLatitude;
    private double companyLongitude;
    private double companyDistance;
    private String companySubGroup;
    public Company(String companyName, String companyNumber, String companyAddress,String companyLatitude,
                   String companyLongitude, String companySubGroup, String companyDistance, String companyId, String companyMenu) {
        this.companyName = companyName;
        this.companyNumber = companyNumber;
        this.companyAddress = companyAddress;
        this.companyLatitude = Double.parseDouble(companyLatitude);
        this.companyLongitude = Double.parseDouble(companyLongitude);
        this.companySubGroup = companySubGroup;
        this.companyDistance = Double.parseDouble(companyDistance);
        this.companyId = Integer.parseInt(companyId);
        this.companyMenu = Integer.parseInt(companyMenu);
    }
    public Company(String companyName, String companyNumber, String companyAddress,String companyLatitude,
                   String companyLongitude, String companyDistance, String companyId, String companyMenu) {
        this.companyName = companyName;
        this.companyNumber = companyNumber;
        this.companyAddress = companyAddress;
        this.companyLatitude = Double.parseDouble(companyLatitude);
        this.companyLongitude = Double.parseDouble(companyLongitude);
        this.companyDistance = Double.parseDouble(companyDistance);
        this.companyId = Integer.parseInt(companyId);
        this.companyMenu = Integer.parseInt(companyMenu);
    }
    public int getCompanyId(){return companyId;}
    public void setCompanyId(String companyId){this.companyId = Integer.parseInt(companyId); }

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

    public int getCompanyMenu() {return companyMenu;}
    public void setCompanyMenu(int companyMenu) {this.companyMenu = companyMenu;}

    public String getCompanySubGroup() {return companySubGroup;}
    public void setCompanySubGroup(String companySubGroup) {this.companySubGroup = companySubGroup; }

    public double getCompanyDistance() {return companyDistance;}
    public void setCompanyDistance(String companyDistance){this.companyDistance = Double.parseDouble(companyDistance);}
}
