package politcc2017.tcc_app.Components.Helpers.SQLiteHelper;

import com.afollestad.inquiry.annotations.Column;
import com.afollestad.inquiry.annotations.Table;

/**
 * Created by Jonatas on 15/02/2017.
 */

@Table
public class BookshelfCategory {
    public BookshelfCategory(){

    }

    public BookshelfCategory(int id, String name){
        this.id = id;
        this.name = name;
        this.header = false;
        this.textCategory = false;
    }

    public BookshelfCategory(int id, String name, boolean header, boolean textCategory){
        this.id = id;
        this.name = name;
        this.header = header;
        this.textCategory = textCategory;
    }
    @Column(primaryKey = true)
    public int id;
    @Column
    public String name;
    @Column
    public boolean header;
    @Column
    public boolean textCategory;
}
