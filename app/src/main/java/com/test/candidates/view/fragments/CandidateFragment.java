package com.test.candidates.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.test.candidates.R;
import com.test.candidates.adapters.DataCandidateListAdapter;
import com.test.candidates.presenter.Presenter;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CandidateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CandidateFragment extends Fragment{

    View view;
    RecyclerView recyclerView;
    Presenter presenter;
    DataCandidateListAdapter dataAdapter;

    private CallBackListener callBackListener;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CandidateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CandidateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CandidateFragment newInstance(String param1, String param2) {
        CandidateFragment fragment = new CandidateFragment();
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
        if (getActivity() instanceof CallBackListener) {
            callBackListener = (CallBackListener) getActivity();
            callBackListener.fragmentLoaded();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        initUI();
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.search_menu, menu);

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        // getting search view of our item.
        SearchView searchView = (SearchView) searchItem.getActionView();

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.

                    filterData(newText);
                    return false;
            }
        });
    }

    private void filterData(String newText) {

         presenter.filterData(newText);

    }

    private void initUI() {

        recyclerView = view.findViewById(R.id.list_data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void initiateAdaptors()
    {
        if (presenter!=null) {
            dataAdapter = new DataCandidateListAdapter(presenter,getContext());
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
    @Override
    public void onResume() {
        super.onResume();
    }

    public interface CallBackListener {
        void fragmentLoaded();//to let mainactivity know that fragment has loaded
    }
}