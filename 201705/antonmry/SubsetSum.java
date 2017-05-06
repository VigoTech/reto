import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SubsetSum {

    @Test
    public void simplifiedSubsetSumSpec() {
        ArrayList<Integer> test1 = new ArrayList<>(Arrays.asList(1, 2, 3));
        assertEquals(getSubsetSum(test1), false);

        ArrayList<Integer> test2 = new ArrayList<>(Arrays.asList(-5, -3, -1, 2, 4, 6));
        assertEquals(getSubsetSum(test2), false);

        ArrayList<Integer> test3 = new ArrayList<>(Arrays.asList());
        assertEquals(getSubsetSum(test3), false);

        ArrayList<Integer> test4 = new ArrayList<>(Arrays.asList(-1, 1));
        assertEquals(getSubsetSum(test4), true);

        ArrayList<Integer> test5 = new ArrayList<>(Arrays.asList(-97364, -71561, -69336, 19675, 71561, 97863));
        assertEquals(getSubsetSum(test5), true);

        ArrayList<Integer> test6 = new ArrayList<>(Arrays.asList(-53974, -39140, -36561, -23935, -15680, 0));
        assertEquals(getSubsetSum(test6), true);
    }

    @Ignore("Not ready yet")
    @Test
    public void bonus1SubsetSumSpec() {
        ArrayList<Integer> test1 = new ArrayList<>(Arrays.asList(-83314, -82838, -80120, -63468, -62478, -59378,
                -56958, -50061, -34791, -32264, -21928, -14988, 23767, 24417, 26403, 26511, 36399, 78055));
        assertEquals(getSubsetSum(test1), true);

        ArrayList<Integer> test2 = new ArrayList<>(Arrays.asList(-92953, -91613, -89733, -50673, -16067, -9172,
                8852, 30883, 46690, 46968, 56772, 58703, 59150, 78476, 84413, 90106, 94777, 95148));
        assertEquals(getSubsetSum(test2), true);

        ArrayList<Integer> test3 = new ArrayList<>(Arrays.asList(-94624, -86776, -85833, -80822, -71902, -54562,
                -38638, -26483, -20207, -1290, 12414, 12627, 19509, 30894, 32505, 46825, 50321, 69294));
        assertEquals(getSubsetSum(test3), true);

        ArrayList<Integer> test4 = new ArrayList<>(Arrays.asList(-83964, -81834, -78386, -70497, -69357, -61867,
                -49127, -47916, -38361, -35772, -29803, -15343, 6918, 19662, 44614, 66049, 93789, 95405));
        assertEquals(getSubsetSum(test4), true);

        ArrayList<Integer> test5 = new ArrayList<>(Arrays.asList(-68808, -58968, -45958, -36013, -32810, -28726,
                -13488, 3986, 26342, 29245, 30686, 47966, 58352, 68610, 74533, 77939, 80520, 87195));
        assertEquals(getSubsetSum(test5), true);

        ArrayList<Integer> test6 = new ArrayList<>(Arrays.asList(-97162, -95761, -94672, -87254, -57207, -22163,
                -20207, -1753, 11646, 13652, 14572, 30580, 52502, 64282, 74896, 83730, 89889, 92200));
        assertEquals(getSubsetSum(test6), false);

        ArrayList<Integer> test7 = new ArrayList<>(Arrays.asList(-93976, -93807, -64604, -59939, -44394, -36454,
                -34635, -16483, 267, 3245, 8031, 10622, 44815, 46829, 61689, 65756, 69220, 70121));
        assertEquals(getSubsetSum(test7), false);

        ArrayList<Integer> test8 = new ArrayList<>(Arrays.asList(-92474, -61685, -55348, -42019, -35902, -7815,
                -5579, 4490, 14778, 19399, 34202, 46624, 55800, 57719, 60260, 71511, 75665, 82754));
        assertEquals(getSubsetSum(test8), false);

        ArrayList<Integer> test9 = new ArrayList<>(Arrays.asList(-85029, -84549, -82646, -80493, -73373, -57478,
                -56711, -42456, -38923, -29277, -3685, -3164, 26863, 29890, 37187, 46607, 69300, 84808));
        assertEquals(getSubsetSum(test9), false);

        ArrayList<Integer> test10 = new ArrayList<>(Arrays.asList(-87565, -71009, -49312, -47554, -27197, 905, 2839,
                8657, 14622, 32217, 35567, 38470, 46885, 59236, 64704, 82944, 86902, 90487));
        assertEquals(getSubsetSum(test10), false);
    }


    @Ignore("Not ready yet")
    @Test(timeout = 300000)
    public void bonusSubsetSumNinetyRecordsTrueSpec() {
        ArrayList<Integer> test1 = new ArrayList<>(Arrays.asList(-83314, -82838, -80120, -63468, -62478, -59378,
                -56958, -50061, -34791, -32264, -21928, -14988, 23767, 24417, 26403, 26511, 36399, 78055, -92953,
                -91613, -89733, -50673, -16067, -9172, 8852, 30883, 46690, 46968, 56772, 58703, 59150, 78476, 84413,
                90106, 94777, 95148, -94624, -86776, -85833, -80822, -71902, -54562, -38638, -26483, -20207, -1290,
                12414, 12627, 19509, 30894, 32505, 46825, 50321, 69294, -83964, -81834, -78386, -70497, -69357, -61867,
                -49127, -47916, -38361, -35772, -29803, -15343, 6918, 19662, 44614, 66049, 93789, 95405, -68808, -58968,
                -45958, -36013, -32810, -28726, -13488, 3986, 26342, 29245, 30686, 47966, 58352, 68610, 74533, 77939,
                80520, 87195));

        long start = System.currentTimeMillis();

        // Note: there are better ways to benchmark but it's good enough for this
        assertEquals(getSubsetSum(test1), true);

        long elapsed = System.currentTimeMillis() - start;
        System.out.println("bonusSubsetSumNinetyRecordsTrueSpec(): elapsed time = " + elapsed + "ms");

    }

    @Ignore("Not ready yet")
    @Test(timeout = 300000)
    public void bonusSubsetSumNinetyRecordsFalseSpec() {
        ArrayList<Integer> test2 = new ArrayList<>(Arrays.asList(-97162, -95761, -94672, -87254, -57207, -22163,
                -20207, -1753, 11646, 13652, 14572, 30580, 52502, 64282, 74896, 83730, 89889, 92200, -93976, -93807,
                -64604, -59939, -44394, -36454, -34635, -16483, 267, 3245, 8031, 10622, 44815, 46829, 61689, 65756,
                69220, 70121, -92474, -61685, -55348, -42019, -35902, -7815, -5579, 4490, 14778, 19399, 34202, 46624,
                55800, 57719, 60260, 71511, 75665, 82754, -85029, -84549, -82646, -80493, -73373, -57478, -56711,
                -42456, -38923, -29277, -3685, -3164, 26863, 29890, 37187, 46607, 69300, 84808, -87565, -71009, -49312,
                -47554, -27197, 905, 2839, 8657, 14622, 32217, 35567, 38470, 46885, 59236, 64704, 82944, 86902, 90487));

        // Note: there are better ways to benchmark but it's good enough for this
        long start = System.currentTimeMillis();

        assertEquals(getSubsetSum(test2), false);

        long elapsed = System.currentTimeMillis() - start;
        System.out.println("bonusSubsetSumNinetyRecordsTrueSpec(): elapsed time = " + elapsed + "ms");
    }

    public boolean getSubsetSum(ArrayList<Integer> arrayList) {
        return arrayList.stream().anyMatch(n1 -> arrayList.stream().anyMatch(n2 -> n1 + n2 == 0));
    }

}
