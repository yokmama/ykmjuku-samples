
package jp.ykmjuku.android.samples.nfcsample02;

import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {
    private NfcAdapter mAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFiltersArray;
    private String[][] techListsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = NfcAdapter.getDefaultAdapter(this);

        pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndef.addDataType("text/plain");
        } catch (MalformedMimeTypeException e) {
            throw new RuntimeException("失敗", e);
        }
        intentFiltersArray = new IntentFilter[] {
            ndef
        };

        techListsArray = new String[][] {
                new String[] {
                        NfcA.class.getName(), Ndef.class.getName()
                },
                new String[] {
                    NfcF.class.getName()
                }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        mAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                Toast.makeText(this, makeToastText(tag.getTechList()), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String makeToastText(String[] strs) {
        StringBuilder buf = new StringBuilder();
        for (String s : strs) {
            if (buf.length() > 0) {
                buf.append("\n");
            }
            buf.append(s);
        }

        return buf.toString();
    }

}
