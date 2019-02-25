package app.domain.models.view;

public class AllDocumentViewModel {
    private String title;
    private String trimmedTitle;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrimmedTitle() {
        //TODO trim the title
        return this.trimmedTitle;
    }

    public void setTrimmedTitle(String trimmedTitle) {
        this.trimmedTitle = trimmedTitle;
    }
}
