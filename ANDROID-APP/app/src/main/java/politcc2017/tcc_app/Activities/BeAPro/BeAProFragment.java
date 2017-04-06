package politcc2017.tcc_app.Activities.BeAPro;

/**
 * Created by Jonatas on 06/04/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import politcc2017.tcc_app.R;

public class BeAProFragment extends Fragment{

    public BeAProFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.be_a_pro_list_news, container, false);
    }

}