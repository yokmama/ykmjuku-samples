
package jp.ykmjuku.android.samples.nfcsample01;

import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        
        if(getIntent()!=null){
            if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())) {
                Tag tag = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
                Toast.makeText(this, makeToastText(tag.getTechList()), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String makeToastText(String[] strs){
        StringBuilder buf = new StringBuilder();
        for(String s : strs){
            if(buf.length()>0){
                buf.append("\n");
            }
            buf.append(s);
        }
        
        return buf.toString();
    }
    
}
