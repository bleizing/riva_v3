package bleizing.riva.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bleizing.riva.MyClickListener;
import bleizing.riva.R;
import bleizing.riva.RecyclerTouchListener;
import bleizing.riva.adapter.TrainingAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrainingFragment extends Fragment {

//    private TrainingAdapter trainingAdapter;

    public TrainingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_training, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        trainingAdapter = new TrainingAdapter();
//
//        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.training_recycler_view);
//        recyclerView.setAdapter(trainingAdapter);
//        trainingAdapter.notifyDataSetChanged();
//
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new MyClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
    }
}
