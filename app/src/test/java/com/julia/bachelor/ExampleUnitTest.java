package com.julia.bachelor;

import com.julia.bachelor.helperClass.Annet;
import com.julia.bachelor.helperClass.BondensMarked;
import com.julia.bachelor.helperClass.Hjemme;
import com.julia.bachelor.helperClass.SalgTemplate;
import com.julia.bachelor.helperClass.SortedObjects;
import com.julia.bachelor.helperClass.Videresalg;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private ArrayList<SalgTemplate> sale;

    @Before
    public void setVariables(){
        String products = "1-1,2-3,3-0,4-5,5-0,6-0,7-0,8-0,9-0,";
        String date = Tools.getDate();
        String payment = "Kort";
        String customer = "Sam Knight";
        sale = new ArrayList<>();
        SalgTemplate home = new Hjemme();
        SalgTemplate bm = new BondensMarked();
        SalgTemplate resell = new Videresalg();
        SalgTemplate other = new Annet();
        sale.addAll(Arrays.asList(home, bm, resell, other));
        for(SalgTemplate sale : sale){
            sale.setBelop(100);
            sale.setBetaling(payment);
            sale.setDato(date);
            sale.setKunde(customer);
            if (!(sale instanceof Annet))
            sale.setVarer(products);
        }
        other.setVarer("Bifolk-12,Voks-0,Pollinering-0,Dronninger-0,");
    }

    @Test
    public void add_isCorrect() {
        SortedObjects sorted = new SortedObjects(true);
        for(SalgTemplate sale : sale){
            sorted.add(sale);
        }
        assertEquals(400, sorted.getBelop());
        assertEquals(400 , sorted.getBetalings()[1]);
        assertEquals(0, sorted.getBetalings()[0]);
        int[] amounts = new int[9];
        int[] expected = {3,3*3,0,5*3,0,0,0,0,0};
        long start = System.nanoTime();
        String products = sorted.getVarer();
        String[] productAmount = products.split(",");
        for(int i = 0; i < productAmount.length; i++){
            String[] amount = productAmount[i].split("-");
            amounts[i]=Integer.parseInt(amount[1]);
        }
        long end = System.nanoTime();
        for(int j =0; j<9;j++) {
            assertEquals(expected[j], amounts[j]);
        }
        long elapsed = end-start;
        System.out.println("Time elapsed: " + elapsed);
    }
}