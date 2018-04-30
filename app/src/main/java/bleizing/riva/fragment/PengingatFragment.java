package bleizing.riva.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import bleizing.riva.R;
import bleizing.riva.adapter.PengingatAdapter;
import bleizing.riva.model.GulaDarah;
import bleizing.riva.model.Model;

/**
 * A simple {@link Fragment} subclass.
 */
public class PengingatFragment extends Fragment {

    private PengingatAdapter pengingatAdapter;
    private ListView listView;

    public PengingatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pengingat, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<GulaDarah> gulaDarahArrayListTemp = new ArrayList<>();

        ArrayList<GulaDarah> gulaDarahArrayList = new ArrayList<>();

        gulaDarahArrayListTemp = Model.getGds();

        for (GulaDarah gulaDarah : gulaDarahArrayListTemp) {
            if (!gulaDarah.getCatatan().equals("")) {
                gulaDarahArrayList.add(gulaDarah);
            }
        }

        pengingatAdapter = new PengingatAdapter(getContext(), gulaDarahArrayList);
        listView = (ListView) getActivity().findViewById(R.id.lvPengingat);
        listView.setAdapter(pengingatAdapter);
    }
}
