package politcc2017.tcc_app.Components.Helpers.SQLiteHelper;

import com.afollestad.inquiry.annotations.Column;
import com.afollestad.inquiry.annotations.Table;

/**
 * Created by Jonatas on 15/02/2017.
 */

@Table
public class ScoringRules {
    public ScoringRules(){

    }

    public ScoringRules(String name, int scoring){
        this.name = name;
        this.scoring = scoring;
    }

    @Column
    public int scoring;
    @Column
    public String name;

}
