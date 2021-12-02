package com.test.candidates.view.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.candidates.R;
import com.test.candidates.adapters.DataSelectedListAdapter;
import com.test.candidates.presenter.Presenter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public View view;
    public RecyclerView recyclerView;
    public Presenter presenter;
    public DataSelectedListAdapter dataAdapter;

    private SelectedFragment.CallBackListener callBackListener;

    public SelectedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectedFragment newInstance(String param1, String param2) {
        SelectedFragment fragment = new SelectedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setRetainInstance(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() instanceof CallBackListener)
        {
            callBackListener = (CallBackListener) getActivity();
            callBackListener.fragmentSelectLoaded();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        initUI();
        // Inflate the layout for this fragment
        return view;
    }

    private void initUI() {

        recyclerView = view.findViewById(R.id.list_data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Row is swiped from recycler view
                // remove it from adapter

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle(R.string.confirmdialogheader);
                builder.setMessage(R.string.confirmmsg);

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        dialog.dismiss();
                        int position = viewHolder.getAdapterPosition();
                        callBackListener.deleteRow(position);
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                // view the background view
            }
        };

        // attaching the touch helper to recycler view
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

    }

    public void initiateAdaptors()
    {
        if (presenter!=null) {
            dataAdapter = new DataSelectedListAdapter(presenter,getContext());
            recyclerView.setAdapter(dataAdapter);
        }
    }

    public void setPresenter(Presenter presenter)
    {
        this.presenter = presenter;
    }

    public void refreshAdaptor()
    {
        dataAdapter.notifyDataSetChanged();
    }

    public void notifyItemRemoved(int position)
    {
        dataAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public interface CallBackListener {
        //   void onCallBack(int indexClicked,Presenter presenter);
        void fragmentSelectLoaded();
        void deleteRow(int position);
    }
}