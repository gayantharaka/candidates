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

    private View candidatesView;

    List<Model> orginalData = new ArrayList<Model>();
    List<Model> dataforsearch = new ArrayList<Model>();


    List<Model> selectedData = new ArrayList<Model>();


    public Presenter(View candidatesView,Context context) {
        this.candidatesView = candidatesView;
        this.context = context;

    }

    public void loadData()
    {
        if (orginalData.size()==0) {
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

                                    orginalData.add(model);
                                }
                                dataforsearch.addAll(orginalData);
                                candidatesView.updateCandidateList();
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
        } else {
            candidatesView.updateCandidateList();
    //        Toast.makeText(context,"test1",Toast.LENGTH_SHORT).show();
        }


    }

    public void loadSelectedData()//check selected candidates from original data and retrieve them to show in 2nd tab
    {

        selectedData = new ArrayList<>();

        for(Model temp: orginalData)
        {
            if(temp.isSelected())
            {
                selectedData.add(temp);
            }
        }

        candidatesView.updateSelectedList();
    }

    public void modifyOriginalData(int position)//update original orginalData retrieved from backend
    {
        Model model = selectedData.get(position);
        model.setSelected(false);

        for(int i = 0; i< orginalData.size(); i++)
        {
            if(orginalData.get(i).getEmailAddress().equalsIgnoreCase(model.getEmailAddress()))
            {
                orginalData.get(i).setSelected(false);
            }
        }

        selectedData.remove(position);

    }

    public void filterData(String text)//perform search function
    {
        ArrayList<Model> filteredlist = new ArrayList<>();

        if (text.length()>3) {
            // running a for loop to compare elements.
            for (Model item : orginalData) {
                // checking if the entered string matched with any item of our recycler view.
                if (item.getFirstName().toLowerCase().contains(text.toLowerCase())
                   || item.getLastName().toLowerCase().contains(text.toLowerCase()))
                {
                    // if the item is matched we are
                    // adding it to our filtered list.
                    filteredlist.add(item);
                }
            }

            if(!filteredlist.isEmpty())
            {
                orginalData.clear();
                orginalData.addAll(filteredlist);
                candidatesView.updateCandidateList();
            }

        }
        else
        {
            orginalData.clear();
            orginalData.addAll(dataforsearch);
            candidatesView.updateCandidateList();
        }

    }

    public List<Model> getSelectedData() {
        return selectedData;
    }

    public void setSelectedData(List<Model> selectedData) {
        this.selectedData = selectedData;
    }

    public List<Model> getOrginalData() {
        return orginalData;
    }

    public void setOrginalData(List<Model> orginalData) {
        this.orginalData = orginalData;
    }

    public void onItemInteraction(int indexClicked)
    {
        candidatesView.showDetails(indexClicked);
    }

    public interface View {
        void updateCandidateList(); //refresh candidate list
        void updateSelectedList();//refresh selected list
        void showDetails(int indexClicked);//show details in new screen
    }

}

