package songjong.com.seongnamgiftcard.FieldClass;

/**
 * Created by TaeHyungKim on 2017-08-13.
 */

public class Company {
    private String companyName;
    private String companyNumber;
    private String companyAddress;
    private String companySubGroup;


    public Company(String companyName, String companyNumber, String companyAddress) {
        this.companyName = companyName;
        this.companyNumber = companyNumber;
        this.companyAddress = companyAddress;
    }
    public Company(String companyName, String companyNumber, String companyAddress,String companySubGroup) {
        this.companyName = companyName;
        this.companyNumber = companyNumber;
        this.companyAddress = companyAddress;
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
    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }
    public void setCompanyAddress(String companyAddress) {this.companyAddress = companyAddress; }

    public String getCompanySubGroup() {
        return companySubGroup;
    }
    public void setCompanyImage(String companySubGroup) {
        this.companySubGroup = companySubGroup;
    }
}
