package mostlydead.whatthehex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final int NUMERIC_FIELD = 1;
    private static final int HEX_FIELD = 2;
    private static final int BINARY_FIELD = 3;
    private int currentField = 0;

    //Will tell if a number is too large to be converted to an int
    private boolean properNumber(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!(c > 47 && c < 58)) {
                return false;
            }
        }
        return true;
    }

    //Will check to see if the given string is hex formatted. Assumes that all characters are upper case.
    private boolean properHex(String str) {
        for (int i = 0; i < str.length(); i++){
            char c = str.charAt(i);
            if (!((c > 47 && c < 58) || (c > 64 && c < 71))) {
                return false;
            }
        }
        return true;
    }

    //Will check to see if the given string is binary formatted
    private boolean properBinary(String str) {
        for (int i = 0; i < str.length(); i++){
            char c = str.charAt(i);
            if (c != '0' && c != '1') {
                return false;
            }
        }
        return true;
    }

    //Will reset the text of all of the text boxes
    private void resetTextEdits() {
        EditText numberEdit = (EditText) findViewById(R.id.main_activity_number_value);
        EditText hexEdit = (EditText) findViewById(R.id.main_activity_hex_value);
        EditText binaryEdit = (EditText) findViewById(R.id.main_activity_binary_value);
        numberEdit.setText("");
        hexEdit.setText("");
        binaryEdit.setText("");

        Button butt = (Button) findViewById(R.id.main_activity_button);
        butt.setText(getString(R.string.main_activity_button_wrong));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Add a focus event listener to the number value field
        findViewById(R.id.main_activity_number_value).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //We do not check the input because it is a numeric text field
                    currentField = NUMERIC_FIELD;
                    Button butt = (Button) findViewById(R.id.main_activity_button);
                    butt.setText(getString(R.string.main_activity_button_numeral));
                }
            }
        });

        //Add a focus event listener to the hex value field
        findViewById(R.id.main_activity_hex_value).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    currentField = HEX_FIELD;
                    Button butt = (Button) findViewById(R.id.main_activity_button);
                    butt.setText(getString(R.string.main_activity_button_hex));
                }
            }
        });

        //Add a focus event listener to the binary value field
        findViewById(R.id.main_activity_binary_value).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    currentField = BINARY_FIELD;
                    Button butt = (Button) findViewById(R.id.main_activity_button);
                    butt.setText(getString(R.string.main_activity_button_binary));
                }
            }
        });
    }

    //Will Check if the int translation is small enough
    public boolean isReasonableInt(String str) {
        return str.length() < 10 || (str.length() == 10 && str.compareTo("2147483647") <= 0);
    }

    //Will check if the hex translation is small enough
    public boolean isReasonableHex(String str) {
        return str.length() < 8 || (str.length() == 8 && str.compareTo("7FFFFFFF") <= 0);
    }

    //Will check if the binary translation is small enough
    public boolean isReasonableBinary(String str) {
        return str.length() < 31 || (str.length() == 31 && str.compareTo("1111111111111111111111111111111") <= 0);
    }


    //method that works when the convert button is pressed
    public void calculateResults(View v) {
        EditText numberEdit = (EditText) findViewById(R.id.main_activity_number_value);
        EditText hexEdit = (EditText) findViewById(R.id.main_activity_hex_value);
        EditText binaryEdit = (EditText) findViewById(R.id.main_activity_binary_value);
        String startValue;
        int intValue;

        switch (currentField) {
            case NUMERIC_FIELD:
                startValue = numberEdit.getText().toString();
                if (startValue.length() > 0 && properNumber(startValue) && isReasonableInt(startValue)) {
                    intValue = Integer.parseInt(startValue);
                    hexEdit.setText(Integer.toHexString(intValue));
                    binaryEdit.setText(Integer.toBinaryString(intValue));
                } else {
                    resetTextEdits();
                }
                break;
            case HEX_FIELD:
                startValue = hexEdit.getText().toString().toUpperCase();
                if (startValue.length() > 0 && properHex(startValue) && isReasonableHex(startValue)) {
                    intValue = Integer.parseInt(startValue,16);
                    numberEdit.setText(Integer.toString(intValue));
                    binaryEdit.setText(Integer.toBinaryString(intValue));
                } else {
                    resetTextEdits();
                }
                break;
            case BINARY_FIELD:
                startValue = binaryEdit.getText().toString();
                if (startValue.length() > 0 && properBinary(startValue) && isReasonableBinary(startValue)) {
                    intValue = Integer.parseInt(startValue, 2);
                    numberEdit.setText(Integer.toString(intValue));
                    hexEdit.setText(Integer.toHexString(intValue));
                } else {
                    resetTextEdits();
                }
                break;
        }
    }
}
