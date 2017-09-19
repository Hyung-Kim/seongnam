package songjong.com.seongnamgiftcard.FieldClass;

/**
 * Created by taehyung on 2017-08-21.
 */

public class Review {
    private String contents;
    private String date;
    public Review(String contents) {
        this.contents = contents;
    }
    public Review(String contents, String date) {
        this.contents = contents;
        this.date = date;
    }
    public String getContents() {
        return contents;
    }
    public void setContents(String contents) {
        this.contents = contents;
    }
    public String getDate() { return date;}
    public void setDate(String date) { this.date = date;}
}
