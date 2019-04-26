package com.julia.bachelor;

import com.julia.bachelor.helperClass.Annet;
import com.julia.bachelor.helperClass.BondensMarked;
import com.julia.bachelor.helperClass.Hjemme;
import com.julia.bachelor.helperClass.Salg;
import com.julia.bachelor.helperClass.SalgTemplate;
import com.julia.bachelor.helperClass.Videresalg;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    ArrayList<SalgTemplate> salg;

    @Before
    private void setVariables(){
        String varer = "1-1,2-3,3-0,4-5,5-0,6-0,7-0,8-0,9-0,";
        String date = Beregninger.getDate();
        String Betaling = "Kort";
        String kunde = "Sam Knight";
        salg = new ArrayList<>();
        SalgTemplate hjemme = new Hjemme();
        SalgTemplate bm = new BondensMarked();
        SalgTemplate videre = new Videresalg();
        SalgTemplate annet = new Annet();
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}