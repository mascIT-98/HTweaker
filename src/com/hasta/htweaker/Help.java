package com.hasta.htweaker;




import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class Help extends Fragment implements OnClickListener {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){
		View main = inflater.inflate(R.layout.help, container, false);		
		Button restore =(Button) main.findViewById(R.id.restore);
		restore.setOnClickListener(this);
		Button github =(Button) main.findViewById(R.id.github);	
		github.setOnClickListener(this);
		Button xda=(Button) main.findViewById(R.id.xda);
		xda.setOnClickListener(this);
	 	return main;
	}
	 @Override
	    public void onClick(View v) {
	        switch (v.getId()) {    
	        case R.id.restore:
	        	Utils.mountSystemRW();
	        	Utils.mRunAsSU("rm /system/etc/init.d/deepest_state");
				Utils.mRunAsSU("rm /system/etc/init.d/fsync_off");
				Utils.mRunAsSU("rm /system/etc/init.d/GPU");
				Utils.mRunAsSU("rm /system/etc/init.d/perf");
				Utils.mRunAsSU("rm /system/etc/init.d/net");
				Utils.mRunAsSU("rm /system/etc/init.d/sd");
				Utils.mRunAsSU("rm /system/etc/init.d/perf");
				Utils.mRunAsSU("rm /system/etc/init.d/sleepers");
				Utils.mRunAsSU("rm /system/etc/init.d/vac");
	            break;
	        case R.id.github:
	        	startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://github.com/hastalafiesta-/HTweaker")));
	        	break;
	        case R.id.xda:
	        	startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://forum.xda-developers.com/showthread.php?t=2644914")));
	        	break;
	        }
	 }
}
	

