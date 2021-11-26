package com.test.candidates.presenter;

import android.content.Context;
import android.widget.Toast;

import com.test.candidates.model.Model;
import com.test.candidates.rest.CandidateResponse;
import com.test.candidates.rest.ClientRx;
import com.test.candidates.rest.RestClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/*Presenter class*/
public class Presenter {

    @NonNull
    private CompositeDisposable compositeDisposable = new CompositeDisposable();//to be disposed on OnDestroy
    private ClientRx clientRx;

    private Context context;

    private View candidatesView,selectedView,detailsView;

    List<Model> data = new ArrayList<Model>();

    List<Model> selecteddata = new ArrayList<Model>();


    public Presenter(View candidatesView,Context context/*, View selectedView, View detailsView,Context context*/) {
        this.candidatesView = candidatesView;
        this.context = context;
       /* this.selectedView = selectedView;
        this.detailsView = detailsView;*/
    }

    public void loadData()
    {
       /* if (data.size()==0) {*/
            RestClient restClient = new RestClient("https://randomuser.me/");
            clientRx = restClient.getClientRx(context);

            compositeDisposable.add(clientRx.getCandidateData("50")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<CandidateResponse>() {
                        @Override
                        public void accept(CandidateResponse serverResponse) throws Exception {

                            try {
                                for(int i=0;i<serverResponse.getResults().size();i++)
                                {
                                    String addr = serverResponse.getResults().get(i).getLocation().getStreet().getNumber()
                                            + "," + serverResponse.getResults().get(i).getLocation().getStreet().getName()
                                            + "," + serverResponse.getResults().get(i).getLocation().getCity()
                                            + "," + serverResponse.getResults().get(i).getLocation().getState()
                                            + "," + serverResponse.getResults().get(i).getLocation().getCountry()
                                            + "," + serverResponse.getResults().get(i).getLocation().getPostcode();

                                    Model model = new Model(serverResponse.getResults().get(i).getPicture().getLarge(),
                                            serverResponse.getResults().get(i).getName().getTitle(),
                                            serverResponse.getResults().get(i).getName().getFirst(),
                                            serverResponse.getResults().get(i).getName().getLast(),
                                            serverResponse.getResults().get(i).getDob().getAge(),
                                            serverResponse.getResults().get(i).getDob().getDate(),
                                            serverResponse.getResults().get(i).getEmail(),
                                            serverResponse.getResults().get(i).getPhone() ,
                                            addr,
                                            false);

                                    data.add(model);
                                }
                                candidatesView.addData(data);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            throwable.printStackTrace();
                            Toast.makeText(context,"Error occurred. Please try again",Toast.LENGTH_SHORT).show();
                        }
                    }));
      /*  } else {
            candidatesView.addData(data);
            Toast.makeText(context,"test1",Toast.LENGTH_SHORT).show();
        }*/


    }

    public void filterData(String text)
    {
        ArrayList<Model> filteredlist = new ArrayList<>();

        if (text.length()>3) {
            // running a for loop to compare elements.
            for (Model item : data) {
                // checking if the entered string matched with any item of our recycler view.
                if (item.getFirstName().toLowerCase().contains(text.toLowerCase())
                   || item.getLastName().toLowerCase().contains(text.toLowerCase()))
                {
                    // if the item is matched we are
                    // adding it to our filtered list.
                    filteredlist.add(item);
                }
            }
            candidatesView.addData(filteredlist);

            if(!filteredlist.isEmpty())
            {
                candidatesView.addData(filteredlist);
            }

        }
        else
        {
            candidatesView.addData(data);
        }

    }

    public void onItemInteraction(int i)
    {
        candidatesView.showDetails(data.get(i));
    }

    public interface View {
        void addData(List<Model> data);
        void showDetails(Model model);
    }

}

