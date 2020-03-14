package sr.unasat.blogger.Entity;

public class Post {
    private  int id;
    private String title;
    private String date;
    private String body;
    private String image;


    public Post(int id, String title, String date, String body,String image) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.body = body;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
