package politcc2017.tcc_app.Components.Helpers.SQLiteHelper;

import com.afollestad.inquiry.annotations.Column;
import com.afollestad.inquiry.annotations.Table;

/**
 * Created by Jonatas on 17/10/2017.
 */

@Table
public class CreatedMiniClasses {
    public CreatedMiniClasses(){

    }

    public CreatedMiniClasses(String language, String translationLanguage, String title, String[] categories, String content){
        this.movie = false;
        this.language = language;
        this.translationLanguage = translationLanguage;
        this.title = title;
        this.categories = categories;
        this.content = content;
    }

    public CreatedMiniClasses(String language, String translationLanguage, String title, String[] categories, String movieUrl, boolean movie){
        this.movie = true;
        this.language = language;
        this.translationLanguage = translationLanguage;
        this.title = title;
        this.categories = categories;
        this.movieUrl = movieUrl;
    }

    @Column
    public String language;
    @Column
    public String translationLanguage;
    @Column
    public String title;
    @Column
    public String[] categories;
    @Column
    public String content;
    @Column
    public String movieUrl;
    @Column
    public boolean movie;
}
