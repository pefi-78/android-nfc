package de.berlin.htw.mynfcreader;

import android.app.Activity;
import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  implements MyCardReader.AccountCallback {

    public MyCardReader mReader;

    // Recommend NfcAdapter flags for reading from other Android devices. Indicates that this
    // activity is interested in NFC-A devices (including other Android devices), and that the
    // system should not check for the presence of NDEF-formatted data (e.g. Android Beam).
    public static int READER_FLAGS =
            NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.addLogLine("onCreate");

        mReader = new MyCardReader(this);

        // Disable Android Beam and register our card reader callback
        enableReaderMode();
    }

    private void addLogLine(String newLine){
        TextView tv = (TextView) findViewById(R.id.txtOutput);
        tv.append(System.getProperty("line.separator"));
        tv.append(newLine);
    }

    public void onClickBtnNfcRead(View v)
    {
        Toast.makeText(this, "Clicked on Button", Toast.LENGTH_LONG).show();
    }



    @Override
    public void onResume() {
        super.onResume();
        enableReaderMode();
    }

    @Override
    public void onAccountReceived(final String account) {
        // This callback is run on a background thread, but updates to UI elements must be performed
        // on the UI thread.
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //mAccountField.setText(account);
                addLogLine("onAccountReceived " + account);
            }
        });
    }

    private void enableReaderMode() {
        //Log.i(TAG, "Enabling reader mode");
        /// Activity activity = getActivity();
        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(this);
        if (nfc != null) {
            nfc.enableReaderMode(this, mReader, READER_FLAGS, null);
        }
    }

    private void disableReaderMode() {
        //Log.i(TAG, "Disabling reader mode");
        //Activity activity = getActivity();
        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(this);
        if (nfc != null) {
            nfc.disableReaderMode(this);
        }
    }

}
