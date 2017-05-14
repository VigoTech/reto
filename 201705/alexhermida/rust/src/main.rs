extern crate itertools;
extern crate time;

use itertools::Itertools;
use time::PreciseTime;


fn check_sum_zero(v: Vec<i32>) -> bool {
    let int_list = v;

    for i in (1..int_list.len()+1).rev() {

        let iter = int_list.iter();

        for item in iter.combinations(i) {
            let a = item.into_iter();
            let sum: i32 = a.sum();

            if sum == 0 {
                return true;
            }
        }
    }
    return false;
}


fn main() {
    // Main function only for benchmarking Optional bonus 2
    let start = PreciseTime::now();
    println!("Running optional bonus 2 test...");

    let list_1 = vec![1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25];

    println!("Result: {:?}", check_sum_zero(list_1));

    let end = PreciseTime::now();

    println!("Second to finish: {}", start.to(end));

}


#[cfg(test)]
mod tests {
    use super::check_sum_zero;

    #[test]
    fn base_examples_ok() {

        let list_ok = [vec![-5, -3, -1, 2, 4, 6], vec![-1, 1],
            vec![-97364, -71561, -69336, 19675, 71561, 97863],
            vec![-53974, -39140, -36561, -23935, -15680, 0], vec![-2, 2, 3]];

        for list_vector in list_ok.iter() {
            assert_eq!(true, check_sum_zero(list_vector.to_vec()));
        }

    }

    #[test]
    fn base_examples_no_ok() {
        let list_no_ok = [vec![1, 2, 3], vec![]];

        for list_vector in list_no_ok.iter() {
            assert_eq!(false, check_sum_zero(list_vector.to_vec()));
        }
    }

    #[test]
    fn optional_bonus_ok() {
        let list_ok = [vec![0], vec![-3, 1, 2],
            vec![-98634, -86888, -48841, -40483, 2612, 9225, 17848, 71967, 84319, 88875]];

        for list_vector in list_ok.iter() {
            assert_eq!(true, check_sum_zero(list_vector.to_vec()));
        }
    }

    #[test]
    fn optional_bonus_examples_no_ok() {
        let list_no_ok = [vec![-83314, -82838, -80120, -63468, -62478, -59378, -56958, -50061, -34791, -32264, -21928, -14988, 23767, 24417, 26403, 26511, 36399, 78055],
            vec![-92953, -91613, -89733, -50673, -16067, -9172, 8852, 30883, 46690, 46968, 56772, 58703, 59150, 78476, 84413, 90106, 94777, 95148],
            vec![-94624, -86776, -85833, -80822, -71902, -54562, -38638, -26483, -20207, -1290, 12414, 12627, 19509, 30894, 32505, 46825, 50321, 69294],
            vec![-83964, -81834, -78386, -70497, -69357, -61867, -49127, -47916, -38361, -35772, -29803, -15343, 6918, 19662, 44614, 66049, 93789, 95405],
            vec![-68808, -58968, -45958, -36013, -32810, -28726, -13488, 3986, 26342, 29245, 30686, 47966, 58352, 68610, 74533, 77939, 80520, 87195]];

        for list_vector in list_no_ok.iter() {
            assert_eq!(false, check_sum_zero(list_vector.to_vec()));
        }
    }

    #[test]
    fn optional_bonus_examples_ok() {
        let list_ok = [vec![-97162, -95761, -94672, -87254, -57207, -22163, -20207, -1753, 11646, 13652, 14572, 30580, 52502, 64282, 74896, 83730, 89889, 92200],
            vec![-93976, -93807, -64604, -59939, -44394, -36454, -34635, -16483, 267, 3245, 8031, 10622, 44815, 46829, 61689, 65756, 69220, 70121],
            vec![-92474, -61685, -55348, -42019, -35902, -7815, -5579, 4490, 14778, 19399, 34202, 46624, 55800, 57719, 60260, 71511, 75665, 82754],
            vec![-85029, -84549, -82646, -80493, -73373, -57478, -56711, -42456, -38923, -29277, -3685, -3164, 26863, 29890, 37187, 46607, 69300, 84808],
            vec![-87565, -71009, -49312, -47554, -27197, 905, 2839, 8657, 14622, 32217, 35567, 38470, 46885, 59236, 64704, 82944, 86902, 90487]];

        for list_vector in list_ok.iter() {
            assert_eq!(true, check_sum_zero(list_vector.to_vec()));
        }
    }
}