class Publication {

    private String title;

    public Publication(String title) {
        this.title = title;
    }

    public final String getInfo() {
        // write your code here
        return this.getType() + " " + getDetails();
    }

    public String getType() {
        return "Publication:";
    }

    public String getDetails() {
        return title;
    }

}

class Newspaper extends Publication {

    private String source;
    private String title;

    public Newspaper(String title, String source) {
        super(title);
        this.title = title;
        this.source = source;
    }

    // write your code here
    @Override
    public String getType() {
        return "Newspaper";
    }

    @Override
    public String getDetails() {
        return "(source - " + source + "): " + title;
    }
}

class Article extends Publication {

    private String author;
    private String title;
    public Article(String title, String author) {
        super(title);
        this.author = author;
        this.title = title;
    }

    // write your code here
    @Override
    public String getType() {
        return "Article";
    }

    @Override
    public String getDetails() {
        return "(author - " + author + "): " + title;
    }

}

class Announcement extends Publication {

    private int daysToExpire;
    private String title;
    public Announcement(String title, int daysToExpire) {
        super(title);
        this.daysToExpire = daysToExpire;
        this.title = title;
    }

    // write your code here
    @Override
    public String getType() {
        return "Announcement";
    }

    @Override
    public String getDetails() {
        return "(days to expire - " + daysToExpire + "): " + title;
    }

}