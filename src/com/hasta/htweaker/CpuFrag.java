package com.hasta.htweaker;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CpuFrag extends Fragment {
	

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) { 
 
		
		Intent i= new Intent(getActivity(), Cpu.class);
        startActivity(i);
		return null;
         
       
    }

}
