package social.media.scptest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.chilkatsoft.CkScp;
import com.chilkatsoft.CkSsh;

public class MainActivity extends AppCompatActivity {

    String TAG ="SCP";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CkSsh ssh = new CkSsh();
        String hostname;
        int port;
        hostname = "www.some-ssh-server.com";
        port = 22;

        boolean success = ssh.Connect(hostname, port);
        if (success != true)
        {
            Log.e("Hey", "this is server error");
            Log.e(TAG, ssh.lastErrorText());
            return;
        }
        ssh.put_IdleTimeoutMs(5000);
        success = ssh.AuthenticatePw("dev", "password123!");
        if (success != true){
            Log.e(TAG,ssh.lastErrorText());
            return;
        }
        else {
            Log.e("GOOD","working");
        }

        CkScp scp = new CkScp();
        success  = scp.UseSsh(ssh);
        if (success != true){
            Log.e("Hey", "this is android error");
            Log.e(TAG, scp.lastErrorText());
            return;
        }
        String remotePath = "/mnt";
        String localPath = "/home/bob/test.txt";
        success = scp.UploadFile(localPath,remotePath);
        if (success != true){
            Log.e("Hey", "this is upload error");
            Log.e(TAG, scp.lastErrorText());
            return;
        }
        Log.e(TAG, "upload success");
        ssh.Disconnect();
    }
    static {
        System.loadLibrary("chilkat");
    }
}
