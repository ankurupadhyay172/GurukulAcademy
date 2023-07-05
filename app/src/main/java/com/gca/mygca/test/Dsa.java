package com.gca.mygca.test;

import android.util.Log;

import com.gca.mygca.model.response.SchoolFeesResponseModel;

import java.util.ArrayList;
import java.util.List;

public class Dsa {
    public List<SchoolFeesResponseModel> sortByAmount(List<SchoolFeesResponseModel> list){
        quickSort(list,0,list.size()-1);
        return list;
    }
    void swap(List<SchoolFeesResponseModel> list,int i,int j){
        SchoolFeesResponseModel temp = list.get(i);
        list.set(i,list.get(j));
        list.set(j,temp);
    }

    int findPartition(List<SchoolFeesResponseModel> list,int s,int e){
        int i=s-1;
        int p = Integer.parseInt(list.get(e).getAmount());

        for (int j=s;j<e;j++){
            if(Integer.parseInt(list.get(j).getAmount())>p){
                i++;
                swap(list,i,j);
            }
        }
        swap(list,i+1,e);

        return  i+1;
    }

    private void quickSort(List<SchoolFeesResponseModel> list, int s, int e) {
        if(s<e) {
        int p = findPartition(list,s,e);
        quickSort(list,s,p-1);
        quickSort(list,p+1,e);
        }
    }

    public void deleteBoardId(String id,List<SchoolFeesResponseModel> list){
        int counter = 0;
        for (int i=0;i<list.size();i++){
            if (!list.get(i).getBoard_id().equals(id)){
                list.set(counter++,list.get(i));
            }
        }
        for (SchoolFeesResponseModel model:list){
            Log.d("myTAG123", "deleteBoardId: "+model);
        }

    }


    public void onlyUniqueValue(List<SchoolFeesResponseModel> list){
        List<String> list1 = new ArrayList<>();
        if (!list.isEmpty()){
            String unique = list.get(0).getAmount();
            for(int i=0;i<list.size();i++){
                if(list.get(i).getAmount().equals(unique))
                    continue;

                unique = list.get(i).getAmount();
                list1.add(list.get(i).getAmount());
            }
            for (String s:list1){
                Log.d("myuniqueValues", "onlyUniqueValue: "+s);
            }
        }

    }
}
