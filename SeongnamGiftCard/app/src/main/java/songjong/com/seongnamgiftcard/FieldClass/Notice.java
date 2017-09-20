package songjong.com.seongnamgiftcard.FieldClass;

/**
 * Created by taehyung on 2017-09-20.
 */

public class Notice {
    private String title, contents, date;
    public Notice(String title, String contents, String date){ this.title =  title; this.contents = contents; this.date = date; }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContents() {
        return contents;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
