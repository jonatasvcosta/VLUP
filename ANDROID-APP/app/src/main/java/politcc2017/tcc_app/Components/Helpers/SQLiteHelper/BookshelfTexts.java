package politcc2017.tcc_app.Components.Helpers.SQLiteHelper;

import com.afollestad.inquiry.annotations.Column;
import com.afollestad.inquiry.annotations.Table;

/**
 * Created by Jonatas on 15/02/2017.
 */

@Table
public class BookshelfTexts {
    public BookshelfTexts(){

    }

    public BookshelfTexts(int id, String content){
        this.id = id;
        this.content = content;
        this.categories = "";
    }

    public BookshelfTexts(int id, String content, String categories){
        this.id = id;
        this.content = content;
        this.categories = categories;
    }
    @Column
    public int id;
    @Column
    public String content;
    @Column
    public String categories;
}
