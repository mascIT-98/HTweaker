package com.hasta.htweaker;


import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AbstractNavDrawerActivity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( savedInstanceState == null ) {
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();
        }
    }
    
    @Override
    protected NavDrawerActivityConfiguration getNavDrawerConfiguration() {
        
        NavDrawerItem[] menu = new NavDrawerItem[] {
                NavMenuItem.create(1,"KERNEL", "", false, this),
                NavMenuItem.create(2, "MISC", "", false, this), 
                NavMenuItem.create(3, "BATTERY", "", false, this),
                NavMenuItem.create(4, "CPU", "", false, this), 
                NavMenuItem.create(5, "EXPLAIN", "", false, this),
                NavMenuItem.create(6, "HELP", "", false, this)
                };
        
        NavDrawerActivityConfiguration navDrawerActivityConfiguration = new NavDrawerActivityConfiguration();
        navDrawerActivityConfiguration.setMainLayout(R.layout.main);
        navDrawerActivityConfiguration.setDrawerLayoutId(R.id.drawer_layout);
        navDrawerActivityConfiguration.setLeftDrawerId(R.id.left_drawer);
        navDrawerActivityConfiguration.setNavItems(menu);
        navDrawerActivityConfiguration.setDrawerShadow(R.drawable.drawer_shadow);       
        navDrawerActivityConfiguration.setDrawerOpenDesc(R.string.drawer_open);
        navDrawerActivityConfiguration.setDrawerCloseDesc(R.string.drawer_close);
        navDrawerActivityConfiguration.setBaseAdapter(
            new NavDrawerAdapter(this, R.layout.navdrawer_item, menu ));
        return navDrawerActivityConfiguration;
    }
    
    @Override
    public void onNavItemSelected(int id) {
        switch (id) {      
        case 1: 
        	 Handler h = new Handler();
        	h.postDelayed(new Runnable() {
        	  @Override
        	  public void run() {
        	getFragmentManager().beginTransaction().replace(R.id.content_frame, new Kernel()).commit();  
        	  }
        	}, 400);
            break;
        case 2:    
        	Handler h2 = new Handler();
        	h2.postDelayed(new Runnable() {
        	  @Override
        	  public void run() {
        	getFragmentManager().beginTransaction().replace(R.id.content_frame, new Misc()).commit();  
        	  }
        	}, 400);
        	break;
        case 3:
        	Handler h3 = new Handler();
        	h3.postDelayed(new Runnable() {
        	  @Override
        	  public void run() {
        	getFragmentManager().beginTransaction().replace(R.id.content_frame, new Battery()).commit();  
        	  }
        	}, 400);
        	break;
        case 4:
        	Handler h4 = new Handler();
        	h4.postDelayed(new Runnable() {
        	  @Override
        	  public void run() {
        	getFragmentManager().beginTransaction().replace(R.id.content_frame, new CpuFrag()).commit();  
        	  }
        	}, 400);
        	break;
        case 5:
        	Handler h5 = new Handler();
        	h5.postDelayed(new Runnable() {
        	  @Override
        	  public void run() {
        	getFragmentManager().beginTransaction().replace(R.id.content_frame, new Explain()).commit();  
        	  }
        	}, 400);
        	break;
        case 6:
        	Handler h6 = new Handler();
        	h6.postDelayed(new Runnable() {
        	  @Override
        	  public void run() {
        	getFragmentManager().beginTransaction().replace(R.id.content_frame, new Help()).commit();  
        	  }
        	}, 400);
        	break;
        }      
    }
}
