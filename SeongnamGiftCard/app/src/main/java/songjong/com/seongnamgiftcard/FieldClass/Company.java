package songjong.com.seongnamgiftcard.FieldClass;

/**
 * Created by TaeHyungKim on 2017-08-13.
 */

public class Company {
    String companyName;
    String companyNumber;
    String companyAddress;
    int companyImage;

    public Company(String companyName, String companyNumber, String companyAddress,int companyImage) {
        this.companyName = companyName;
        this.companyNumber = companyNumber;
        this.companyAddress = companyAddress;
        this.companyImage = companyImage;
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

    public int getCompanyImage() {
        return companyImage;
    }
    public void setCompanyImage(int companyImage) {
        this.companyImage = companyImage;
    }
}
