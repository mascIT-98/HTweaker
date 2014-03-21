package com.hasta.htweaker;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

class  Utils {
	
    public static String SU_wop(String cmds) {
//     FLAG:0x2
        String out = null;
        try {
            out = new String();
            Process p = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(p.getOutputStream());
            os.writeBytes(cmds+"\n");
            os.writeBytes("exit\n");
            os.flush();
            p.waitFor();
            InputStream stdout = p.getInputStream();
            byte[] buffer = new byte[4096];
            int read;
            while (true) {
                read = stdout.read(buffer);
                out += new String(buffer, 0, read);
                if (read < 4096) {
                    break;
                }
            }
        } catch (Exception e) {
            final Activity activity = new Cpu();
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(activity, "Error Occured...!", Toast.LENGTH_SHORT).show();
                }
            });
            Log.e("cocoremanager", "Error executing SU command, flag:0x2");
        }
        return out.substring(0,out.length()-1);
    }


    public static void copyAssets(String script,String path,int mode,Context context) {
        AssetManager assetManager = context.getAssets();
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(script);
            File outFile = new File(Environment.getExternalStorageDirectory().getPath(), script);
            out = new FileOutputStream(outFile);
            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch(IOException e) {
            Log.e("cocoremanager", "Failed to handle: " + script, e);
        }
        mRunAsSU("mkdir -p " + path, "cp -f "+Environment.getExternalStorageDirectory().getPath()+"/"+script+" "+path+"/"+script, "rm "+Environment.getExternalStorageDirectory().getPath()+"/"+script, "chmod "+Integer.toString(mode)+" "+path+"/"+script);
    }



    public static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
    
    public static void mountSystemRW() {
    	mRunAsSU("busybox mount -o remount,rw /system system");
    }
    
    public static void mSetFilePerm(String path,int mode) {
		mRunAsSU("chmod "+mode+" "+path);
	}
    
    public static void mRunAsSU(String... cmds) {
    	Process process;
		try {
			process = Runtime.getRuntime().exec("su");
	        DataOutputStream os = new DataOutputStream(process.getOutputStream());
	        for(int i=0;i<cmds.length;i++)
	            os.writeBytes(cmds[i]+"\n");
	        os.writeBytes("exit\n");
	        os.flush();
	        process.waitFor();
		} catch(Exception e) {
			Log.e("cocoretest", "Error executing....");
		}
    }
}

class SU extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... cmds) {
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            for(int i=0;i<cmds.length;i++)
                os.writeBytes(cmds[i]+"\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {}
        return null;
    }
}

