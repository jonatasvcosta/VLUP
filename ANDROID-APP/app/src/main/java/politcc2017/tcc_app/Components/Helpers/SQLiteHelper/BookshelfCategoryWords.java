package politcc2017.tcc_app.Components.Helpers.SQLiteHelper;

import com.afollestad.inquiry.annotations.Column;
import com.afollestad.inquiry.annotations.Table;

/**
 * Created by Jonatas on 15/02/2017.
 */

@Table
public class BookshelfCategoryWords {
    public BookshelfCategoryWords(){

    }

    public BookshelfCategoryWords(int id, String name, String language){
        this.id = id;
        this.name = name;
        this.language = language;
    }

    @Column
    public int id;
    @Column
    public String name;
    @Column
    public String language;
}
