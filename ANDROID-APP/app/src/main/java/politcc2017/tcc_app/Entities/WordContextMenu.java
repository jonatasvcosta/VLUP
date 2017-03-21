package politcc2017.tcc_app.Entities;

/**
 * Created by Jonatas on 20/03/2017.
 */

public class WordContextMenu {
    public String translation, synonym, antonym;
    public String[] similarWords;

    public WordContextMenu(String translation, String synonym, String antonym, String[] similarWords){
        this.translation = translation;
        this.synonym = synonym;
        this.antonym = antonym;
        this.similarWords = similarWords;
    }
}
